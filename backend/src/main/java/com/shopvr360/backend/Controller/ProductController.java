package com.shopvr360.backend.Controller;


import com.shopvr360.backend.Service.FileStorageService;
import com.shopvr360.backend.Service.ProDuctService;
import com.shopvr360.backend.dto.ProductRequest;
import com.shopvr360.backend.entity.TableProduct;
import com.shopvr360.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProDuctService proDuctService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping
    public List<TableProduct> getAllProducts() {
        return proDuctService.getAllProducts();
    }

    @GetMapping("/view")
    public List<TableProduct> getAllProductsOnView() {
        return proDuctService.getAllProductByHienThi();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> toggleActive(@PathVariable Long id, @RequestParam String type) {
        proDuctService.toggleField(id,type);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody ProductRequest request) throws IOException {
        TableProduct product = proDuctService.createProduct(request);
        return ResponseEntity.ok(product);
    }


    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File ảnh không được để trống");
            }


            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity.badRequest().body("File phải là hình ảnh");
            }


            if (file.getSize() > 5 * 1024 * 1024) {
                return ResponseEntity.badRequest().body("Kích thước file không được vượt quá 5MB");
            }


            String originalFileName = file.getOriginalFilename();
            String fileExtension = "";
            if (originalFileName != null && originalFileName.contains(".")) {
                fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }

            String fileName = "product_" + System.currentTimeMillis() + "_" +
                    UUID.randomUUID().toString().substring(0, 8) + fileExtension;


            LocalDate now = LocalDate.now();
            String year = String.valueOf(now.getYear());
            String month = String.format("%02d", now.getMonthValue());


            String uploadRoot = "C:/Users/Nitro 5/Desktop/Shopvr360/ShopVr360/image/";
            String uploadDir = uploadRoot;


            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);


            String baseUrl = "http://localhost:8080";
            String imageUrl = baseUrl + "/image/"  + fileName;

            Map<String, String> response = new HashMap<>();
            response.put("imageUrl", imageUrl);
            response.put("filename", fileName);
            response.put("message", "Upload ảnh thành công");

            return ResponseEntity.ok(response);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi upload ảnh: " + e.getMessage());
        }
    }


    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestBody ProductRequest productDTO) throws IOException {
        Long id = productDTO.getId();
        return ResponseEntity.ok(proDuctService.updateProduct(id, productDTO));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        proDuctService.deleteProduct(id);
        return ResponseEntity.ok("Xóa thành công sản phẩm có ID = " + id);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteMultiple(@RequestBody List<Long> ids) {
        proDuctService.deleteMultiple(ids);
        return ResponseEntity.ok("Xoá Thành công nhiều sản phẩm ");
    }









}