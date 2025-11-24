package com.shopvr360.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "table_user_log")
public class TableUserLog {
    @Id
    @Column(name = "id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "id_user", nullable = false)
    private String idUser;

    @Size(max = 255)
    @NotNull
    @Column(name = "ip", nullable = false)
    private String ip;

    @Size(max = 255)
    @NotNull
    @Column(name = "timelog", nullable = false)
    private String timelog;

    @Size(max = 255)
    @NotNull
    @Column(name = "user_agent", nullable = false)
    private String userAgent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getTimelog() {
        return timelog;
    }

    public void setTimelog(String timelog) {
        this.timelog = timelog;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

}