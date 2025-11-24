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

        for (CartItemRequest itemReq : request.getCartItems()) {
            var product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found id=" + itemReq.getProductId()));

            totalKg += product.getKg() * itemReq.getQuantity();
            tonggia += product.getGiaban() * itemReq.getQuantity();

        }

        var province = provinceRepository.findById(request.getThanhpho())
                .orElseThrow(() -> new RuntimeException("Province not found id=" + request.getThanhpho()));

        float phivanchuyen = totalKg * province.getPhivanchuyen();
        dh.setPhivanchuyen(phivanchuyen);

        dh.setTonggia(tonggia + Math.round(phivanchuyen));

        setDefaultFields(dh, request);

        TableDonhang savedDh = odersRepository.save(dh);

        for (CartItemRequest itemReq : request.getCartItems()) {
            var product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found id=" + itemReq.getProductId()));

            TableChitietdonhang chitiet = new TableChitietdonhang();

            chitiet.setIdDonhang(savedDh.getId());
            chitiet.setIdSanpham(product.getId());
            chitiet.setTen(product.getTenVi());
            chitiet.setSoluong(itemReq.getQuantity());
            chitiet.setGia(product.getGiaban());
            chitiet.setKg(product.getKg());

            chitiet.setSize("default");
            chitiet.setMasp(product.getMasp());
            chitiet.setMadonhang(savedDh.getMadonhang());
            chitiet.setPhoto(product.getPhoto());
            chitiet.setMausac("default");
            chitiet.setStt(1L);

            chitiet.setTonggia(product.getGiaban() * itemReq.getQuantity());
            chitiet.setGiakm(0);
            chitiet.setView(0);

            chitiet.setNgaysua(now);
            chitiet.setNgaytao(now);

            chitietRepository.save(chitiet);
        }
        System.out.println(totalKg);
        System.out.println(tonggia);
        return savedDh;
    }

    public TableDonhang updateDonhang(Integer id, OderRequest request) {

        // Lấy đơn hàng cũ
        TableDonhang dh = odersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found id=" + id));

        // Map các field từ request sang entity (chỉ field trùng)
        modelMapper.map(request, dh);

        // Cập nhật ngày sửa
        int now = (int) (System.currentTimeMillis() / 1000);
        dh.setNgaycapnhat(now);

        // Xử lý lại tổng giá + cân nặng
        float totalKg = 0;
        int tonggia = 0;

        // CartItems mới => xoá cũ, tạo lại
        chitietRepository.deleteAllByIdDonhang(dh.getId());

        for (CartItemRequest itemReq : request.getCartItems()) {
            var product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found id=" + itemReq.getProductId()));

            totalKg += product.getKg() * itemReq.getQuantity();
            tonggia += product.getGiaban() * itemReq.getQuantity();

            TableChitietdonhang chitiet = new TableChitietdonhang();
            chitiet.setIdDonhang(dh.getId());
            chitiet.setIdSanpham(product.getId());
            chitiet.setTen(product.getTenVi());
            chitiet.setSoluong(itemReq.getQuantity());
            chitiet.setGia(product.getGiaban());
            chitiet.setKg(product.getKg());
            chitiet.setSize("default");
            chitiet.setMasp(product.getMasp());
            chitiet.setMadonhang(dh.getMadonhang());
            chitiet.setPhoto(product.getPhoto());
            chitiet.setMausac("default");
            chitiet.setStt(1L);
            chitiet.setTonggia(product.getGiaban() * itemReq.getQuantity());
            chitiet.setGiakm(0);
            chitiet.setView(0);
            chitiet.setNgaysua(now);
            chitiet.setNgaytao(now);
            chitietRepository.save(chitiet);
        }

        // Tính lại phí vận chuyển
        var province = provinceRepository.findById(request.getThanhpho())
                .orElseThrow(() -> new RuntimeException("Province not found id=" + request.getThanhpho()));

        float phivanchuyen = totalKg * province.getPhivanchuyen();
        dh.setPhivanchuyen(phivanchuyen);

        dh.setTonggia(tonggia + Math.round(phivanchuyen));

        // Set default nếu request không gửi lên đủ
        setDefaultFields(dh, request);

        return odersRepository.save(dh);
    }


    public void deleteProduct(Long id) {
        TableDonhang dh = odersRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng có id = " + id));

        dh.setIsDelete(1);
        dh.setNgaycapnhat((int) (System.currentTimeMillis() / 1000));
        odersRepository.save(dh);
    }


    public void deleteMultiple(List<Integer> ids) {
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


}
