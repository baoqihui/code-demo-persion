<template>
  <div id="role">
    <div class="operate">
      <el-button-group>
        <el-button v-hasPermi="['system:role:add']" type="primary" icon="el-icon-circle-plus-outline" size="small" @click="openDia(0)">新增</el-button>
        <el-button v-hasPermi="['system:role:edit']" type="primary" :disabled="isalter" icon="el-icon-edit" @click="openDia(1)" size="small">修改</el-button>
        <el-button v-hasPermi="['system:role:del']" type="primary" :disabled="isalter" icon="el-icon-remove-outline" @click="delrole()" size="small">删除</el-button>
      </el-button-group>
    </div>
    <div class="role_main">
      <div class="role_list">
        <el-divider content-position="left">角色列表</el-divider>
        <div class="tree">
          <p v-for="(item,index) in allRoles" :key="index" :class="{tree_p:item.id==current}" @click="pColor(item)">{{item.roleName}}</p>
        </div>
      </div>
      <div class="role_list" v-if="aaa">
        <el-divider content-position="left">功能权限</el-divider>
        <el-tree
          :data="qxData"
          show-checkbox
          node-key="id"
          :default-expanded-keys="defChe"
          :default-checked-keys="defChe"
          :props="defaultProps"
          @check-change="reload(a)">
        </el-tree>
      </div>
      
    </div>
    <el-dialog :title="title" :visible.sync="dialogForm"  @close="resetForm()" destroy-on-close width="30%">
      <el-form :model="form" ref="newadd" :rules="rules" label-width="80px">
        <el-form-item label="角色名称" prop="roleName">
          <el-input v-model.trim="form.roleName"></el-input>
        </el-form-item>
        <el-form-item prop="tree">
          <el-tree
            v-if="bbb"
            ref="operTree"
            :data="qxData"
            show-checkbox
            node-key="id"
            :props="defaultProps"
            :default-checked-keys="formdefChe"
            accordion >
          </el-tree>
        </el-form-item>
        <el-form-item>
          <el-button style="float:right" v-if="isadd" type="primary" @click="addDia()">确定</el-button>
          <el-button style="float:right" v-else type="primary" @click="amendDia()">确定</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // 权限的所有最后一层id
      ids:[],
      isalter:true,
      aaa:true,
      bbb:true,
      isadd:true,
      allRoles:[],
      // 选中的id
      cheId:"",
      current:-1,
      qxData:[],
      defExp:[],
      defChe:[],
      dialogForm:false,
      title:"新增",
      form:{
        roleName:"",
      },
      formdefChe:[],
      rules:{
        roleName:[{ required: true, message: '请输入角色名称', trigger: 'blur' }],
      },
      name:"",
      defaultProps: {
        children: 'children',
        label: 'perName'
      }
    };
  },
  mounted(){
    this.getRole()
    this.allTree()
  },
  methods: { 
    // 查询角色
    getRole() {
      this.api({
        url:'/ums/umsRole/list',
        method:'post',
        data:{}
      }).then((res) => {
        this.allRoles = res.datas.list;
      });
    },
    // 查询所有权限
    allTree(){
      this.api({
        url:'/ums/umsPermission/selectPermissionTreeByParentId/0',
        method:'post',
      }).then(res=>{
        if (res.resp_code == 0) {
          this.gitListId(res.datas) 
          this.qxData= res.datas
        } else {
          this.failmeg(res.resp_msg)
        }
      })
    },
    // 得到所有最后一层的ID
    gitListId(arr){
      arr.map((v)=>{
        if (v.children&&v.children.length>0) {
          this.gitListId(v.children)
        }else{
          this.ids.push(v.id)
        }
      })
    },
    // 选择的角色
    pColor(item){
      this.defChe = []
      this.cheId = item.id
      this.name = item.roleName
      this.isalter = false
      this.current = item.id
      
      var listIds = item.perIds?item.perIds:[]
      item.perIds.map(v=>{
        if (this.ids.includes(v)) {
          this.defChe.push(v)
        }
      })
      this.reload()
    },
    openDia(a){
      this.dialogForm = true
      if (a == 1) {
        this.isadd = false
        this.form.roleName = this.name
        this.formdefChe = this.defChe
      }else{
        this.isadd = true
        this.form.roleName = ""
        this.formdefChe =[]
      }
      this.reloadB()
    },
    // 新增提交
    addDia(){
      this.$refs.newadd.validate((valid)=>{
        if (valid) {
          this.api({
            url:'/ums/umsRole/addRoleAndPer',
            method:'post',
            data:{
              perIdList:this.$refs.operTree.getHalfCheckedKeys().concat(this.$refs.operTree.getCheckedKeys()),
              roleName:this.form.roleName
            }
          }).then(res=>{
            if (res.resp_code == 0) {
              this.getRole()
              this.winmeg(res.resp_msg)
            } else {
              this.failmeg(res.resp_msg)
            }
            this.dialogForm = false
          })
        }else{
          return false
        } 
      })
    },
    // 修改
    amendDia(){
      this.$refs.newadd.validate((valid)=>{
        if (valid) {
          this.api({
            url:'/ums/umsRole/editRoleAndPer',
            method:'post',
            data:{
              id:this.cheId,
              perIdList:this.$refs.operTree.getHalfCheckedKeys().concat(this.$refs.operTree.getCheckedKeys()),
              roleName:this.form.roleName
            }
          }).then(res=>{
            if (res.resp_code == 0) {
              this.getRole()
              this.reload()
              this.winmeg(res.resp_msg)
            } else {
              this.failmeg(res.resp_msg)
            }
            this.dialogForm = false
            this.current = -1
            this.isalter = true
          })
        }else{
          return false
        } 
      }) 
    },
    delrole(){
      this.api({
        url:'/ums/umsRole/del/' + this.cheId,
        method:'post', 
      }).then(res=>{
        if (res.resp_code == 0) {
          this.getRole()
          this.winmeg(res.resp_msg)
        } else {
          this.failmeg(res.resp_msg)
        }
        this.current = -1
        this.isalter = true
      })
    },
    resetForm() {
      this.$refs.newadd.resetFields();
    },
    handleCheckChange(data, checked, indeterminate) {
    },
    reload(a){
      this.aaa = false;
      this.$nextTick(function(){
        this.aaa = true
      })
    },
    reloadB(){
      this.bbb = false;
      this.$nextTick(function(){
        this.bbb = true
      })
    },
  },
};
</script>

<style scoped>
#roleInfo {
  width: 100%;
}
.role_main {
  width: 100%;
  height: 600px;
  display: flex;
  justify-content: space-around;
}
.role_list {
  width: 40%;
  height: 600px;
  border: 1px solid #c1c5ce;
  border-radius: 5px;
  overflow-y: auto;
}
.tree p {
  margin: 2px 0;
  padding: 2px 0;
}
.tree_p{
  background-color: rgb(218, 237, 255);
}
.operate{
    margin: 5px 20px;
}
</style>