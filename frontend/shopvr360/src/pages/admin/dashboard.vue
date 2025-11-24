<template>
  <div class="admin-dashboard">
    <!-- Header -->
    <div class="dashboard-header">
      <div class="header-content">
        <h1 class="dashboard-title">
          <span class="title-accent">Xen</span> chiao 1
        </h1>
        <div class="user-info">
          <div class="user-avatar">
            <i class="fas fa-user-shield"></i>
          </div>
        </div>
      </div>
    </div>

    <!-- Welcome Section -->
    <div class="welcome-card">
      <div class="welcome-icon">
        <i class="fas fa-tachometer-alt"></i>
      </div>
      <div class="welcome-content">
        <h2>Chào mừng đến với Administrator</h2>
        <p>HỆ THỐNG QUẢN TRỊ NỘI DUNG WEBSITE</p>
      </div>
    </div>

    <!-- Main Content -->
    <div class="dashboard-content">
      <!-- Statistics Section -->
      <div class="stats-section">
        <div class="section-header">
          <h3><i class="fas fa-chart-bar"></i> Thống kê theo tháng</h3>
          <div class="header-actions">
            <button class="btn-primary">
              <i class="fas fa-download"></i> Xuất báo cáo
            </button>
          </div>
        </div>

        <div class="date-inputs">
          <div class="input-group">
            <label>Thời gian</label>
            <div class="input-with-icon">
              <i class="fas fa-calendar"></i>
              <input type="text" placeholder="yyyy mm-dd" class="modern-input">
            </div>
          </div>
          <div class="input-group">
            <label>Mã truy cập</label>
            <div class="input-with-icon">
              <i class="fas fa-key"></i>
              <input type="text" placeholder="xgx11.7066v2rC" class="modern-input">
            </div>
          </div>
        </div>
      </div>

      <!-- Divider -->
      <div class="divider">
        <div class="divider-line"></div>
        <div class="divider-icon">
          <i class="fas fa-chart-line"></i>
        </div>
        <div class="divider-line"></div>
      </div>

      <!-- Access Statistics -->
      <div class="access-stats">
        <div class="stats-header">
          <h3><i class="fas fa-users"></i> Thống kê truy cập tháng</h3>
          <div class="month-badge">
            <span class="badge">11 – 2025</span>
          </div>
        </div>

        <!-- Stats Cards -->
        <div class="stats-grid">
          <div class="stat-card primary">
            <div class="stat-icon">
              <i class="fas fa-eye"></i>
            </div>
            <div class="stat-content">
              <h4>12,458</h4>
              <p>Lượt xem trang</p>
            </div>
          </div>
          <div class="stat-card success">
            <div class="stat-icon">
              <i class="fas fa-user-plus"></i>
            </div>
            <div class="stat-content">
              <h4>2,847</h4>
              <p>Người dùng mới</p>
            </div>
          </div>
          <div class="stat-card warning">
            <div class="stat-icon">
              <i class="fas fa-clock"></i>
            </div>
            <div class="stat-content">
              <h4>3m 24s</h4>
              <p>Thời gian trung bình</p>
            </div>
          </div>
          <div class="stat-card danger">
            <div class="stat-icon">
              <i class="fas fa-bounce-rate"></i>
            </div>
            <div class="stat-content">
              <h4>32.5%</h4>
              <p>Tỷ lệ thoát</p>
            </div>
          </div>
        </div>
      </div>

      <!-- Timestamp -->
      <div class="timestamp">
        <div class="timestamp-content">
          <i class="fas fa-sync-alt"></i>
          <span>Cập nhật lần cuối: 2011-25 15:10 pm</span>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.admin-dashboard {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.dashboard-header {
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(10px);
  border-bottom: 1px solid rgba(255, 255, 255, 0.2);
  padding: 1rem 2rem;
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  max-width: 1200px;
  margin: 0 auto;
}

.dashboard-title {
  color: white;
  font-size: 2rem;
  font-weight: 700;
}

.title-accent {
  background: linear-gradient(45deg, #ffd89b, #19547b);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.user-avatar {
  width: 45px;
  height: 45px;
  background: rgba(255, 255, 255, 0.2);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 1.2rem;
}

.welcome-card {
  background: rgba(255, 255, 255, 0.95);
  margin: 2rem auto;
  max-width: 1200px;
  border-radius: 20px;
  padding: 2rem;
  display: flex;
  align-items: center;
  gap: 1.5rem;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
  border: 1px solid rgba(255, 255, 255, 0.3);
}

.welcome-icon {
  width: 70px;
  height: 70px;
  background: linear-gradient(135deg, #667eea, #764ba2);
  border-radius: 15px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 1.8rem;
}

.welcome-content h2 {
  color: #2d3748;
  margin-bottom: 0.5rem;
  font-size: 1.5rem;
}

.welcome-content p {
  color: #718096;
  font-weight: 600;
  letter-spacing: 1px;
}

.dashboard-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 2rem 2rem;
}

.stats-section {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 20px;
  padding: 2rem;
  margin-bottom: 2rem;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2rem;
}

.section-header h3 {
  color: #2d3748;
  font-size: 1.3rem;
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.btn-primary {
  background: linear-gradient(135deg, #667eea, #764ba2);
  color: white;
  border: none;
  padding: 0.75rem 1.5rem;
  border-radius: 10px;
  font-weight: 600;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 0.5rem;
  transition: transform 0.2s;
}

.btn-primary:hover {
  transform: translateY(-2px);
}

.date-inputs {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1.5rem;
}

.input-group label {
  display: block;
  margin-bottom: 0.5rem;
  color: #4a5568;
  font-weight: 600;
}

.input-with-icon {
  position: relative;
}

.input-with-icon i {
  position: absolute;
  left: 1rem;
  top: 50%;
  transform: translateY(-50%);
  color: #a0aec0;
}

.modern-input {
  width: 100%;
  padding: 1rem 1rem 1rem 3rem;
  border: 2px solid #e2e8f0;
  border-radius: 12px;
  font-size: 1rem;
  transition: all 0.3s;
  background: white;
}

.modern-input:focus {
  outline: none;
  border-color: #667eea;
  box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
}

.divider {
  display: flex;
  align-items: center;
  margin: 3rem 0;
}

.divider-line {
  flex: 1;
  height: 2px;
  background: rgba(255, 255, 255, 0.3);
}

.divider-icon {
  width: 50px;
  height: 50px;
  background: white;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #667eea;
  margin: 0 1rem;
}

.access-stats {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 20px;
  padding: 2rem;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
}

.stats-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2rem;
}

.stats-header h3 {
  color: #2d3748;
  font-size: 1.3rem;
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.badge {
  background: linear-gradient(135deg, #667eea, #764ba2);
  color: white;
  padding: 0.5rem 1rem;
  border-radius: 20px;
  font-weight: 600;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 1.5rem;
  margin-bottom: 1rem;
}

.stat-card {
  background: white;
  padding: 1.5rem;
  border-radius: 15px;
  display: flex;
  align-items: center;
  gap: 1rem;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.08);
  border-left: 4px solid;
  transition: transform 0.3s;
}

.stat-card:hover {
  transform: translateY(-5px);
}

.stat-card.primary {
  border-left-color: #667eea;
}

.stat-card.success {
  border-left-color: #48bb78;
}

.stat-card.warning {
  border-left-color: #ed8936;
}

.stat-card.danger {
  border-left-color: #f56565;
}

.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.5rem;
  color: white;
}

.stat-card.primary .stat-icon {
  background: #667eea;
}

.stat-card.success .stat-icon {
  background: #48bb78;
}

.stat-card.warning .stat-icon {
  background: #ed8936;
}

.stat-card.danger .stat-icon {
  background: #f56565;
}

.stat-content h4 {
  font-size: 1.8rem;
  font-weight: 700;
  margin-bottom: 0.25rem;
}

.stat-content p {
  color: #718096;
  font-weight: 600;
}

.timestamp {
  text-align: center;
  margin-top: 2rem;
}

.timestamp-content {
  display: inline-flex;
  align-items: center;
  gap: 0.5rem;
  background: rgba(255, 255, 255, 0.2);
  color: white;
  padding: 0.75rem 1.5rem;
  border-radius: 25px;
  font-weight: 600;
  backdrop-filter: blur(10px);
}

@media (max-width: 768px) {
  .date-inputs {
    grid-template-columns: 1fr;
  }
  
  .section-header {
    flex-direction: column;
    gap: 1rem;
    align-items: flex-start;
  }
  
  .stats-grid {
    grid-template-columns: 1fr;
  }
  
  .welcome-card {
    flex-direction: column;
    text-align: center;
  }
}
</style>

<script>
export default {
  name: 'AdminDashboard'
}
</script>