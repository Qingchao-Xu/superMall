<template>
  <header class="header">
    <div class="header-content">
      <router-link to="/" class="logo">
        商城
      </router-link>
      
      <nav class="nav-links">
        <router-link to="/">首页</router-link>
        <router-link to="/cart">
          购物车
          <span v-if="cartCount" class="cart-badge">{{ cartCount }}</span>
        </router-link>
        <router-link to="/order">我的订单</router-link>
      </nav>
      
      <div class="user-section">
        <template v-if="user">
          <span class="username">{{ user.username }}</span>
          <a href="#" @click.prevent="handleLogout">退出</a>
        </template>
        <template v-else>
          <router-link to="/login">登录</router-link>
          <router-link to="/register">注册</router-link>
        </template>
      </div>
    </div>
  </header>
</template>

<script>
import { computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'Header',
  setup() {
    const store = useStore()
    const router = useRouter()

    const user = computed(() => store.state.user)
    const cartCount = computed(() => store.state.cart?.items?.length || 0)

    const handleLogout = async () => {
      try {
        await store.dispatch('logout')
        router.push('/login')
      } catch (error) {
        console.error('退出失败:', error)
      }
    }

    return {
      user,
      cartCount,
      handleLogout
    }
  }
}
</script>

<style scoped>
.header {
  background: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  position: sticky;
  top: 0;
  z-index: 100;
}

.header-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 15px 20px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.logo {
  font-size: 24px;
  font-weight: bold;
  color: #409eff;
  text-decoration: none;
}

.nav-links {
  display: flex;
  gap: 20px;
}

.nav-links a {
  color: #333;
  text-decoration: none;
  position: relative;
}

.cart-badge {
  position: absolute;
  top: -8px;
  right: -12px;
  background: #f56c6c;
  color: white;
  border-radius: 10px;
  padding: 2px 6px;
  font-size: 12px;
}

.user-section {
  display: flex;
  gap: 15px;
  align-items: center;
}

.user-section a {
  color: #666;
  text-decoration: none;
}

.username {
  color: #409eff;
}
</style>