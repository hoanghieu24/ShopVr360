package com.shopvr360.backend.Controller;

import com.shopvr360.backend.Service.OderService;
import com.shopvr360.backend.dto.OderRequest;
import com.shopvr360.backend.entity.TableDonhang;
import com.shopvr360.backend.entity.TableTinh;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/oders")
public class OderController {

    @Autowired
    private OderService orderService;

    @GetMapping
    public List<TableDonhang> GetOders(){
        return orderService.getAllOder();
    }

    @GetMapping("/view/{idUser}")
    public List<TableDonhang> getAllProductsOnView(@PathVariable Integer idUser) {
        return orderService.getAllDonHangByIdUser(idUser);
    }
    @PostMapping
    public ResponseEntity<TableDonhang> createOrder(@RequestBody OderRequest request) {
        TableDonhang order = orderService.createDonhang(request);
        return ResponseEntity.ok(order);
    }
    @PutMapping("/{id}")
    public ResponseEntity<TableDonhang> updateOrder(
            @PathVariable Integer id,
            @RequestBody OderRequest request
    ) {
        return ResponseEntity.ok(orderService.updateDonhang(id, request));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteNew(@PathVariable Long id) throws IOException {
        orderService.deleteProduct(id);
        return ResponseEntity.ok("Xóa thành công loại sản phẩm có ID = " + id);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteMultiple(@RequestBody List<Integer> ids) throws IOException {
        orderService.deleteMultiple(ids);
        return ResponseEntity.ok("Xóa thành công loại sản phẩm có các ID = " + ids);
    }


}
