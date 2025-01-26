<template>
  <div class="checkout-container">
    <h2>确认订单</h2>
    
    <div v-if="loading" class="loading">
      加载中...
    </div>
    
    <div v-else class="checkout-content">
      <div class="order-items">
        <div v-for="item in orderItems" :key="item.id" class="order-item">
          <img :src="item.product.picture" :alt="item.product.name">
          <div class="item-info">
            <h3>{{ item.product.name }}</h3>
            <p class="price">¥{{ item.product.price }}</p>
            <p class="quantity">数量: {{ item.quantity }}</p>
          </div>
          <div class="item-total">
            小计: ¥{{ item.product.price * item.quantity }}
          </div>
        </div>
      </div>
      
      <div class="address-section">
        <h3>收货地址</h3>
        <div class="address-form">
          <div class="form-item">
            <input type="text" v-model="address.name" placeholder="收货人姓名">
          </div>
          <div class="form-item">
            <input type="text" v-model="address.phone" placeholder="手机号码">
          </div>
          <div class="form-item">
            <textarea v-model="address.detail" placeholder="详细地址"></textarea>
          </div>
        </div>
      </div>
      
      <div class="order-summary">
        <div class="total">
          总计: ¥{{ total }}
        </div>
        <button class="submit-btn" @click="submitOrder" :disabled="submitting">
          {{ submitting ? '提交中...' : '提交订单' }}
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, computed, onMounted } from 'vue'
import { useStore } from 'vuex'
import { useRouter, useRoute } from 'vue-router'
import { orderApi } from '../api'

export default {
  name: 'Checkout',
  setup() {
    const store = useStore()
    const router = useRouter()
    const route = useRoute()
    
    const loading = ref(false)
    const submitting = ref(false)
    const orderItems = ref([])
    const address = ref({
      name: '',
      phone: '',
      detail: ''
    })

    const total = computed(() => {
      return orderItems.value.reduce((sum, item) => {
        return sum + item.product.price * item.quantity
      }, 0)
    })

    const loadOrderItems = async () => {
      loading.value = true
      try {
        // 如果是从购物车来的，获取选中的商品
        if (route.query.fromCart) {
          const cartItems = await store.dispatch('getSelectedCartItems')
          orderItems.value = cartItems
        } 
        // 如果是立即购买，获取单个商品信息
        else if (route.query.productId) {
          const product = await store.dispatch('getProduct', route.query.productId)
          orderItems.value = [{
            product,
            quantity: Number(route.query.quantity) || 1
          }]
        }
      } catch (error) {
        console.error('加载订单商品失败:', error)
      } finally {
        loading.value = false
      }
    }

    const submitOrder = async () => {
      if (!address.value.name || !address.value.phone || !address.value.detail) {
        alert('请填写完整的收货信息')
        return
      }

      submitting.value = true
      try {
        const orderData = {
          userId: store.state.user.userId,
          items: orderItems.value.map(item => ({
            productId: item.product.id,
            quantity: item.quantity
          })),
          address: address.value
        }
        
        const res = await orderApi.createOrder(orderData)
        alert('订单提交成功')
        router.push(`/order/${res.data.orderId}`)
      } catch (error) {
        alert('订单提交失败：' + error.message)
      } finally {
        submitting.value = false
      }
    }

    onMounted(loadOrderItems)

    return {
      loading,
      submitting,
      orderItems,
      address,
      total,
      submitOrder
    }
  }
}
</script>

<style scoped>
.checkout-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.order-item {
  display: flex;
  align-items: center;
  padding: 20px;
  border-bottom: 1px solid #eee;
}

.order-item img {
  width: 100px;
  height: 100px;
  object-fit: cover;
  margin-right: 20px;
}

.item-info {
  flex: 1;
}

.item-total {
  font-weight: bold;
}

.address-section {
  margin: 30px 0;
  padding: 20px;
  background: #f5f5f5;
  border-radius: 8px;
}

.form-item {
  margin: 15px 0;
}

input, textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

textarea {
  height: 100px;
  resize: vertical;
}

.order-summary {
  text-align: right;
  padding: 20px;
  background: #f5f5f5;
  border-radius: 8px;
}

.total {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 20px;
}

.submit-btn {
  padding: 12px 30px;
  background: #409eff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.submit-btn:disabled {
  background: #a0cfff;
  cursor: not-allowed;
}
</style> 