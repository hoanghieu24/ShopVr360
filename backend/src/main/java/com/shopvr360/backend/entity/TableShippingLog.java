package com.shopvr360.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.time.Instant;

@Entity
@Table(name = "table_shipping_log")
public class TableShippingLog {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "provider")
    private String provider;

    @Lob
    @Column(name = "api")
    private String api;

    @Lob
    @Column(name = "param")
    private String param;

    @Column(name = "result")
    private Integer result;

    @Lob
    @Column(name = "response")
    private String response;

    @Column(name = "created_datetime")
    private Instant createdDatetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Instant getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(Instant createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

}