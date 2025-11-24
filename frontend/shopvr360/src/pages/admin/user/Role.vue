<template>
  <div class="maintenance-page">
    <!-- Background Animation -->
    <div class="background-animation">
      <div class="circle circle-1"></div>
      <div class="circle circle-2"></div>
      <div class="circle circle-3"></div>
      <div class="circle circle-4"></div>
    </div>

    <!-- Main Content -->
    <div class="maintenance-container">
      <!-- Logo -->
      <div class="logo-section">
        <div class="logo">
          <i class="fas fa-tools"></i>
          <span class="logo-text">SYSTEM</span>
        </div>
      </div>

      <!-- Maintenance Illustration -->
      <div class="illustration">
        <div class="gear gear-1">
          <div class="gear-inner">
            <div class="bar"></div>
            <div class="bar"></div>
            <div class="bar"></div>
            <div class="bar"></div>
            <div class="bar"></div>
            <div class="bar"></div>
          </div>
        </div>
        <div class="gear gear-2">
          <div class="gear-inner">
            <div class="bar"></div>
            <div class="bar"></div>
            <div class="bar"></div>
            <div class="bar"></div>
            <div class="bar"></div>
            <div class="bar"></div>
          </div>
        </div>
        <div class="gear gear-3">
          <div class="gear-inner">
            <div class="bar"></div>
            <div class="bar"></div>
            <div class="bar"></div>
            <div class="bar"></div>
            <div class="bar"></div>
            <div class="bar"></div>
          </div>
        </div>
        <div class="maintenance-icon">
          <i class="fas fa-cogs"></i>
        </div>
      </div>

      <!-- Content -->
      <div class="content-section">
        <h1 class="main-title">Hệ Thống Đang Bảo Trì</h1>
        <p class="subtitle">Chúng tôi đang nâng cấp hệ thống để phục vụ bạn tốt hơn</p>

        <!-- Progress Section -->
        <div class="progress-section">
          <div class="progress-info">
            <span class="progress-label">Tiến độ bảo trì</span>
            <span class="progress-percentage">{{ progress }}%</span>
          </div>
          <div class="progress-bar">
            <div class="progress-fill" :style="{ width: progress + '%' }"></div>
          </div>
          <div class="progress-time">
            <span>Đã bảo trì: {{ elapsedTime }}</span>
            <span>Dự kiến hoàn thành: {{ estimatedCompletion }}</span>
          </div>
        </div>

        <!-- Countdown Timer -->
        <div class="countdown-section">
          <h3>Thời gian còn lại</h3>
          <div class="countdown-timer">
            <div class="time-unit">
              <span class="time-value">{{ countdown.hours }}</span>
              <span class="time-label">Giờ</span>
            </div>
            <div class="time-separator">:</div>
            <div class="time-unit">
              <span class="time-value">{{ countdown.minutes }}</span>
              <span class="time-label">Phút</span>
            </div>
            <div class="time-separator">:</div>
            <div class="time-unit">
              <span class="time-value">{{ countdown.seconds }}</span>
              <span class="time-label">Giây</span>
            </div>
          </div>
        </div>

        <!-- Maintenance Details -->
        <div class="details-section">
          <div class="detail-card">
            <div class="detail-icon">
              <i class="fas fa-sync-alt"></i>
            </div>
            <div class="detail-content">
              <h4>Nâng cấp Hệ thống</h4>
              <p>Cập nhật phiên bản mới với nhiều tính năng được cải thiện</p>
            </div>
          </div>

          <div class="detail-card">
            <div class="detail-icon">
              <i class="fas fa-shield-alt"></i>
            </div>
            <div class="detail-content">
              <h4>Bảo mật</h4>
              <p>Tăng cường hệ thống bảo mật để bảo vệ dữ liệu của bạn</p>
            </div>
          </div>

          <div class="detail-card">
            <div class="detail-icon">
              <i class="fas fa-bolt"></i>
            </div>
            <div class="detail-content">
              <h4>Hiệu suất</h4>
              <p>Tối ưu hóa tốc độ và hiệu suất hệ thống</p>
            </div>
          </div>
        </div>

        <!-- Contact Information -->
        <div class="contact-section">
          <div class="contact-info">
            <h3>Liên hệ hỗ trợ</h3>
            <div class="contact-methods">
              <div class="contact-method">
                <i class="fas fa-envelope"></i>
                <span>support@company.com</span>
              </div>
              <div class="contact-method">
                <i class="fas fa-phone"></i>
                <span>+84 123 456 789</span>
              </div>
              <div class="contact-method">
                <i class="fab fa-facebook"></i>
                <span>facebook.com/company</span>
              </div>
            </div>
          </div>
        </div>

        <!-- Refresh Button -->
        <div class="action-section">
          <button class="refresh-btn" @click="refreshPage">
            <i class="fas fa-redo"></i>
            Thử lại
          </button>
          <div class="auto-refresh">
            <label class="checkbox-container">
              <input type="checkbox" v-model="autoRefresh">
              <span class="checkmark"></span>
              Tự động làm mới (30s)
            </label>
          </div>
        </div>

        <!-- Social Links -->
        <div class="social-section">
          <p>Theo dõi chúng tôi để cập nhật thông tin mới nhất</p>
          <div class="social-links">
            <a href="#" class="social-link">
              <i class="fab fa-facebook-f"></i>
            </a>
            <a href="#" class="social-link">
              <i class="fab fa-twitter"></i>
            </a>
            <a href="#" class="social-link">
              <i class="fab fa-linkedin-in"></i>
            </a>
            <a href="#" class="social-link">
              <i class="fab fa-github"></i>
            </a>
          </div>
        </div>
      </div>

      <!-- Footer -->
      <div class="footer">
        <p>&copy; 2024 Your Company. All rights reserved.</p>
        <p>Hệ thống sẽ trở lại trong thời gian sớm nhất. Xin cảm ơn!</p>
      </div>
    </div>

    <!-- Notification -->
    <div v-if="showNotification" class="notification">
      <i class="fas fa-bell"></i>
      <span>Hệ thống đang được bảo trì. Vui lòng quay lại sau.</span>
      <button class="close-notification" @click="showNotification = false">
        <i class="fas fa-times"></i>
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted, computed } from 'vue'

// Reactive data
const progress = ref(0)
const elapsedTime = ref('00:00:00')
const estimatedCompletion = ref('12:00 PM')
const countdown = ref({ hours: '02', minutes: '30', seconds: '00' })
const autoRefresh = ref(false)
const showNotification = ref(true)
const startTime = ref(new Date())

// Simulate progress
const simulateProgress = () => {
  const interval = setInterval(() => {
    if (progress.value < 100) {
      progress.value += Math.random() * 2
      if (progress.value > 100) progress.value = 100
    } else {
      clearInterval(interval)
    }
  }, 1000)
}

// Update elapsed time
const updateElapsedTime = () => {
  const now = new Date()
  const diff = now - startTime.value
  const hours = Math.floor(diff / 3600000)
  const minutes = Math.floor((diff % 3600000) / 60000)
  const seconds = Math.floor((diff % 60000) / 1000)
  
  elapsedTime.value = 
    `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`
}

// Update countdown
const updateCountdown = () => {
  // Simulate countdown from 2 hours 30 minutes
  let totalSeconds = 2 * 3600 + 30 * 60
  const now = new Date()
  const start = new Date(now)
  start.setHours(now.getHours() - 1) // Started 1 hour ago
  
  const elapsed = Math.floor((now - start) / 1000)
  totalSeconds = Math.max(0, totalSeconds - elapsed)
  
  const hours = Math.floor(totalSeconds / 3600)
  const minutes = Math.floor((totalSeconds % 3600) / 60)
  const seconds = totalSeconds % 60
  
  countdown.value = {
    hours: hours.toString().padStart(2, '0'),
    minutes: minutes.toString().padStart(2, '0'),
    seconds: seconds.toString().padStart(2, '0')
  }
}

// Auto refresh
let refreshInterval = null
const setupAutoRefresh = () => {
  if (autoRefresh.value) {
    refreshInterval = setInterval(() => {
      window.location.reload()
    }, 30000)
  } else {
    if (refreshInterval) {
      clearInterval(refreshInterval)
      refreshInterval = null
    }
  }
}

// Refresh page
const refreshPage = () => {
  window.location.reload()
}

// Lifecycle
onMounted(() => {
  simulateProgress()
  updateElapsedTime()
  updateCountdown()
  
  // Update timers every second
  const timerInterval = setInterval(() => {
    updateElapsedTime()
    updateCountdown()
  }, 1000)
  
  // Cleanup
  onUnmounted(() => {
    clearInterval(timerInterval)
    if (refreshInterval) {
      clearInterval(refreshInterval)
    }
  })
})
</script>

<style scoped>
.maintenance-page {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  position: relative;
  overflow: hidden;
}

/* Background Animation */
.background-animation {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 1;
}

.circle {
  position: absolute;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.1);
  animation: float 6s ease-in-out infinite;
}

.circle-1 {
  width: 100px;
  height: 100px;
  top: 10%;
  left: 10%;
  animation-delay: 0s;
}

.circle-2 {
  width: 150px;
  height: 150px;
  top: 60%;
  left: 80%;
  animation-delay: 2s;
}

.circle-3 {
  width: 80px;
  height: 80px;
  top: 80%;
  left: 20%;
  animation-delay: 4s;
}

.circle-4 {
  width: 120px;
  height: 120px;
  top: 30%;
  left: 70%;
  animation-delay: 1s;
}

@keyframes float {
  0%, 100% {
    transform: translateY(0) rotate(0deg);
  }
  50% {
    transform: translateY(-20px) rotate(180deg);
  }
}

/* Main Container */
.maintenance-container {
  position: relative;
  z-index: 2;
  max-width: 1200px;
  margin: 0 auto;
  padding: 40px 20px;
  color: white;
}

/* Logo */
.logo-section {
  text-align: center;
  margin-bottom: 40px;
}

.logo {
  display: inline-flex;
  align-items: center;
  gap: 10px;
  font-size: 2rem;
  font-weight: bold;
}

.logo i {
  font-size: 2.5rem;
  color: #ffd700;
}

/* Illustration */
.illustration {
  position: relative;
  width: 200px;
  height: 200px;
  margin: 0 auto 40px;
}

.gear {
  position: absolute;
  border: 4px solid rgba(255, 255, 255, 0.3);
  border-radius: 50%;
  animation: rotate linear infinite;
}

.gear-inner {
  position: relative;
  width: 100%;
  height: 100%;
}

.bar {
  position: absolute;
  background: rgba(255, 255, 255, 0.3);
  width: 8px;
  height: 40px;
  top: 50%;
  left: 50%;
  transform-origin: center;
}

.bar:nth-child(1) { transform: translate(-50%, -50%) rotate(0deg); }
.bar:nth-child(2) { transform: translate(-50%, -50%) rotate(60deg); }
.bar:nth-child(3) { transform: translate(-50%, -50%) rotate(120deg); }
.bar:nth-child(4) { transform: translate(-50%, -50%) rotate(180deg); }
.bar:nth-child(5) { transform: translate(-50%, -50%) rotate(240deg); }
.bar:nth-child(6) { transform: translate(-50%, -50%) rotate(300deg); }

.gear-1 {
  width: 120px;
  height: 120px;
  top: 0;
  left: 0;
  animation-duration: 8s;
}

.gear-2 {
  width: 80px;
  height: 80px;
  top: 60px;
  left: 100px;
  animation-duration: 6s;
  animation-direction: reverse;
}

.gear-3 {
  width: 60px;
  height: 60px;
  top: 120px;
  left: 40px;
  animation-duration: 4s;
}

.maintenance-icon {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 3rem;
  color: #ffd700;
}

@keyframes rotate {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
}

/* Content Section */
.content-section {
  text-align: center;
  max-width: 800px;
  margin: 0 auto;
}

.main-title {
  font-size: 3rem;
  font-weight: 700;
  margin-bottom: 20px;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3);
}

.subtitle {
  font-size: 1.2rem;
  margin-bottom: 40px;
  opacity: 0.9;
}

/* Progress Section */
.progress-section {
  background: rgba(255, 255, 255, 0.1);
  padding: 30px;
  border-radius: 15px;
  margin-bottom: 40px;
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.2);
}

.progress-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.progress-label {
  font-size: 1.1rem;
}

.progress-percentage {
  font-size: 1.5rem;
  font-weight: bold;
  color: #ffd700;
}

.progress-bar {
  width: 100%;
  height: 12px;
  background: rgba(255, 255, 255, 0.2);
  border-radius: 6px;
  overflow: hidden;
  margin-bottom: 15px;
}

.progress-fill {
  height: 100%;
  background: linear-gradient(90deg, #ffd700, #ffed4e);
  border-radius: 6px;
  transition: width 0.3s ease;
  position: relative;
  overflow: hidden;
}

.progress-fill::after {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.4), transparent);
  animation: shimmer 2s infinite;
}

@keyframes shimmer {
  0% {
    left: -100%;
  }
  100% {
    left: 100%;
  }
}

.progress-time {
  display: flex;
  justify-content: space-between;
  font-size: 0.9rem;
  opacity: 0.8;
}

/* Countdown */
.countdown-section {
  margin-bottom: 40px;
}

.countdown-section h3 {
  margin-bottom: 20px;
  font-size: 1.5rem;
}

.countdown-timer {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 20px;
}

.time-unit {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.time-value {
  font-size: 3rem;
  font-weight: bold;
  background: rgba(255, 255, 255, 0.1);
  padding: 20px;
  border-radius: 10px;
  min-width: 80px;
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.2);
}

.time-label {
  margin-top: 5px;
  font-size: 0.9rem;
  opacity: 0.8;
}

.time-separator {
  font-size: 2rem;
  font-weight: bold;
  margin-bottom: 20px;
  color: #ffd700;
}

/* Details Section */
.details-section {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 20px;
  margin-bottom: 40px;
}

.detail-card {
  background: rgba(255, 255, 255, 0.1);
  padding: 25px;
  border-radius: 10px;
  display: flex;
  align-items: flex-start;
  gap: 15px;
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.2);
  transition: transform 0.3s ease;
}

.detail-card:hover {
  transform: translateY(-5px);
}

.detail-icon {
  font-size: 2rem;
  color: #ffd700;
  flex-shrink: 0;
}

.detail-content h4 {
  margin: 0 0 10px 0;
  font-size: 1.2rem;
}

.detail-content p {
  margin: 0;
  opacity: 0.8;
  font-size: 0.9rem;
}

/* Contact Section */
.contact-section {
  background: rgba(255, 255, 255, 0.1);
  padding: 30px;
  border-radius: 15px;
  margin-bottom: 40px;
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.2);
}

.contact-section h3 {
  margin-bottom: 20px;
  text-align: center;
}

.contact-methods {
  display: flex;
  justify-content: center;
  gap: 40px;
  flex-wrap: wrap;
}

.contact-method {
  display: flex;
  align-items: center;
  gap: 10px;
}

.contact-method i {
  color: #ffd700;
  font-size: 1.2rem;
}

/* Action Section */
.action-section {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 15px;
  margin-bottom: 40px;
}

.refresh-btn {
  background: #ffd700;
  color: #333;
  border: none;
  padding: 15px 30px;
  border-radius: 50px;
  font-size: 1.1rem;
  font-weight: 600;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 10px;
  transition: all 0.3s ease;
}

.refresh-btn:hover {
  background: #ffed4e;
  transform: scale(1.05);
}

.auto-refresh {
  display: flex;
  align-items: center;
  gap: 10px;
}

.checkbox-container {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
}

.checkbox-container input {
  display: none;
}

.checkmark {
  width: 18px;
  height: 18px;
  border: 2px solid rgba(255, 255, 255, 0.5);
  border-radius: 3px;
  position: relative;
  transition: all 0.3s ease;
}

.checkbox-container input:checked + .checkmark {
  background: #ffd700;
  border-color: #ffd700;
}

.checkbox-container input:checked + .checkmark::after {
  content: '✓';
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: #333;
  font-size: 12px;
  font-weight: bold;
}

/* Social Section */
.social-section {
  margin-bottom: 40px;
}

.social-section p {
  margin-bottom: 20px;
  opacity: 0.8;
}

.social-links {
  display: flex;
  justify-content: center;
  gap: 15px;
}

.social-link {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 50px;
  height: 50px;
  background: rgba(255, 255, 255, 0.1);
  border-radius: 50%;
  color: white;
  text-decoration: none;
  transition: all 0.3s ease;
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.2);
}

.social-link:hover {
  background: #ffd700;
  color: #333;
  transform: scale(1.1);
}

/* Footer */
.footer {
  text-align: center;
  padding-top: 30px;
  border-top: 1px solid rgba(255, 255, 255, 0.2);
}

.footer p {
  margin: 5px 0;
  opacity: 0.7;
  font-size: 0.9rem;
}

/* Notification */
.notification {
  position: fixed;
  top: 20px;
  right: 20px;
  background: rgba(255, 255, 255, 0.95);
  color: #333;
  padding: 15px 20px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  gap: 10px;
  z-index: 1000;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
  backdrop-filter: blur(10px);
  animation: slideIn 0.5s ease;
}

.notification i {
  color: #ff6b6b;
}

.close-notification {
  background: none;
  border: none;
  color: #666;
  cursor: pointer;
  padding: 5px;
  margin-left: 10px;
}

@keyframes slideIn {
  from {
    transform: translateX(100%);
    opacity: 0;
  }
  to {
    transform: translateX(0);
    opacity: 1;
  }
}

/* Responsive Design */
@media (max-width: 768px) {
  .main-title {
    font-size: 2rem;
  }
  
  .countdown-timer {
    gap: 10px;
  }
  
  .time-value {
    font-size: 2rem;
    padding: 15px;
    min-width: 60px;
  }
  
  .contact-methods {
    flex-direction: column;
    gap: 15px;
  }
  
  .details-section {
    grid-template-columns: 1fr;
  }
  
  .notification {
    left: 20px;
    right: 20px;
  }
}

@media (max-width: 480px) {
  .maintenance-container {
    padding: 20px 15px;
  }
  
  .logo {
    font-size: 1.5rem;
  }
  
  .logo i {
    font-size: 2rem;
  }
  
  .illustration {
    width: 150px;
    height: 150px;
  }
  
  .gear-1 {
    width: 90px;
    height: 90px;
  }
  
  .gear-2 {
    width: 60px;
    height: 60px;
    top: 45px;
    left: 75px;
  }
  
  .gear-3 {
    width: 45px;
    height: 45px;
    top: 90px;
    left: 30px;
  }
  
  .maintenance-icon {
    font-size: 2rem;
  }
  
  .countdown-timer {
    gap: 5px;
  }
  
  .time-value {
    font-size: 1.5rem;
    padding: 10px;
    min-width: 50px;
  }
  
  .time-separator {
    font-size: 1.5rem;
  }
}
</style>