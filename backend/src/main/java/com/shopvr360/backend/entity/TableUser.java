package com.shopvr360.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Table(name = "table_user")
public class TableUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @Size(max = 225)
    @NotNull
    @Column(name = "username", nullable = false, length = 225)
    private String username;

    @Size(max = 225)
    @NotNull
    @Column(name = "password", nullable = false, length = 225)
    private String password;

    @Size(max = 225)
    @Column(name = "ten", length = 225)
    private String ten;

    @Size(max = 225)
    @Column(name = "dienthoai", length = 225)
    private String dienthoai;

    @Size(max = 225)
    @NotNull
    @Column(name = "email", nullable = false, length = 225)
    private String email;

    @Size(max = 225)
    @Column(name = "diachi", length = 225)
    private String diachi;

    @Column(name = "sex")
    private Boolean sex;

    @Size(max = 225)
    @Column(name = "nick_yahoo", length = 225)
    private String nickYahoo;

    @Size(max = 225)
    @Column(name = "nick_skype", length = 225)
    private String nickSkype;

    @Size(max = 225)
    @Column(name = "congty", length = 225)
    private String congty;

    @Size(max = 225)
    @Column(name = "country", length = 225)
    private String country;

    @Size(max = 225)
    @Column(name = "city", length = 225)
    private String city;

    @Size(max = 255)
    @Column(name = "quyen")
    private String quyen;

    @Column(name = "role", columnDefinition = "tinyint UNSIGNED")
    private Short role;

    @Column(name = "hienthi")
    private Boolean hienthi;

    @Size(max = 225)
    @Column(name = "com", length = 225)
    private String com;

    @Size(max = 255)
    @Column(name = "secretKey")
    private String secretKey;

    @Size(max = 255)
    @Column(name = "login_session")
    private String loginSession;

    @Size(max = 255)
    @Column(name = "user_token")
    private String userToken;

    @Size(max = 255)
    @Column(name = "lastlogin")
    private String lastlogin;

    @Column(name = "codeExpiry")
    private LocalDateTime codeExpiry;

    public @Size(max = 225) String getCity() {
        return city;
    }

    public void setCity(@Size(max = 225) String city) {
        this.city = city;
    }

    public LocalDateTime getCodeExpiry() {
        return codeExpiry;
    }

    public void setCodeExpiry(LocalDateTime codeExpiry) {
        this.codeExpiry = codeExpiry;
    }

    public @Size(max = 225) String getCom() {
        return com;
    }

    public void setCom(@Size(max = 225) String com) {
        this.com = com;
    }

    public @Size(max = 225) String getCongty() {
        return congty;
    }

    public void setCongty(@Size(max = 225) String congty) {
        this.congty = congty;
    }

    public @Size(max = 225) String getCountry() {
        return country;
    }

    public void setCountry(@Size(max = 225) String country) {
        this.country = country;
    }

    public @Size(max = 225) String getDiachi() {
        return diachi;
    }

    public void setDiachi(@Size(max = 225) String diachi) {
        this.diachi = diachi;
    }

    public @Size(max = 225) String getDienthoai() {
        return dienthoai;
    }

    public void setDienthoai(@Size(max = 225) String dienthoai) {
        this.dienthoai = dienthoai;
    }

    public @Size(max = 225) @NotNull String getEmail() {
        return email;
    }

    public void setEmail(@Size(max = 225) @NotNull String email) {
        this.email = email;
    }

    public Boolean getHienthi() {
        return hienthi;
    }

    public void setHienthi(Boolean hienthi) {
        this.hienthi = hienthi;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @Size(max = 255) String getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(@Size(max = 255) String lastlogin) {
        this.lastlogin = lastlogin;
    }

    public @Size(max = 255) String getLoginSession() {
        return loginSession;
    }

    public void setLoginSession(@Size(max = 255) String loginSession) {
        this.loginSession = loginSession;
    }

    public @Size(max = 225) String getNickSkype() {
        return nickSkype;
    }

    public void setNickSkype(@Size(max = 225) String nickSkype) {
        this.nickSkype = nickSkype;
    }

    public @Size(max = 225) String getNickYahoo() {
        return nickYahoo;
    }

    public void setNickYahoo(@Size(max = 225) String nickYahoo) {
        this.nickYahoo = nickYahoo;
    }

    public @Size(max = 225) @NotNull String getPassword() {
        return password;
    }

    public void setPassword(@Size(max = 225) @NotNull String password) {
        this.password = password;
    }

    public @Size(max = 255) String getQuyen() {
        return quyen;
    }

    public void setQuyen(@Size(max = 255) String quyen) {
        this.quyen = quyen;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private RoleEntity roleEntity;

    public RoleEntity getRoleEntity() {
        return roleEntity;
    }

    public void setRoleEntity(RoleEntity roleEntity) {
        this.roleEntity = roleEntity;
    }



    public Short getRole() {
        return role;
    }

    public void setRole(Short role) {
        this.role = role;
    }

    public @Size(max = 255) String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(@Size(max = 255) String secretKey) {
        this.secretKey = secretKey;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public @Size(max = 225) String getTen() {
        return ten;
    }

    public void setTen(@Size(max = 225) String ten) {
        this.ten = ten;
    }

    public @Size(max = 225) @NotNull String getUsername() {
        return username;
    }

    public void setUsername(@Size(max = 225) @NotNull String username) {
        this.username = username;
    }

    public @Size(max = 255) String getUserToken() {
        return userToken;
    }

    public void setUserToken(@Size(max = 255) String userToken) {
        this.userToken = userToken;
    }

    // getter + setter giữ nguyên
}


