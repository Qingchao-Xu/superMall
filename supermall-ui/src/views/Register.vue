<template>
  <div class="register-container">
    <div class="register-box">
      <h2>用户注册</h2>
      <div class="form-item">
        <input type="text" v-model="username" placeholder="用户名">
      </div>
      <div class="form-item">
        <input type="password" v-model="password" placeholder="密码">
      </div>
      <div class="form-item">
        <input type="password" v-model="confirmPassword" placeholder="确认密码">
      </div>
      <div class="form-item">
        <button @click="handleRegister" :disabled="loading">
          {{ loading ? '注册中...' : '注册' }}
        </button>
      </div>
      <div class="links">
        <router-link to="/login">已有账号？立即登录</router-link>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { userApi } from '../api'

export default {
  name: 'Register',
  setup() {
    const router = useRouter()
    const username = ref('')
    const password = ref('')
    const confirmPassword = ref('')
    const loading = ref(false)

    const handleRegister = async () => {
      if (!username.value || !password.value || !confirmPassword.value) {
        alert('请填写完整信息')
        return
      }

      if (password.value !== confirmPassword.value) {
        alert('两次输入的密码不一致')
        return
      }

      loading.value = true
      try {
        await userApi.register({
          username: username.value,
          password: password.value
        })
        alert('注册成功')
        router.push('/login')
      } catch (error) {
        alert('注册失败：' + error.message)
      } finally {
        loading.value = false
      }
    }

    return {
      username,
      password,
      confirmPassword,
      loading,
      handleRegister
    }
  }
}
</script>

<style scoped>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f5f5f5;
}

.register-box {
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