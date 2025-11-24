<template>
    <footer class="footer-container">
        <div class="footer-content">
            <!-- CHĂM SÓC KHÁCH HÀNG -->
            <div class="footer-section">
                <h3>CHĂM SÓC KHÁCH HÀNG</h3>
                <ul class="footer-links">
                    <li><a href="#">Trung tâm trợ giúp</a></li>
                    <li><a href="#">Hướng dẫn đặt hàng</a></li>
                    <li><a href="#">Cước phí & Biểu phí</a></li>
                    <li><a href="#">Chính sách thanh toán</a></li>
                    <li><a href="#">Chính sách vận chuyển</a></li>
                    <li><a href="#">Chính sách đảm bảo</a></li>
                    <li><a href="#">Điều khoản dịch vụ</a></li>
                </ul>
            </div>
            
            <!-- VỀ SABOMALL -->
            <div class="footer-section">
                <h3>VỀ SHOPVR360</h3>
                <ul class="footer-links">
                    <li><a href="#">Giới thiệu về ShopVR360</a></li>
                    <li><a href="#">Chính sách bảo mật</a></li>
                    <li><a href="#">Quy chế hoạt động</a></li>
                    <li><a href="#">Chương trình đối tác</a></li>
                    <li><a href="#">Tuyển dụng</a></li>
                    <li><a href="#">Liên hệ hợp tác</a></li>
                </ul>
            </div>
            
            <!-- KẾT NỐI VỚI CHÚNG TÔI -->
            <div class="footer-section">
                <h3>KẾT NỐI VỚI CHÚNG TÔI</h3>
                <div class="social-links">
                    <a href="#" class="social-icon">
                        <i class="icon-facebook"></i>
                    </a>
                    <a href="#" class="social-icon">
                        <i class="icon-instagram"></i>
                    </a>
                    <a href="#" class="social-icon">
                        <i class="icon-twitter"></i>
                    </a>
                    <a href="#" class="social-icon">
                        <i class="icon-youtube"></i>
                    </a>
                </div>
                
                <h3 class="section-subtitle">TẢI ỨNG DỤNG ĐIỆN THOẠI</h3>
                <div class="app-download">
                    <a href="#" class="app-btn">
                        <i class="icon-apple"></i>
                        <span>App Store</span>
                    </a>
                    <a href="#" class="app-btn">
                        <i class="icon-google-play"></i>
                        <span>Google Play</span>
                    </a>
                </div>
            </div>
            
            <!-- CÔNG CỤ MUA HÀNG & THANH TOÁN -->
            <div class="footer-section">
                <h3>CÔNG CỤ MUA HÀNG</h3>
                <div class="payment-methods">
                    <div class="payment-icon">EXTENSION</div>
                    <div class="payment-icon">HOA HỒNG</div>
                </div>
                
                <h3 class="section-subtitle">ĐỐI TÁC THANH TOÁN</h3>
                <div class="payment-methods">
                    <div class="payment-icon">VISA</div>
                    <div class="payment-icon">MC</div>
                    <div class="payment-icon">JCB</div>
                    <div class="payment-icon">ATM</div>
                </div>
                
                <h3 class="section-subtitle">ĐỐI TÁC VẬN CHUYỂN</h3>
                <div class="shipping-partners">
                    <div class="shipping-icon">GHTK</div>
                    <div class="shipping-icon">GHN</div>
                    <div class="shipping-icon">VT</div>
                    <div class="shipping-icon">J&T</div>
                </div>
            </div>
        </div>
        
        <div class="footer-bottom">
            <div class="company-info">
                <p><strong>BẢN QUYỀN THUỘC CÔNG TY CÔNG NGHỆ MỚI KTS GROUP</strong></p>
                <p>Giấy chứng nhận Đăng ký Kinh doanh số: 2500690154 Ngày cấp: 14/10/2022</p>
                <p>Địa chỉ: NV09 – Ô đất O16-HH1, Khu đô thị mới Tây Hồ Tây, Phường Nghĩa Đô, Thành phố Hà Nội, Việt Nam</p>
            </div>
            
            <div class="certification">
                <div class="certificate">DMCA</div>
                <div class="certificate">Bộ Công Thương</div>
            </div>
        </div>
    </footer>
</template>

<script>
export default {
    name: 'AppFooter'
}
</script>

<style scoped>
.footer-container {
    background-color: #1a1a1a;
    color: #fff;
    padding: 40px 20px 20px;
    margin-top: auto;
}

.footer-content {
    max-width: 1200px;
    margin: 0 auto;
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 30px;
}

.footer-section h3 {
    font-size: 18px;
    margin-bottom: 20px;
    color: #ff6b00;
    position: relative;
    padding-bottom: 10px;
}

.footer-section h3::after {
    content: '';
    position: absolute;
    bottom: 0;
    left: 0;
    width: 40px;
    height: 2px;
    background-color: #ff6b00;
}

.section-subtitle {
    font-size: 18px;
    margin: 25px 0 15px 0;
    color: #ff6b00;
    position: relative;
    padding-bottom: 10px;
}

.section-subtitle::after {
    content: '';
    position: absolute;
    bottom: 0;
    left: 0;
    width: 40px;
    height: 2px;
    background-color: #ff6b00;
}

.footer-links {
    list-style: none;
}

.footer-links li {
    margin-bottom: 12px;
}

.footer-links a {
    color: #ccc;
    text-decoration: none;
    transition: color 0.3s;
    display: block;
}

.footer-links a:hover {
    color: #ff6b00;
    padding-left: 5px;
}

.social-links {
    display: flex;
    gap: 15px;
    margin-top: 15px;
}

.social-icon {
    width: 36px;
    height: 36px;
    border-radius: 50%;
    background-color: #333;
    display: flex;
    align-items: center;
    justify-content: center;
    color: #fff;
    text-decoration: none;
    transition: all 0.3s;
}

.social-icon:hover {
    background-color: #ff6b00;
    transform: translateY(-3px);
}

.app-download {
    display: flex;
    flex-direction: column;
    gap: 10px;
    margin-top: 15px;
}

.app-btn {
    display: flex;
    align-items: center;
    gap: 8px;
    padding: 10px 15px;
    background-color: #333;
    border-radius: 6px;
    color: #fff;
    text-decoration: none;
    transition: background-color 0.3s;
    font-size: 14px;
}

.app-btn:hover {
    background-color: #ff6b00;
}

.payment-methods {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
    margin-top: 15px;
}

.payment-icon {
    width: 70px;
    height: 35px;
    background-color: #fff;
    border-radius: 4px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-weight: bold;
    font-size: 11px;
    color: #333;
}

.shipping-partners {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
    margin-top: 15px;
}

.shipping-icon {
    width: 60px;
    height: 30px;
    background-color: #fff;
    border-radius: 4px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-weight: bold;
    font-size: 10px;
    color: #333;
}

.footer-bottom {
    max-width: 1200px;
    margin: 40px auto 0;
    padding-top: 20px;
    border-top: 1px solid #333;
    display: flex;
    justify-content: space-between;
    align-items: center;
    flex-wrap: wrap;
    gap: 20px;
}

.company-info {
    color: #999;
    font-size: 14px;
    line-height: 1.6;
}

.company-info strong {
    color: #fff;
}

.certification {
    display: flex;
    align-items: center;
    gap: 15px;
}

.certificate {
    padding: 8px 15px;
    background-color: #333;
    border-radius: 4px;
    font-size: 14px;
    color: #ccc;
}

/* Icon styles */
.icon-facebook:before { content: "f"; font-weight: bold; }
.icon-instagram:before { content: "ig"; font-size: 12px; }
.icon-twitter:before { content: "X"; font-weight: bold; }
.icon-youtube:before { content: "YT"; font-size: 12px; }
.icon-apple:before { content: ""; }
.icon-google-play:before { content: "▶"; font-size: 12px; }

/* Responsive styles */
@media (max-width: 992px) {
    .footer-content {
        grid-template-columns: repeat(2, 1fr);
    }
}

@media (max-width: 576px) {
    .footer-content {
        grid-template-columns: 1fr;
    }
    
    .footer-bottom {
        flex-direction: column;
        text-align: center;
    }
    
    .app-download {
        flex-direction: row;
        justify-content: center;
    }
}
</style>