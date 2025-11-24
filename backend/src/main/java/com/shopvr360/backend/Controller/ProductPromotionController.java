package com.shopvr360.backend.Controller;


import com.shopvr360.backend.Service.ProductPromotionService;
import com.shopvr360.backend.dto.NewsRequest;
import com.shopvr360.backend.entity.TableBaiviet;
import com.shopvr360.backend.entity.TableKhuyenmai;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/productPromotion")
public class ProductPromotionController {

    @Autowired
    private ProductPromotionService productPromotionService;

    @GetMapping
    public List<TableKhuyenmai> getAll(){
        return productPromotionService.getTableKhuyenMai();
    }

//    @PostMapping
//    public ResponseEntity<?> addNew(@RequestBody NewsRequest newsRequest) throws IOException {
//        TableKhuyenmai tableKhuyenmai = newsService.createBaiViet(newsRequest);
//        return ResponseEntity.ok(tableBaiviet);
//    }
}
