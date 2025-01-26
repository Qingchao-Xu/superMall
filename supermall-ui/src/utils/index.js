// 格式化金额
export const formatPrice = (price) => {
  return `¥${price.toFixed(2)}`
}

// 格式化日期
export const formatDate = (date) => {
  return new Date(date).toLocaleString()
}

// 本地存储封装
export const storage = {
  set(key, value) {
    localStorage.setItem(key, JSON.stringify(value))
  },
  
  get(key) {
    const value = localStorage.getItem(key)
    try {
      return JSON.parse(value)
    } catch {
      return value
    }
  },
  
  remove(key) {
    localStorage.removeItem(key)
  },
  
  clear() {
    localStorage.clear()
  }
}

// 防抖函数
export const debounce = (fn, delay) => {
  let timer = null
  return function (...args) {
    if (timer) clearTimeout(timer)
    timer = setTimeout(() => {
      fn.apply(this, args)
    }, delay)
  }
}

// 节流函数
export const throttle = (fn, delay) => {
  let timer = null
  return function (...args) {
    if (!timer) {
      timer = setTimeout(() => {
        fn.apply(this, args)
        timer = null
      }, delay)
    }
  }
} 