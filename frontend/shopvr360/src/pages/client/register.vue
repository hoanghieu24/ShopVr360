<template>
    <!-- Loading Screen -->
    <div v-if="isLoading" class="loading-screen">
        <div class="loading-container">
            <div class="loading-logo">
                <img src="../client/image/image.png" alt="">
                <span>ShopVR360</span>
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

    <!-- Main Register Content -->
    <div v-else class="register-wrapper">
        <div class="register-container">
            <div class="register-header">
                <div class="logo">
                    <img src="../client/image/image.png" alt="">
                    <span>ShopVR360</span>
                </div>
                <h1>Tạo Tài Khoản Mới</h1>
                <p>Đăng ký để trải nghiệm đầy đủ tính năng</p>
            </div>

            <form class="register-form" @submit.prevent="handleRegister">
                <!-- Username -->
                <div class="input-group">
                    <div class="input-icon">
                        <i class="fas fa-user"></i>
                    </div>
                    <input v-model="username" type="text" placeholder="Tên đăng nhập" required
                        :class="{ 'input-error': usernameError || isCheckingUsername, 'input-success': usernameAvailable }"
                        @input="handleUsernameInput" @blur="handleUsernameBlur">
                    <div v-if="isCheckingUsername" class="loading-icon">
                        <i class="fas fa-spinner fa-spin"></i>
                    </div>
                    <div v-else-if="usernameError" class="error-icon">
                        <i class="fas fa-exclamation-circle"></i>
                    </div>
                    <div v-else-if="usernameAvailable && username" class="success-icon">
                        <i class="fas fa-check-circle"></i>
                    </div>
                </div>
                <div v-if="isCheckingUsername" class="validation-message checking">
                    <i class="fas fa-spinner fa-spin"></i>
                    Đang kiểm tra tên đăng nhập...
                </div>
                <div v-else-if="usernameError" class="error-message">
                    <i class="fas fa-exclamation-circle"></i>
                    {{ usernameError }}
                </div>
                <div v-else-if="usernameAvailable && username" class="success-message">
                    <i class="fas fa-check-circle"></i>
                    Tên đăng nhập có thể sử dụng
                </div>

                <!-- Full Name -->
                <div class="input-group">
                    <div class="input-icon">
                        <i class="fas fa-id-card"></i>
                    </div>
                    <input v-model="ten" type="text" placeholder="Tên đầy đủ" required>
                </div>

                <!-- Email -->
                <div class="input-group">
                    <div class="input-icon">
                        <i class="fas fa-envelope"></i>
                    </div>
                    <input v-model="email" type="email" placeholder="Email đăng ký" required
                        :class="{ 'input-error': emailError || isCheckingEmail, 'input-success': emailAvailable }"
                        @input="handleEmailInput" @blur="handleEmailBlur">
                    <div v-if="isCheckingEmail" class="loading-icon">
                        <i class="fas fa-spinner fa-spin"></i>
                    </div>
                    <div v-else-if="emailError" class="error-icon">
                        <i class="fas fa-exclamation-circle"></i>
                    </div>
                    <div v-else-if="emailAvailable && email" class="success-icon">
                        <i class="fas fa-check-circle"></i>
                    </div>
                </div>
                <div v-if="isCheckingEmail" class="validation-message checking">
                    <i class="fas fa-spinner fa-spin"></i>
                    Đang kiểm tra email...
                </div>
                <div v-else-if="emailError" class="error-message">
                    <i class="fas fa-exclamation-circle"></i>
                    {{ emailError }}
                </div>
                <div v-else-if="emailAvailable && email" class="success-message">
                    <i class="fas fa-check-circle"></i>
                    Email có thể sử dụng
                </div>

                <!-- Phone -->
                <div class="input-group">
                    <div class="input-icon">
                        <i class="fas fa-envelope"></i>
                    </div>
                    <input v-model="dienthoai" type="tel" placeholder="Số điện thoại" required
                        :class="{ 'input-error': phoneError || isCheckingPhone, 'input-success': phoneAvailable }"
                        @input="handlePhoneInput" @blur="handlePhoneBlur">
                    <div v-if="isCheckingPhone" class="loading-icon">
                        <i class="fas fa-spinner fa-spin"></i>
                    </div>
                    <div v-else-if="phoneError" class="error-icon">
                        <i class="fas fa-exclamation-circle"></i>
                    </div>
                    <div v-else-if="phoneAvailable && dienthoai" class="success-icon">
                        <i class="fas fa-check-circle"></i>
                    </div>
                </div>
                <div v-if="isCheckingPhone" class="validation-message checking">
                    <i class="fas fa-spinner fa-spin"></i>
                    Đang kiểm tra số điện thoại...
                </div>
                <div v-else-if="phoneError" class="error-message">
                    <i class="fas fa-exclamation-circle"></i>
                    {{ phoneError }}
                </div>
                <div v-else-if="phoneAvailable && dienthoai" class="success-message">
                    <i class="fas fa-check-circle"></i>
                    Số điện thoại có thể sử dụng
                </div>

                <!-- City -->
                <div class="input-group">
                    <div class="input-icon">
                        <i class="fas fa-city"></i>
                    </div>
                    <select v-model="city" class="city-select" required :disabled="isLoadingCities">
                        <option value="">Chọn thành phố</option>
                        <option v-for="province in provinces" :key="province.id" :value="province.ten">
                            {{ province.ten }}
                        </option>
                    </select>
                    <div v-if="isLoadingCities" class="loading-icon">
                        <i class="fas fa-spinner fa-spin"></i>
                    </div>
                </div>

                <!-- Gender -->
                <div class="input-group">
                    <div class="input-icon">
                        <i class="fas fa-venus-mars"></i>
                    </div>
                    <select v-model="sex" class="gender-select" required>
                        <option :value="null">Chọn giới tính</option>
                        <option :value="true">Nam</option>
                        <option :value="false">Nữ</option>
                    </select>
                </div>

                <!-- Password -->
                <div class="input-group">
                    <div class="input-icon">
                        <i class="fas fa-lock"></i>
                    </div>
                    <input v-model="password" :type="showPassword ? 'text' : 'password'" placeholder="Mật khẩu" required
                        @input="validatePassword">
                    <button type="button" class="password-toggle" @click="showPassword = !showPassword">
                        <i :class="showPassword ? 'fas fa-eye-slash' : 'fas fa-eye'"></i>
                    </button>
                </div>

                <!-- Confirm Password -->
                <div class="input-group">
                    <div class="input-icon">
                        <i class="fas fa-lock"></i>
                    </div>
                    <input v-model="confirmPassword" :type="showConfirmPassword ? 'text' : 'password'"
                        placeholder="Xác nhận mật khẩu" required @input="validatePasswordMatch">
                    <button type="button" class="password-toggle" @click="showConfirmPassword = !showConfirmPassword">
                        <i :class="showConfirmPassword ? 'fas fa-eye-slash' : 'fas fa-eye'"></i>
                    </button>
                </div>

                <!-- Password Strength & Match Validation -->
                <div class="validation-messages">
                    <div v-if="password" class="password-strength">
                        <div class="strength-bar">
                            <div class="strength-fill" :class="passwordStrength"></div>
                        </div>
                        <span class="strength-text" :class="passwordStrength">
                            {{ getPasswordStrengthText() }}
                        </span>
                    </div>
                    <div v-if="password && confirmPassword && !passwordsMatch" class="error-message">
                        <i class="fas fa-exclamation-circle"></i>
                        Mật khẩu xác nhận không khớp
                    </div>
                </div>

                <!-- Terms Agreement -->
                <div class="form-options">
                    <label class="agree-terms">
                        <input type="checkbox" v-model="agreeTerms" required>
                        <span class="checkmark"></span>
                        Tôi đồng ý với <a href="#">Điều khoản dịch vụ</a> và <a href="#">Chính sách bảo mật</a>
                    </label>
                </div>

                <!-- Newsletter -->
                <div class="form-options">
                    <label class="newsletter">
                        <input type="checkbox" v-model="subscribeNewsletter">
                        <span class="checkmark"></span>
                        Đăng ký nhận bản tin khuyến mãi
                    </label>
                </div>

                <!-- Submit Button -->
                <button type="submit" class="register-btn" :class="{ 'btn-loading': isRegistering }"
                    :disabled="!canSubmit">
                    <span v-if="!isRegistering">Đăng Ký Tài Khoản</span>
                    <span v-else class="loading-text">
                        <i class="fas fa-spinner fa-spin"></i>
                        Đang xử lý...
                    </span>
                </button>

                <!-- Back to Home -->
                <div class="back-home-section">
                    <router-link to="/" class="back-home-link">
                        <i class="fas fa-home"></i>
                        <span>Quay trở lại trang chủ</span>
                    </router-link>
                </div>

                <!-- Social Login -->
                <div class="divider">
                    <span>Hoặc đăng ký với</span>
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

            <!-- Login Link -->
            <div class="register-footer">
                <p>Đã có tài khoản? <router-link to="/login">Đăng nhập ngay</router-link></p>
            </div>
        </div>

        <!-- Background Decoration -->
        <div class="background-decoration">
            <div class="decoration-circle circle-1"></div>
            <div class="decoration-circle circle-2"></div>
            <div class="decoration-circle circle-3"></div>
        </div>
    </div>

    <!-- Verification Modal -->
    <div v-if="showVerificationModal" class="modal-overlay">
        <div class="verification-modal">
            <div class="modal-header">
                <h2>Xác Thực Tài Khoản</h2>
                <button class="close-btn" @click="closeVerificationModal">
                    <i class="fas fa-times"></i>
                </button>
            </div>

            <div class="modal-body">
                <div class="verification-icon">
                    <i class="fas fa-envelope-circle-check"></i>
                </div>

                <p class="verification-text">
                    Mã xác thực đã được gửi đến email: <strong>{{ email }}</strong>
                </p>

                <div class="code-input-group">
                    <input v-for="(digit, index) in verificationCode" :key="index" v-model="verificationCode[index]"
                        type="text" maxlength="1" class="code-input" :class="{ 'error': codeError }"
                        @input="handleCodeInput(index, $event)" @keydown="handleCodeKeydown(index, $event)"
                        @paste="handleCodePaste" ref="codeInputs">
                </div>

                <div v-if="codeError" class="error-message modal-error">
                    <i class="fas fa-exclamation-circle"></i>
                    {{ codeError }}
                </div>

                <div class="timer-section">
                    <p class="timer-text" :class="{ 'expired': timeLeft === 0 }">
                        Mã sẽ hết hạn sau:
                        <span class="timer">{{ formatTime(timeLeft) }}</span>
                    </p>
                </div>

                <div class="resend-section">
                    <button class="resend-btn" :disabled="!canResend" @click="resendVerificationCode">
                        <span v-if="!isResending">Gửi lại mã</span>
                        <span v-else class="loading-text">
                            <i class="fas fa-spinner fa-spin"></i>
                            Đang gửi...
                        </span>
                    </button>
                </div>
            </div>

            <div class="modal-footer">
                <button class="cancel-btn" @click="closeVerificationModal">
                    Hủy
                </button>
                <button class="verify-btn" :class="{ 'btn-loading': isVerifying }" @click="verifyCode"
                    :disabled="!isCodeComplete || isVerifying">
                    <span v-if="!isVerifying">Xác Thực</span>
                    <span v-else class="loading-text">
                        <i class="fas fa-spinner fa-spin"></i>
                        Đang xác thực...
                    </span>
                </button>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted, nextTick, watch } from 'vue'
import { useRouter } from 'vue-router'
import { toast } from 'vue3-toastify'

const router = useRouter()

// Form data - khớp với API
const username = ref('')
const ten = ref('')
const email = ref('')
const dienthoai = ref('')
const city = ref('')
const sex = ref(true)
const password = ref('')
const confirmPassword = ref('')
const agreeTerms = ref(false)
const subscribeNewsletter = ref(false)
const userEntity = ref({ id: 1 }) 

// UI states
const showPassword = ref(false)
const showConfirmPassword = ref(false)
const isLoading = ref(true)
const isRegistering = ref(false)

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

// Verification Modal States
const showVerificationModal = ref(false)
const verificationCode = ref(['', '', '', '', '', ''])
const isVerifying = ref(false)
const isResending = ref(false)
const codeError = ref('')
const timeLeft = ref(300) // 5 minutes
const canResend = ref(false)
const codeInputs = ref([])

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
        agreeTerms.value &&
        passwordsMatch.value
})

const canSubmit = computed(() => {
    return isFormValid.value &&
        usernameAvailable.value &&
        emailAvailable.value &&
        !isCheckingUsername.value &&
        !isCheckingEmail.value &&
        !isCheckingPhone.value
})

const isCodeComplete = computed(() => {
    return verificationCode.value.every(digit => digit !== '')
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
    passwordsMatch.value.length  >= 8 
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

        const data = await response.json()

        if (response.ok) {
            if (data.available) {
                usernameAvailable.value = true
                usernameError.value = ''
            } else {
                usernameAvailable.value = false
                usernameError.value = data.message || 'Tên đăng nhập đã tồn tại'
            }
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
    // Basic email validation
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

        const data = await response.json()

        if (response.ok) {
            if (data.available) {
                emailAvailable.value = true
                emailError.value = ''
            } else {
                emailAvailable.value = false
                emailError.value = data.message || 'Email đã được đăng ký'
            }
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
        phoneError.value = phoneValue ? 'Số điện thoại không hợp lệ (định dạng: 09xxxxxxxx, 03xxxxxxxx)' : ''
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

        const data = await response.json()

        if (response.ok) {
            if (data.available) {
                phoneAvailable.value = true
                phoneError.value = ''
            } else {
                phoneAvailable.value = false
                phoneError.value = data.message || 'Số điện thoại đã được đăng ký'
            }
        }

    } catch (error) {
        console.error('Lỗi kiểm tra số điện thoại:', error)
        phoneAvailable.value = false
        phoneError.value = 'Lỗi kiểm tra số điện thoại'
    } finally {
        isCheckingPhone.value = false
    }
}

// Sửa hàm handlePhoneInput
const handlePhoneInput = (event) => {
    const value = event.target.value
    dienthoai.value = value // Sửa thành dienthoai.value

    // Clear previous timer
    if (phoneDebounceTimer.value) {
        clearTimeout(phoneDebounceTimer.value)
    }

    // Set new timer with debounce
    phoneDebounceTimer.value = setTimeout(() => {
        checkPhoneAvailability(value)
    }, 500) // 500ms debounce
}

// Debounced input handlers
const handleUsernameInput = (event) => {
    const value = event.target.value
    username.value = value

    // Clear previous timer
    if (usernameDebounceTimer.value) {
        clearTimeout(usernameDebounceTimer.value)
    }

    // Set new timer with debounce
    usernameDebounceTimer.value = setTimeout(() => {
        checkUsernameAvailability(value)
    }, 500) // 500ms debounce
}

const handleEmailInput = (event) => {
    const value = event.target.value
    email.value = value

    // Clear previous timer
    if (emailDebounceTimer.value) {
        clearTimeout(emailDebounceTimer.value)
    }

    // Set new timer with debounce
    emailDebounceTimer.value = setTimeout(() => {
        checkEmailAvailability(value)
    }, 500) // 500ms debounce
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

// Blur handlers for immediate validation
const handlePhoneBlur = () => {
    if (phoneDebounceTimer.value) {
        clearTimeout(phoneDebounceTimer.value)
    }
    checkPhoneAvailability(phone.value)
}



// Fetch provinces from API
const fetchProvinces = async () => {
    isLoadingCities.value = true
    try {
        const response = await fetch('/api/provinces/view')
        if (!response.ok) throw new Error('Không thể tải danh sách thành phố')
        const data = await response.json()
        provinces.value = data
    } catch (error) {
        console.error('Lỗi khi tải danh sách thành phố:', error)
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

const handleRegister = async () => {
    if (!canSubmit.value) {
        toast.error('Vui lòng kiểm tra lại thông tin đăng ký')
        return
    }

    isRegistering.value = true
    try {
        const registerData = {
            username: username.value,
            password: password.value,
            email: email.value,
            ten: ten.value,
            dienthoai: dienthoai.value,
            city: city.value,
            sex: sex.value ,
            userEntity: userEntity.id == 1
        }

        // Gọi API register → chỉ gửi OTP, chưa tạo user thật
        const response = await fetch('/api/users/register', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(registerData)
        })

        const data = await response.json()

        if (!response.ok) {
            throw new Error(data.message || 'Đăng ký thất bại')
        }

        // Nếu thành công, hiển thị modal xác thực
        showVerificationModal.value = true
        startTimer()
        resetVerificationCode()
        toast.info('Mã xác thực đã được gửi đến email của bạn')

    } catch (error) {
        console.error('Lỗi đăng ký:', error)

        // Xử lý các lỗi khác
        if (error.message.includes('Network') || error.message.includes('Failed to fetch')) {
            toast.error('Lỗi kết nối mạng. Vui lòng kiểm tra lại kết nối.')
        } else {
            toast.error(error.message)
        }
    } finally {
        isRegistering.value = false
    }
}

// Verification Modal Methods
const closeVerificationModal = () => {
    showVerificationModal.value = false
    resetVerificationCode()
    clearTimer()
}

const resetVerificationCode = () => {
    verificationCode.value = ['', '', '', '', '', '']
    codeError.value = ''
}

const handleCodeInput = (index, event) => {
    const value = event.target.value.replace(/[^0-9]/g, '')
    verificationCode.value[index] = value

    codeError.value = ''

    // Auto focus next input
    if (value && index < 5) {
        nextTick(() => {
            codeInputs.value[index + 1]?.focus()
        })
    }

    // Auto submit when complete
    if (isCodeComplete.value) {
        verifyCode()
    }
}

const handleCodeKeydown = (index, event) => {
    if (event.key === 'Backspace' && !verificationCode.value[index] && index > 0) {
        nextTick(() => {
            codeInputs.value[index - 1]?.focus()
        })
    }
}

const handleCodePaste = (event) => {
    event.preventDefault()
    const pasteData = event.clipboardData.getData('text').replace(/[^0-9]/g, '')
    const digits = pasteData.split('').slice(0, 6)

    digits.forEach((digit, index) => {
        if (index < 6) {
            verificationCode.value[index] = digit
        }
    })

    nextTick(() => {
        if (isCodeComplete.value) {
            verifyCode()
        } else {
            const nextEmptyIndex = verificationCode.value.findIndex(digit => digit === '')
            if (nextEmptyIndex !== -1) {
                codeInputs.value[nextEmptyIndex]?.focus()
            }
        }
    })
}

const verifyCode = async () => {
    if (!isCodeComplete.value) {
        codeError.value = 'Vui lòng nhập đầy đủ mã xác thực'
        return
    }

    isVerifying.value = true
    codeError.value = ''

    try {
        const code = verificationCode.value.join('')

        // Gọi API verify → nếu OTP đúng, server tạo user trong DB
        const response = await fetch('/api/users/verify', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                email: email.value,
                code: code,
                username: username.value,
                password: password.value,
                ten: ten.value,
                dienthoai: dienthoai.value,
                city: city.value,
                sex: sex.value
            })
        })

        if (!response.ok) {
            const errorData = await response.json()
            throw new Error(errorData.message || 'Mã xác thực không hợp lệ')
        }

        toast.success('Xác thực thành công! Tài khoản của bạn đã được tạo.')

        await new Promise(resolve => setTimeout(resolve, 2000))
        closeVerificationModal()
        router.push('/login')

    } catch (error) {
        console.error('Lỗi xác thực:', error)
        codeError.value = error.message
    } finally {
        isVerifying.value = false
    }
}

const resendVerificationCode = async () => {
    isResending.value = true
    codeError.value = ''

    try {
        const response = await fetch('/api/users/resend-code', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ email: email.value })
        });

        if (!response.ok) {
            throw new Error('Không thể gửi lại mã xác thực')
        }

        resetVerificationCode()
        timeLeft.value = 300
        canResend.value = false
        startTimer()

        toast.info('Mã xác thực mới đã được gửi đến email của bạn')

    } catch (error) {
        console.error('Lỗi gửi lại mã:', error)
        codeError.value = error.message
    } finally {
        isResending.value = false
    }
}

// Timer functions
let timerInterval = null

const startTimer = () => {
    timeLeft.value = 300
    canResend.value = false
    clearInterval(timerInterval)

    timerInterval = setInterval(() => {
        timeLeft.value--
        if (timeLeft.value <= 0) {
            clearInterval(timerInterval)
            canResend.value = true
        }
    }, 1000)
}

const clearTimer = () => {
    clearInterval(timerInterval)
}

const formatTime = (seconds) => {
    const minutes = Math.floor(seconds / 60)
    const secs = seconds % 60
    return `${minutes.toString().padStart(2, '0')}:${secs.toString().padStart(2, '0')}`
}

onMounted(() => {
    fetchProvinces()
    setTimeout(() => {
        isLoading.value = false
    }, 2500)
})
</script>

<style scoped>
/* Modal Overlay */
.modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 10000;
    animation: fadeIn 0.3s ease;
}

.verification-modal {
    background: white;
    border-radius: 20px;
    box-shadow: 0 20px 40px rgba(0, 0, 0, 0.2);
    width: 90%;
    max-width: 450px;
    animation: slideUp 0.3s ease;
}

.modal-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 25px 30px;
    border-bottom: 1px solid #e2e8f0;
}

.modal-header h2 {
    color: #2d3748;
    font-size: 1.5rem;
    font-weight: 600;
    margin: 0;
}

.close-btn {
    background: none;
    border: none;
    font-size: 1.2rem;
    color: #a0aec0;
    cursor: pointer;
    padding: 5px;
    border-radius: 50%;
    transition: all 0.3s ease;
}

.close-btn:hover {
    background: #f7fafc;
    color: #4a5568;
}

.modal-body {
    padding: 30px;
    text-align: center;
}

.verification-icon {
    font-size: 3rem;
    color: #667eea;
    margin-bottom: 20px;
}

.verification-text {
    color: #4a5568;
    margin-bottom: 30px;
    line-height: 1.5;
}

.verification-text strong {
    color: #2d3748;
}

.code-input-group {
    display: flex;
    gap: 10px;
    justify-content: center;
    margin-bottom: 20px;
}

.code-input {
    width: 50px;
    height: 60px;
    border: 2px solid #e2e8f0;
    border-radius: 10px;
    text-align: center;
    font-size: 1.5rem;
    font-weight: 600;
    color: #2d3748;
    background: #f7fafc;
    transition: all 0.3s ease;
}

.code-input:focus {
    border-color: #667eea;
    box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
    outline: none;
}

.code-input.error {
    border-color: #e53e3e;
    background-color: #fef5f5;
}

.modal-error {
    justify-content: center;
    margin-bottom: 20px;
}

.timer-section {
    margin-bottom: 20px;
}

.timer-text {
    color: #4a5568;
    font-size: 0.9rem;
}

.timer-text.expired {
    color: #e53e3e;
}

.timer {
    font-weight: 600;
    color: #667eea;
}

.timer-text.expired .timer {
    color: #e53e3e;
}

.resend-section {
    margin-bottom: 10px;
}

.resend-btn {
    background: none;
    border: none;
    color: #667eea;
    font-weight: 500;
    cursor: pointer;
    padding: 8px 16px;
    border-radius: 8px;
    transition: all 0.3s ease;
}

.resend-btn:hover:not(:disabled) {
    background: rgba(102, 126, 234, 0.1);
}

.resend-btn:disabled {
    color: #a0aec0;
    cursor: not-allowed;
}

.modal-footer {
    display: flex;
    gap: 15px;
    padding: 20px 30px;
    border-top: 1px solid #e2e8f0;
}

.cancel-btn {
    flex: 1;
    padding: 12px;
    border: 2px solid #e2e8f0;
    border-radius: 10px;
    background: white;
    color: #4a5568;
    font-weight: 500;
    cursor: pointer;
    transition: all 0.3s ease;
}

.cancel-btn:hover {
    border-color: #a0aec0;
    background: #f7fafc;
}

.verify-btn {
    flex: 1;
    padding: 12px;
    border: none;
    border-radius: 10px;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    color: white;
    font-weight: 500;
    cursor: pointer;
    transition: all 0.3s ease;
}

.verify-btn:hover:not(:disabled) {
    transform: translateY(-1px);
    box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);
}

.verify-btn:disabled {
    opacity: 0.7;
    cursor: not-allowed;
    transform: none;
}

/* Responsive */
@media (max-width: 480px) {
    .verification-modal {
        width: 95%;
        margin: 20px;
    }

    .modal-header,
    .modal-body,
    .modal-footer {
        padding: 20px;
    }

    .code-input {
        width: 45px;
        height: 55px;
        font-size: 1.3rem;
    }

    .code-input-group {
        gap: 8px;
    }
}

/* Error Styles */
.input-error {
    border-color: #e53e3e !important;
    background-color: #fef5f5 !important;
}

.input-success {
    border-color: #38a169 !important;
    background-color: #f0fff4 !important;
}

.error-icon {
    padding: 0 15px;
    color: #e53e3e;
    animation: shake 0.5s ease-in-out;
}

.success-icon {
    padding: 0 15px;
    color: #38a169;
}

.loading-icon {
    padding: 0 15px;
    color: #a0aec0;
}

.error-message {
    color: #e53e3e;
    font-size: 0.8rem;
    margin-top: 5px;
    display: flex;
    align-items: center;
    gap: 5px;
}

.success-message {
    color: #38a169;
    font-size: 0.8rem;
    margin-top: 5px;
    display: flex;
    align-items: center;
    gap: 5px;
}

.validation-message {
    font-size: 0.8rem;
    margin-top: 5px;
    display: flex;
    align-items: center;
    gap: 5px;
}

.validation-message.checking {
    color: #667eea;
}

.validation-messages {
    margin-top: -10px;
    margin-bottom: 10px;
}

/* Gender Select */
.gender-select {
    flex: 1;
    padding: 15px 0;
    border: none;
    background: transparent;
    outline: none;
    font-size: 1rem;
    color: #4a5568;
    appearance: none;
    cursor: pointer;
}

.input-group:focus-within .gender-select {
    color: #2d3748;
}

/* Responsive adjustments */
@media (max-width: 480px) {

    .error-message,
    .success-message,
    .validation-message {
        font-size: 0.75rem;
    }

    .validation-messages {
        margin-bottom: 5px;
    }
}

/* Animation for error */
@keyframes shake {

    0%,
    100% {
        transform: translateX(0);
    }

    25% {
        transform: translateX(-5px);
    }

    75% {
        transform: translateX(5px);
    }
}

/* Các style khác giữ nguyên từ code trước */
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
    gap: 12px;
    margin-bottom: 40px;
    font-size: 2rem;
    font-weight: bold;
}

.loading-logo img {
    margin: 0;
    padding: 15px;
    width: 58px;
    height: auto;
    display: block;
    filter: brightness(0) saturate(100%) invert(80%) sepia(30%) saturate(800%) hue-rotate(165deg) brightness(110%) contrast(105%);
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
    margin-left: 65px;
    font-weight: bold;
}

/* City Select Styles */
.city-select {
    flex: 1;
    padding: 15px 0;
    border: none;
    background: transparent;
    outline: none;
    font-size: 1rem;
    color: #4a5568;
    appearance: none;
    cursor: pointer;
}

.input-group:focus-within .city-select {
    color: #2d3748;
}

.loading-icon {
    padding: 0 15px;
    color: #a0aec0;
}

/* Style cho select khi đang loading */
.city-select {
    flex: 1;
    padding: 15px 0;
    border: none;
    background: transparent;
    outline: none;
    font-size: 1rem;
    color: #000000;
    appearance: none;
    cursor: pointer;
}

.input-group:focus-within .city-select {
    color: #2d3748;
}

.loading-icon {
    padding: 0 15px;
    color: #a0aec0;
}

/* Style cho select khi đang loading */
.city-select:disabled {
    opacity: 0.7;
    cursor: not-allowed;
}

/* Error state cho city select */
.input-group.error .city-select {
    border-color: #e53e3e;
}

.city-error {
    color: #e53e3e;
    font-size: 0.8rem;
    margin-top: 5px;
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

.register-wrapper {
    min-height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    position: relative;
    overflow: hidden;
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    animation: fadeIn 0.8s ease-out;
    padding: 20px 0;
}

.register-container {
    background: rgba(255, 255, 255, 0.95);
    backdrop-filter: blur(10px);
    padding: 40px;
    border-radius: 20px;
    box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
    width: 100%;
    max-width: 500px;
    position: relative;
    z-index: 10;
    animation: slideUp 0.6s ease-out;
}

.register-header {
    text-align: center;
    margin-bottom: 30px;
}

.logo {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 10px;
    margin-bottom: 20px;
    color: #667eea;
    font-weight: bold;
    font-size: 1.5rem;
}

.logo img {
    margin: 0;
    padding: 15;
    width: 58px;
    /* tuỳ ông chỉnh cho đẹp */
    height: auto;
    display: block;
    /* xoá khoảng trống inline */
}

.register-header h1 {
    color: #2d3748;
    margin-bottom: 10px;
    font-size: 1.8rem;
    font-weight: 600;
}

.register-header p {
    color: #718096;
    font-size: 0.9rem;
}

.register-form {
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

.password-toggle {
    background: none;
    border: none;
    padding: 0 15px;
    color: #a0aec0;
    cursor: pointer;
    transition: color 0.3s ease;
}

.password-toggle:hover {
    color: #667eea;
}

.password-strength {
    margin-top: 5px;
}

.strength-bar {
    height: 6px;
    background: #e2e8f0;
    border-radius: 3px;
    overflow: hidden;
    margin-bottom: 5px;
}

.strength-fill {
    height: 100%;
    border-radius: 3px;
    transition: all 0.3s ease;
}

.strength-fill.weak {
    background: #e53e3e;
    width: 33%;
}

.strength-fill.medium {
    background: #d69e2e;
    width: 66%;
}

.strength-fill.strong {
    background: #38a169;
    width: 100%;
}

.strength-text {
    font-size: 0.8rem;
    font-weight: 500;
}

.strength-text.weak {
    color: #e53e3e;
}

.strength-text.medium {
    color: #d69e2e;
}

.strength-text.strong {
    color: #38a169;
}

.form-options {
    display: flex;
    align-items: center;
    font-size: 0.9rem;
}

.agree-terms,
.newsletter {
    display: flex;
    align-items: flex-start;
    gap: 8px;
    cursor: pointer;
    color: #4a5568;
    line-height: 1.4;
}

.agree-terms a,
.newsletter a {
    color: #667eea;
    text-decoration: none;
}

.agree-terms a:hover,
.newsletter a:hover {
    text-decoration: underline;
}

.agree-terms input,
.newsletter input {
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
    flex-shrink: 0;
    margin-top: 2px;
}

.agree-terms input:checked+.checkmark,
.newsletter input:checked+.checkmark {
    background: #667eea;
    border-color: #667eea;
}

.agree-terms input:checked+.checkmark:after,
.newsletter input:checked+.checkmark:after {
    content: '✓';
    color: white;
    font-size: 12px;
}

.register-btn {
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
    position: relative;
}

.register-btn:hover:not(:disabled) {
    transform: translateY(-2px);
    box-shadow: 0 6px 20px rgba(102, 126, 234, 0.4);
}

.register-btn:disabled {
    opacity: 0.7;
    cursor: not-allowed;
    background: linear-gradient(135deg, #a0aec0 0%, #718096 100%);
}

.btn-loading {
    background: linear-gradient(135deg, #a3bcf9 0%, #9d7bbf 100%);
}

.loading-text {
    display: flex;
    align-items: center;
    gap: 8px;
}

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

.register-footer {
    text-align: center;
    margin-top: 25px;
    color: #718096;
    font-size: 0.9rem;
}

.register-footer a {
    color: #667eea;
    text-decoration: none;
    font-weight: 500;
}

.register-footer a:hover {
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

@keyframes fadeIn {
    from {
        opacity: 0;
    }

    to {
        opacity: 1;
    }
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

@media (max-width: 480px) {
    .register-container {
        margin: 20px;
        padding: 30px 25px;
    }

    .form-options {
        align-items: flex-start;
    }

    .agree-terms,
    .newsletter {
        font-size: 0.85rem;
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