<template>
  <footer class="footer">
    <div class="footer-content">
      <div class="footer-section">
        <h3>关于我们</h3>
        <ul>
          <li><a href="#">公司简介</a></li>
          <li><a href="#">联系我们</a></li>
          <li><a href="#">加入我们</a></li>
        </ul>
      </div>
      
      <div class="footer-section">
        <h3>帮助中心</h3>
        <ul>
          <li><a href="#">购物指南</a></li>
          <li><a href="#">支付方式</a></li>
          <li><a href="#">配送方式</a></li>
        </ul>
      </div>
      
      <div class="footer-section">
        <h3>售后服务</h3>
        <ul>
          <li><a href="#">退货政策</a></li>
          <li><a href="#">退货流程</a></li>
          <li><a href="#">常见问题</a></li>
        </ul>
      </div>
      
      <div class="footer-section">
        <h3>关注我们</h3>
        <div class="social-links">
          <a href="#" class="social-link">微信</a>
          <a href="#" class="social-link">微博</a>
          <a href="#" class="social-link">抖音</a>
        </div>
      </div>
    </div>
    
    <div class="footer-bottom">
      <p>© 2024 商城. All rights reserved.</p>
    </div>
  </footer>
</template>

<script>
export default {
  name: 'Footer'
}
</script>

<style scoped>
.footer {
  background: #f5f5f5;
  padding: 40px 0 20px;
  margin-top: 60px;
}

.footer-content {
  max-width: 1200px;
  margin: 0 auto;
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 30px;
  padding: 0 20px;
}

.footer-section h3 {
  margin-bottom: 20px;
  color: #333;
}

.footer-section ul {
  list-style: none;
  padding: 0;
}

.footer-section ul li {
  margin-bottom: 10px;
}

.footer-section a {
  color: #666;
  text-decoration: none;
}

.social-links {
  display: flex;
  gap: 15px;
}

.social-link {
  color: #666;
  text-decoration: none;
}

.footer-bottom {
  text-align: center;
  margin-top: 40px;
  padding-top: 20px;
  border-top: 1px solid #ddd;
  color: #999;
}
</style> 