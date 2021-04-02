// import {getInfo, login, logout} from '@/api/login'
import {getToken, removeToken, setToken} from '@/utils/auth'
import {default as api} from '../../utils/api'
import store from '../../store'
import router from '../../router'

const user = {
  state: {
    nickname: "",
    nickAccount: "",
    depaCode: "",
    depaName: "",
    userId: "",
    avatar: 'https://www.gravatar.com/avatar/6560ed55e62396e40b34aac1e5041028',
    role: '',
    menus: [],
    permissions: [],//按钮
  },
  mutations: {
    SET_USER: (state, userInfo) => {
      state.nickAccount = userInfo.userAccount;
      state.nickname = userInfo.userName;
      state.depaCode = userInfo.depaCode;
      state.depaName = userInfo.depaName;
      state.userId = userInfo.id;
      state.role = userInfo.roleName;
      state.menus = userInfo.userAllPermission;
      state.permissions = userInfo.userButtonPermission;
    },
    RESET_USER: (state) => {
      state.nickAccount = '';
      state.nickname = '';
      state.depaCode = '';
      state.depaName = '';
      state.userId = '';
      state.role = '';
      state.menus = [];
      state.permissions = [];
    }
  },
  actions: {
    // 登录
    Login({commit, state}, loginForm) {
      return new Promise((resolve, reject) => {
        api({
          url: "/ums/auth",
          method: "post",
          data: loginForm
        }).then(res => {
          if (res.resp_code === 0) {
            //cookie中保存前端登录状态
            setToken();
          }
          resolve(res);
        }).catch(err => {
          reject(err)
        })
      })
    },
    // 获取用户信息
    GetInfo({commit, state}) {
      return new Promise((resolve, reject) => {
        api({
          url: '/ums/getInfo',
          method: 'post'
        }).then(res => {
          //储存用户信息
          commit('SET_USER',res.datas);
          //cookie保存登录状态,仅靠vuex保存的话,页面刷新就会丢失登录状态
          setToken();
          //生成路由
          let userPermission = res.datas ;//用户全部信息 
          store.dispatch('GenerateRoutes', userPermission).then(() => {
            //生成该用户的新路由json操作完毕之后,调用vue-router的动态新增路由方法,将新路由添加
            router.addRoutes(store.getters.addRouters)
          })
          resolve(res)
        }).catch(error => {
          reject(error)
        })
      })
    },
    // 登出
    LogOut({commit}) {
      return new Promise((resolve) => {
        api({
          url: "/ums/logout",
          method: "post"
        }).then(data => {
          commit('RESET_USER')
          removeToken()
          resolve(data);
        }).catch(() => {
          commit('RESET_USER')
          removeToken()
        })
      })
    },
    // 前端 登出
    FedLogOut({commit}) {
      return new Promise(resolve => {
        commit('RESET_USER')
        removeToken()
        resolve()
      })
    }
  }
}
export default user
