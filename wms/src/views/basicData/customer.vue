<template>
  <div id="customer" class="app-container">
    <el-form :inline="true" ref="sear" :model="formInline" size="mini">
      <el-form-item label="客户代码" prop="custCode"> 
          <el-input v-model="formInline.custCode" clearable></el-input> 
      </el-form-item>
      <el-form-item label="客户名称" prop="custName">
        <el-input v-model="formInline.custName" clearable></el-input>
      </el-form-item>
      <el-form-item label="客户等级" prop="level"> 
        <el-select v-model="formInline.level" clearable>
            <el-option value="A"></el-option>
            <el-option  value="B"></el-option>
            <el-option value="C"></el-option>
            <el-option value="D"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getList()">查询</el-button>
        <el-button type="primary" @click="clear()">重置</el-button>
        <el-button type="primary" @click="CompileModel()">新增</el-button>
        <el-button type="primary" :disabled='!(multipleSelection.length>0)' @click="delItems()">删除</el-button>
      </el-form-item>
    </el-form>

    <TabelCommon
      ref="tabel"
      :TableHeader="TableHeader"
      :TabularData="TabularData"
      :recordsCount="recordsCount"
      :currentPage="currentPage"
      :loading="loading"
      :isChoice="true"
      :iscompile="true"
      :isPager="true"
      :isXhShow="false"
      @CompileModel="CompileModel"
      @handleSelectionChange="handleSelectionChange"
      @handleCurrentChange="handleCurrentChange"
      @handleSizeChange="handleSizeChange"
    ></TabelCommon>
    <!-- 添加或修改部门对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row> 
          <el-col :span="12">
            <el-form-item label="客户代码" prop="custCode">
              <el-input v-model="form.custCode" placeholder="请输入客户代码" />
            </el-form-item>
          </el-col> 
          <el-col :span="12">
            <el-form-item label="客户名称" prop="custName">
              <el-input v-model="form.custName" placeholder="请输入客户名称" />
            </el-form-item>
          </el-col> 
          <el-col :span="12">
            <el-form-item label="客户简称" prop="custShort">
              <el-input v-model="form.custShort" placeholder="请输入客户简称"  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="客户等级" prop="level">
              <!-- <el-input v-model="form.level" placeholder="请输入客户等级" maxlength="11" /> -->
              <el-select v-model="formInline.level" placeholder="请选择客户等级" clearable>
                <el-option value="A"></el-option>
                <el-option  value="B"></el-option>
                <el-option value="C"></el-option>
                <el-option value="D"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系人" prop="linkUser">
              <el-input v-model="form.linkUser" placeholder="请输入联系人" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话" prop="linkPhone">
              <el-input v-model="form.linkPhone" placeholder="请输入联系电话" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="通信地址" prop="address">
              <el-input v-model="form.address" type="textarea" placeholder="请输入通信地址" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="客户说明" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入客户说明" />
            </el-form-item>
          </el-col>
           
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
    name:'customer',
    components: {
        TabelCommon: () => import("@/components/common/TabelCommon"),
    },
  data() {
    return {
      formInline: {
        custCode: "",
        custName: "",
        level: "",
      },
      TableHeader: [
        { type: "custCode", label: "客户代码" },
        { type: "custName", label: "客户名称" },
        { type: "custShort", label: "简称" },
        { type: "level", label: "客户等级" },
        { type: "linkUser", label: "联系人" },
        { type: "linkPhone", label: "联系电话" },
        { type: "address", label: "通信地址" }, 
        { type: "remark", label: "客户说明" },
        { type: "createUser", label: "创建人" },
        { type: "createTime", label: "创建时间" },
        { type: "updateUser", label: "编辑人" },
        { type: "updateTime", label: "编辑时间" },
      ],
      TabularData: [],
      // 当前页
      currentPage: 1,
      recordsCount: 0,
      // 每页条数
      pageSize: 10,
      loading: false,
      multipleSelection: [],

      title:'',
      open:false,
      form:{},
      // 表单校验
      rules: {
        custCode: [
          { required: true, message: "客户编号不能为空", trigger: "blur" }
        ],
        custName: [
          { required: true, message: "客户名称不能为空", trigger: "blur" }
        ],
        custShort: [
          { required: true, message: "客户简称不能为空", trigger: "blur" }
        ], 
        linkUser: [
          { required: true, message: "联系人不能为空", trigger: "blur" }
        ], 
        // linkPhone: [
        //   {
        //     pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
        //     message: "请输入正确的手机号码",
        //     trigger: "blur"
        //   }
        // ]
      }
    };
  },
  mounted(){
    this.getList()
  },
  methods:{
    getList(pageNum = 1){
        this.loading = true;
        this.currentPage = pageNum;
        this.api({
            url:'/ums/umsCustomer/list',
            method:'post',
            data:{
                pageNum,
                pageSize: this.pageSize,
                custCode :this.formInline.custCode,
                custName :this.formInline.custName,
                level :this.formInline.level,
            }
        }).then(res=>{ 
            this.loading = false
            this.TabularData = res.datas.list
            this.recordsCount = res.datas.total
        })
    },
    submitForm(){
        this.api({
            url:'/ums/umsCustomer/save',
            method:'post',
            data:this.form
        }).then(res=>{
            this.winmeg(res.resp_msg)
            this.open = false
            this.getList()
        }) 
    },
    reset() {
      this.form = {
        id: undefined,
        custCode: undefined,
        custName: undefined,
        custShort: undefined,
        level: undefined,
        linkUser: undefined,
        linkPhone: undefined,
        address: undefined,
        remark: undefined, 
      };
      this.resetForm("form");
    },
    clear(){
        this.formInline = {
            custCode: "",
            custName: "",
            level: "",
        }
        this.multipleSelection=[]
        this.$refs.tabel.clearSelect()
        this.getList()
    },
    CompileModel(row){
        this.reset()
        if (row) {
            this.api({
                url:'/ums/umsCustomer/sel/' + row.row.id,
                method:'post',
            }).then(res=>{
                this.form=res.datas
                this.open = true
                this.title = '修改'
            })
        } else {
            this.open = true
            this.title = '新增'
        } 
    },
    delItems(){ 
        console.log(this.multipleSelection)
        this.api({
            url:'/ums/umsCustomer/del',
            method:'post',
            data:{
                ids:this.multipleSelection
            }
        }).then(res=>{
            this.winmeg(res.resp_msg);
            this.getList()
            this.multipleSelection=[]
        }).catch(err=>{
            this.multipleSelection=[]
        })
    },
      // 选中的数据的id
    handleSelectionChange(val) {
      this.multipleSelection = [];
      val.map((item) => {
        this.multipleSelection.push(item.id);
      });
    },
    // 分页事件
    handleCurrentChange(val) {
      this.getList(val);
    },
    // pageSize变更
    handleSizeChange(val) {
      this.pageSize = val;
      this.getList(1);
    },
  }
};
</script>

<style scoped>
</style>