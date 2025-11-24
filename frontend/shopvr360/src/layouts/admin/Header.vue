<template>
    <div class="admin-layout">
        <!-- Header -->
        <header class="admin-header">
            <div class="header-content">
                <!-- Left Section -->
                <div class="header-left">
                    <button class="sidebar-toggle" @click="toggleSidebar">
                        <i class="fas fa-bars"></i>
                    </button>
                    <div class="greeting-section">
                        <h1 class="greeting">Chào mừng trở lại, <span class="highlight-name">{{ fullName }}</span></h1>
                        <div class="user-meta">
                            <div class="meta-tags">
                                <span class="role-tag">{{ role }}</span>
                                <span class="location-tag">
                                    <i class="fas fa-map-marker-alt"></i>
                                    {{ city }}
                                </span>
                                <span class="status-tag" :class="{ 'online': isLoggedIn }">
                                    <i class="fas fa-circle"></i>
                                    {{ isLoggedIn ? 'Online' : 'Offline' }}
                                </span>
                            </div>
                            <p class="login-time">
                                <i class="fas fa-clock"></i>
                                Đăng nhập: {{ loginTime }}
                            </p>
                        </div>
                    </div>
                </div>

                <!-- Right Section -->
                <div class="header-right">
                    <!-- Quick Actions -->
                    <div class="quick-actions">
                        <button class="action-btn" @click="goHome">
                            <i class="fas fa-home"></i>
                        </button>

                        <button class="action-btn" @click="handleNotification">
                            <i class="fas fa-bell"></i>
                            <span class="notification-badge" v-if="unreadNotifications">3</span>
                        </button>
                        <button class="action-btn" @click="handleSettings">
                            <i class="fas fa-cog"></i>
                        </button>
                    </div>

                    <!-- User Profile -->
                    <div class="user-profile" @click="toggleUserMenu">
                        <div class="profile-avatar">
                            <div class="avatar-circle">
                                <span class="avatar-text">{{ getInitials(displayName) }}</span>
                                <div class="online-indicator" v-if="isLoggedIn"></div>
                            </div>
                        </div>
                        <div class="profile-info">
                            <span class="profile-name">{{ displayName }}</span>
                            <span class="profile-role">{{ role }}</span>
                        </div>
                        <i class="dropdown-arrow fas fa-chevron-down" :class="{ 'rotated': showUserMenu }"></i>
                    </div>

                    <!-- User Dropdown Menu -->
                    <div v-if="showUserMenu" class="user-dropdown-menu">
                        <div class="dropdown-header">
                            <div class="dropdown-avatar">
                                <div class="avatar-circle large">
                                    <span class="avatar-text">{{ getInitials(displayName) }}</span>
                                </div>
                            </div>
                            <div class="dropdown-user-info">
                                <h4>{{ displayName }}</h4>
                                <p>{{ email }}</p>
                            </div>
                        </div>

                        <div class="dropdown-divider"></div>

                        <div class="dropdown-items">
                            <a href="#" class="dropdown-item" @click.prevent="viewProfile">
                                <i class="fas fa-user"></i>
                                <span>Hồ sơ cá nhân</span>
                            </a>
                            <a href="#" class="dropdown-item" @click.prevent="viewSettings">
                                <i class="fas fa-cog"></i>
                                <span>Cài đặt</span>
                            </a>
                            <a href="#" class="dropdown-item" @click.prevent="viewActivity">
                                <i class="fas fa-chart-line"></i>
                                <span>Hoạt động</span>
                            </a>
                        </div>

                        <div class="dropdown-divider"></div>

                        <div class="dropdown-items">
                            <a href="#" class="dropdown-item" @click.prevent="showFullInfo">
                                <i class="fas fa-info-circle"></i>
                                <span>Thông tin đầy đủ</span>
                            </a>
                            <a href="#" class="dropdown-item logout" @click.prevent="logout">
                                <i class="fas fa-sign-out-alt"></i>
                                <span>Đăng xuất</span>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </header>

        <!-- Main Layout -->
        <div class="main-layout">
            <!-- Sidebar -->
            <aside class="admin-sidebar" :class="{ 'collapsed': sidebarCollapsed }">
                <div class="sidebar-header">
                    <div class="logo" v-if="!sidebarCollapsed">
                        <img src="../client/image/image.png" alt="Logo">
                        <span class="logo-text">SHOPVR360</span>
                    </div>
                    <div class="logo-collapsed" v-else>
                        <img src="../client/image/image.png" alt="Logo">
                    </div>
                </div>

                <nav class="sidebar-nav">
                    <!-- Dashboard -->
                    <div class="nav-section">
                        <div class="section-label" v-if="!sidebarCollapsed">TỔNG QUAN</div>
                        <router-link to="/admin/dashboard" class="nav-item" active-class="active">
                            <i class="nav-icon fas fa-tachometer-alt"></i>
                            <span class="nav-text">Dashboard</span>
                            <span class="nav-badge" v-if="sidebarCollapsed">12</span>
                        </router-link>
                    </div>

                    <!-- Quản lý người dùng -->
                    <div class="nav-section">
                        <div class="section-label" v-if="!sidebarCollapsed">QUẢN LÝ NGƯỜI DÙNG</div>
                        <router-link to="/admin/users" class="nav-item" active-class="active">
                            <i class="nav-icon fas fa-users"></i>
                            <span class="nav-text">Quản lý Users</span>
                            <span class="nav-badge">24</span>
                        </router-link>
                        <router-link to="/admin/roles" class="nav-item" active-class="active" @click="BaoTri">
                            <i class="nav-icon fas fa-user-shield"></i>
                            <span class="nav-text">Phân quyền</span>
                        </router-link>
                    </div>

                    <!-- Quản lý sản phẩm -->
                    <div class="nav-section">
                        <div class="section-label" v-if="!sidebarCollapsed">QUẢN LÝ SẢN PHẨM</div>
                        <router-link to="/admin/products" class="nav-item" active-class="active">
                            <i class="nav-icon fas fa-box"></i>
                            <span class="nav-text">Sản phẩm</span>
                            <span class="nav-badge">156</span>
                        </router-link>
                        <router-link to="/admin/categories" class="nav-item" active-class="active">
                            <i class="nav-icon fas fa-tags"></i>
                            <span class="nav-text">Danh mục</span>
                        </router-link>
                        <router-link to="/admin/inventory" class="nav-item" active-class="active">
                            <i class="nav-icon fas fa-warehouse"></i>
                            <span class="nav-text">Tồn kho</span>
                        </router-link>
                    </div>

                    <!-- Quản lý đơn hàng -->
                    <div class="nav-section">
                        <div class="section-label" v-if="!sidebarCollapsed">QUẢN LÝ ĐƠN HÀNG</div>
                        <router-link to="/admin/orders" class="nav-item" active-class="active">
                            <i class="nav-icon fas fa-shopping-cart"></i>
                            <span class="nav-text">Đơn hàng</span>
                            <span class="nav-badge">42</span>
                        </router-link>
                        <router-link to="/admin/transactions" class="nav-item" active-class="active">
                            <i class="nav-icon fas fa-exchange-alt"></i>
                            <span class="nav-text">Giao dịch</span>
                        </router-link>
                    </div>

                    <!-- Báo cáo & Thống kê -->
                    <div class="nav-section">
                        <div class="section-label" v-if="!sidebarCollapsed">BÁO CÁO & THỐNG KÊ</div>
                        <router-link to="/admin/reports" class="nav-item" active-class="active">
                            <i class="nav-icon fas fa-chart-bar"></i>
                            <span class="nav-text">Báo cáo</span>
                        </router-link>
                        <router-link to="/admin/analytics" class="nav-item" active-class="active">
                            <i class="nav-icon fas fa-chart-line"></i>
                            <span class="nav-text">Phân tích</span>
                        </router-link>
                    </div>

                    <!-- Hệ thống -->
                    <div class="nav-section">
                        <div class="section-label" v-if="!sidebarCollapsed">HỆ THỐNG</div>
                        <router-link to="/admin/settings" class="nav-item" active-class="active">
                            <i class="nav-icon fas fa-cog"></i>
                            <span class="nav-text">Cài đặt</span>
                        </router-link>
                        <router-link to="/admin/logs" class="nav-item" active-class="active">
                            <i class="nav-icon fas fa-clipboard-list"></i>
                            <span class="nav-text">Nhật ký</span>
                        </router-link>
                    </div>
                </nav>

                <div class="sidebar-footer">
                    <button class="collapse-btn" @click="toggleSidebar">
                        <i class="fas" :class="sidebarCollapsed ? 'fa-chevron-right' : 'fa-chevron-left'"></i>
                        <span v-if="!sidebarCollapsed">Thu gọn</span>
                    </button>
                </div>
            </aside>

            <!-- Main Content -->
            <main class="admin-content" :class="{ 'expanded': sidebarCollapsed }">
                <div class="content-wrapper">
                    <router-view v-slot="{ Component }">
                        <transition name="fade" mode="out-in">
                            <component :is="Component" :key="$route.path" />
                        </transition>
                    </router-view>
                </div>
            </main>
        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted, onUnmounted, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { toast } from 'vue3-toastify'

const router = useRouter()
const route = useRoute()

// Debug route changes
watch(() => route.path, (newPath, oldPath) => {
  console.log('📍 Route changed:', oldPath, '→', newPath)
})

// Lấy dữ liệu từ localStorage
const userData = ref(JSON.parse(localStorage.getItem('user') || '{}'))
const token = ref(localStorage.getItem('token') || '')
const loginTime = ref(localStorage.getItem('loginTime') || '')

// Computed properties từ dữ liệu localStorage
const fullName = computed(() => userData.value?.ten || userData.value?.fullName || 'Người dùng')
const displayName = computed(() => userData.value?.username || userData.value?.displayName || fullName.value)
const role = computed(() => userData.value?.role || userData.value?.quyen || 'Người dùng')
const city = computed(() => userData.value?.city || userData.value?.thanhPho || 'Chưa cập nhật')
const email = computed(() => userData.value?.email || 'Chưa có email')
const isLoggedIn = computed(() => !!token.value)

const showUserMenu = ref(false)
const sidebarCollapsed = ref(false)
const unreadNotifications = ref(3)

// Hàm lấy chữ cái đầu cho avatar
const getInitials = (name) => {
    if (!name) return 'U'
    return name.split(' ').map(word => word[0]).join('').toUpperCase().slice(0, 2)
}

const toggleSidebar = () => {
    sidebarCollapsed.value = !sidebarCollapsed.value
}

const toggleUserMenu = () => {
    showUserMenu.value = !showUserMenu.value
}

const showFullInfo = () => {
    console.log('=== TOÀN BỘ THÔNG TIN USER TỪ LOCALSTORAGE ===', {
        user: userData.value,
        token: token.value,
        loginTime: loginTime.value
    })
    showUserMenu.value = false
}

const viewProfile = () => {
    router.push('/admin/profile')
    showUserMenu.value = false
}

const BaoTri = () => {
    
    toast('Chức năng đang được bảo trì!')
}

const viewSettings = () => {
    router.push('/admin/settings')
    showUserMenu.value = false
}

const viewActivity = () => {
    router.push('/admin/activity')
    showUserMenu.value = false
}

const handleNotification = () => {
    console.log('Notification clicked')
}

const goHome = () => {
    router.push('/')
}

const handleSettings = () => {
    console.log('Settings clicked')
}

const logout = () => {
    // Xóa tất cả dữ liệu từ localStorage
    localStorage.removeItem('token')
    localStorage.removeItem('user')
    localStorage.removeItem('loginTime')
    localStorage.removeItem('userData')

    // Reset các ref
    userData.value = {}
    token.value = ''
    loginTime.value = ''

    // Chuyển hướng về trang login
    router.push('/login')
}

// Kiểm tra và cập nhật dữ liệu từ localStorage
const checkLocalStorage = () => {
    const storedUser = localStorage.getItem('user')
    const storedToken = localStorage.getItem('token')

    if (storedUser) {
        try {
            userData.value = JSON.parse(storedUser)
        } catch (e) {
            console.error('Lỗi parse user data từ localStorage:', e)
            userData.value = {}
        }
    }

    if (storedToken) {
        token.value = storedToken
    }

    loginTime.value = localStorage.getItem('loginTime') || new Date().toLocaleString('vi-VN')
}

// Close dropdown when clicking outside
const handleClickOutside = (event) => {
    if (!event.target.closest('.user-profile') && !event.target.closest('.user-dropdown-menu')) {
        showUserMenu.value = false
    }
}

// Lắng nghe sự thay đổi từ localStorage (nếu có nhiều tab)
const handleStorageChange = (event) => {
    if (event.key === 'user' || event.key === 'token') {
        checkLocalStorage()
    }
}

onMounted(() => {
    console.log('🟢 AdminLayout mounted')
    // Kiểm tra dữ liệu khi component mounted
    checkLocalStorage()

    // Thêm event listeners
    document.addEventListener('click', handleClickOutside)
    window.addEventListener('storage', handleStorageChange)
})

onUnmounted(() => {
    console.log('🔴 AdminLayout unmounted')
    // Cleanup event listeners
    document.removeEventListener('click', handleClickOutside)
    window.removeEventListener('storage', handleStorageChange)
})
</script>

<style scoped>
/* Reset và Base Styles */
.admin-layout {
    min-height: 100vh;
    background: #f8fafc;
    display: flex;
    flex-direction: column;
}

/* Header Styles - Fixed */
.admin-header {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    border-bottom: 1px solid rgba(255, 255, 255, 0.1);
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
    position: sticky;
    top: 0;
    z-index: 1000;
    width: 100%;
    min-height: 80px;
    display: flex;
    align-items: center;
    flex-shrink: 0;
}

.header-content {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0 2rem;
    width: 100%;
    max-width: 100%;
    margin: 0 auto;
    min-height: 80px;
}

.header-left {
    display: flex;
    align-items: center;
    gap: 1rem;
    flex: 1;
    min-width: 0;
}

.sidebar-toggle {
    background: rgba(255, 255, 255, 0.1);
    border: 1px solid rgba(255, 255, 255, 0.2);
    color: white;
    width: 2.5rem;
    height: 2.5rem;
    border-radius: 12px;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    transition: all 0.3s ease;
    flex-shrink: 0;
    border: none;
    outline: none;
}

.sidebar-toggle:hover {
    background: rgba(255, 255, 255, 0.2);
    transform: translateY(-2px);
}

.greeting-section {
    color: white;
    min-width: 0;
    flex: 1;
}

.greeting {
    font-size: 1.5rem;
    font-weight: 700;
    margin: 0 0 0.5rem 0;
    background: linear-gradient(135deg, #ffffff 0%, #f8f9fa 100%);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    background-clip: text;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    line-height: 1.2;
}

.highlight-name {
    background: linear-gradient(135deg, #ffd700 0%, #ffed4e 100%);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    background-clip: text;
}

.user-meta {
    display: flex;
    flex-direction: column;
    gap: 0.5rem;
    min-width: 0;
}

.meta-tags {
    display: flex;
    gap: 0.75rem;
    align-items: center;
    flex-wrap: wrap;
}

.role-tag {
    background: rgba(255, 255, 255, 0.2);
    color: white;
    padding: 0.25rem 0.75rem;
    border-radius: 20px;
    font-size: 0.75rem;
    font-weight: 600;
    border: 1px solid rgba(255, 255, 255, 0.3);
    flex-shrink: 0;
}

.location-tag,
.status-tag {
    display: flex;
    align-items: center;
    gap: 0.5rem;
    color: rgba(255, 255, 255, 0.9);
    font-size: 0.8rem;
    font-weight: 500;
    flex-shrink: 0;
}

.status-tag.online i {
    color: #10b981;
}

.status-tag:not(.online) i {
    color: #6b7280;
}

.login-time {
    color: rgba(255, 255, 255, 0.8);
    font-size: 0.8rem;
    margin: 0;
    display: flex;
    align-items: center;
    gap: 0.5rem;
    white-space: nowrap;
}

/* Right Section */
.header-right {
    display: flex;
    align-items: center;
    gap: 1.5rem;
    flex-shrink: 0;
}

.quick-actions {
    display: flex;
    gap: 0.5rem;
}

.action-btn {
    position: relative;
    background: rgba(255, 255, 255, 0.1);
    border: 1px solid rgba(255, 255, 255, 0.2);
    color: white;
    width: 2.5rem;
    height: 2.5rem;
    border-radius: 12px;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    transition: all 0.3s ease;
    flex-shrink: 0;
    border: none;
    outline: none;
}

.action-btn:hover {
    background: rgba(255, 255, 255, 0.2);
    transform: translateY(-2px);
}

.notification-badge {
    position: absolute;
    top: -5px;
    right: -5px;
    background: #ef4444;
    color: white;
    font-size: 0.7rem;
    font-weight: 600;
    width: 1.25rem;
    height: 1.25rem;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
}

/* User Profile */
.user-profile {
    display: flex;
    align-items: center;
    gap: 0.75rem;
    padding: 0.5rem 1rem;
    border-radius: 16px;
    background: rgba(255, 255, 255, 0.1);
    border: 1px solid rgba(255, 255, 255, 0.2);
    cursor: pointer;
    transition: all 0.3s ease;
    position: relative;
    flex-shrink: 0;
    min-width: 0;
    border: none;
    outline: none;
}

.user-profile:hover {
    background: rgba(255, 255, 255, 0.2);
    transform: translateY(-2px);
}

.profile-avatar {
    position: relative;
    flex-shrink: 0;
}

.avatar-circle {
    width: 2.5rem;
    height: 2.5rem;
    border-radius: 50%;
    background: linear-gradient(135deg, #ffd700 0%, #ffed4e 100%);
    display: flex;
    align-items: center;
    justify-content: center;
    color: #1f2937;
    font-weight: 700;
    font-size: 1rem;
    position: relative;
    flex-shrink: 0;
}

.avatar-circle.large {
    width: 3rem;
    height: 3rem;
    font-size: 1.2rem;
}

.online-indicator {
    position: absolute;
    bottom: 2px;
    right: 2px;
    width: 0.75rem;
    height: 0.75rem;
    background: #10b981;
    border: 2px solid white;
    border-radius: 50%;
}

.profile-info {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    min-width: 0;
    flex: 1;
}

.profile-name {
    color: white;
    font-weight: 600;
    font-size: 0.9rem;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    max-width: 120px;
}

.profile-role {
    color: rgba(255, 255, 255, 0.8);
    font-size: 0.75rem;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    max-width: 120px;
}

.dropdown-arrow {
    color: rgba(255, 255, 255, 0.8);
    font-size: 0.8rem;
    transition: transform 0.3s ease;
    flex-shrink: 0;
}

.dropdown-arrow.rotated {
    transform: rotate(180deg);
}

/* Dropdown Menu */
.user-dropdown-menu {
    position: absolute;
    top: 100%;
    right: 0;
    margin-top: 0.5rem;
    background: white;
    border-radius: 16px;
    box-shadow: 0 20px 40px rgba(0, 0, 0, 0.15);
    min-width: 280px;
    z-index: 1001;
    animation: dropdownAppear 0.3s ease;
    border: 1px solid #e5e7eb;
    overflow: hidden;
}

@keyframes dropdownAppear {
    from {
        opacity: 0;
        transform: translateY(-10px) scale(0.95);
    }
    to {
        opacity: 1;
        transform: translateY(0) scale(1);
    }
}

.dropdown-header {
    padding: 1.5rem;
    background: linear-gradient(135deg, #f8fafc 0%, #f1f5f9 100%);
    border-bottom: 1px solid #e5e7eb;
}

.dropdown-avatar {
    display: flex;
    justify-content: center;
    margin-bottom: 1rem;
}

.dropdown-user-info {
    text-align: center;
}

.dropdown-user-info h4 {
    margin: 0 0 0.25rem 0;
    color: #1f2937;
    font-weight: 700;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}

.dropdown-user-info p {
    margin: 0;
    color: #6b7280;
    font-size: 0.875rem;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}

.dropdown-divider {
    height: 1px;
    background: #e5e7eb;
    margin: 0.5rem 0;
}

.dropdown-items {
    padding: 0.5rem 0;
}

.dropdown-item {
    display: flex;
    align-items: center;
    gap: 0.75rem;
    padding: 0.75rem 1.5rem;
    color: #4b5563;
    text-decoration: none;
    transition: all 0.2s ease;
    font-weight: 500;
    white-space: nowrap;
    border: none;
    background: none;
    width: 100%;
    text-align: left;
    cursor: pointer;
}

.dropdown-item:hover {
    background: #f3f4f6;
    color: #1f2937;
}

.dropdown-item i {
    width: 1rem;
    color: #9ca3af;
    flex-shrink: 0;
}

.dropdown-item.logout {
    color: #ef4444;
}

.dropdown-item.logout:hover {
    background: #fef2f2;
    color: #dc2626;
}

/* Main Layout */
.main-layout {
    display: flex;
    flex: 1;
    min-height: calc(100vh - 80px);
    position: relative;
}

/* Sidebar Styles */
.admin-sidebar {
    width: 280px;
    background: white;
    border-right: 1px solid #e5e7eb;
    display: flex;
    flex-direction: column;
    transition: all 0.3s ease;
    box-shadow: 4px 0 20px rgba(0, 0, 0, 0.08);
    position: relative;
    z-index: 900;
    flex-shrink: 0;
}

.admin-sidebar.collapsed {
    width: 70px;
}

.sidebar-header {
    padding: 1.5rem;
    border-bottom: 1px solid #e5e7eb;
    background: linear-gradient(135deg, #f8fafc 0%, #f1f5f9 100%);
    flex-shrink: 0;
}

.logo {
    display: flex;
    align-items: center;
    gap: 1rem;
    color: #667eea;
    font-weight: 400;
    font-size: 1.25rem;
}

.logo img {
    width: 100px;
    height: auto;
    display: block;
}

.logo-collapsed {
    display: flex;
    align-items: center;
    justify-content: center;
    color: #667eea;
    font-size: 1.5rem;
}

.sidebar-nav {
    flex: 1;
    padding: 1rem 0;
    overflow-y: auto;
}

.nav-section {
    margin-bottom: 1.5rem;
}

.section-label {
    padding: 0.5rem 1.5rem;
    color: #6b7280;
    font-size: 0.75rem;
    font-weight: 600;
    text-transform: uppercase;
    letter-spacing: 0.5px;
}

.nav-item {
    display: flex;
    align-items: center;
    gap: 0.75rem;
    padding: 0.75rem 1.5rem;
    color: #4b5563;
    text-decoration: none;
    transition: all 0.3s ease;
    position: relative;
    border-left: 3px solid transparent;
}

.nav-item:hover {
    background: #f3f4f6;
    color: #1f2937;
    border-left-color: #667eea;
}

.nav-item.active {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    color: white;
    border-left-color: #ffd700;
}

.nav-icon {
    width: 1.25rem;
    text-align: center;
    font-size: 1rem;
    flex-shrink: 0;
}

.nav-text {
    flex: 1;
    font-weight: 500;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}

.admin-sidebar.collapsed .nav-text {
    display: none;
}

.nav-badge {
    background: #ef4444;
    color: white;
    font-size: 0.7rem;
    font-weight: 600;
    padding: 0.25rem 0.5rem;
    border-radius: 12px;
    min-width: 1.5rem;
    text-align: center;
    flex-shrink: 0;
}

.admin-sidebar.collapsed .nav-badge {
    position: absolute;
    top: 0.5rem;
    right: 0.5rem;
    transform: scale(0.8);
}

.sidebar-footer {
    padding: 1rem;
    border-top: 1px solid #e5e7eb;
    background: #f8fafc;
    flex-shrink: 0;
}

.collapse-btn {
    display: flex;
    align-items: center;
    gap: 0.75rem;
    width: 100%;
    padding: 0.75rem;
    background: transparent;
    border: 1px solid #e5e7eb;
    border-radius: 8px;
    color: #6b7280;
    cursor: pointer;
    transition: all 0.3s ease;
    font-weight: 500;
    border: none;
    outline: none;
}

.collapse-btn:hover {
    background: white;
    color: #374151;
    border-color: #d1d5db;
}

.admin-sidebar.collapsed .collapse-btn span {
    display: none;
}

/* Main Content */
.admin-content {
    flex: 1;
    transition: all 0.3s ease;
    background: #f8fafc;
    min-height: calc(100vh - 80px);
    overflow: auto;
}

.admin-content.expanded {
    margin-left: 0;
}

.content-wrapper {
    padding: 2rem;
    max-width: 1400px;
    margin: 0 auto;
    width: 100%;
}

/* Transition Effects */
.fade-enter-active,
.fade-leave-active {
    transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
    opacity: 0;
}

/* Responsive Design */
@media (max-width: 1024px) {
    .admin-sidebar {
        position: fixed;
        left: 0;
        top: 80px;
        height: calc(100vh - 80px);
        transform: translateX(0);
        z-index: 800;
    }

    .admin-sidebar.collapsed {
        transform: translateX(-100%);
    }

    .admin-content {
        margin-left: 0;
        width: 100%;
    }
}

@media (max-width: 768px) {
    .header-content {
        padding: 0 1rem;
    }

    .header-left {
        gap: 0.5rem;
    }

    .greeting {
        font-size: 1.25rem;
    }

    .meta-tags {
        justify-content: flex-start;
    }

    .header-right {
        gap: 1rem;
    }

    .user-profile .profile-info {
        display: none;
    }

    .content-wrapper {
        padding: 1rem;
    }

    .user-dropdown-menu {
        min-width: 250px;
        right: -1rem;
    }
}

@media (max-width: 480px) {
    .header-content {
        padding: 0 0.5rem;
    }

    .greeting {
        font-size: 1rem;
    }

    .role-tag {
        font-size: 0.7rem;
        padding: 0.2rem 0.5rem;
    }

    .quick-actions {
        gap: 0.25rem;
    }

    .action-btn {
        width: 2rem;
        height: 2rem;
    }

    .location-tag,
    .login-time {
        display: none;
    }
}

/* Scrollbar Styling */
.sidebar-nav::-webkit-scrollbar {
    width: 4px;
}

.sidebar-nav::-webkit-scrollbar-track {
    background: #f1f1f1;
}

.sidebar-nav::-webkit-scrollbar-thumb {
    background: #c1c1c1;
    border-radius: 2px;
}

.sidebar-nav::-webkit-scrollbar-thumb:hover {
    background: #a8a8a8;
}

/* Focus States for Accessibility */
.sidebar-toggle:focus,
.action-btn:focus,
.user-profile:focus,
.collapse-btn:focus,
.nav-item:focus {
    outline: 2px solid #667eea;
    outline-offset: 2px;
}
</style>