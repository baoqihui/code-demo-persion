import {asyncRouterMap, constantRouterMap} from '@/router/index'
import Layout from '../../views/layout/Layout'
const _import = require('../../router/_import_' + process.env.NODE_ENV)//获取组件的方法
/**
 * 判断用户是否拥有此菜单
 * @param menus
 * @param route
 */
function hasPermission(menus, route) {
  if (route.menu) {
    /*
    * 如果这个路由有menu属性,就需要判断用户是否拥有此menu权限
    */
    return menus.indexOf(route.menu) > -1;
  } else {
    return true
  }
}

/**
 * 递归过滤异步路由表，返回符合用户菜单权限的路由表 
 * @param menus
 */
function filterAsyncRouter(asyncRouterMap) {
  return asyncRouterMap.filter(route => {
    route.name = route.path 
    route.meta = {
      title : route.perName,
      requiresAuth: true
    }
    if (route.component) { 
      if (route.component === 'Layout') {
        route.component = Layout
        route.path = '/' + route.path
      } else {
        route.component =  _import(route.component)
      }
    }
    if (route.children != null && route.children && route.children.length) {
      route.children = filterAsyncRouter(route.children)
    }
    return true

  })
} 
const permission = {
  state: {
    routers: constantRouterMap, //本用户所有的路由,包括了固定的路由和下面的addRouters
    addRouters: [] //本用户的角色赋予的新增的动态路由
  },
  mutations: {
    SET_ROUTERS: (state, routers) => {
      state.addRouters = routers //根据权限得到的动态路由
      state.routers = constantRouterMap.concat(routers) //将固定路由和新增路由进行合并, 成为本用户最终的全部路由信息
    }
  },
  actions: {
    GenerateRoutes({commit}, userPermission) { 
      //生成路由
      return new Promise(resolve => {
        //roles是后台传过来的角色数组,比如['管理员','文章']
        // const role = userPermission;
        const menus = userPermission.userAllPermission;//用户的权限
        //声明 该角色可用的路由
        let accessedRouters 
        accessedRouters = filterAsyncRouter(menus) 
        //执行设置路由的方法
        commit('SET_ROUTERS', accessedRouters) //accessedRouters是过滤到的路由
        resolve()
      })
    }
  }
}
 
export default permission
