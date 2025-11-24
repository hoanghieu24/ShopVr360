package com.shopvr360.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "table_size")
public class TableSize {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "id_product", nullable = false)
    private Integer idProduct;

    @Size(max = 255)
    @NotNull
    @Column(name = "ten_size", nullable = false)
    private String tenSize;

    @NotNull
    @Column(name = "giacu", nullable = false)
    private Integer giacu;

    @NotNull
    @Column(name = "giaban", nullable = false)
    private Integer giaban;

    @Column(name = "gianewp")
    private Integer gianewp;

    @Size(max = 255)
    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    @NotNull
    @Column(name = "hienthi", nullable = false)
    private Integer hienthi;

    @NotNull
    @Column(name = "stt", nullable = false)
    private Integer stt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }

    public String getTenSize() {
        return tenSize;
    }

    public void setTenSize(String tenSize) {
        this.tenSize = tenSize;
    }

    public Integer getGiacu() {
        return giacu;
    }

    public void setGiacu(Integer giacu) {
        this.giacu = giacu;
    }

    public Integer getGiaban() {
        return giaban;
    }

    public void setGiaban(Integer giaban) {
        this.giaban = giaban;
    }

    public Integer getGianewp() {
        return gianewp;
    }

    public void setGianewp(Integer gianewp) {
        this.gianewp = gianewp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

}