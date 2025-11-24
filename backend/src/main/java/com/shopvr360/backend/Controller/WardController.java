package com.shopvr360.backend.Controller;

import com.shopvr360.backend.Service.WardService;
import com.shopvr360.backend.entity.TableDonhang;
import com.shopvr360.backend.entity.TableQuan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/wards")
public class WardController {

    @Autowired
    private WardService wardService;

    @GetMapping
    public List<TableQuan> findAllWard(){
        return wardService.getAllWard();
    }

    @GetMapping("/view")
    public List<TableQuan> getAllProductsOnView() {
        return wardService.getAllQuanOnView();
    }

    @PutMapping("/{id}/toggle-active")
    public ResponseEntity<?> toggleActive(@PathVariable int id) {
        wardService.toggleActive(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<TableQuan> createQuan(@RequestBody Map<String, Object> payload) {
        Integer idCat = (Integer) payload.get("idCat");
        String ten = (String) payload.get("ten");
        Boolean hienthi = payload.get("hienthi") != null ? (Boolean) payload.get("hienthi") : true;

        TableQuan quan = wardService.createQuan(idCat, ten, hienthi);
        return ResponseEntity.ok(quan);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TableQuan> updateQuan(@PathVariable Integer id, @RequestBody Map<String, Object> payload) {
        Integer idCat = (Integer) payload.get("idCat");
        String ten = (String) payload.get("ten");
        Boolean hienthi = payload.get("hienthi") != null ? (Boolean) payload.get("hienthi") : null;

        TableQuan quan = wardService.updateQuan(id, idCat , ten, hienthi);
        return ResponseEntity.ok(quan);
    }

    @GetMapping("/tinh/{idCat}")
    public ResponseEntity<List<TableQuan>> getAllByTinh(@PathVariable Integer idCat) {
        return ResponseEntity.ok(wardService.getAllByTinh(idCat));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteNew(@PathVariable Long id) throws IOException {
        wardService.deleteProduct(id);
        return ResponseEntity.ok("Xóa thành công loại sản phẩm có ID = " + id);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteMultiple(@RequestBody List<Integer> ids) throws IOException {
        wardService.deleteMultiple(ids);
        return ResponseEntity.ok("Xóa thành công loại sản phẩm có các ID = " + ids);
    }




}
