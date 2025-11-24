package com.shopvr360.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "table_mavung")
public class TableMavung {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private Integer code;

    @NotNull
    @ColumnDefault("1")
    @Column(name = "status", nullable = false)
    private Integer status;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "is_africa", nullable = false)
    private Integer isAfrica;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsAfrica() {
        return isAfrica;
    }

    public void setIsAfrica(Integer isAfrica) {
        this.isAfrica = isAfrica;
    }

}