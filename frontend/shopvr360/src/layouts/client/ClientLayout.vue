<script setup>
import { ref, onMounted } from 'vue'
import Header from './Header.vue'
import Footer from './Footer.vue'

const isLoading = ref(true)

onMounted(() => {
  // Giả lập thời gian loading (có thể thay bằng API call thực tế)
  setTimeout(() => {
    isLoading.value = false
  }, 2000)
})
</script>

<template>
  <!-- Loading Screen -->
  <div v-if="isLoading" class="loading-screen">
    <div class="loading-container">
      <div class="loading-spinner">
        <div class="spinner-circle"></div>
        <div class="spinner-circle"></div>
        <div class="spinner-circle"></div>
        <div class="spinner-circle"></div>
      </div>
      <div class="loading-text">
        <h2>Đang tải trang...</h2>
        <p>Vui lòng chờ trong giây lát</p>
      </div>
      <div class="loading-progress">
        <div class="progress-bar">
          <div class="progress-fill"></div>
        </div>
      </div>
    </div>
  </div>

  <!-- Main Content -->
  <div v-else class="main-content">
    <Header />
    
    <main>
      <RouterView />
    </main>

    <Footer />
  </div>
</template>

<style scoped>
/* Loading Screen Styles */
.loading-screen {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 9999;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.loading-container {
  text-align: center;
  color: white;
  max-width: 400px;
  padding: 40px;
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(10px);
  border-radius: 20px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.3);
  border: 1px solid rgba(255, 255, 255, 0.2);
}

.loading-spinner {
  display: flex;
  justify-content: center;
  gap: 8px;
  margin-bottom: 30px;
}

.spinner-circle {
  width: 16px;
  height: 16px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.8);
  animation: bounce 1.4s infinite ease-in-out both;
}

.spinner-circle:nth-child(1) { animation-delay: -0.32s; }
.spinner-circle:nth-child(2) { animation-delay: -0.16s; }
.spinner-circle:nth-child(3) { animation-delay: -0.08s; }
.spinner-circle:nth-child(4) { animation-delay: 0s; }

@keyframes bounce {
  0%, 80%, 100% {
    transform: scale(0.8);
    opacity: 0.5;
  }
  40% {
    transform: scale(1);
    opacity: 1;
  }
}

.loading-text h2 {
  margin: 0 0 10px 0;
  font-size: 24px;
  font-weight: 600;
  color: white;
}

.loading-text p {
  margin: 0;
  font-size: 14px;
  opacity: 0.9;
  color: rgba(255, 255, 255, 0.9);
}

.loading-progress {
  margin-top: 30px;
}

.progress-bar {
  width: 100%;
  height: 6px;
  background: rgba(255, 255, 255, 0.2);
  border-radius: 3px;
  overflow: hidden;
}

.progress-fill {
  height: 100%;
  background: linear-gradient(90deg, #ffd89b, #19547b);
  border-radius: 3px;
  animation: progress 2s ease-in-out infinite;
}

@keyframes progress {
  0% {
    width: 0%;
    transform: translateX(-100%);
  }
  50% {
    width: 60%;
  }
  100% {
    width: 100%;
    transform: translateX(0%);
  }
}

/* Main Content */
.main-content {
  opacity: 0;
  animation: fadeIn 0.5s ease-in-out forwards;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* Responsive */
@media (max-width: 768px) {
  .loading-container {
    margin: 20px;
    padding: 30px 20px;
  }
  
  .loading-text h2 {
    font-size: 20px;
  }
}
</style>