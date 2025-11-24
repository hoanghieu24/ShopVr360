package com.shopvr360.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "table_product_sub")
public class TableProductSub {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "id_list", nullable = false)
    private Integer idList;

    @NotNull
    @Column(name = "id_cat", nullable = false)
    private Integer idCat;

    @NotNull
    @Column(name = "id_item", nullable = false)
    private Integer idItem;

    @Size(max = 100)
    @NotNull
    @Column(name = "type", nullable = false, length = 100)
    private String type;

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
    @Column(name = "ten_cn", nullable = false)
    private String tenCn;

    @Size(max = 255)
    @NotNull
    @Column(name = "tenkhongdau", nullable = false)
    private String tenkhongdau;

    @NotNull
    @Lob
    @Column(name = "mota_vi", nullable = false)
    private String motaVi;

    @NotNull
    @Lob
    @Column(name = "mota_en", nullable = false)
    private String motaEn;

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
    @Column(name = "thumb", nullable = false)
    private String thumb;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdList() {
        return idList;
    }

    public void setIdList(Integer idList) {
        this.idList = idList;
    }

    public Integer getIdCat() {
        return idCat;
    }

    public void setIdCat(Integer idCat) {
        this.idCat = idCat;
    }

    public Integer getIdItem() {
        return idItem;
    }

    public void setIdItem(Integer idItem) {
        this.idItem = idItem;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getTenkhongdau() {
        return tenkhongdau;
    }

    public void setTenkhongdau(String tenkhongdau) {
        this.tenkhongdau = tenkhongdau;
    }

    public String getMotaVi() {
        return motaVi;
    }

    public void setMotaVi(String motaVi) {
        this.motaVi = motaVi;
    }

    public String getMotaEn() {
        return motaEn;
    }

    public void setMotaEn(String motaEn) {
        this.motaEn = motaEn;
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

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
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

}