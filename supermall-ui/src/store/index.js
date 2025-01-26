import { createStore } from 'vuex'
import { userApi, cartApi } from '../api'

export default createStore({
  state: {
    user: null,
    cart: null,
    token: localStorage.getItem('token')
  },
  
  mutations: {
    SET_USER(state, user) {
      state.user = user
    },
    SET_CART(state, cart) {
      state.cart = cart
    },
    SET_TOKEN(state, token) {
      state.token = token
      localStorage.setItem('token', token)
    }
  },
  
  actions: {
    async login({ commit }, credentials) {
      const res = await userApi.login(credentials)
      commit('SET_USER', res.data)
      return res
    },
    
    async getCartInfo({ commit }, userId) {
      const res = await cartApi.getCartInfo(userId)
      commit('SET_CART', res.data)
      return res
    }
  }
})