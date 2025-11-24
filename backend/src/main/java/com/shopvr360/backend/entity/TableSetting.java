package com.shopvr360.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "table_setting")
public class TableSetting {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Size(max = 1024)
    @NotNull
    @Column(name = "keywords", nullable = false, length = 1024)
    private String keywords;

    @Size(max = 1024)
    @NotNull
    @Column(name = "description", nullable = false, length = 1024)
    private String description;

    @Size(max = 255)
    @NotNull
    @Column(name = "photo", nullable = false)
    private String photo;

    @Size(max = 255)
    @NotNull
    @Column(name = "ten_vi", nullable = false)
    private String tenVi;

    @Size(max = 255)
    @NotNull
    @Column(name = "ten_en", nullable = false)
    private String tenEn;

    @Size(max = 225)
    @NotNull
    @Column(name = "ten_cn", nullable = false, length = 225)
    private String tenCn;

    @Size(max = 255)
    @NotNull
    @Column(name = "slogan_vi", nullable = false)
    private String sloganVi;

    @Size(max = 255)
    @NotNull
    @Column(name = "slogan_en", nullable = false)
    private String sloganEn;

    @Size(max = 255)
    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Size(max = 255)
    @NotNull
    @Column(name = "dienthoai", nullable = false)
    private String dienthoai;

    @Size(max = 255)
    @NotNull
    @Column(name = "diachi_vi", nullable = false)
    private String diachiVi;

    @Size(max = 255)
    @NotNull
    @Column(name = "diachi_en", nullable = false)
    private String diachiEn;

    @Size(max = 255)
    @NotNull
    @Column(name = "hotline", nullable = false)
    private String hotline;

    @Size(max = 255)
    @NotNull
    @Column(name = "hotline2", nullable = false)
    private String hotline2;

    @Size(max = 255)
    @NotNull
    @Column(name = "hotline3", nullable = false)
    private String hotline3;

    @Size(max = 255)
    @NotNull
    @Column(name = "zalo", nullable = false)
    private String zalo;

    @Size(max = 500)
    @NotNull
    @Column(name = "chiduong", nullable = false, length = 500)
    private String chiduong;

    @Size(max = 255)
    @NotNull
    @Column(name = "website", nullable = false)
    private String website;

    @Size(max = 255)
    @NotNull
    @Column(name = "facebook", nullable = false)
    private String facebook;

    @NotNull
    @Lob
    @Column(name = "vchat", nullable = false)
    private String vchat;

    @NotNull
    @Lob
    @Column(name = "codehead", nullable = false)
    private String codehead;

    @NotNull
    @Lob
    @Column(name = "codebody", nullable = false)
    private String codebody;

    @Size(max = 15)
    @NotNull
    @Column(name = "bgweb", nullable = false, length = 15)
    private String bgweb;

    @NotNull
    @Lob
    @Column(name = "version_face", nullable = false)
    private String versionFace;

    @NotNull
    @Lob
    @Column(name = "appid_face", nullable = false)
    private String appidFace;

    @NotNull
    @Lob
    @Column(name = "iframe", nullable = false)
    private String iframe;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

    public String getTenCn() {
        return tenCn;
    }

    public void setTenCn(String tenCn) {
        this.tenCn = tenCn;
    }

    public String getSloganVi() {
        return sloganVi;
    }

    public void setSloganVi(String sloganVi) {
        this.sloganVi = sloganVi;
    }

    public String getSloganEn() {
        return sloganEn;
    }

    public void setSloganEn(String sloganEn) {
        this.sloganEn = sloganEn;
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

    public String getDiachiVi() {
        return diachiVi;
    }

    public void setDiachiVi(String diachiVi) {
        this.diachiVi = diachiVi;
    }

    public String getDiachiEn() {
        return diachiEn;
    }

    public void setDiachiEn(String diachiEn) {
        this.diachiEn = diachiEn;
    }

    public String getHotline() {
        return hotline;
    }

    public void setHotline(String hotline) {
        this.hotline = hotline;
    }

    public String getHotline2() {
        return hotline2;
    }

    public void setHotline2(String hotline2) {
        this.hotline2 = hotline2;
    }

    public String getHotline3() {
        return hotline3;
    }

    public void setHotline3(String hotline3) {
        this.hotline3 = hotline3;
    }

    public String getZalo() {
        return zalo;
    }

    public void setZalo(String zalo) {
        this.zalo = zalo;
    }

    public String getChiduong() {
        return chiduong;
    }

    public void setChiduong(String chiduong) {
        this.chiduong = chiduong;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getVchat() {
        return vchat;
    }

    public void setVchat(String vchat) {
        this.vchat = vchat;
    }

    public String getCodehead() {
        return codehead;
    }

    public void setCodehead(String codehead) {
        this.codehead = codehead;
    }

    public String getCodebody() {
        return codebody;
    }

    public void setCodebody(String codebody) {
        this.codebody = codebody;
    }

    public String getBgweb() {
        return bgweb;
    }

    public void setBgweb(String bgweb) {
        this.bgweb = bgweb;
    }

    public String getVersionFace() {
        return versionFace;
    }

    public void setVersionFace(String versionFace) {
        this.versionFace = versionFace;
    }

    public String getAppidFace() {
        return appidFace;
    }

    public void setAppidFace(String appidFace) {
        this.appidFace = appidFace;
    }

    public String getIframe() {
        return iframe;
    }

    public void setIframe(String iframe) {
        this.iframe = iframe;
    }

}