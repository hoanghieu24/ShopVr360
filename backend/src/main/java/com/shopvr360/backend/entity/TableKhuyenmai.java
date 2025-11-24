package com.shopvr360.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "table_khuyenmai")
public class TableKhuyenmai {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 10)
    @NotNull
    @Column(name = "makm", nullable = false, length = 10)
    private String makm;

    @Size(max = 10)
    @NotNull
    @Column(name = "loaikm", nullable = false, length = 10)
    private String loaikm;

    @Size(max = 10)
    @NotNull
    @Column(name = "thanhtoan", nullable = false, length = 10)
    private String thanhtoan;

    @NotNull
    @Column(name = "phantram", nullable = false)
    private Integer phantram;

    @NotNull
    @Column(name = "giamtoida", nullable = false)
    private Integer giamtoida;

    @NotNull
    @Column(name = "donhangtoithieu", nullable = false)
    private Integer donhangtoithieu;

    @Size(max = 255)
    @Column(name = "ten_vi")
    private String tenVi;

    @Size(max = 255)
    @Column(name = "ten_en")
    private String tenEn;

    @Size(max = 255)
    @Column(name = "ten_cn")
    private String tenCn;

    @Lob
    @Column(name = "mota_vi")
    private String motaVi;

    @Lob
    @Column(name = "mota_en")
    private String motaEn;

    @Lob
    @Column(name = "mota_cn")
    private String motaCn;

    @NotNull
    @Column(name = "ngaybatdau", nullable = false)
    private Integer ngaybatdau;

    @NotNull
    @Column(name = "ngayketthuc", nullable = false)
    private Integer ngayketthuc;

    @NotNull
    @Column(name = "solankmtoida", nullable = false)
    private Integer solankmtoida;

    @NotNull
    @Column(name = "luotkm", nullable = false)
    private Integer luotkm;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMakm() {
        return makm;
    }

    public void setMakm(String makm) {
        this.makm = makm;
    }

    public String getLoaikm() {
        return loaikm;
    }

    public void setLoaikm(String loaikm) {
        this.loaikm = loaikm;
    }

    public String getThanhtoan() {
        return thanhtoan;
    }

    public void setThanhtoan(String thanhtoan) {
        this.thanhtoan = thanhtoan;
    }

    public Integer getPhantram() {
        return phantram;
    }

    public void setPhantram(Integer phantram) {
        this.phantram = phantram;
    }

    public Integer getGiamtoida() {
        return giamtoida;
    }

    public void setGiamtoida(Integer giamtoida) {
        this.giamtoida = giamtoida;
    }

    public Integer getDonhangtoithieu() {
        return donhangtoithieu;
    }

    public void setDonhangtoithieu(Integer donhangtoithieu) {
        this.donhangtoithieu = donhangtoithieu;
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

    public Integer getNgaybatdau() {
        return ngaybatdau;
    }

    public void setNgaybatdau(Integer ngaybatdau) {
        this.ngaybatdau = ngaybatdau;
    }

    public Integer getNgayketthuc() {
        return ngayketthuc;
    }

    public void setNgayketthuc(Integer ngayketthuc) {
        this.ngayketthuc = ngayketthuc;
    }

    public Integer getSolankmtoida() {
        return solankmtoida;
    }

    public void setSolankmtoida(Integer solankmtoida) {
        this.solankmtoida = solankmtoida;
    }

    public Integer getLuotkm() {
        return luotkm;
    }

    public void setLuotkm(Integer luotkm) {
        this.luotkm = luotkm;
    }

}