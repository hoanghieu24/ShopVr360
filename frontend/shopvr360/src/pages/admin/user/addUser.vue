<template>
  <div class="admin-add-user-page">
    <!-- Header -->
    <div class="admin-header">
      <div class="header-content">
        <div class="header-left">
          <button class="back-btn" @click="goBack">
            <i class="fas fa-arrow-left"></i>
          </button>
          <div class="header-title">
            <h1>Thêm Người Dùng Mới</h1>
            <p>Tạo tài khoản người dùng mới cho hệ thống</p>
          </div>
        </div>
        <div class="header-actions">
          <button class="btn-secondary" @click="goBack">
            <i class="fas fa-times"></i>
            Hủy
          </button>
          <button class="btn-primary" @click="handleAddUser" :disabled="!canSubmit" :class="{ 'btn-loading': isSubmitting }">
            <i class="fas fa-save"></i>
            {{ isSubmitting ? 'Đang lưu...' : 'Lưu người dùng' }}
          </button>
        </div>
      </div>
    </div>

    <!-- Main Content -->
    <div class="admin-content">
      <div class="form-container">
        <div class="form-section">
          <h3 class="section-title">
            <i class="fas fa-user-circle"></i>
            Thông tin cơ bản
          </h3>
          <div class="form-grid">
            <!-- Username -->
            <div class="form-group">
              <label class="form-label">
                Tên đăng nhập <span class="required">*</span>
              </label>
              <div class="input-container">
                <input 
                  v-model="username" 
                  type="text" 
                  class="form-input"
                  :class="{ 
                    'input-error': usernameError, 
                    'input-success': usernameAvailable 
                  }"
                  placeholder="Nhập tên đăng nhập"
                  @input="handleUsernameInput"
                  @blur="handleUsernameBlur"
                >
                <div class="input-status">
                  <i v-if="isCheckingUsername" class="fas fa-spinner fa-spin loading"></i>
                  <i v-else-if="usernameError" class="fas fa-exclamation-circle error"></i>
                  <i v-else-if="usernameAvailable && username" class="fas fa-check-circle success"></i>
                </div>
              </div>
              <div v-if="isCheckingUsername" class="validation-message checking">
                <i class="fas fa-spinner fa-spin"></i>
                Đang kiểm tra...
              </div>
              <div v-else-if="usernameError" class="validation-message error">
                {{ usernameError }}
              </div>
              <div v-else-if="usernameAvailable && username" class="validation-message success">
                Tên đăng nhập có thể sử dụng
              </div>
            </div>

            <!-- Full Name -->
            <div class="form-group">
              <label class="form-label">
                Họ và tên <span class="required">*</span>
              </label>
              <input 
                v-model="ten" 
                type="text" 
                class="form-input"
                placeholder="Nhập họ và tên đầy đủ"
              >
            </div>

            <!-- Email -->
            <div class="form-group">
              <label class="form-label">
                Email <span class="required">*</span>
              </label>
              <div class="input-container">
                <input 
                  v-model="email" 
                  type="email" 
                  class="form-input"
                  :class="{ 
                    'input-error': emailError, 
                    'input-success': emailAvailable 
                  }"
                  placeholder="Nhập địa chỉ email"
                  @input="handleEmailInput"
                  @blur="handleEmailBlur"
                >
                <div class="input-status">
                  <i v-if="isCheckingEmail" class="fas fa-spinner fa-spin loading"></i>
                  <i v-else-if="emailError" class="fas fa-exclamation-circle error"></i>
                  <i v-else-if="emailAvailable && email" class="fas fa-check-circle success"></i>
                </div>
              </div>
              <div v-if="isCheckingEmail" class="validation-message checking">
                <i class="fas fa-spinner fa-spin"></i>
                Đang kiểm tra...
              </div>
              <div v-else-if="emailError" class="validation-message error">
                {{ emailError }}
              </div>
              <div v-else-if="emailAvailable && email" class="validation-message success">
                Email có thể sử dụng
              </div>
            </div>

            <!-- Phone -->
            <div class="form-group">
              <label class="form-label">
                Số điện thoại <span class="required">*</span>
              </label>
              <div class="input-container">
                <input 
                  v-model="dienthoai" 
                  type="tel" 
                  class="form-input"
                  :class="{ 
                    'input-error': phoneError, 
                    'input-success': phoneAvailable 
                  }"
                  placeholder="Nhập số điện thoại"
                  @input="handlePhoneInput"
                  @blur="handlePhoneBlur"
                >
                <div class="input-status">
                  <i v-if="isCheckingPhone" class="fas fa-spinner fa-spin loading"></i>
                  <i v-else-if="phoneError" class="fas fa-exclamation-circle error"></i>
                  <i v-else-if="phoneAvailable && dienthoai" class="fas fa-check-circle success"></i>
                </div>
              </div>
              <div v-if="isCheckingPhone" class="validation-message checking">
                <i class="fas fa-spinner fa-spin"></i>
                Đang kiểm tra...
              </div>
              <div v-else-if="phoneError" class="validation-message error">
                {{ phoneError }}
              </div>
              <div v-else-if="phoneAvailable && dienthoai" class="validation-message success">
                Số điện thoại có thể sử dụng
              </div>
            </div>
          </div>
        </div>

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
                <select 
                  v-model="city" 
                  class="form-select"
                  :disabled="isLoadingCities"
                >
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
              <select v-model="sex" class="form-select">
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
              <select v-model="quyen" class="form-select">
                <option value="user">Người dùng</option>
                <option value="moderator">Điều hành</option>
                <option value="admin">Quản trị viên</option>
              </select>
            </div>

            <!-- Status -->
            <div class="form-group">
              <label class="form-label">
                Trạng thái <span class="required">*</span>
              </label>
              <select v-model="hienthi" class="form-select">
                <option :value="true">Đang hoạt động</option>
                <option :value="false">Vô hiệu hóa</option>
              </select>
            </div>
          </div>
        </div>

        <div class="form-section">
          <h3 class="section-title">
            <i class="fas fa-lock"></i>
            Bảo mật
          </h3>
          <div class="form-grid">
            <!-- Password -->
            <div class="form-group">
              <label class="form-label">
                Mật khẩu <span class="required">*</span>
              </label>
              <div class="input-container">
                <input 
                  v-model="password" 
                  :type="showPassword ? 'text' : 'password'" 
                  class="form-input"
                  placeholder="Nhập mật khẩu"
                  @input="validatePassword"
                >
                <button 
                  type="button" 
                  class="password-toggle"
                  @click="showPassword = !showPassword"
                >
                  <i :class="showPassword ? 'fas fa-eye-slash' : 'fas fa-eye'"></i>
                </button>
              </div>
              <div v-if="password" class="password-strength">
                <div class="strength-bar">
                  <div class="strength-fill" :class="passwordStrength"></div>
                </div>
                <span class="strength-text" :class="passwordStrength">
                  {{ getPasswordStrengthText() }}
                </span>
              </div>
            </div>

            <!-- Confirm Password -->
            <div class="form-group">
              <label class="form-label">
                Xác nhận mật khẩu <span class="required">*</span>
              </label>
              <div class="input-container">
                <input 
                  v-model="confirmPassword" 
                  :type="showConfirmPassword ? 'text' : 'password'" 
                  class="form-input"
                  :class="{ 'input-error': !passwordsMatch && confirmPassword }"
                  placeholder="Nhập lại mật khẩu"
                  @input="validatePasswordMatch"
                >
                <button 
                  type="button" 
                  class="password-toggle"
                  @click="showConfirmPassword = !showConfirmPassword"
                >
                  <i :class="showConfirmPassword ? 'fas fa-eye-slash' : 'fas fa-eye'"></i>
                </button>
              </div>
              <div v-if="password && confirmPassword && !passwordsMatch" class="validation-message error">
                Mật khẩu xác nhận không khớp
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
          <p>Đã thêm người dùng <strong>{{ username }}</strong> thành công vào hệ thống.</p>
          <div class="user-info">
            <div class="info-item">
              <span class="label">Tên đăng nhập:</span>
              <span class="value">{{ username }}</span>
            </div>
            <div class="info-item">
              <span class="label">Vai trò:</span>
              <span class="value role-badge" :class="getRoleClass(quyen)">
                {{ getRoleLabel(quyen) }}
              </span>
            </div>
            <div class="info-item">
              <span class="label">Trạng thái:</span>
              <span class="value status-badge" :class="hienthi ? 'active' : 'inactive'">
                {{ hienthi ? 'Đang hoạt động' : 'Vô hiệu hóa' }}
              </span>
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <button class="btn-outline" @click="resetForm">
            Thêm người dùng khác
          </button>
          <button class="btn-primary" @click="goBack">
            Quay lại danh sách
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { toast } from 'vue3-toastify'

const router = useRouter()

// Form data
const username = ref('')
const ten = ref('')
const email = ref('')
const dienthoai = ref('')
const city = ref('')
const sex = ref(true)
const quyen = ref('user')
const hienthi = ref(true)
const password = ref('')
const confirmPassword = ref('')

// UI states
const showPassword = ref(false)
const showConfirmPassword = ref(false)
const isSubmitting = ref(false)
const showSuccessModal = ref(false)

// Validation states
const usernameError = ref('')
const phoneError = ref('')
const emailError = ref('')
const passwordsMatch = ref(true)

// Real-time validation states
const isCheckingUsername = ref(false)
const isCheckingEmail = ref(false)
const isCheckingPhone = ref(false)
const usernameAvailable = ref(false)
const emailAvailable = ref(false)
const phoneAvailable = ref(false)
const usernameDebounceTimer = ref(null)
const emailDebounceTimer = ref(null)
const phoneDebounceTimer = ref(null)

// Cities data
const provinces = ref([])
const isLoadingCities = ref(false)

// Computed properties
const passwordStrength = computed(() => {
  const strength = calculatePasswordStrength(password.value)
  if (strength < 2) return 'weak'
  if (strength < 4) return 'medium'
  return 'strong'
})

const isFormValid = computed(() => {
  return username.value &&
    ten.value &&
    email.value &&
    dienthoai.value &&
    city.value &&
    sex.value !== null &&
    password.value &&
    confirmPassword.value &&
    passwordsMatch.value
})

const canSubmit = computed(() => {
  return isFormValid.value &&
    usernameAvailable.value &&
    emailAvailable.value &&
    phoneAvailable.value &&
    !isCheckingUsername.value &&
    !isCheckingEmail.value &&
    !isCheckingPhone.value
})

// Methods
const calculatePasswordStrength = (pass) => {
  let strength = 0
  if (pass.length >= 8) strength++
  if (pass.match(/[a-z]/) && pass.match(/[A-Z]/)) strength++
  if (pass.match(/\d/)) strength++
  if (pass.match(/[^a-zA-Z\d]/)) strength++
  return strength
}

const getPasswordStrengthText = () => {
  switch (passwordStrength.value) {
    case 'weak': return 'Mật khẩu yếu'
    case 'medium': return 'Mật khẩu trung bình'
    case 'strong': return 'Mật khẩu mạnh'
    default: return ''
  }
}

const validatePassword = () => {
  validatePasswordMatch()
}

const validatePasswordMatch = () => {
  passwordsMatch.value = password.value === confirmPassword.value
}

// Real-time validation methods
const checkUsernameAvailability = async (usernameValue) => {
  if (!usernameValue || usernameValue.length < 3) {
    usernameAvailable.value = false
    usernameError.value = usernameValue ? 'Tên đăng nhập phải có ít nhất 3 ký tự' : ''
    return
  }

  isCheckingUsername.value = true
  usernameError.value = ''
  usernameAvailable.value = false

  try {
    const response = await fetch('/api/users/check-username', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ username: usernameValue })
    })

    if (!response.ok) {
      throw new Error('Không thể kiểm tra tên đăng nhập')
    }

    const data = await response.json()

    if (data.available) {
      usernameAvailable.value = true
      usernameError.value = ''
    } else {
      usernameAvailable.value = false
      usernameError.value = data.message || 'Tên đăng nhập đã tồn tại'
    }

  } catch (error) {
    console.error('Lỗi kiểm tra username:', error)
    usernameAvailable.value = false
    usernameError.value = 'Lỗi kiểm tra tên đăng nhập'
  } finally {
    isCheckingUsername.value = false
  }
}

const checkEmailAvailability = async (emailValue) => {
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
    const response = await fetch('/api/users/check-email', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ email: emailValue })
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
    const response = await fetch('/api/users/check-phone', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ phone: phoneValue })
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
const handleUsernameInput = (event) => {
  const value = event.target.value
  username.value = value

  if (usernameDebounceTimer.value) {
    clearTimeout(usernameDebounceTimer.value)
  }

  usernameDebounceTimer.value = setTimeout(() => {
    checkUsernameAvailability(value)
  }, 500)
}

const handleEmailInput = (event) => {
  const value = event.target.value
  email.value = value

  if (emailDebounceTimer.value) {
    clearTimeout(emailDebounceTimer.value)
  }

  emailDebounceTimer.value = setTimeout(() => {
    checkEmailAvailability(value)
  }, 500)
}

const handlePhoneInput = (event) => {
  const value = event.target.value
  dienthoai.value = value

  if (phoneDebounceTimer.value) {
    clearTimeout(phoneDebounceTimer.value)
  }

  phoneDebounceTimer.value = setTimeout(() => {
    checkPhoneAvailability(value)
  }, 500)
}

// Blur handlers for immediate validation
const handleUsernameBlur = () => {
  if (usernameDebounceTimer.value) {
    clearTimeout(usernameDebounceTimer.value)
  }
  checkUsernameAvailability(username.value)
}

const handleEmailBlur = () => {
  if (emailDebounceTimer.value) {
    clearTimeout(emailDebounceTimer.value)
  }
  checkEmailAvailability(email.value)
}

const handlePhoneBlur = () => {
  if (phoneDebounceTimer.value) {
    clearTimeout(phoneDebounceTimer.value)
  }
  checkPhoneAvailability(dienthoai.value)
}

// Fetch provinces from API
const fetchProvinces = async () => {
  isLoadingCities.value = true
  try {
    const response = await fetch('/api/provinces/view')
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
      { id: 5, ten: 'Cần Thơ' },
      { id: 6, ten: 'An Giang' },
      { id: 7, ten: 'Bà Rịa - Vũng Tàu' },
      { id: 8, ten: 'Bắc Giang' },
      { id: 9, ten: 'Bắc Kạn' },
      { id: 10, ten: 'Bạc Liêu' }
    ]
    toast.error('Không thể tải danh sách thành phố. Đang sử dụng dữ liệu mặc định.')
  } finally {
    isLoadingCities.value = false
  }
}

// Role helper functions
const getRoleLabel = (quyen) => {
  const labels = {
    'admin': 'Quản trị viên',
    'moderator': 'Điều hành viên',
    'user': 'Người dùng'
  }
  return labels[quyen] || 'Người dùng'
}

const getRoleClass = (quyen) => {
  const classes = {
    'admin': 'admin',
    'moderator': 'moderator',
    'user': 'user'
  }
  return classes[quyen] || 'user'
}

// Main form submission
const handleAddUser = async () => {
  if (!canSubmit.value) {
    toast.error('Vui lòng kiểm tra lại thông tin người dùng')
    return
  }

  isSubmitting.value = true
  try {
    const userData = {
      username: username.value,
      password: password.value,
      email: email.value,
      ten: ten.value,
      dienthoai: dienthoai.value,
      city: city.value,
      sex: sex.value,
      quyen: quyen.value,
      hienthi: hienthi.value
    }

    console.log('Gửi dữ liệu người dùng:', userData)

    const response = await fetch('/api/users/register', {
      method: 'POST',
      headers: { 
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('token') || 'admin-token'}`
      },
      body: JSON.stringify(userData)
    })

    if (!response.ok) {
      const errorData = await response.json().catch(() => ({ message: 'Lỗi không xác định' }))
      throw new Error(errorData.message || `HTTP error! status: ${response.status}`)
    }

    const data = await response.json()
    console.log('Phản hồi từ server:', data)

    showSuccessModal.value = true
    toast.success('Đã thêm người dùng thành công!')

  } catch (error) {
    console.error('Lỗi thêm người dùng:', error)
    
    if (error.message.includes('Network') || error.message.includes('Failed to fetch')) {
      toast.error('Lỗi kết nối mạng. Vui lòng kiểm tra lại kết nối.')
    } else if (error.message.includes('401') || error.message.includes('403')) {
      toast.error('Bạn không có quyền thực hiện thao tác này.')
    } else {
      toast.error(error.message || 'Có lỗi xảy ra khi thêm người dùng')
    }
  } finally {
    isSubmitting.value = false
  }
}

// Navigation
const goBack = () => {
  router.push('/admin/users')
}

const closeSuccessModal = () => {
  showSuccessModal.value = false
  goBack()
}

const resetForm = () => {
  username.value = ''
  ten.value = ''
  email.value = ''
  dienthoai.value = ''
  city.value = ''
  sex.value = true
  quyen.value = 'user'
  hienthi.value = true
  password.value = ''
  confirmPassword.value = ''
  
  usernameError.value = ''
  phoneError.value = ''
  emailError.value = ''
  passwordsMatch.value = true
  
  usernameAvailable.value = false
  emailAvailable.value = false
  phoneAvailable.value = false
  
  showSuccessModal.value = false
  
  toast.info('Form đã được reset. Bạn có thể thêm người dùng mới.')
}

onMounted(() => {
  fetchProvinces()
})
</script>

<style scoped>
.admin-add-user-page {
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
  gap: 0.75rem;
}

.btn-secondary, .btn-primary {
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

.btn-primary:disabled {
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

.form-input, .form-select {
  width: 100%;
  padding: 0.75rem 1rem;
  border: 1px solid #d1d5db;
  border-radius: 8px;
  font-size: 0.875rem;
  transition: all 0.2s;
  background: white;
}

.form-input:focus, .form-select:focus {
  outline: none;
  border-color: #6366f1;
  box-shadow: 0 0 0 3px rgba(99, 102, 241, 0.1);
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

.password-toggle {
  position: absolute;
  right: 1rem;
  background: none;
  border: none;
  color: #6b7280;
  cursor: pointer;
  padding: 0.25rem;
}

.password-toggle:hover {
  color: #374151;
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

/* Password Strength */
.password-strength {
  margin-top: 0.5rem;
}

.strength-bar {
  height: 4px;
  background: #e5e7eb;
  border-radius: 2px;
  overflow: hidden;
  margin-bottom: 0.25rem;
}

.strength-fill {
  height: 100%;
  border-radius: 2px;
  transition: all 0.3s ease;
}

.strength-fill.weak {
  background: #ef4444;
  width: 33%;
}

.strength-fill.medium {
  background: #f59e0b;
  width: 66%;
}

.strength-fill.strong {
  background: #10b981;
  width: 100%;
}

.strength-text {
  font-size: 0.75rem;
  font-weight: 500;
}

.strength-text.weak {
  color: #ef4444;
}

.strength-text.medium {
  color: #f59e0b;
}

.strength-text.strong {
  color: #10b981;
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
  max-width: 400px;
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

.role-badge, .status-badge {
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

.status-badge.active {
  background: #f0fdf4;
  color: #16a34a;
}

.status-badge.inactive {
  background: #fef2f2;
  color: #dc2626;
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
    justify-content: flex-end;
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
  
  .modal-footer {
    flex-direction: column;
  }
}
</style>