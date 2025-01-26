<template>
  <div class="product-container">
    <div v-if="loading" class="loading">
      加载中...
    </div>
    
    <div v-else-if="error" class="error">
      {{ error }}
    </div>
    
    <div v-else class="product-detail">
      <div class="product-image">
        <img :src="product.picture" :alt="product.name">
      </div>
      
      <div class="product-info">
        <h1>{{ product.name }}</h1>
        <div class="price">¥{{ product.price }}</div>
        <div class="description">{{ product.description }}</div>
        
        <div class="quantity-selector">
          <button @click="quantity > 1 && quantity--">-</button>
          <input type="number" v-model.number="quantity" min="1">
          <button @click="quantity++">+</button>
        </div>
        
        <div class="actions">
          <button class="add-to-cart" @click="addToCart">
            加入购物车
          </button>
          <button class="buy-now" @click="buyNow">
            立即购买
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useStore } from 'vuex'
import { productApi, cartApi } from '../api'

export default {
  name: 'Product',
  setup() {
    const route = useRoute()
    const router = useRouter()
    const store = useStore()
    
    const product = ref(null)
    const loading = ref(true)
    const error = ref(null)
    const quantity = ref(1)

    const loadProduct = async () => {
      loading.value = true
      error.value = null
      try {
        const res = await productApi.getProduct(route.params.id)
        product.value = res.data
      } catch (err) {
        error.value = '商品加载失败'
        console.error(err)
      } finally {
        loading.value = false
      }
    }

    const addToCart = async () => {
      if (!store.state.user) {
        router.push('/login')
        return
      }

      try {
        await cartApi.createCart({
          userId: store.state.user.userId,
          productId: product.value.id,
          quantity: quantity.value
        })
        alert('添加成功')
      } catch (error) {
        alert('添加失败：' + error.message)
      }
    }

    const buyNow = () => {
      if (!store.state.user) {
        router.push('/login')
        return
      }
      
      router.push({
        path: '/checkout',
        query: {
          productId: product.value.id,
          quantity: quantity.value
        }
      })
    }

    onMounted(loadProduct)

    return {
      product,
      loading,
      error,
      quantity,
      addToCart,
      buyNow
    }
  }
}
</script>

<style scoped>
.product-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.product-detail {
  display: flex;
  gap: 40px;
}

.product-image {
  flex: 1;
}

.product-image img {
  width: 100%;
  height: auto;
  border-radius: 8px;
}

.product-info {
  flex: 1;
}

.price {
  font-size: 24px;
  color: #f00;
  margin: 20px 0;
}

.description {
  color: #666;
  margin: 20px 0;
  line-height: 1.6;
}

.quantity-selector {
  display: flex;
  align-items: center;
  margin: 20px 0;
}

.quantity-selector button {
  width: 36px;
  height: 36px;
  border: 1px solid #ddd;
  background: #fff;
  cursor: pointer;
}

.quantity-selector input {
  width: 60px;
  height: 36px;
  text-align: center;
  border: 1px solid #ddd;
  margin: 0 10px;
}

.actions {
  display: flex;
  gap: 20px;
}

.actions button {
  flex: 1;
  padding: 12px 0;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.add-to-cart {
  background: #ff9900;
  color: white;
}

.buy-now {
  background: #f00;
  color: white;
}

.loading, .error {
  text-align: center;
  padding: 40px;
}
</style> 