<template>
    <div class="category-management-page">
        <!-- Header -->
        <div class="page-header">
            <h1>Quản Lý Danh Mục</h1>
            <div class="header-actions">
                <span class="category-count">Tổng: {{ totalCategories }} danh mục</span>
                <button class="btn-add-category" @click="showAddModal = true">
                    Thêm danh mục
                </button>
            </div>
        </div>

        <!-- Search -->
        <div class="search-container">
            <input 
                type="text" 
                v-model="searchQuery" 
                placeholder="Tìm kiếm danh mục..." 
                class="search-input" 
                @input="handleSearch"
            />
            <button class="btn-reset" @click="resetFilters">Đặt lại</button>
        </div>

        <!-- Loading -->
        <div v-if="loading" class="loading">
            <p>Đang tải...</p>
        </div>

        <!-- Categories Table -->
        <div v-else class="table-container">
            <div class="table-wrapper">
                <table class="categories-table">
                    <thead>
                        <tr>
                            <th class="col-order">
                                <div class="th-content">
                                    STT
                                    <button class="btn-sort" @click="sortByOrder = !sortByOrder" title="Sắp xếp">
                                        {{ sortByOrder ? '↓' : '↑' }}
                                    </button>
                                </div>
                            </th>
                            <th class="col-name">Tên Danh mục</th>
                            <th class="col-featured">Nổi bật</th>
                            <th class="col-status">Hiển thị</th>
                            <th class="col-actions">Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(category, index) in paginatedCategories" :key="category.id">
                            <td class="col-order">
                                <div class="order-controls">
                                    <button class="btn-move-up" @click="moveCategory(index, -1)" :disabled="index === 0"
                                        title="Lên">
                                        ↑
                                    </button>
                                    <span class="order-number">{{ getCategoryIndex(index) }}</span>
                                    <button class="btn-move-down" @click="moveCategory(index, 1)"
                                        :disabled="index === paginatedCategories.length - 1" title="Xuống">
                                        ↓
                                    </button>
                                </div>
                            </td>

                            <td class="col-name">
                                <div class="category-name">{{ category.tenVi }}</div>
                                <div v-if="category.tenEn" class="category-name-en">{{ category.tenEn }}</div>
                            </td>

                            <td class="col-featured">
                                <div class="featured-display" :class="{ featured: category.noibat === true }">
                                    <button class="btn-toggle-featured" @click="toggleFeatured(category)"
                                        :title="category.noibat === 1 ? 'Bỏ nổi bật' : 'Đánh dấu nổi bật'">
                                        <span class="featured-icon">
                                            {{ category.noibat === 1 ? '★' : '☆' }}
                                        </span>
                                        <span class="featured-text">
                                            {{ category.noibat === 1 ? 'Nổi bật' : 'Thường' }}
                                        </span>
                                    </button>
                                </div>
                            </td>

                            <td class="col-status">
                                <div class="status-display" :class="{ active: category.hienthi === 1 }">
                                    <button class="btn-toggle-status" @click="toggleStatus(category)"
                                        :title="category.hienthi === 1 ? 'Ẩn danh mục' : 'Hiển thị danh mục'">
                                        <span class="status-icon">
                                            {{ category.hienthi === 1 ? '🔘' : '⚪' }}
                                        </span>
                                        <span class="status-text">
                                            {{ category.hienthi === 1 ? 'Hiển thị' : 'Ẩn' }}
                                        </span>
                                    </button>
                                </div>
                            </td>

                            <td class="col-actions">
                                <button class="btn-edit" @click="editCategory(category)" title="Sửa">
                                    ✏️
                                </button>
                                <button class="btn-delete" @click="openDeleteModal(category)" title="Xóa">
                                    🗑️
                                </button>
                            </td>
                        </tr>
                    </tbody>
                </table>

                <!-- Empty State -->
                <div v-if="sortedCategories.length === 0" class="empty-state">
                    <p>Không tìm thấy danh mục</p>
                    <button class="btn-add-category" @click="showAddModal = true">
                        Thêm danh mục đầu tiên
                    </button>
                </div>
            </div>

            <!-- Pagination -->
            <div v-if="sortedCategories.length > 0" class="pagination">
                <div class="pagination-info">
                    Hiển thị {{ startIndex + 1 }}-{{ endIndex }} của {{ totalCategories }} danh mục
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

        <!-- Add/Edit Modal -->
        <div v-if="showAddModal || showEditModal" class="modal">
            <div class="modal-content">
                <div class="modal-header">
                    <h3>{{ showEditModal ? 'Sửa Danh Mục' : 'Thêm Danh Mục' }}</h3>
                    <button class="close-btn" @click="closeModal">✕</button>
                </div>

                <div class="modal-body">
                    <div class="form-row">
                        <div class="form-group">
                            <label>Tên danh mục (Tiếng Việt) *</label>
                            <input type="text" v-model="formData.tenVi" required placeholder="Nhập tên danh mục"
                                class="form-input" @input="generateSlug">
                        </div>

                        <div class="form-group">
                            <label>Tên không dấu *</label>
                            <input type="text" v-model="formData.tenkhongdau" required
                                placeholder="Tên không dấu tự động" class="form-input" readonly>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label>Tên danh mục (Tiếng Anh)</label>
                            <input type="text" v-model="formData.tenEn" placeholder="Nhập tên tiếng Anh"
                                class="form-input">
                        </div>

                        <div class="form-group">
                            <label>Tên danh mục (Tiếng Trung)</label>
                            <input type="text" v-model="formData.tenCn" placeholder="Nhập tên tiếng Trung"
                                class="form-input">
                        </div>
                    </div>

                    <div class="form-group">
                        <label>Mô tả</label>
                        <textarea v-model="formData.description" placeholder="Nhập mô tả danh mục" class="form-textarea"
                            rows="3"></textarea>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label>Số thứ tự</label>
                            <input type="number" v-model="formData.stt" placeholder="Số thứ tự" class="form-input">
                        </div>

                        <div class="form-group">
                            <label>Keywords (SEO)</label>
                            <input type="text" v-model="formData.Keywords" placeholder="Từ khóa SEO" class="form-input">
                        </div>
                    </div>

                    <div class="form-group">
                        <label>Hình ảnh</label>
                        <div class="image-upload-section">
                            <input type="file" ref="fileInput" @change="handleImageUpload" accept="image/*"
                                class="file-input" style="display: none">
                            <button type="button" class="btn-upload" @click="$refs.fileInput.click()">
                                Chọn hình ảnh
                            </button>
                            <div v-if="formData.photo" class="image-preview">
                                <img :src="formData.photo" alt="Preview" class="preview-image">
                                <button type="button" class="btn-remove-image" @click="removeImage">×</button>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="modal-footer">
                    <button class="btn-cancel" @click="closeModal">Hủy</button>
                    <button class="btn-save" @click="saveCategory" :disabled="saving">
                        {{ saving ? 'Đang lưu...' : 'Lưu' }}
                    </button>
                </div>
            </div>
        </div>

        <!-- Delete Confirmation Modal -->
        <div v-if="showDeleteModal" class="modal">
            <div class="modal-content delete-modal">
                <div class="modal-header">
                    <h3>Xác nhận xóa</h3>
                    <button class="close-btn" @click="closeDeleteModal">✕</button>
                </div>

                <div class="modal-body">
                    <div class="delete-icon">
                        <span>🗑️</span>
                    </div>
                    <p class="delete-message">Bạn có chắc chắn muốn xóa danh mục <strong>"{{ deletingCategory?.tenVi
                            }}"</strong>?</p>
                    <p class="delete-warning">Hành động này không thể hoàn tác.</p>
                </div>

                <div class="modal-footer">
                    <button class="btn-cancel" @click="closeDeleteModal">Hủy</button>
                    <button class="btn-delete-confirm" @click="confirmDelete" :disabled="deleting">
                        {{ deleting ? 'Đang xóa...' : 'Xóa' }}
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { toast } from 'vue3-toastify'

// Data
const categories = ref([])
const loading = ref(false)
const showAddModal = ref(false)
const showEditModal = ref(false)
const showDeleteModal = ref(false)
const saving = ref(false)
const deleting = ref(false)
const searchQuery = ref('')
const editingCategory = ref(null)
const deletingCategory = ref(null)
const sortByOrder = ref(true)
const fileInput = ref(null)

// Pagination data
const currentPage = ref(1)
const pageSize = ref(20)

// Form data
const formData = ref({
    tenVi: '',
    tenEn: '',
    tenCn: '',
    tenkhongdau: '',
    description: '',
    stt: 0,
    Keywords: '',
    photo: ''
})

// API config
const API_BASE_URL = 'https://jq6kflwj-5173.asse.devtunnels.ms/api'

// Computed properties
const filteredCategories = computed(() => {
    if (!searchQuery.value) return categories.value

    return categories.value.filter(category =>
        category.tenVi?.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
        category.tenEn?.toLowerCase().includes(searchQuery.value.toLowerCase())
    )
})

const sortedCategories = computed(() => {
    const filtered = [...filteredCategories.value]
    return sortByOrder.value ? filtered : filtered.reverse()
})

const totalCategories = computed(() => sortedCategories.value.length)
const totalPages = computed(() => Math.ceil(totalCategories.value / pageSize.value))
const startIndex = computed(() => (currentPage.value - 1) * pageSize.value)
const endIndex = computed(() => Math.min(startIndex.value + pageSize.value, totalCategories.value))

const paginatedCategories = computed(() => {
    return sortedCategories.value.slice(startIndex.value, endIndex.value)
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

// Pagination methods
const handleSearch = () => {
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

const getCategoryIndex = (index) => {
    return startIndex.value + index + 1
}

// Utility methods
const generateSlug = () => {
    if (formData.value.tenVi) {
        formData.value.tenkhongdau = formData.value.tenVi
            .toLowerCase()
            .normalize('NFD')
            .replace(/[\u0300-\u036f]/g, '')
            .replace(/[đĐ]/g, 'd')
            .replace(/[^a-z0-9 ]/g, '')
            .replace(/\s+/g, '-')
    }
}

const handleImageUpload = (event) => {
    const file = event.target.files[0]
    if (file) {
        // Trong thực tế, bạn cần upload file lên server
        // Ở đây tôi giả lập bằng URL.createObjectURL cho demo
        const imageUrl = URL.createObjectURL(file)
        formData.value.photo = imageUrl
    }
}

const removeImage = () => {
    formData.value.photo = ''
    if (fileInput.value) {
        fileInput.value.value = ''
    }
}

const openAddModal = () => {
    formData.value = {
        tenVi: '',
        tenEn: '',
        tenCn: '',
        tenkhongdau: '',
        description: '',
        stt: categories.value.length + 1, // Auto increment
        Keywords: '',
        photo: ''
    }
    showAddModal.value = true
}

// API calls
const fetchCategories = async () => {
    loading.value = true
    try {
        const response = await fetch(`${API_BASE_URL}/productLists`)
        const data = await response.json()
        categories.value = data
    } catch (error) {
        console.error('Lỗi tải danh mục:', error)
        alert('Lỗi khi tải danh sách danh mục')
    } finally {
        loading.value = false
    }
}

const saveCategory = async () => {
    if (!formData.value.tenVi.trim()) {
        alert('Vui lòng nhập tên danh mục tiếng Việt')
        return
    }

    if (!formData.value.tenkhongdau.trim()) {
        alert('Vui lòng kiểm tra tên không dấu')
        return
    }

    saving.value = true
    try {
        if (showEditModal.value && editingCategory.value) {
            // Update
            await fetch(`${API_BASE_URL}/productLists`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    ...formData.value,
                    id: editingCategory.value.id
                })
            })

            // Update local
            const index = categories.value.findIndex(c => c.id === editingCategory.value.id)
            if (index !== -1) {
                categories.value[index] = { ...categories.value[index], ...formData.value }
            }
        } else {
            // Create
            const response = await fetch(`${API_BASE_URL}/productLists`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(formData.value)
            })
            const newCategory = await response.json()
            categories.value.unshift(newCategory)
        }

        closeModal()
        toast.success('Lưu danh mục thành công!')
    } catch (error) {
        console.error('Lỗi khi lưu danh mục:', error)
        alert('Lỗi khi lưu danh mục')
    } finally {
        saving.value = false
    }
}

const openDeleteModal = (category) => {
    deletingCategory.value = category
    showDeleteModal.value = true
}

const closeDeleteModal = () => {
    showDeleteModal.value = false
    deletingCategory.value = null
    deleting.value = false
}

const confirmDelete = async () => {
    if (!deletingCategory.value) return

    deleting.value = true
    try {
        await fetch(`${API_BASE_URL}/productLists/${deletingCategory.value.id}`, {
            method: 'DELETE'
        })
        categories.value = categories.value.filter(c => c.id !== deletingCategory.value.id)
        closeDeleteModal()
        toast.success('Xóa danh mục thành công!')
    } catch (error) {
        alert('Lỗi khi xóa danh mục')
    } finally {
        deleting.value = false
    }
}

const toggleFeatured = async (category) => {
    try {
        await fetch(`${API_BASE_URL}/productLists/${category.id}?type=outstanding`, {
            method: 'PUT'
        })
        category.noibat = category.noibat === 1 ? 0 : 1
        toast.success('Cập nhật nổi bật thành công!')
    } catch (error) {
        alert('Lỗi khi cập nhật nổi bật')
    }
}

const toggleStatus = async (category) => {
    try {
        await fetch(`${API_BASE_URL}/productLists/${category.id}?type=active`, {
            method: 'PUT'
        })
        category.hienthi = category.hienthi === 1 ? 0 : 1
        toast.success('Cập nhật trạng thái thành công!')
    } catch (error) {
        alert('Lỗi khi cập nhật trạng thái')
    }
}

// Move category up/down
const moveCategory = (currentIndex, direction) => {
    const newIndex = currentIndex + direction
    if (newIndex < 0 || newIndex >= paginatedCategories.value.length) return

    const temp = paginatedCategories.value[currentIndex]
    paginatedCategories.value[currentIndex] = paginatedCategories.value[newIndex]
    paginatedCategories.value[newIndex] = temp
}

// Methods
const editCategory = (category) => {
    editingCategory.value = category
    formData.value = {
        tenVi: category.tenVi || '',
        tenEn: category.tenEn || '',
        tenCn: category.tenCn || '',
        tenkhongdau: category.tenkhongdau || '',
        description: category.description || '',
        stt: category.stt || 0,
        Keywords: category.Keywords || '',
        photo: category.photo || ''
    }
    showEditModal.value = true
}

const closeModal = () => {
    showAddModal.value = false
    showEditModal.value = false
    editingCategory.value = null
    formData.value = {
        tenVi: '',
        tenEn: '',
        tenCn: '',
        tenkhongdau: '',
        description: '',
        stt: 0,
        Keywords: '',
        photo: ''
    }
    if (fileInput.value) {
        fileInput.value.value = ''
    }
}

const resetFilters = () => {
    searchQuery.value = ''
    currentPage.value = 1
}

// Lifecycle
onMounted(() => {
    fetchCategories()
})
</script>

<style scoped>
.category-management-page {
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
    font-size: 24px;
}

.header-actions {
    display: flex;
    align-items: center;
    gap: 15px;
}

.category-count {
    background: #e9ecef;
    padding: 8px 12px;
    border-radius: 4px;
    font-size: 14px;
    color: #495057;
}

.btn-add-category {
    background: #007bff;
    color: white;
    border: none;
    padding: 10px 15px;
    border-radius: 4px;
    cursor: pointer;
    font-weight: 500;
}

.btn-add-category:hover {
    background: #0056b3;
}

.search-container {
    display: flex;
    gap: 10px;
    margin-bottom: 20px;
    align-items: center;
}

.search-input {
    flex: 1;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 4px;
    max-width: 300px;
    font-size: 14px;
}

.btn-reset {
    background: #6c757d;
    color: white;
    border: none;
    padding: 10px 15px;
    border-radius: 4px;
    cursor: pointer;
    font-size: 14px;
}

.btn-reset:hover {
    background: #545b62;
}

.loading {
    text-align: center;
    padding: 40px;
    color: #666;
}

.table-container {
    background: white;
    border-radius: 8px;
    overflow: hidden;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.table-wrapper {
    max-height: calc(100vh - 350px);
    overflow-y: auto;
}

.categories-table {
    width: 100%;
    border-collapse: collapse;
}

.categories-table th {
    background: #f8f9fa;
    padding: 15px 12px;
    text-align: left;
    border-bottom: 2px solid #dee2e6;
    font-weight: 600;
    color: #333;
    font-size: 14px;
    position: sticky;
    top: 0;
    z-index: 10;
}

.categories-table td {
    padding: 15px 12px;
    border-bottom: 1px solid #e9ecef;
    vertical-align: middle;
}

.categories-table tr:hover {
    background: #f8f9fa;
}

.col-order {
    width: 100px;
    text-align: center;
}

.col-name {
    min-width: 200px;
}

.col-featured,
.col-status {
    width: 120px;
    text-align: center;
}

.col-actions {
    width: 100px;
    text-align: center;
}

/* Order Controls */
.th-content {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 8px;
}

.btn-sort {
    background: none;
    border: none;
    cursor: pointer;
    font-size: 12px;
    color: #666;
    padding: 2px 6px;
    border-radius: 3px;
}

.btn-sort:hover {
    background: #e9ecef;
}

.order-controls {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 8px;
}

.btn-move-up,
.btn-move-down {
    background: none;
    border: 1px solid #ddd;
    border-radius: 3px;
    padding: 4px 6px;
    cursor: pointer;
    font-size: 10px;
    color: #666;
}

.btn-move-up:hover:not(:disabled),
.btn-move-down:hover:not(:disabled) {
    background: #007bff;
    color: white;
    border-color: #007bff;
}

.btn-move-up:disabled,
.btn-move-down:disabled {
    opacity: 0.3;
    cursor: not-allowed;
}

.order-number {
    font-weight: 600;
    color: #007bff;
    font-size: 14px;
    min-width: 20px;
    text-align: center;
}

/* Category Name */
.category-name {
    font-weight: 600;
    color: #333;
    font-size: 14px;
    margin-bottom: 4px;
}

.category-name-en {
    color: #666;
    font-size: 12px;
    font-style: italic;
}

/* Featured Display */
.featured-display {
    display: flex;
    justify-content: center;
}

.featured-display.featured {
    background: linear-gradient(135deg, #fff3cd, #ffeaa7);
    border-radius: 6px;
    padding: 4px;
}

.btn-toggle-featured {
    background: none;
    border: none;
    cursor: pointer;
    display: flex;
    align-items: center;
    gap: 6px;
    padding: 6px 10px;
    border-radius: 4px;
    transition: all 0.3s;
    font-size: 12px;
}

.btn-toggle-featured:hover {
    background: rgba(0, 0, 0, 0.05);
}

.featured-icon {
    font-size: 16px;
    color: #ffc107;
}

.featured-text {
    font-weight: 500;
    color: #333;
}

.featured-display.featured .featured-text {
    color: #e65100;
    font-weight: 600;
}

/* Status Display */
.status-display {
    display: flex;
    justify-content: center;
}

.status-display.active {
    background: linear-gradient(135deg, #d1ecf1, #bee5eb);
    border-radius: 6px;
    padding: 4px;
}

.btn-toggle-status {
    background: none;
    border: none;
    cursor: pointer;
    display: flex;
    align-items: center;
    gap: 6px;
    padding: 6px 10px;
    border-radius: 4px;
    transition: all 0.3s;
    font-size: 12px;
}

.btn-toggle-status:hover {
    background: rgba(0, 0, 0, 0.05);
}

.status-icon {
    font-size: 14px;
}

.status-display.active .status-icon {
    color: #28a745;
}

.status-text {
    font-weight: 500;
    color: #666;
}

.status-display.active .status-text {
    color: #155724;
    font-weight: 600;
}

/* Action Buttons */
.btn-edit,
.btn-delete {
    background: none;
    border: none;
    padding: 8px 10px;
    cursor: pointer;
    font-size: 14px;
    border-radius: 4px;
    margin: 0 2px;
    transition: all 0.3s;
}

.btn-edit:hover {
    background: #007bff;
    color: white;
}

.btn-delete:hover {
    background: #dc3545;
    color: white;
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

/* Form Styles */
.form-row {
    display: flex;
    gap: 15px;
}

.form-row .form-group {
    flex: 1;
}

.form-textarea {
    width: 100%;
    padding: 10px 12px;
    border: 1px solid #ddd;
    border-radius: 4px;
    box-sizing: border-box;
    font-size: 14px;
    font-family: inherit;
    resize: vertical;
    min-height: 80px;
}

.form-textarea:focus {
    outline: none;
    border-color: #007bff;
    box-shadow: 0 0 0 2px rgba(0, 123, 255, 0.25);
}

.image-upload-section {
    display: flex;
    flex-direction: column;
    gap: 10px;
}

.btn-upload {
    background: #28a745;
    color: white;
    border: none;
    padding: 8px 15px;
    border-radius: 4px;
    cursor: pointer;
    font-size: 14px;
    align-self: flex-start;
}

.btn-upload:hover {
    background: #218838;
}

.image-preview {
    position: relative;
    display: inline-block;
    max-width: 200px;
}

.preview-image {
    width: 100%;
    height: auto;
    border-radius: 4px;
    border: 1px solid #ddd;
}

.btn-remove-image {
    position: absolute;
    top: -8px;
    right: -8px;
    background: #dc3545;
    color: white;
    border: none;
    border-radius: 50%;
    width: 24px;
    height: 24px;
    cursor: pointer;
    font-size: 14px;
    display: flex;
    align-items: center;
    justify-content: center;
}

.btn-remove-image:hover {
    background: #c82333;
}

.empty-state {
    text-align: center;
    padding: 40px;
    color: #666;
}

.empty-state p {
    margin-bottom: 15px;
    font-size: 16px;
}

/* Modal */
.modal {
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

.modal-content {
    background: white;
    border-radius: 8px;
    width: 90%;
    max-width: 500px;
    box-shadow: 0 10px 25px rgba(0, 0, 0, 0.2);
    animation: modalSlideIn 0.3s ease;
}

.delete-modal {
    max-width: 450px;
    text-align: center;
}

@keyframes modalSlideIn {
    from {
        opacity: 0;
        transform: translateY(-20px);
    }

    to {
        opacity: 1;
        transform: translateY(0);
    }
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
    color: #333;
    font-size: 18px;
}

.close-btn {
    background: none;
    border: none;
    font-size: 18px;
    cursor: pointer;
    color: #666;
    padding: 5px;
    border-radius: 3px;
}

.close-btn:hover {
    background: #f8f9fa;
    color: #333;
}

.modal-body {
    padding: 20px;
}

/* Delete Modal Styles */
.delete-icon {
    font-size: 48px;
    margin-bottom: 15px;
    opacity: 0.8;
}

.delete-message {
    font-size: 16px;
    margin-bottom: 10px;
    color: #333;
}

.delete-warning {
    font-size: 14px;
    color: #dc3545;
    margin-bottom: 0;
}

.form-group {
    margin-bottom: 20px;
}

.form-group label {
    display: block;
    margin-bottom: 8px;
    font-weight: 500;
    color: #333;
    font-size: 14px;
}

.form-input {
    width: 100%;
    padding: 10px 12px;
    border: 1px solid #ddd;
    border-radius: 4px;
    box-sizing: border-box;
    font-size: 14px;
    transition: border-color 0.3s;
}

.form-input:focus {
    outline: none;
    border-color: #007bff;
    box-shadow: 0 0 0 2px rgba(0, 123, 255, 0.25);
}

.modal-footer {
    display: flex;
    gap: 10px;
    justify-content: flex-end;
    padding: 20px;
    border-top: 1px solid #dee2e6;
}

.btn-cancel {
    background: #6c757d;
    color: white;
    border: none;
    padding: 10px 20px;
    border-radius: 4px;
    cursor: pointer;
    font-weight: 500;
}

.btn-cancel:hover {
    background: #545b62;
}

.btn-save {
    background: #007bff;
    color: white;
    border: none;
    padding: 10px 20px;
    border-radius: 4px;
    cursor: pointer;
    font-weight: 500;
}

.btn-save:hover:not(:disabled) {
    background: #0056b3;
}

.btn-save:disabled {
    background: #ccc;
    cursor: not-allowed;
}

.btn-delete-confirm {
    background: #dc3545;
    color: white;
    border: none;
    padding: 10px 20px;
    border-radius: 4px;
    cursor: pointer;
    font-weight: 500;
}

.btn-delete-confirm:hover:not(:disabled) {
    background: #c82333;
}

.btn-delete-confirm:disabled {
    background: #e0a6ad;
    cursor: not-allowed;
}

/* Responsive */
@media (max-width: 768px) {
    .page-header {
        flex-direction: column;
        align-items: flex-start;
        gap: 10px;
    }

    .search-container {
        flex-direction: column;
        align-items: stretch;
    }

    .search-input {
        max-width: none;
    }

    .categories-table {
        font-size: 12px;
    }

    .categories-table th,
    .categories-table td {
        padding: 10px 8px;
    }

    .order-controls {
        flex-direction: column;
        gap: 4px;
    }

    .featured-text,
    .status-text {
        display: none;
    }

    .modal-content {
        width: 95%;
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
}
</style>