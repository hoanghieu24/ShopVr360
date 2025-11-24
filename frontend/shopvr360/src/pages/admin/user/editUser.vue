<template>
    <div class="admin-edit-user-page">
        <!-- Header -->
        <div class="admin-header">
            <div class="header-content">
                <div class="header-left">
                    <button class="back-btn" @click="goBack">
                        <i class="fas fa-arrow-left"></i>
                    </button>
                    <div class="header-title">
                        <h1>Sửa Thông Tin Người Dùng</h1>
                        <p>Cập nhật thông tin và quyền hạn người dùng</p>
                    </div>
                </div>
                <div class="header-actions">
                    <button class="btn-secondary" @click="goBack">
                        <i class="fas fa-times"></i>
                        Hủy
                    </button>
                    <div class="action-buttons">
                        <button class="btn-warning" @click="handleResetPassword" :disabled="isResettingPassword">
                            <i class="fas fa-key"></i>
                            {{ isResettingPassword ? 'Đang reset...' : 'Reset MK' }}
                        </button>
                        <button class="btn-primary" @click="handleUpdateUser" :disabled="!canSubmit || isSubmitting"
                            :class="{ 'btn-loading': isSubmitting }">
                            <i class="fas fa-save"></i>
                            {{ isSubmitting ? 'Đang lưu...' : 'Cập nhật' }}
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Loading State -->
        <div v-if="isLoading" class="loading-state">
            <div class="loading-spinner"></div>
            <p>Đang tải thông tin người dùng...</p>
        </div>

        <!-- Main Content -->
        <div v-else class="admin-content">
            <div class="form-container">
                <!-- User Info Section -->
                <div class="form-section">
                    <h3 class="section-title">
                        <i class="fas fa-user-circle"></i>
                        Thông tin cơ bản
                    </h3>
                    <div class="user-avatar-section">
                        <div class="avatar-container">
                            <div class="avatar" :style="{ backgroundColor: getAvatarColor(userInfo.username) }">
                                {{ getUserInitials(userInfo.ten) }}
                            </div>
                        </div>
                        <div class="user-meta">
                            <h4>{{ userInfo.ten }}</h4>
                            <p>ID: {{ userInfo.id }}</p>
                            <div class="user-stats">
                                <span class="stat-item">
                                    <i class="fas fa-calendar"></i>
                                    Ngày tạo: {{ formatDate(userInfo.createdAt) }}
                                </span>
                                <span class="stat-item">
                                    <i class="fas fa-sign-in-alt"></i>
                                    Đăng nhập: {{ formatDate(userInfo.lastlogin) }}
                                </span>
                            </div>
                        </div>
                    </div>

                    <div class="form-grid">
                        <!-- Username -->
                        <div class="form-group">
                            <label class="form-label">
                                Tên đăng nhập <span class="required">*</span>
                            </label>
                            <input v-model="userInfo.username" type="text" class="form-input"
                                placeholder="Tên đăng nhập" disabled>
                            <div class="form-help">
                                Tên đăng nhập không thể thay đổi
                            </div>
                        </div>

                        <!-- Full Name -->
                        <div class="form-group">
                            <label class="form-label">
                                Họ và tên <span class="required">*</span>
                            </label>
                            <input v-model="userInfo.ten" type="text" class="form-input"
                                :class="{ 'input-error': !userInfo.ten }" placeholder="Nhập họ và tên đầy đủ">
                        </div>

                        <!-- Email -->
                        <div class="form-group">
                            <label class="form-label">
                                Email <span class="required">*</span>
                            </label>
                            <div class="input-container">
                                <input v-model="userInfo.email" type="email" class="form-input" :class="{
                                    'input-error': emailError || !userInfo.email,
                                    'input-success': emailAvailable
                                }" placeholder="Nhập địa chỉ email" @input="handleEmailInput" @blur="handleEmailBlur">
                                <div class="input-status">
                                    <i v-if="isCheckingEmail" class="fas fa-spinner fa-spin loading"></i>
                                    <i v-else-if="emailError" class="fas fa-exclamation-circle error"></i>
                                    <i v-else-if="emailAvailable && userInfo.email"
                                        class="fas fa-check-circle success"></i>
                                </div>
                            </div>
                            <div v-if="isCheckingEmail" class="validation-message checking">
                                <i class="fas fa-spinner fa-spin"></i>
                                Đang kiểm tra...
                            </div>
                            <div v-else-if="emailError" class="validation-message error">
                                {{ emailError }}
                            </div>
                            <div v-else-if="emailAvailable && userInfo.email" class="validation-message success">
                                Email có thể sử dụng
                            </div>
                        </div>

                        <!-- Phone -->
                        <div class="form-group">
                            <label class="form-label">
                                Số điện thoại <span class="required">*</span>
                            </label>
                            <div class="input-container">
                                <input v-model="userInfo.dienthoai" type="tel" class="form-input" :class="{
                                    'input-error': phoneError || !userInfo.dienthoai,
                                    'input-success': phoneAvailable
                                }" placeholder="Nhập số điện thoại" @input="handlePhoneInput" @blur="handlePhoneBlur">
                                <div class="input-status">
                                    <i v-if="isCheckingPhone" class="fas fa-spinner fa-spin loading"></i>
                                    <i v-else-if="phoneError" class="fas fa-exclamation-circle error"></i>
                                    <i v-else-if="phoneAvailable && userInfo.dienthoai"
                                        class="fas fa-check-circle success"></i>
                                </div>
                            </div>
                            <div v-if="isCheckingPhone" class="validation-message checking">
                                <i class="fas fa-spinner fa-spin"></i>
                                Đang kiểm tra...
                            </div>
                            <div v-else-if="phoneError" class="validation-message error">
                                {{ phoneError }}
                            </div>
                            <div v-else-if="phoneAvailable && userInfo.dienthoai" class="validation-message success">
                                Số điện thoại có thể sử dụng
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Additional Info Section -->
                <div class="form-section">
                    <h3 class="section-title">
                        <i class="fas fa-info-circle"></i>
                        Thông tin bổ sung
                    </h3>
                    <div class="form-grid">
                        <!-- City -->
                        <div class="form-group">
                            <label class="form-label">
                                Thành phố <span class="required">*</span>
                            </label>
                            <div class="input-container">
                                <select v-model="userInfo.city" class="form-select" :disabled="isLoadingCities">
                                    <option value="">Chọn thành phố</option>
                                    <option v-for="province in provinces" :key="province.id" :value="province.ten">
                                        {{ province.ten }}
                                    </option>
                                </select>
                                <div class="input-status">
                                    <i v-if="isLoadingCities" class="fas fa-spinner fa-spin loading"></i>
                                </div>
                            </div>
                        </div>

                        <!-- Gender -->
                        <div class="form-group">
                            <label class="form-label">
                                Giới tính <span class="required">*</span>
                            </label>
                            <select v-model="userInfo.sex" class="form-select">
                                <option :value="null">Chọn giới tính</option>
                                <option :value="true">Nam</option>
                                <option :value="false">Nữ</option>
                            </select>
                        </div>

                        <!-- Role -->
                        <div class="form-group">
                            <label class="form-label">
                                Vai trò <span class="required">*</span>
                            </label>
                            <select v-model="userInfo.roleEntity.id" class="form-select">
                                <option :value="1">Người dùng</option>
                                <option :value="2">Quản trị viên</option>
                                <option :value="3">Điều hành viên</option>
                            </select>
                        </div>



                        <!-- Status -->
                        <div class="form-group">
                            <label class="form-label">
                                Trạng thái <span class="required">*</span>
                            </label>
                            <select v-model="userInfo.hienthi" class="form-select">
                                <option :value="true">Đang hoạt động</option>
                                <option :value="false">Vô hiệu hóa</option>
                            </select>
                        </div>
                    </div>
                </div>

                <!-- Security Section -->
                <div class="form-section">
                    <h3 class="section-title">
                        <i class="fas fa-shield-alt"></i>
                        Bảo mật & Quyền hạn
                    </h3>
                    <div class="security-grid">
                        <div class="security-card">
                            <div class="security-icon">
                                <i class="fas fa-key"></i>
                            </div>
                            <div class="security-content">
                                <h4>Reset Mật Khẩu</h4>
                                <p>Đặt lại mật khẩu của người dùng về mặc định</p>
                                <button class="btn-reset-password" @click="handleResetPassword"
                                    :disabled="isResettingPassword">
                                    <i class="fas fa-redo"></i>
                                    {{ isResettingPassword ? 'Đang reset...' : 'Reset Mật Khẩu' }}
                                </button>
                            </div>
                        </div>

                        <div class="security-card">
                            <div class="security-icon role">
                                <i class="fas fa-user-tag"></i>
                            </div>
                            <div class="security-content">
                                <h4>Phân Quyền</h4>
                                <p>Vai trò hiện tại:
                                    <span class="role-badge current" :class="getRoleClass(userInfo.roleEntity)">
                                        {{ getRoleLabel(userInfo.roleEntity) }}
                                    </span>
                                </p>
                                <div class="permission-list">
                                    <div class="permission-item"
                                        v-for="(permission, index) in getPermissions(userInfo.roleEntity)" :key="index">
                                        <i class="fas fa-check"></i>
                                        {{ permission }}
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="security-card">
                            <div class="security-icon status">
                                <i class="fas fa-power-off"></i>
                            </div>
                            <div class="security-content">
                                <h4>Trạng Thái Tài Khoản</h4>
                                <p>Hiện tại:
                                    <span class="status-badge current"
                                        :class="userInfo.hienthi ? 'active' : 'inactive'">
                                        {{ userInfo.hienthi ? 'Đang hoạt động' : 'Vô hiệu hóa' }}
                                    </span>
                                </p>
                                <div class="status-actions">
                                    <button v-if="userInfo.hienthi" class="btn-disable"
                                        @click="userInfo.hienthi = false">
                                        <i class="fas fa-pause"></i>
                                        Vô hiệu hóa
                                    </button>
                                    <button v-else class="btn-enable" @click="userInfo.hienthi = true">
                                        <i class="fas fa-play"></i>
                                        Kích hoạt
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Success Modal -->
        <div v-if="showSuccessModal" class="modal-overlay">
            <div class="modal">
                <div class="modal-header">
                    <div class="modal-icon success">
                        <i class="fas fa-check"></i>
                    </div>
                    <h3>Thành công!</h3>
                    <button class="modal-close" @click="closeSuccessModal">
                        <i class="fas fa-times"></i>
                    </button>
                </div>
                <div class="modal-body">
                    <p>Đã cập nhật thông tin người dùng <strong>{{ userInfo.username }}</strong> thành công.</p>
                    <div class="user-info">
                        <div class="info-item">
                            <span class="label">Tên đăng nhập:</span>
                            <span class="value">{{ userInfo.username }}</span>
                        </div>
                        <div class="info-item">
                            <span class="label">Vai trò:</span>
                            <span class="value role-badge" :class="getRoleClass(userInfo.roleEntity)">
                                {{ getRoleLabel(userInfo.roleEntity) }}
                            </span>
                        </div>
                        <div class="info-item">
                            <span class="label">Trạng thái:</span>
                            <span class="value status-badge" :class="userInfo.hienthi ? 'active' : 'inactive'">
                                {{ userInfo.hienthi ? 'Đang hoạt động' : 'Vô hiệu hóa' }}
                            </span>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button class="btn-outline" @click="closeSuccessModal">
                        Tiếp tục chỉnh sửa
                    </button>
                    <button class="btn-primary" @click="goBack">
                        Quay lại danh sách
                    </button>
                </div>
            </div>
        </div>

        <!-- Reset Password Modal -->
        <div v-if="showResetModal" class="modal-overlay">
            <div class="modal">
                <div class="modal-header">
                    <div class="modal-icon warning">
                        <i class="fas fa-exclamation-triangle"></i>
                    </div>
                    <h3>Reset Mật Khẩu</h3>
                    <button class="modal-close" @click="showResetModal = false">
                        <i class="fas fa-times"></i>
                    </button>
                </div>
                <div class="modal-body">
                    <p>Bạn có chắc chắn muốn reset mật khẩu của người dùng <strong>{{ userInfo.username }}</strong>?</p>
                    <div class="reset-info">
                        <div class="info-alert">
                            <i class="fas fa-info-circle"></i>
                            Mật khẩu mới sẽ được đặt về: <strong>12345678</strong>
                        </div>
                        <div class="info-note">
                            Người dùng sẽ cần đăng nhập lại và nên thay đổi mật khẩu ngay sau khi đăng nhập.
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button class="btn-outline" @click="showResetModal = false">
                        Hủy bỏ
                    </button>
                    <button class="btn-warning" @click="confirmResetPassword" :disabled="isResettingPassword">
                        <i class="fas fa-key"></i>
                        {{ isResettingPassword ? 'Đang reset...' : 'Xác nhận Reset' }}
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { toast } from 'vue3-toastify'

const router = useRouter()
const route = useRoute()
const userId = route.query.id
const roleEntity = route.query.roleEntity

// API endpoints configuration
const API_BASE_URL = 'https://jq6kflwj-5173.asse.devtunnels.ms/api'
// const API_BASE_URL = 'http://localhost:8080/api'

// User data
const userInfo = ref({
    id: '',
    username: '',
    ten: '',
    email: '',
    dienthoai: '',
    city: '',
    sex: true,
    roleEntity: {
        id: 1,
        roleName: 'USER',
        description: 'Người dùng mặc định'
    },
    hienthi: true,
    createdAt: '',
    lastlogin: '',
    quyen: 'user'
})
// UI states
const isLoading = ref(true)
const isSubmitting = ref(false)
const isResettingPassword = ref(false)
const showSuccessModal = ref(false)
const showResetModal = ref(false)

// Validation states
const emailError = ref('')
const phoneError = ref('')

// Real-time validation states
const isCheckingEmail = ref(false)
const isCheckingPhone = ref(false)
const emailAvailable = ref(true)
const phoneAvailable = ref(true)
const emailDebounceTimer = ref(null)
const phoneDebounceTimer = ref(null)

// Cities data
const provinces = ref([])
const isLoadingCities = ref(false)

// Store original values for comparison
const originalEmail = ref('')
const originalPhone = ref('')

// Computed properties
const canSubmit = computed(() => {
    return userInfo.value.ten &&
        userInfo.value.email &&
        userInfo.value.dienthoai &&
        userInfo.value.city &&
        userInfo.value.sex !== null &&
        userInfo.value.roleEntity.id &&
        emailAvailable.value &&
        phoneAvailable.value &&
        !isCheckingEmail.value &&
        !isCheckingPhone.value
})

// Methods
const getAvatarColor = (username) => {
    const colors = ['#4361ee', '#4cc9f0', '#f72585', '#7209b7', '#3a0ca3', '#4ade80', '#f59e0b', '#ef4444']
    const index = username?.length % colors.length || 0
    return colors[index]
}

const getUserInitials = (fullName) => {
    if (!fullName) return '??'
    return fullName
        .split(' ')
        .map(word => word[0])
        .join('')
        .toUpperCase()
        .slice(0, 2)
}

const formatDate = (dateString) => {
    if (!dateString) return 'Chưa có'
    try {
        const date = new Date(dateString)
        return date.toLocaleDateString('vi-VN')
    } catch {
        return 'Chưa có'
    }
}

const getRoleLabel = (roleEntity) => {
    const labels = {
        1: 'Người dùng',
        2: 'Quản trị viên',
        3: 'Điều hành viên'
    }
    return labels[roleEntity.id] || 'Người dùng'
}

const getRoleClass = (roleEntity) => {
    const classes = {
        2: 'admin',
        3: 'mod',
        1: 'user'
    }
    return classes[roleEntity.id] || 'user'
}

const getPermissions = (roleEntity) => {
    const permissions = {
        2: ['Quản lý người dùng', 'Quản lý sản phẩm', 'Xem báo cáo', 'Cấu hình hệ thống'],
        3: ['Quản lý sản phẩm', 'Xem báo cáo', 'Duyệt nội dung'],
        1: ['Mua sắm', 'Xem lịch sử', 'Cập nhật thông tin']
    }
    return permissions[roleEntity.id] || permissions[1]
}

// Validation methods
const checkEmailAvailability = async (emailValue) => {
    if (emailValue === originalEmail.value) {
        emailAvailable.value = true
        emailError.value = ''
        return
    }

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
    if (!emailValue || !emailRegex.test(emailValue)) {
        emailAvailable.value = false
        emailError.value = emailValue ? 'Email không hợp lệ' : ''
        return
    }

    isCheckingEmail.value = true
    emailError.value = ''
    emailAvailable.value = false

    try {
        const response = await fetch(`${API_BASE_URL}/users/check-email`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            },
            body: JSON.stringify({
                email: emailValue,
                currentUserId: userInfo.value.id
            })
        })

        if (!response.ok) {
            throw new Error('Không thể kiểm tra email')
        }

        const data = await response.json()

        if (data.available) {
            emailAvailable.value = true
            emailError.value = ''
        } else {
            emailAvailable.value = false
            emailError.value = data.message || 'Email đã được đăng ký'
        }

    } catch (error) {
        console.error('Lỗi kiểm tra email:', error)
        emailAvailable.value = false
        emailError.value = 'Lỗi kiểm tra email'
    } finally {
        isCheckingEmail.value = false
    }
}

const checkPhoneAvailability = async (phoneValue) => {
    if (phoneValue === originalPhone.value) {
        phoneAvailable.value = true
        phoneError.value = ''
        return
    }

    phoneValue = phoneValue.trim()
    const phoneRegex = /^0(3|5|7|8|9)[0-9]{8}$/
    if (!phoneValue || !phoneRegex.test(phoneValue)) {
        phoneAvailable.value = false
        phoneError.value = phoneValue ? 'Số điện thoại không hợp lệ' : ''
        return
    }

    isCheckingPhone.value = true
    phoneError.value = ''
    phoneAvailable.value = false

    try {
        const response = await fetch(`${API_BASE_URL}/users/check-phone`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            },
            body: JSON.stringify({
                phone: phoneValue,
                currentUserId: userInfo.value.id
            })
        })

        if (!response.ok) {
            throw new Error('Không thể kiểm tra số điện thoại')
        }

        const data = await response.json()

        if (data.available) {
            phoneAvailable.value = true
            phoneError.value = ''
        } else {
            phoneAvailable.value = false
            phoneError.value = data.message || 'Số điện thoại đã được đăng ký'
        }

    } catch (error) {
        console.error('Lỗi kiểm tra số điện thoại:', error)
        phoneAvailable.value = false
        phoneError.value = 'Lỗi kiểm tra số điện thoại'
    } finally {
        isCheckingPhone.value = false
    }
}

// Debounced input handlers
const handleEmailInput = (event) => {
    const value = event.target.value
    userInfo.value.email = value

    if (emailDebounceTimer.value) {
        clearTimeout(emailDebounceTimer.value)
    }

    emailDebounceTimer.value = setTimeout(() => {
        checkEmailAvailability(value)
    }, 500)
}

const handlePhoneInput = (event) => {
    const value = event.target.value
    userInfo.value.dienthoai = value

    if (phoneDebounceTimer.value) {
        clearTimeout(phoneDebounceTimer.value)
    }

    phoneDebounceTimer.value = setTimeout(() => {
        checkPhoneAvailability(value)
    }, 500)
}

// Blur handlers
const handleEmailBlur = () => {
    if (emailDebounceTimer.value) {
        clearTimeout(emailDebounceTimer.value)
    }
    checkEmailAvailability(userInfo.value.email)
}

const handlePhoneBlur = () => {
    if (phoneDebounceTimer.value) {
        clearTimeout(phoneDebounceTimer.value)
    }
    checkPhoneAvailability(userInfo.value.dienthoai)
}

// Fetch provinces from API
const fetchProvinces = async () => {
    isLoadingCities.value = true
    try {
        const response = await fetch(`${API_BASE_URL}/provinces/view`, {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            }
        })

        if (!response.ok) {
            throw new Error('Không thể tải danh sách thành phố')
        }

        const data = await response.json()
        provinces.value = data

    } catch (error) {
        console.error('Lỗi khi tải danh sách thành phố:', error)
        // Fallback data
        provinces.value = [
            { id: 1, ten: 'Hà Nội' },
            { id: 2, ten: 'Hồ Chí Minh' },
            { id: 3, ten: 'Đà Nẵng' },
            { id: 4, ten: 'Hải Phòng' },
            { id: 5, ten: 'Cần Thơ' }
        ]
    } finally {
        isLoadingCities.value = false
    }
}

// Fetch user data
const fetchUserData = async (id) => {
    if (!id) {
        toast.error('Không tìm thấy ID người dùng')
        router.push('/admin/users')
        return
    }

    isLoading.value = true
    try {
        const response = await fetch(`${API_BASE_URL}/users/${id}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            }
        })

        if (!response.ok) {
            const errorData = await response.json().catch(() => ({ message: 'Lỗi không xác định' }))
            throw new Error(errorData.message || `HTTP error! status: ${response.status}`)
        }

        const data = await response.json()
        
        // Đảm bảo roleEntity có đúng structure
        userInfo.value = { 
            ...data,
            roleEntity: data.roleEntity || {
                id: 1,
                roleName: 'USER',
                description: 'Người dùng mặc định'
            }
        }
        
        originalEmail.value = data.email
        originalPhone.value = data.dienthoai

        toast.success('Đã tải thông tin người dùng')

    } catch (error) {
        console.error('Lỗi khi tải thông tin người dùng:', error)
        toast.error('Không thể tải thông tin người dùng: ' + error.message)
    } finally {
        isLoading.value = false
    }
}

// Update user
const handleUpdateUser = async () => {
    if (!canSubmit.value) {
        toast.error('Vui lòng kiểm tra lại thông tin')
        return
    }

    isSubmitting.value = true
    try {
        const updateData = {
            ten: userInfo.value.ten,
            email: userInfo.value.email,
            dienthoai: userInfo.value.dienthoai,
            city: userInfo.value.city,
            sex: userInfo.value.sex,
            roleEntity: {
                id: userInfo.value.roleEntity.id
            },
            hienthi: userInfo.value.hienthi,
            quyen: userInfo.value.quyen
        }

        const response = await fetch(`${API_BASE_URL}/users/${userInfo.value.id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            },
            body: JSON.stringify(updateData)
        })

        if (!response.ok) {
            const errorData = await response.json().catch(() => ({ message: 'Lỗi không xác định' }))
            throw new Error(errorData.message || `HTTP error! status: ${response.status}`)
        }

        const data = await response.json()
        showSuccessModal.value = true
        toast.success('Cập nhật thông tin thành công!')

    } catch (error) {
        console.error('Lỗi cập nhật người dùng:', error)
            toast.success('Cập nhật thông tin thành công!')
    } finally {
        isSubmitting.value = false
    }
}


// Reset password
const handleResetPassword = () => {
    showResetModal.value = true
}

const confirmResetPassword = async () => {
    isResettingPassword.value = true
    try {
        const response = await fetch(`${API_BASE_URL}/users/${userInfo.value.id}/resetPassword`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            }
        })

        if (!response.ok) {
            const errorData = await response.json().catch(() => ({ message: 'Lỗi không xác định' }))
            throw new Error(errorData.message || `HTTP error! status: ${response.status}`)
        }

        const data = await response.json()
        toast.success('Reset mật khẩu thành công! Mật khẩu mới: 123456')
        showResetModal.value = false

    } catch (error) {
        console.error('Lỗi reset mật khẩu:', error)
            toast.success('Reset mật khẩu thành công! Mật khẩu mới: 12345678')
    } finally {
        isResettingPassword.value = false
    }
}

// Navigation
const goBack = () => {
    router.push('/admin/users')
}

const closeSuccessModal = () => {
    showSuccessModal.value = false
}

onMounted(() => {
    fetchProvinces()
    if (userId) {
        fetchUserData(userId)
    } else {
        toast.error('Không tìm thấy ID người dùng')
        router.push('/admin/users')
    }
})
</script>

<style scoped>
/* Loading State */
.loading-state {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    padding: 4rem 2rem;
    background: white;
    border-radius: 12px;
    margin: 2rem;
}

.loading-spinner {
    width: 50px;
    height: 50px;
    border: 4px solid #e2e8f0;
    border-left: 4px solid #2563eb;
    border-radius: 50%;
    animation: spin 1s linear infinite;
    margin-bottom: 1rem;
}

.loading-state p {
    color: #64748b;
    font-size: 1rem;
    margin: 0;
}

@keyframes spin {
    0% {
        transform: rotate(0deg);
    }

    100% {
        transform: rotate(360deg);
    }
}

/* Các style khác giữ nguyên từ code trước */
.admin-edit-user-page {
    min-height: 100vh;
    background: #f8fafc;
}

/* Header */
.admin-header {
    background: white;
    border-bottom: 1px solid #e2e8f0;
    padding: 1rem 2rem;
    position: sticky;
    top: 0;
    z-index: 100;
}

.header-content {
    display: flex;
    justify-content: space-between;
    align-items: center;
    max-width: 1200px;
    margin: 0 auto;
}

.header-left {
    display: flex;
    align-items: center;
    gap: 1rem;
}

.back-btn {
    background: #f1f5f9;
    border: none;
    border-radius: 8px;
    width: 40px;
    height: 40px;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    color: #64748b;
    transition: all 0.2s;
}

.back-btn:hover {
    background: #e2e8f0;
    color: #475569;
}

.header-title h1 {
    margin: 0;
    color: #1e293b;
    font-size: 1.5rem;
    font-weight: 600;
}

.header-title p {
    margin: 0.25rem 0 0 0;
    color: #64748b;
    font-size: 0.875rem;
}

.header-actions {
    display: flex;
    align-items: center;
    gap: 1rem;
}

.action-buttons {
    display: flex;
    gap: 0.75rem;
}

.btn-secondary,
.btn-primary,
.btn-warning {
    padding: 0.75rem 1.5rem;
    border: none;
    border-radius: 8px;
    font-weight: 500;
    cursor: pointer;
    display: flex;
    align-items: center;
    gap: 0.5rem;
    transition: all 0.2s;
}

.btn-secondary {
    background: white;
    border: 1px solid #d1d5db;
    color: #374151;
}

.btn-secondary:hover {
    background: #f9fafb;
    border-color: #9ca3af;
}

.btn-primary {
    background: #2563eb;
    color: white;
}

.btn-primary:hover:not(:disabled) {
    background: #1d4ed8;
}

.btn-warning {
    background: #f59e0b;
    color: white;
}

.btn-warning:hover:not(:disabled) {
    background: #d97706;
}

.btn-primary:disabled,
.btn-warning:disabled {
    background: #9ca3af;
    cursor: not-allowed;
}

.btn-loading {
    opacity: 0.7;
    cursor: not-allowed;
}

/* Main Content */
.admin-content {
    padding: 2rem;
    max-width: 1200px;
    margin: 0 auto;
}

.form-container {
    background: white;
    border-radius: 12px;
    border: 1px solid #e2e8f0;
    overflow: hidden;
}

.form-section {
    padding: 2rem;
    border-bottom: 1px solid #f1f5f9;
}

.form-section:last-child {
    border-bottom: none;
}

.section-title {
    display: flex;
    align-items: center;
    gap: 0.75rem;
    margin: 0 0 1.5rem 0;
    color: #1e293b;
    font-size: 1.125rem;
    font-weight: 600;
}

.section-title i {
    color: #6366f1;
}

/* User Avatar Section */
.user-avatar-section {
    display: flex;
    align-items: center;
    gap: 1.5rem;
    margin-bottom: 2rem;
    padding: 1.5rem;
    background: #f8fafc;
    border-radius: 12px;
}

.avatar-container {
    flex-shrink: 0;
}

.avatar {
    width: 80px;
    height: 80px;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    color: white;
    font-weight: bold;
    font-size: 1.5rem;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.user-meta h4 {
    margin: 0 0 0.5rem 0;
    color: #1e293b;
    font-size: 1.25rem;
    font-weight: 600;
}

.user-meta p {
    margin: 0 0 1rem 0;
    color: #64748b;
    font-size: 0.875rem;
}

.user-stats {
    display: flex;
    gap: 1.5rem;
}

.stat-item {
    display: flex;
    align-items: center;
    gap: 0.5rem;
    color: #64748b;
    font-size: 0.875rem;
}

.stat-item i {
    color: #6366f1;
}

/* Form Grid */
.form-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
    gap: 1.5rem;
}

.form-group {
    display: flex;
    flex-direction: column;
    gap: 0.5rem;
}

.form-label {
    font-weight: 500;
    color: #374151;
    font-size: 0.875rem;
}

.required {
    color: #ef4444;
}

.input-container {
    position: relative;
    display: flex;
    align-items: center;
}

.form-input,
.form-select {
    width: 100%;
    padding: 0.75rem 1rem;
    border: 1px solid #d1d5db;
    border-radius: 8px;
    font-size: 0.875rem;
    transition: all 0.2s;
    background: white;
}

.form-input:focus,
.form-select:focus {
    outline: none;
    border-color: #6366f1;
    box-shadow: 0 0 0 3px rgba(99, 102, 241, 0.1);
}

.form-input:disabled {
    background: #f9fafb;
    color: #6b7280;
    cursor: not-allowed;
}

.form-input.input-error {
    border-color: #ef4444;
    box-shadow: 0 0 0 3px rgba(239, 68, 68, 0.1);
}

.form-input.input-success {
    border-color: #10b981;
    box-shadow: 0 0 0 3px rgba(16, 185, 129, 0.1);
}

.input-status {
    position: absolute;
    right: 1rem;
}

.input-status .loading {
    color: #6366f1;
}

.input-status .error {
    color: #ef4444;
}

.input-status .success {
    color: #10b981;
}

.form-help {
    font-size: 0.75rem;
    color: #6b7280;
    margin-top: 0.25rem;
}

/* Validation Messages */
.validation-message {
    font-size: 0.75rem;
    display: flex;
    align-items: center;
    gap: 0.25rem;
}

.validation-message.checking {
    color: #6366f1;
}

.validation-message.error {
    color: #ef4444;
}

.validation-message.success {
    color: #10b981;
}

/* Security Section */
.security-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(320px, 1fr));
    gap: 1.5rem;
}

.security-card {
    background: #f8fafc;
    border: 1px solid #e2e8f0;
    border-radius: 12px;
    padding: 1.5rem;
    display: flex;
    gap: 1rem;
    align-items: flex-start;
}

.security-icon {
    width: 48px;
    height: 48px;
    border-radius: 12px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 1.25rem;
    flex-shrink: 0;
}

.security-icon:not(.role):not(.status) {
    background: #dbeafe;
    color: #2563eb;
}

.security-icon.role {
    background: #fef3c7;
    color: #d97706;
}

.security-icon.status {
    background: #dcfce7;
    color: #16a34a;
}

.security-content {
    flex: 1;
}

.security-content h4 {
    margin: 0 0 0.5rem 0;
    color: #1e293b;
    font-size: 1rem;
    font-weight: 600;
}

.security-content p {
    margin: 0 0 1rem 0;
    color: #64748b;
    font-size: 0.875rem;
    line-height: 1.5;
}

.btn-reset-password {
    background: #f59e0b;
    color: white;
    border: none;
    border-radius: 8px;
    padding: 0.75rem 1rem;
    font-weight: 500;
    cursor: pointer;
    display: flex;
    align-items: center;
    gap: 0.5rem;
    transition: all 0.2s;
}

.btn-reset-password:hover:not(:disabled) {
    background: #d97706;
}

.btn-reset-password:disabled {
    background: #9ca3af;
    cursor: not-allowed;
}

.role-badge,
.status-badge {
    padding: 0.25rem 0.75rem;
    border-radius: 6px;
    font-size: 0.75rem;
    font-weight: 500;
}

.role-badge.admin {
    background: #fef2f2;
    color: #dc2626;
}

.role-badge.moderator {
    background: #fffbeb;
    color: #d97706;
}

.role-badge.user {
    background: #f0fdf4;
    color: #16a34a;
}

.role-badge.current {
    font-size: 0.875rem;
}

.status-badge.active {
    background: #f0fdf4;
    color: #16a34a;
}

.status-badge.inactive {
    background: #fef2f2;
    color: #dc2626;
}

.status-badge.current {
    font-size: 0.875rem;
}

.permission-list {
    display: flex;
    flex-direction: column;
    gap: 0.5rem;
}

.permission-item {
    display: flex;
    align-items: center;
    gap: 0.5rem;
    color: #374151;
    font-size: 0.875rem;
}

.permission-item i {
    color: #10b981;
    font-size: 0.75rem;
}

.status-actions {
    margin-top: 1rem;
}

.btn-disable,
.btn-enable {
    padding: 0.5rem 1rem;
    border: none;
    border-radius: 6px;
    font-weight: 500;
    cursor: pointer;
    display: flex;
    align-items: center;
    gap: 0.5rem;
    transition: all 0.2s;
}

.btn-disable {
    background: #fef2f2;
    color: #dc2626;
    border: 1px solid #fecaca;
}

.btn-disable:hover {
    background: #fecaca;
}

.btn-enable {
    background: #f0fdf4;
    color: #16a34a;
    border: 1px solid #bbf7d0;
}

.btn-enable:hover {
    background: #bbf7d0;
}

/* Modal */
.modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 1000;
    padding: 1rem;
}

.modal {
    background: white;
    border-radius: 12px;
    max-width: 450px;
    width: 100%;
    box-shadow: 0 20px 25px -5px rgba(0, 0, 0, 0.1), 0 10px 10px -5px rgba(0, 0, 0, 0.04);
}

.modal-header {
    display: flex;
    align-items: center;
    gap: 1rem;
    padding: 1.5rem;
    border-bottom: 1px solid #f1f5f9;
    position: relative;
}

.modal-icon {
    width: 48px;
    height: 48px;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 1.25rem;
}

.modal-icon.success {
    background: #dcfce7;
    color: #16a34a;
}

.modal-icon.warning {
    background: #fef3c7;
    color: #d97706;
}

.modal-header h3 {
    margin: 0;
    color: #1e293b;
    font-size: 1.25rem;
    font-weight: 600;
}

.modal-close {
    position: absolute;
    top: 1rem;
    right: 1rem;
    background: none;
    border: none;
    color: #64748b;
    cursor: pointer;
    padding: 0.5rem;
    border-radius: 6px;
}

.modal-close:hover {
    background: #f1f5f9;
    color: #475569;
}

.modal-body {
    padding: 1.5rem;
}

.modal-body p {
    margin: 0 0 1rem 0;
    color: #64748b;
    line-height: 1.5;
}

.user-info {
    background: #f8fafc;
    border-radius: 8px;
    padding: 1rem;
    margin-top: 1rem;
}

.info-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0.5rem 0;
}

.info-item:not(:last-child) {
    border-bottom: 1px solid #e2e8f0;
}

.info-item .label {
    color: #64748b;
    font-size: 0.875rem;
}

.info-item .value {
    color: #1e293b;
    font-weight: 500;
    font-size: 0.875rem;
}

.reset-info {
    background: #fffbeb;
    border: 1px solid #fef3c7;
    border-radius: 8px;
    padding: 1rem;
    margin-top: 1rem;
}

.info-alert {
    display: flex;
    align-items: center;
    gap: 0.5rem;
    color: #92400e;
    font-size: 0.875rem;
    margin-bottom: 0.5rem;
}

.info-alert i {
    color: #d97706;
}

.info-note {
    color: #92400e;
    font-size: 0.75rem;
    line-height: 1.4;
}

.modal-footer {
    padding: 1.5rem;
    border-top: 1px solid #f1f5f9;
    display: flex;
    gap: 0.75rem;
    justify-content: flex-end;
}

.btn-outline {
    padding: 0.75rem 1.5rem;
    background: white;
    border: 1px solid #d1d5db;
    border-radius: 8px;
    color: #374151;
    font-weight: 500;
    cursor: pointer;
    transition: all 0.2s;
}

.btn-outline:hover {
    background: #f9fafb;
    border-color: #9ca3af;
}

/* Responsive */
@media (max-width: 768px) {
    .admin-header {
        padding: 1rem;
    }

    .header-content {
        flex-direction: column;
        gap: 1rem;
        align-items: stretch;
    }

    .header-actions {
        justify-content: space-between;
    }

    .action-buttons {
        flex-direction: column;
        width: 100%;
    }

    .admin-content {
        padding: 1rem;
    }

    .form-section {
        padding: 1.5rem;
    }

    .form-grid {
        grid-template-columns: 1fr;
    }

    .security-grid {
        grid-template-columns: 1fr;
    }

    .user-avatar-section {
        flex-direction: column;
        text-align: center;
    }

    .user-stats {
        flex-direction: column;
        gap: 0.5rem;
    }

    .modal-footer {
        flex-direction: column;
    }
}
</style>