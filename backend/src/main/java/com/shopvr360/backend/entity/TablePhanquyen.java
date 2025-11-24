package com.shopvr360.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "table_phanquyen")
public class TablePhanquyen {
    @Id
    @Column(name = "id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "id_list", nullable = false)
    private String idList;

    @Size(max = 255)
    @NotNull
    @Column(name = "id_cat", nullable = false)
    private String idCat;

    @Size(max = 255)
    @NotNull
    @Column(name = "id_item", nullable = false)
    private String idItem;

    @ColumnDefault("'1'")
    @Column(name = "stt", columnDefinition = "int UNSIGNED not null")
    private Long stt;

    @Size(max = 200)
    @NotNull
    @Column(name = "ten", nullable = false, length = 200)
    private String ten;

    @NotNull
    @Lob
    @Column(name = "com", nullable = false)
    private String com;

    @Size(max = 225)
    @NotNull
    @Column(name = "table_vitri", nullable = false, length = 225)
    private String tableVitri;

    @Size(max = 225)
    @NotNull
    @Column(name = "xem", nullable = false, length = 225)
    private String xem;

    @Size(max = 225)
    @NotNull
    @Column(name = "them", nullable = false, length = 225)
    private String them;

    @Size(max = 225)
    @NotNull
    @Column(name = "sua", nullable = false, length = 225)
    private String sua;

    @Size(max = 225)
    @NotNull
    @Column(name = "xoa", nullable = false, length = 225)
    private String xoa;

    @Size(max = 200)
    @NotNull
    @Column(name = "soluong", nullable = false, length = 200)
    private String soluong;

    @Size(max = 50)
    @NotNull
    @Column(name = "mausac", nullable = false, length = 50)
    private String mausac;

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

    public String getIdList() {
        return idList;
    }

    public void setIdList(String idList) {
        this.idList = idList;
    }

    public String getIdCat() {
        return idCat;
    }

    public void setIdCat(String idCat) {
        this.idCat = idCat;
    }

    public String getIdItem() {
        return idItem;
    }

    public void setIdItem(String idItem) {
        this.idItem = idItem;
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

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getTableVitri() {
        return tableVitri;
    }

    public void setTableVitri(String tableVitri) {
        this.tableVitri = tableVitri;
    }

    public String getXem() {
        return xem;
    }

    public void setXem(String xem) {
        this.xem = xem;
    }

    public String getThem() {
        return them;
    }

    public void setThem(String them) {
        this.them = them;
    }

    public String getSua() {
        return sua;
    }

    public void setSua(String sua) {
        this.sua = sua;
    }

    public String getXoa() {
        return xoa;
    }

    public void setXoa(String xoa) {
        this.xoa = xoa;
    }

    public String getSoluong() {
        return soluong;
    }

    public void setSoluong(String soluong) {
        this.soluong = soluong;
    }

    public String getMausac() {
        return mausac;
    }

    public void setMausac(String mausac) {
        this.mausac = mausac;
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