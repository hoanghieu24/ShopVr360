package com.shopvr360.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "table_album")
public class TableAlbum {
    @Id
    @Column(name = "id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @NotNull
    @Column(name = "id_list", nullable = false)
    private Integer idList;

    @NotNull
    @Column(name = "noibat", nullable = false)
    private Integer noibat;

    @NotNull
    @Column(name = "top_nb", nullable = false)
    private Byte topNb;

    @Size(max = 100)
    @NotNull
    @Column(name = "type", nullable = false, length = 100)
    private String type;

    @Size(max = 225)
    @NotNull
    @Column(name = "photo", nullable = false, length = 225)
    private String photo;

    @Size(max = 225)
    @NotNull
    @Column(name = "thumb", nullable = false, length = 225)
    private String thumb;

    @Size(max = 100)
    @NotNull
    @Column(name = "ten_vi", nullable = false, length = 100)
    private String tenVi;

    @Size(max = 255)
    @NotNull
    @Column(name = "tenkhongdau", nullable = false)
    private String tenkhongdau;

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

    @NotNull
    @Lob
    @Column(name = "mota_vi", nullable = false)
    private String motaVi;

    @NotNull
    @Lob
    @Column(name = "noidung_vi", nullable = false)
    private String noidungVi;

    @Size(max = 255)
    @NotNull
    @Column(name = "ten_en", nullable = false)
    private String tenEn;

    @NotNull
    @Lob
    @Column(name = "mota_en", nullable = false)
    private String motaEn;

    @NotNull
    @Lob
    @Column(name = "noidung_en", nullable = false)
    private String noidungEn;

    @ColumnDefault("'1'")
    @Column(name = "stt", columnDefinition = "int UNSIGNED not null")
    private Long stt;

    @NotNull
    @ColumnDefault("1")
    @Column(name = "hienthi", nullable = false)
    private Boolean hienthi = false;

    @NotNull
    @Column(name = "ngaytao", nullable = false)
    private Integer ngaytao;

    @NotNull
    @Column(name = "ngaysua", nullable = false)
    private Integer ngaysua;

    @NotNull
    @Column(name = "luotxem", nullable = false)
    private Integer luotxem;

    @Size(max = 255)
    @NotNull
    @Column(name = "name_vi", nullable = false)
    private String nameVi;

    @Size(max = 255)
    @NotNull
    @Column(name = "name_en", nullable = false)
    private String nameEn;

    @Size(max = 255)
    @NotNull
    @Column(name = "ten_cn", nullable = false)
    private String tenCn;

    @Size(max = 225)
    @NotNull
    @Column(name = "mota_cn", nullable = false, length = 225)
    private String motaCn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdList() {
        return idList;
    }

    public void setIdList(Integer idList) {
        this.idList = idList;
    }

    public Integer getNoibat() {
        return noibat;
    }

    public void setNoibat(Integer noibat) {
        this.noibat = noibat;
    }

    public Byte getTopNb() {
        return topNb;
    }

    public void setTopNb(Byte topNb) {
        this.topNb = topNb;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getTenVi() {
        return tenVi;
    }

    public void setTenVi(String tenVi) {
        this.tenVi = tenVi;
    }

    public String getTenkhongdau() {
        return tenkhongdau;
    }

    public void setTenkhongdau(String tenkhongdau) {
        this.tenkhongdau = tenkhongdau;
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

    public String getMotaVi() {
        return motaVi;
    }

    public void setMotaVi(String motaVi) {
        this.motaVi = motaVi;
    }

    public String getNoidungVi() {
        return noidungVi;
    }

    public void setNoidungVi(String noidungVi) {
        this.noidungVi = noidungVi;
    }

    public String getTenEn() {
        return tenEn;
    }

    public void setTenEn(String tenEn) {
        this.tenEn = tenEn;
    }

    public String getMotaEn() {
        return motaEn;
    }

    public void setMotaEn(String motaEn) {
        this.motaEn = motaEn;
    }

    public String getNoidungEn() {
        return noidungEn;
    }

    public void setNoidungEn(String noidungEn) {
        this.noidungEn = noidungEn;
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

    public Integer getLuotxem() {
        return luotxem;
    }

    public void setLuotxem(Integer luotxem) {
        this.luotxem = luotxem;
    }

    public String getNameVi() {
        return nameVi;
    }

    public void setNameVi(String nameVi) {
        this.nameVi = nameVi;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getTenCn() {
        return tenCn;
    }

    public void setTenCn(String tenCn) {
        this.tenCn = tenCn;
    }

    public String getMotaCn() {
        return motaCn;
    }

    public void setMotaCn(String motaCn) {
        this.motaCn = motaCn;
    }

}