package com.shopvr360.backend.Controller;

import com.shopvr360.backend.Service.NewsService;
import com.shopvr360.backend.dto.NewsRequest;
import com.shopvr360.backend.dto.ProductListRequest;
import com.shopvr360.backend.entity.TableBaiviet;
import com.shopvr360.backend.entity.TableNewsletter;
import com.shopvr360.backend.entity.TableProductList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

//    @GetMapping
//    public List<TableBaiviet> getAll() {
//        return newsService.getTableBaiviet();
//    }

    @GetMapping
    public List<TableBaiviet> getTintuc(
            @RequestParam(value = "type", required = false) String type)
          {

        if (type == null || type.isEmpty()) {
            return newsService.getTableBaiviet();
        } else {
            return newsService.getTinTuc(type, 1);
        }
    }

    @PostMapping
    public ResponseEntity<?> addNew(@RequestBody NewsRequest newsRequest) throws IOException {
        TableBaiviet tableBaiviet = newsService.createBaiViet(newsRequest);
        return ResponseEntity.ok(tableBaiviet);
    }

    @PutMapping
    public ResponseEntity<?> updateNew(@RequestBody NewsRequest newsRequest) throws IOException {
        Long id = (long) newsRequest.getId();
        return ResponseEntity.ok(newsService.updateProduct(id, newsRequest));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteNew(@PathVariable Long id) throws IOException {
        newsService.deleteProduct(id);
        return ResponseEntity.ok("Xóa thành công loại sản phẩm có ID = " + id);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteMultiple(@RequestBody List<Integer> ids) throws IOException {
        newsService.deleteMultiple(ids);
        return ResponseEntity.ok("Xóa thành công loại sản phẩm có các ID = " + ids);
    }

    @PutMapping("/{id}/toggle-active")
    public ResponseEntity<?> toggleActive(@PathVariable int id) {
        newsService.toggleActive(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/toggle-outstanding")
    public ResponseEntity<?> toggleOutstanding(@PathVariable int id) {
        newsService.toggleOutstanding(id);
        return ResponseEntity.ok().build();
    }




}
