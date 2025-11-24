package com.shopvr360.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "table_user_limit")
public class TableUserLimit {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "login_ip", nullable = false, length = 50)
    private String loginIp;

    @NotNull
    @Column(name = "login_attempts", nullable = false)
    private Integer loginAttempts;

    @NotNull
    @Column(name = "attempt_time", nullable = false)
    private Integer attemptTime;

    @NotNull
    @Column(name = "locked_time", nullable = false)
    private Integer lockedTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Integer getLoginAttempts() {
        return loginAttempts;
    }

    public void setLoginAttempts(Integer loginAttempts) {
        this.loginAttempts = loginAttempts;
    }

    public Integer getAttemptTime() {
        return attemptTime;
    }

    public void setAttemptTime(Integer attemptTime) {
        this.attemptTime = attemptTime;
    }

    public Integer getLockedTime() {
        return lockedTime;
    }

    public void setLockedTime(Integer lockedTime) {
        this.lockedTime = lockedTime;
    }

}