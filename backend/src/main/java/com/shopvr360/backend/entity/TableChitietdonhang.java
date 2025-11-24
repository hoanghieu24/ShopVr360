package com.shopvr360.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Table(name = "table_chitietdonhang")
public class TableChitietdonhang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @Size(max = 225)
    @NotNull
    @Column(name = "ten", nullable = false, length = 225)
    private String ten;

    @NotNull
    @Column(name = "gia", nullable = false)
    private Integer gia;

    @NotNull
    @Column(name = "soluong", nullable = false)
    private Integer soluong;

    @Size(max = 30)
    @NotNull
    @Column(name = "size", nullable = false, length = 30)
    private String size;

    @NotNull
    @Column(name = "tonggia", nullable = false)
    private Integer tonggia;

    @Size(max = 100)
    @NotNull
    @Column(name = "photo", nullable = false, length = 100)
    private String photo;

    @ColumnDefault("'1'")
    @Column(name = "stt", columnDefinition = "int UNSIGNED not null")
    private Long stt;

    @NotNull
    @ColumnDefault("1")
    @Column(name = "hienthi", nullable = false)
    private Boolean hienthi = false;

    @ColumnDefault("'0'")
    @Column(name = "ngaytao", columnDefinition = "int UNSIGNED not null")
    private int ngaytao;

    @ColumnDefault("'0'")
    @Column(name = "ngaysua", columnDefinition = "int UNSIGNED not null")
    private int ngaysua;

    @Size(max = 50)
    @NotNull
    @Column(name = "madonhang", nullable = false, length = 50)
    private String madonhang;

    @Size(max = 200)
    @NotNull
    @Column(name = "masp", nullable = false, length = 200)
    private String masp;

    @NotNull
    @Column(name = "id_donhang", nullable = false)
    private Integer idDonhang;

    @NotNull
    @Column(name = "giakm", nullable = false)
    private Integer giakm;

    @NotNull
    @Column(name = "id_sanpham", nullable = false)
    private Long idSanpham;

    @Size(max = 50)
    @NotNull
    @Column(name = "mausac", nullable = false, length = 50)
    private String mausac;

    @NotNull
    @Column(name = "view", nullable = false)
    private Integer view;

    @Column(name = "partner_id")
    private Integer partnerId;

    @Column(name = "shipping_status")
    private Integer shippingStatus;

    @Column(name = "kg")
    private Integer kg;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Integer getGia() {
        return gia;
    }

    public void setGia(Integer gia) {
        this.gia = gia;
    }

    public Integer getSoluong() {
        return soluong;
    }

    public void setSoluong(Integer soluong) {
        this.soluong = soluong;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getTonggia() {
        return tonggia;
    }

    public void setTonggia(Integer tonggia) {
        this.tonggia = tonggia;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Long getStt() {
        return stt;
    }

    public void setStt(Long stt) {
        this.stt = stt;
    }

    public Boolean getHienthi() {
        return hienthi;
    }

    public void setHienthi(Boolean hienthi) {
        this.hienthi = hienthi;
    }

    public int getNgaytao() {
        return ngaytao;
    }

    public void setNgaytao(int ngaytao) {
        this.ngaytao = ngaytao;
    }

    public int getNgaysua() {
        return ngaysua;
    }

    public void setNgaysua(int ngaysua) {
        this.ngaysua = ngaysua;
    }

    public String getMadonhang() {
        return madonhang;
    }

    public void setMadonhang(String madonhang) {
        this.madonhang = madonhang;
    }

    public String getMasp() {
        return masp;
    }

    public void setMasp(String masp) {
        this.masp = masp;
    }

    public Integer getIdDonhang() {
        return idDonhang;
    }

    public void setIdDonhang(Integer idDonhang) {
        this.idDonhang = idDonhang;
    }

    public Integer getGiakm() {
        return giakm;
    }

    public void setGiakm(Integer giakm) {
        this.giakm = giakm;
    }

    public Long getIdSanpham() {
        return idSanpham;
    }

    public void setIdSanpham(Long idSanpham) {
        this.idSanpham = idSanpham;
    }

    public String getMausac() {
        return mausac;
    }

    public void setMausac(String mausac) {
        this.mausac = mausac;
    }

    public Integer getView() {
        return view;
    }

    public void setView(Integer view) {
        this.view = view;
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    public Integer getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(Integer shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    public Integer getKg() {
        return kg;
    }

    public void setKg(Integer kg) {
        this.kg = kg;
    }
}