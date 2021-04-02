import Vue from 'vue'
import Router from 'vue-router'
import VueRouter from 'vue-router' 
import Layout from '../views/layout/Layout'

Vue.use(VueRouter)


//静态
export const constantRouterMap = [
  {
    path: '/login',
    meta: {
      title: 'login',
    },
    component: () => import('@/views/Login.vue'), 
    hidden: true
  }, 
  {
    path: '/',
    redirect: '/welcome',
    component: Layout, 
    children:[
      {
        path: 'welcome',
        name: 'welcome',
        component: () => import('@/views/welcome.vue'),
        meta: {
          title: '首页',
          requiresAuth: true
        },
      },  
    ]
  },   
]
export const asyncRouterMap=[
  {
    path: '/404',
    name: '404',
    component: () => import('../views/404.vue')
  },
]


export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({y: 0}),
  routes: constantRouterMap
}) 
