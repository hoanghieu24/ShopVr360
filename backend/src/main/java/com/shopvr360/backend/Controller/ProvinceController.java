package com.shopvr360.backend.Controller;

import com.shopvr360.backend.Service.ProvinceService;
import com.shopvr360.backend.entity.TableProduct;
import com.shopvr360.backend.entity.TableTinh;
import com.shopvr360.backend.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/provinces")

public class ProvinceController {

    private final ProvinceService provinceService;
    @Autowired
    private ProvinceRepository provinceRepository;

    public ProvinceController(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @PostMapping
    public ResponseEntity<TableTinh> createTinh(@RequestBody TableTinh body) {

        String ten = body.getTen();
        Long stt = body.getStt() != null ? body.getStt().longValue() : null;
        Integer phivanchuyen = body.getPhivanchuyen() != null ? body.getPhivanchuyen() : 20000;

        TableTinh tinh = provinceService.createTinhThanh(ten, stt, phivanchuyen);

        return ResponseEntity.ok(tinh);
    }


    @PutMapping("/{id}")
    public TableTinh updateProvince(
            @PathVariable Integer id,
            @RequestBody TableTinh body
    ) {
        // map possible update fields from body (null-safe)
        String ten = body.getTen();
        Integer phivanchuyen = body.getPhivanchuyen();
        Integer stt = body.getStt();
        Integer hienthi = body.getHienthi();
        return provinceService.updateTinhThanh(id, ten, phivanchuyen, stt, hienthi);
    }

    @PutMapping("/{id}/toggle-active")
    public ResponseEntity<?> toggleActive(@PathVariable int id) {
        provinceService.toggleActive(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteNew(@PathVariable Long id) throws IOException {
        provinceService.deleteProduct(id);
        return ResponseEntity.ok("Xóa thành công loại sản phẩm có ID = " + id);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteMultiple(@RequestBody List<Long> ids) throws IOException {
        provinceService.deleteMultiple(ids);
        return ResponseEntity.ok("Xóa thành công loại sản phẩm có các ID = " + ids);
    }





    @GetMapping
    public List<TableTinh> getAll() {
        return provinceService.getAllProvinces();
    }

    @GetMapping("/view")
    public List<TableTinh> getAllProductsOnView() {
        return provinceService.getAllTinhByHienThi();
    }
}
