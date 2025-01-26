<template>
  <div class="order-container">
    <h2>我的订单</h2>
    
    <div v-if="loading" class="loading">
      加载中...
    </div>
    
    <div v-else-if="!orders.length" class="empty-order">
      暂无订单，去<router-link to="/">购物</router-link>
    </div>
    
    <div v-else class="order-list">
      <div v-for="order in orders" :key="order.id" class="order-card">
        <div class="order-header">
          <span class="order-id">订单号：{{ order.id }}</span>
          <span class="order-status">{{ getStatusText(order.status) }}</span>
        </div>
        
        <div class="order-items">
          <div v-for="item in order.items" :key="item.id" class="order-item">
            <img :src="item.product.picture" :alt="item.product.name">
            <div class="item-info">
              <h3>{{ item.product.name }}</h3>
              <p class="price">¥{{ item.product.price }}</p>
              <p class="quantity">x{{ item.quantity }}</p>
            </div>
          </div>
        </div>
        
        <div class="order-footer">
          <div class="order-total">
            总计: ¥{{ calculateTotal(order) }}
          </div>
          <div class="order-actions">
            <button v-if="order.status === 'PENDING'" @click="cancelOrder(order.id)">
              取消订单
            </button>
            <button v-if="order.status === 'PENDING'" @click="payOrder(order.id)">
              去支付
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import { useStore } from 'vuex'
import { orderApi } from '../api'

export default {
  name: 'Order',
  setup() {
    const store = useStore()
    const loading = ref(false)
    const orders = ref([])

    const getStatusText = (status) => {
      const statusMap = {
        'PENDING': '待支付',
        'PAID': '已支付',
        'SHIPPED': '已发货',
        'COMPLETED': '已完成',
        'CANCELLED': '已取消'
      }
      return statusMap[status] || status
    }

    const calculateTotal = (order) => {
      return order.items.reduce((sum, item) => {
        return sum + item.product.price * item.quantity
      }, 0)
    }

    const loadOrders = async () => {
      loading.value = true
      try {
        const res = await orderApi.getOrderList(store.state.user.userId)
        orders.value = res.data
      } catch (error) {
        console.error('加载订单失败:', error)
      } finally {
        loading.value = false
      }
    }

    const cancelOrder = async (orderId) => {
      try {
        await orderApi.cancelOrder(orderId)
        await loadOrders()
      } catch (error) {
        alert('取消订单失败：' + error.message)
      }
    }

    const payOrder = async (orderId) => {
      try {
        await orderApi.payOrder(orderId)
        await loadOrders()
      } catch (error) {
        alert('支付失败：' + error.message)
      }
    }

    onMounted(loadOrders)

    return {
      loading,
      orders,
      getStatusText,
      calculateTotal,
      cancelOrder,
      payOrder
    }
  }
}
</script>

<style scoped>
.order-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.order-card {
  margin-bottom: 20px;
  border: 1px solid #eee;
  border-radius: 8px;
}

.order-header {
  padding: 15px;
  background: #f5f5f5;
  border-radius: 8px 8px 0 0;
  display: flex;
  justify-content: space-between;
}

.order-status {
  color: #409eff;
}

.order-item {
  display: flex;
  padding: 15px;
  border-bottom: 1px solid #eee;
}

.order-item img {
  width: 80px;
  height: 80px;
  object-fit: cover;
  margin-right: 15px;
}

.item-info {
  flex: 1;
}

.order-footer {
  padding: 15px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.order-total {
  font-size: 18px;
  font-weight: bold;
}

.order-actions button {
  margin-left: 10px;
  padding: 8px 15px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.order-actions button:first-child {
  background: #f56c6c;
  color: white;
}

.order-actions button:last-child {
  background: #409eff;
  color: white;
}

.empty-order {
  text-align: center;
  padding: 40px;
}
</style> 