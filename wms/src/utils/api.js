import axios from 'axios'
import {Message, MessageBox} from 'element-ui'
import {getToken} from '@/utils/auth'
import store from '../store'
// 创建axios实例
const service = axios.create({
  baseURL: "/api", // api的base_url
  timeout: 15000                  // 请求超时时间2
})
// request拦截器
service.interceptors.request.use(config => {
  return config
}, error => {
  // Do something with request error
  console.error(error) // for debug
  Promise.reject(error)
})
// respone拦截器
service.interceptors.response.use(
  response => {
    const res = response.data; 
    if (res.resp_code !== 0) {
      Message({
        message:res.resp_msg,
        type:'error',
        duration: 3 * 1000
      })
      // 未登录
      if (res.resp_code ==202) {
        MessageBox.confirm('与服务器断开连接，请重新重新登录', '确定登出', {
          confirmButtonText: '重新登录',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => { 
          store.dispatch('FedLogOut').then(() => {
            location.reload()// 为了重新实例化vue-router对象 避免bug
          })
        })
      }
      if (res.resp_code ==203) {
        MessageBox.confirm('登录已过期，请重新重新登录', '确定登出', {
          confirmButtonText: '重新登录',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => { 
          // store.dispatch('FedLogOut').then(() => {
          //   location.reload()// 为了重新实例化vue-router对象 避免bug
          // })
          // 此时应该清除缓存，cookie等，跳转到登录
          store.dispatch('FedLogOut').then(() => {
            location.reload()// 为了重新实例化vue-router对象 避免bug
          })
        })
      }
      if (res.resp_code ==204) {
        Message({
          message:res.resp_msg,
          type:'error',
          duration: 3 * 1000
        })
      }
      return Promise.reject(res.resp_msg)
    }else{
      return response.data
    }
  },
  error => {
    console.error('err' + error)// for debug
    Message({
      message: error,
      type: 'error',
      duration: 3 * 1000
    })
    return Promise.reject(error)
  }
)
export default service

