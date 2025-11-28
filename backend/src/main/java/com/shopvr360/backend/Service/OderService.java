package com.shopvr360.backend.Service;


import com.shopvr360.backend.dto.CartItemRequest;
import com.shopvr360.backend.dto.NewsRequest;
import com.shopvr360.backend.dto.OderRequest;
import com.shopvr360.backend.entity.*;
import com.shopvr360.backend.repository.DonhangChitietRepository;
import com.shopvr360.backend.repository.OdersRepository;
import com.shopvr360.backend.repository.ProductRepository;
import com.shopvr360.backend.repository.ProvinceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OderService {

    @Autowired
    private OdersRepository odersRepository;

    @Autowired
    private DonhangChitietRepository chitietRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<TableDonhang> getAllOder() {
        return odersRepository.findAll();
    }

    public TableDonhang createDonhang(OderRequest request) {

        TableDonhang dh = new TableDonhang();

        modelMapper.map(request, dh);


        dh.setNgaytao(String.valueOf(LocalDateTime.now()));

        int now = (int) (System.currentTimeMillis() / 1000);
        dh.setNgaycapnhat(now);


        dh.setMadonhang("DH" + System.currentTimeMillis());

        float totalKg = 0;
        int tonggia = 0;

//        for (CartItemRequest itemReq : request.getCartItems()) {
//            var product = productRepository.findById(itemReq.getProductId())
//                    .orElseThrow(() -> new RuntimeException("Product not found id=" + itemReq.getProductId()));
//
//            totalKg += product.getKg() * itemReq.getQuantity();
//            tonggia += product.getGiaban() * itemReq.getQuantity();
//
//        }

        var province = provinceRepository.findById(request.getThanhpho())
                .orElseThrow(() -> new RuntimeException("Province not found id=" + request.getThanhpho()));

        float phivanchuyen = totalKg * province.getPhivanchuyen();
        dh.setPhivanchuyen(phivanchuyen);

        dh.setTonggia(tonggia + Math.round(phivanchuyen));

        setDefaultFields(dh, request);

        TableDonhang savedDh = odersRepository.save(dh);

//        for (CartItemRequest itemReq : request.getCartItems()) {
//            var product = productRepository.findById(itemReq.getProductId())
//                    .orElseThrow(() -> new RuntimeException("Product not found id=" + itemReq.getProductId()));
//
//            TableChitietdonhang chitiet = new TableChitietdonhang();
//
//            chitiet.setIdDonhang(savedDh.getId());
//            chitiet.setIdSanpham(product.getId());
//            chitiet.setTen(product.getTenVi());
//            chitiet.setSoluong(itemReq.getQuantity());
//            chitiet.setGia(product.getGiaban());
//            chitiet.setKg(product.getKg());
//
//            chitiet.setSize("default");
//            chitiet.setMasp(product.getMasp());
//            chitiet.setMadonhang(savedDh.getMadonhang());
//            chitiet.setPhoto(product.getPhoto());
//            chitiet.setMausac("default");
//            chitiet.setStt(1L);
//
//            chitiet.setTonggia(product.getGiaban() * itemReq.getQuantity());
//            chitiet.setGiakm(0);
//            chitiet.setView(0);
//
//            chitiet.setNgaysua(now);
//            chitiet.setNgaytao(now);
//
//            chitietRepository.save(chitiet);
//        }
        System.out.println(totalKg);
        System.out.println(tonggia);
        return savedDh;
    }

    public TableDonhang updateDonhang(Long id, OderRequest request) {

        // Lấy đơn hàng cũ
        TableDonhang dh = odersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found id=" + id));

        // Map các field từ request sang entity (ngoại trừ cartItems)
        modelMapper.map(request, dh);

        int now = (int) (System.currentTimeMillis() / 1000);
        dh.setNgaycapnhat(now);

        // Xoá chi tiết đơn hàng cũ
        chitietRepository.deleteAllByIdDonhang(dh.getId());

        float totalKg = 0;
        int tonggia = 0;

        // Tạo chi tiết đơn hàng mới từ request
//        if (request.getCartItems() != null && !request.getCartItems().isEmpty()) {
//            for (CartItemRequest itemReq : request.getCartItems()) {
//                TableProduct product = productRepository.findById(itemReq.getProductId())
//                        .orElseThrow(() -> new RuntimeException("Product not found id=" + itemReq.getProductId()));
//
//                // Tính tổng cân nặng và tổng giá
//                totalKg += product.getKg() * itemReq.getQuantity();
//                tonggia += product.getGiaban() * itemReq.getQuantity();
//
//                // Tạo chi tiết đơn hàng
//                TableChitietdonhang chitiet = new TableChitietdonhang();
//                chitiet.setIdDonhang(dh.getId());
//                chitiet.setIdSanpham(product.getId());
//                chitiet.setTen(product.getTenVi());
//                chitiet.setSoluong(itemReq.getQuantity());
//                chitiet.setGia(product.getGiaban());
//                chitiet.setKg(product.getKg());
//                chitiet.setSize("default");
//                chitiet.setMasp(product.getMasp());
//                chitiet.setMadonhang(dh.getMadonhang());
//                chitiet.setPhoto(product.getPhoto());
//                chitiet.setMausac("default");
//                chitiet.setStt(1L);
//                chitiet.setTonggia(product.getGiaban() * itemReq.getQuantity());
//                chitiet.setGiakm(0);
//                chitiet.setView(0);
//                chitiet.setNgaytao(now);
//                chitiet.setNgaysua(now);
//
//                chitietRepository.save(chitiet);
//            }
//        }

        // Tính phí vận chuyển dựa trên thành phố
        var province = provinceRepository.findById(request.getThanhpho())
                .orElseThrow(() -> new RuntimeException("Province not found id=" + request.getThanhpho()));
        float phivanchuyen = totalKg * province.getPhivanchuyen();
        dh.setPhivanchuyen(phivanchuyen);

        // Cập nhật tổng giá đơn hàng
        dh.setTonggia(tonggia + Math.round(phivanchuyen));

        // Gán các field mặc định nếu thiếu
        setDefaultFields(dh, request);

        return odersRepository.save(dh);
    }



    public void deleteProduct(Long id) {
        TableDonhang dh = odersRepository.findById((long) Math.toIntExact(id))
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng có id = " + id));

        dh.setIsDelete(1);
        dh.setNgaycapnhat((int) (System.currentTimeMillis() / 1000));
        odersRepository.save(dh);
    }


    public void deleteMultiple(List<Long > ids) {
        List<TableDonhang> orders = odersRepository.findAllById(ids);

        if (orders.isEmpty()) {
            throw new RuntimeException("Không tìm thấy đơn hàng nào trong danh sách.");
        }

        int now = (int) (System.currentTimeMillis() / 1000);

        for (TableDonhang dh : orders) {
            dh.setIsDelete(1);
            dh.setNgaycapnhat(now);
        }

        odersRepository.saveAll(orders);
    }



    public List<TableDonhang> getAllDonHangByIdUser(Integer idUser) {
        return odersRepository.findAllByIdUser(idUser);
    }


    private void setDefaultFields(TableDonhang dh, OderRequest req) {
        dh.setNgayin("");
        dh.setPhigiam(0);
        dh.setNgaydi("");
        dh.setThanhtoan(0);
        dh.setNoidung("");
        dh.setExport((byte) 0);
        dh.setIp("127.0.0.1");
        dh.setGioin("");
        dh.setPhithem(0);
        dh.setThuve(0);
        dh.setTinhtrang(1);
        dh.setIsDelete(0);
        dh.setNguongoc("website");

        dh.setView(0);

        int now = (int) (System.currentTimeMillis() / 1000);
        dh.setNgaycapnhat(now);
    }


    public Map<String, Object> getOrderDetail(Long  id) {

        Optional<TableDonhang> orderOpt = odersRepository.findById(id);
        if (orderOpt.isEmpty()) {
            throw new RuntimeException("Đơn hàng không tồn tại");
        }
        TableDonhang order = orderOpt.get();


        String madonhang = order.getMadonhang();


        List<TableChitietdonhang> items = chitietRepository.findByMadonhang(madonhang);


        double totalWeight = items.stream()
                .mapToDouble(item -> item.getKg() * item.getSoluong()) // kg
                .sum();

        int shippingFee;
        if (totalWeight <= 3) shippingFee = 18000;
        else if (totalWeight <= 5) shippingFee = 25000;
        else if (totalWeight <= 6) shippingFee = 31000;
        else if (totalWeight <= 7) shippingFee = 37000;
        else if (totalWeight <= 8) shippingFee = 43000;
        else if (totalWeight <= 9) shippingFee = 49000;
        else shippingFee = 49000 + (int)((totalWeight - 9) * 10000);


        int totalServiceFee = shippingFee + (order.getPhithem() != null ? order.getPhithem() : 0);


        int totalProductPrice = items.stream()
                .mapToInt(item -> item.getGia() * item.getSoluong())
                .sum();
        int totalPayment = totalProductPrice + totalServiceFee - (order.getPhigiam() != null ? order.getPhigiam() : 0);

        // 8. Trả về map cho FE
        return Map.of(
                "order", order,
                "items", items,
                "totalWeight", totalWeight,
                "shippingFee", shippingFee,
                "totalServiceFee", totalServiceFee,
                "totalPayment", totalPayment
        );
    }


    public void payOrder(Long  id) {
        TableDonhang n = odersRepository.findById(id).orElseThrow();
        n.setThanhtoan(n.getThanhtoan() == 1 ? 0 : 1);
        odersRepository.save(n);
    }

    public void statusOrder(Long  id, Integer status) {
        TableDonhang n = odersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn!"));

        // Kiểm tra status hợp lệ
        if (status < 1 || status > 5) {
            throw new IllegalArgumentException("Trạng thái không hợp lệ!");
        }

        n.setTinhtrang(status);
        odersRepository.save(n);
    }

}
