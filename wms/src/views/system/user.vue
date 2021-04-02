<template>
  <div id="staff">
    <div class="operate">
      <el-button-group>
        <el-button
          type="primary"
          icon="el-icon-circle-plus-outline"
          @click="addNew()"
          v-hasPermi="['system:user:add']"
          size="small"
        >新增</el-button> 
        <el-button type="primary" v-hasPermi="['system:user:del']" icon="el-icon-remove-outline" @click="removeId()" size="small">删除</el-button> 
      </el-button-group>
    </div>
    <div class="staff_body">
      <div class="staff_body_search">
        <el-form :inline="true" :model="formInline" class="demo-form-inline">
          <el-form-item label="所属部门"> 
            <treeselect style="width:220px" v-model="formInline.depaCode" :options="options" :normalizer="normalizer"  :show-count="true" placeholder="请选择归属部门" />
          </el-form-item>
          <el-form-item label="员工编号">
            <el-input v-model="formInline.userAccount" clearable placeholder="员工编号"></el-input>
          </el-form-item>
          <el-form-item label="员工姓名">
            <el-input v-model="formInline.userName" clearable placeholder="员工姓名"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button v-hasPermi="['system:user:list']" type="primary" @click="getuserlist()">查询</el-button>
          </el-form-item>
        </el-form>
      </div>
      <div>
        <div >
          <TabelCommon
            :TableHeader="TableHeader"
            :TabularData="TabularData"
            :recordsCount="recordsCount"
            :currentPage="currentPage"
            :isremove="true&&(hasPerm('system:user:del'))"
            :isamend="true&&(hasPerm('system:user:amend'))"
            :isChoice="true"
            :loading="loading"
            @amendModel="amendModel"
            @RemoveModel="RemoveModel"
            @handleSelectionChange="handleSelectionChange"
            @handleCurrentChange="handleCurrentChange"
            @handleSizeChange="handleSizeChange" 
          ></TabelCommon>
        </div> 
      </div>
    </div>
    <div>
      <el-dialog :visible.sync="uesrDialog" @close="resetForm()">
        <!-- <div class="form_user"> -->
        <el-form :model="Userform" :rules="rules" inline ref="User" label-width="80px"  >
              <el-form-item label="员工编号" prop="userAccount">
                <el-input v-model.trim="Userform.userAccount" style="width:220px"></el-input>
              </el-form-item>
              <el-form-item label="密码" prop="userPwd">
                <el-input v-model.trim="Userform.userPwd"  style="width:220px"></el-input>
              </el-form-item>
              <el-form-item label="员工姓名" prop="userName">
                <el-input v-model.trim="Userform.userName" style="width:220px"></el-input>
              </el-form-item>
              <el-form-item label="角色" prop="roles">
                <el-select v-model="Userform.roles" multiple placeholder style="width:220px">
                  <el-option
                    v-for="(item,index) in allRoles"
                    :key="index"
                    :label="item.roleName"
                    :value="item.id"
                  ></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="所属部门" prop="depaCode"> 
                <treeselect style="width:220px" v-model="Userform.depaCode" :options="options" :normalizer="normalizer"  :show-count="true" placeholder="请选择归属部门" />
              </el-form-item>
              <el-form-item label="员工分类" prop="isExter">
                <el-select v-model="Userform.isExter" placeholder="" style="width:220px">
                  <el-option label="内部" :value="false"></el-option>
                  <el-option label="外部" :value="true"></el-option>
                </el-select>
              </el-form-item> 
              <el-form-item label="状态"  prop="state">
                    <el-switch
                        v-model="Userform.state"
                        active-text="正常"
                        inactive-text="停用">
                    </el-switch>
                </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
            <el-button @click="uesrDialog = false">取 消</el-button>
            <el-button type="primary" v-if="isnow" @click="refer()">提交</el-button>
            <el-button type="primary" v-else @click="amendTo()">提交修改</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>
<script>
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
export default {
    name:'User',
  components: {
    TabelCommon: () => import("../../components/common/TabelCommon"), 
    Treeselect
  },
  data() {
    return {
      isnow: true,
      isActive: true,
      formInline: {
        user: "",
        department: undefined,
        depaCode:undefined,
        name: "",
      },
      options: [
      ],
      defaultProps: {
        checkStrictly: true,
        children: 'childVOS',
        label: 'name',
        value:"code",
        id:"id"
      },
      Userform: {
        department: undefined,
        depaCode:undefined,
        userAccount: "",
        userName: "",
        userPwd: "",
        roles: [],
        isExter: "",
        placeholder:"",
        state:true
      },
      rules: {
        userAccount: [
          { required: true, message: "请输入员工编号", trigger: "blur" },
          { min: 3, message: "长度大于3个字符", trigger: "blur" },
        ],
        // userPwd: [
        //   { required: true, message: "请输入密码", trigger: "blur" },
        //   { min: 3, message: "长度大于3个字符", trigger: "blur" },
        // ],
        userName: [{ required: true, message: "请输入姓名", trigger: "blur" }],
        roles: [{ required: true, message: "请选择角色", trigger: "blur" }],
        department: [
          { required: true, message: "请选择部门", trigger: "blur" },
        ],
        isExter: [
          { required: true, message: "请选择员工分类", trigger: "blur" },
        ],
      },
      allRoles: [],
      // 当前要修改的对象
      amendItem: {},
      // 选择的项
      multipleSelection: [],
      //   角色
      roles: [],
      // 部门
      branch: [],
      TableHeader: [
        { type: "userAccount", label: "员工编号" },
        { type: "userName", label: "员工姓名" },
        { type: "state", label: "状态" },
        { type: "depaName", label: "所属部门" },
        { type: "createTime", label: "创建时间" },
      ],
      TabularData: [],
      // 当前页
      currentPage: 1,
      recordsCount: 0,
      // 每页条数
      pageSize: 10,
      loading: false,
      uesrDialog: false,
    };
  },
  mounted() {
    this.getuserlist();
    this.getTreeList();
    this.getRole();
  },
  methods: {  
    //   查询部门
    getTreeList() {
      this.api({
        url:"/ums/umsDepa/depaTree/0",
        method:'post'
      }).then((res) => {
        if (res.resp_code == 0) {
          this.copydata(res.datas);
          this.options = res.datas; 
        }
      });
    },
    // 查询角色
    getRole() {
      this.api({
        url:"/ums/umsRole/list",
        method:'post',
        data:{}
      }).then((res) => { 
        this.allRoles = res.datas.list;
      });
    },
    // 新增
    refer() {
      this.$refs.User.validate((valid) => {
        if (valid) { 
          this.api({
            url:'/ums/umsUser/save',
            method:'post',
            data:{
                userAccount: this.Userform.userAccount,
                userPwd: this.Userform.userPwd,
                userName: this.Userform.userName,
                depaCode: this.Userform.depaCode,
                isExter: this.Userform.isExter,
                roleIds: this.Userform.roles,
                state: this.Userform.state,
            }
          }).then((res) => {
            if (res.resp_code == 0) {
              this.winmeg(res.resp_msg);
              this.uesrDialog = false;
              this.resetForm();
              this.getuserlist();
            }
          });
        } else {
          return false;
        }
      });
    },
    amendTo() {
      this.$refs.User.validate((valid) => {
        if (valid) { 
          this.api({
            url:'/ums/umsUser/save',
            method:'post',
            data:{
                userAccount: this.Userform.userAccount,
                userPwd: this.Userform.userPwd,
                userName: this.Userform.userName,
               depaCode: this.Userform.depaCode,
                isExter: this.Userform.isExter,
                roleIds: this.Userform.roles,
                state: this.Userform.state,
            }
          })
          .then((res) => {
            if (res.resp_code == 0) {
              this.winmeg(res.resp_msg);
              this.uesrDialog = false;
              this.resetForm();
              this.getuserlist();
            }
          });
          this.isActive = true
        } else {
          return false;
        }
      });
    },
    resetForm() {
        this.Userform = {
        department: undefined,
        depaCode:undefined,
        userAccount: "",
        userName: "",
        userPwd: "",
        roles: [],
        isExter: "",
        placeholder:"", 
        state:true
      },
        this.Userform.roles = []
      this.$refs.User.resetFields();
    },
    // 部门列表数据处理
    copydata(arr) {
      arr.forEach((item) => {
        if (item.childVOS.length == 0 ) {
          delete item.childVOS
        } else{
          this.copydata(item.childVOS)
        }
      });
    },
    normalizer(node){ 
        if (node.childVOS && !node.childVOS.length) {
            delete node.childVOS;
        }
        return {
            id:node.code,
            label: node.name,
            children: node.childVOS,
        } 
    },
    // 查询用户列表
    getuserlist(pageNum = 1) {
      this.loading = true;
      this.currentPage = pageNum; 
      this.api({
        url:"/ums/umsUser/list",
        method:'post',
        data:{
            pageNum,
            pageSize: this.pageSize,
            userName: this.formInline.userName,
            userAccount: this.formInline.userAccount,
            depaCode: this.formInline.depaCode
        }
      }) .then((res) => {
        if (res.resp_code == 0 ) {
          this.TabularData = res.datas.list;
          this.recordsCount = res.datas.total;
        } else {
          this.TabularData = [];
          this.recordsCount = 0;
          this.failmeg(res.resp_msg);
        }
        this.loading = false;
      });
    },
    addNew() {
      this.uesrDialog = true;
      this.isnow = true;
    },
    amendModel(row) {
        this.Userform.roles=[]
      this.api({
        url:"/ums/umsUser/sel/" + row.row.id,
        method:'post',
      }).then((res) => {
        if (res.resp_code == 0) {
            this.uesrDialog = true;
            this.isnow = false; 
            this.Userform.userAccount = row.row.userAccount;
            this.Userform.userName = row.row.userName;
            this.Userform.isExter = row.row.isExter;
            this.Userform.depaCode = row.row.depaCode;
            this.Userform.state = row.row.state;
            res.datas.roles.forEach(element => { 
                this.Userform.roles.push(element.id)
            });
        }
      });
    }, 
    // 删除
    removeId() {
      this.api({
        url:"/ums/umsUser/del", 
        method:'post',
        data:{
            ids:this.multipleSelection,
        } 
      }).then((res) => {
          if (res.resp_code == 0) {
            this.winmeg(res.resp_msg);
            this.getuserlist();
          }
        })
        .catch((err) => {
          this, failmeg(res.resp_msg);
        });
    },
    RemoveModel(row){
        this.api({
            url:'/ums/umsUser/del',
            method:'post',
            data:{
                ids:[row.row.id]
            }
        }).then(res=>{
            this.winmeg(res.resp_msg);
            this.getuserlist();
        }).catch(err=>{
            
        })
    },
    getid(row) {
      return row.number;
    },
    // 选中的数据的id
    handleSelectionChange(val) { 
      val.map((item) => {
        this.multipleSelection.push(item.id);
      });
    },
    // 分页事件
    handleCurrentChange(val) {
      this.getuserlist(val);
    },
    // pageSize变更
    handleSizeChange(val) {
      this.pageSize = val;
      this.getuserlist(1);
    },
  },
};
</script>
<style scoped>
#staff {
  height: calc(100vh - 50px);
}
.operate {
  width: 100%;
  height: 40px;
  padding-left: 10px;
}
.staff_body {
  width: 100%;
  /* height: 100%; */
  padding: 10px;
}
.staff_body_search {
  margin-left: 10px;
}
.staff_body_show {
  width: 100%;
  display: flex;
  /* height: 85vh; */
}
.staff_body_show_table {
  width: 50%;
  height: 100%;
  overflow: auto;
}
.staff_body_show_info {
  width: 50%;

  display: flex;
  flex-direction: column;
  align-items: center;
  /* justify-content: space-between; */
}
.staff_body_show_info_craft,
.staff_body_show_info_role,
.staff_body_show_info_department {
  width: 100%;
  height: 33%;
}
.title {
  width: 100%;
  background-color: rgb(194, 193, 193);
  text-align: center;
  height: 25px;
  line-height: 25px;
  border: 1px solid #a5a4a4;
}
.info_list {
  overflow-y: auto;
  height: 100%;
}
.info_list_item {
  height: 20px;
  line-height: 20px;
  background-color: rgb(245, 245, 245);
  margin-bottom: 3px;
  border: 1px solid #d3d3d3fa;
}
.info_list_item_index {
  display: inline-block;
  text-align: center;
  width: 20px;
  border-right: 1px solid #d3d3d3fa;
}
.info_list_item_item {
  padding-left: 10px;
}

.form_user {
  width: 100%;
  display: flex;
  justify-content: space-around;
}
</style>