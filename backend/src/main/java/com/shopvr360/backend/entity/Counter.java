package com.shopvr360.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "counter")
public class Counter {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "tm", nullable = false)
    private Integer tm;

    @Size(max = 16)
    @NotNull
    @ColumnDefault("'0.0.0.0'")
    @Column(name = "ip", nullable = false, length = 16)
    private String ip;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTm() {
        return tm;
    }

    public void setTm(Integer tm) {
        this.tm = tm;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

}