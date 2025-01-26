import axios from 'axios'

// 创建axios实例
const request = axios.create({
  baseURL: 'http://localhost:9090',  // 替换为实际的API地址
  timeout: 5000
})

// 请求拦截器
request.interceptors.request.use(
  config => {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers['Authorization'] = token
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// API接口
export const userApi = {
  login: (data) => request.post('/user/login', data),
  register: (data) => request.post('/user/register', data),
  getUserInfo: (userId) => request.get(`/user/info?userId=${userId}`)
}

export const productApi = {
  getProduct: (id) => request.get(`/product/info?id=${id}`),
  getProductList: (page, pageSize, categoryName) => 
    request.get(`/product/list?page=${page}&pageSize=${pageSize}${categoryName ? `&categoryName=${categoryName}` : ''}`)
}

export const cartApi = {
  createCart: (data) => request.post('/cart/create', data),
  clearCart: (userId) => request.get(`/cart/clear?userId=${userId}`),
  getCartInfo: (userId) => request.get(`/cart/info?userId=${userId}`)
}

export const orderApi = {
  createOrder: (data) => request.post('/order/create', data),
  getOrderList: (userId) => request.get(`/order/list?userId=${userId}`),
  checkout: (data) => request.post('/order/checkout', data)
} 