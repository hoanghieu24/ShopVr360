<template>
    <!-- Loading Screen -->
    <div v-if="isLoading" class="loading-screen">
        <div class="loading-container">
            <div class="loading-logo">
                <img src="../client/image/image.png" alt="" >
                <span>SHOPVR360</span>
            </div>
            <div class="loading-animation">
                <div class="loading-spinner"></div>
                <div class="loading-dots">
                    <div class="dot"></div>
                    <div class="dot"></div>
                    <div class="dot"></div>
                </div>
            </div>
            <p class="loading-text">Đang tải hệ thống...</p>
        </div>
    </div>

    <!-- Main Login Content -->
    <div v-else class="login-wrapper">
        <div class="login-container">
            <div class="login-header">
                <div class="logo">
                <img src="../client/image/image.png" alt="" >
                    <span>SHOPVR360</span>
                </div>
                <h1>Đăng Nhập Hệ Thống</h1>
                <p>Vui lòng nhập thông tin đăng nhập của bạn</p>
            </div>

            <form class="login-form" @submit.prevent="handleLogin">
                <div class="input-group">
                    <div class="input-icon">
                        <i class="fas fa-envelope"></i>
                    </div>
                    <input v-model="username" type="text" placeholder="Tên đăng nhập" required>
                </div>

                <div class="input-group password-group">
                    <div class="input-icon">
                        <i class="fas fa-lock"></i>
                    </div>
                    <input v-model="password" :type="showPassword ? 'text' : 'password'" placeholder="Mật khẩu" required
                        class="password-input">
                    <button type="button" class="password-toggle" @click="showPassword = !showPassword">
                        <i :class="showPassword ? 'fas fa-eye-slash' : 'fas fa-eye'"></i>
                    </button>
                </div>


                <div class="form-options">
                    <label class="remember-me">
                        <input type="checkbox" v-model="rememberMe">
                        <span class="checkmark"></span>
                        Ghi nhớ đăng nhập
                    </label>
                    <a href="#" class="forgot-password">Quên mật khẩu?</a>
                </div>

                <button type="submit" class="login-btn">
                    <span>Đăng Nhập</span>
                    <i class="fas fa-arrow-right"></i>
                </button>

                <!-- Cải tiến phần Quay trở lại trang chủ -->
                <div class="back-home-section">
                    <router-link to="/" class="back-home-link">
                        <i class="fas fa-home"></i>
                        <span>Quay trở lại trang chủ</span>
                    </router-link>
                </div>

                <div class="divider">
                    <span>Hoặc đăng nhập với</span>
                </div>

                <div class="social-login">
                    <button type="button" class="social-btn google">
                        <i class="fab fa-google"></i>
                        Google
                    </button>
                    <button type="button" class="social-btn microsoft">
                        <i class="fab fa-microsoft"></i>
                        Microsoft
                    </button>
                </div>
            </form>

            <div class="login-footer">
                <p>Chưa có tài khoản?
                    <router-link to="/register">Đăng ký ngay</router-link>
                </p>
            </div>

        </div>

        <div class="background-decoration">
            <div class="decoration-circle circle-1"></div>
            <div class="decoration-circle circle-2"></div>
            <div class="decoration-circle circle-3"></div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { toast } from 'vue3-toastify'
import axios from 'axios'
import { useRouter } from 'vue-router'

const router = useRouter()

const username = ref('')
const showPassword = ref(false)
const password = ref('')
const rememberMe = ref(false)
const isLoading = ref(true)

const handleLogin = async () => {
    try {
        console.log('=== BẮT ĐẦU ĐĂNG NHẬP ===')
        console.log('Username:', username.value)

        // 1️⃣ Login lấy token
        const loginRes = await axios.post('/api/users/login', {
            username: username.value,
            password: password.value,
            rememberMe: rememberMe.value
        })

        const token = loginRes.data.data
        if (!token) throw new Error('Không nhận được token từ server')

        localStorage.setItem('token', token)
        console.log('✅ Đã lưu token:', token)

        // 2️⃣ Lấy thông tin người dùng
        const userRes = await axios.get('/api/users/me', {
            headers: { Authorization: `Bearer ${token}` }
        })

        const user = userRes.data
        localStorage.setItem('user', JSON.stringify(user))

        // 3️⃣ Lưu thời gian đăng nhập
        const loginTime = new Date().toLocaleString('vi-VN')
        localStorage.setItem('loginTime', loginTime)

        toast.success("Đăng nhập thành công!")

        setTimeout(() => {
            router.push('/')
        }, 1000)

    } catch (err) {
        console.error('=== LỖI ĐĂNG NHẬP ===', err)
        console.error('Error response:', err.response?.data)

        const message = err.response?.data?.message || ''

        // 🔥 Bắt riêng lỗi tài khoản bị vô hiệu hoá
        if (message.includes("vô hiệu hóa") || message.includes("vô hiệu hoá")) {
            toast.error("Tài khoản đã bị vô hiệu hoá. Vui lòng liên hệ admin!")
            return
        }

        // ❌ Lỗi chung
        toast.error("Sai tài khoản hoặc mật khẩu, kiểm tra lại nha!")
    }
}



onMounted(() => {
    // Hiển thị loading trong 2.5 giây
    setTimeout(() => {
        isLoading.value = false
    }, 2500)
})

const goRegister = () => {
    router.push('/register')
}
</script>

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
    animation: fadeInUp 0.8s ease-out;
}

.loading-logo {
    display: flex;
    align-items: center;
    justify-content: center;
    gap : 5px;
    font-size: 2rem;
    font-weight: bold;
    filter: brightness(0) saturate(100%) invert(80%) sepia(30%) saturate(800%) hue-rotate(165deg) brightness(110%) contrast(105%);
}


.loading-logo img {
    margin: 0;
    padding: 15;
    width: 58px;      /* tuỳ ông chỉnh cho đẹp */
    height: auto;
    display: block;   /* xoá khoảng trống inline */
}



.loading-animation {
    margin-bottom: 30px;
    position: relative;
}

.loading-spinner {
    width: 80px;
    height: 80px;
    border: 4px solid rgba(255, 255, 255, 0.3);
    border-top: 4px solid #ffffff;
    border-radius: 50%;
    animation: spin 1.5s linear infinite;
    margin: 0 auto 20px;
}

.loading-dots {
    display: flex;
    justify-content: center;
    gap: 8px;
    margin-top: 20px;
}

.dot {
    width: 12px;
    height: 12px;
    background: white;
    border-radius: 50%;
    animation: bounce 1.4s infinite ease-in-out;
}

.dot:nth-child(1) {
    animation-delay: -0.32s;
}

.dot:nth-child(2) {
    animation-delay: -0.16s;
}

.dot:nth-child(3) {
    animation-delay: 0s;
}

.loading-text {
    font-size: 1.1rem;
    opacity: 0.9;
    animation: pulse 2s infinite;
    margin-top: 20px;
    
}

/* Animations */
@keyframes spin {
    0% {
        transform: rotate(0deg);
    }

    100% {
        transform: rotate(360deg);
    }
}

@keyframes bounce {

    0%,
    80%,
    100% {
        transform: scale(0.8);
        opacity: 0.5;
    }

    40% {
        transform: scale(1);
        opacity: 1;
    }
}

@keyframes fadeInUp {
    from {
        opacity: 0;
        transform: translateY(30px);
    }

    to {
        opacity: 1;
        transform: translateY(0);
    }
}

@keyframes pulse {

    0%,
    100% {
        opacity: 0.7;
    }

    50% {
        opacity: 1;
    }
}

/* Existing styles remain the same */
.login-wrapper {
    min-height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    position: relative;
    overflow: hidden;
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    animation: fadeIn 0.8s ease-out;
}

@keyframes fadeIn {
    from {
        opacity: 0;
    }

    to {
        opacity: 1;
    }
}

.login-container {
    background: rgba(255, 255, 255, 0.95);
    backdrop-filter: blur(10px);
    padding: 40px;
    border-radius: 20px;
    box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
    width: 100%;
    max-width: 450px;
    position: relative;
    z-index: 10;
    animation: slideUp 0.6s ease-out;
}

@keyframes slideUp {
    from {
        opacity: 0;
        transform: translateY(30px);
    }

    to {
        opacity: 1;
        transform: translateY(0);
    }
}

.login-header {
    text-align: center;
    margin-bottom: 30px;
}

.logo {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 10px;
    margin-bottom: 10px;
    color: #667eea;
    font-weight: bold;
    font-size: 1.5rem;
}

.logo img {
    margin: 0;
    padding: 15;
    width: 58px;      /* tuỳ ông chỉnh cho đẹp */
    height: auto;
    display: block;   /* xoá khoảng trống inline */
}


.login-header h1 {
    color: #2d3748;
    margin-bottom: 10px;
    font-size: 1.8rem;
    font-weight: 600;
}

.login-header p {
    color: #718096;
    font-size: 0.9rem;
}

.login-form {
    display: flex;
    flex-direction: column;
    gap: 20px;
}

.input-group {
    position: relative;
    display: flex;
    align-items: center;
    background: #f7fafc;
    border: 2px solid #e2e8f0;
    border-radius: 12px;
    transition: all 0.3s ease;
}

.input-group:focus-within {
    border-color: #667eea;
    box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
}

.input-icon {
    padding: 0 15px;
    color: #a0aec0;
}

.input-group input {
    flex: 1;
    padding: 15px 0;
    border: none;
    background: transparent;
    outline: none;
    font-size: 1rem;
}

.password-group {
    position: relative;
    display: flex;
    align-items: center;
}

.password-input {
    flex: 1;
    padding: 15px 45px 15px 0; 
    border: none;
    background: transparent;
    outline: none;
    font-size: 1rem;
    width: 100%;
}

.password-toggle {
    position: absolute;
    right: 15px;
    background: none;
    border: none;
    cursor: pointer;

    color: #ccc !important;     /* màu icon */
    font-size: 25px;            /* icon vừa vặn */
    padding: 8px;               /* gọn */
    
    display: flex;
    align-items: center;
    justify-content: center;

    transition: color 0.3s ease;
    z-index: 10;
    border-radius: 50%;
}



.password-toggle:hover {
    color: #667eea;
     color: #555 !important
}

/* Đảm bảo input-group vẫn hoạt động bình thường */
.input-group {
    position: relative;
    display: flex;
    align-items: center;
    background: #f7fafc;
    border: 2px solid #e2e8f0;
    border-radius: 12px;
    transition: all 0.3s ease;
}

.input-group:focus-within {
    border-color: #667eea;
    box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
}

.input-icon {
    padding: 0 15px;
    color: #a0aec0;
}

.input-group input:not(.password-input) {
    flex: 1;
    padding: 15px 0;
    border: none;
    background: transparent;
    outline: none;
    font-size: 1rem;
}

.form-options {
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 0.9rem;
}

.remember-me {
    display: flex;
    align-items: center;
    gap: 8px;
    cursor: pointer;
    color: #4a5568;
}

.remember-me input {
    display: none;
}

.checkmark {
    width: 18px;
    height: 18px;
    border: 2px solid #cbd5e0;
    border-radius: 4px;
    display: flex;
    align-items: center;
    justify-content: center;
    transition: all 0.3s ease;
}

.remember-me input:checked+.checkmark {
    background: #667eea;
    border-color: #667eea;
}

.remember-me input:checked+.checkmark:after {
    content: '✓';
    color: white;
    font-size: 12px;
}

.forgot-password {
    color: #667eea;
    text-decoration: none;
    transition: color 0.3s ease;
}

.forgot-password:hover {
    color: #5a67d8;
    text-decoration: underline;
}

.login-btn {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    color: white;
    border: none;
    padding: 15px;
    border-radius: 12px;
    font-size: 1rem;
    font-weight: 600;
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 10px;
    transition: all 0.3s ease;
    box-shadow: 0 4px 15px rgba(102, 126, 234, 0.3);
}

.login-btn:hover {
    transform: translateY(-2px);
    box-shadow: 0 6px 20px rgba(102, 126, 234, 0.4);
}

/* Phần Quay trở lại trang chủ được cải tiến */
.back-home-section {
    text-align: center;
    margin: 10px 0;
}

.back-home-link {
    display: inline-flex;
    align-items: center;
    gap: 8px;
    color: #667eea;
    text-decoration: none;
    font-weight: 500;
    padding: 10px 20px;
    border: 2px solid transparent;
    border-radius: 25px;
    transition: all 0.3s ease;
    background: rgba(102, 126, 234, 0.1);
}

.back-home-link:hover {
    background: rgba(102, 126, 234, 0.2);
    border-color: #667eea;
    transform: translateY(-1px);
    box-shadow: 0 4px 12px rgba(102, 126, 234, 0.2);
}

.back-home-link i {
    font-size: 0.9rem;
    transition: transform 0.3s ease;
}

.back-home-link:hover i {
    transform: translateX(-2px);
}

.divider {
    text-align: center;
    position: relative;
    margin: 5px 0;
    color: #a0aec0;
    font-size: 0.9rem;
}

.divider:before {
    content: '';
    position: absolute;
    top: 50%;
    left: 0;
    right: 0;
    height: 1px;
    background: #e2e8f0;
}

.divider span {
    background: white;
    padding: 0 15px;
    position: relative;
}

.social-login {
    display: flex;
    gap: 15px;
}

.social-btn {
    flex: 1;
    padding: 12px;
    border: 2px solid #e2e8f0;
    border-radius: 10px;
    background: white;
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 8px;
    cursor: pointer;
    transition: all 0.3s ease;
    font-weight: 500;
}

.social-btn:hover {
    border-color: #667eea;
    transform: translateY(-1px);
}

.social-btn.google {
    color: #db4437;
}

.social-btn.microsoft {
    color: #0078d4;
}

.login-footer {
    text-align: center;
    margin-top: 25px;
    color: #718096;
    font-size: 0.9rem;
}

.login-footer a {
    color: #667eea;
    text-decoration: none;
    font-weight: 500;
}

.login-footer a:hover {
    text-decoration: underline;
}

.background-decoration {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    pointer-events: none;
}

.decoration-circle {
    position: absolute;
    border-radius: 50%;
    background: rgba(255, 255, 255, 0.1);
}

.circle-1 {
    width: 200px;
    height: 200px;
    top: 10%;
    right: 10%;
}

.circle-2 {
    width: 150px;
    height: 150px;
    bottom: 20%;
    left: 5%;
}

.circle-3 {
    width: 100px;
    height: 100px;
    bottom: 10%;
    right: 20%;
}

@media (max-width: 480px) {
    .login-container {
        margin: 20px;
        padding: 30px 25px;
    }

    .form-options {
        flex-direction: column;
        gap: 15px;
        align-items: flex-start;
    }

    .social-login {
        flex-direction: column;
    }

    .back-home-link {
        padding: 8px 16px;
        font-size: 0.9rem;
    }

    .loading-logo {
        font-size: 1.5rem;
    }

    .loading-logo i {
        font-size: 2rem;
    }

    .loading-spinner {
        width: 60px;
        height: 60px;
    }
}
</style>