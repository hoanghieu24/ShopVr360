package com.shopvr360.backend.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotNull;

public class NewsRequest {
    private int id;
    private String photo;
    private Long stt;
    private String ten_vi;
    private String ten_en;
    private String ten_cn;
    private String motaVi;
    private String noidungVi;
    private String tenkhongdau;
    private String description;
    private String keywords;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNoidungVi() {
        return noidungVi;
    }

    public void setNoidungVi(String noidungVi) {
        this.noidungVi = noidungVi;
    }

    public String getMotaVi() {
        return motaVi;
    }

    public void setMotaVi(String motaVi) {
        this.motaVi = motaVi;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Long getStt() {
        return stt;
    }

    public void setStt(Long stt) {
        this.stt = stt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTen_cn() {
        return ten_cn;
    }

    public void setTen_cn(String ten_cn) {
        this.ten_cn = ten_cn;
    }

    public String getTen_en() {
        return ten_en;
    }

    public void setTen_en(String ten_en) {
        this.ten_en = ten_en;
    }

    public String getTen_vi() {
        return ten_vi;
    }

    public void setTen_vi(String ten_vi) {
        this.ten_vi = ten_vi;
    }

    public String getTenkhongdau() {
        return tenkhongdau;
    }

    public void setTenkhongdau(String tenkhongdau) {
        this.tenkhongdau = tenkhongdau;
    }
}
