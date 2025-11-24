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
import java.util.List;

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
        return ResponseEntity.ok("Đã xóa các sản phẩm có ID: " + ids);
    }



}
