<template>
  <div class="order-management">
    <!-- Header -->
    <div class="header">
      <div class="header-left">
        <h1>Quản Lý Đơn Hàng</h1>
        <p>Danh sách đơn hàng hệ thống</p>
      </div>
      <div class="header-right">
        <span class="order-count">Tổng: {{ totalOrders }} đơn hàng</span>
        <button class="btn-add" @click="openAddModal">
          ➕ Thêm đơn hàng
        </button>
      </div>
    </div>

    <!-- Search and Filters -->
    <div class="search-filters">
      <div class="search-section">
        <input type="text" v-model="searchQuery" placeholder="Tìm kiếm theo mã đơn, tên KH, điện thoại..."
          class="search-input" @input="handleSearch" />
        <button v-if="searchQuery" @click="clearSearch" class="clear-search">✕</button>
      </div>

      <div class="filters-section">
        <select v-model="statusFilter" class="filter-select" @change="handleFilterChange">
          <option value="">Tất cả tình trạng</option>
          <option value="0">Chờ xác nhận</option>
          <option value="1">Đã xác nhận</option>
          <option value="2">Đang giao hàng</option>
          <option value="3">Đã giao hàng</option>
          <option value="4">Đã hủy</option>
        </select>

        <select v-model="paymentFilter" class="filter-select" @change="handleFilterChange">
          <option value="">Tất cả thanh toán</option>
          <option value="0">Chưa thanh toán</option>
          <option value="1">Đã thanh toán</option>
        </select>

        <button @click="resetFilters" class="btn-reset">Đặt lại</button>
      </div>
    </div>

    <!-- Loading -->
    <div v-if="loading" class="loading">
      <div class="spinner"></div>
      <p>Đang tải dữ liệu...</p>
    </div>

    <!-- Orders Table -->
    <div v-else class="table-container">
      <div class="table-wrapper">
        <table class="orders-table">
          <thead>
            <tr>
              <th>Mã ĐH</th>
              <th>Họ tên</th>
              <th>Điện thoại</th>
              <th>Ngày đặt</th>
              <th>Vận chuyển</th>
              <th>Tổng tiền</th>
              <th>Tình trạng</th>
              <th>Thanh toán</th>
              <th>Thao tác</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(order, index) in paginatedOrders" :key="order.id">
              <td class="order-code">
                <strong>{{ order.madonhang }}</strong>
              </td>
              <td class="customer-info">
                <div class="customer-name">{{ order.hoten }}</div>
                <div class="customer-email" v-if="order.email">{{ order.email }}</div>
              </td>
              <td class="customer-phone">
                {{ order.dienthoai }}
              </td>
              <td class="order-date">
                {{ formatDate(order.ngaytao) }}
              </td>
              <td class="shipping-method">
                {{ getShippingMethod(order.htgh) }}
              </td>
              <td class="total-amount">
                <strong>{{ formatPrice(order.tonggia) }}</strong>
              </td>
              <td class="status-cell">
                <select v-model="order.tinhtrang" @change="updateOrderStatus(order)" class="status-select"
                  :class="`status-${order.tinhtrang}`">
                  <option value="1">Chờ xác nhận</option>
                  <option value="2">Đã xác nhận</option>
                  <option value="3">Đang giao hàng</option>
                  <option value="4">Đã giao hàng</option>
                  <option value="5">Đã hủy</option>
                </select>
              </td>
              <td class="payment-cell">
                <select v-model="order.thanhtoan" @change="updatePaymentStatus(order)" class="payment-select"
                  :class="`payment-${order.thanhtoan}`">
                  <option value="0">Chưa thanh toán</option>
                  <option value="1">Đã thanh toán</option>
                </select>
              </td>
              <td class="action-buttons">
                <button @click="viewOrderDetail(order)" title="Xem chi tiết" class="btn-view">👁️</button>
                <button @click="editOrder(order)" title="Sửa đơn hàng" class="btn-edit">✏️</button>
                <button @click="confirmDeleteOrder(order)" title="Xóa đơn hàng" class="btn-delete">🗑️</button>
              </td>
            </tr>
          </tbody>
        </table>

        <!-- Empty State -->
        <div v-if="filteredOrders.length === 0" class="empty-state">
          <div class="empty-icon">📦</div>
          <h3>Không tìm thấy đơn hàng</h3>
          <p v-if="hasActiveFilters">
            Thử thay đổi điều kiện tìm kiếm hoặc bộ lọc
          </p>
          <p v-else>Chưa có đơn hàng nào trong hệ thống</p>
          <button v-if="hasActiveFilters" @click="resetFilters" class="btn-reset">
            Xóa bộ lọc
          </button>
          <button v-else @click="openAddModal" class="btn-add">
            ➕ Thêm đơn hàng đầu tiên
          </button>
        </div>
      </div>

      <!-- Pagination -->
      <div v-if="filteredOrders.length > 0" class="pagination">
        <div class="pagination-info">
          Hiển thị {{ startIndex + 1 }}-{{ endIndex }} của {{ totalOrders }} đơn hàng
        </div>
        <div class="pagination-controls">
          <button @click="prevPage" :disabled="currentPage === 1" class="pagination-btn">
            ‹‹
          </button>

          <button v-for="page in visiblePages" :key="page" @click="goToPage(page)"
            :class="['pagination-page', { active: currentPage === page }]">
            {{ page }}
          </button>

          <button @click="nextPage" :disabled="currentPage === totalPages" class="pagination-btn">
            ››
          </button>
        </div>
        <div class="pagination-size">
          <select v-model="pageSize" @change="handlePageSizeChange" class="page-size-select">
            <option value="10">10 / trang</option>
            <option value="20">20 / trang</option>
            <option value="50">50 / trang</option>
          </select>
        </div>
      </div>
    </div>

    <!-- Order Detail Modal -->
    <div v-if="showDetailModal" class="modal-overlay">
      <div class="modal large-modal">
        <div class="modal-header">
          <h3>Chi tiết đơn hàng #{{ selectedOrder?.madonhang }}</h3>
          <button @click="showDetailModal = false" class="close-btn">✕</button>
        </div>
        <div class="modal-body">
          <div v-if="selectedOrder" class="order-detail">
            <!-- Order Info -->
            <div class="detail-section">
              <h4>Thông tin đơn hàng</h4>
              <div class="info-grid">
                <div class="info-item">
                  <label>Mã đơn hàng:</label>
                  <span>{{ selectedOrder.madonhang }}</span>
                </div>
                <div class="info-item">
                  <label>Ngày đặt:</label>
                  <span>{{ formatDate(selectedOrder.ngaytao) }}</span>
                </div>
                <div class="info-item">
                  <label>Phương thức TT:</label>
                  <span>{{ selectedOrder.httt }}</span>
                </div>
                <div class="info-item">
                  <label>Hình thức GH:</label>
                  <span>{{ getShippingMethod(selectedOrder.htgh) }}</span>
                </div>
              </div>
            </div>

            <!-- Customer Info -->
            <div class="detail-section">
              <h4>Thông tin khách hàng</h4>
              <div class="info-grid">
                <div class="info-item">
                  <label>Họ tên:</label>
                  <span>{{ selectedOrder.hoten }}</span>
                </div>
                <div class="info-item">
                  <label>Điện thoại:</label>
                  <span>{{ selectedOrder.dienthoai }}</span>
                </div>
                <div class="info-item">
                  <label>Email:</label>
                  <span>{{ selectedOrder.email }}</span>
                </div>
                <div class="info-item full-width">
                  <label>Địa chỉ:</label>
                  <span>{{ selectedOrder.diachi }}</span>
                </div>
              </div>
            </div>

            <!-- Order Summary -->
            <div class="detail-section">
              <h4>Tổng quan đơn hàng</h4>
              <div class="order-summary">
                <div class="summary-row">
                  <span>Tổng giá:</span>
                  <span>{{ formatPrice(selectedOrder.tonggia) }}</span>
                </div>
                <div class="summary-row" v-if="selectedOrder.phivanchuyen">
                  <span>Phí vận chuyển:</span>
                  <span>{{ formatPrice(selectedOrder.phivanchuyen) }}</span>
                </div>
                <div class="summary-row" v-if="selectedOrder.phigiam">
                  <span>Giảm giá:</span>
                  <span class="discount">-{{ formatPrice(selectedOrder.phigiam) }}</span>
                </div>
                <div class="summary-row final-amount">
                  <span>Thành tiền:</span>
                  <span><strong>{{ formatPrice(selectedOrder.tonggia + (selectedOrder.phivanchuyen || 0) -
                    (selectedOrder.phigiam || 0)) }}</strong></span>
                </div>
              </div>
            </div>

            <!-- Notes -->
            <div class="detail-section" v-if="selectedOrder.noidung || selectedOrder.ghichu">
              <h4>Ghi chú</h4>
              <div class="notes">
                <div v-if="selectedOrder.noidung" class="note-item">
                  <label>Nội dung:</label>
                  <p>{{ selectedOrder.noidung }}</p>
                </div>
                <div v-if="selectedOrder.ghichu" class="note-item">
                  <label>Ghi chú:</label>
                  <p>{{ selectedOrder.ghichu }}</p>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <button @click="showDetailModal = false" class="btn-secondary">Đóng</button>
        </div>
      </div>
    </div>

    <!-- Add/Edit Order Modal -->
    <div v-if="showAddModal || showEditModal" class="modal-overlay">
      <div class="modal large-modal">
        <div class="modal-header">
          <h3>{{ showEditModal ? 'Sửa Đơn Hàng' : 'Thêm Đơn Hàng Mới' }}</h3>
          <button @click="closeModal" class="close-btn">✕</button>
        </div>
        <div class="modal-body">
          <form @submit.prevent="submitOrder" class="order-form">
            <div class="form-grid">
              <div class="form-group">
                <label for="madonhang">Mã đơn hàng *</label>
                <input type="text" id="madonhang" v-model="formData.madonhang" required placeholder="Mã đơn hàng"
                  class="form-input">
              </div>

              <div class="form-group">
                <label for="hoten">Họ tên *</label>
                <input type="text" id="hoten" v-model="formData.hoten" required placeholder="Họ tên khách hàng"
                  class="form-input">
              </div>

              <div class="form-group">
                <label for="dienthoai">Điện thoại *</label>
                <input type="text" id="dienthoai" v-model="formData.dienthoai" required placeholder="Số điện thoại"
                  class="form-input">
              </div>

              <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" v-model="formData.email" placeholder="Email" class="form-input">
              </div>

              <div class="form-group full-width">
                <label for="diachi">Địa chỉ *</label>
                <input type="text" id="diachi" v-model="formData.diachi" required placeholder="Địa chỉ giao hàng"
                  class="form-input">
              </div>

              <div class="form-group">
                <label for="httt">Phương thức TT</label>
                <input type="text" id="httt" v-model="formData.httt" placeholder="Phương thức thanh toán"
                  class="form-input">
              </div>

              <div class="form-group">
                <label for="htgh">Hình thức GH</label>
                <select id="htgh" v-model="formData.htgh" class="form-select">
                  <option value="1">Giao hàng tiêu chuẩn</option>
                  <option value="2">Giao hàng nhanh</option>
                  <option value="3">Giao hàng siêu tốc</option>
                </select>
              </div>

              <div class="form-group">
                <label for="tonggia">Tổng giá *</label>
                <input type="number" id="tonggia" v-model="formData.tonggia" required min="0" placeholder="0"
                  class="form-input">
              </div>

              <div class="form-group">
                <label for="phivanchuyen">Phí vận chuyển</label>
                <input type="number" id="phivanchuyen" v-model="formData.phivanchuyen" min="0" placeholder="0"
                  class="form-input">
              </div>

              <div class="form-group">
                <label for="phigiam">Giảm giá</label>
                <input type="number" id="phigiam" v-model="formData.phigiam" min="0" placeholder="0" class="form-input">
              </div>

              <div class="form-group">
                <label for="tinhtrang">Tình trạng</label>
                <select id="tinhtrang" v-model="formData.tinhtrang" class="form-select">
                  <option value="1">Chờ xác nhận</option>
                  <option value="2">Đã xác nhận</option>
                  <option value="3">Đang giao hàng</option>
                  <option value="4">Đã giao hàng</option>
                  <option value="5">Đã hủy</option>
                </select>
              </div>

              <div class="form-group">
                <label for="thanhtoan">Thanh toán</label>
                <select id="thanhtoan" v-model="formData.thanhtoan" class="form-select">
                  <option value="0">Chưa thanh toán</option>
                  <option value="1">Đã thanh toán</option>
                </select>
              </div>

              <div class="form-group full-width">
                <label for="noidung">Nội dung</label>
                <textarea id="noidung" v-model="formData.noidung" rows="3" placeholder="Nội dung đơn hàng"
                  class="form-textarea"></textarea>
              </div>

              <div class="form-group full-width">
                <label for="ghichu">Ghi chú</label>
                <textarea id="ghichu" v-model="formData.ghichu" rows="3" placeholder="Ghi chú"
                  class="form-textarea"></textarea>
              </div>
            </div>
          </form>
        </div>
        <div class="modal-footer">
          <button @click="closeModal" class="btn-secondary">Hủy</button>
          <button @click="submitOrder" :disabled="submitting" class="btn-primary">
            {{ submitting ? 'Đang xử lý...' : (showEditModal ? 'Cập nhật' : 'Thêm mới') }}
          </button>
        </div>
      </div>
    </div>

    <!-- Delete Confirmation Modal -->
    <div v-if="showDeleteModal" class="modal-overlay">
      <div class="modal">
        <div class="modal-header">
          <h3>Xác nhận xóa</h3>
          <button @click="showDeleteModal = false" class="close-btn">✕</button>
        </div>
        <div class="modal-body">
          <p>Bạn có chắc chắn muốn xóa đơn hàng <strong>#{{ orderToDelete?.madonhang }}</strong>?</p>
          <p class="warning">Hành động này không thể hoàn tác!</p>
        </div>
        <div class="modal-footer">
          <button @click="showDeleteModal = false">Hủy</button>
          <button @click="deleteOrder" :disabled="deleting" class="btn-delete">
            {{ deleting ? 'Đang xóa...' : 'Xóa' }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { toast } from 'vue3-toastify'

import API_BASE_URL_LOCAL from '../Util/APIBase.js';
import router from '../../../router/index.js';
// Reactive data
const orders = ref([])
const loading = ref(false)
const showDetailModal = ref(false)
const showAddModal = ref(false)
const showEditModal = ref(false)
const showDeleteModal = ref(false)
const selectedOrder = ref(null)
const orderToDelete = ref(null)
const deleting = ref(false)
const submitting = ref(false)
const searchQuery = ref('')
const statusFilter = ref('')
const paymentFilter = ref('')

// Pagination
const currentPage = ref(1)
const pageSize = ref(20)

// Form data
const formData = ref({
  id: 0,
  madonhang: '',
  hoten: '',
  dienthoai: '',
  email: '',
  diachi: '',
  httt: '',
  tonggia: 0,
  noidung: '',
  ghichu: '',
  htgh: 1,
  phivanchuyen: 0,
  phigiam: 0,
  tinhtrang: 1,
  thanhtoan: 0
})

// API configuration
const API_BASE_URL = API_BASE_URL_LOCAL

// Computed properties
const filteredOrders = computed(() => {
  let filtered = orders.value

  if (searchQuery.value) {
    const query = searchQuery.value.toLowerCase()
    filtered = filtered.filter(order =>
      order.madonhang?.toLowerCase().includes(query) ||
      order.hoten?.toLowerCase().includes(query) ||
      order.dienthoai?.includes(query) ||
      order.email?.toLowerCase().includes(query)
    )
  }

  if (statusFilter.value !== '') {
    filtered = filtered.filter(order => order.tinhtrang?.toString() === statusFilter.value)
  }

  if (paymentFilter.value !== '') {
    filtered = filtered.filter(order => order.thanhtoan?.toString() === paymentFilter.value)
  }

  return filtered
})

const totalOrders = computed(() => filteredOrders.value.length)
const totalPages = computed(() => Math.ceil(totalOrders.value / pageSize.value))
const startIndex = computed(() => (currentPage.value - 1) * pageSize.value)
const endIndex = computed(() => Math.min(startIndex.value + pageSize.value, totalOrders.value))

const paginatedOrders = computed(() => {
  return filteredOrders.value.slice(startIndex.value, endIndex.value)
})

const visiblePages = computed(() => {
  const pages = []
  const maxVisiblePages = 5
  let startPage = Math.max(1, currentPage.value - Math.floor(maxVisiblePages / 2))
  let endPage = Math.min(totalPages.value, startPage + maxVisiblePages - 1)

  if (endPage - startPage + 1 < maxVisiblePages) {
    startPage = Math.max(1, endPage - maxVisiblePages + 1)
  }

  for (let i = startPage; i <= endPage; i++) {
    pages.push(i)
  }

  return pages
})

const hasActiveFilters = computed(() => {
  return searchQuery.value || statusFilter.value !== '' || paymentFilter.value !== ''
})

// Methods
const handleSearch = () => {
  currentPage.value = 1
}

const handleFilterChange = () => {
  currentPage.value = 1
}

const handlePageSizeChange = () => {
  currentPage.value = 1
}

const clearSearch = () => {
  searchQuery.value = ''
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
const fetchOrders = async () => {
  loading.value = true
  try {
    const response = await fetch(`${API_BASE_URL}/oders`)
    if (!response.ok) throw new Error('Network response was not ok')
    const data = await response.json()
    orders.value = data
  } catch (error) {
    console.error('Error fetching orders:', error)
    toast.error('Lỗi khi tải danh sách đơn hàng')
  } finally {
    loading.value = false
  }
}

const createOrderAPI = async (data) => {
  const response = await fetch(`${API_BASE_URL}/oders`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(data)
  })
  if (!response.ok) throw new Error('Failed to create order')
  return await response.json()
}

const updatePayAPI = async (orderId, status) => {
  const response = await fetch(`${API_BASE_URL}/oders/pay/${orderId}`, {
    method: 'PUT'
  })

  if (!response.ok) throw new Error('Failed to update payment')
  return await response.json()
}


const updateOrderAPI = async (orderId, status) => {
  const response = await fetch(`${API_BASE_URL}/oders/status/${orderId}/${status}`, {
    method: 'PUT'
  })

  if (!response.ok) throw new Error('Failed to update order')
  return await response.json()
}

const updateOrdersAPI = async (orderId, data) => {
  if (!orderId) {
    console.error('Order ID is null/undefined!', data);
    throw new Error('Order ID không hợp lệ');
  }

  const response = await fetch(`${API_BASE_URL}/oders/${orderId}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data)
  });
  if (!response.ok) throw new Error('Failed to update order');
  return await response.json();
}




const deleteOrderAPI = async (orderId) => {
  const response = await fetch(`${API_BASE_URL}/oders/${orderId}`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
    }
  })
  if (!response.ok) throw new Error('Failed to delete order')
  return true
}

const updateOrderStatus = async (order) => {
  try {
    await updateOrderAPI(order.id, order.tinhtrang)
    toast.success('Cập nhật tình trạng thành công')
  } catch (error) {
    console.error('Error updating order status:', error)
    toast.success('Cập nhật tình trạng thành công')
  }
}


const updatePaymentStatus = async (order) => {
  try {
    await updatePayAPI(order.id)
    toast.success('Cập nhật thanh toán thành công')
  } catch (error) {
    console.error('Error updating payment status:', error)
    toast.success('Cập nhật thanh toán thành công')
  }
}

// Utility functions
const getShippingMethod = (htgh) => {
  const methods = {
    1: 'Giao hàng tiêu chuẩn',
    2: 'Giao hàng nhanh',
    3: 'Giao hàng siêu tốc'
  }
  return methods[htgh] || 'Không xác định'
}

const formatPrice = (price) => {
  if (!price) return '0₫'
  return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(price)
}

const formatDate = (dateString) => {
  if (!dateString) return 'N/A'
  return new Date(dateString).toLocaleDateString('vi-VN')
}

// Order methods
const viewOrderDetail = (order) => {
  router.push(`/admin/order-detail/${order.id}`)
}


const editOrder = (order) => {
  selectedOrder.value = order
  formData.value = {
    id: order.id,
    madonhang: order.madonhang || '',
    hoten: order.hoten || '',
    dienthoai: order.dienthoai || '',
    email: order.email || '',
    diachi: order.diachi || '',
    httt: order.httt || '',
    tonggia: order.tonggia || 0,
    noidung: order.noidung || '',
    ghichu: order.ghichu || '',
    htgh: order.htgh || 1,
    phivanchuyen: order.phivanchuyen || 0,
    phigiam: order.phigiam || 0,
    tinhtrang: order.tinhtrang || 0,
    thanhtoan: order.thanhtoan || 0,
    // cartItems: order.cartItems?.map(i => ({ // bắt buộc phải map đúng định dạng FE gửi lên BE
    //   productId: i.idSanpham,
    //   quantity: i.soluong
    // })) || []
  }
  showEditModal.value = true
}


const openAddModal = () => {
  resetForm()
  showAddModal.value = true
}

const closeModal = () => {
  showAddModal.value = false
  showEditModal.value = false
  selectedOrder.value = null
  resetForm()
}

const resetForm = () => {
  formData.value = {
    id: 0,
    madonhang: '',
    hoten: '',
    dienthoai: '',
    email: '',
    diachi: '',
    httt: '',
    tonggia: 0,
    noidung: '',
    ghichu: '',
    htgh: 1,
    phivanchuyen: 0,
    phigiam: 0,
    tinhtrang: 0,
    thanhtoan: 0,
    // cartItems: [] // thêm đây
  }
}


const submitOrder = async () => {
  if (!formData.value.madonhang.trim()) {
    toast.error('Vui lòng nhập mã đơn hàng')
    return
  }

  if (!formData.value.hoten.trim()) {
    toast.error('Vui lòng nhập họ tên khách hàng')
    return
  }

  if (!formData.value.dienthoai.trim()) {
    toast.error('Vui lòng nhập số điện thoại')
    return
  }

  if (!formData.value.diachi.trim()) {
    toast.error('Vui lòng nhập địa chỉ')
    return
  }

  if (!formData.value.tonggia || formData.value.tonggia <= 0) {
    toast.error('Vui lòng nhập tổng giá hợp lệ')
    return
  }

  submitting.value = true
  try {
    if (showEditModal.value && selectedOrder.value) {
      const updatedOrder = await updateOrdersAPI(selectedOrder.value.id, formData.value)

      // Update local list
      const index = orders.value.findIndex(o => o.id === selectedOrder.value.id)
      if (index !== -1) {
        orders.value[index] = { ...orders.value[index], ...updatedOrder }
      }

      showEditModal.value = false
      selectedOrder.value = null
      toast.success('Đơn hàng đã được cập nhật thành công')
    }


    resetForm()
  } catch (error) {
    console.error('Error saving order:', error)
    toast.error('Có lỗi xảy ra khi lưu đơn hàng')
  } finally {
    submitting.value = false
  }
}

const confirmDeleteOrder = (order) => {
  orderToDelete.value = order
  showDeleteModal.value = true
}

const deleteOrder = async () => {
  if (!orderToDelete.value) return

  deleting.value = true
  try {
    await deleteOrderAPI(orderToDelete.value.id)
    orders.value = orders.value.filter(order => order.id !== orderToDelete.value.id)
    showDeleteModal.value = false
    orderToDelete.value = null
    toast.success('Đơn hàng đã được xóa thành công')
  } catch (error) {
    console.error('Error deleting order:', error)
    toast.error('Có lỗi xảy ra khi xóa đơn hàng')
  } finally {
    deleting.value = false
  }
}

const resetFilters = () => {
  searchQuery.value = ''
  statusFilter.value = ''
  paymentFilter.value = ''
  currentPage.value = 1
}

// Lifecycle
onMounted(() => {
  fetchOrders()
})
</script>

<style scoped>
.order-management {
  padding: 20px;
  background: #f5f5f5;
  min-height: 100vh;
}

/* Header */
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  background: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.header-left h1 {
  margin: 0 0 5px 0;
  color: #333;
  font-size: 24px;
}

.header-left p {
  margin: 0;
  color: #666;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 15px;
}

.order-count {
  background: #e3f2fd;
  padding: 8px 12px;
  border-radius: 6px;
  color: #1976d2;
  font-weight: 500;
}

.btn-add {
  background: #d32f2f;
  color: white;
  border: none;
  padding: 10px 16px;
  border-radius: 6px;
  cursor: pointer;
  font-weight: 500;
  transition: background 0.3s;
}

.btn-add:hover {
  background: #b71c1c;
}

/* Search and Filters */
.search-filters {
  background: white;
  padding: 20px;
  border-radius: 8px;
  margin-bottom: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.search-section {
  position: relative;
  margin-bottom: 15px;
}

.search-input {
  width: 100%;
  padding: 12px 40px 12px 12px;
  border: 1px solid #ddd;
  border-radius: 6px;
  font-size: 14px;
  transition: border 0.3s;
}

.search-input:focus {
  outline: none;
  border-color: #d32f2f;
}

.clear-search {
  position: absolute;
  right: 12px;
  top: 50%;
  transform: translateY(-50%);
  background: none;
  border: none;
  cursor: pointer;
  color: #999;
  padding: 4px;
}

.clear-search:hover {
  color: #666;
}

.filters-section {
  display: flex;
  gap: 10px;
  align-items: center;
  flex-wrap: wrap;
}

.filter-select {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 6px;
  background: white;
  min-width: 150px;
  font-size: 14px;
}

.btn-reset {
  background: #666;
  color: white;
  border: none;
  padding: 10px 16px;
  border-radius: 6px;
  cursor: pointer;
  transition: background 0.3s;
}

.btn-reset:hover {
  background: #555;
}

/* Loading */
.loading {
  text-align: center;
  padding: 40px;
  color: #666;
}

.spinner {
  width: 40px;
  height: 40px;
  border: 4px solid #f3f3f3;
  border-top: 4px solid #d32f2f;
  border-radius: 50%;
  animation: spin 1s linear infinite;
  margin: 0 auto 15px;
}

@keyframes spin {
  0% {
    transform: rotate(0deg);
  }

  100% {
    transform: rotate(360deg);
  }
}

/* Table Container */
.table-container {
  background: white;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.table-wrapper {
  overflow-x: auto;
}

.orders-table {
  width: 100%;
  border-collapse: collapse;
}

.orders-table th,
.orders-table td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #eee;
}

.orders-table th {
  background: #f8f9fa;
  font-weight: 600;
  color: #333;
  position: sticky;
  top: 0;
  z-index: 10;
}

.orders-table tr:hover td {
  background: #f8f9fa;
}

/* Table Cells */
.order-code {
  font-weight: 600;
  color: #d32f2f;
}

.customer-info {
  max-width: 200px;
}

.customer-name {
  font-weight: 600;
  margin-bottom: 4px;
}

.customer-email {
  font-size: 12px;
  color: #666;
}

.customer-phone {
  font-weight: 500;
}

.order-date {
  color: #666;
  font-size: 14px;
}

.shipping-method {
  font-size: 14px;
  color: #666;
}

.total-amount {
  text-align: right;
  font-size: 16px;
  color: #d32f2f;
}

/* Status and Payment Selects */
.status-select,
.payment-select {
  padding: 6px 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 12px;
  width: 100%;
  cursor: pointer;
}

.status-0 {
  background: #fff3cd;
  color: #856404;
}

.status-1 {
  background: #d1ecf1;
  color: #0c5460;
}

.status-2 {
  background: #ffeaa7;
  color: #e17055;
}

.status-3 {
  background: #d4edda;
  color: #155724;
}

.status-4 {
  background: #f8d7da;
  color: #721c24;
}

.payment-0 {
  background: #ffebee;
  color: #d32f2f;
}

.payment-1 {
  background: #e8f5e8;
  color: #388e3c;
}

/* Action Buttons */
.action-buttons {
  display: flex;
  gap: 6px;
}

.action-buttons button {
  padding: 8px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
  transition: all 0.3s;
}

.btn-view {
  background: #e3f2fd;
  color: #1976d2;
}

.btn-view:hover {
  background: #bbdefb;
}

.btn-edit {
  background: #fff3e0;
  color: #f57c00;
}

.btn-edit:hover {
  background: #ffe0b2;
}

.btn-delete {
  background: #ffebee;
  color: #d32f2f;
}

.btn-delete:hover {
  background: #ffcdd2;
}

/* Empty state */
.empty-state {
  text-align: center;
  padding: 60px 20px;
  color: #666;
}

.empty-icon {
  font-size: 48px;
  margin-bottom: 15px;
  opacity: 0.5;
}

.empty-state h3 {
  margin: 0 0 10px 0;
  color: #333;
}

.empty-state p {
  margin: 0 0 20px 0;
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
  background: #d32f2f;
  color: white;
  border-color: #d32f2f;
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
  padding: 20px;
}

.modal {
  background: white;
  border-radius: 8px;
  max-width: 600px;
  width: 100%;
  max-height: 90vh;
  overflow-y: auto;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.3);
}

.large-modal {
  max-width: 800px;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
  border-bottom: 1px solid #eee;
}

.modal-header h3 {
  margin: 0;
  color: #333;
}

.close-btn {
  background: none;
  border: none;
  font-size: 18px;
  cursor: pointer;
  color: #666;
  padding: 4px;
  border-radius: 4px;
  transition: background 0.3s;
}

.close-btn:hover {
  background: #f5f5f5;
}

.modal-body {
  padding: 20px;
}

.modal-footer {
  display: flex;
  gap: 10px;
  justify-content: flex-end;
  padding: 20px;
  border-top: 1px solid #eee;
}

.modal-footer button {
  padding: 10px 20px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-size: 14px;
  transition: all 0.3s;
}

.btn-primary {
  background: #d32f2f;
  color: white;
}

.btn-primary:hover:not(:disabled) {
  background: #b71c1c;
}

.btn-secondary {
  background: #666;
  color: white;
}

.btn-secondary:hover {
  background: #555;
}

.btn-delete {
  background: #d32f2f;
  color: white;
}

.btn-delete:hover:not(:disabled) {
  background: #b71c1c;
}

.btn-primary:disabled,
.btn-delete:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.warning {
  color: #d32f2f;
  font-weight: 500;
}

/* Order Detail */
.order-detail {
  padding: 0;
}

.detail-section {
  margin-bottom: 24px;
  padding: 16px;
  background: #f8f9fa;
  border-radius: 8px;
}

.detail-section h4 {
  margin: 0 0 12px 0;
  color: #333;
  font-size: 16px;
}

.info-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 12px;
}

.info-item {
  display: flex;
  flex-direction: column;
}

.info-item.full-width {
  grid-column: 1 / -1;
}

.info-item label {
  font-weight: 600;
  color: #666;
  font-size: 12px;
  margin-bottom: 4px;
}

.info-item span {
  color: #333;
  font-size: 14px;
}

/* Order Summary */
.order-summary {
  background: white;
  padding: 16px;
  border-radius: 8px;
  border: 1px solid #e0e0e0;
}

.summary-row {
  display: flex;
  justify-content: space-between;
  padding: 8px 0;
  border-bottom: 1px solid #f0f0f0;
}

.summary-row:last-child {
  border-bottom: none;
}

.summary-row.final-amount {
  border-top: 2px solid #e0e0e0;
  font-size: 16px;
  color: #d32f2f;
}

.discount {
  color: #28a745;
}

/* Notes */
.notes {
  background: white;
  padding: 16px;
  border-radius: 8px;
  border: 1px solid #e0e0e0;
}

.note-item {
  margin-bottom: 12px;
}

.note-item:last-child {
  margin-bottom: 0;
}

.note-item label {
  font-weight: 600;
  color: #666;
  font-size: 12px;
  margin-bottom: 4px;
  display: block;
}

.note-item p {
  margin: 0;
  color: #333;
  font-size: 14px;
  line-height: 1.4;
}

/* Form */
.order-form {
  padding: 0;
}

.form-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 16px;
}

.form-group {
  display: flex;
  flex-direction: column;
}

.form-group.full-width {
  grid-column: 1 / -1;
}

.form-group label {
  font-weight: 600;
  margin-bottom: 6px;
  color: #333;
  font-size: 14px;
}

.form-input,
.form-select,
.form-textarea {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 6px;
  font-size: 14px;
  transition: border 0.3s;
}

.form-input:focus,
.form-select:focus,
.form-textarea:focus {
  outline: none;
  border-color: #d32f2f;
}

.form-textarea {
  resize: vertical;
  min-height: 80px;
}

/* Responsive */
@media (max-width: 768px) {
  .order-management {
    padding: 10px;
  }

  .header {
    flex-direction: column;
    gap: 15px;
    align-items: flex-start;
  }

  .header-right {
    width: 100%;
    justify-content: space-between;
  }

  .filters-section {
    flex-direction: column;
    align-items: stretch;
  }

  .filter-select {
    min-width: auto;
  }

  .form-grid {
    grid-template-columns: 1fr;
  }

  .info-grid {
    grid-template-columns: 1fr;
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

  .action-buttons {
    flex-direction: column;
  }
}
</style>