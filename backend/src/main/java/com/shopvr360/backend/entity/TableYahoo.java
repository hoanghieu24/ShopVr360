package com.shopvr360.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "table_yahoo")
public class TableYahoo {
    @Id
    @Column(name = "id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "ten_vi", nullable = false)
    private String tenVi;

    @Size(max = 255)
    @NotNull
    @Column(name = "ten_en", nullable = false)
    private String tenEn;

    @Size(max = 255)
    @NotNull
    @Column(name = "bophan_vi", nullable = false)
    private String bophanVi;

    @Size(max = 255)
    @NotNull
    @Column(name = "bophan_en", nullable = false)
    private String bophanEn;

    @Size(max = 30)
    @NotNull
    @Column(name = "yahoo", nullable = false, length = 30)
    private String yahoo;

    @Size(max = 255)
    @NotNull
    @Column(name = "skype", nullable = false)
    private String skype;

    @Size(max = 30)
    @NotNull
    @Column(name = "dienthoai", nullable = false, length = 30)
    private String dienthoai;

    @Size(max = 255)
    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Size(max = 255)
    @NotNull
    @Column(name = "zalo", nullable = false)
    private String zalo;

    @Size(max = 255)
    @NotNull
    @Column(name = "viber", nullable = false)
    private String viber;

    @NotNull
    @Column(name = "stt", nullable = false)
    private Integer stt;

    @NotNull
    @Column(name = "hienthi", nullable = false)
    private Integer hienthi;

    @NotNull
    @Column(name = "ngaytao", nullable = false)
    private Integer ngaytao;

    @NotNull
    @Column(name = "ngaysua", nullable = false)
    private Integer ngaysua;

    @Size(max = 255)
    @NotNull
    @Column(name = "photo", nullable = false)
    private String photo;

    @Size(max = 255)
    @NotNull
    @Column(name = "photo_zalo", nullable = false)
    private String photoZalo;

    @Size(max = 255)
    @NotNull
    @Column(name = "thumb_zalo", nullable = false)
    private String thumbZalo;

    @Size(max = 255)
    @NotNull
    @Column(name = "thumb", nullable = false)
    private String thumb;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenVi() {
        return tenVi;
    }

    public void setTenVi(String tenVi) {
        this.tenVi = tenVi;
    }

    public String getTenEn() {
        return tenEn;
    }

    public void setTenEn(String tenEn) {
        this.tenEn = tenEn;
    }

    public String getBophanVi() {
        return bophanVi;
    }

    public void setBophanVi(String bophanVi) {
        this.bophanVi = bophanVi;
    }

    public String getBophanEn() {
        return bophanEn;
    }

    public void setBophanEn(String bophanEn) {
        this.bophanEn = bophanEn;
    }

    public String getYahoo() {
        return yahoo;
    }

    public void setYahoo(String yahoo) {
        this.yahoo = yahoo;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
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

    public String getZalo() {
        return zalo;
    }

    public void setZalo(String zalo) {
        this.zalo = zalo;
    }

    public String getViber() {
        return viber;
    }

    public void setViber(String viber) {
        this.viber = viber;
    }

    public Integer getStt() {
        return stt;
    }

    public void setStt(Integer stt) {
        this.stt = stt;
    }

    public Integer getHienthi() {
        return hienthi;
    }

    public void setHienthi(Integer hienthi) {
        this.hienthi = hienthi;
    }

    public Integer getNgaytao() {
        return ngaytao;
    }

    public void setNgaytao(Integer ngaytao) {
        this.ngaytao = ngaytao;
    }

    public Integer getNgaysua() {
        return ngaysua;
    }

    public void setNgaysua(Integer ngaysua) {
        this.ngaysua = ngaysua;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhotoZalo() {
        return photoZalo;
    }

    public void setPhotoZalo(String photoZalo) {
        this.photoZalo = photoZalo;
    }

    public String getThumbZalo() {
        return thumbZalo;
    }

    public void setThumbZalo(String thumbZalo) {
        this.thumbZalo = thumbZalo;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

}