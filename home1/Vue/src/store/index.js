import { createStore } from 'vuex'

export default createStore({
  state: {
    userPhoto:''
  },
  mutations: {
    update(state,user){
      state.userPhoto = user.photo
      console.log(state.userPhoto)
    }
  },
  actions: {
  },
  modules: {
  }
})
