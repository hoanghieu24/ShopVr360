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
        <button class="btn-add" @click="openAddModal">
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
                  <img :src="getImageUrl(product.photo)" :alt="product.tenVi" @error="handleImageError" />
                  <div v-if="product.gianewp && product.giaban" class="sale-badge">
                    -{{ calculateDiscount(product.giaban, product.gianewp) }}%
                  </div>
                </div>
              </td>
              <td class="info-cell">
                <div class="product-name">{{ product.tenVi }}</div>
                <div v-if="product.tenEn" class="product-name-en">{{ product.tenEn }}</div>
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
                  <button @click="toggleFlag(product, 'newField')" :class="{ active: product.newField === 1 }"
                    title="Mới">🆕</button>
                  <button @click="toggleFlag(product, 'khuyenmai')" :class="{ active: product.khuyenmai === 1 }"
                    title="Khuyến mãi">🎁</button>
                  <button @click="toggleFlag(product, 'nenmua')" :class="{ active: product.nenmua === 1 }"
                    title="Nên mua">👍</button>
                  <button @click="toggleStatus(product)" :class="{ active: product.hienthi === 1 }"
                    :title="product.hienthi === 1 ? 'Đang hiển thị' : 'Đang ẩn'">
                    {{ product.hienthi === 1 ? '👁️' : '👁️‍🗨️' }}
                  </button>
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
          <button v-else @click="openAddModal" class="btn-add">
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
          <p>Bạn có chắc chắn muốn xóa sản phẩm <strong>{{ productToDelete?.tenVi }}</strong>?</p>
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
            <div v-for="product in selectedProducts.slice(0, 5)" :key="product.id" class="selected-item">
              <img :src="getImageUrl(product.photo)" class="thumb">
              <span class="product-name">{{ product.tenVi }}</span>
              <span class="product-id">(ID: {{ product.id }})</span>
            </div>
            <div v-if="selectedProducts.length > 5" class="selected-more">
              ... và {{ selectedProducts.length - 5 }} sản phẩm khác
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
      <div class="modal large-modal product-modal">
        <div class="modal-header">
          <h3>
            <span class="modal-icon">{{ showEditModal ? '✏️' : '➕' }}</span>
            {{ showEditModal ? 'Chỉnh Sửa Sản Phẩm' : 'Thêm Sản Phẩm Mới' }}
            <span v-if="showEditModal" class="product-id">(ID: {{ formData.id }})</span>
          </h3>
          <button @click="closeModal" class="close-btn" :disabled="submitting">✕</button>
        </div>

        <!-- Progress Steps -->
        <div class="form-steps">
          <div class="step-indicator">
            <div v-for="(step, index) in formSteps" :key="index"
              :class="['step', { active: currentStep === index, completed: currentStep > index }]"
              @click="goToStep(index)">
              <span class="step-number">{{ index + 1 }}</span>
              <span class="step-label">{{ step.label }}</span>
            </div>
          </div>
        </div>

        <div class="modal-body">
          <form @submit.prevent="submitProduct" class="product-form" enctype="multipart/form-data">

            <!-- Step 1: Thông tin cơ bản -->
            <div v-if="currentStep === 0" class="form-step">
              <div class="step-header">
                <h4>📋 Thông tin cơ bản</h4>
                <p class="step-description">Nhập thông tin cơ bản của sản phẩm</p>
              </div>

              <div class="form-grid">
                <div class="form-group required">
                  <label for="tenVi" class="form-label">
                    Tên sản phẩm (Tiếng Việt)
                    <span class="required-star">*</span>
                  </label>
                  <input type="text" id="tenVi" v-model="formData.tenVi" required
                    placeholder="Nhập tên sản phẩm tiếng Việt" class="form-input"
                    :class="{ error: !formData.tenVi.trim() }">
                  <div v-if="!formData.tenVi.trim()" class="error-message">Vui lòng nhập tên sản phẩm</div>
                </div>

                <div class="form-group">
                  <label for="tenEn" class="form-label">Tên sản phẩm (Tiếng Anh)</label>
                  <input type="text" id="tenEn" v-model="formData.tenEn" placeholder="Nhập tên sản phẩm tiếng Anh"
                    class="form-input">
                </div>

                <div class="form-group required">
                  <label for="id_list" class="form-label">
                    Danh mục
                    <span class="required-star">*</span>
                  </label>
                  <select id="id_list" v-model="formData.idList" required class="form-select"
                    :class="{ error: !formData.idList }">
                    <option value="">Chọn danh mục</option>
                    <option v-for="item in categories" :key="item.id" :value="item.id">
                      {{ item.tenVi }}
                    </option>
                  </select>
                  <div v-if="!formData.idList" class="error-message">Vui lòng chọn danh mục</div>
                </div>

                <div class="form-group">
                  <label for="masp" class="form-label">Mã sản phẩm</label>
                  <input type="text" id="masp" v-model="formData.masp" placeholder="Mã sản phẩm" class="form-input">
                </div>

                <div class="form-group required">
                  <label for="giaban" class="form-label">
                    Giá bán
                    <span class="required-star">*</span>
                  </label>
                  <div class="price-input-group">
                    <input type="number" id="giaban" v-model="formData.giaban" required min="0" placeholder="0"
                      class="form-input price-input" :class="{ error: !formData.giaban || formData.giaban <= 0 }">
                    <span class="currency">₫</span>
                  </div>
                  <div v-if="!formData.giaban || formData.giaban <= 0" class="error-message">
                    Vui lòng nhập giá bán hợp lệ
                  </div>
                </div>

                <div class="form-group">
                  <label for="gianewp" class="form-label">Giá khuyến mãi</label>
                  <div class="price-input-group">
                    <input type="number" id="gianewp" v-model="formData.gianewp" min="0" placeholder="0"
                      class="form-input price-input">
                    <span class="currency">₫</span>
                  </div>
                </div>

                <div class="form-group">
                  <label for="giacu" class="form-label">Giá cũ</label>
                  <div class="price-input-group">
                    <input type="number" id="giacu" v-model="formData.giacu" min="0" placeholder="0"
                      class="form-input price-input">
                    <span class="currency">₫</span>
                  </div>
                </div>

                <div class="form-group">
                  <label for="soluong" class="form-label">Số lượng tồn kho</label>
                  <input type="number" id="soluong" v-model="formData.soluong" min="0" placeholder="0"
                    class="form-input">
                </div>

                <div class="form-group">
                  <label for="donVi" class="form-label">Đơn vị tính</label>
                  <input type="text" id="donVi" v-model="formData.donVi" placeholder="Cái, Hộp, Gói..."
                    class="form-input">
                </div>

                <div class="form-group">
                  <label for="kg" class="form-label">Khối lượng (kg)</label>
                  <div class="weight-input-group">
                    <input type="number" id="kg" v-model="formData.kg" min="0" step="0.01" placeholder="0.00"
                      class="form-input weight-input">
                    <span class="unit">kg</span>
                  </div>
                  <div class="help-text">Khối lượng sản phẩm tính bằng kilogram</div>
                </div>
              </div>
            </div>

            <!-- Step 2: Hình ảnh & Media -->
            <div v-if="currentStep === 1" class="form-step">
              <div class="step-header">
                <h4>🖼️ Hình ảnh & Media</h4>
                <p class="step-description">Tải lên hình ảnh và video cho sản phẩm</p>
              </div>

              <!-- Ảnh chính -->
              <div class="upload-section">
                <div class="section-title">Ảnh đại diện chính</div>

                <div class="main-image-upload">
                  <div v-if="mainImagePreview" class="main-image-preview">
                    <img :src="mainImagePreview" alt="Preview" class="preview-image">
                    <div class="preview-actions">
                      <button type="button" @click="removeMainImage" class="btn-action danger" title="Xóa ảnh">
                        🗑️
                      </button>
                      <button type="button" @click="openImageUpload" class="btn-action" title="Thay đổi ảnh">
                        🔄
                      </button>
                    </div>
                  </div>

                  <div v-else class="upload-placeholder">
                    <div class="upload-icon">📷</div>
                    <p class="upload-text">Chưa có ảnh đại diện</p>
                    <div class="upload-options">
                      <input type="file" id="mainImageUpload" @change="handleMainImageUpload" accept="image/*"
                        class="file-input" :disabled="submitting">
                      <label for="mainImageUpload" class="upload-option-btn primary">
                        <span class="upload-icon">📁</span>
                        <span>Tải ảnh lên</span>
                      </label>
                      <span class="option-divider">hoặc</span>
                      <input type="text" v-model="formData.photo" placeholder="Dán URL hình ảnh"
                        class="form-input url-input" @input="handleUrlInput">
                    </div>
                    <div class="upload-hint">
                      <small>Định dạng: JPG, PNG, GIF • Tối đa: 5MB • Tỷ lệ khuyến nghị: 1:1</small>
                    </div>
                  </div>
                </div>

                <div v-if="imageError" class="error-message full-width">
                  {{ imageError }}
                </div>
              </div>

              <!-- Ảnh phụ -->
              <div class="upload-section">
                <div class="section-title">
                  Ảnh phụ ({{ uploadedImages.length }}/5)
                  <small class="optional">Tùy chọn</small>
                </div>

                <div class="additional-images">
                  <div class="image-grid">
                    <div v-for="(image, index) in uploadedImages" :key="index" class="image-item">
                      <img :src="image.preview" :alt="image.name" class="preview-img">
                      <div class="image-overlay">
                        <button type="button" @click="removeImage(index)" class="btn-action danger small">
                          ×
                        </button>
                      </div>
                    </div>

                    <div v-if="uploadedImages.length < 5" class="upload-item">
                      <input type="file" id="fileUpload" @change="handleFileUpload" multiple accept="image/*"
                        class="file-input">
                      <label for="fileUpload" class="upload-box">
                        <span class="upload-icon">➕</span>
                        <span class="upload-text">Thêm ảnh</span>
                      </label>
                    </div>
                  </div>
                </div>
              </div>

              <!-- Video -->
              <div class="form-group">
                <label for="videoYoutube" class="form-label">
                  Video YouTube
                  <small class="optional">Tùy chọn</small>
                </label>
                <input type="text" id="videoYoutube" v-model="formData.videoYoutube"
                  placeholder="ID hoặc URL video YouTube" class="form-input">
                <div class="help-text">
                  Ví dụ: dQw4w9WgXcQ hoặc https://youtu.be/dQw4w9WgXcQ
                </div>
              </div>
            </div>

            <!-- Step 3: Mô tả & Thuộc tính -->
            <div v-if="currentStep === 2" class="form-step">
              <div class="step-header">
                <h4>📝 Mô tả & Thuộc tính</h4>
                <p class="step-description">Thêm mô tả và các thuộc tính của sản phẩm</p>
              </div>

              <div class="form-group">
                <label for="motaVi" class="form-label">Mô tả ngắn (Tiếng Việt)</label>
                <textarea id="motaVi" v-model="formData.motaVi" rows="3" placeholder="Mô tả ngắn về sản phẩm..."
                  class="form-textarea"></textarea>
                <div class="char-count">{{ formData.motaVi.length }}/500</div>
              </div>

              <div class="form-group">
                <label for="noidungVi" class="form-label">Nội dung chi tiết (Tiếng Việt)</label>
                <textarea id="noidungVi" v-model="formData.noidungVi" rows="6"
                  placeholder="Nội dung chi tiết về sản phẩm..." class="form-textarea large"></textarea>
                <div class="char-count">{{ formData.noidungVi.length }}/2000</div>
              </div>

              <!-- Thuộc tính sản phẩm -->
              <div class="attributes-section">
                <div class="section-title">
                  Thuộc tính sản phẩm
                  <small class="optional">Tùy chọn</small>
                </div>

                <div class="attributes-list">
                  <div v-for="(attribute, index) in formData.thuocTinh" :key="index" class="attribute-row">
                    <input type="text" v-model="attribute.ten" placeholder="Tên thuộc tính"
                      class="form-input attribute-input">
                    <span class="attribute-separator">:</span>
                    <input type="text" v-model="attribute.giaTri" placeholder="Giá trị"
                      class="form-input attribute-input">
                    <button type="button" @click="removeAttribute(index)" class="btn-action danger"
                      title="Xóa thuộc tính">
                      🗑️
                    </button>
                  </div>

                  <button type="button" @click="addAttribute" class="btn-add-attribute">
                    <span class="icon">➕</span>
                    Thêm thuộc tính
                  </button>
                </div>
              </div>
            </div>

            <!-- Step 4: Cài đặt & SEO -->
            <div v-if="currentStep === 3" class="form-step">
              <div class="step-header">
                <h4>⚙️ Cài đặt & SEO</h4>
                <p class="step-description">Cấu hình trạng thái và tối ưu SEO</p>
              </div>

              <!-- Trạng thái sản phẩm -->
              <div class="status-section">
                <div class="section-title">Trạng thái sản phẩm</div>
                <div class="status-grid compact">
                  <label class="status-checkbox featured">
                    <input type="checkbox" v-model="formData.noibat" :true-value="1" :false-value="0">
                    <span class="status-icon">⭐</span>
                    <span class="status-label">Nổi bật</span>
                  </label>

                  <label class="status-checkbox hot">
                    <input type="checkbox" v-model="formData.banchay" :true-value="1" :false-value="0">
                    <span class="status-icon">🔥</span>
                    <span class="status-label">Bán chạy</span>
                  </label>

                  <label class="status-checkbox new">
                    <input type="checkbox" v-model="formData.newField" :true-value="1" :false-value="0">
                    <span class="status-icon">🆕</span>
                    <span class="status-label">Mới</span>
                  </label>

                  <label class="status-checkbox sale">
                    <input type="checkbox" v-model="formData.khuyenmai" :true-value="1" :false-value="0">
                    <span class="status-icon">🎁</span>
                    <span class="status-label">Khuyến mãi</span>
                  </label>

                  <label class="status-checkbox recommended">
                    <input type="checkbox" v-model="formData.nenmua" :true-value="1" :false-value="0">
                    <span class="status-icon">👍</span>
                    <span class="status-label">Nên mua</span>
                  </label>

                  <label class="status-checkbox visible">
                    <input type="checkbox" v-model="formData.hienthi" :true-value="1" :false-value="0">
                    <span class="status-icon">👁️</span>
                    <span class="status-label">Hiển thị</span>
                  </label>
                </div>
              </div>

              <!-- Thông tin SEO -->
              <div class="seo-section">
                <div class="section-title">
                  Thông tin SEO
                  <small class="optional">Tùy chọn</small>
                </div>

                <div class="form-group">
                  <label for="seoTitle" class="form-label">Tiêu đề SEO</label>
                  <input type="text" id="seoTitle" v-model="formData.seoTitle" placeholder="Tiêu đề SEO"
                    class="form-input">
                  <div class="char-count">{{ formData.seoTitle.length }}/60</div>
                </div>

                <div class="form-group">
                  <label for="seoDescription" class="form-label">Mô tả SEO</label>
                  <textarea id="seoDescription" v-model="formData.seoDescription" rows="3" placeholder="Mô tả SEO"
                    class="form-textarea"></textarea>
                  <div class="char-count">{{ formData.seoDescription.length }}/160</div>
                </div>

                <div class="form-group">
                  <label for="seoKeywords" class="form-label">Từ khóa SEO</label>
                  <input type="text" id="seoKeywords" v-model="formData.seoKeywords"
                    placeholder="Từ khóa SEO (phân cách bằng dấu phẩy)" class="form-input">
                </div>

                <div class="form-group">
                  <label for="tags" class="form-label">Tags</label>
                  <input type="text" id="tags" v-model="formData.tags" placeholder="tag1, tag2, tag3"
                    class="form-input">
                  <div class="help-text">Phân cách các tags bằng dấu phẩy</div>
                </div>
              </div>
            </div>
          </form>
        </div>

        <!-- Modal Footer với Navigation -->
        <div class="modal-footer with-nav">
          <div class="nav-buttons">
            <button v-if="currentStep > 0" @click="prevStep" type="button" class="btn-secondary" :disabled="submitting">
              ← Quay lại
            </button>
            <button v-else @click="closeModal" type="button" class="btn-secondary" :disabled="submitting">
              Hủy
            </button>
          </div>

          <div class="step-indicator-mobile">
            Bước {{ currentStep + 1 }}/{{ formSteps.length }}
          </div>

          <div class="action-buttons">
            <button v-if="currentStep < formSteps.length - 1" @click="nextStep" type="button" class="btn-primary"
              :disabled="!isStepValid(currentStep)">
              Tiếp theo →
            </button>
            <button v-else @click="submitProduct" type="button" class="btn-primary"
              :disabled="submitting || !isFormValid">
              {{ submitting ? '🔄 Đang xử lý...' : (showEditModal ? '💾 Cập nhật' : '➕ Thêm mới') }}
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { toast } from 'vue3-toastify'


import API_BASE_URL_LOCAL from '../Util/APIBase.js';

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
const uploadedImages = ref([])
const mainImagePreview = ref('')
const imageError = ref('')

// Form steps
const currentStep = ref(0)
const formSteps = ref([
  { label: 'Thông tin cơ bản', key: 'basic' },
  { label: 'Hình ảnh', key: 'images' },
  { label: 'Mô tả', key: 'description' },
  { label: 'Cài đặt', key: 'settings' }
])

// Pagination data
const currentPage = ref(1)
const pageSize = ref(20)

// Form data
const formData = ref({
  id: 0,
  tenVi: '',
  tenEn: '',
  tieuDeTrung: '',
  tenKhongDau: '',
  masp: '',
  idList: '',
  giaban: 0,
  gianewp: 0,
  giacu: 0,
  soluong: 0,
  donVi: '',
  stt: 1,
  noibat: 0,
  banchay: 0,
  newField: 0,
  khuyenmai: 0,
  nenmua: 0,
  hienthi: 1,
  motaVi: '',
  noidungVi: '',
  photo: '',
  videoYoutube: '',
  thuocTinh: [],
  seoTitle: '',
  seoKeywords: '',
  seoDescription: '',
  tags: '',
  dangKyKinhDoanhFile: null,
  giayPhepKinhDoanhFile: null,
  banCongBoSanPhamFile: null,
  mainImageFile: null,
  type: 'product'
})

// API configuration
const API_BASE_URL = API_BASE_URL_LOCAL

// Computed properties
const filteredProducts = computed(() => {
  let filtered = products.value

  if (searchQuery.value) {
    const query = searchQuery.value.toLowerCase()
    filtered = filtered.filter(product =>
      product.tenVi?.toLowerCase().includes(query) ||
      product.tenEn?.toLowerCase().includes(query) ||
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

const isFormValid = computed(() => {
  return formData.value.tenVi.trim() &&
    formData.value.idList &&
    formData.value.giaban > 0
})

// Methods
const isStepValid = (step) => {
  switch (step) {
    case 0: // Basic info
      return formData.value.tenVi.trim() &&
        formData.value.idList &&
        formData.value.giaban > 0
    case 1: // Images
      return true // Optional step
    case 2: // Description
      return true // Optional step
    case 3: // Settings
      return true // Optional step
    default:
      return false
  }
}

const nextStep = () => {
  if (currentStep.value < formSteps.value.length - 1 && isStepValid(currentStep.value)) {
    currentStep.value++
  }
}

const prevStep = () => {
  if (currentStep.value > 0) {
    currentStep.value--
  }
}

const goToStep = (step) => {
  if (step >= 0 && step < formSteps.value.length) {
    // Validate all previous steps before allowing navigation
    let canNavigate = true
    for (let i = 0; i < step; i++) {
      if (!isStepValid(i)) {
        canNavigate = false
        break
      }
    }

    if (canNavigate) {
      currentStep.value = step
    } else {
      toast.warning('Vui lòng hoàn thành các bước trước đó')
    }
  }
}

const openImageUpload = () => {
  document.getElementById('mainImageUpload')?.click()
}

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
    const data = await response.json()
    products.value = data
  } catch (error) {
    console.error('Error fetching products:', error)
    toast.error('Lỗi khi tải danh sách sản phẩm')
  } finally {
    loading.value = false
  }
}

const fetchCategories = async () => {
  try {
    const response = await fetch(`${API_BASE_URL}/productLists`)
    if (!response.ok) throw new Error('Network response was not ok')
    categories.value = await response.json()
  } catch (error) {
    console.error('Error fetching categories:', error)
    toast.error('Lỗi khi tải danh sách danh mục')
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
  return true
}

const uploadImageAPI = async (file) => {
  const formData = new FormData();
  formData.append('image', file);

  const response = await fetch(`${API_BASE_URL}/products/upload`, {
    method: 'POST',
    body: formData
  });

  if (!response.ok) throw new Error('Upload failed');
  return await response.json();
};

const bulkDeleteProductsAPI = async (productIds) => {
  const response = await fetch(`${API_BASE_URL}/products/bulk-delete`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ productIds })
  })
  if (!response.ok) throw new Error('Failed to bulk delete products')
  return true
}

const updateProductAPI = async (productId, data) => {
  const response = await fetch(`${API_BASE_URL}/products`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ ...data, id: productId })
  })
  if (!response.ok) throw new Error('Failed to update product')
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
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ productIds, hienthi: status })
  })
  if (!response.ok) throw new Error('Failed to bulk update status')
  return true
}

const bulkUpdateFlagAPI = async (productIds, field, value) => {
  const response = await fetch(`${API_BASE_URL}/products`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ productIds, [field]: value })
  })
  if (!response.ok) throw new Error('Failed to bulk update flag')
  return true
}

// Utility functions
const getCategoryName = (id) => {
  const category = categories.value.find(cat => cat.id === id)
  return category ? category.tenVi : "N/A"
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
    id: product.id,
    tenVi: product.tenVi || '',
    tenEn: product.tenEn || '',
    tieuDeTrung: product.tieuDeTrung || '',
    tenKhongDau: product.tenKhongDau || '',
    masp: product.masp || '',
    idList: product.idList || '',
    giaban: product.giaban || 0,
    gianewp: product.gianewp || 0,
    giacu: product.giacu || 0,
    soluong: product.soluong || 0,
    donVi: product.donVi || '',
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
    videoYoutube: product.videoYoutube || '',
    thuocTinh: product.thuocTinh || [],
    seoTitle: product.seoTitle || '',
    seoKeywords: product.seoKeywords || '',
    seoDescription: product.seoDescription || '',
    tags: product.tags || '',
    type: product.type || 'product',
    kg: product.kg || 0
  }

  // Set main image preview if exists
  if (product.photo) {
    mainImagePreview.value = getImageUrl(product.photo)
  }

  showEditModal.value = true
  currentStep.value = 0
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
    toast.success('Sản phẩm đã được xóa thành công')
  } catch (error) {
    console.error('Error deleting product:', error)
    toast.error('Có lỗi xảy ra khi xóa sản phẩm')
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
    toast.success('Các sản phẩm đã được xóa thành công')
  } catch (error) {
    console.error('Error bulk deleting products:', error)
    toast.error('Có lỗi xảy ra khi xóa sản phẩm')
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
    toast.success('Trạng thái các sản phẩm đã được cập nhật')
  } catch (error) {
    console.error('Error bulk updating status:', error)
    toast.error('Có lỗi xảy ra khi cập nhật trạng thái')
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
    toast.success('Trạng thái các sản phẩm đã được cập nhật')
  } catch (error) {
    console.error('Error bulk updating flag:', error)
    toast.error('Có lỗi xảy ra khi cập nhật trạng thái')
  }
}

const viewProduct = (product) => {
  window.open(`/product/${product.id}`, '_blank')
}

// File upload methods
const handleMainImageUpload = async (event) => {
  const file = event.target.files[0]
  if (!file) return

  // Validate file type
  if (!file.type.startsWith('image/')) {
    imageError.value = 'File phải là hình ảnh'
    return
  }

  // Validate file size (max 5MB)
  if (file.size > 5 * 1024 * 1024) {
    imageError.value = 'Kích thước file không được vượt quá 5MB'
    return
  }

  imageError.value = ''

  // Tạo preview tạm thời
  const reader = new FileReader()
  reader.onload = (e) => {
    mainImagePreview.value = e.target.result
  }
  reader.readAsDataURL(file)

  // Lưu file để upload sau
  formData.value.mainImageFile = file
  // Clear URL cũ nếu có
  formData.value.photo = ''

  // Reset input
  event.target.value = ''
}

const handleUrlInput = () => {
  // Khi người dùng nhập URL, xóa ảnh đã upload
  if (formData.value.photo) {
    mainImagePreview.value = ''
    formData.value.mainImageFile = null
    imageError.value = ''
  }
}

const removeMainImage = () => {
  mainImagePreview.value = ''
  formData.value.mainImageFile = null
  formData.value.photo = ''
  imageError.value = ''
}

const handleFileUpload = (event) => {
  const files = event.target.files
  for (let i = 0; i < files.length; i++) {
    const file = files[i]
    // Check if maximum limit reached
    if (uploadedImages.value.length >= 5) {
      toast.warning('Chỉ có thể upload tối đa 5 ảnh')
      break
    }

    const reader = new FileReader()

    reader.onload = (e) => {
      uploadedImages.value.push({
        file: file,
        preview: e.target.result,
        name: file.name
      })
    }

    reader.readAsDataURL(file)
  }

  event.target.value = ''
}

const removeImage = (index) => {
  uploadedImages.value.splice(index, 1)
}

const addAttribute = () => {
  if (!formData.value.thuocTinh) {
    formData.value.thuocTinh = []
  }
  formData.value.thuocTinh.push({ ten: '', giaTri: '' })
}

const removeAttribute = (index) => {
  formData.value.thuocTinh.splice(index, 1)
}

const handleFileChange = (event, field) => {
  const file = event.target.files[0]
  if (file) {
    formData.value[field + 'File'] = file
  }
}

const removeFile = (field) => {
  formData.value[field + 'File'] = null
  document.getElementById(field).value = ''
}

const openAddModal = () => {
  resetForm()
  showAddModal.value = true
  currentStep.value = 0
}

const closeModal = () => {
  showAddModal.value = false
  showEditModal.value = false
  productToEdit.value = null
  resetForm()
}

const resetForm = () => {
  formData.value = {
    id: 0,
    tenVi: '',
    tenEn: '',
    tieuDeTrung: '',
    tenKhongDau: '',
    masp: '',
    idList: '',
    giaban: 0,
    gianewp: 0,
    giacu: 0,
    soluong: 0,
    donVi: '',
    stt: 1,
    noibat: 0,
    banchay: 0,
    newField: 0,
    khuyenmai: 0,
    nenmua: 0,
    hienthi: 1,
    motaVi: '',
    noidungVi: '',
    photo: '',
    videoYoutube: '',
    thuocTinh: [],
    seoTitle: '',
    seoKeywords: '',
    seoDescription: '',
    tags: '',
    type: 'san-pham',
    kg: 0,
    mainImageFile: null
  }

  uploadedImages.value = []
  mainImagePreview.value = ''
  imageError.value = ''
  currentStep.value = 0
}

const submitProduct = async () => {
  if (!isFormValid.value) {
    toast.error('Vui lòng hoàn thành các thông tin bắt buộc')
    currentStep.value = 0
    return
  }

  // Validate main image - chỉ kiểm tra nếu là thêm mới
  if (showAddModal.value && !formData.value.photo && !formData.value.mainImageFile) {
    toast.error('Vui lòng chọn hình ảnh chính hoặc nhập URL hình ảnh')
    currentStep.value = 1
    return
  }

  submitting.value = true
  try {
    // Upload ảnh chính nếu có file mới
    let finalPhotoUrl = formData.value.photo;
    if (formData.value.mainImageFile) {
      const uploadResult = await uploadImageAPI(formData.value.mainImageFile);
      finalPhotoUrl = uploadResult.imageUrl;
    }

    // Chuẩn bị dữ liệu gửi lên server
    const submitData = {
      // Thông tin cơ bản
      tenVi: formData.value.tenVi,
      tenEn: formData.value.tenEn,
      tieuDeTrung: formData.value.tieuDeTrung,
      tenKhongDau: formData.value.tenKhongDau,
      masp: formData.value.masp,
      idList: formData.value.idList,

      // Giá cả
      giaban: formData.value.giaban,
      gianewp: formData.value.gianewp,
      giacu: formData.value.giacu,

      // Tồn kho & thông tin khác
      soluong: formData.value.soluong,
      donVi: formData.value.donVi,
      stt: formData.value.stt,
      kg: formData.value.kg,

      // Trạng thái
      noibat: formData.value.noibat,
      banchay: formData.value.banchay,
      newField: formData.value.newField,
      khuyenmai: formData.value.khuyenmai,
      nenmua: formData.value.nenmua,
      hienthi: formData.value.hienthi,

      // Hình ảnh & Media
      photo: finalPhotoUrl,
      videoYoutube: formData.value.videoYoutube,

      // Nội dung
      motaVi: formData.value.motaVi,
      noidungVi: formData.value.noidungVi,

      // Thuộc tính
      thuocTinh: formData.value.thuocTinh,

      // SEO
      seoTitle: formData.value.seoTitle,
      seoKeywords: formData.value.seoKeywords,
      seoDescription: formData.value.seoDescription,
      tags: formData.value.tags,

      // Type
      type: formData.value.type
    }

    // Thêm id nếu là chỉnh sửa
    if (showEditModal.value && productToEdit.value) {
      submitData.id = productToEdit.value.id;
    }

    if (showEditModal.value && productToEdit.value) {
      const updatedProduct = await updateProductAPI(productToEdit.value.id, submitData)

      // Cập nhật lại danh sách sản phẩm
      const index = products.value.findIndex(p => p.id === productToEdit.value.id)
      if (index !== -1) {
        products.value[index] = { ...products.value[index], ...updatedProduct }
      }

      showEditModal.value = false
      productToEdit.value = null
      toast.success('Sản phẩm đã được cập nhật thành công')
    } else {
      const newProduct = await createProductAPI(submitData)
      products.value.unshift(newProduct)
      showAddModal.value = false
      toast.success('Sản phẩm đã được thêm thành công')
    }

    resetForm()
  } catch (error) {
    console.error('Error saving product:', error)
    toast.error('Có lỗi xảy ra khi lưu sản phẩm: ' + error.message)
  } finally {
    submitting.value = false
  }
}

const resetFilters = () => {
  searchQuery.value = ''
  categoryFilter.value = ''
  statusFilter.value = ''
  featuredFilter.value = ''
  hotFilter.value = ''
  currentPage.value = 1
}

// Lifecycle
onMounted(() => {
  fetchProducts()
  fetchCategories()
})
</script>

<style scoped>
/* Base Styles */
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
  max-width: 800px;
}

.product-modal {
  max-width: 800px;
  max-height: 90vh;
  display: flex;
  flex-direction: column;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 24px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
}

.modal-header h3 {
  margin: 0;
  color: white;
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 18px;
}

.modal-icon {
  font-size: 20px;
}

.product-id {
  font-size: 14px;
  opacity: 0.8;
  font-weight: normal;
}

.close-btn {
  background: none;
  border: none;
  font-size: 18px;
  cursor: pointer;
  color: white;
  padding: 4px;
  border-radius: 4px;
  transition: background 0.3s;
}

.close-btn:hover {
  background: rgba(255, 255, 255, 0.2);
}

.modal-body {
  padding: 0;
  flex: 1;
  overflow-y: auto;
}

.modal-footer {
  display: flex;
  gap: 10px;
  justify-content: flex-end;
  padding: 20px;
  border-top: 1px solid #eee;
}

.modal-footer.with-nav {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 24px;
  border-top: 1px solid #eee;
  background: #f8f9fa;
}

.nav-buttons,
.action-buttons {
  flex: 1;
}

.action-buttons {
  text-align: right;
}

.step-indicator-mobile {
  display: none;
  font-size: 14px;
  color: #666;
  font-weight: 500;
}

.btn-primary,
.btn-secondary {
  padding: 12px 24px;
  border: none;
  border-radius: 8px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  font-size: 14px;
}

/* Weight Input Group */
.weight-input-group {
  position: relative;
}

.weight-input {
  padding-right: 50px;
}

.unit {
  position: absolute;
  right: 12px;
  top: 50%;
  transform: translateY(-50%);
  color: #666;
  font-weight: 600;
  background: #f8f9fa;
  padding: 2px 8px;
  border-radius: 4px;
  font-size: 12px;
}

.btn-primary {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
}

.btn-primary:hover:not(:disabled) {
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);
}

.btn-primary:disabled {
  opacity: 0.6;
  cursor: not-allowed;
  transform: none;
}

.btn-secondary {
  background: white;
  color: #666;
  border: 2px solid #e0e0e0;
}

.btn-secondary:hover:not(:disabled) {
  border-color: #667eea;
  color: #667eea;
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

.selected-more {
  padding: 8px;
  color: #666;
  font-style: italic;
  text-align: center;
}

/* Form Steps */
.form-steps {
  padding: 20px 24px 0;
  background: white;
  border-bottom: 1px solid #eee;
}

.step-indicator {
  display: flex;
  justify-content: space-between;
  position: relative;
}

.step-indicator::before {
  content: '';
  position: absolute;
  top: 20px;
  left: 0;
  right: 0;
  height: 2px;
  background: #e0e0e0;
  z-index: 1;
}

.step {
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;
  z-index: 2;
  cursor: pointer;
  flex: 1;
}

.step-number {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background: #f5f5f5;
  border: 2px solid #e0e0e0;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 600;
  margin-bottom: 8px;
  transition: all 0.3s ease;
}

.step-label {
  font-size: 12px;
  color: #666;
  text-align: center;
  font-weight: 500;
}

.step.active .step-number {
  background: #667eea;
  border-color: #667eea;
  color: white;
}

.step.completed .step-number {
  background: #4caf50;
  border-color: #4caf50;
  color: white;
}

.step.active .step-label {
  color: #667eea;
  font-weight: 600;
}

/* Form Steps Content */
.form-step {
  padding: 24px;
}

.step-header {
  margin-bottom: 24px;
  text-align: center;
}

.step-header h4 {
  font-size: 20px;
  color: #333;
  margin: 0 0 8px 0;
}

.step-description {
  color: #666;
  margin: 0;
  font-size: 14px;
}

/* Form Grid */
.form-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 16px;
}

/* Form Improvements */
.form-group {
  margin-bottom: 20px;
}

.form-label {
  display: block;
  margin-bottom: 6px;
  font-weight: 600;
  color: #333;
  font-size: 14px;
}

.required-star {
  color: #f44336;
}

.optional {
  color: #666;
  font-weight: normal;
  font-size: 12px;
}

.form-input,
.form-select,
.form-textarea {
  width: 90%;
  padding: 12px;
  border: 2px solid #e0e0e0;
  border-radius: 8px;
  font-size: 14px;
  transition: all 0.3s ease;
  background: white;
}

.form-input:focus,
.form-select:focus,
.form-textarea:focus {
  outline: none;
  border-color: #667eea;
  box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
}

.form-input.error,
.form-select.error {
  border-color: #f44336;
}

.form-textarea.large {
  min-height: 120px;
  resize: vertical;
}

/* Price Input */
.price-input-group {
  position: relative;
}

.price-input {
  padding-right: 40px;
}

.currency {
  position: absolute;
  right: 12px;
  top: 50%;
  transform: translateY(-50%);
  color: #666;
  font-weight: 600;
}

/* Upload Sections */
.upload-section {
  margin-bottom: 24px;
  padding: 20px;
  background: #f8f9fa;
  border-radius: 12px;
  border: 2px dashed #e0e0e0;
}

.section-title {
  font-weight: 600;
  color: #333;
  margin-bottom: 16px;
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 16px;
}

/* Main Image Upload */
.main-image-upload {
  text-align: center;
}

.main-image-preview {
  position: relative;
  display: inline-block;
  margin-bottom: 16px;
}

.preview-image {
  width: 200px;
  height: 200px;
  object-fit: cover;
  border-radius: 12px;
  border: 3px solid #e0e0e0;
}

.preview-actions {
  position: absolute;
  bottom: 12px;
  right: 12px;
  display: flex;
  gap: 8px;
}

.upload-placeholder {
  padding: 40px 20px;
  border: 2px dashed #ccc;
  border-radius: 12px;
  background: white;
}

.upload-icon {
  font-size: 48px;
  margin-bottom: 12px;
  opacity: 0.5;
}

.upload-text {
  color: #666;
  margin-bottom: 16px;
}

.upload-options {
  display: flex;
  align-items: center;
  gap: 12px;
  justify-content: center;
  flex-wrap: wrap;
}

.upload-option-btn {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 10px 16px;
  border: 2px solid #667eea;
  border-radius: 8px;
  background: white;
  color: #667eea;
  cursor: pointer;
  font-weight: 500;
  transition: all 0.3s ease;
}

.upload-option-btn.primary {
  background: #667eea;
  color: white;
}

.upload-option-btn:hover {
  transform: translateY(-1px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.upload-hint {
  margin-top: 12px;
  color: #999;
}

/* Additional Images */
.image-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(100px, 1fr));
  gap: 12px;
}

.image-item {
  position: relative;
  aspect-ratio: 1;
}

.preview-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 8px;
  border: 2px solid #e0e0e0;
}

.image-overlay {
  position: absolute;
  top: 4px;
  right: 4px;
  opacity: 0;
  transition: opacity 0.3s ease;
}

.image-item:hover .image-overlay {
  opacity: 1;
}

.upload-item {
  aspect-ratio: 1;
}

.upload-box {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;
  border: 2px dashed #ccc;
  border-radius: 8px;
  background: white;
  cursor: pointer;
  transition: all 0.3s ease;
}

.upload-box:hover {
  border-color: #667eea;
  background: #f8f9ff;
}

/* Attributes */
.attributes-section {
  margin-top: 24px;
}

.attribute-row {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 12px;
}

.attribute-input {
  flex: 1;
}

.attribute-separator {
  color: #666;
  font-weight: 600;
}

.btn-add-attribute {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 16px;
  border: 2px dashed #667eea;
  border-radius: 8px;
  background: transparent;
  color: #667eea;
  cursor: pointer;
  font-weight: 500;
  transition: all 0.3s ease;
  width: 100%;
  justify-content: center;
}

.btn-add-attribute:hover {
  background: #667eea;
  color: white;
}

/* Status Grid Compact */
.status-grid.compact {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 12px;
}

.status-checkbox {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 12px;
  background: white;
  border: 2px solid #e0e0e0;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.status-checkbox:hover {
  border-color: #667eea;
  transform: translateY(-1px);
}

.status-checkbox input:checked+.status-icon+.status-label {
  font-weight: 600;
}

/* Different colors for different status types */
.status-checkbox.featured input:checked~.status-icon {
  color: #ffd700;
}

.status-checkbox.hot input:checked~.status-icon {
  color: #ff6b6b;
}

.status-checkbox.new input:checked~.status-icon {
  color: #4ecdc4;
}

.status-checkbox.sale input:checked~.status-icon {
  color: #ff9ff3;
}

.status-checkbox.recommended input:checked~.status-icon {
  color: #1dd1a1;
}

.status-checkbox.visible input:checked~.status-icon {
  color: #54a0ff;
}

/* Character Count */
.char-count {
  text-align: right;
  font-size: 12px;
  color: #999;
  margin-top: 4px;
}

/* Buttons */
.btn-action {
  padding: 6px;
  border: none;
  border-radius: 6px;
  background: white;
  cursor: pointer;
  font-size: 14px;
  transition: all 0.3s ease;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.btn-action.danger {
  background: #ff6b6b;
  color: white;
}

.btn-action.small {
  padding: 4px 8px;
  font-size: 12px;
}

.btn-action:hover {
  transform: translateY(-1px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

/* Helpers */
.full-width {
  width: 100%;
}

.text-center {
  text-align: center;
}

.mt-4 {
  margin-top: 16px;
}

.mb-4 {
  margin-bottom: 16px;
}

.error-message {
  color: #f44336;
  font-size: 12px;
  margin-top: 4px;
}

.help-text {
  color: #666;
  font-size: 12px;
  margin-top: 4px;
}

/* File upload styles */
.file-input {
  display: none;
}

.upload-btn {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 10px 20px;
  background-color: #f8f9fa;
  border: 1px solid #ddd;
  border-radius: 4px;
  cursor: pointer;
  transition: all 0.3s;
}

.upload-btn:hover {
  background-color: #e9ecef;
}

.upload-btn.disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.upload-btn.disabled:hover {
  background-color: #f8f9fa;
  border-color: #ddd;
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

  .status-grid.compact {
    grid-template-columns: 1fr;
  }

  .upload-options {
    flex-direction: column;
  }

  .attribute-row {
    flex-direction: column;
    align-items: stretch;
  }

  .attribute-separator {
    display: none;
  }

  .product-modal {
    margin: 10px;
    max-height: 95vh;
  }

  .form-steps {
    display: none;
  }

  .step-indicator-mobile {
    display: block;
    text-align: center;
    flex: 1;
  }

  .modal-footer.with-nav {
    flex-direction: column;
    gap: 12px;
  }

  .nav-buttons,
  .action-buttons {
    width: 100%;
    text-align: center;
  }

  .form-grid {
    grid-template-columns: 1fr;
  }

  .status-grid.compact {
    grid-template-columns: 1fr;
  }

  .upload-options {
    flex-direction: column;
  }

  .attribute-row {
    flex-direction: column;
    align-items: stretch;
  }

  .attribute-separator {
    display: none;
  }

  .table-wrapper {
    max-height: calc(100vh - 450px);
  }
}
</style>