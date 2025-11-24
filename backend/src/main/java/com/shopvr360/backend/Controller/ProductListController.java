package com.shopvr360.backend.Controller;

import com.shopvr360.backend.Service.ProductListService;
import com.shopvr360.backend.dto.ProductListRequest;
import com.shopvr360.backend.dto.ProductRequest;
import com.shopvr360.backend.entity.TableProductList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/productLists")
public class ProductListController {

    @Autowired
    private ProductListService ProductList;

    @GetMapping
    public List<TableProductList> getAll() {
        return ProductList.getAllProductList();
    }

    @GetMapping("/view")
    public List<TableProductList> getAllOnView(@RequestParam(required = false) String category) {
        if (category != null) {
            return ProductList.getAllProductListByType(category);
        }
        return ProductList.getAllProductListByHienThi();
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> toggleActive(@PathVariable Long id,  @RequestParam String type) {
        ProductList.toggleField(id,type);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> addProductList(@RequestBody ProductListRequest productList) throws IOException {
        TableProductList tableProductList = ProductList.createProductList(productList);
        return ResponseEntity.ok(tableProductList);
    }

    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestBody ProductListRequest productList) throws IOException {
        Long id = productList.getId();
        return ResponseEntity.ok(ProductList.updateProduct(id, productList));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteProductList(@PathVariable Long id) throws IOException {
        ProductList.deleteProduct(id);
        return ResponseEntity.ok("Xóa thành công loại sản phẩm có ID = " + id);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteMultiple(@RequestBody List<Long> ids) throws IOException {
        ProductList.deleteMultiple(ids);
        return ResponseEntity.ok("Xóa thành công loại sản phẩm có các ID = " + ids);
    }


}
