// composables/useUser.js
import { ref, computed } from 'vue'

export function useUser() {
    // Lấy thông tin user từ localStorage
    const getUserInfo = () => {
        try {
            const userString = localStorage.getItem('user')
            return userString ? JSON.parse(userString) : null
        } catch (error) {
            console.error('Error parsing user data:', error)
            return null
        }
    }

    // Computed properties cho các thông tin thường dùng
    const user = computed(() => getUserInfo())
    
    // Thông tin cơ bản
    const id = computed(() => user.value?.id)
    const username = computed(() => user.value?.username || 'unknown')
    const email = computed(() => user.value?.email || 'Chưa có email')
    
    // Thông tin cá nhân (từ backend của bạn)
    const ten = computed(() => user.value?.ten || user.value?.username) // full name
    const fullName = computed(() => user.value?.ten || user.value?.username)
    const displayName = computed(() => user.value?.ten || user.value?.username)
    const dienthoai = computed(() => user.value?.dienthoai || 'Chưa có số điện thoại')
    const city = computed(() => user.value?.city || 'Chưa có thành phố')
    const sex = computed(() => user.value?.sex || 'Chưa xác định')
    
    // Thông tin hiển thị
    const avatar = computed(() => user.value?.avatar || '👤')
    
    // Thông tin vai trò
    const role = computed(() => user.value?.role || 'user')
    const permissions = computed(() => user.value?.permissions || [])
    const isAdmin = computed(() => user.value?.role === 'admin')
    
    // Thông tin đăng nhập
    const loginTime = computed(() => localStorage.getItem('loginTime') || 'Chưa xác định')
    const isLoggedIn = computed(() => !!localStorage.getItem('token') && !!user.value)
    const rememberMe = computed(() => user.value?.rememberMe || false)

    // Format thông tin để hiển thị
    const profileInfo = computed(() => {
        return {
            id: id.value,
            username: username.value,
            email: email.value,
            fullName: fullName.value,
            phone: dienthoai.value,
            city: city.value,
            gender: sex.value === 'male' ? 'Nam' : sex.value === 'female' ? 'Nữ' : 'Chưa xác định',
            role: role.value,
            loginTime: loginTime.value,
            avatar: avatar.value
        }
    })

    // Các hàm tiện ích
    const hasPermission = (permission) => {
        return user.value?.permissions?.includes(permission) || false
    }

    const canAccess = (requiredRole) => {
        const rolesHierarchy = {
            'user': 1,
            'moderator': 2,
            'admin': 3,
            'superadmin': 4
        }
        
        const userRoleLevel = rolesHierarchy[user.value?.role] || 0
        const requiredRoleLevel = rolesHierarchy[requiredRole] || 0
        
        return userRoleLevel >= requiredRoleLevel
    }

    const getUserField = (fieldPath, defaultValue = null) => {
        if (!user.value) return defaultValue
        
        const fields = fieldPath.split('.')
        let value = user.value
        
        for (const field of fields) {
            if (value && value[field] !== undefined) {
                value = value[field]
            } else {
                return defaultValue
            }
        }
        
        return value
    }

    // Lấy tất cả thông tin user
    const getAllUserInfo = () => {
        return {
            basicInfo: {
                id: id.value,
                username: username.value,
                email: email.value
            },
            personalInfo: {
                ten: ten.value,
                fullName: fullName.value,
                dienthoai: dienthoai.value,
                city: city.value,
                sex: sex.value
            },
            accountInfo: {
                role: role.value,
                permissions: permissions.value,
                isAdmin: isAdmin.value,
                loginTime: loginTime.value,
                isLoggedIn: isLoggedIn.value
            },
            rawData: user.value // Toàn bộ dữ liệu gốc
        }
    }

    return {
        // Data
        user,
        
        // Basic info
        id,
        username,
        email,
        
        // Personal info (từ backend của bạn)
        ten,
        fullName,
        displayName,
        dienthoai,
        city,
        sex,
        
        // Display info
        avatar,
        
        // Role info
        role,
        permissions,
        isAdmin,
        
        // Login info
        loginTime,
        isLoggedIn,
        rememberMe,
        
        // Formatted info
        profileInfo,
        
        // Methods
        hasPermission,
        canAccess,
        getUserField,
        getAllUserInfo,
        getUserInfo
    }
}