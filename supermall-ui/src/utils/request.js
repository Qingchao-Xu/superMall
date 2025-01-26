import axios from 'axios'
import { handleError } from './error'
import store from '../store'

// 创建 axios 实例
const request = axios.create({
  baseURL: import.meta.env.VITE_APP_API_URL,
  timeout: 5000,
  headers: {
    'Content-Type': 'application/json'
  }
})

// 请求拦截器
request.interceptors.request.use(
  config => {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// 响应拦截器
request.interceptors.response.use(
  response => {
    const res = response.data
    
    // 这里可以根据后端的响应结构进行适当的处理
    if (res.code !== 0) {
      handleError(new Error(res.message || '未知错误'))
      return Promise.reject(new Error(res.message || '未知错误'))
    }
    
    return res.data
  },
  error => {
    return handleError(error)
  }
)

export default request