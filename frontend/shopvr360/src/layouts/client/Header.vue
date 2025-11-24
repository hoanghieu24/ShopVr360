<template>
    <div class="header-container">
        <!-- Top bar -->
        <div class="top-bar">
            <div class="top-left">
                <span class="contact-info">cs@ktsgroup.com.vn | 08:00 - 17:45</span>
                <span class="divider">|</span>
                <div class="exchange-rates">
                    <span class="rate-item">USD: 23,250</span>
                    <span class="rate-item">EUR: 25,500</span>
                    <span class="rate-item">JPY: 155</span>
                </div>
            </div>
            <div class="top-right">
                <a href="#" class="top-link">Hỗ trợ</a>
                <a href="#" class="top-link">Theo dõi đơn hàng</a>
                <a href="#" class="top-link">Về chúng tôi</a>
            </div>
        </div>

        <!-- Main header -->
        <div class="main-header">
            <div class="logo">
                <img src="../client/image/image.png" alt="Logo">
            </div>
            <div class="nav-buttons">
                <button class="nav-btn">
                    <i class="icon-gift"></i>
                    <span>YÊU CẦU TƯ VẤN</span>
                </button>
                <button class="nav-btn">
                    <i class="icon-percent"></i>
                    <span>ƯU ĐÃI GIÁ TỐT</span>
                </button>
                <button class="nav-btn">
                    <i class="icon-cart"></i>
                    <span>MUA HÀNG DỄ DÀNG</span>
                </button>
            </div>
            <div class="auth-buttons" v-if="!isLoggedIn">
                <button class="auth-btn login-btn" @click="goLogin">
                    <i class="icon-user"></i>
                    <span>Đăng nhập</span>
                </button>
                <button class="auth-btn register-btn" @click="goRegister">
                    <i class="icon-user-plus"></i>
                    <span>Đăng ký</span>
                </button>
            </div>

            <!-- Hiển thị user info khi đã login -->
            <div class="user-info" v-else>
                <div class="user-welcome">
                    <span>Xin chào, </span>
                    <strong>{{ userDisplayName }}</strong>
                </div>
                <div class="user-actions">
                    <!-- CHỈ HIỂN THỊ NÚT ADMIN KHI ROLE LÀ ADMIN HOẶC MOD -->
                    <button class="user-btn" v-if="showAdminButton" @click="goToProAdmin">
                        <i class="icon-user"></i>
                        <span>Admin</span>
                    </button>

                    <button class="user-btn" @click="goToProfile">
                        <i class="icon-user"></i>
                        <span>Tài khoản</span>
                    </button>
                    <button class="user-btn logout-btn" @click="openLogoutModal">
                        <i class="icon-logout"></i>
                        <span>Đăng xuất</span>
                    </button>
                </div>
            </div>
        </div>

        <!-- Search section -->
        <div class="search-section">
            <div class="search-title">SHOPVR360</div>
            <div class="search-container">
                <div class="search-box">
                    <input type="text" class="search-input" placeholder="Nhập link sản phẩm hoặc từ khóa...">
                    <button class="search-btn">
                        <i class="icon-search"></i>
                        <span>Tìm kiếm</span>
                    </button>
                </div>
                <div class="cart-icon">
                    <i class="icon-shopping-cart"></i>
                    <span class="cart-count">0</span>
                </div>
            </div>

            <!-- Categories with hover dropdown -->
            <div class="categories">
                <div v-for="cat in categories" :key="cat.id" class="category"
                    @mouseenter="() => cat.name === 'Danh Mục Sản Phẩm' && handleHover(cat)"
                    @mouseleave="cat.name === 'Danh Mục Sản Phẩm' && handleLeave()">
                    <i :class="cat.icon" v-if="cat.icon"></i>
                    {{ cat.name }}

                    <!-- Dropdown sản phẩm -->
                    <div v-if="hoveredCategory === cat.name" class="dropdown-products">
                        <div class="dropdown-header">
                            <h3>Danh mục sản phẩm</h3>
                        </div>
                        <div class="products-grid">
                            <div v-if="products.length === 0" class="no-products">
                                Không có sản phẩm
                            </div>
                            <div v-for="product in products" :key="product.id" class="product-item">
                                <div class="product-image-container">
                                    <img :src="product.image" :alt="product.ten_vi" class="product-image">
                                    <div class="product-overlay">
                                        <button class="quick-view-btn">Xem nhanh</button>
                                    </div>
                                </div>
                                <div class="product-info">
                                    <div class="product-name">{{ product.name }}</div>
                                    <div class="product-rating">
                                        <i class="icon-star"></i>
                                        <i class="icon-star"></i>
                                        <i class="icon-star"></i>
                                        <i class="icon-star"></i>
                                        <i class="icon-star-half"></i>
                                        <span class="rating-count">(15)</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="dropdown-footer">
                            <a href="#" class="view-all-link">Xem tất cả sản phẩm <i class="icon-arrow-right"></i></a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal Đăng xuất -->
    <div class="modal-overlay" v-if="showLogoutModal">
        <div class="modal">
            <div class="modal-header">
                <h2>Đăng Xuất</h2>
                <p>Bạn có chắc chắn muốn đăng xuất?</p>
            </div>
            <div class="modal-body">
                <div class="modal-icon">
                    <i>!</i>
                </div>
                <p>Sau khi đăng xuất, bạn sẽ cần đăng nhập lại để tiếp tục sử dụng tài khoản.</p>
                <div class="modal-actions">
                    <button class="btn btn-cancel" @click="closeLogoutModal">Hủy</button>
                    <button class="btn btn-logout" @click="handleLogout">Đăng Xuất</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted, onUnmounted } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

const user = ref(JSON.parse(localStorage.getItem('user') || '{}'))
const canShowProfile = user.value.role === 4
const router = useRouter()

// Lấy dữ liệu từ localStorage
const userData = ref(JSON.parse(localStorage.getItem('user') || '{}'))
const token = ref(localStorage.getItem('token') || '')

// Computed properties
const isLoggedIn = computed(() => !!token.value)
const userDisplayName = computed(() => {
    return userData.value?.ten ||
        userData.value?.username ||
        'Người dùng'
})

// Modal state
const showLogoutModal = ref(false)

// Mở modal đăng xuất
const openLogoutModal = () => {
    showLogoutModal.value = true
}

// Đóng modal đăng xuất
const closeLogoutModal = () => {
    showLogoutModal.value = false
}

// Xử lý đăng xuất
const handleLogout = () => {
    // Xóa dữ liệu localStorage
    localStorage.removeItem('token')
    localStorage.removeItem('user')
    localStorage.removeItem('loginTime')

    // Reset state
    userData.value = {}
    token.value = ''
    showLogoutModal.value = false

    // Reload page để cập nhật UI
    window.location.reload()
}

// Đóng modal khi click bên ngoài
const handleOverlayClick = (event) => {
    if (event.target.classList.contains('modal-overlay')) {
        closeLogoutModal()
    }
}

// Thêm event listener để đóng modal khi click bên ngoài
onMounted(() => {
    checkLocalStorage()
    window.addEventListener('storage', handleStorageChange)

    // Thêm event listener cho modal overlay
    const modalOverlay = document.querySelector('.modal-overlay');
    if (modalOverlay) {
        modalOverlay.addEventListener('click', handleOverlayClick);
    }
})

const showAdminButton = computed(() => {
    const userRole = userData.value?.roleEntity?.roleName?.toLowerCase() || 
                    userData.value?.role?.toString().toLowerCase() || 
                    '';
    
    // Hiển thị nút Admin nếu role là 'admin' hoặc 'mod' (moderator)
    return userRole === 'admin' || userRole === 'mod' || userRole === 'moderator';
})

onUnmounted(() => {
    window.removeEventListener('storage', handleStorageChange)

    // Remove event listener khi component unmount
    const modalOverlay = document.querySelector('.modal-overlay');
    if (modalOverlay) {
        modalOverlay.removeEventListener('click', handleOverlayClick);
    }
})

const goLogin = () => {
    router.push('/login')
}

const goRegister = () => {
    router.push('/register')
}

const goToProfile = () => {
    router.push('/admin/profile')
}

const goToProAdmin = () => {
    router.push('/admin')
}

// Kiểm tra localStorage khi component mounted
const checkLocalStorage = () => {
    const storedUser = localStorage.getItem('user')
    const storedToken = localStorage.getItem('token')

    if (storedUser) {
        try {
            userData.value = JSON.parse(storedUser)
        } catch (e) {
            console.error('Lỗi parse user data:', e)
            userData.value = {}
        }
    }

    if (storedToken) {
        token.value = storedToken
    }
}

// Lắng nghe sự thay đổi localStorage (nếu có nhiều tab)
const handleStorageChange = (event) => {
    if (event.key === 'user' || event.key === 'token') {
        checkLocalStorage()
    }
}

// Danh mục
const categories = [
    { id: 1, name: 'Danh Mục Sản Phẩm', icon: 'icon-grid' },
    { id: 2, name: 'Khuyến Mãi Hót', icon: 'icon-fire' },
    { id: 3, name: 'Hệ Thống Cửa Hàng', icon: 'icon-store' },
    { id: 4, name: 'Sản Phẩm Bán Chạy', icon: 'icon-trending-up' },
    { id: 5, name: 'Mẹo Vặt', icon: 'icon-lightbulb' },
]

// Data
const products = ref([])
const hoveredCategory = ref(null)

// Hover vào danh mục
const handleHover = async (cat) => {
    hoveredCategory.value = cat.name
    try {
        const res = await axios.get('/api/productLists/view', {
            params: { category: cat.type }
        })

        products.value = res.data.map(item => ({
            name: item.tenVi,
            photo: item.photo
        }))
    } catch (err) {
        console.error(err)
        products.value = []
    }
}

// Rời chuột
const handleLeave = () => {
    hoveredCategory.value = null
    products.value = []
}
</script>

<script>
export default {
    filters: {
        currency(value) {
            if (!value) return ''
            return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(value)
        }
    }
}
</script>

<style scoped>
/* Reset và base styles */
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

.user-info {
    display: flex;
    align-items: center;
    gap: 15px;
}

.user-welcome {
    color: #333;
    font-size: 14px;
}

.user-welcome strong {
    color: #ff6b00;
}

.user-actions {
    display: flex;
    gap: 8px;
}

.user-btn {
    padding: 6px 12px;
    border: 1px solid #ddd;
    background: white;
    border-radius: 4px;
    cursor: pointer;
    display: flex;
    align-items: center;
    gap: 6px;
    transition: all 0.3s;
    font-size: 13px;
    color: #333;
}

.user-btn:hover {
    border-color: #ff6b00;
    color: #ff6b00;
}

.logout-btn:hover {
    background-color: #ff6b00;
    color: white;
    border-color: #ff6b00;
}

/* Thêm icon logout */
.icon-logout:before {
    content: "🚪";
}

/* Responsive cho user info */
@media (max-width: 768px) {
    .user-info {
        flex-direction: column;
        gap: 8px;
        text-align: center;
    }

    .user-actions {
        justify-content: center;
    }
}

.header-container {
    background-color: white;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    overflow: visible;
    width: 100%;
    height: auto;
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

/* Top bar styles */
.top-bar {
    background-color: #f8f8f8;
    padding: 8px 20px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 13px;
    border-bottom: 1px solid #eee;
    color: #666;
}

.top-left {
    display: flex;
    align-items: center;
    gap: 15px;
}

.contact-info {
    font-weight: 500;
}

.divider {
    color: #ddd;
}

.exchange-rates {
    display: flex;
    gap: 15px;
}

.rate-item {
    font-weight: 500;
}

.top-right {
    display: flex;
    gap: 20px;
}

.top-link {
    color: #666;
    text-decoration: none;
    transition: color 0.3s;
}

.top-link:hover {
    color: #ff6b00;
}

/* Main header styles */
.main-header {
    padding: 15px 20px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    border-bottom: 1px solid #eee;
}

.logo img {
    height: 45px;
}

.nav-buttons {
    display: flex;
    gap: 15px;
}

.nav-btn {
    padding: 10px 15px;
    border: none;
    background: none;
    font-weight: bold;
    cursor: pointer;
    color: #333;
    display: flex;
    align-items: center;
    gap: 8px;
    transition: color 0.3s;
    font-size: 14px;
}

.nav-btn:hover {
    color: #ff6b00;
}

.auth-buttons {
    display: flex;
    gap: 10px;
}

.auth-btn {
    padding: 8px 15px;
    border: 1px solid #ddd;
    background: white;
    border-radius: 4px;
    cursor: pointer;
    display: flex;
    align-items: center;
    gap: 8px;
    transition: all 0.3s;
    font-size: 14px;
}

.login-btn {
    color: #333;
}

.login-btn:hover {
    border-color: #ff6b00;
    color: #ff6b00;
}

.register-btn {
    background-color: #ff6b00;
    color: white;
    border-color: #ff6b00;
}

.register-btn:hover {
    background-color: #e55a00;
    border-color: #e55a00;
}

/* Search section styles */
.search-section {
    padding: 100px;
    background-color: #f9f9f9;

    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 15px;
    overflow: visible;
    position: relative;
}

.search-title {
    font-size: 24px;
    font-weight: bold;
    color: #333;
    letter-spacing: 1px;
}

.search-container {
    display: flex;
    align-items: center;
    gap: 15px;
    width: 100%;
    max-width: 700px;
}

.search-box {
    display: flex;
    flex: 1;
}

.search-input {
    flex: 1;
    padding: 12px 15px;
    border: 2px solid #ff6b00;
    border-right: none;
    border-radius: 4px 0 0 4px;
    font-size: 16px;
    outline: none;
}

.search-btn {
    background-color: #ff6b00;
    color: white;
    border: none;
    padding: 0 20px;
    border-radius: 0 4px 4px 0;
    cursor: pointer;
    font-weight: bold;
    display: flex;
    align-items: center;
    gap: 8px;
    transition: background-color 0.3s;
}

.search-btn:hover {
    background-color: #e55a00;
}

.cart-icon {
    position: relative;
    background-color: #ff6b00;
    color: white;
    width: 45px;
    height: 45px;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    font-size: 18px;
}

.cart-count {
    position: absolute;
    top: -5px;
    right: -5px;
    background-color: #e74c3c;
    color: white;
    border-radius: 50%;
    width: 20px;
    height: 20px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 12px;
    font-weight: bold;
}

/* Categories styles */
.categories {
    display: flex;
    gap: 20px;
    flex-wrap: wrap;
    justify-content: center;
    position: static;
}

.category {
    padding: 10px 15px;
    background-color: white;
    border: 1px solid #ddd;
    border-radius: 20px;
    cursor: pointer;
    font-size: 14px;
    position: relative;
    display: flex;
    align-items: center;
    gap: 8px;
    transition: all 0.3s;
    font-weight: 500;
}

.category:hover {
    background-color: #ff6b00;
    color: white;
    border-color: #ff6b00;
}

/* Dropdown products styles */
.dropdown-products {
    position: absolute;
    top: 100%;
    left: 0;
    background: white;
    border: 1px solid #ddd;
    border-radius: 6px;
    padding: 15px;
    box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
    z-index: 9999;
    min-width: 800px;
    max-height: 500px;
    overflow-y: auto;
}

.dropdown-header {
    padding-bottom: 10px;
    margin-bottom: 15px;
    border-bottom: 1px solid #eee;
}

.dropdown-header h3 {
    color: #333;
    font-size: 18px;
}

.products-grid {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 15px;
}

.product-item {
    border: 1px solid #eee;
    border-radius: 6px;
    overflow: hidden;
    transition: all 0.3s;
    cursor: pointer;
}

.product-item:hover {
    box-shadow: 0 3px 10px rgba(0, 0, 0, 0.1);
    transform: translateY(-3px);
}

.product-image-container {
    position: relative;
    overflow: hidden;
}

.product-image {
    width: 100%;
    height: 150px;
    object-fit: cover;
    transition: transform 0.3s;
}

.product-item:hover .product-image {
    transform: scale(1.05);
}

.product-overlay {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    align-items: center;
    justify-content: center;
    opacity: 0;
    transition: opacity 0.3s;
}

.product-item:hover .product-overlay {
    opacity: 1;
}

.quick-view-btn {
    background: white;
    color: #333;
    border: none;
    padding: 8px 15px;
    border-radius: 4px;
    font-weight: bold;
    cursor: pointer;
    transition: all 0.3s;
}

.quick-view-btn:hover {
    background: #ff6b00;
    color: white;
}

.product-info {
    padding: 10px;
}

.product-name {
    font-weight: bold;
    margin-bottom: 5px;
    font-size: 14px;
    height: 40px;
    overflow: hidden;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    color: black;
}

.product-price {
    font-size: 16px;
    color: #ff6b00;
    font-weight: bold;
    margin-bottom: 5px;
}

.product-rating {
    display: flex;
    align-items: center;
    gap: 3px;
    color: #ffc107;
    font-size: 12px;
}

.rating-count {
    color: #666;
    margin-left: 5px;
}

.no-products {
    text-align: center;
    padding: 20px;
    color: #666;
    grid-column: 1 / -1;
}

.dropdown-footer {
    margin-top: 15px;
    padding-top: 10px;
    border-top: 1px solid #eee;
    text-align: center;
}

.view-all-link {
    color: #ff6b00;
    text-decoration: none;
    font-weight: bold;
    display: inline-flex;
    align-items: center;
    gap: 5px;
    transition: color 0.3s;
}

.view-all-link:hover {
    color: #e55a00;
}

/* Icon styles (giả lập) */
.icon-grid:before {
    content: "☰";
}

.icon-fire:before {
    content: "🔥";
}

.icon-store:before {
    content: "🏪";
}

.icon-trending-up:before {
    content: "📈";
}

.icon-lightbulb:before {
    content: "💡";
}

.icon-gift:before {
    content: "🎁";
}

.icon-percent:before {
    content: "％";
}

.icon-cart:before {
    content: "🛒";
}

.icon-user:before {
    content: "👤";
}

.icon-user-plus:before {
    content: "➕";
}

.icon-search:before {
    content: "🔍";
}

.icon-shopping-cart:before {
    content: "🛒";
}

.icon-star:before {
    content: "★";
}

.icon-star-half:before {
    content: "½";
}

.icon-arrow-right:before {
    content: "→";
}

/* Responsive styles */
@media (max-width: 1024px) {
    .products-grid {
        grid-template-columns: repeat(2, 1fr);
    }

    .dropdown-products {
        min-width: 600px;
    }
}

@media (max-width: 768px) {
    .top-bar {
        flex-direction: column;
        gap: 10px;
        text-align: center;
    }

    .main-header {
        flex-direction: column;
        gap: 15px;
    }

    .nav-buttons,
    .auth-buttons {
        flex-wrap: wrap;
        justify-content: center;
    }

    .search-container {
        flex-direction: column;
    }

    .cart-icon {
        align-self: flex-end;
    }

    .dropdown-products {
        min-width: 300px;
        left: -100px;
    }

    .products-grid {
        grid-template-columns: 1fr;
    }
}
.modal-overlay {
    position: fixed;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 10000;
    /* Đảm bảo luôn ở giữa */
    pointer-events: auto;
}

.modal {
    background: white;
    width: 90%;
    max-width: 450px;
    border-radius: 20px;
    overflow: hidden;
    box-shadow: 0 20px 40px rgba(0, 0, 0, 0.3);
    animation: modalAppear 0.3s ease;
    /* Căn giữa tuyệt đối */
    position: relative;
    margin: auto;
    /* Giới hạn chiều cao */
    max-height: 90vh;
}

/* Animation xuất hiện từ giữa */
@keyframes modalAppear {
    from {
        opacity: 0;
        transform: scale(0.8) translateY(-20px);
    }
    to {
        opacity: 1;
        transform: scale(1) translateY(0);
    }
}

.modal-header {
    background: linear-gradient(135deg, #6a11cb 0%, #2575fc 100%);
    color: white;
    padding: 25px 30px;
    text-align: center;
}

.modal-header h2 {
    font-size: 24px;
    margin-bottom: 5px;
    font-weight: 600;
}

.modal-header p {
    opacity: 0.9;
    font-size: 15px;
    margin: 0;
}

.modal-body {
    padding: 30px;
    text-align: center;
}

.modal-icon {
    width: 80px;
    height: 80px;
    background: #fff5f5;
    border-radius: 50%;
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 0 auto 20px;
    color: #ff6b6b;
    font-size: 36px;
    font-weight: bold;
    border: 2px dashed #ff6b6b;
}

.modal-body p {
    color: #555;
    margin-bottom: 25px;
    line-height: 1.6;
    font-size: 16px;
}

.modal-actions {
    display: flex;
    justify-content: center;
    gap: 15px;
}

.btn {
    padding: 12px 25px;
    border: none;
    border-radius: 50px;
    font-size: 16px;
    font-weight: 600;
    cursor: pointer;
    transition: all 0.3s ease;
    min-width: 120px;
}

.btn-cancel {
    background: #f8f9fa;
    color: #495057;
    border: 2px solid #e9ecef;
}

.btn-cancel:hover {
    background: #e9ecef;
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.btn-logout {
    background: linear-gradient(135deg, #ff6b6b 0%, #ff8e8e 100%);
    color: white;
    box-shadow: 0 4px 15px rgba(255, 107, 107, 0.3);
}

.btn-logout:hover {
    transform: translateY(-2px);
    box-shadow: 0 6px 20px rgba(255, 107, 107, 0.4);
}

/* Responsive cho mobile */
@media (max-width: 480px) {
    .modal {
        width: 95%;
        margin: 20px;
    }
    
    .modal-header {
        padding: 20px;
    }
    
    .modal-body {
        padding: 20px;
    }
    
    .modal-icon {
        width: 60px;
        height: 60px;
        font-size: 28px;
    }
    
    .modal-actions {
        flex-direction: column;
        gap: 10px;
    }
    
    .btn {
        width: 100%;
    }
}

/* Đảm bảo modal luôn trên cùng */
body.modal-open {
    overflow: hidden;
}

/* Hiệu ứng khi click */
.btn:active {
    transform: translateY(0);
}
</style>