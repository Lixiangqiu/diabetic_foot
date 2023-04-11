import { createStore } from 'vuex'

export default createStore({
  state: {
    userPhoto:''
  },
  mutations: {
    update(state,user){
      state.userPhoto = user.photo
    }
  },
  actions: {
  },
  modules: {
  }
})
