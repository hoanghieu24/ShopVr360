package com.shopvr360.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "table_baiviet")
public class TableBaiviet {
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

    @NotNull
    @Column(name = "id_sub", nullable = false)
    private Integer idSub;

    @Size(max = 255)
    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    @NotNull
    @Column(name = "hienthi", nullable = false)
    private Integer hienthi;

    @NotNull
    @Column(name = "noibat", nullable = false)
    private Integer noibat;

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
    @Column(name = "tenkhongdau", nullable = false)
    private String tenkhongdau;

    @Size(max = 255)
    @NotNull
    @Column(name = "ten_vi", nullable = false)
    private String tenVi;

    @Size(max = 255)
    @NotNull
    @Column(name = "ten_en", nullable = false)
    private String tenEn;

    @Size(max = 255)
    @NotNull
    @Column(name = "name_vi", nullable = false)
    private String nameVi;

    @NotNull
    @Lob
    @Column(name = "mota_vi", nullable = false)
    private String motaVi;

    @Size(max = 255)
    @NotNull
    @Column(name = "name_en", nullable = false)
    private String nameEn;

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

    @Size(max = 255)
    @NotNull
    @Column(name = "file_download", nullable = false)
    private String fileDownload;

    @Size(max = 255)
    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Lob
    @Column(name = "keywords", nullable = false)
    private String keywords;

    @NotNull
    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "ngaytao", nullable = false)
    private Integer ngaytao;

    @NotNull
    @Column(name = "ngaysua", nullable = false)
    private Integer ngaysua;

    @NotNull
    @Column(name = "luotxem", nullable = false)
    private Integer luotxem;

    @ColumnDefault("'1'")
    @Column(name = "stt", columnDefinition = "int UNSIGNED not null")
    private Long stt;

    @Size(max = 255)
    @NotNull
    @Column(name = "ten_cn", nullable = false)
    private String tenCn;

    @Size(max = 255)
    @NotNull
    @Column(name = "mota_cn", nullable = false)
    private String motaCn;

    @NotNull
    @Lob
    @Column(name = "noidung_cn", nullable = false)
    private String noidungCn;

    @Size(max = 255)
    @NotNull
    @Column(name = "metadata1", nullable = false)
    private String metadata1;

    @Size(max = 255)
    @NotNull
    @Column(name = "metadata2", nullable = false)
    private String metadata2;

    @Size(max = 255)
    @NotNull
    @Column(name = "metadata3", nullable = false)
    private String metadata3;

    @Size(max = 255)
    @NotNull
    @Column(name = "metadata4", nullable = false)
    private String metadata4;

    @Column(name = "user_id")
    private Integer userId;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getHienthi() {
        return hienthi;
    }

    public void setHienthi(Integer hienthi) {
        this.hienthi = hienthi;
    }

    public Integer getNoibat() {
        return noibat;
    }

    public void setNoibat(Integer noibat) {
        this.noibat = noibat;
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

    public String getMotaVi() {
        return motaVi;
    }

    public void setMotaVi(String motaVi) {
        this.motaVi = motaVi;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
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

    public String getFileDownload() {
        return fileDownload;
    }

    public void setFileDownload(String fileDownload) {
        this.fileDownload = fileDownload;
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

    public Integer getLuotxem() {
        return luotxem;
    }

    public void setLuotxem(Integer luotxem) {
        this.luotxem = luotxem;
    }

    public Long getStt() {
        return stt;
    }

    public void setStt(Long stt) {
        this.stt = stt;
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

    public String getMetadata1() {
        return metadata1;
    }

    public void setMetadata1(String metadata1) {
        this.metadata1 = metadata1;
    }

    public String getMetadata2() {
        return metadata2;
    }

    public void setMetadata2(String metadata2) {
        this.metadata2 = metadata2;
    }

    public String getMetadata3() {
        return metadata3;
    }

    public void setMetadata3(String metadata3) {
        this.metadata3 = metadata3;
    }

    public String getMetadata4() {
        return metadata4;
    }

    public void setMetadata4(String metadata4) {
        this.metadata4 = metadata4;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}