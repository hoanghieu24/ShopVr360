package com.shopvr360.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "table_quan")
public class TableQuan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "ten")
    private String ten;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "id_cat", nullable = false)
    private Integer idCat;

    @Size(max = 10)
    @NotNull
    @Column(name = "code", nullable = false, length = 10)
    private String code;

    @NotNull
    @ColumnDefault("1")
    @Column(name = "stt", nullable = false)
    private Integer stt;

    @NotNull
    @ColumnDefault("1")
    @Column(name = "hienthi", nullable = false)
    private Boolean hienthi = false;

    @NotNull
    @Column(name = "phivanchuyen", nullable = false)
    private Integer phivanchuyen;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Integer getIdCat() {
        return idCat;
    }

    public void setIdCat(Integer idCat) {
        this.idCat = idCat;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getStt() {
        return stt;
    }

    public void setStt(Integer stt) {
        this.stt = stt;
    }

    public Boolean getHienthi() {
        return hienthi;
    }

    public void setHienthi(Boolean hienthi) {
        this.hienthi = hienthi;
    }

    public Integer getPhivanchuyen() {
        return phivanchuyen;
    }

    public void setPhivanchuyen(Integer phivanchuyen) {
        this.phivanchuyen = phivanchuyen;
    }

}