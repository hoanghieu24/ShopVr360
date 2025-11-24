<template>
  <div class="product-management">
    <!-- Header -->
    <div class="header">
      <div class="header-left">
        <h1>Quản Lý Sản Phẩm</h1>
        <p>Danh sách sản phẩm hệ thống</p>
      </div>
      <div class="header-right">
        <span class="product-count">Tổng: {{ totalProducts }} sản phẩm</span>
        <button class="btn-add" @click="showAddModal = true">
          ➕ Thêm sản phẩm
        </button>
      </div>
    </div>

    <!-- Bulk Actions -->
    <div v-if="selectedProducts.length > 0" class="bulk-actions">
      <div class="bulk-info">
        <span>{{ selectedProducts.length }} sản phẩm được chọn</span>
      </div>
      <div class="bulk-buttons">
        <button @click="bulkUpdateStatus(1)">👁️ Hiển thị</button>
        <button @click="bulkUpdateStatus(0)">👁️‍🗨️ Ẩn</button>
        <button @click="bulkUpdateFlag('noibat', 1)">⭐ Nổi bật</button>
        <button @click="bulkUpdateFlag('noibat', 0)">⚪ Bỏ nổi bật</button>
        <button @click="bulkUpdateFlag('banchay', 1)">🔥 Bán chạy</button>
        <button @click="bulkUpdateFlag('banchay', 0)">⚪ Bỏ bán chạy</button>
        <button @click="confirmBulkDelete" class="btn-delete">
          🗑️ Xóa ({{ selectedProducts.length }})
        </button>
        <button @click="clearSelection" class="btn-clear">✕ Bỏ chọn</button>
      </div>
    </div>

    <!-- Search and Filters -->
    <div class="search-filters">
      <div class="search-section">
        <input type="text" v-model="searchQuery" placeholder="Tìm kiếm theo tên, mã SP, tags..." class="search-input"
          @input="handleSearch" />
        <button v-if="searchQuery" @click="clearSearch" class="clear-search">✕</button>
      </div>

      <div class="filters-section">
        <select v-model="categoryFilter" class="filter-select" @change="handleFilterChange">
          <option value="">Tất cả danh mục</option>

          <option v-for="item in categories" :key="item.id" :value="item.id">
            {{ item.tenVi }}
          </option>
        </select>


        <select v-model="statusFilter" class="filter-select" @change="handleFilterChange">
          <option value="">Tất cả trạng thái</option>
          <option value="1">Đang hiển thị</option>
          <option value="0">Đang ẩn</option>
        </select>

        <select v-model="featuredFilter" class="filter-select" @change="handleFilterChange">
          <option value="">Tất cả nổi bật</option>
          <option value="1">Nổi bật</option>
          <option value="0">Không nổi bật</option>
        </select>

        <select v-model="hotFilter" class="filter-select" @change="handleFilterChange">
          <option value="">Tất cả bán chạy</option>
          <option value="1">Bán chạy</option>
          <option value="0">Không bán chạy</option>
        </select>

        <button @click="resetFilters" class="btn-reset">Đặt lại</button>
      </div>
    </div>

    <!-- Loading -->
    <div v-if="loading" class="loading">
      <div class="spinner"></div>
      <p>Đang tải dữ liệu...</p>
    </div>

    <!-- Products Table -->
    <div v-else class="table-container">
      <div class="table-wrapper">
        <table class="products-table">
          <thead>
            <tr>
              <th class="sticky-checkbox">
                <input type="checkbox" :checked="isAllSelected" @change="toggleSelectAll" />
              </th>
              <th class="sticky-order">STT</th>
              <th>Hình ảnh</th>
              <th>Thông tin SP</th>
              <th>Danh mục</th>
              <th>Giá</th>
              <th>Tồn kho</th>
              <th>Trạng thái</th>
              <th>Lượt xem</th>
              <th>Ngày tạo</th>
              <th class="sticky-actions">Thao tác</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(product, index) in paginatedProducts" :key="product.id"
              :class="{ selected: isProductSelected(product.id) }">
              <td class="sticky-checkbox">
                <input type="checkbox" :checked="isProductSelected(product.id)"
                  @change="toggleProductSelection(product.id)" />
              </td>
              <td class="sticky-order">{{ getProductIndex(index) }}</td>
              <td class="image-cell">
                <div class="product-img">
                  <img :src="getImageUrl(product.photo)" :alt="product.ten_vi" @error="handleImageError" />
                  <div v-if="product.gianewp && product.giaban" class="sale-badge">
                    -{{ calculateDiscount(product.giaban, product.gianewp) }}%
                  </div>
                </div>
              </td>
              <td class="info-cell">
                <div class="product-name">{{ product.tenVi }}</div>
                <div v-if="product.ten_en" class="product-name-en">{{ product.ten_en }}</div>
                <div class="product-code">Mã: {{ product.masp || 'N/A' }}</div>
                <div class="product-tags" v-if="product.tags">
                  <span v-for="tag in getTags(product.tags)" :key="tag">#{{ tag }}</span>
                </div>
              </td>
              <td class="category-cell">
                {{ getCategoryName(product.idList) }}
              </td>
              <td class="price-cell">
                <div class="current-price">{{ formatPrice(product.giaban) }}</div>
                <div v-if="product.gianewp" class="sale-price">{{ formatPrice(product.gianewp) }}</div>
                <div v-if="product.giacu && product.giacu > product.giaban" class="old-price">
                  {{ formatPrice(product.giacu) }}
                </div>
              </td>
              <td class="stock-cell">
                <span :class="{ 'low-stock': product.soluong <= 10 }">
                  {{ product.soluong || 0 }}
                </span>
              </td>
              <td class="status-cell">
                <div class="status-buttons">
                  <button @click="toggleFlag(product, 'noibat')" :class="{ active: product.noibat === 1 }"
                    title="Nổi bật">⭐</button>
                  <button @click="toggleFlag(product, 'banchay')" :class="{ active: product.banchay === 1 }"
                    title="Bán chạy">🔥</button>
                  <button @click="toggleFlag(product, 'new')" :class="{ active: product.new === 1 }"
                    title="Mới">🆕</button>
                  <button @click="toggleFlag(product, 'khuyenmai')" :class="{ active: product.khuyenmai === 1 }"
                    title="Khuyến mãi">🎁</button>
                  <button @click="toggleFlag(product, 'nenmua')" :class="{ active: product.nenmua === 1 }"
                    title="Nên mua">👍</button>
                  <button @click="toggleStatus(product , )" :class="{ active: product.hienthi === 1 }"
                    :title="product.hienthi === 1 ? 'Đang hiển thị' : 'Đang ẩn'">{{ product.hienthi === 1 ? '👁️' :
                      '👁️‍🗨️' }}</button>
                </div>
              </td>
              <td class="views-cell">
                {{ product.luotxem || 0 }}
              </td>
              <td class="date-cell">
                {{ formatDate(product.ngaytao) }}
              </td>
              <td class="sticky-actions">
                <div class="action-buttons">
                  <button @click="editProduct(product)" title="Sửa" class="btn-edit">✏️</button>
                  <button @click="confirmDeleteProduct(product)" title="Xóa" class="btn-delete">🗑️</button>
                  <button @click="viewProduct(product)" title="Xem" class="btn-view">👀</button>
                </div>
              </td>
            </tr>
          </tbody>
        </table>

        <!-- Empty State -->
        <div v-if="filteredProducts.length === 0" class="empty-state">
          <div class="empty-icon">📦</div>
          <h3>Không tìm thấy sản phẩm</h3>
          <p v-if="hasActiveFilters">
            Thử thay đổi điều kiện tìm kiếm hoặc bộ lọc
          </p>
          <p v-else>Chưa có sản phẩm nào trong hệ thống</p>
          <button v-if="hasActiveFilters" @click="resetFilters" class="btn-reset">
            Xóa bộ lọc
          </button>
          <button v-else @click="showAddModal = true" class="btn-add">
            ➕ Thêm sản phẩm đầu tiên
          </button>
        </div>
      </div>

      <!-- Pagination -->
      <div v-if="filteredProducts.length > 0" class="pagination">
        <div class="pagination-info">
          Hiển thị {{ startIndex + 1 }}-{{ endIndex }} của {{ totalProducts }} sản phẩm
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
            <option value="100">100 / trang</option>
          </select>
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
          <p>Bạn có chắc chắn muốn xóa sản phẩm <strong>{{ productToDelete?.ten_vi }}</strong>?</p>
          <p class="warning">Hành động này không thể hoàn tác!</p>
        </div>
        <div class="modal-footer">
          <button @click="showDeleteModal = false">Hủy</button>
          <button @click="deleteProduct" :disabled="deleting" class="btn-delete">
            {{ deleting ? 'Đang xóa...' : 'Xóa' }}
          </button>
        </div>
      </div>
    </div>

    <!-- Bulk Delete Confirmation Modal -->
    <div v-if="showBulkDeleteModal" class="modal-overlay">
      <div class="modal">
        <div class="modal-header">
          <h3>Xác nhận xóa nhiều</h3>
          <button @click="showBulkDeleteModal = false" class="close-btn">✕</button>
        </div>
        <div class="modal-body">
          <p>Bạn có chắc chắn muốn xóa <strong>{{ selectedProducts.length }} sản phẩm</strong> đã chọn?</p>
          <div class="selected-list">
            <div v-for="product in selectedProducts" :key="product.id" class="selected-item">
              <img :src="getImageUrl(product.photo)" class="thumb">
              <span class="product-name">{{ product.ten_vi }}</span>
              <span class="product-id">(ID: {{ product.id }})</span>
            </div>
          </div>
          <p class="warning">Hành động này không thể hoàn tác!</p>
        </div>
        <div class="modal-footer">
          <button @click="showBulkDeleteModal = false">Hủy</button>
          <button @click="bulkDeleteProducts" :disabled="deleting" class="btn-delete">
            {{ deleting ? 'Đang xóa...' : `Xóa (${selectedProducts.length})` }}
          </button>
        </div>
      </div>
    </div>

    <!-- Add/Edit Product Modal -->
    <div v-if="showAddModal || showEditModal" class="modal-overlay">
      <div class="modal large-modal">
        <div class="modal-header">
          <h3>{{ showEditModal ? 'Sửa Sản Phẩm' : 'Thêm Sản Phẩm Mới' }}</h3>
          <button @click="closeModal" class="close-btn">✕</button>
        </div>
        <div class="modal-body">
          <form @submit.prevent="submitProduct" class="product-form">
            <div class="form-section">
              <h4>Thông tin cơ bản</h4>
              <div class="form-row">
                <div class="form-group">
                  <label for="tenVi">Tên sản phẩm (Tiếng Việt) *</label>
                  <input type="text" id="tenVi" v-model="formData.tenVi" required
                    placeholder="Nhập tên sản phẩm tiếng Việt" class="form-input">
                </div>
                <div class="form-group">
                  <label for="ten_en">Tên sản phẩm (Tiếng Anh)</label>
                  <input type="text" id="tenEn" v-model="formData.tenEn" placeholder="Nhập tên sản phẩm tiếng Anh"
                    class="form-input">
                </div>
              </div>

              <div class="form-row">
                <div class="form-group">
                  <label for="masp">Mã sản phẩm</label>
                  <input type="text" id="masp" v-model="formData.masp" placeholder="Mã sản phẩm" class="form-input">
                </div>
                <div class="form-group">
                  <label for="id_list">Danh mục *</label>
                  <select id="id_list" v-model="formData.idList" required class="form-select">
                    <option value="">Chọn danh mục</option>
                    <option v-for="item in categories" :key="item.id" :value="item.id">
                      {{ item.tenVi }}
                    </option>
                  </select>

                </div>
              </div>

              <div class="form-row">
                <div class="form-group">
                  <label for="giaban">Giá bán *</label>
                  <input type="number" id="giaban" v-model="formData.giaban" required min="0" placeholder="Giá bán"
                    class="form-input">
                </div>
                <div class="form-group">
                  <label for="gianewp">Giá khuyến mãi</label>
                  <input type="number" id="gianewp" v-model="formData.gianewp" min="0" placeholder="Giá khuyến mãi"
                    class="form-input">
                </div>
                <div class="form-group">
                  <label for="giacu">Giá cũ</label>
                  <input type="number" id="giacu" v-model="formData.giacu" min="0" placeholder="Giá cũ"
                    class="form-input">
                </div>
              </div>

              <div class="form-row">
                <div class="form-group">
                  <label for="soluong">Số lượng tồn kho</label>
                  <input type="number" id="soluong" v-model="formData.soluong" min="0" placeholder="Số lượng"
                    class="form-input">
                </div>
                <div class="form-group">
                  <label for="stt">Thứ tự hiển thị</label>
                  <input type="number" id="stt" v-model="formData.stt" min="1" placeholder="Thứ tự" class="form-input">
                </div>
              </div>
            </div>

            <div class="form-section">
              <h4>Trạng thái sản phẩm</h4>
              <div class="status-grid">
                <label class="status-checkbox">
                  <input type="checkbox" v-model="formData.noibat" :true-value="1" :false-value="0">
                  <span class="status-icon">⭐</span>
                  <span>Nổi bật</span>
                </label>
                <label class="status-checkbox">
                  <input type="checkbox" v-model="formData.banchay" :true-value="1" :false-value="0">
                  <span class="status-icon">🔥</span>
                  <span>Bán chạy</span>
                </label>
                <label class="status-checkbox">
                  <input type="checkbox" v-model="formData.new" :true-value="1" :false-value="0">
                  <span class="status-icon">🆕</span>
                  <span>Mới</span>
                </label>
                <label class="status-checkbox">
                  <input type="checkbox" v-model="formData.khuyenmai" :true-value="1" :false-value="0">
                  <span class="status-icon">🎁</span>
                  <span>Khuyến mãi</span>
                </label>
                <label class="status-checkbox">
                  <input type="checkbox" v-model="formData.nenmua" :true-value="1" :false-value="0">
                  <span class="status-icon">👍</span>
                  <span>Nên mua</span>
                </label>
                <label class="status-checkbox">
                  <input type="checkbox" v-model="formData.hienthi" :true-value="1" :false-value="0">
                  <span class="status-icon">👁️</span>
                  <span>Hiển thị</span>
                </label>
              </div>
            </div>

            <div class="form-section">
              <h4>Mô tả & Nội dung</h4>
              <div class="form-group">
                <label for="mota_vi">Mô tả ngắn (Tiếng Việt)</label>
                <textarea id="mota_vi" v-model="formData.motaVi" rows="3" placeholder="Mô tả ngắn về sản phẩm..."
                  class="form-textarea"></textarea>
              </div>
              <div class="form-group">
                <label for="noidung_vi">Nội dung chi tiết (Tiếng Việt)</label>
                <textarea id="noidung_vi" v-model="formData.noidungVi" rows="5"
                  placeholder="Nội dung chi tiết về sản phẩm..." class="form-textarea"></textarea>
              </div>
            </div>

            <div class="form-section">
              <h4>Hình ảnh & SEO</h4>
              <div class="form-row">
                <div class="form-group">
                  <label for="photo">URL hình ảnh chính</label>
                  <input type="text" id="photo" v-model="formData.photo" placeholder="Đường dẫn hình ảnh"
                    class="form-input">
                </div>
                <div class="form-group">
                  <label for="tags">Tags (phân cách bằng dấu phẩy)</label>
                  <input type="text" id="tags" v-model="formData.tags" placeholder="tag1, tag2, tag3"
                    class="form-input">
                </div>
              </div>
            </div>
          </form>
        </div>
        <div class="modal-footer">
          <button @click="closeModal">Hủy</button>
          <button @click="submitProduct" :disabled="submitting" class="btn-primary">
            {{ submitting ? 'Đang xử lý...' : (showEditModal ? 'Cập nhật' : 'Thêm mới') }}
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
const products = ref([])
const categories = ref([])
const loading = ref(false)
const showDeleteModal = ref(false)
const showBulkDeleteModal = ref(false)
const showAddModal = ref(false)
const showEditModal = ref(false)
const productToDelete = ref(null)
const productToEdit = ref(null)
const deleting = ref(false)
const submitting = ref(false)
const searchQuery = ref('')
const categoryFilter = ref('')
const statusFilter = ref('')
const featuredFilter = ref('')
const hotFilter = ref('')
const selectedProducts = ref([])

// Pagination data
const currentPage = ref(1)
const pageSize = ref(20)

// Form data
const formData = ref({
  id: 0,           // Thêm id ở đây, mặc định 0 cho add mới
  tenVi: '',
  tenEn: '',
  masp: '',
  idList: '',
  giaban: 0,
  gianewp: 0,
  giacu: 0,
  soluong: 0,
  stt: 1,
  noibat: 0,
  banchay: 0,
  newField: 0,     // đổi new -> newField để khớp backend
  khuyenmai: 0,
  nenmua: 0,
  hienthi: 1,
  motaVi: '',
  noidungVi: '',
  photo: '',
  tags: '',
  type: 'san-pham'
})



// API configuration
const API_BASE_URL = 'https://jq6kflwj-5173.asse.devtunnels.ms/api'

// Computed properties
const filteredProducts = computed(() => {
  let filtered = products.value

  if (searchQuery.value) {
    const query = searchQuery.value.toLowerCase()
    filtered = filtered.filter(product =>
      product.ten_vi?.toLowerCase().includes(query) ||
      product.ten_en?.toLowerCase().includes(query) ||
      product.masp?.toLowerCase().includes(query) ||
      product.tags?.toLowerCase().includes(query)
    )
  }

  if (categoryFilter.value) {
    filtered = filtered.filter(product => product.idList?.toString() === categoryFilter.value)
  }

  if (statusFilter.value) {
    filtered = filtered.filter(product => product.hienthi?.toString() === statusFilter.value)
  }

  if (featuredFilter.value) {
    filtered = filtered.filter(product => product.noibat?.toString() === featuredFilter.value)
  }

  if (hotFilter.value) {
    filtered = filtered.filter(product => product.banchay?.toString() === hotFilter.value)
  }

  return filtered
})

const totalProducts = computed(() => filteredProducts.value.length)
const totalPages = computed(() => Math.ceil(totalProducts.value / pageSize.value))
const startIndex = computed(() => (currentPage.value - 1) * pageSize.value)
const endIndex = computed(() => Math.min(startIndex.value + pageSize.value, totalProducts.value))

const paginatedProducts = computed(() => {
  return filteredProducts.value.slice(startIndex.value, endIndex.value)
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
  return searchQuery.value || categoryFilter.value || statusFilter.value || featuredFilter.value || hotFilter.value
})

const isAllSelected = computed(() => {
  return paginatedProducts.value.length > 0 &&
    paginatedProducts.value.every(product => selectedProducts.value.some(selected => selected.id === product.id))
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

const getProductIndex = (index) => {
  return startIndex.value + index + 1
}

// API calls
const fetchProducts = async () => {
  loading.value = true
  try {
    const response = await fetch(`${API_BASE_URL}/products`)
    if (!response.ok) throw new Error('Network response was not ok')
    products.value = await response.json()
    toast.success('Danh sách sản phẩm đã được tải thành công')
  } catch (error) {
    console.error('Error fetching products:', error)
    alert('Lỗi khi tải danh sách sản phẩm')
  } finally {
    loading.value = false
  }
}

const fetchCategories = async () => {
  try {
    const response = await fetch(`${API_BASE_URL}/productLists`)
    if (!response.ok) throw new Error('Network response was not ok')
    categories.value = await response.json()
    toast.success('Danh sách danh mục đã được tải thành công')
  } catch (error) {
    console.error('Error fetching categories:', error)
  }
}

const deleteProductAPI = async (productId) => {
  const response = await fetch(`${API_BASE_URL}/products/${productId}`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
    }
  })
  if (!response.ok) throw new Error('Failed to delete product')
  toast.success('Sản phẩm đã được xóa thành công')
  return true
}

const bulkDeleteProductsAPI = async (productIds) => {
  const response = await fetch(`${API_BASE_URL}/products`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ productIds })
  })
  if (!response.ok) throw new Error('Failed to bulk delete products')
  toast.success('Các sản phẩm đã được xóa thành công')
  return true
}

const updateProductAPI = async (productId, data) => {
  const response = await fetch(`${API_BASE_URL}/products`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ id: productId, ...data })
  })
  if (!response.ok) throw new Error('Failed to update product')
  toast.success('Sản phẩm đã được cập nhật thành công')
  return await response.json()
}

const createProductAPI = async (data) => {
  const response = await fetch(`${API_BASE_URL}/products`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(data)
  })
  if (!response.ok) throw new Error('Failed to create product')
  toast.success('Sản phẩm đã được tạo thành công')
  return await response.json()
}

const updateProductFlagAPI = async (productId, field) => {
  const response = await fetch(`${API_BASE_URL}/products/${productId}?type=${field}`, {
    method: 'PUT',
  })
  
  if (!response.ok) {
    throw new Error('Failed to toggle product flag')
  }
  toast.success('Cờ sản phẩm đã được chuyển đổi thành công')
  return true
}


const bulkUpdateStatusAPI = async (productIds, status) => {
  const response = await fetch(`${API_BASE_URL}/products/bulk-status`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ productIds, hienthi: status })
  })
  if (!response.ok) throw new Error('Failed to bulk update status')
  toast.success('Trạng thái các sản phẩm đã được cập nhật thành công')
  return true
}

const bulkUpdateFlagAPI = async (productIds, field, value) => {
  const response = await fetch(`${API_BASE_URL}/products/bulk-flag`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ productIds, [field]: value })
  })
  if (!response.ok) throw new Error('Failed to bulk update flag')
  toast.success('Cờ các sản phẩm đã được cập nhật thành công')
  return true
}

// Utility functions
const getCategoryName = (id) => {
  const c = categories.value.find(x => x.id === id)
  return c ? c.tenVi : "N/A"
}


const getTags = (tags) => {
  if (!tags) return []
  return tags.split(',').map(tag => tag.trim()).slice(0, 3)
}

const formatPrice = (price) => {
  if (!price) return '0₫'
  return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(price)
}

const calculateDiscount = (currentPrice, originalPrice) => {
  if (!currentPrice || !originalPrice || originalPrice <= currentPrice) return 0
  return Math.round(((originalPrice - currentPrice) / originalPrice) * 100)
}

const formatDate = (timestamp) => {
  if (!timestamp) return 'N/A'
  return new Date(timestamp * 1000).toLocaleDateString('vi-VN')
}

const getImageUrl = (photo) => {
  if (!photo) return '/placeholder-image.jpg'
  if (photo.startsWith('http')) return photo

  return `${API_BASE_URL}/uploads/${photo}`
}

const handleImageError = (event) => {
  event.target.src = '/placeholder-image.jpg'
}

// Selection methods
const toggleProductSelection = (productId) => {
  const product = products.value.find(p => p.id === productId)
  if (!product) return

  const index = selectedProducts.value.findIndex(p => p.id === productId)
  if (index > -1) {
    selectedProducts.value.splice(index, 1)
  } else {
    selectedProducts.value.push(product)
  }
}

const toggleSelectAll = () => {
  if (isAllSelected.value) {
    paginatedProducts.value.forEach(product => {
      const index = selectedProducts.value.findIndex(p => p.id === product.id)
      if (index > -1) {
        selectedProducts.value.splice(index, 1)
      }
    })
  } else {
    paginatedProducts.value.forEach(product => {
      if (!selectedProducts.value.some(selected => selected.id === product.id)) {
        selectedProducts.value.push(product)
      }
    })
  }
}

const isProductSelected = (productId) => {
  return selectedProducts.value.some(product => product.id === productId)
}

const clearSelection = () => {
  selectedProducts.value = []
}

// Product methods
const editProduct = (product) => {
  productToEdit.value = product
  formData.value = {
    tenVi: product.tenVi || '',
    tenEn: product.tenEn || '',
    masp: product.masp || '',
    idList: product.idList || '',
    giaban: product.giaban || 0,
    gianewp: product.gianewp || 0,
    giacu: product.giacu || 0,
    soluong: product.soluong || 0,
    stt: product.stt || 1,
    noibat: product.noibat || 0,
    banchay: product.banchay || 0,
    newField: product.newField || 0,
    khuyenmai: product.khuyenmai || 0,
    nenmua: product.nenmua || 0,
    hienthi: product.hienthi !== undefined ? product.hienthi : 1,
    motaVi: product.motaVi || '',
    noidungVi: product.noidungVi || '',
    photo: product.photo || '',
    tags: product.tags || '',
    type: product.type || 'product'
  }
  showEditModal.value = true
}

const confirmDeleteProduct = (product) => {
  productToDelete.value = product
  showDeleteModal.value = true
}

const deleteProduct = async () => {
  if (!productToDelete.value) return

  deleting.value = true
  try {
    await deleteProductAPI(productToDelete.value.id)
    products.value = products.value.filter(product => product.id !== productToDelete.value.id)
    selectedProducts.value = selectedProducts.value.filter(product => product.id !== productToDelete.value.id)
    showDeleteModal.value = false
    productToDelete.value = null
  } catch (error) {
    console.error('Error deleting product:', error)
    alert('Có lỗi xảy ra khi xóa sản phẩm')
  } finally {
    deleting.value = false
  }
}

const confirmBulkDelete = () => {
  if (selectedProducts.value.length === 0) return
  showBulkDeleteModal.value = true
}

const bulkDeleteProducts = async () => {
  if (selectedProducts.value.length === 0) return

  deleting.value = true
  try {
    const productIds = selectedProducts.value.map(product => product.id)
    await bulkDeleteProductsAPI(productIds)
    products.value = products.value.filter(product => !productIds.includes(product.id))
    showBulkDeleteModal.value = false
    selectedProducts.value = []
  } catch (error) {
    console.error('Error bulk deleting products:', error)
    alert('Có lỗi xảy ra khi xóa sản phẩm')
  } finally {
    deleting.value = false
  }
}

const toggleFlag = async (product, field) => {
  const newValue = product[field] === 1 ? 0 : 1
  try {
    await updateProductFlagAPI(product.id, field, newValue)
    product[field] = newValue
  } catch (error) {
    console.error('Error updating product flag:', error)
    alert('Có lỗi xảy ra khi cập nhật trạng thái')
  }
}

const toggleStatus = async (product) => {
  const newStatus = product.hienthi === 1 ? 0 : 1
  try {
    await updateProductFlagAPI(product.id, 'active', newStatus)
    product.hienthi = newStatus
  } catch (error) {
    console.error('Error updating product status:', error)
    alert('Có lỗi xảy ra khi cập nhật trạng thái')
  }
}

const bulkUpdateStatus = async (status) => {
  if (selectedProducts.value.length === 0) return

  try {
    const productIds = selectedProducts.value.map(product => product.id)
    await bulkUpdateStatusAPI(productIds, status)
    products.value.forEach(product => {
      if (productIds.includes(product.id)) {
        product.hienthi = status
      }
    })
  } catch (error) {
    console.error('Error bulk updating status:', error)
    alert('Có lỗi xảy ra khi cập nhật trạng thái')
  }
}

const bulkUpdateFlag = async (field, value) => {
  if (selectedProducts.value.length === 0) return

  try {
    const productIds = selectedProducts.value.map(product => product.id)
    await bulkUpdateFlagAPI(productIds, field, value)
    products.value.forEach(product => {
      if (productIds.includes(product.id)) {
        product[field] = value
      }
    })
  } catch (error) {
    console.error('Error bulk updating flag:', error)
    alert('Có lỗi xảy ra khi cập nhật trạng thái')
  }
}

const viewProduct = (product) => {
  console.log('View product:', product)
  alert(`Xem chi tiết sản phẩm: ${product.tenVi}`)
}

const submitProduct = async () => {
  if (!formData.value.tenVi.trim()) {
    alert('Vui lòng nhập tên sản phẩm tiếng Việt')
    return
  }

  if (!formData.value.idList) {
    alert('Vui lòng chọn danh mục')
    return
  }

  if (!formData.value.giaban || formData.value.giaban <= 0) {
    alert('Vui lòng nhập giá bán hợp lệ')
    return
  }

  submitting.value = true
  try {
    const submitData = {
      ...formData.value,
      ngaytao: Math.floor(Date.now() / 1000),
      ngaysua: Math.floor(Date.now() / 1000),
      username: 'admin'
    }

    if (showEditModal.value && productToEdit.value) {
      const updatedProduct = await updateProductAPI(productToEdit.value.id, submitData)
      const index = products.value.findIndex(p => p.id === productToEdit.value.id)
      if (index !== -1) {
        products.value[index] = { ...products.value[index], ...updatedProduct }
      }
      showEditModal.value = false
      productToEdit.value = null
    } else {
      const newProduct = await createProductAPI(submitData)
      products.value.unshift(newProduct)
      showAddModal.value = false
    }

    formData.value = {
      ten_vi: '',
      ten_en: '',
      masp: '',
      id_list: '',
      giaban: 0,
      gianewp: 0,
      giacu: 0,
      soluong: 0,
      stt: 1,
      noibat: 0,
      banchay: 0,
      new: 0,
      khuyenmai: 0,
      nenmua: 0,
      hienthi: 1,
      mota_vi: '',
      noidung_vi: '',
      photo: '',
      tags: '',
      type: 'product'
    }

  } catch (error) {
    console.error('Error saving product:', error)
    alert('Có lỗi xảy ra khi lưu sản phẩm')
  } finally {
    submitting.value = false
  }
}

const closeModal = () => {
  showAddModal.value = false
  showEditModal.value = false
  productToEdit.value = null
  formData.value = {
    ten_vi: '',
    ten_en: '',
    masp: '',
    id_list: '',
    giaban: 0,
    gianewp: 0,
    giacu: 0,
    soluong: 0,
    stt: 1,
    noibat: 0,
    banchay: 0,
    new: 0,
    khuyenmai: 0,
    nenmua: 0,
    hienthi: 1,
    mota_vi: '',
    noidung_vi: '',
    photo: '',
    tags: '',
    type: 'product'
  }
}

const resetFilters = () => {
  searchQuery.value = ''
  categoryFilter.value = ''
  statusFilter.value = ''
  featuredFilter.value = ''
  hotFilter.value = ''
  selectedProducts.value = []
  currentPage.value = 1
}

// Lifecycle
onMounted(() => {
  fetchProducts()
  fetchCategories()
})
</script>

<style scoped>
.product-management {
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

.product-count {
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

/* Bulk Actions */
.bulk-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #e8f5e8;
  padding: 15px;
  border-radius: 8px;
  margin-bottom: 20px;
  border-left: 4px solid #4caf50;
}

.bulk-buttons {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
}

.bulk-buttons button {
  padding: 8px 12px;
  border: 1px solid #ddd;
  border-radius: 4px;
  background: white;
  cursor: pointer;
  font-size: 14px;
  transition: all 0.3s;
}

.bulk-buttons button:hover {
  background: #f5f5f5;
}

.bulk-buttons .btn-delete {
  background: #ffebee;
  color: #d32f2f;
  border-color: #ffcdd2;
}

.bulk-buttons .btn-delete:hover {
  background: #ffcdd2;
}

.bulk-buttons .btn-clear {
  background: #f5f5f5;
  color: #666;
}

.bulk-buttons .btn-clear:hover {
  background: #e0e0e0;
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
  max-height: calc(100vh - 400px);
  overflow-y: auto;
}

.products-table {
  width: 100%;
  border-collapse: collapse;
  min-width: 1200px;
}

.products-table th,
.products-table td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #eee;
}

.products-table th {
  background: #f8f9fa;
  font-weight: 600;
  color: #333;
  position: sticky;
  top: 0;
  z-index: 10;
}

/* Sticky columns */
.sticky-checkbox {
  position: sticky;
  left: 0;
  background: white;
  z-index: 5;
  min-width: 50px;
  box-shadow: 2px 0 4px rgba(0, 0, 0, 0.1);
}

.sticky-order {
  position: sticky;
  left: 50px;
  background: white;
  z-index: 5;
  min-width: 60px;
  box-shadow: 2px 0 4px rgba(0, 0, 0, 0.1);
}

.sticky-actions {
  position: sticky;
  right: 0;
  background: white;
  z-index: 5;
  min-width: 120px;
  box-shadow: -2px 0 4px rgba(0, 0, 0, 0.1);
}

/* Selected row */
.products-table tr.selected td {
  background: #f0f7ff;
}

.products-table tr:hover td {
  background: #f8f9fa;
}

/* Image */
.product-img {
  position: relative;
  width: 60px;
  height: 60px;
}

.product-img img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 4px;
}

.sale-badge {
  position: absolute;
  top: -5px;
  right: -5px;
  background: #d32f2f;
  color: white;
  padding: 2px 6px;
  border-radius: 10px;
  font-size: 10px;
  font-weight: bold;
}

/* Product info */
.product-name {
  font-weight: 600;
  margin-bottom: 4px;
  color: #333;
}

.product-name-en {
  color: #666;
  font-size: 12px;
  margin-bottom: 4px;
  font-style: italic;
}

.product-code {
  color: #999;
  font-size: 12px;
  margin-bottom: 4px;
}

.product-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 4px;
}

.product-tags span {
  background: #f0f0f0;
  padding: 2px 6px;
  border-radius: 10px;
  font-size: 10px;
  color: #666;
}

/* Price */
.current-price {
  font-weight: 600;
  color: #d32f2f;
}

.sale-price {
  color: #666;
  font-size: 12px;
  text-decoration: line-through;
}

.old-price {
  color: #999;
  font-size: 11px;
  text-decoration: line-through;
}

/* Stock */
.low-stock {
  color: #d32f2f;
  font-weight: 600;
}

/* Status buttons */
.status-buttons {
  display: flex;
  gap: 4px;
  flex-wrap: wrap;
}

.status-buttons button {
  padding: 6px;
  border: 1px solid #ddd;
  border-radius: 4px;
  background: white;
  cursor: pointer;
  font-size: 12px;
  transition: all 0.3s;
}

.status-buttons button:hover {
  background: #f5f5f5;
}

.status-buttons button.active {
  background: #e8f5e8;
  border-color: #4caf50;
}

/* Action buttons */
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

.btn-edit {
  background: #e3f2fd;
  color: #1976d2;
}

.btn-edit:hover {
  background: #bbdefb;
}

.btn-delete {
  background: #ffebee;
  color: #d32f2f;
}

.btn-delete:hover {
  background: #ffcdd2;
}

.btn-view {
  background: #f3e5f5;
  color: #7b1fa2;
}

.btn-view:hover {
  background: #e1bee7;
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
  max-width: 500px;
  width: 100%;
  max-height: 90vh;
  overflow-y: auto;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.3);
}

.large-modal {
  max-width: 700px;
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

/* Selected list */
.selected-list {
  max-height: 150px;
  overflow-y: auto;
  margin: 15px 0;
  border: 1px solid #eee;
  border-radius: 6px;
  padding: 10px;
  background: #fafafa;
}

.selected-item {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 8px;
  border-bottom: 1px solid #f5f5f5;
}

.selected-item:last-child {
  border-bottom: none;
}

.thumb {
  width: 30px;
  height: 30px;
  object-fit: cover;
  border-radius: 4px;
}

.product-id {
  color: #999;
  font-size: 12px;
}

/* Form */
.product-form {
  display: flex;
  flex-direction: column;
  gap: 0;
}

.form-section {
  margin-bottom: 20px;
  padding: 15px;
  background: #f8f9fa;
  border-radius: 6px;
  border: 1px solid #eee;
}

.form-section h4 {
  margin: 0 0 15px 0;
  color: #333;
  font-size: 16px;
  font-weight: 600;
}

.form-row {
  display: flex;
  gap: 15px;
  margin-bottom: 15px;
}

.form-row:last-child {
  margin-bottom: 0;
}

.form-group {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.form-group label {
  font-weight: 500;
  color: #333;
  font-size: 14px;
}

.form-input,
.form-select,
.form-textarea {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 14px;
  font-family: inherit;
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

/* Status grid */
.status-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 10px;
}

.status-checkbox {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 8px;
  background: white;
  border: 1px solid #ddd;
  border-radius: 4px;
  cursor: pointer;
  transition: all 0.3s;
}

.status-checkbox:hover {
  border-color: #d32f2f;
}

.status-checkbox input {
  margin: 0;
}

.status-icon {
  font-size: 16px;
}

/* Responsive */
@media (max-width: 768px) {
  .product-management {
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

  .bulk-actions {
    flex-direction: column;
    gap: 15px;
    align-items: flex-start;
  }

  .bulk-buttons {
    width: 100%;
    justify-content: flex-start;
  }

  .filters-section {
    flex-direction: column;
    align-items: stretch;
  }

  .filter-select {
    min-width: auto;
  }

  .form-row {
    flex-direction: column;
  }

  .status-grid {
    grid-template-columns: 1fr;
  }

  .modal {
    margin: 10px;
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

  .table-wrapper {
    max-height: calc(100vh - 450px);
  }
}
</style>