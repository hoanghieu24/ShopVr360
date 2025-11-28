package com.shopvr360.backend.dto;

import java.util.ArrayList;
import java.util.List;

public class OderRequest {
    private Long  id;
    private String hoten;
    private String dienthoai;
    private Long thanhpho;
    private Integer quan;
    private Integer phuong;
    private String diachi;
    private String email;
    private String makm;      // mã khuyến mãi
    private String ghichu;
    private String httt;       // hình thức thanh toán
    private Integer htgh;
    private Integer idUser;
//    private List<CartItemRequest> cartItems = new ArrayList<>();// phương thức vận chuyển

    public Long  getId() {
        return id;
    }

    public void setId(Long  id) {
        this.id = id;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }



    public String getDiachi() {
        return diachi;
    }


//    public List<CartItemRequest> getCartItems() {
//        return cartItems;
//    }
//
//    public void setCartItems(List<CartItemRequest> cartItems) {
//        this.cartItems = cartItems;
//    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getDienthoai() {
        return dienthoai;
    }

    public void setDienthoai(String dienthoai) {
        this.dienthoai = dienthoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public Integer getHtgh() {
        return htgh;
    }

    public void setHtgh(Integer htgh) {
        this.htgh = htgh;
    }

    public String getHttt() {
        return httt;
    }

    public void setHttt(String httt) {
        this.httt = httt;
    }

    public String getMakm() {
        return makm;
    }

    public void setMakm(String makm) {
        this.makm = makm;
    }

    public Integer getPhuong() {
        return phuong;
    }

    public void setPhuong(Integer phuong) {
        this.phuong = phuong;
    }

    public Integer getQuan() {
        return quan;
    }

    public void setQuan(Integer quan) {
        this.quan = quan;
    }

    public Long getThanhpho() {
        return thanhpho;
    }

    public void setThanhpho(Long thanhpho) {
        this.thanhpho = thanhpho;
    }
}
