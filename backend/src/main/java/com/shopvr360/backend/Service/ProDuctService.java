package com.shopvr360.backend.Service;

import com.shopvr360.backend.dto.ProductRequest;
import com.shopvr360.backend.entity.TableProduct;
import com.shopvr360.backend.entity.TableProductList;
import com.shopvr360.backend.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class ProDuctService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<TableProduct> getAllProducts() {
        return productRepository.findAll();
    }
    public List<TableProduct> getAllProductByHienThi() {
        return productRepository.findAllByHienthi(1);
    }

    public void toggleField(Long id, String type) {
        TableProduct n = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        switch (type.toLowerCase()) {
            case "active":
                n.setHienthi(n.getHienthi() == 1 ? 0 : 1);
                break;
            case "noibat":
                n.setNoibat(n.getNoibat() == 1 ? 0 : 1);
                break;
            case "new":
                n.setNewField(n.getNewField() == 1 ? 0 : 1);
                break;
            case "banchay":
                n.setBanchay(n.getBanchay() == 1 ? 0 : 1);
                break;
            case "khuyenmai":
                n.setKhuyenmai(n.getKhuyenmai() == 1 ? 0 : 1);
                break;
            case "nenmua":
                n.setNenmua(n.getNenmua() == 1 ? 0 : 1);
                break;
            default:
                throw new IllegalArgumentException("Invalid toggle type");
        }

        productRepository.save(n);
    }


    public TableProduct createProduct(ProductRequest request) throws IOException {
        TableProduct product = modelMapper.map(request, TableProduct.class);

        product.setId(null);

        product.setThumb((request.getPhoto()));
        product.setThumb2((request.getPhoto2()));


        setDefaultFields(product);

        return productRepository.save(product);
    }

    public TableProduct updateProduct(Long id, ProductRequest request) throws IOException {
        TableProduct product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Map những field không null từ request sang entity
        modelMapper.map(request, product);

        // Upload file nếu có
        if (request.getPhoto() != null) product.setThumb((request.getPhoto()));
        if (request.getPhoto2() != null) product.setThumb2((request.getPhoto2()));

        // Cập nhật ngày sửa
        product.setNgaysua((int) (System.currentTimeMillis() / 1000));

        return productRepository.save(product);
    }

    private void setDefaultFields(TableProduct product) {
        product.setIdCat(0);
        product.setIdItem(0);
        product.setIdSub(0);
        product.setTags("");
        product.setType("default");
        product.setNoibat(0);
        product.setBanchay(0);
        product.setNewField(0);
        product.setKhuyenmai(0);
        product.setNenmua(0);
        product.setLuotxem(0);
        product.setNgaytao((int) (System.currentTimeMillis() / 1000));
        product.setNgaysua((int) (System.currentTimeMillis() / 1000));
        product.setUsername("admin");
        product.setStt(1L);

        // Đồng bộ name/mota/noidung
        product.setNameVi(product.getTenVi());
        product.setNameEn(product.getTenEn());
        product.setNameCn(product.getTenCn());
        product.setMotaEn(product.getMotaVi());
        product.setNoidungEn(product.getNoidungVi());
        product.setMotaCn(product.getMotaVi());
        product.setNoidungCn(product.getNoidungVi());
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy sản phẩm có id = " + id);
        }
        productRepository.deleteById(id);
    }

    public void deleteMultiple(List<Long> ids) {
        List<TableProduct> products = productRepository.findAllById(ids);
        if (products.isEmpty()) {
            throw new RuntimeException("Không tìm thấy sản phẩm nào trong danh sách.");
        }
        productRepository.deleteAll(products);
    }

//    private String uploadFile(MultipartFile file) throws IOException {
//        if (file == null || file.isEmpty()) return null;
//        String uploadDir = "uploads/";
//        Path uploadPath = Paths.get(uploadDir);
//        if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);
//        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
//        Path filePath = uploadPath.resolve(fileName);
//        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//        return fileName;
//    }
}
