<template>
  <div class="user-management-page">
    <!-- Header -->
    <div class="page-header">
      <h1>Quản Lý Người Dùng</h1>
      <div class="header-actions">
        <span class="user-count">Tổng: {{ totalUsers }} người dùng</span>
        <router-link to="/admin/add-user" class="btn-add-user" active-class="active">
          <i class="nav-icon fas fa-user-shield"></i>
          <span class="nav-text">Thêm người dùng</span>
        </router-link>
      </div>
    </div>

    <!-- Bulk Actions -->
    <div v-if="selectedUsers.length > 0" class="bulk-actions-bar">
      <span>{{ selectedUsers.length }} người dùng được chọn</span>
      <div class="bulk-buttons">
        <button @click="bulkUpdateStatus(true)">Kích hoạt</button>
        <button @click="bulkUpdateStatus(false)">Vô hiệu hóa</button>
        <button @click="confirmBulkDelete">Xóa ({{ selectedUsers.length }})</button>
        <button @click="clearSelection">Bỏ chọn</button>
      </div>
    </div>

    <!-- Search and Filters -->
    <div class="search-filters-container">
      <div class="search-box">
        <input 
          type="text" 
          v-model="searchQuery" 
          placeholder="Tìm kiếm người dùng..." 
          class="search-input" 
          @input="handleSearch"
        />
      </div>

      <div class="filter-controls">
        <select v-model="roleFilter" class="filter-select" @change="handleFilterChange">
          <option value="">Tất cả vai trò</option>
          <option value="admin">Quản trị</option>
          <option value="mod">Điều hành</option>
          <option value="user">Người dùng</option>
        </select>

        <select v-model="statusFilter" class="filter-select" @change="handleFilterChange">
          <option value="">Tất cả trạng thái</option>
          <option value="true">Đang hoạt động</option>
          <option value="false">Vô hiệu hóa</option>
        </select>

        <button class="btn-reset" @click="resetFilters">Đặt lại</button>
      </div>
    </div>

    <!-- Loading State -->
    <div v-if="loading" class="loading-state">
      <p>Đang tải dữ liệu...</p>
    </div>

    <!-- Users Table -->
    <div v-else class="table-container">
      <div class="table-wrapper">
        <table class="users-table">
          <thead>
            <tr>
              <th class="col-checkbox">
                <input type="checkbox" :checked="isAllSelected" @change="toggleSelectAll" />
              </th>
              <th class="col-user">Người dùng</th>
              <th class="col-contact">Liên hệ</th>
              <th class="col-role">Vai trò</th>
              <th class="col-status">Trạng thái</th>
              <th class="col-actions">Thao tác</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="user in paginatedUsers" :key="user.id" :class="{ selected: isUserSelected(user.id) }">
              <td class="col-checkbox">
                <input type="checkbox" :checked="isUserSelected(user.id)" @change="toggleUserSelection(user.id)" />
              </td>
              <td class="col-user">
                <div class="user-info">
                  <div class="username">{{ user.username }}</div>
                  <div class="fullname">{{ user.ten }}</div>
                </div>
              </td>
              <td class="col-contact">
                <div>{{ user.email || 'Chưa có email' }}</div>
                <div>{{ user.dienthoai || 'Chưa có SĐT' }}</div>
              </td>
              <td class="col-role">
                <span class="role-badge" :class="getRoleClass(user.roleEntity)">
                  {{ getRoleLabel(user.roleEntity) }}
                </span>
              </td>
              <td class="col-status">
                <span class="status-badge" :class="{ active: user.hienthi === true, inactive: user.hienthi === false }">
                  {{ user.hienthi === true ? 'Đang hoạt động' : 'Vô hiệu hóa' }}
                </span>
              </td>
              <td class="col-actions">
                <div class="action-buttons">
                  <router-link :to="{ name: 'admin-edit-user', query: { id: user.id } }" class="btn-edit-user"
                    active-class="active">
                    <button class="btn-edit">Sửa</button>
                  </router-link>

                  <button class="btn-delete" @click="confirmDeleteUser(user)">Xóa</button>
                  <button class="btn-status" @click="toggleUserStatus(user)">
                    {{ user.hienthi === true ? 'Vô hiệu' : 'Kích hoạt' }}
                  </button>
                </div>
              </td>
            </tr>
          </tbody>
        </table>

        <!-- Empty State -->
        <div v-if="filteredUsers.length === 0" class="empty-state">
          <h3>Không tìm thấy người dùng</h3>
          <p v-if="searchQuery || roleFilter || statusFilter">
            Thử thay đổi điều kiện tìm kiếm hoặc bộ lọc
          </p>
          <p v-else>Chưa có người dùng nào trong hệ thống</p>
          <button v-if="searchQuery || roleFilter || statusFilter" class="btn-reset" @click="resetFilters">
            Xóa bộ lọc
          </button>
        </div>
      </div>

      <!-- Pagination -->
      <div v-if="filteredUsers.length > 0" class="pagination">
        <div class="pagination-info">
          Hiển thị {{ startIndex + 1 }}-{{ endIndex }} của {{ totalUsers }} người dùng
        </div>
        <div class="pagination-controls">
          <button 
            @click="prevPage" 
            :disabled="currentPage === 1"
            class="pagination-btn"
          >
            ‹‹
          </button>
          
          <button 
            v-for="page in visiblePages" 
            :key="page"
            @click="goToPage(page)"
            :class="['pagination-page', { active: currentPage === page }]"
          >
            {{ page }}
          </button>
          
          <button 
            @click="nextPage" 
            :disabled="currentPage === totalPages"
            class="pagination-btn"
          >
            ››
          </button>
        </div>
        <div class="pagination-size">
          <select v-model="pageSize" @change="handlePageSizeChange" class="page-size-select">
            <option value="10">10 / trang</option>
            <option value="20">20 / trang</option>
            <option value="50">50 / trang</option>
            <option value="100">100 / trang</option>
          </select>
        </div>
      </div>
    </div>

    <!-- Delete Confirmation Modal -->
    <div v-if="showDeleteModal" class="modal-overlay">
      <div class="modal-content">
        <div class="modal-header">
          <h3>Xác nhận xóa</h3>
          <button class="close-btn" @click="showDeleteModal = false">✕</button>
        </div>
        <div class="modal-body">
          <p>Bạn có chắc chắn muốn xóa người dùng <strong>{{ userToDelete?.username }}</strong>?</p>
          <p class="text-warning">Hành động này không thể hoàn tác!</p>
          <p class="text-warning">Nếu chỉ muốn xoá mềm có thể thay đổi trạng thái hoạt động !</p>
        </div>
        <div class="modal-footer">
          <button class="btn-secondary" @click="showDeleteModal = false">Hủy</button>
          <button class="btn-danger" @click="deleteUser" :disabled="deleting">
            {{ deleting ? 'Đang xóa...' : 'Xóa' }}
          </button>
        </div>
      </div>
    </div>

    <!-- Bulk Delete Confirmation Modal -->
    <div v-if="showBulkDeleteModal" class="modal-overlay">
      <div class="modal-content">
        <div class="modal-header">
          <h3>Xác nhận xóa nhiều</h3>
          <button class="close-btn" @click="showBulkDeleteModal = false">✕</button>
        </div>
        <div class="modal-body">
          <p>Bạn có chắc chắn muốn xóa <strong>{{ selectedUsers.length }} người dùng</strong> đã chọn?</p>
          <p class="text-warning">Hành động này không thể hoàn tác!</p>
          <p class="text-warning">Nếu chỉ muốn xoá mềm có thể thay đổi trạng thái hoạt động !</p>
        </div>
        <div class="modal-footer">
          <button class="btn-secondary" @click="showBulkDeleteModal = false">Hủy</button>
          <button class="btn-danger" @click="bulkDeleteUsers" :disabled="deleting">
            {{ deleting ? 'Đang xóa...' : `Xóa (${selectedUsers.length})` }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { toast } from 'vue3-toastify'

// Reactive data
const users = ref([])
const loading = ref(false)
const showDeleteModal = ref(false)
const showBulkDeleteModal = ref(false)
const userToDelete = ref(null)
const deleting = ref(false)
const searchQuery = ref('')
const roleFilter = ref('')
const statusFilter = ref('')
const selectedUsers = ref([])

// Pagination data
const currentPage = ref(1)
const pageSize = ref(20)

// API endpoints configuration
const API_BASE_URL = 'https://jq6kflwj-5173.asse.devtunnels.ms/api'
// const API_BASE_URL = 'http://localhost:8080/api'

// Computed properties
const filteredUsers = computed(() => {
  let filtered = users.value

  // Apply search filter
  if (searchQuery.value) {
    const query = searchQuery.value.toLowerCase()
    filtered = filtered.filter(user =>
      user.username?.toLowerCase().includes(query) ||
      user.ten?.toLowerCase().includes(query) ||
      user.email?.toLowerCase().includes(query) ||
      user.dienthoai?.toLowerCase().includes(query)
    )
  }

  // Apply role filter (dùng roleEntity.roleName)
  if (roleFilter.value) {
    filtered = filtered.filter(user => user.roleEntity?.roleName === roleFilter.value)
  }

  // Apply status filter
  if (statusFilter.value) {
    filtered = filtered.filter(user => user.hienthi?.toString() === statusFilter.value)
  }

  return filtered
})

const totalUsers = computed(() => filteredUsers.value.length)
const totalPages = computed(() => Math.ceil(totalUsers.value / pageSize.value))
const startIndex = computed(() => (currentPage.value - 1) * pageSize.value)
const endIndex = computed(() => Math.min(startIndex.value + pageSize.value, totalUsers.value))

const paginatedUsers = computed(() => {
  return filteredUsers.value.slice(startIndex.value, endIndex.value)
})

const visiblePages = computed(() => {
  const pages = []
  const maxVisiblePages = 5
  let startPage = Math.max(1, currentPage.value - Math.floor(maxVisiblePages / 2))
  let endPage = Math.min(totalPages.value, startPage + maxVisiblePages - 1)
  
  // Adjust start page if we're near the end
  if (endPage - startPage + 1 < maxVisiblePages) {
    startPage = Math.max(1, endPage - maxVisiblePages + 1)
  }
  
  for (let i = startPage; i <= endPage; i++) {
    pages.push(i)
  }
  
  return pages
})

const isAllSelected = computed(() => {
  return paginatedUsers.value.length > 0 &&
    paginatedUsers.value.every(user => selectedUsers.value.some(selected => selected.id === user.id))
})

// Pagination methods
const handleSearch = () => {
  currentPage.value = 1
}

const handleFilterChange = () => {
  currentPage.value = 1
}

const handlePageSizeChange = () => {
  currentPage.value = 1
}

const prevPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--
  }
}

const nextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++
  }
}

const goToPage = (page) => {
  currentPage.value = page
}

// API calls
const fetchUsers = async () => {
  loading.value = true
  try {
    const response = await fetch(`${API_BASE_URL}/users`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    })

    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`)
    }

    const data = await response.json()
    users.value = data.users || data

  } catch (error) {
    console.error('Error fetching users:', error)
    alert('Lỗi khi tải danh sách người dùng')
  } finally {
    loading.value = false
  }
}

const disableUserAPI = async (userId) => {
  try {
    const response = await fetch(`${API_BASE_URL}/users/${userId}`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    })

    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`)
    }

    // 🔔 Thông báo cực chill
    toast.success("Đã Xoá tài khoản!", {
      autoClose: 2000,
      position: "top-right"
    })

    return true

  } catch (error) {
    console.error('Error deleting user:', error)

    toast.error("Xóa thất bại, thử lại đi bro!", {
      autoClose: 2000,
      position: "top-right"
    })

    throw error
  }
}

const bulkDeleteUsersAPI = async (userIds) => {
  try {
    const response = await fetch(`${API_BASE_URL}/users/bulk-delete`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      },
      body: JSON.stringify({ userIds })
    })

    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`)
    }

    return true

  } catch (error) {
    console.error('Error bulk deleting users:', error)
    throw error
  }
}

const updateUserStatusAPI = async (userId, status) => {
  try {
    const response = await fetch(`${API_BASE_URL}/users/${userId}/status`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      },
      body: JSON.stringify({ hienthi: status })
    })

    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`)
    }

    return true

  } catch (error) {
    console.error('Error updating user status:', error)
    throw error
  }
}

const bulkUpdateStatusAPI = async (userIds, status) => {
  try {
    const response = await fetch(`${API_BASE_URL}/users/bulk-status`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      },
      body: JSON.stringify({ userIds, hienthi: status })
    })

    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`)
    }

    return true

  } catch (error) {
    console.error('Error bulk updating user status:', error)
    throw error
  }
}

// Selection methods
const toggleUserSelection = (userId) => {
  const user = users.value.find(u => u.id === userId)
  if (!user) return

  const index = selectedUsers.value.findIndex(u => u.id === userId)
  if (index > -1) {
    selectedUsers.value.splice(index, 1)
  } else {
    selectedUsers.value.push(user)
  }
}

const toggleSelectAll = () => {
  if (isAllSelected.value) {
    // Bỏ chọn tất cả users trên trang hiện tại
    paginatedUsers.value.forEach(user => {
      const index = selectedUsers.value.findIndex(u => u.id === user.id)
      if (index > -1) {
        selectedUsers.value.splice(index, 1)
      }
    })
  } else {
    // Chọn tất cả users trên trang hiện tại
    paginatedUsers.value.forEach(user => {
      if (!selectedUsers.value.some(selected => selected.id === user.id)) {
        selectedUsers.value.push(user)
      }
    })
  }
}

const isUserSelected = (userId) => {
  return selectedUsers.value.some(user => user.id === userId)
}

const clearSelection = () => {
  selectedUsers.value = []
}

const confirmDeleteUser = (user) => {
  userToDelete.value = user
  showDeleteModal.value = true
}

const deleteUser = async () => {
  if (!userToDelete.value) return

  deleting.value = true
  try {
    await disableUserAPI(userToDelete.value.id)

    // update UI
    const user = users.value.find(u => u.id === userToDelete.value.id)
    if (user) user.hienthi = false

    selectedUsers.value = selectedUsers.value.filter(
      u => u.id !== userToDelete.value.id
    )

    showDeleteModal.value = false
    userToDelete.value = null

  } catch (error) {
    alert('Có lỗi xảy ra khi vô hiệu hóa người dùng')
  } finally {
    deleting.value = false
  }
}

const confirmBulkDelete = () => {
  if (selectedUsers.value.length === 0) return
  showBulkDeleteModal.value = true
}

const bulkDeleteUsers = async () => {
  if (selectedUsers.value.length === 0) return

  deleting.value = true
  try {
    const userIds = selectedUsers.value.map(user => user.id)
    await bulkDeleteUsersAPI(userIds)

    // Remove from local state
    users.value = users.value.filter(user => !userIds.includes(user.id))

    showBulkDeleteModal.value = false
    selectedUsers.value = []

  } catch (error) {
    alert('Có lỗi xảy ra khi xóa người dùng')
  } finally {
    deleting.value = false
  }
}

const toggleUserStatus = async (user) => {
  const newStatus = !user.hienthi
  console.log(`Chuyển trạng thái user ${user.username} từ ${user.hienthi} sang ${newStatus}`)

  try {
    await updateUserStatusAPI(user.id, newStatus)

    // Update local state
    user.hienthi = newStatus
    toast.success(`Đã cập nhật trạng thái người dùng ${user.username}`, {
      autoClose: 2000,
      position: "top-right"
    })

  } catch (error) {
    alert('Có lỗi xảy ra khi cập nhật trạng thái')
  }
}

const bulkUpdateStatus = async (status) => {
  if (selectedUsers.value.length === 0) return

  try {
    const userIds = selectedUsers.value.map(user => user.id)
    await bulkUpdateStatusAPI(userIds, status)

    // Update local state
    users.value.forEach(user => {
      if (userIds.includes(user.id)) {
        user.hienthi = status
      }
    })

  } catch (error) {
    alert('Có lỗi xảy ra khi cập nhật trạng thái')
  }
}

const resetFilters = () => {
  searchQuery.value = ''
  roleFilter.value = ''
  statusFilter.value = ''
  selectedUsers.value = []
  currentPage.value = 1
}

// Utility functions
const getRoleLabel = (roleEntity) => {
  const roleName = roleEntity?.roleName?.toLowerCase() || 'user'
  const labels = {
    'admin': 'Quản trị',
    'mod': 'Điều hành',
    'user': 'Người dùng'
  }
  return labels[roleName] || 'Người dùng'
}

const getRoleClass = (roleEntity) => {
  const roleName = roleEntity?.roleName?.toLowerCase() || 'user'
  const classes = {
    'admin': 'admin',
    'mod': 'mod',
    'user': 'user'
  }
  return classes[roleName] || 'user'
}

// Lifecycle
onMounted(() => {
  fetchUsers()
})
</script>

<style scoped>
.user-management-page {
  padding: 20px;
  background: #f5f5f5;
  min-height: 100vh;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding-bottom: 15px;
  border-bottom: 1px solid #ddd;
}

.page-header h1 {
  margin: 0;
  color: #333;
}

.header-actions {
  display: flex;
  align-items: center;
  gap: 15px;
}

.user-count {
  background: #e9ecef;
  padding: 8px 12px;
  border-radius: 4px;
  font-size: 14px;
}

.btn-add-user {
  background: #007bff;
  color: white;
  border: none;
  padding: 10px 15px;
  border-radius: 4px;
  cursor: pointer;
  text-decoration: none;
  display: flex;
  align-items: center;
  gap: 5px;
}

.btn-add-user:hover {
  background: #0056b3;
}

/* Bulk Actions */
.bulk-actions-bar {
  background: #e9ecef;
  padding: 15px;
  margin-bottom: 20px;
  border-radius: 4px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.bulk-buttons {
  display: flex;
  gap: 10px;
}

.bulk-buttons button {
  padding: 8px 12px;
  border: 1px solid #ccc;
  border-radius: 4px;
  background: white;
  cursor: pointer;
}

.bulk-buttons button:hover {
  background: #f8f9fa;
}

/* Search and Filters */
.search-filters-container {
  background: white;
  padding: 15px;
  margin-bottom: 20px;
  border-radius: 4px;
  display: flex;
  gap: 15px;
  align-items: center;
}

.search-input {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  width: 300px;
}

.filter-controls {
  display: flex;
  gap: 10px;
  align-items: center;
}

.filter-select {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  background: white;
}

.btn-reset {
  background: #6c757d;
  color: white;
  border: none;
  padding: 10px 15px;
  border-radius: 4px;
  cursor: pointer;
}

.btn-reset:hover {
  background: #545b62;
}

/* Loading State */
.loading-state {
  text-align: center;
  padding: 40px;
  color: #666;
}

/* Table Container */
.table-container {
  background: white;
  border-radius: 4px;
  overflow: hidden;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.table-wrapper {
  max-height: calc(100vh - 350px);
  overflow-y: auto;
}

.users-table {
  width: 100%;
  border-collapse: collapse;
}

.users-table th {
  background: #f8f9fa;
  padding: 12px 15px;
  text-align: left;
  border-bottom: 1px solid #dee2e6;
  font-weight: 600;
  position: sticky;
  top: 0;
  z-index: 10;
}

.users-table td {
  padding: 12px 15px;
  border-bottom: 1px solid #dee2e6;
}

.users-table tr:hover {
  background: #f8f9fa;
}

.users-table tr.selected {
  background: #e3f2fd;
}

.col-checkbox {
  width: 40px;
}

.col-user {
  min-width: 200px;
}

.col-contact {
  min-width: 200px;
}

.col-role {
  min-width: 120px;
}

.col-status {
  min-width: 120px;
}

.col-actions {
  min-width: 150px;
}

/* User info */
.user-info .username {
  font-weight: 600;
  color: #333;
}

.user-info .fullname {
  color: #666;
  font-size: 14px;
}

/* Badges */
.role-badge,
.status-badge {
  padding: 5px 10px;
  border-radius: 4px;
  font-size: 12px;
  font-weight: 600;
}

.role-badge.admin {
  background: #f8d7da;
  color: #721c24;
}

.role-badge.mod {
  background: #fff3cd;
  color: #856404;
}

.role-badge.user {
  background: #d1ecf1;
  color: #0c5460;
}

.status-badge.active {
  background: #d4edda;
  color: #155724;
}

.status-badge.inactive {
  background: #f8d7da;
  color: #721c24;
}

/* Action buttons */
.action-buttons {
  display: flex;
  gap: 5px;
}

.action-buttons button {
  padding: 6px 10px;
  border: none;
  border-radius: 3px;
  cursor: pointer;
  font-size: 12px;
}

.btn-edit {
  background: #17a2b8;
  color: white;
}

.btn-edit:hover {
  background: #138496;
}

.btn-delete {
  background: #dc3545;
  color: white;
}

.btn-delete:hover {
  background: #c82333;
}

.btn-status {
  background: #28a745;
  color: white;
}

.btn-status:hover {
  background: #218838;
}

/* Empty State */
.empty-state {
  text-align: center;
  padding: 40px;
  color: #666;
}

.empty-state h3 {
  margin-bottom: 10px;
  color: #333;
}

/* Pagination */
.pagination {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
  border-top: 1px solid #eee;
  background: #fafafa;
}

.pagination-info {
  color: #666;
  font-size: 14px;
}

.pagination-controls {
  display: flex;
  gap: 5px;
  align-items: center;
}

.pagination-btn,
.pagination-page {
  padding: 8px 12px;
  border: 1px solid #ddd;
  background: white;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
  min-width: 40px;
  text-align: center;
  transition: all 0.3s;
}

.pagination-btn:hover:not(:disabled),
.pagination-page:hover:not(.active) {
  background: #f0f0f0;
}

.pagination-btn:disabled {
  background: #f5f5f5;
  color: #999;
  cursor: not-allowed;
}

.pagination-page.active {
  background: #007bff;
  color: white;
  border-color: #007bff;
}

.pagination-size {
  display: flex;
  align-items: center;
}

.page-size-select {
  padding: 8px 12px;
  border: 1px solid #ddd;
  border-radius: 4px;
  background: white;
  font-size: 14px;
}

/* Modals */
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
}

.modal-content {
  background: white;
  border-radius: 8px;
  width: 500px;
  max-width: 90%;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
  border-bottom: 1px solid #dee2e6;
}

.modal-header h3 {
  margin: 0;
}

.close-btn {
  background: none;
  border: none;
  font-size: 20px;
  cursor: pointer;
  color: #666;
}

.modal-body {
  padding: 20px;
}

.modal-body p {
  margin: 0 0 15px 0;
}

.text-warning {
  color: #dc3545;
  font-weight: 600;
}

.modal-footer {
  display: flex;
  gap: 10px;
  justify-content: flex-end;
  padding: 20px;
  border-top: 1px solid #dee2e6;
}

.btn-secondary {
  background: #6c757d;
  color: white;
  border: none;
  padding: 10px 15px;
  border-radius: 4px;
  cursor: pointer;
}

.btn-secondary:hover {
  background: #545b62;
}

.btn-danger {
  background: #dc3545;
  color: white;
  border: none;
  padding: 10px 15px;
  border-radius: 4px;
  cursor: pointer;
}

.btn-danger:hover:not(:disabled) {
  background: #c82333;
}

.btn-danger:disabled {
  background: #e9a0a7;
  cursor: not-allowed;
}

/* Responsive */
@media (max-width: 768px) {
  .page-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 10px;
  }

  .search-filters-container {
    flex-direction: column;
    align-items: stretch;
  }

  .search-input {
    width: 100%;
  }

  .filter-controls {
    flex-wrap: wrap;
  }

  .bulk-actions-bar {
    flex-direction: column;
    align-items: flex-start;
    gap: 10px;
  }

  .bulk-buttons {
    flex-wrap: wrap;
  }

  .users-table {
    font-size: 14px;
  }

  .users-table th,
  .users-table td {
    padding: 8px 10px;
  }

  .action-buttons {
    flex-direction: column;
  }

  .pagination {
    flex-direction: column;
    gap: 15px;
    text-align: center;
  }
  
  .pagination-controls {
    order: 2;
  }
  
  .pagination-size {
    order: 1;
  }
  
  .pagination-info {
    order: 3;
  }
}
</style>