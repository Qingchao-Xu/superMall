import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import './assets/main.css'

// 创建应用实例
const app = createApp(App)

// 注册全局组件
import Loading from './components/Loading.vue'
app.component('Loading', Loading)

// 路由守卫
router.beforeEach((to, from, next) => {
  const isAuthenticated = store.state.user !== null
  
  // 需要登录的路由
  const authRequired = ['cart', 'order', 'checkout'].includes(to.name)
  
  if (authRequired && !isAuthenticated) {
    next('/login')
  } else {
    next()
  }
})

// 使用插件
app.use(router)
app.use(store)
app.use(ElementPlus)

// 挂载应用
app.mount('#app') 