<template>
  <div id="login">
    <div class="main"></div>
    <div class="info">
      <div class="info_top"></div>
      <div class="info_title">I_WMS</div>
      <div class="info_main">
        <el-form :model="loginForm" ref="loginForm" :rules="loginRules" label-position="left">
          <el-form-item prop="userAccount" style="margin-top: 40px">
            <el-input
              name="userAccount"
              type="text"
              v-model="loginForm.userAccount"
              autocomplete="off"
              placeholder="请输入用户名"
              clearable
            >
              <span slot="prefix" style="color: rgb(95, 132, 241)" class="el-icon-user"></span>
            </el-input>
          </el-form-item>
          <el-form-item prop="userPwd" style="margin-top: 30px">
            <el-input
              type="password"
              name="userPwd"
              v-model="loginForm.userPwd"
              show-password
              clearable
              autocomplete="off"
              placeholder="请输入密码"
              @keyup.enter.native="tologin()"
            >
              <span slot="prefix" style="color: rgb(95, 132, 241)" class="el-icon-lock"></span>
            </el-input>
          </el-form-item>
          <el-form-item style="margin-top: 50px;text-align: center">
            <el-button style="width: 45%" :loading="loading" type="primary" @click="tologin()">登录</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import { isvalidUsername } from "@/utils/validate";
import { mapActions } from "vuex";
import {getToken, removeToken, setToken} from '@/utils/auth'
import "../../public/gVerify";
import Cookies from 'js-cookie'
import axios from 'axios'
export default {
  data() {
    const validateUsername = (rule, value, callback) => {
      if (!isvalidUsername(value)) {
        callback(new Error("请输入正确的用户名"));
      } else {
        callback();
      }
    };
    const validatePass = (rule, value, callback) => {
      if (value.length < 3) {
        callback(new Error("密码不能小于3位"));
      } else {
        callback();
      }
    };
    return {
      loginForm: {
        userAccount: "",
        userPwd: "",
      },
      loginRules: {
        userAccount: [
          { required: true, trigger: "blur", validator: validateUsername },
        ],
        userPwd: [
          { required: true, trigger: "blur", validator: validatePass },
        ],
      },
      loading:false
    };
  },
  mounted() { 
  },
  methods: {
    ...mapActions(["loginStatus"]),
    tologin() { 
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          this.$store.dispatch('Login',this.loginForm).then(res=>{
            this.loading = false  
            this.$router.push({path: '/welcome'}) 
          }).catch(err=>{
            this.loading = false 
          })
        } else {
          return false
        }
      }) 
    }, 
    // 验证码
    changeQRCode() {
      this.QRCode = new GVerify("v_container").options.code;
    },
    // 请求到菜单列表的数据处理
    async getMenuList(arr) {
      var arrParent = [];
      for (const key in arr) {
        if (arr[key].perType == 0) {
          arr[key].parentId == 0 ? arrParent.push(arr[key]) : "";
        }
      }
      //
      this.deepMapTree(arrParent, arr);
      return await arrParent;
    },
    // 遍历
    deepMapTree(arr1, arr2) {
      // 从底层开始遍历
      for (const key in arr1) {
        if (arr2[key].perType == 0) {
            if (arr1.hasOwnProperty(key)) {
            // 一级菜单
            const element = arr1[key];
            // 添加子节点
            element.children = [];
            for (const k in arr2) {
              var e = arr2[k];
              // 如果当前节点的父级ID为外层遍历的主键ID，则为外层的子节点
              if (e.parentId == element.id) {
                element.children.push(e);
              }
            }
            // 上级遍历完成之后，如果外层拥有子节点，进行递归调用
            if (element.children.length > 0) {
              this.deepMapTree(element.children, arr2);
              element.HasChild = true;
            }
          }
        } 
      }
    },
  },
};
</script>

<style scoped>
#login {
  width: 100vw;
  height: 100vh;
  /* background-color: rgb(72, 100, 184); */
  position: relative;
}
.main {
  width: 100%;
  height: 200px;
  background-color: rgb(95, 132, 241);
  position: absolute;
  top: calc(50% - 100px);
}
.info {
  width: 360px;
  height: 400px;
  background-color: #ffffff;
  position: absolute;
  left: calc(50% - 200px);
  top: calc(50% - 200px);
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  border-radius: 5px;
  overflow: hidden;
}
.info_top {
  width: 100%;
  height: 20px;
  background-color: rgb(95, 132, 241);
}
.info_title {
  text-align: center;
  font-size: 30px;
  color: rgba(95, 131, 241, 0.932);
  margin: 20px 0 20px 0;
}
.info_main {
  width: 90%;
  margin: 0 auto;
}
</style>