package com.shopvr360.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "table_product")
public class TableProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @Column(name = "id_cat", nullable = false)
    private Integer idCat;

    @Column(name = "id_list", nullable = false)
    private Integer idList;

    @Column(name = "id_item", nullable = false)
    private Integer idItem;

    @Column(name = "kg")
    private Integer kg;

    @Column(name = "id_sub")
    private Integer idSub;

    @Size(max = 2000)
    @Column(name = "tags", length = 2000)
    private String tags;

    @Size(max = 100)
    @Column(name = "type", length = 100)
    private String type;

    @Column(name = "noibat")
    private Integer noibat;

    @Column(name = "banchay")
    private Integer banchay;

    @Column(name = "new")
    private Integer newField;

    @Column(name = "khuyenmai")
    private Integer khuyenmai;

    @Column(name = "nenmua")
    private Integer nenmua;

    @ColumnDefault("1")
    @Column(name = "hienthi")
    private Integer hienthi;

    @Size(max = 225)
    @Column(name = "photo", length = 225)
    private String photo;

    @Size(max = 225)
    @Column(name = "thumb", length = 225)
    private String thumb;

    @Size(max = 255)
    @Column(name = "thumb2")
    private String thumb2;

    @Size(max = 255)
    @Column(name = "photo2")
    private String photo2;

    @Size(max = 255)
    @Column(name = "masp")
    private String masp;

    @Size(max = 255)
    @Column(name = "tenkhongdau")
    private String tenkhongdau;

    @Size(max = 100)
    @Column(name = "ten_vi", length = 100)
    private String tenVi;

    @Size(max = 255)
    @Column(name = "ten_en")
    private String tenEn;

    @Size(max = 255)
    @Column(name = "name_vi")
    private String nameVi;

    @Size(max = 255)
    @Column(name = "name_en")
    private String nameEn;

    @Lob
    @Column(name = "mota_vi")
    private String motaVi;

    @Lob
    @Column(name = "mota_en")
    private String motaEn;

    @Lob
    @Column(name = "noidung_vi")
    private String noidungVi;

    @Lob
    @Column(name = "noidung_en")
    private String noidungEn;

    @Column(name = "giaban")
    private Integer giaban;

    @Column(name = "gianewp")
    private Integer gianewp;

    @Column(name = "giacu")
    private Integer giacu;

    @Size(max = 255)
    @Column(name = "title")
    private String title;

    @Size(max = 1024)
    @Column(name = "keywords", length = 1024)
    private String keywords;

    @Size(max = 1024)
    @Column(name = "description", length = 1024)
    private String description;

    @Column(name = "soluong")
    private Integer soluong;

    @ColumnDefault("'1'")
    @Column(name = "stt", columnDefinition = "int UNSIGNED")
    private Long stt;

    @Column(name = "luotxem")
    private Integer luotxem;

    @Column(name = "ngaytao")
    private Integer ngaytao;

    @Column(name = "ngaysua")
    private Integer ngaysua;

    @Size(max = 255)
    @Column(name = "username")
    private String username;

    @Size(max = 100)
    @Column(name = "ten_cn", length = 100)
    private String tenCn;

    @Lob
    @Column(name = "mota_cn")
    private String motaCn;

    @Lob
    @Column(name = "noidung_cn")
    private String noidungCn;

    @Size(max = 225)
    @Column(name = "name_cn", length = 225)
    private String nameCn;

    @Column(name = "partner_id")
    private Integer partnerId;

    @Size(max = 255)
    @Column(name = "link_youtube")
    private String linkYoutube;

    @Lob
    @Column(name = "dangkykinhdoanh")
    private String dangkykinhdoanh;

    @Lob
    @Column(name = "giayphepkinhdoanh")
    private String giayphepkinhdoanh;

    @Lob
    @Column(name = "congbosanpham")
    private String congbosanpham;

    public Integer getBanchay() {
        return banchay;
    }

    public void setBanchay(Integer banchay) {
        this.banchay = banchay;
    }

    public String getCongbosanpham() {
        return congbosanpham;
    }

    public void setCongbosanpham(String congbosanpham) {
        this.congbosanpham = congbosanpham;
    }

    public String getDangkykinhdoanh() {
        return dangkykinhdoanh;
    }

    public void setDangkykinhdoanh(String dangkykinhdoanh) {
        this.dangkykinhdoanh = dangkykinhdoanh;
    }

    public @Size(max = 1024) String getDescription() {
        return description;
    }

    public void setDescription(@Size(max = 1024) String description) {
        this.description = description;
    }

    public Integer getGiaban() {
        return giaban;
    }

    public void setGiaban(Integer giaban) {
        this.giaban = giaban;
    }

    public Integer getGiacu() {
        return giacu;
    }

    public void setGiacu(Integer giacu) {
        this.giacu = giacu;
    }

    public Integer getGianewp() {
        return gianewp;
    }

    public void setGianewp(Integer gianewp) {
        this.gianewp = gianewp;
    }

    public String getGiayphepkinhdoanh() {
        return giayphepkinhdoanh;
    }

    public void setGiayphepkinhdoanh(String giayphepkinhdoanh) {
        this.giayphepkinhdoanh = giayphepkinhdoanh;
    }

    public Integer getHienthi() {
        return hienthi;
    }

    public void setHienthi(Integer hienthi) {
        this.hienthi = hienthi;
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

    public Integer getIdItem() {
        return idItem;
    }

    public void setIdItem(Integer idItem) {
        this.idItem = idItem;
    }

    public Integer getIdList() {
        return idList;
    }

    public void setIdList(Integer idList) {
        this.idList = idList;
    }

    public Integer getIdSub() {
        return idSub;
    }

    public void setIdSub(Integer idSub) {
        this.idSub = idSub;
    }

    public @Size(max = 1024) String getKeywords() {
        return keywords;
    }

    public void setKeywords(@Size(max = 1024) String keywords) {
        this.keywords = keywords;
    }

    public Integer getKg() {
        return kg;
    }

    public void setKg(Integer kg) {
        this.kg = kg;
    }

    public Integer getKhuyenmai() {
        return khuyenmai;
    }

    public void setKhuyenmai(Integer khuyenmai) {
        this.khuyenmai = khuyenmai;
    }

    public @Size(max = 255) String getLinkYoutube() {
        return linkYoutube;
    }

    public void setLinkYoutube(@Size(max = 255) String linkYoutube) {
        this.linkYoutube = linkYoutube;
    }

    public Integer getLuotxem() {
        return luotxem;
    }

    public void setLuotxem(Integer luotxem) {
        this.luotxem = luotxem;
    }

    public @Size(max = 255) String getMasp() {
        return masp;
    }

    public void setMasp(@Size(max = 255) String masp) {
        this.masp = masp;
    }

    public String getMotaCn() {
        return motaCn;
    }

    public void setMotaCn(String motaCn) {
        this.motaCn = motaCn;
    }

    public String getMotaEn() {
        return motaEn;
    }

    public void setMotaEn(String motaEn) {
        this.motaEn = motaEn;
    }

    public String getMotaVi() {
        return motaVi;
    }

    public void setMotaVi(String motaVi) {
        this.motaVi = motaVi;
    }

    public @Size(max = 225) String getNameCn() {
        return nameCn;
    }

    public void setNameCn(@Size(max = 225) String nameCn) {
        this.nameCn = nameCn;
    }

    public @Size(max = 255) String getNameEn() {
        return nameEn;
    }

    public void setNameEn(@Size(max = 255) String nameEn) {
        this.nameEn = nameEn;
    }

    public @Size(max = 255) String getNameVi() {
        return nameVi;
    }

    public void setNameVi(@Size(max = 255) String nameVi) {
        this.nameVi = nameVi;
    }

    public Integer getNenmua() {
        return nenmua;
    }

    public void setNenmua(Integer nenmua) {
        this.nenmua = nenmua;
    }

    public Integer getNewField() {
        return newField;
    }

    public void setNewField(Integer newField) {
        this.newField = newField;
    }

    public Integer getNgaysua() {
        return ngaysua;
    }

    public void setNgaysua(Integer ngaysua) {
        this.ngaysua = ngaysua;
    }

    public Integer getNgaytao() {
        return ngaytao;
    }

    public void setNgaytao(Integer ngaytao) {
        this.ngaytao = ngaytao;
    }

    public Integer getNoibat() {
        return noibat;
    }

    public void setNoibat(Integer noibat) {
        this.noibat = noibat;
    }

    public String getNoidungCn() {
        return noidungCn;
    }

    public void setNoidungCn(String noidungCn) {
        this.noidungCn = noidungCn;
    }

    public String getNoidungEn() {
        return noidungEn;
    }

    public void setNoidungEn(String noidungEn) {
        this.noidungEn = noidungEn;
    }

    public String getNoidungVi() {
        return noidungVi;
    }

    public void setNoidungVi(String noidungVi) {
        this.noidungVi = noidungVi;
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    public @Size(max = 255) String getPhoto2() {
        return photo2;
    }

    public void setPhoto2(@Size(max = 255) String photo2) {
        this.photo2 = photo2;
    }

    public @Size(max = 225) String getPhoto() {
        return photo;
    }

    public void setPhoto(@Size(max = 225) String photo) {
        this.photo = photo;
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

    public @Size(max = 2000) String getTags() {
        return tags;
    }

    public void setTags(@Size(max = 2000) String tags) {
        this.tags = tags;
    }

    public @Size(max = 100) String getTenCn() {
        return tenCn;
    }

    public void setTenCn(@Size(max = 100) String tenCn) {
        this.tenCn = tenCn;
    }

    public @Size(max = 255) String getTenEn() {
        return tenEn;
    }

    public void setTenEn(@Size(max = 255) String tenEn) {
        this.tenEn = tenEn;
    }

    public @Size(max = 255) String getTenkhongdau() {
        return tenkhongdau;
    }

    public void setTenkhongdau(@Size(max = 255) String tenkhongdau) {
        this.tenkhongdau = tenkhongdau;
    }

    public @Size(max = 100) String getTenVi() {
        return tenVi;
    }

    public void setTenVi(@Size(max = 100) String tenVi) {
        this.tenVi = tenVi;
    }

    public @Size(max = 255) String getThumb2() {
        return thumb2;
    }

    public void setThumb2(@Size(max = 255) String thumb2) {
        this.thumb2 = thumb2;
    }

    public @Size(max = 225) String getThumb() {
        return thumb;
    }

    public void setThumb(@Size(max = 225) String thumb) {
        this.thumb = thumb;
    }

    public @Size(max = 255) String getTitle() {
        return title;
    }

    public void setTitle(@Size(max = 255) String title) {
        this.title = title;
    }

    public @Size(max = 100) String getType() {
        return type;
    }

    public void setType(@Size(max = 100) String type) {
        this.type = type;
    }

    public @Size(max = 255) String getUsername() {
        return username;
    }

    public void setUsername(@Size(max = 255) String username) {
        this.username = username;
    }
}
