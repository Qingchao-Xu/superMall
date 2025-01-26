<template>
  <div class="home">
    <div class="product-list">
      <div v-for="product in products" :key="product.id" class="product-card">
        <img :src="product.picture" :alt="product.name">
        <h3>{{ product.name }}</h3>
        <p>{{ product.description }}</p>
        <p class="price">¥{{ product.price }}</p>
        <button @click="addToCart(product)">加入购物车</button>
      </div>
    </div>
    
    <div class="pagination">
      <button :disabled="currentPage === 1" @click="changePage(currentPage - 1)">上一页</button>
      <span>{{ currentPage }}</span>
      <button @click="changePage(currentPage + 1)">下一页</button>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import { productApi, cartApi } from '../api'
import { useStore } from 'vuex'

export default {
  name: 'Home',
  setup() {
    const store = useStore()
    const products = ref([])
    const currentPage = ref(1)
    const pageSize = 10
    
    const loadProducts = async (page) => {
      try {
        const res = await productApi.getProductList(page, pageSize)
        products.value = res.data
      } catch (error) {
        console.error('加载商品失败:', error)
      }
    }
    
    const addToCart = async (product) => {
      try {
        await cartApi.createCart({
          userId: store.state.user?.userId,
          productId: product.id,
          quantity: 1
        })
        alert('添加成功')
      } catch (error) {
        console.error('添加购物车失败:', error)
      }
    }
    
    const changePage = (page) => {
      currentPage.value = page
      loadProducts(page)
    }
    
    onMounted(() => {
      loadProducts(currentPage.value)
    })
    
    return {
      products,
      currentPage,
      addToCart,
      changePage
    }
  }
}
</script>

<style scoped>
.home {
  padding: 20px;
}

.product-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
  gap: 20px;
}

.product-card {
  border: 1px solid #ddd;
  padding: 10px;
  text-align: center;
}

.product-card img {
  width: 100%;
  height: 200px;
  object-fit: cover;
}

.price {
  color: #f00;
  font-weight: bold;
}

.pagination {
  margin-top: 20px;
  text-align: center;
}

button {
  margin: 0 10px;
  padding: 5px 10px;
}
</style> 