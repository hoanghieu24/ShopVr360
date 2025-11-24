<template>
  <div class="products-page">
    <!-- Main Products Content -->
    <div class="products-container">
      <!-- Breadcrumb -->
      <div class="breadcrumb">
        <a href="/">Trang chủ</a>
        <span class="separator">/</span>
        <span class="current">Tất cả sản phẩm</span>
      </div>

      <!-- Page Title -->
      <div class="page-title">
        <h1>TẤT CẢ SẢN PHẨM</h1>
        <p class="subtitle">Khám phá bộ sưu tập sản phẩm đa dạng và chất lượng</p>
      </div>

      <!-- Filters and Sorting -->
      <div class="filters-section">
        <div class="filter-group">
          <label for="category-filter">Danh mục:</label>
          <select id="category-filter" v-model="selectedCategory" @change="filterProducts">
            <option value="all">Tất cả danh mục</option>
            <option v-for="category in categories" :key="category.id" :value="category.id">
              {{ category.name }}
            </option>
          </select>
        </div>

        <div class="filter-group">
          <label for="price-filter">Khoảng giá:</label>
          <select id="price-filter" v-model="selectedPriceRange" @change="filterProducts">
            <option value="all">Tất cả mức giá</option>
            <option value="0-500000">Dưới 500,000₫</option>
            <option value="500000-2000000">500,000₫ - 2,000,000₫</option>
            <option value="2000000-5000000">2,000,000₫ - 5,000,000₫</option>
            <option value="5000000-10000000">5,000,000₫ - 10,000,000₫</option>
            <option value="10000000-99999999">Trên 10,000,000₫</option>
          </select>
        </div>

        <div class="filter-group">
          <label for="sort-by">Sắp xếp:</label>
          <select id="sort-by" v-model="selectedSort" @change="sortProducts">
            <option value="default">Mặc định</option>
            <option value="price_asc">Giá: Thấp đến Cao</option>
            <option value="price_desc">Giá: Cao đến Thấp</option>
            <option value="name_asc">Tên: A-Z</option>
            <option value="name_desc">Tên: Z-A</option>
            <option value="newest">Mới nhất</option>
            <option value="bestseller">Bán chạy nhất</option>
          </select>
        </div>

        <div class="view-options">
          <button class="view-btn" :class="{ active: viewMode === 'grid' }" @click="viewMode = 'grid'">
            <i class="icon-grid-view">◼◼</i>
          </button>
          <button class="view-btn" :class="{ active: viewMode === 'list' }" @click="viewMode = 'list'">
            <i class="icon-list-view">≡</i>
          </button>
        </div>
      </div>

      <!-- Products Count -->
      <div class="products-count">
        Hiển thị {{ startIndex + 1 }}-{{ endIndex }} của {{ totalProducts }} sản phẩm
      </div>

      <!-- Products Grid/List -->
      <div class="products-display">
        <!-- Loading State -->
        <div v-if="loading" class="loading-state">
          <div class="spinner"></div>
          <p>Đang tải sản phẩm...</p>
        </div>

        <!-- Error State -->
        <div v-else-if="error" class="error-state">
          <i class="icon-error">⚠️</i>
          <h3>Lỗi tải sản phẩm</h3>
          <p>{{ error }}</p>
          <button class="retry-btn" @click="fetchProducts">Thử lại</button>
        </div>

        <!-- Products Grid View -->
        <div v-else-if="viewMode === 'grid' && paginatedProducts.length > 0" class="products-grid">
          <div v-for="product in paginatedProducts" :key="product.id" class="product-card">
            <div class="product-image-container">
              <img :src="getProductImage(product)" :alt="product.ten_vi" class="product-image" @error="handleImageError">
              <div class="product-badges">
                <span v-if="product.is_new" class="badge new">MỚI</span>
                <span v-if="product.giam_gia > 0" class="badge discount">-{{ product.giam_gia }}%</span>
                <span v-if="product.noi_bat" class="badge bestseller">BÁN CHẠY</span>
              </div>
              <div class="product-overlay">
                <button class="quick-view-btn" @click="openQuickView(product)">
                  <i class="icon-eye">👁️</i>
                  Xem nhanh
                </button>
                <button class="add-to-cart-btn" @click="addToCart(product)">
                  <i class="icon-cart">🛒</i>
                  Thêm vào giỏ
                </button>
              </div>
            </div>
            <div class="product-info">
              <h3 class="product-name">{{ product.tenVi }}</h3>
              <div class="product-rating">
                <div class="stars">
                  <i v-for="n in 5" :key="n" 
                     :class="n <= getProductRating(product) ? 'icon-star-full' : 'icon-star-empty'">
                    {{ n <= getProductRating(product) ? '★' : '☆' }}
                  </i>
                </div>
                <span class="rating-count">({{ product.so_luot_danh_gia || 0 }})</span>
              </div>
              <div class="product-price">
                <span class="current-price">{{ formatPrice(getFinalPrice(product)) }}</span>
                <span v-if="product.giam_gia > 0" class="original-price">
                  {{ formatPrice(product.giaban) }}
                </span>
              </div>
              <div class="product-actions">
                <button class="wishlist-btn" @click="toggleWishlist(product)">
                  <i :class="product.in_wishlist ? 'icon-heart-filled' : 'icon-heart'">
                    {{ product.in_wishlist ? '❤️' : '🤍' }}
                  </i>
                </button>
                <button class="compare-btn" @click="addToCompare(product)">
                  <i class="icon-compare">⚖️</i>
                </button>
              </div>
            </div>
          </div>
        </div>

        <!-- Products List View -->
        <div v-else-if="viewMode === 'list' && paginatedProducts.length > 0" class="products-list">
          <div v-for="product in paginatedProducts" :key="product.id" class="product-list-item">
            <div class="list-image-container">
              <img :src="getProductImage(product)" :alt="product.ten_vi" class="list-product-image" @error="handleImageError">
              <div class="list-badges">
                <span v-if="product.is_new" class="badge new">MỚI</span>
                <span v-if="product.giam_gia > 0" class="badge discount">-{{ product.giam_gia }}%</span>
              </div>
            </div>
            <div class="list-product-info">
              <h3 class="list-product-name">{{ product.tenVi }}</h3>
              <div class="list-product-rating">
                <div class="stars">
                  <i v-for="n in 5" :key="n" 
                     :class="n <= getProductRating(product) ? 'icon-star-full' : 'icon-star-empty'">
                    {{ n <= getProductRating(product) ? '★' : '☆' }}
                  </i>
                </div>
                <span class="rating-count">({{ product.so_luot_danh_gia || 0 }} đánh giá)</span>
              </div>
              <p class="list-product-description">{{ product.mo_ta_ngan || 'Sản phẩm chất lượng cao' }}</p>
              <div class="list-product-price">
                <span class="current-price">{{ formatPrice(getFinalPrice(product)) }}</span>
                <span v-if="product.giam_gia > 0" class="original-price">
                  {{ formatPrice(product.giaban) }}
                </span>
              </div>
              <div class="list-product-actions">
                <button class="add-to-cart-btn" @click="addToCart(product)">
                  <i class="icon-cart">🛒</i>
                  Thêm vào giỏ
                </button>
                <button class="wishlist-btn" @click="toggleWishlist(product)">
                  <i :class="product.in_wishlist ? 'icon-heart-filled' : 'icon-heart'">
                    {{ product.in_wishlist ? '❤️' : '🤍' }}
                  </i>
                </button>
                <button class="compare-btn" @click="addToCompare(product)">
                  <i class="icon-compare">⚖️</i>
                </button>
                <button class="quick-view-btn" @click="openQuickView(product)">
                  <i class="icon-eye">👁️</i>
                </button>
              </div>
            </div>
          </div>
        </div>

        <!-- No Products State -->
        <div v-else-if="!loading && displayedProducts.length === 0" class="no-products">
          <i class="icon-search">🔍</i>
          <h3>Không tìm thấy sản phẩm nào</h3>
          <p>Hãy thử điều chỉnh bộ lọc hoặc tìm kiếm với từ khóa khác</p>
          <button class="reset-filters-btn" @click="resetFilters">Đặt lại bộ lọc</button>
        </div>
      </div>

      <!-- Pagination -->
      <div v-if="displayedProducts.length > 0 && totalPages > 1" class="pagination">
        <button class="pagination-btn" :disabled="currentPage === 1" @click="changePage(currentPage - 1)">
          <i class="icon-chevron-left">←</i>
          Trước
        </button>
        
        <button v-for="page in visiblePages" :key="page" 
                class="pagination-btn" 
                :class="{ active: page === currentPage }"
                @click="changePage(page)"
                :disabled="page === '...'">
          {{ page }}
        </button>
        
        <button class="pagination-btn" :disabled="currentPage === totalPages" @click="changePage(currentPage + 1)">
          Sau
          <i class="icon-chevron-right">→</i>
        </button>
      </div>

      <!-- Quick View Modal -->
      <div v-if="showQuickView" class="modal-overlay" @click="closeQuickView">
        <div class="quick-view-modal" @click.stop>
          <button class="close-modal" @click="closeQuickView">
            <i class="icon-close">✕</i>
          </button>
          <div v-if="selectedProduct" class="quick-view-content">
            <div class="quick-view-images">
              <img :src="getProductImage(selectedProduct)" :alt="selectedProduct.ten_vi" class="main-image" @error="handleImageError">
            </div>
            <div class="quick-view-details">
              <h2>{{ selectedProduct.tenVi }}</h2>
              <div class="quick-view-rating">
                <div class="stars">
                  <i v-for="n in 5" :key="n" 
                     :class="n <= getProductRating(selectedProduct) ? 'icon-star-full' : 'icon-star-empty'">
                    {{ n <= getProductRating(selectedProduct) ? '★' : '☆' }}
                  </i>
                </div>
                <span class="rating-count">({{ selectedProduct.so_luot_danh_gia || 0 }} đánh giá)</span>
              </div>
              <div class="quick-view-price">
                <span class="current-price">{{ formatPrice(getFinalPrice(selectedProduct)) }}</span>
                <span v-if="selectedProduct.giam_gia > 0" class="original-price">
                  {{ formatPrice(selectedProduct.giaban) }}
                </span>
              </div>
              <p class="quick-view-description">{{ selectedProduct.mo_ta || selectedProduct.mo_ta_ngan || 'Sản phẩm chất lượng cao' }}</p>
              
              <div class="quick-view-actions">
                <div class="quantity-selector">
                  <button class="qty-btn" @click="decreaseQuantity">-</button>
                  <span class="quantity">{{ quickViewQuantity }}</span>
                  <button class="qty-btn" @click="increaseQuantity">+</button>
                </div>
                <button class="add-to-cart-large" @click="addToCart(selectedProduct, quickViewQuantity)">
                  <i class="icon-cart">🛒</i>
                  Thêm vào giỏ hàng
                </button>
                <button class="wishlist-btn" @click="toggleWishlist(selectedProduct)">
                  <i :class="selectedProduct.in_wishlist ? 'icon-heart-filled' : 'icon-heart'">
                    {{ selectedProduct.in_wishlist ? '❤️' : '🤍' }}
                  </i>
                </button>
              </div>
              
              <div class="product-meta">
                <div class="meta-item">
                  <strong>SKU:</strong> {{ selectedProduct.sku || selectedProduct.ma_san_pham || 'N/A' }}
                </div>
                <div class="meta-item">
                  <strong>Danh mục:</strong> {{ selectedProduct.ten_danh_muc || 'Chưa phân loại' }}
                </div>
                <div class="meta-item">
                  <strong>Tình trạng:</strong> 
                  <span :class="selectedProduct.so_luong_ton > 0 ? 'in-stock' : 'out-of-stock'">
                    {{ selectedProduct.so_luong_ton > 0 ? 'Còn hàng' : 'Hết hàng' }}
                  </span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'ProductsPage',
  data() {
    return {
      // Products data
      allProducts: [],
      displayedProducts: [],
      loading: true,
      error: null,
      viewMode: 'grid',
      
      // Categories data
      categories: [],
      
      // Filter data
      selectedCategory: 'all',
      selectedPriceRange: 'all',
      selectedSort: 'default',
      
      // Pagination
      currentPage: 1,
      productsPerPage: 16, // Đã sửa thành 15 sản phẩm mỗi trang
      totalPages: 1,
      totalProducts: 0
    }
  },
  
  computed: {
    // Tính toán sản phẩm hiển thị cho trang hiện tại
    paginatedProducts() {
      const startIndex = (this.currentPage - 1) * this.productsPerPage;
      const endIndex = startIndex + this.productsPerPage;
      return this.displayedProducts.slice(startIndex, endIndex);
    },
    
    // Tính toán chỉ số bắt đầu và kết thúc
    startIndex() {
      return (this.currentPage - 1) * this.productsPerPage;
    },
    
    endIndex() {
      const end = this.startIndex + this.productsPerPage;
      return end > this.displayedProducts.length ? this.displayedProducts.length : end;
    },
    
    visiblePages() {
      const pages = [];
      const total = this.totalPages;
      const current = this.currentPage;
      
      if (total <= 7) {
        for (let i = 1; i <= total; i++) {
          pages.push(i);
        }
      } else {
        if (current <= 4) {
          for (let i = 1; i <= 5; i++) {
            pages.push(i);
          }
          pages.push('...');
          pages.push(total);
        } else if (current >= total - 3) {
          pages.push(1);
          pages.push('...');
          for (let i = total - 4; i <= total; i++) {
            pages.push(i);
          }
        } else {
          pages.push(1);
          pages.push('...');
          for (let i = current - 1; i <= current + 1; i++) {
            pages.push(i);
          }
          pages.push('...');
          pages.push(total);
        }
      }
      
      return pages;
    }
  },
  
  async mounted() {
    await this.fetchCategories();
    await this.fetchProducts();
  },
  
  methods: {
    async fetchCategories() {
      try {
        const response = await axios.get('/api/categories');
        this.categories = response.data;
      } catch (error) {
        console.error('Error fetching categories:', error);
      }
    },
    
    async fetchProducts() {
      this.loading = true;
      this.error = null;
      
      try {
        const response = await axios.get('/api/products/view');
        
        if (response.data && Array.isArray(response.data)) {
          this.allProducts = response.data;
          this.totalProducts = this.allProducts.length;
          this.filterProducts();
        } else {
          throw new Error('Dữ liệu sản phẩm không hợp lệ');
        }
      } catch (error) {
        console.error('Error fetching products:', error);
        this.error = error.response?.data?.message || 'Không thể tải danh sách sản phẩm';
        this.allProducts = [];
        this.displayedProducts = [];
      } finally {
        this.loading = false;
      }
    },
    
    getProductImage(product) {
      if (product.photo) {
        return product.photo;
      }
      return 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTixbrVNY9XIHQBZ1iehMIV0Z9AtHB9dp46lg&s';
    },
    
    handleImageError(event) {
      event.target.src = 'https://via.placeholder.com/300x300?text=No+Image';
    },
    
    getFinalPrice(product) {
      // Sửa lỗi tính toán giá
      if (product.giam_gia && product.giam_gia > 0) {
        return product.giaban * (1 - product.giam_gia / 100);
      }
      return product.giaban;
    },
    
    getProductRating(product) {
      if (product.trung_binh_sao) {
        return Math.round(product.trung_binh_sao);
      }
      return 4;
    },
    
    filterProducts() {
      let filtered = [...this.allProducts];
      
      if (this.selectedCategory !== 'all') {
        filtered = filtered.filter(product => 
          product.danh_muc_id == this.selectedCategory || 
          product.id_danh_muc == this.selectedCategory
        );
      }
      
      if (this.selectedPriceRange !== 'all') {
        const [min, max] = this.selectedPriceRange.split('-').map(Number);
        filtered = filtered.filter(product => {
          const finalPrice = this.getFinalPrice(product);
          return finalPrice >= min && finalPrice <= max;
        });
      }
      
      this.displayedProducts = filtered;
      this.currentPage = 1;
      this.updatePagination();
    },
    
    sortProducts() {
      const sorted = [...this.displayedProducts];
      
      switch (this.selectedSort) {
        case 'price_asc':
          sorted.sort((a, b) => this.getFinalPrice(a) - this.getFinalPrice(b));
          break;
        case 'price_desc':
          sorted.sort((a, b) => this.getFinalPrice(b) - this.getFinalPrice(a));
          break;
        case 'name_asc':
          sorted.sort((a, b) => (a.tenVi || a.ten_vi).localeCompare(b.tenVi || b.ten_vi));
          break;
        case 'name_desc':
          sorted.sort((a, b) => (b.tenVi || b.ten_vi).localeCompare(a.tenVi || a.ten_vi));
          break;
        case 'newest':
          sorted.sort((a, b) => new Date(b.created_at) - new Date(a.created_at));
          break;
        case 'bestseller':
          sorted.sort((a, b) => {
            if (a.noi_bat && !b.noi_bat) return -1;
            if (!a.noi_bat && b.noi_bat) return 1;
            return (b.so_luong_da_ban || 0) - (a.so_luong_da_ban || 0);
          });
          break;
      }
      
      this.displayedProducts = sorted;
    },
    
    updatePagination() {
      this.totalPages = Math.ceil(this.displayedProducts.length / this.productsPerPage);
    },
    
    changePage(page) {
      if (page >= 1 && page <= this.totalPages && page !== this.currentPage) {
        this.currentPage = page;
      }
    },
    
    formatPrice(price) {
      if (!price) return '0₫';
      return new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND'
      }).format(price);
    },
    
    addToCart(product, quantity = 1) {
      alert(`Đã thêm ${quantity} ${product.tenVi || product.ten_vi} vào giỏ hàng`);
    },
    
    toggleWishlist(product) {
      product.in_wishlist = !product.in_wishlist;
    },
    
    addToCompare(product) {
      alert(`Đã thêm ${product.tenVi || product.ten_vi} vào danh sách so sánh`);
    },
    
    openQuickView(product) {
      this.selectedProduct = product;
      this.quickViewQuantity = 1;
      this.showQuickView = true;
    },
    
    closeQuickView() {
      this.showQuickView = false;
      this.selectedProduct = null;
    },
    
    increaseQuantity() {
      this.quickViewQuantity++;
    },
    
    decreaseQuantity() {
      if (this.quickViewQuantity > 1) {
        this.quickViewQuantity--;
      }
    },
    
    resetFilters() {
      this.selectedCategory = 'all';
      this.selectedPriceRange = 'all';
      this.selectedSort = 'default';
      this.filterProducts();
    }
  },
  
  watch: {
    displayedProducts() {
      this.updatePagination();
    },
    
    currentPage() {
      window.scrollTo({ top: 0, behavior: 'smooth' });
    }
  }
}
</script>

<style scoped>
/* CSS styles giữ nguyên, chỉ thêm phần products-count */
.products-page {
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  background-color: #f8f9fa;
  min-height: 100vh;
}

.products-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.breadcrumb {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 20px;
  font-size: 14px;
  color: #666;
}

.breadcrumb a {
  color: #007bff;
  text-decoration: none;
}

.breadcrumb a:hover {
  text-decoration: underline;
}

.separator {
  color: #999;
}

.page-title {
  text-align: center;
  margin-bottom: 30px;
}

.page-title h1 {
  font-size: 32px;
  color: #333;
  margin-bottom: 8px;
}

.subtitle {
  font-size: 16px;
  color: #666;
}

.filters-section {
  display: flex;
  gap: 20px;
  margin-bottom: 20px;
  padding: 20px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  flex-wrap: wrap;
  align-items: center;
}

.filter-group {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.filter-group label {
  font-weight: 600;
  font-size: 14px;
  color: #333;
}

.filter-group select {
  padding: 8px 12px;
  border: 1px solid #ddd;
  border-radius: 4px;
  background: white;
  min-width: 150px;
}

.view-options {
  display: flex;
  gap: 5px;
  margin-left: auto;
}

.view-btn {
  padding: 8px 12px;
  border: 1px solid #ddd;
  background: white;
  border-radius: 4px;
  cursor: pointer;
  transition: all 0.3s;
}

.view-btn.active {
  background: #007bff;
  color: white;
  border-color: #007bff;
}

.products-count {
  text-align: right;
  margin-bottom: 15px;
  color: #666;
  font-size: 14px;
  font-weight: 500;
}

.products-display {
  min-height: 400px;
}

.loading-state {
  text-align: center;
  padding: 60px 20px;
}

.spinner {
  border: 3px solid #f3f3f3;
  border-top: 3px solid #007bff;
  border-radius: 50%;
  width: 40px;
  height: 40px;
  animation: spin 1s linear infinite;
  margin: 0 auto 20px;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

.error-state {
  text-align: center;
  padding: 60px 20px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
}

.error-state i {
  font-size: 48px;
  margin-bottom: 20px;
  display: block;
}

.error-state h3 {
  font-size: 24px;
  color: #dc3545;
  margin-bottom: 10px;
}

.error-state p {
  color: #666;
  margin-bottom: 20px;
}

.retry-btn {
  padding: 10px 20px;
  background: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-weight: 600;
}

.retry-btn:hover {
  background: #0056b3;
}

.products-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 20px;
  margin-bottom: 30px;
}

.product-card {
  background: white;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
  transition: transform 0.3s, box-shadow 0.3s;
}

.product-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 5px 15px rgba(0,0,0,0.2);
}

.product-image-container {
  position: relative;
  overflow: hidden;
  height: 250px;
}

.product-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s;
}

.product-card:hover .product-image {
  transform: scale(1.05);
}

.product-badges {
  position: absolute;
  top: 10px;
  left: 10px;
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.badge {
  padding: 4px 8px;
  border-radius: 4px;
  font-size: 11px;
  font-weight: bold;
  color: white;
}

.badge.new {
  background: #28a745;
}

.badge.discount {
  background: #dc3545;
}

.badge.bestseller {
  background: #ffc107;
  color: #333;
}

.product-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0,0,0,0.7);
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 10px;
  opacity: 0;
  transition: opacity 0.3s;
}

.product-card:hover .product-overlay {
  opacity: 1;
}

.quick-view-btn, .add-to-cart-btn {
  padding: 10px 15px;
  border: none;
  border-radius: 4px;
  background: white;
  color: #333;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s;
  display: flex;
  align-items: center;
  gap: 5px;
}

.quick-view-btn:hover, .add-to-cart-btn:hover {
  background: #007bff;
  color: white;
}

.product-info {
  padding: 15px;
}

.product-name {
  font-size: 16px;
  font-weight: 600;
  margin-bottom: 8px;
  color: #333;
  line-height: 1.3;
  height: 42px;
  overflow: hidden;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}

.product-rating {
  display: flex;
  align-items: center;
  gap: 5px;
  margin-bottom: 8px;
}

.stars {
  display: flex;
  gap: 2px;
  color: #ffc107;
}

.rating-count {
  font-size: 12px;
  color: #666;
}

.product-price {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 12px;
}

.current-price {
  font-size: 18px;
  font-weight: bold;
  color: #dc3545;
}

.original-price {
  font-size: 14px;
  color: #999;
  text-decoration: line-through;
}

.product-actions {
  display: flex;
  gap: 8px;
}

.wishlist-btn, .compare-btn {
  padding: 8px;
  border: 1px solid #ddd;
  background: white;
  border-radius: 4px;
  cursor: pointer;
  transition: all 0.3s;
}

.wishlist-btn:hover, .compare-btn:hover {
  background: #f8f9fa;
  border-color: #007bff;
}

.products-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.product-list-item {
  display: flex;
  gap: 20px;
  background: white;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
}

.list-image-container {
  position: relative;
  flex-shrink: 0;
  width: 200px;
  height: 200px;
}

.list-product-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 4px;
}

.list-badges {
  position: absolute;
  top: 10px;
  left: 10px;
  display: flex;
  gap: 5px;
}

.list-product-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.list-product-name {
  font-size: 18px;
  font-weight: 600;
  margin-bottom: 8px;
  color: #333;
}

.list-product-rating {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 10px;
}

.list-product-description {
  color: #666;
  line-height: 1.5;
  margin-bottom: 15px;
  flex: 1;
}

.list-product-price {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 15px;
}

.list-product-actions {
  display: flex;
  gap: 10px;
}

.no-products {
  text-align: center;
  padding: 60px 20px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
}

.no-products i {
  font-size: 48px;
  margin-bottom: 20px;
  display: block;
}

.no-products h3 {
  font-size: 24px;
  color: #333;
  margin-bottom: 10px;
}

.no-products p {
  color: #666;
  margin-bottom: 20px;
}

.reset-filters-btn {
  padding: 10px 20px;
  background: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-weight: 600;
  transition: background 0.3s;
}

.reset-filters-btn:hover {
  background: #0056b3;
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 5px;
  margin-top: 30px;
}

.pagination-btn {
  padding: 8px 12px;
  border: 1px solid #ddd;
  background: white;
  border-radius: 4px;
  cursor: pointer;
  transition: all 0.3s;
  display: flex;
  align-items: center;
  gap: 5px;
}

.pagination-btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.pagination-btn.active {
  background: #007bff;
  color: white;
  border-color: #007bff;
}

.pagination-btn:not(:disabled):hover {
  background: #f8f9fa;
  border-color: #007bff;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0,0,0,0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.quick-view-modal {
  background: white;
  border-radius: 8px;
  width: 90%;
  max-width: 900px;
  max-height: 90vh;
  overflow-y: auto;
  position: relative;
}

.close-modal {
  position: absolute;
  top: 15px;
  right: 15px;
  background: none;
  border: none;
  font-size: 24px;
  cursor: pointer;
  z-index: 1001;
  color: #333;
}

.quick-view-content {
  display: flex;
  gap: 30px;
  padding: 30px;
}

.quick-view-images {
  flex: 1;
}

.main-image {
  width: 100%;
  height: 400px;
  object-fit: cover;
  border-radius: 8px;
}

.quick-view-details {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.quick-view-details h2 {
  font-size: 24px;
  color: #333;
  margin-bottom: 10px;
}

.quick-view-rating {
  display: flex;
  align-items: center;
  gap: 10px;
}

.quick-view-price {
  display: flex;
  align-items: center;
  gap: 15px;
}

.quick-view-description {
  color: #666;
  line-height: 1.6;
}

.quick-view-actions {
  display: flex;
  align-items: center;
  gap: 15px;
  margin: 20px 0;
}

.quantity-selector {
  display: flex;
  align-items: center;
  border: 1px solid #ddd;
  border-radius: 4px;
  overflow: hidden;
}

.qty-btn {
  padding: 8px 12px;
  border: none;
  background: #f8f9fa;
  cursor: pointer;
  transition: background 0.3s;
}

.qty-btn:hover {
  background: #e9ecef;
}

.quantity {
  padding: 8px 15px;
  background: white;
  min-width: 40px;
  text-align: center;
}

.add-to-cart-large {
  flex: 1;
  padding: 12px 20px;
  background: #dc3545;
  color: white;
  border: none;
  border-radius: 4px;
  font-weight: 600;
  cursor: pointer;
  transition: background 0.3s;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
}

.add-to-cart-large:hover {
  background: #c82333;
}

.product-meta {
  border-top: 1px solid #eee;
  padding-top: 15px;
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.meta-item {
  display: flex;
  gap: 8px;
}

.meta-item strong {
  color: #333;
  min-width: 80px;
}

.in-stock {
  color: #28a745;
  font-weight: 600;
}

.out-of-stock {
  color: #dc3545;
  font-weight: 600;
}

@media (max-width: 768px) {
  .filters-section {
    flex-direction: column;
    align-items: stretch;
  }
  
  .view-options {
    margin-left: 0;
    justify-content: center;
  }
  
  .products-grid {
    grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
  }
  
  .product-list-item {
    flex-direction: column;
  }
  
  .list-image-container {
    width: 100%;
    height: 250px;
  }
  
  .quick-view-content {
    flex-direction: column;
  }
  
  .pagination {
    flex-wrap: wrap;
  }
}
</style>