package com.shopvr360.backend.dto;

import org.springframework.web.multipart.MultipartFile;

public class ProductRequest {
    private Long id;
    private Integer idCat;
    private Integer idList;
    private Integer idItem;
    private Integer idSub;
    private Integer noibat;
    private Integer banchay;
    private Integer newField;
    private Integer khuyenmai;
    private Integer nenmua;
    private Integer hienthi;
    private Integer giaban;
    private Integer giacu;
    private Integer soluong;
    private Integer kg;
    private Long stt;
    private Integer luotxem;
    private String type;

    private String photo;
    private String thumb;
    private String photo2;
    private String thumb2;
    private String masp;
    private String tenkhongdau;
    private String tenVi;
    private String tenEn;
    private String tenCn;
    private String nameVi;
    private String nameEn;
    private String nameCn;
    private String motaVi;
    private String motaEn;
    private String motaCn;
    private String noidungVi;
    private String noidungEn;
    private String noidungCn;
    private String title;
    private String keywords;
    private String description;
    private String linkYoutube;
    private String username;
    private String dangkykinhdoanh;
    private String giayphepkinhdoanh;
    private String congbosanpham;

    // File uploads
    private MultipartFile mainImageFile;
    private MultipartFile thumb2File;
    private MultipartFile dangKyKinhDoanhFile;
    private MultipartFile giayPhepKinhDoanhFile;
    private MultipartFile banCongBoSanPhamFile;
    private String tieuDeTrung;

    public Integer getBanchay() {
        return banchay;
    }

    public void setBanchay(Integer banchay) {
        this.banchay = banchay;
    }

    public MultipartFile getBanCongBoSanPhamFile() {
        return banCongBoSanPhamFile;
    }

    public void setBanCongBoSanPhamFile(MultipartFile banCongBoSanPhamFile) {
        this.banCongBoSanPhamFile = banCongBoSanPhamFile;
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

    public MultipartFile getDangKyKinhDoanhFile() {
        return dangKyKinhDoanhFile;
    }

    public void setDangKyKinhDoanhFile(MultipartFile dangKyKinhDoanhFile) {
        this.dangKyKinhDoanhFile = dangKyKinhDoanhFile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
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

    public String getGiayphepkinhdoanh() {
        return giayphepkinhdoanh;
    }

    public void setGiayphepkinhdoanh(String giayphepkinhdoanh) {
        this.giayphepkinhdoanh = giayphepkinhdoanh;
    }

    public MultipartFile getGiayPhepKinhDoanhFile() {
        return giayPhepKinhDoanhFile;
    }

    public void setGiayPhepKinhDoanhFile(MultipartFile giayPhepKinhDoanhFile) {
        this.giayPhepKinhDoanhFile = giayPhepKinhDoanhFile;
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

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
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

    public String getLinkYoutube() {
        return linkYoutube;
    }

    public void setLinkYoutube(String linkYoutube) {
        this.linkYoutube = linkYoutube;
    }

    public Integer getLuotxem() {
        return luotxem;
    }

    public void setLuotxem(Integer luotxem) {
        this.luotxem = luotxem;
    }

    public MultipartFile getMainImageFile() {
        return mainImageFile;
    }

    public void setMainImageFile(MultipartFile mainImageFile) {
        this.mainImageFile = mainImageFile;
    }

    public String getMasp() {
        return masp;
    }

    public void setMasp(String masp) {
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

    public String getNameCn() {
        return nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameVi() {
        return nameVi;
    }

    public void setNameVi(String nameVi) {
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

    public String getPhoto2() {
        return photo2;
    }

    public void setPhoto2(String photo2) {
        this.photo2 = photo2;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
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

    public String getTenCn() {
        return tenCn;
    }

    public void setTenCn(String tenCn) {
        this.tenCn = tenCn;
    }

    public String getTenEn() {
        return tenEn;
    }

    public void setTenEn(String tenEn) {
        this.tenEn = tenEn;
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

    public String getThumb2() {
        return thumb2;
    }

    public void setThumb2(String thumb2) {
        this.thumb2 = thumb2;
    }

    public MultipartFile getThumb2File() {
        return thumb2File;
    }

    public void setThumb2File(MultipartFile thumb2File) {
        this.thumb2File = thumb2File;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getTieuDeTrung() {
        return tieuDeTrung;
    }

    public void setTieuDeTrung(String tieuDeTrung) {
        this.tieuDeTrung = tieuDeTrung;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
