package com.shopvr360.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "table_newsletter")
public class TableNewsletter {
    @Id
    @Column(name = "id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @ColumnDefault("'1'")
    @Column(name = "stt", columnDefinition = "int UNSIGNED not null")
    private Long stt;

    @Size(max = 225)
    @NotNull
    @Column(name = "ten", nullable = false, length = 225)
    private String ten;

    @Size(max = 255)
    @NotNull
    @Column(name = "gioitinh", nullable = false)
    private String gioitinh;

    @Size(max = 50)
    @NotNull
    @Column(name = "sanpham", nullable = false, length = 50)
    private String sanpham;

    @Size(max = 200)
    @NotNull
    @Column(name = "tieude", nullable = false, length = 200)
    private String tieude;

    @NotNull
    @Lob
    @Column(name = "noidung", nullable = false)
    private String noidung;

    @NotNull
    @ColumnDefault("1")
    @Column(name = "hienthi", nullable = false)
    private Boolean hienthi = false;

    @ColumnDefault("'0'")
    @Column(name = "ngaytao", columnDefinition = "int UNSIGNED not null")
    private Long ngaytao;

    @ColumnDefault("'0'")
    @Column(name = "ngaysua", columnDefinition = "int UNSIGNED not null")
    private Long ngaysua;

    @Size(max = 225)
    @NotNull
    @Column(name = "email", nullable = false, length = 225)
    private String email;

    @Size(max = 255)
    @NotNull
    @Column(name = "dienthoai", nullable = false)
    private String dienthoai;

    @Size(max = 255)
    @NotNull
    @Column(name = "diachi", nullable = false)
    private String diachi;

    @NotNull
    @Column(name = "view", nullable = false)
    private Integer view;

    @Size(max = 255)
    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStt() {
        return stt;
    }

    public void setStt(Long stt) {
        this.stt = stt;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getSanpham() {
        return sanpham;
    }

    public void setSanpham(String sanpham) {
        this.sanpham = sanpham;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public Boolean getHienthi() {
        return hienthi;
    }

    public void setHienthi(Boolean hienthi) {
        this.hienthi = hienthi;
    }

    public Long getNgaytao() {
        return ngaytao;
    }

    public void setNgaytao(Long ngaytao) {
        this.ngaytao = ngaytao;
    }

    public Long getNgaysua() {
        return ngaysua;
    }

    public void setNgaysua(Long ngaysua) {
        this.ngaysua = ngaysua;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDienthoai() {
        return dienthoai;
    }

    public void setDienthoai(String dienthoai) {
        this.dienthoai = dienthoai;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public Integer getView() {
        return view;
    }

    public void setView(Integer view) {
        this.view = view;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}