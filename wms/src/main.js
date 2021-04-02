import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import {hasPermission} from "./utils/hasPermission";
import {default as api} from './utils/api'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import '@/permission' // 权限

import permission from './directive/permission'
Vue.use(permission)


Vue.config.productionTip = false


Vue.use(ElementUI);

Vue.prototype.api = api
// 全局的常量
Vue.prototype.hasPerm = hasPermission
//生产环境时自动设置为 false 以阻止 vue 在启动时生成生产提示。
Vue.config.productionTip = (process.env.NODE_ENV != 'production')

import MinXin from './components/common/mixins'
Vue.mixin(MinXin);
import {  handleTree ,resetForm} from "@/utils/wms";
Vue.prototype.handleTree = handleTree
Vue.prototype.resetForm = resetForm

 

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
