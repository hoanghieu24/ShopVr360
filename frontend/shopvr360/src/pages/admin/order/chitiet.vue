<template>
    <div class="order-detail-page">
        <!-- Header -->
        <div class="header">
            <button @click="$router.back()" class="btn-back">← Quay lại</button>
            <h1>Chi tiết đơn hàng #{{ order?.madonhang }}</h1>
            <div class="header-actions">
                <button @click="printOrder" class="btn-print">🖨️ In đơn</button>
                <button @click="editOrder" class="btn-edit">✏️ Sửa đơn</button>
            </div>
        </div>

        <!-- Loading -->
        <div v-if="loading" class="loading">
            <div class="spinner"></div>
            <p>Đang tải chi tiết đơn hàng...</p>
        </div>

        <!-- Order Details -->
        <div v-else-if="order" class="order-detail-container">
            <!-- Order Status & Summary -->
            <div class="status-summary">
                <div class="status-info">
                    <div class="status-badge" :class="`status-${order.tinhtrang}`">
                        {{ getStatusText(order.tinhtrang) }}
                    </div>
                    <div class="payment-status" :class="`payment-${order.thanhtoan}`">
                        {{ order.thanhtoan ? 'Đã thanh toán' : 'Chưa thanh toán' }}
                    </div>
                </div>
                <div class="order-summary">
                    <div class="summary-item">
                        <span class="label">Ngày đặt:</span>
                        <span class="value">{{ formatDateTime(order.ngaytao) }}</span>
                    </div>
                    <div class="summary-item">
                        <span class="label">Tổng tiền:</span>
                        <span class="value total-amount">{{ formatPrice(getTotalPayment())}}</span>
                    </div>
                </div>
            </div>

            <!-- Main Content Grid -->
            <div class="detail-grid">
                <!-- Customer Information -->
                <div class="detail-section">
                    <div class="section-header">
                        <h3>👤 Thông tin khách hàng</h3>
                    </div>
                    <div class="section-content">
                        <div class="info-item">
                            <label>Họ tên:</label>
                            <span>{{ order.hoten }}</span>
                        </div>
                        <div class="info-item">
                            <label>Điện thoại:</label>
                            <span>{{ order.dienthoai }}</span>
                        </div>
                        <div class="info-item">
                            <label>Email:</label>
                            <span>{{ order.email || 'Không có' }}</span>
                        </div>
                        <div class="info-item">
                            <label>Địa chỉ:</label>
                            <span class="address">{{ order.diachi }}</span>
                        </div>
                        <div v-if="order.mavung" class="info-item">
                            <label>Mã vùng:</label>
                            <span>{{ order.mavung }}</span>
                        </div>
                    </div>
                </div>

                <!-- Shipping Information -->
                <div class="detail-section">
                    <div class="section-header">
                        <h3>🚚 Thông tin giao hàng</h3>
                    </div>
                    <div class="section-content">
                        <div class="info-item">
                            <label>Hình thức GH:</label>
                            <span>{{ getShippingMethod(order.htgh) }}</span>
                        </div>
                        <div class="info-item">
                            <label>Phương thức TT:</label>
                            <span>{{ order.httt || 'Không có' }}</span>
                        </div>
                        <div class="info-item">
                            <label>Phí vận chuyển:</label>
                            <span>{{ formatPrice(getTotalServiceFee()) }}</span>
                        </div>
                        <div v-if="order.donvivanchuyen" class="info-item">
                            <label>Đơn vị VC:</label>
                            <span>{{ order.donvivanchuyen }}</span>
                        </div>
                        <div v-if="order.madonvan" class="info-item">
                            <label>Mã đơn vận:</label>
                            <span>{{ order.madonvan }}</span>
                        </div>
                    </div>
                </div>

                <!-- Order Items - UPDATED WITH DETAILED CALCULATIONS -->
                <div class="detail-section items-section">
                    <div class="section-header">
                        <h3>📦 Chi tiết sản phẩm ({{ orderItems.length }})</h3>
                    </div>
                    <div class="section-content">
                        <div class="order-items">
                            <div v-for="(item, index) in orderItems" :key="item.id" class="order-item">
                                <div class="item-header">
                                    <span class="item-number">#{{ index + 1 }}</span>
                                    <span class="item-id">ID: {{ item.id }}</span>
                                </div>

                                <div class="item-main">
                                    <div class="item-image">
                                        <img :src="getImageUrl(item.photo)" :alt="item.ten" @error="handleImageError">
                                        <div v-if="item.giakm && item.giakm > 0 && item.giakm < item.gia"
                                            class="sale-badge">
                                            -{{ calculateDiscountPercent(item.gia, item.giakm) }}%
                                        </div>
                                    </div>

                                    <div class="item-details">
                                        <div class="item-name">{{ item.ten }}</div>
                                        <div class="item-codes">
                                            <span class="item-sku">Mã SP: {{ item.masp }}</span>
                                            <span class="item-product-id">ID SP: {{ item.id_sanpham }}</span>
                                        </div>

                                        <div class="item-attributes">
                                            <span class="attribute" v-if="item.size && item.size !== 'default'">
                                                <strong>Size:</strong> {{ item.size }}
                                            </span>
                                            <span class="attribute" v-if="item.mausac && item.mausac !== 'default'">
                                                <strong>Màu:</strong> {{ item.mausac }}
                                            </span>
                                        </div>

                                        <!-- Price Calculation Details -->
                                        <div class="price-calculation">
                                            <div class="calc-row">
                                                <span>Đơn giá:</span>
                                                <span class="original-price">{{ formatPrice(item.gia) }}</span>
                                            </div>

                                            <div v-if="item.giakm && item.giakm > 0 && item.giakm < item.gia"
                                                class="calc-row discount">
                                                <span>Giá khuyến mãi:</span>
                                                <span class="sale-price">{{ formatPrice(item.giakm) }}</span>
                                            </div>

                                            <div class="calc-row">
                                                <span>Số lượng:</span>
                                                <span class="quantity">x{{ getTotalQuantity()  }}</span>
                                            </div>

                                            <div class="calc-row total">
                                                <span><strong>Thành tiền:</strong></span>
                                                <span class="item-total-price">
                                                    <strong>{{ formatPrice(item.tonggia) }}</strong>
                                                </span>
                                            </div>

                                            <div v-if="item.giakm && item.giakm > 0 && item.giakm < item.gia"
                                                class="calc-row saved">
                                                <span>Tiết kiệm:</span>
                                                <span class="saved-amount">
                                                    +{{ formatPrice((item.gia - item.giakm) * item.soluong) }}
                                                </span>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="item-meta">
                                    <div class="meta-info">
                                        <div class="meta-item">
                                            <span class="meta-label">Trạng thái:</span>
                                            <span class="meta-value" :class="`visible-${item.hienthi}`">
                                                {{ item.hienthi ? 'Hiển thị' : 'Ẩn' }}
                                            </span>
                                        </div>
                                        <div class="meta-item">
                                            <span class="meta-label">STT:</span>
                                            <span class="meta-value">{{ item.stt }}</span>
                                        </div>
                                        <div class="meta-item">
                                            <span class="meta-label">Lượt xem:</span>
                                            <span class="meta-value">{{ item.view }}</span>
                                        </div>
                                        <div v-if="item.partner_id" class="meta-item">
                                            <span class="meta-label">Partner:</span>
                                            <span class="meta-value">{{ item.partner_id }}</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Order Items Summary -->
                        <div class="order-summary-detailed">
                            <h4>Tổng kết đơn hàng</h4>
                            <div class="summary-section">
                                <div class="summary-title">Tổng sản phẩm</div>
                                <div class="summary-calc">
                                    <div class="calc-line">
                                        <span>Tổng số lượng:</span>
                                        <span><strong>{{ getTotalQuantity() }}</strong> sản phẩm</span>
                                    </div>
                                    <div class="calc-line">
                                        <span>Tổng tiền hàng:</span>
                                        <span>{{ formatPrice(getTotalProductPrice()) }}</span>
                                    </div>

                                    <div class="calc-line">
                                        <span>Tổng trọng lượng:</span>
                                        <span><strong>{{ totalWeight.toFixed(2) }}</strong> kg</span>
                                    </div>
                                    <div v-if="getTotalSaved() > 0" class="calc-line saved">
                                        <span>Tiết kiệm từ KM:</span>
                                        <span class="saved-total">+{{ formatPrice(getTotalSaved()) }}</span>
                                    </div>
                                </div>
                            </div>

                            <div class="summary-section">
                                <div class="summary-title">Phí dịch vụ</div>
                                <div class="summary-calc">
                                    <div class="calc-line">
                                        <span>Phí vận chuyển:</span>
                                        <span> <span><strong>{{ totalWeight.toFixed(2) }}</strong> kg</span> = {{
                                            formatPrice(getTotalServiceFee()) }}</span>
                                    </div>
                                    <div v-if="order.phithem" class="calc-line">
                                        <span>Phí thêm:</span>
                                        <span>{{ formatPrice(order.phithem) }}</span>
                                    </div>
                                    <div v-if="order.phigiam" class="calc-line discount">
                                        <span>Giảm giá đơn hàng:</span>
                                        <span>-{{ formatPrice(order.phigiam) }}</span>
                                    </div>
                                </div>
                            </div>

                            <div class="summary-section final">
                                <div class="summary-title">Tổng thanh toán</div>
                                <div class="final-calc">
                                    <div class="calc-line">
                                        <span>Tổng tiền hàng:</span>
                                        <span>{{ formatPrice(getTotalProductPrice()) }}</span>
                                    </div>
                                    <div class="calc-line">
                                        <span>Phí dịch vụ:</span>
                                        <span>{{ formatPrice(getTotalServiceFee()) }}</span>
                                    </div>
                                    <div v-if="order.phigiam" class="calc-line discount">
                                        <span>Giảm giá:</span>
                                        <span>-{{ formatPrice(order.phigiam) }}</span>
                                    </div>
                                    <div class="calc-line final-total">
                                        <span><strong>Tổng thanh toán:</strong></span>
                                        <span><strong>{{ formatPrice(getTotalPayment()) }}</strong></span>
                                    </div>
                                    <div class="calculation-breakdown">
                                        <small>
                                            = {{ formatPrice(getTotalProductPrice()) }} (tiền hàng)
                                            + {{ formatPrice(getTotalServiceFee()) }} (phí dịch vụ)
                                            - {{ formatPrice(order.phigiam || 0) }} (giảm giá)
                                        </small>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Additional Information -->
                <div class="detail-section">
                    <div class="section-header">
                        <h3>📝 Thông tin bổ sung</h3>
                    </div>
                    <div class="section-content">
                        <div class="info-grid">
                            <div class="info-item">
                                <label>Nội dung đơn hàng:</label>
                                <span class="content">{{ order.noidung || 'Không có' }}</span>
                            </div>
                            <div class="info-item">
                                <label>Ghi chú:</label>
                                <span class="content">{{ order.ghichu || 'Không có' }}</span>
                            </div>
                            <div class="info-item">
                                <label>Mã khuyến mãi:</label>
                                <span>{{ order.makm || 'Không có' }}</span>
                            </div>
                            <div class="info-item">
                                <label>Nguồn đơn hàng:</label>
                                <span>{{ order.nguongoc || 'Không có' }}</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Action Buttons -->
            <div class="action-buttons">
                <button @click="updateOrderStatus(1)" v-if="order.tinhtrang === 0" class="btn-confirm">
                    ✅ Xác nhận đơn hàng
                </button>
                <button @click="updateOrderStatus(2)" v-if="order.tinhtrang === 1" class="btn-shipping">
                    🚚 Bắt đầu giao hàng
                </button>
                <button @click="updateOrderStatus(3)" v-if="order.tinhtrang === 2" class="btn-complete">
                    📦 Hoàn thành đơn hàng
                </button>
                <button @click="updateOrderStatus(4)" v-if="order.tinhtrang !== 4" class="btn-cancel">
                    ❌ Hủy đơn hàng
                </button>
                <button @click="markAsPaid" v-if="!order.thanhtoan" class="btn-pay">
                    💰 Đánh dấu đã thanh toán
                </button>
            </div>
        </div>

        <!-- Error State -->
        <div v-else class="error-state">
            <div class="error-icon">❌</div>
            <h3>Không tìm thấy đơn hàng</h3>
            <p>Đơn hàng bạn đang tìm kiếm không tồn tại hoặc đã bị xóa.</p>
            <button @click="$router.back()" class="btn-back">Quay lại danh sách</button>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { toast } from 'vue3-toastify'
import API_BASE_URL_LOCAL from '../Util/APIBase.js';

const route = useRoute()
const router = useRouter()

// Reactive data
const order = ref(null)
const orderItems = ref([])
const loading = ref(false)
const totalWeight = ref(0)
const shippingFee = ref(0)
const totalServiceFee = ref(0)
const totalPayment = ref(0)

// API configuration
const API_BASE_URL = API_BASE_URL_LOCAL

// Lifecycle
onMounted(() => {
    fetchOrderDetail()
})

// API calls
const fetchOrderDetail = async () => {
    loading.value = true
    try {
        const orderId = route.params.id

        const response = await fetch(`${API_BASE_URL}/oders/detail/${orderId}`)
        if (!response.ok) throw new Error('Failed to fetch order detail')

        const data = await response.json()

        order.value = data.order
        orderItems.value = data.items
        totalWeight.value = data.totalWeight || 0
        shippingFee.value = data.shippingFee || 0
        totalServiceFee.value = data.totalServiceFee || 0
        totalPayment.value = data.totalPayment || 0

    } catch (error) {
        console.error('Error fetching order detail:', error)
        toast.error('Lỗi khi tải chi tiết đơn hàng')
    } finally {
        loading.value = false
    }
}

const updateOrderStatus = async (newStatus) => {
    try {
        const response = await fetch(`${API_BASE_URL}/oders/${order.value.id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ tinhtrang: newStatus })
        })

        if (!response.ok) throw new Error('Failed to update order status')

        order.value.tinhtrang = newStatus
        toast.success('Cập nhật trạng thái thành công')
    } catch (error) {
        console.error('Error updating order status:', error)
        toast.error('Có lỗi xảy ra khi cập nhật trạng thái')
    }
}

const markAsPaid = async () => {
    try {
        const response = await fetch(`${API_BASE_URL}/oders/pay/${order.value.id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ thanhtoan: 1 })
        })

        if (!response.ok) throw new Error('Failed to update payment status')

        order.value.thanhtoan = 1
        toast.success('Đánh dấu đã thanh toán thành công')
    } catch (error) {
        console.error('Error updating payment status:', error)
        toast.error('Có lỗi xảy ra khi cập nhật thanh toán')
    }
}

// Calculation functions
const getTotalQuantity = () => {
    return orderItems.value.reduce((total, item) => total + item.soluong, 0)
}

const getTotalProductPrice = () => {
    return orderItems.value.reduce((total, item) => total + (item.gia * item.soluong), 0)
}
const getTotalWeight = () => {
    return orderItems.value.reduce((total, item) => total + ((item.kg || 0) * item.soluong), 0)
}

const getTotalSaved = () => {
    return orderItems.value.reduce((total, item) => {
        if (item.giakm && item.giakm > 0 && item.giakm < item.gia) {
            return total + (item.gia - item.giakm) * item.soluong
        }
        return total
    }, 0)
}

const getTotalServiceFee = () => {
    return shippingFee.value + (order.value?.phithem || 0)
}

const getTotalPayment = () => {
    const totalProductPrice = getTotalProductPrice()
    const totalServiceFee = getTotalServiceFee()
    const discount = order.value?.phigiam || 0
    return totalProductPrice + totalServiceFee - discount
}

const calculateDiscountPercent = (originalPrice, salePrice) => {
    if (!originalPrice || !salePrice || originalPrice <= salePrice) return 0
    return Math.round(((originalPrice - salePrice) / originalPrice) * 100)
}

// Utility functions
const getStatusText = (status) => {
    const statusMap = {
        0: 'Chờ xác nhận',
        1: 'Đã xác nhận',
        2: 'Đang giao hàng',
        3: 'Đã giao hàng',
        4: 'Đã hủy'
    }
    return statusMap[status] || 'Không xác định'
}

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

const formatDateTime = (timestamp) => {
    if (!timestamp) return 'N/A'

    if (typeof timestamp === 'string' && timestamp.includes('-')) {
        return new Date(timestamp).toLocaleString('vi-VN')
    }

    const date = new Date(parseInt(timestamp) * 1000)
    return date.toLocaleString('vi-VN')
}

const getImageUrl = (image) => {
    if (!image) return '/placeholder-product.jpg'
    if (image.startsWith('http')) return image
    // Thử các đường dẫn khác nhau
    if (image.startsWith('/uploads/')) return `${API_BASE_URL}${image}`
    if (image.startsWith('uploads/')) return `${API_BASE_URL}/${image}`
    return `http://localhost:8080/image/${image}`
}


const handleImageError = (event) => {
    console.log('Image error:', event.target.src)
    event.target.src = '/placeholder-product.jpg'
    // Hoặc sử dụng placeholder online
    // event.target.src = 'https://via.placeholder.com/80x80?text=No+Image'
}

const getImageUrl2 = (image) => {
    if (!image) return '/placeholder-product.jpg'
    
    // Nếu ảnh đã có full URL
    if (image.startsWith('http')) return image
    
    // Xử lý các trường hợp đường dẫn khác nhau
    const baseUrl = API_BASE_URL.replace('/api', '') // Remove /api nếu có
    
    // Kiểm tra xem image có chứa 'uploads' không
    if (image.includes('uploads')) {
        return `${baseUrl}/${image}`
    }
    
    // Mặc định
    return `${baseUrl}/products/uploads/${image}`
}

// Actions
const printOrder = () => {
    window.print()
    toast.success('Đang chuẩn bị in đơn hàng...')
}

const editOrder = () => {
    router.push(`/orders/edit/${order.value.id}`)
}
</script>

<style scoped>
.order-detail-page {
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

.header h1 {
    margin: 0;
    color: #333;
    font-size: 24px;
}

.btn-back {
    background: #6c757d;
    color: white;
    border: none;
    padding: 10px 16px;
    border-radius: 6px;
    cursor: pointer;
    font-weight: 500;
}

.header-actions {
    display: flex;
    gap: 10px;
}

.btn-print,
.btn-edit {
    padding: 10px 16px;
    border: none;
    border-radius: 6px;
    cursor: pointer;
    font-weight: 500;
}

.btn-print {
    background: #17a2b8;
    color: white;
}

.btn-edit {
    background: #ffc107;
    color: #212529;
}

/* Loading */
.loading {
    text-align: center;
    padding: 60px 20px;
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

/* Status Summary */
.status-summary {
    display: flex;
    justify-content: space-between;
    align-items: center;
    background: white;
    padding: 20px;
    border-radius: 8px;
    margin-bottom: 20px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.status-info {
    display: flex;
    gap: 15px;
    align-items: center;
}

.status-badge {
    padding: 8px 16px;
    border-radius: 20px;
    font-weight: 600;
    font-size: 14px;
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

.payment-status {
    padding: 6px 12px;
    border-radius: 15px;
    font-size: 12px;
    font-weight: 600;
}

.payment-0 {
    background: #ffebee;
    color: #d32f2f;
}

.payment-1 {
    background: #e8f5e8;
    color: #388e3c;
}

.order-summary {
    display: flex;
    gap: 30px;
}

.summary-item {
    display: flex;
    flex-direction: column;
    align-items: flex-end;
}

.summary-item .label {
    font-size: 12px;
    color: #666;
    margin-bottom: 4px;
}

.summary-item .value {
    font-weight: 600;
    color: #333;
}

.total-amount {
    font-size: 18px;
    color: #d32f2f;
}

/* Detail Grid */
.detail-grid {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 20px;
    margin-bottom: 20px;
}

.detail-section {
    background: white;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    overflow: hidden;
}

.items-section {
    grid-column: 1 / -1;
}

.section-header {
    background: #f8f9fa;
    padding: 15px 20px;
    border-bottom: 1px solid #e9ecef;
}

.section-header h3 {
    margin: 0;
    color: #333;
    font-size: 16px;
}

.section-content {
    padding: 20px;
}

/* Order Items - UPDATED STYLES */
.order-items {
    margin-bottom: 20px;
}

.order-item {
    border: 1px solid #e9ecef;
    border-radius: 8px;
    margin-bottom: 15px;
    background: #fafbfc;
    overflow: hidden;
}

.item-header {
    background: #e9ecef;
    padding: 8px 15px;
    display: flex;
    justify-content: space-between;
    font-size: 12px;
    color: #6c757d;
    border-bottom: 1px solid #dee2e6;
}

.item-main {
    display: flex;
    padding: 15px;
    gap: 15px;
}

.item-image {
    position: relative;
    flex-shrink: 0;
}

.item-image img {
    width: 80px;
    height: 80px;
    object-fit: cover;
    border-radius: 6px;
    border: 1px solid #dee2e6;
}

.sale-badge {
    position: absolute;
    top: -5px;
    right: -5px;
    background: #dc3545;
    color: white;
    padding: 2px 6px;
    border-radius: 10px;
    font-size: 10px;
    font-weight: bold;
}

.item-details {
    flex: 1;
}

.item-name {
    font-weight: 600;
    font-size: 16px;
    color: #333;
    margin-bottom: 8px;
}

.item-codes {
    display: flex;
    gap: 15px;
    margin-bottom: 10px;
    font-size: 12px;
    color: #6c757d;
}

.item-attributes {
    display: flex;
    gap: 10px;
    margin-bottom: 15px;
}

.attribute {
    background: #e9ecef;
    padding: 4px 8px;
    border-radius: 4px;
    font-size: 11px;
    color: #495057;
}

/* Price Calculation */
.price-calculation {
    background: white;
    padding: 12px;
    border-radius: 6px;
    border: 1px solid #e9ecef;
}

.calc-row {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 4px 0;
    font-size: 13px;
}

.calc-row.discount {
    color: #28a745;
}

.calc-row.saved {
    color: #fd7e14;
}

.calc-row.total {
    border-top: 1px solid #e9ecef;
    margin-top: 4px;
    padding-top: 8px;
    font-size: 14px;
}

.original-price {
    color: #6c757d;
}

.sale-price {
    color: #dc3545;
    font-weight: 600;
}

.saved-amount {
    color: #28a745;
    font-weight: 600;
}

.item-total-price {
    color: #d32f2f;
    font-size: 15px;
}

/* Item Meta */
.item-meta {
    background: #f8f9fa;
    padding: 10px 15px;
    border-top: 1px solid #e9ecef;
}

.meta-info {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
    gap: 8px;
}

.meta-item {
    display: flex;
    justify-content: space-between;
    font-size: 11px;
}

.meta-label {
    color: #6c757d;
}

.meta-value {
    font-weight: 500;
}

.visible-1 {
    color: #28a745;
}

.visible-0 {
    color: #dc3545;
}

/* Order Summary Detailed */
.order-summary-detailed {
    background: white;
    border: 1px solid #e9ecef;
    border-radius: 8px;
    padding: 20px;
}

.order-summary-detailed h4 {
    margin: 0 0 15px 0;
    color: #333;
    font-size: 16px;
    text-align: center;
}

.summary-section {
    margin-bottom: 20px;
    padding-bottom: 15px;
    border-bottom: 1px solid #e9ecef;
}

.summary-section:last-child {
    border-bottom: none;
    margin-bottom: 0;
}

.summary-section.final {
    background: #f8f9fa;
    margin: 0 -20px -20px -20px;
    padding: 15px 20px;
    border-radius: 0 0 8px 8px;
}

.summary-title {
    font-weight: 600;
    color: #495057;
    margin-bottom: 10px;
    font-size: 14px;
}

.summary-calc,
.final-calc {
    display: flex;
    flex-direction: column;
    gap: 6px;
}

.calc-line {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 4px 0;
    font-size: 13px;
}

.calc-line.saved {
    color: #28a745;
}

.calc-line.discount {
    color: #dc3545;
}

.calc-line.final-total {
    border-top: 2px solid #dee2e6;
    margin-top: 8px;
    padding-top: 8px;
    font-size: 16px;
    color: #d32f2f;
}

.saved-total {
    color: #28a745;
    font-weight: 600;
}

.calculation-breakdown {
    margin-top: 8px;
    padding-top: 8px;
    border-top: 1px dashed #dee2e6;
    text-align: center;
    color: #6c757d;
    font-size: 11px;
}

/* Info Items */
.info-item {
    display: flex;
    margin-bottom: 12px;
}

.info-item:last-child {
    margin-bottom: 0;
}

.info-item label {
    font-weight: 600;
    color: #666;
    min-width: 140px;
    font-size: 14px;
}

.info-item span {
    color: #333;
    flex: 1;
}

.info-item .address {
    line-height: 1.4;
}

.info-item .content {
    line-height: 1.4;
    background: #f8f9fa;
    padding: 8px 12px;
    border-radius: 4px;
    border-left: 3px solid #d32f2f;
}

/* Action Buttons */
.action-buttons {
    display: flex;
    gap: 10px;
    justify-content: center;
    background: white;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.action-buttons button {
    padding: 12px 20px;
    border: none;
    border-radius: 6px;
    cursor: pointer;
    font-weight: 500;
    transition: all 0.3s;
}

.btn-confirm {
    background: #17a2b8;
    color: white;
}

.btn-shipping {
    background: #fd7e14;
    color: white;
}

.btn-complete {
    background: #28a745;
    color: white;
}

.btn-cancel {
    background: #dc3545;
    color: white;
}

.btn-pay {
    background: #ffc107;
    color: #212529;
}

.action-buttons button:hover {
    transform: translateY(-1px);
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

/* Error State */
.error-state {
    text-align: center;
    padding: 60px 20px;
    background: white;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.error-icon {
    font-size: 48px;
    margin-bottom: 15px;
}

.error-state h3 {
    margin: 0 0 10px 0;
    color: #333;
}

.error-state p {
    margin: 0 0 20px 0;
    color: #666;
}

/* Responsive */
@media (max-width: 768px) {
    .order-detail-page {
        padding: 10px;
    }

    .header {
        flex-direction: column;
        gap: 15px;
        align-items: flex-start;
    }

    .header-actions {
        width: 100%;
        justify-content: space-between;
    }

    .status-summary {
        flex-direction: column;
        gap: 15px;
        align-items: flex-start;
    }

    .order-summary {
        width: 100%;
        justify-content: space-between;
    }

    .detail-grid {
        grid-template-columns: 1fr;
    }

    .item-main {
        flex-direction: column;
    }

    .item-image {
        align-self: flex-start;
    }

    .item-codes {
        flex-direction: column;
        gap: 5px;
    }

    .item-attributes {
        flex-wrap: wrap;
    }

    .meta-info {
        grid-template-columns: 1fr;
    }

    .action-buttons {
        flex-direction: column;
    }

    .info-item {
        flex-direction: column;
        gap: 4px;
    }

    .info-item label {
        min-width: auto;
    }
}

@media print {

    .header-actions,
    .action-buttons {
        display: none;
    }

    .order-detail-page {
        background: white;
        padding: 0;
    }

    .detail-section {
        box-shadow: none;
        border: 1px solid #ddd;
        margin-bottom: 10px;
    }
}
</style>