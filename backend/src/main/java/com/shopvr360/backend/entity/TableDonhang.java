package com.shopvr360.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Table(name = "table_donhang")
public class TableDonhang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 20)
    @NotNull
    @Column(name = "madonhang", nullable = false, length = 20)
    private String madonhang;

    @Size(max = 255)
    @NotNull
    @Column(name = "hoten", nullable = false)
    private String hoten;

    @Size(max = 255)
    @NotNull
    @Column(name = "dienthoai", nullable = false)
    private String dienthoai;

    @Size(max = 5)
    @Column(name = "mavung", length = 5)
    private String mavung;

    @Size(max = 255)
    @NotNull
    @Column(name = "diachi", nullable = false)
    private String diachi;

    @Size(max = 255)
    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Size(max = 255)
    @NotNull
    @Column(name = "httt", nullable = false)
    private String httt;

    @NotNull
    @Column(name = "tonggia", nullable = false)
    private Integer tonggia;

    @NotNull
    @Lob
    @Column(name = "noidung", nullable = false)
    private String noidung;

    @NotNull
    @Lob
    @Column(name = "ghichu", nullable = false)
    private String ghichu;

    @Size(max = 30)
    @NotNull
    @Column(name = "ngaytao", nullable = false, length = 30)
    private String ngaytao;

    @NotNull
    @Column(name = "tinhtrang", nullable = false)
    private Integer tinhtrang;

    @NotNull
    @Column(name = "thanhpho", nullable = false)
    private Long thanhpho;

    @NotNull
    @Column(name = "quan", nullable = false)
    private Integer quan;

    @NotNull
    @ColumnDefault("1")
    @Column(name = "htgh", nullable = false)
    private Integer htgh;

    @Size(max = 255)
    @NotNull
    @Column(name = "nguongoc", nullable = false)
    private String nguongoc;

    @NotNull
    @Column(name = "ngaycapnhat", nullable = false)
    private int ngaycapnhat;

    @Size(max = 30)
    @NotNull
    @Column(name = "ngaydi", nullable = false, length = 30)
    private String ngaydi;

    @Size(max = 50)
    @NotNull
    @Column(name = "ip", nullable = false, length = 50)
    private String ip;

    @Size(max = 300)
    @NotNull
    @Column(name = "ngayin", nullable = false, length = 300)
    private String ngayin;

    @NotNull
    @Column(name = "export", nullable = false)
    private Byte export;

    @Size(max = 20)
    @NotNull
    @Column(name = "gioin", nullable = false, length = 20)
    private String gioin;

    @NotNull
    @Column(name = "phivanchuyen", nullable = false)
    private Float phivanchuyen;

    @NotNull
    @Column(name = "phithem", nullable = false)
    private Integer phithem;

    @NotNull
    @Column(name = "thuve", nullable = false)
    private Integer thuve;

    @NotNull
    @Column(name = "id_user", nullable = false)
    private Integer idUser;

    @Min(1)
    @Max(100)
    @NotNull
    @Column(name = "phuong", nullable = false, length = 11)
    private Integer phuong;

    @NotNull
    @Column(name = "phigiam", nullable = false)
    private Integer phigiam;

    @NotNull
    @Column(name = "view", nullable = false)
    private Integer view;

    @Size(max = 255)
    @Column(name = "tx")
    private String tx;

    @Size(max = 255)
    @Column(name = "donvivanchuyen")
    private String donvivanchuyen;

    @Size(max = 255)
    @Column(name = "madonvan")
    private String madonvan;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "thanhtoan", nullable = false)
    private Integer thanhtoan;

    @Size(max = 10)
    @NotNull
    @Column(name = "makm", nullable = false, length = 10)
    private String makm;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "is_delete", nullable = false)
    private Integer isDelete;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMadonhang() {
        return madonhang;
    }

    public void setMadonhang(String madonhang) {
        this.madonhang = madonhang;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getDienthoai() {
        return dienthoai;
    }

    public void setDienthoai(String dienthoai) {
        this.dienthoai = dienthoai;
    }

    public String getMavung() {
        return mavung;
    }

    public void setMavung(String mavung) {
        this.mavung = mavung;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHttt() {
        return httt;
    }

    public void setHttt(String httt) {
        this.httt = httt;
    }

    public Integer getTonggia() {
        return tonggia;
    }

    public void setTonggia(Integer tonggia) {
        this.tonggia = tonggia;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public String getNgaytao() {
        return ngaytao;
    }

    public void setNgaytao(String ngaytao) {
        this.ngaytao = ngaytao;
    }

    public Integer getTinhtrang() {
        return tinhtrang;
    }

    public void setTinhtrang(Integer tinhtrang) {
        this.tinhtrang = tinhtrang;
    }

    public Long getThanhpho() {
        return thanhpho;
    }

    public void setThanhpho(Long thanhpho) {
        this.thanhpho = thanhpho;
    }

    public Integer getQuan() {
        return quan;
    }

    public void setQuan(Integer quan) {
        this.quan = quan;
    }

    public Integer getHtgh() {
        return htgh;
    }

    public void setHtgh(Integer htgh) {
        this.htgh = htgh;
    }

    public String getNguongoc() {
        return nguongoc;
    }

    public void setNguongoc(String nguongoc) {
        this.nguongoc = nguongoc;
    }

    public int getNgaycapnhat() {
        return ngaycapnhat;
    }

    public void setNgaycapnhat(int ngaycapnhat) {
        this.ngaycapnhat = ngaycapnhat;
    }

    public String getNgaydi() {
        return ngaydi;
    }

    public void setNgaydi(String ngaydi) {
        this.ngaydi = ngaydi;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getNgayin() {
        return ngayin;
    }

    public void setNgayin(String ngayin) {
        this.ngayin = ngayin;
    }

    public Byte getExport() {
        return export;
    }

    public void setExport(Byte export) {
        this.export = export;
    }

    public String getGioin() {
        return gioin;
    }

    public void setGioin(String gioin) {
        this.gioin = gioin;
    }

    public Float getPhivanchuyen() {
        return phivanchuyen;
    }

    public void setPhivanchuyen(Float phivanchuyen) {
        this.phivanchuyen = phivanchuyen;
    }

    public Integer getPhithem() {
        return phithem;
    }

    public void setPhithem(Integer phithem) {
        this.phithem = phithem;
    }

    public Integer getThuve() {
        return thuve;
    }

    public void setThuve(Integer thuve) {
        this.thuve = thuve;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getPhuong() {
        return phuong;
    }

    public void setPhuong(Integer phuong) {
        this.phuong = phuong;
    }

    public Integer getPhigiam() {
        return phigiam;
    }

    public void setPhigiam(Integer phigiam) {
        this.phigiam = phigiam;
    }

    public Integer getView() {
        return view;
    }

    public void setView(Integer view) {
        this.view = view;
    }

    public String getTx() {
        return tx;
    }

    public void setTx(String tx) {
        this.tx = tx;
    }

    public String getDonvivanchuyen() {
        return donvivanchuyen;
    }

    public void setDonvivanchuyen(String donvivanchuyen) {
        this.donvivanchuyen = donvivanchuyen;
    }

    public String getMadonvan() {
        return madonvan;
    }

    public void setMadonvan(String madonvan) {
        this.madonvan = madonvan;
    }

    public Integer getThanhtoan() {
        return thanhtoan;
    }

    public void setThanhtoan(Integer thanhtoan) {
        this.thanhtoan = thanhtoan;
    }

    public String getMakm() {
        return makm;
    }

    public void setMakm(String makm) {
        this.makm = makm;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

}