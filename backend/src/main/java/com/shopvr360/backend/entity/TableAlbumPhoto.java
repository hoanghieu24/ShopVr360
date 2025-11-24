package com.shopvr360.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "table_album_photo")
public class TableAlbumPhoto {
    @Id
    @Column(name = "id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @NotNull
    @Column(name = "id_album", nullable = false)
    private Integer idAlbum;

    @Size(max = 50)
    @NotNull
    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Size(max = 225)
    @NotNull
    @Column(name = "photo", nullable = false, length = 225)
    private String photo;

    @Size(max = 225)
    @NotNull
    @Column(name = "thumb", nullable = false, length = 225)
    private String thumb;

    @Size(max = 255)
    @NotNull
    @Column(name = "ten", nullable = false)
    private String ten;

    @NotNull
    @Lob
    @Column(name = "mota", nullable = false)
    private String mota;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(Integer idAlbum) {
        this.idAlbum = idAlbum;
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

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
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

}