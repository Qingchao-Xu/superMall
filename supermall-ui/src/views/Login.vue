<template>
  <div class="login-container">
    <div class="login-box">
      <h2>用户登录</h2>
      <div class="form-item">
        <input type="text" v-model="username" placeholder="用户名">
      </div>
      <div class="form-item">
        <input type="password" v-model="password" placeholder="密码">
      </div>
      <div class="form-item">
        <button @click="handleLogin" :disabled="loading">
          {{ loading ? '登录中...' : '登录' }}
        </button>
      </div>
      <div class="links">
        <router-link to="/register">还没有账号？立即注册</router-link>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'Login',
  setup() {
    const store = useStore()
    const router = useRouter()
    const username = ref('')
    const password = ref('')
    const loading = ref(false)

    const handleLogin = async () => {
      if (!username.value || !password.value) {
        alert('请输入用户名和密码')
        return
      }

      loading.value = true
      try {
        console.log('开始登录请求，用户名:', username.value)
        console.log('API地址:', import.meta.env.VITE_APP_API_URL)
        await store.dispatch('login', {
          username: username.value,
          password: password.value
        })
        router.push('/')
      } catch (error) {
        console.error('登录错误详情：', error)
        console.error('错误配置：', error.config)
        
        if (error.message.includes('CORS')) {
          alert('跨域请求被拒绝，请确保：\n1. 后端已配置允许跨域\n2. 允许的域名包含：' + window.location.origin)
        } else if (error.message === 'Network Error') {
          alert('网络连接失败，请检查：\n1. 服务器是否正常运行（端口：9090）\n2. 网络连接是否正常')
        } else {
          alert('登录失败：' + error.message)
        }
      } finally {
        loading.value = false
      }
    }

    return {
      username,
      password,
      loading,
      handleLogin
    }
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f5f5f5;
}

.login-box {
  width: 350px;
  padding: 20px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.form-item {
  margin: 20px 0;
}

input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

button {
  width: 100%;
  padding: 10px;
  background: #409eff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:disabled {
  background: #a0cfff;
  cursor: not-allowed;
}

.links {
  text-align: center;
  font-size: 14px;
}

a {
  color: #409eff;
  text-decoration: none;
}
</style> 