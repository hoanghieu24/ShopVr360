package com.shopvr360.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "table_photo")
public class TablePhoto {
    @Id
    @Column(name = "id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @Size(max = 30)
    @NotNull
    @Column(name = "type", nullable = false, length = 30)
    private String type;

    @NotNull
    @Column(name = "id_vitri", nullable = false)
    private Integer idVitri;

    @Size(max = 225)
    @NotNull
    @Column(name = "photo_vi", nullable = false, length = 225)
    private String photoVi;

    @Size(max = 225)
    @NotNull
    @Column(name = "photo_en", nullable = false, length = 225)
    private String photoEn;

    @Size(max = 225)
    @Column(name = "photo_cn", length = 225)
    private String photoCn;

    @Size(max = 150)
    @NotNull
    @Column(name = "thumb_vi", nullable = false, length = 150)
    private String thumbVi;

    @Size(max = 150)
    @NotNull
    @Column(name = "thumb_en", nullable = false, length = 150)
    private String thumbEn;

    @Size(max = 225)
    @Column(name = "thumb_cn", length = 225)
    private String thumbCn;

    @Size(max = 255)
    @NotNull
    @Column(name = "thumb", nullable = false)
    private String thumb;

    @Size(max = 100)
    @NotNull
    @Column(name = "ten_vi", nullable = false, length = 100)
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
    @Column(name = "link", nullable = false)
    private String link;

    @NotNull
    @Lob
    @Column(name = "mota_vi", nullable = false)
    private String motaVi;

    @NotNull
    @Lob
    @Column(name = "mota_en", nullable = false)
    private String motaEn;

    @Lob
    @Column(name = "mota_cn")
    private String motaCn;

    @ColumnDefault("'1'")
    @Column(name = "stt", columnDefinition = "int UNSIGNED not null")
    private Long stt;

    @NotNull
    @ColumnDefault("1")
    @Column(name = "hienthi", nullable = false)
    private Boolean hienthi = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getIdVitri() {
        return idVitri;
    }

    public void setIdVitri(Integer idVitri) {
        this.idVitri = idVitri;
    }

    public String getPhotoVi() {
        return photoVi;
    }

    public void setPhotoVi(String photoVi) {
        this.photoVi = photoVi;
    }

    public String getPhotoEn() {
        return photoEn;
    }

    public void setPhotoEn(String photoEn) {
        this.photoEn = photoEn;
    }

    public String getPhotoCn() {
        return photoCn;
    }

    public void setPhotoCn(String photoCn) {
        this.photoCn = photoCn;
    }

    public String getThumbVi() {
        return thumbVi;
    }

    public void setThumbVi(String thumbVi) {
        this.thumbVi = thumbVi;
    }

    public String getThumbEn() {
        return thumbEn;
    }

    public void setThumbEn(String thumbEn) {
        this.thumbEn = thumbEn;
    }

    public String getThumbCn() {
        return thumbCn;
    }

    public void setThumbCn(String thumbCn) {
        this.thumbCn = thumbCn;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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

    public String getMotaCn() {
        return motaCn;
    }

    public void setMotaCn(String motaCn) {
        this.motaCn = motaCn;
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

}