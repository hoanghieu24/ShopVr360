package com.shopvr360.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "table_contact")
public class TableContact {
    @Id
    @Column(name = "id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @ColumnDefault("'1'")
    @Column(name = "stt", columnDefinition = "int UNSIGNED not null")
    private Long stt;

    @Size(max = 255)
    @NotNull
    @Column(name = "ten", nullable = false)
    private String ten;

    @Size(max = 255)
    @NotNull
    @Column(name = "diachi", nullable = false)
    private String diachi;

    @Size(max = 100)
    @NotNull
    @Column(name = "dienthoai", nullable = false, length = 100)
    private String dienthoai;

    @Size(max = 100)
    @NotNull
    @Column(name = "photo", nullable = false, length = 100)
    private String photo;

    @Size(max = 100)
    @NotNull
    @Column(name = "thumb", nullable = false, length = 100)
    private String thumb;

    @Size(max = 100)
    @NotNull
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Size(max = 255)
    @NotNull
    @Column(name = "tieude", nullable = false)
    private String tieude;

    @NotNull
    @Lob
    @Column(name = "noidung", nullable = false)
    private String noidung;

    @NotNull
    @Lob
    @Column(name = "ghichu", nullable = false)
    private String ghichu;

    @NotNull
    @Column(name = "view", nullable = false)
    private Integer view;

    @NotNull
    @ColumnDefault("1")
    @Column(name = "hienthi", nullable = false)
    private Boolean hienthi = false;

    @NotNull
    @Column(name = "ngaytao", nullable = false)
    private Integer ngaytao;

    @Size(max = 255)
    @NotNull
    @Column(name = "tinhthanh", nullable = false)
    private String tinhthanh;

    @Size(max = 255)
    @NotNull
    @Column(name = "quanhuyen", nullable = false)
    private String quanhuyen;

    @Size(max = 255)
    @NotNull
    @Column(name = "linhvuc", nullable = false)
    private String linhvuc;

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

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getDienthoai() {
        return dienthoai;
    }

    public void setDienthoai(String dienthoai) {
        this.dienthoai = dienthoai;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public Integer getView() {
        return view;
    }

    public void setView(Integer view) {
        this.view = view;
    }

    public Boolean getHienthi() {
        return hienthi;
    }

    public void setHienthi(Boolean hienthi) {
        this.hienthi = hienthi;
    }

    public Integer getNgaytao() {
        return ngaytao;
    }

    public void setNgaytao(Integer ngaytao) {
        this.ngaytao = ngaytao;
    }

    public String getTinhthanh() {
        return tinhthanh;
    }

    public void setTinhthanh(String tinhthanh) {
        this.tinhthanh = tinhthanh;
    }

    public String getQuanhuyen() {
        return quanhuyen;
    }

    public void setQuanhuyen(String quanhuyen) {
        this.quanhuyen = quanhuyen;
    }

    public String getLinhvuc() {
        return linhvuc;
    }

    public void setLinhvuc(String linhvuc) {
        this.linhvuc = linhvuc;
    }

}