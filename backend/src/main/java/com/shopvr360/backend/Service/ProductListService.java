package com.shopvr360.backend.Service;

import com.shopvr360.backend.dto.ProductListRequest;
import com.shopvr360.backend.dto.ProductRequest;
import com.shopvr360.backend.entity.TableProduct;
import com.shopvr360.backend.entity.TableProductList;
import com.shopvr360.backend.repository.ProductListRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

@Service
public class ProductListService {

    @Autowired
    private ProductListRepository productListRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Lấy tất cả danh mục
    public List<TableProductList> getAllProductList() {
        return productListRepository.findAll();
    }

    // Lấy các danh mục hiển thị (hienthi = 1)
    public List<TableProductList> getAllProductListByHienThi() {
        return productListRepository.findAllByHienthi(1);
    }

    // Bật / tắt trạng thái hiển thị hoặc nổi bật
    public void toggleField(Long id, String type) {
        TableProductList n = productListRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product List not found"));

        switch (type.toLowerCase()) {
            case "active":
                n.setHienthi(n.getHienthi() == 1 ? 0 : 1);
                break;
            case "outstanding":
                n.setNoibat(n.getNoibat() == 1 ? 0 : 1);
                break;
            default:
                throw new IllegalArgumentException("Invalid toggle type: " + type);
        }

        productListRepository.save(n);
    }

    // Tạo mới ProductList
    public TableProductList createProductList(ProductListRequest request) throws IOException {
        TableProductList productList = modelMapper.map(request, TableProductList.class);
        productList.setId(null);

        if (request.getPhoto() != null) productList.setPhoto(request.getPhoto());
        if (request.getStt() == null) productList.setStt(1);

        setDefaultFields(productList, request);

        return productListRepository.save(productList);
    }

    public TableProductList updateProduct(Long id, ProductListRequest request) throws IOException {
        TableProductList product = productListRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));


        modelMapper.map(request, product);

        setDefaultFields(product, request);

        if (request.getPhoto() != null) product.setThumb((request.getPhoto()));

        product.setNgaysua((int) (System.currentTimeMillis() / 1000));

        return productListRepository.save(product);
    }

    public void deleteProduct(Long id) {
        if (!productListRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy sản phẩm có id = " + id);
        }
        productListRepository.deleteById(id);
    }

    public void deleteMultiple(List<Long> ids) {
        List<TableProductList> products = productListRepository.findAllById(ids);
        if (products.isEmpty()) {
            throw new RuntimeException("Không tìm thấy sản phẩm nào trong danh sách.");
        }
        productListRepository.deleteAll(products);
    }

    private void setDefaultFields(TableProductList list, ProductListRequest req) {

        list.setType("product");
        list.setNoibat(0);
        list.setNeww(0);
        list.setMotaVi("");
        list.setMotaEn("");
        list.setMotaCn("");
        list.setLink("#");
        list.setTitle(req.getTenVi());
        list.setTenVi(req.getTenVi());
        list.setTenEn(req.getTenEn());
        list.setNameVi(req.getTenVi());
        list.setNameEn(req.getTenEn());

        list.setDescription(req.getDescription() != null ? req.getDescription() : "");
        list.setKeywords(req.getKeywords() != null ? req.getKeywords() : "");
        list.setHienthi(1);
        list.setNgaytao((int) Instant.now().getEpochSecond());
        list.setNgaysua((int) Instant.now().getEpochSecond());

        if (list.getThumb() == null) list.setThumb("");
        if (list.getThumb2() == null) list.setThumb2("");
        if (list.getPhoto2() == null) list.setPhoto2("");
    }


    public List<TableProductList> getAllProductListByType(String tyPe) {
        return productListRepository.findByType(tyPe);
    }
}
