package com.shopvr360.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "table_com")
public class TableCom {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "ten")
    private String ten;

    @Size(max = 100)
    @NotNull
    @Column(name = "ten_com", nullable = false, length = 100)
    private String tenCom;

    @Size(max = 100)
    @NotNull
    @Column(name = "act_com", nullable = false, length = 100)
    private String actCom;

    @NotNull
    @Column(name = "danhmuc", nullable = false)
    private Integer danhmuc;

    @Size(max = 100)
    @NotNull
    @Column(name = "type", nullable = false, length = 100)
    private String type;

    @Size(max = 225)
    @NotNull
    @Column(name = "act", nullable = false, length = 225)
    private String act;

    @NotNull
    @ColumnDefault("1")
    @Column(name = "hienthi", nullable = false)
    private Boolean hienthi = false;

    @NotNull
    @Column(name = "stt", nullable = false)
    private Integer stt;

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

    public String getTenCom() {
        return tenCom;
    }

    public void setTenCom(String tenCom) {
        this.tenCom = tenCom;
    }

    public String getActCom() {
        return actCom;
    }

    public void setActCom(String actCom) {
        this.actCom = actCom;
    }

    public Integer getDanhmuc() {
        return danhmuc;
    }

    public void setDanhmuc(Integer danhmuc) {
        this.danhmuc = danhmuc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public Boolean getHienthi() {
        return hienthi;
    }

    public void setHienthi(Boolean hienthi) {
        this.hienthi = hienthi;
    }

    public Integer getStt() {
        return stt;
    }

    public void setStt(Integer stt) {
        this.stt = stt;
    }

}