package com.shopvr360.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "table_bgweb")
public class TableBgweb {
    @Id
    @Column(name = "id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @Size(max = 20)
    @NotNull
    @Column(name = "re_peat", nullable = false, length = 20)
    private String rePeat;

    @Size(max = 20)
    @NotNull
    @Column(name = "tren", nullable = false, length = 20)
    private String tren;

    @Size(max = 20)
    @NotNull
    @Column(name = "trai", nullable = false, length = 20)
    private String trai;

    @Size(max = 20)
    @NotNull
    @Column(name = "fix_bg", nullable = false, length = 20)
    private String fixBg;

    @Size(max = 20)
    @NotNull
    @Column(name = "mauneen", nullable = false, length = 20)
    private String mauneen;

    @Size(max = 50)
    @NotNull
    @Column(name = "type", nullable = false, length = 50)
    private String type;

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

    @NotNull
    @ColumnDefault("0")
    @Column(name = "ngaytao", nullable = false)
    private Integer ngaytao;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "ngaysua", nullable = false)
    private Integer ngaysua;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRePeat() {
        return rePeat;
    }

    public void setRePeat(String rePeat) {
        this.rePeat = rePeat;
    }

    public String getTren() {
        return tren;
    }

    public void setTren(String tren) {
        this.tren = tren;
    }

    public String getTrai() {
        return trai;
    }

    public void setTrai(String trai) {
        this.trai = trai;
    }

    public String getFixBg() {
        return fixBg;
    }

    public void setFixBg(String fixBg) {
        this.fixBg = fixBg;
    }

    public String getMauneen() {
        return mauneen;
    }

    public void setMauneen(String mauneen) {
        this.mauneen = mauneen;
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