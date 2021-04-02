<template>
  <div id="supplier" class="app-container">
    <el-form :inline="true" ref="sear" :model="formInline" size="mini">
      <el-form-item label="供应商代码" prop="supplierCode"> 
          <el-input v-model="formInline.supplierCode" clearable></el-input> 
      </el-form-item>
      <el-form-item label="供应商名称" prop="supplierName">
        <el-input v-model="formInline.supplierName" clearable></el-input>
      </el-form-item>
      <el-form-item label="等级" prop="level">
        <el-input
          v-model.trim="formInline.level" clearable
        ></el-input>
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
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body @close="closeModel()">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row> 
          <el-col :span="12">
            <el-form-item label="供应商代码" prop="supplierCode">
              <el-input v-model="form.supplierCode" placeholder="请输入供应商代码" />
            </el-form-item>
          </el-col> 
          <el-col :span="12">
            <el-form-item label="供应商名称" prop="supplierName">
              <el-input v-model="form.supplierName" placeholder="请输入供应商名称" />
            </el-form-item>
          </el-col>   
          <el-col :span="12">
            <el-form-item label="联系人" prop="linkUser">
              <el-input v-model="form.linkUser" placeholder="请输入联系人" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话" prop="linkPhone">
              <el-input v-model="form.linkPhone" placeholder="请输入联系电话"  />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="通信地址" prop="address">
              <el-input v-model="form.address" type="textarea" placeholder="请输入通信地址" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="供应商说明" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入供应商说明" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="供应商等级" prop="level">
              <el-select v-model="form.level" placeholder="请选择供应商等级" clearable>
                <el-option value="A"></el-option>
                <el-option  value="B"></el-option>
                <el-option value="C"></el-option>
                <el-option value="D"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col >
            <el-form-item label="资质证明" prop="qualificationImage">
              <el-upload
                :class="{uoloadSty:showBtnImg,disUoloadSty:noneBtnImg}" 
                :action=" action + '/uploadToNginxForOpen'"
                :data="{
                  modelName: 'qualificationImage',
                }"
                name='file'
                :on-success="handleAvatarSuccess"
                :before-upload="beforeUploadB"
                :file-list="accessory"
                list-type="picture-card" 
              >
                <i slot="default" class="el-icon-plus"></i>
                <div slot="file" slot-scope="{file}" style="width:100%;height:100%">
                  <img class="el-upload-list__item-thumbnail" :src="file.url" alt="" style="object-fit:cover">
                  <span class="el-upload-list__item-actions">
                    <span class="el-upload-list__item-preview" @click="handlePictureCardPreview(file)">
                      <i class="el-icon-zoom-in"></i>
                    </span>
                    <span
                      v-if="!disabled"
                      class="el-upload-list__item-delete"
                      @click="handleDownload(file)"
                    > 
                      <i class="el-icon-download"></i> 
                    </span>
                    <span
                      v-if="!disabled"
                      class="el-upload-list__item-delete"
                      @click="handleRemove(file)"
                    >
                      <i class="el-icon-delete"  v-if="editable"></i>
                    </span>
                  </span>
                </div>
                <el-dialog :visible.sync="picVis" append-to-body>
                  <img width="100%" :src="dialogImageUrl" alt="">
                </el-dialog>
              </el-upload>
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
    name:'supplier',
    components: {
        TabelCommon: () => import("@/components/common/TabelCommon"),
    },
  data() {
    return { 
      action:process.env.VUE_APP_BASE_API,
      formInline: {
        supplierCode: "",
        supplierName: "",
        level: "",
      },
      TableHeader: [
        { type: "supplierCode", label: "供应商代码" },
        { type: "supplierName", label: "供应商名称" },
        { type: "linkUser", label: "联系人" },
        { type: "linkPhone", label: "联系电话" },
        { type: "address", label: "通信地址" }, 
        { type: "remark", label: "供应商说明" },
        { type: "level", label: "供应商等级" },
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
        supplierCode: [
          { required: true, message: "供应商编号不能为空", trigger: "blur" }
        ],
        supplierName: [
          { required: true, message: "供应商名称不能为空", trigger: "blur" }
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
      },
      editable:true,
      showBtnImg:true,  //显示上传图标按钮
      noneBtnImg:false,  //不显示上传图标按钮
      disabled:false,
      accessory:[],
      picVis:false,
      dialogImageUrl:'',
      imageList:[],
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
            url:'/ums/umsSupplier/list',
            method:'post',
            data:{
                pageNum,
                pageSize: this.pageSize,
                supplierCode :this.formInline.supplierCode,
                supplierName :this.formInline.supplierName,
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
          url:'/ums/umsSupplier/save',
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
        supplierCode: undefined,
        supplierName: undefined, 
        level: undefined,
        linkUser: undefined,
        linkPhone: undefined,
        address: undefined,
        remark: undefined, 
        qualificationImage: undefined, 
      };
      this.resetForm("form");
      this.accessory = []
    },
    closeModel(){
      this.accessory = []
    },
    clear(){
      this.formInline = {
          supplierCode: "",
          supplierName: "",
          level: "",
      } 
      this.reset()
      this.multipleSelection=[]
      this.$refs.tabel.clearSelect()
      this.getList()
    },
    CompileModel(row){ 
      this.accessory = []
      this.reset()
      if (row) {
        this.api({
            url:'/ums/umsSupplier/sel/' + row.row.id,
            method:'post',
        }).then(res=>{
            this.form=res.datas 
            if (res.datas.qualificationImage ) {
              console.log(123)
              this.accessory.push({
                name:new Date().getTime(),
                url:this.form.qualificationImage
              })
              this.noneBtnImg = true   //不显示上传按钮
              this.showBtnImg = false  //显示上传按钮
            } else {
              this.noneBtnImg = false   
              this.showBtnImg = true   
            }
            this.open = true
            this.title = '修改'
        })
      } else {
        this.noneBtnImg = false   
        this.showBtnImg = true   
          this.open = true
          this.title = '新增'
      } 
    },
    delItems(){ 
      console.log(this.multipleSelection)
      this.api({
          url:'/ums/umsSupplier/del',
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
    handleAvatarSuccess(response, file, fileList){ 
      this.noneBtnImg = true   //不显示上传按钮
      this.showBtnImg = false  //显示上传按钮
      this.form.qualificationImage = response
      this.accessory = fileList 
      this.downloading = false
    },
    beforeUploadB(file){
      this.downloading = true
    },
    handlePictureCardPreview(file) { 
      this.dialogImageUrl = file.url;
      this.picVis = true;
    }, 
    handleDownload(file){
      console.log(file)
      var alink = document.createElement("a");
      alink.href = file.url;
      alink.download = "pic"; //图片名
      alink.click();
    },
    handleRemove(file,fileList){ 
      this.noneBtnImg = false   //不显示上传按钮
      this.showBtnImg = true  //显示上传按钮
      var index = this.accessory.findIndex(item=>{
        return item.url == file.url
      })
      this.accessory.splice(index,1)
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
.disUoloadSty >>> .el-upload--picture-card{
  	display:none;   /* 上传按钮隐藏 */
 	}
</style>