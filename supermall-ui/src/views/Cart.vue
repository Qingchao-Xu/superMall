<template>
  <div class="cart-container">
    <h2>购物车</h2>
    
    <div v-if="loading" class="loading">
      加载中...
    </div>
    
    <div v-else-if="!cartItems?.length" class="empty-cart">
      购物车是空的，去<router-link to="/">购物</router-link>
    </div>
    
    <div v-else class="cart-content">
      <div class="cart-items">
        <div v-for="item in cartItems" :key="item.id" class="cart-item">
          <img :src="item.product.picture" :alt="item.product.name">
          <div class="item-info">
            <h3>{{ item.product.name }}</h3>
            <p class="price">¥{{ item.product.price }}</p>
          </div>
          <div class="quantity-control">
            <button @click="updateQuantity(item, -1)" :disabled="item.quantity <= 1">-</button>
            <span>{{ item.quantity }}</span>
            <button @click="updateQuantity(item, 1)">+</button>
          </div>
          <button class="remove-btn" @click="removeItem(item)">删除</button>
        </div>
      </div>
      
      <div class="cart-summary">
        <div class="total">
          总计: ¥{{ total }}
        </div>
        <button class="checkout-btn" @click="checkout">结算</button>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, computed, onMounted } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { cartApi } from '../api'

export default {
  name: 'Cart',
  setup() {
    const store = useStore()
    const router = useRouter()
    const loading = ref(false)
    const cartItems = ref([])

    const total = computed(() => {
      return cartItems.value.reduce((sum, item) => {
        return sum + item.product.price * item.quantity
      }, 0)
    })

    const loadCart = async () => {
      loading.value = true
      try {
        const userId = store.state.user?.userId
        if (!userId) {
          router.push('/login')
          return
        }
        const res = await cartApi.getCartInfo(userId)
        cartItems.value = res.data
      } catch (error) {
        console.error('加载购物车失败:', error)
      } finally {
        loading.value = false
      }
    }

    const updateQuantity = async (item, delta) => {
      const newQuantity = item.quantity + delta
      if (newQuantity < 1) return
      
      try {
        await cartApi.updateCart({
          userId: store.state.user?.userId,
          productId: item.product.id,
          quantity: newQuantity
        })
        item.quantity = newQuantity
      } catch (error) {
        console.error('更新数量失败:', error)
      }
    }

    const removeItem = async (item) => {
      try {
        await cartApi.removeFromCart({
          userId: store.state.user?.userId,
          productId: item.product.id
        })
        cartItems.value = cartItems.value.filter(i => i.id !== item.id)
      } catch (error) {
        console.error('删除商品失败:', error)
      }
    }

    const checkout = () => {
      router.push('/checkout')
    }

    onMounted(loadCart)

    return {
      loading,
      cartItems,
      total,
      updateQuantity,
      removeItem,
      checkout
    }
  }
}
</script>

<style scoped>
.cart-container {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.cart-item {
  display: flex;
  align-items: center;
  padding: 20px;
  border-bottom: 1px solid #eee;
}

.cart-item img {
  width: 100px;
  height: 100px;
  object-fit: cover;
  margin-right: 20px;
}

.item-info {
  flex: 1;
}

.quantity-control {
  display: flex;
  align-items: center;
  margin: 0 20px;
}

.quantity-control button {
  width: 30px;
  height: 30px;
  margin: 0 10px;
}

.remove-btn {
  padding: 5px 10px;
  background: #ff4444;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.cart-summary {
  margin-top: 20px;
  text-align: right;
  padding: 20px;
  background: #f5f5f5;
  border-radius: 4px;
}

.total {
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 10px;
}

.checkout-btn {
  padding: 10px 20px;
  background: #409eff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.empty-cart {
  text-align: center;
  padding: 40px;
}

.loading {
  text-align: center;
  padding: 40px;
}
</style> 