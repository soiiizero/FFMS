import VUE from 'vue'
import VUEX from 'vuex'

VUE.use(VUEX)

const state = {
  flag: false,
  userInfo: null,
  menu: [
    {
      index: '1',
      title: '支出管理',
      icon: 'icon-performance',
      content:[{item1:'支出详情',path:'/pay'},{item2:'添加支出',path:'/addPay'}],
    },
    {
      index: '2',
      title: '收入管理',
      icon: 'icon-performance',
      content:[{item1:'收入详情',path:'/income'},{item2: '添加收入',path: '/addIncome'}],
    },
  ],
}
const mutations = {
  toggle(state) {
    state.flag = !state.flag
  },
  changeUserInfo(state,info) {
    state.userInfo = info
  }
}
const getters = {

}
const actions = {
  getUserInfo(context,info) {
    context.commit('changeUserInfo',info)
  }
}
export default new VUEX.Store({
  state,
  mutations,
  getters,
  actions,
})
