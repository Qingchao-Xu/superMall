import { ElMessage } from 'element-plus'
import store from '../store'
import router from '../router'

// 错误码映射
const ERROR_CODE_MAP = {
  400: '请求参数错误',
  401: '未授权，请登录',
  403: '拒绝访问',
  404: '请求地址出错',
  408: '请求超时',
  500: '服务器内部错误',
  501: '服务未实现',
  502: '网关错误',
  503: '服务不可用',
  504: '网关超时',
  505: 'HTTP版本不受支持'
}

// 处理错误响应
export const handleError = (error) => {
  if (error.response) {
    const status = error.response.status
    const message = ERROR_CODE_MAP[status] || error.response.data?.message || '未知错误'
    
    // 401 未授权，清除用户信息并跳转登录页
    if (status === 401) {
      store.commit('SET_USER', null)
      store.commit('SET_TOKEN', null)
      router.push('/login')
    }
    
    // 使用 Element Plus 的消息提示
    ElMessage.error(message)
    
    return Promise.reject(new Error(message))
  }
  
  ElMessage.error('网络错误，请检查您的网络连接')
  return Promise.reject(error)
}