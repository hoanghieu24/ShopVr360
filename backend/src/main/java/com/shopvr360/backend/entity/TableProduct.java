package com.shopvr360.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "table_product")
public class TableProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @NotNull
    @Column(name = "id_cat", nullable = false)
    private Integer idCat;

    @NotNull
    @Column(name = "id_list", nullable = false)
    private Integer idList;

    @NotNull
    @Column(name = "id_item", nullable = false)
    private Integer idItem;

    @Column(name = "kg")
    private Integer kg;   // hoặc Float


    @NotNull
    @Column(name = "id_sub", nullable = false)
    private Integer idSub;

    @Size(max = 2000)
    @NotNull
    @Column(name = "tags", nullable = false, length = 2000)
    private String tags;

    @Size(max = 100)
    @NotNull
    @Column(name = "type", nullable = false, length = 100)
    private String type;

    @NotNull
    @Column(name = "noibat", nullable = false)
    private Integer noibat;

    @NotNull
    @Column(name = "banchay", nullable = false)
    private Integer banchay;

    @NotNull
    @Column(name = "new", nullable = false)
    private Integer newField;

    @NotNull
    @Column(name = "khuyenmai", nullable = false)
    private Integer khuyenmai;

    @NotNull
    @Column(name = "nenmua", nullable = false)
    private Integer nenmua;

    @NotNull
    @ColumnDefault("1")
    @Column(name = "hienthi", nullable = false)
    private Integer hienthi;

    @Size(max = 225)
    @NotNull
    @Column(name = "photo", nullable = false, length = 225)
    private String photo;

    @Size(max = 225)
    @NotNull
    @Column(name = "thumb", nullable = false, length = 225)
    private String thumb;

    @Size(max = 255)
    @NotNull
    @Column(name = "thumb2", nullable = false)
    private String thumb2;

    @Size(max = 255)
    @NotNull
    @Column(name = "photo2", nullable = false)
    private String photo2;

    @Size(max = 255)
    @NotNull
    @Column(name = "masp", nullable = false)
    private String masp;

    @Size(max = 255)
    @NotNull
    @Column(name = "tenkhongdau", nullable = false)
    private String tenkhongdau;

    @Size(max = 100)
    @NotNull
    @Column(name = "ten_vi", nullable = false, length = 100)
    private String tenVi;

    @Size(max = 255)
    @NotNull
    @Column(name = "ten_en", nullable = false)
    private String tenEn;

    @Size(max = 255)
    @NotNull
    @Column(name = "name_vi", nullable = false)
    private String nameVi;

    @Size(max = 255)
    @NotNull
    @Column(name = "name_en", nullable = false)
    private String nameEn;

    @NotNull
    @Lob
    @Column(name = "mota_vi", nullable = false)
    private String motaVi;

    @NotNull
    @Lob
    @Column(name = "mota_en", nullable = false)
    private String motaEn;

    @NotNull
    @Lob
    @Column(name = "noidung_vi", nullable = false)
    private String noidungVi;

    @NotNull
    @Lob
    @Column(name = "noidung_en", nullable = false)
    private String noidungEn;

    @NotNull
    @Column(name = "giaban", nullable = false)
    private Integer giaban;

    @Column(name = "gianewp")
    private Integer gianewp;

    @NotNull
    @Column(name = "giacu", nullable = false)
    private Integer giacu;

    @Size(max = 255)
    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Size(max = 1024)
    @NotNull
    @Column(name = "keywords", nullable = false, length = 1024)
    private String keywords;

    @Size(max = 1024)
    @NotNull
    @Column(name = "description", nullable = false, length = 1024)
    private String description;

    @NotNull
    @Column(name = "soluong", nullable = false)
    private Integer soluong;

    @ColumnDefault("'1'")
    @Column(name = "stt", columnDefinition = "int UNSIGNED not null")
    private Long stt;

    @NotNull
    @Column(name = "luotxem", nullable = false)
    private Integer luotxem;

    @NotNull
    @Column(name = "ngaytao", nullable = false)
    private Integer ngaytao;

    @NotNull
    @Column(name = "ngaysua", nullable = false)
    private Integer ngaysua;

    @Size(max = 255)
    @NotNull
    @Column(name = "username", nullable = false)
    private String username;

    @Size(max = 100)
    @NotNull
    @Column(name = "ten_cn", nullable = false, length = 100)
    private String tenCn;

    @NotNull
    @Lob
    @Column(name = "mota_cn", nullable = false)
    private String motaCn;

    @NotNull
    @Lob
    @Column(name = "noidung_cn", nullable = false)
    private String noidungCn;

    @Size(max = 225)
    @NotNull
    @Column(name = "name_cn", nullable = false, length = 225)
    private String nameCn;

    @Column(name = "partner_id")
    private Integer partnerId;

    @Size(max = 255)
    @NotNull
    @Column(name = "link_youtube", nullable = false)
    private String linkYoutube;

    @NotNull
    @Lob
    @Column(name = "dangkykinhdoanh", nullable = false)
    private String dangkykinhdoanh;

    @NotNull
    @Lob
    @Column(name = "giayphepkinhdoanh", nullable = false)
    private String giayphepkinhdoanh;

    @NotNull
    @Lob
    @Column(name = "congbosanpham", nullable = false)
    private String congbosanpham;

    public Integer getKg() {
        return kg;
    }

    public void setKg(Integer kg) {
        this.kg = kg;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdCat() {
        return idCat;
    }

    public void setIdCat(Integer idCat) {
        this.idCat = idCat;
    }

    public Integer getIdList() {
        return idList;
    }

    public void setIdList(Integer idList) {
        this.idList = idList;
    }

    public Integer getIdItem() {
        return idItem;
    }

    public void setIdItem(Integer idItem) {
        this.idItem = idItem;
    }

    public Integer getIdSub() {
        return idSub;
    }

    public void setIdSub(Integer idSub) {
        this.idSub = idSub;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getNoibat() {
        return noibat;
    }

    public void setNoibat(Integer noibat) {
        this.noibat = noibat;
    }

    public Integer getBanchay() {
        return banchay;
    }

    public void setBanchay(Integer banchay) {
        this.banchay = banchay;
    }

    public Integer getNewField() {
        return newField;
    }

    public void setNewField(Integer newField) {
        this.newField = newField;
    }

    public Integer getKhuyenmai() {
        return khuyenmai;
    }

    public void setKhuyenmai(Integer khuyenmai) {
        this.khuyenmai = khuyenmai;
    }

    public Integer getNenmua() {
        return nenmua;
    }

    public void setNenmua(Integer nenmua) {
        this.nenmua = nenmua;
    }

    public Integer getHienthi() {
        return hienthi;
    }

    public void setHienthi(Integer hienthi) {
        this.hienthi = hienthi;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getThumb2() {
        return thumb2;
    }

    public void setThumb2(String thumb2) {
        this.thumb2 = thumb2;
    }

    public String getPhoto2() {
        return photo2;
    }

    public void setPhoto2(String photo2) {
        this.photo2 = photo2;
    }

    public String getMasp() {
        return masp;
    }

    public void setMasp(String masp) {
        this.masp = masp;
    }

    public String getTenkhongdau() {
        return tenkhongdau;
    }

    public void setTenkhongdau(String tenkhongdau) {
        this.tenkhongdau = tenkhongdau;
    }

    public String getTenVi() {
        return tenVi;
    }

    public void setTenVi(String tenVi) {
        this.tenVi = tenVi;
    }

    public String getTenEn() {
        return tenEn;
    }

    public void setTenEn(String tenEn) {
        this.tenEn = tenEn;
    }

    public String getNameVi() {
        return nameVi;
    }

    public void setNameVi(String nameVi) {
        this.nameVi = nameVi;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getMotaVi() {
        return motaVi;
    }

    public void setMotaVi(String motaVi) {
        this.motaVi = motaVi;
    }

    public String getMotaEn() {
        return motaEn;
    }

    public void setMotaEn(String motaEn) {
        this.motaEn = motaEn;
    }

    public String getNoidungVi() {
        return noidungVi;
    }

    public void setNoidungVi(String noidungVi) {
        this.noidungVi = noidungVi;
    }

    public String getNoidungEn() {
        return noidungEn;
    }

    public void setNoidungEn(String noidungEn) {
        this.noidungEn = noidungEn;
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

    public Integer getGiacu() {
        return giacu;
    }

    public void setGiacu(Integer giacu) {
        this.giacu = giacu;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSoluong() {
        return soluong;
    }

    public void setSoluong(Integer soluong) {
        this.soluong = soluong;
    }

    public Long getStt() {
        return stt;
    }

    public void setStt(Long stt) {
        this.stt = stt;
    }

    public Integer getLuotxem() {
        return luotxem;
    }

    public void setLuotxem(Integer luotxem) {
        this.luotxem = luotxem;
    }

    public Integer getNgaytao() {
        return ngaytao;
    }

    public void setNgaytao(Integer ngaytao) {
        this.ngaytao = ngaytao;
    }

    public Integer getNgaysua() {
        return ngaysua;
    }

    public void setNgaysua(Integer ngaysua) {
        this.ngaysua = ngaysua;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTenCn() {
        return tenCn;
    }

    public void setTenCn(String tenCn) {
        this.tenCn = tenCn;
    }

    public String getMotaCn() {
        return motaCn;
    }

    public void setMotaCn(String motaCn) {
        this.motaCn = motaCn;
    }

    public String getNoidungCn() {
        return noidungCn;
    }

    public void setNoidungCn(String noidungCn) {
        this.noidungCn = noidungCn;
    }

    public String getNameCn() {
        return nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    public String getLinkYoutube() {
        return linkYoutube;
    }

    public void setLinkYoutube(String linkYoutube) {
        this.linkYoutube = linkYoutube;
    }

    public String getDangkykinhdoanh() {
        return dangkykinhdoanh;
    }

    public void setDangkykinhdoanh(String dangkykinhdoanh) {
        this.dangkykinhdoanh = dangkykinhdoanh;
    }

    public String getGiayphepkinhdoanh() {
        return giayphepkinhdoanh;
    }

    public void setGiayphepkinhdoanh(String giayphepkinhdoanh) {
        this.giayphepkinhdoanh = giayphepkinhdoanh;
    }

    public String getCongbosanpham() {
        return congbosanpham;
    }

    public void setCongbosanpham(String congbosanpham) {
        this.congbosanpham = congbosanpham;
    }

}