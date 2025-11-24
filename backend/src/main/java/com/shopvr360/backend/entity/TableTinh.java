package com.shopvr360.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "table_tinh")
public class TableTinh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "ten")
    private String ten;

    @ColumnDefault("1")
    @Column(name = "hienthi", nullable = false)
    private Integer hienthi ;

    @NotNull
    @Column(name = "stt", nullable = false)
    private Integer stt;

    @NotNull
    @Column(name = "phivanchuyen", nullable = false)
    private Integer phivanchuyen;

    public Integer getId(Integer id) {
        return this.id;
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

    public Integer getHienthi() {
        return hienthi;
    }

    public void setHienthi(Integer hienthi) {
        this.hienthi = hienthi;
    }

    public Integer getStt() {
        return stt;
    }

    public void setStt(Integer stt) {
        this.stt = stt;
    }

    public Integer getPhivanchuyen() {
        return phivanchuyen;
    }

    public void setPhivanchuyen(Integer phivanchuyen) {
        this.phivanchuyen = phivanchuyen;
    }

}