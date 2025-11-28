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
            @PathVariable Long  id,
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
    public ResponseEntity<?> deleteMultiple(@RequestBody List<Long > ids) throws IOException {
        orderService.deleteMultiple(ids);
        return ResponseEntity.ok("Xóa thành công loại sản phẩm có các ID = " + ids);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getOrderDetail(@PathVariable Long  id) {
        return ResponseEntity.ok(orderService.getOrderDetail(id));
    }

    @PutMapping("/pay/{id}")
    public ResponseEntity<?> payOrder(@PathVariable Long  id) {
        orderService.payOrder(id);
        return ResponseEntity.ok("Đã thanh toán thành công!");
    }

    @PutMapping("status/{id}/{status}")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long  id , @PathVariable Integer status) {
        orderService.statusOrder(id,status);
        return ResponseEntity.ok("Đã thay đổi trạng thái thành công");

    }




}
