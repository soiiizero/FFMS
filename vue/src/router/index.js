import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'login', //登录界面
      component: () => import('@/components/common/login')
    },
    {
      path: '/index', //管理员主页
      component: () => import('@/components/admin/index'),
      children: [
        {
          path: '/', //首页默认路由
          component: () => import('@/components/common/hello')
        },
        {
          path: '/memberManage', //家庭成员管理界面
          component: () => import('@/components/admin/MemberManage')
        },
        {
          path: '/addMember', //添加家庭成员
          component: () => import('@/components/admin/addMember')
        },
        {
          path: '/addPay', //添加支出
          component: () => import('@/components/admin/addPay')
        },
        {
          path: '/pay', //支出管理
          component: () => import('@/components/admin/pay')
        },
        {
          path: '/addIncome', //添加收入
          component: () => import('@/components/admin/addIncome')
        },
        {
          path: '/income', //收入管理
          component: () => import('@/components/admin/income')
        },
        {
          path: '/selpayincome', //成员统计
          component: () => import('@/components/charts/selpayincome')
        }
      ]
    }
  ]
})
