package com.shopvr360.backend.Service;

import com.shopvr360.backend.entity.TableKhuyenmai;
import com.shopvr360.backend.repository.ProductPromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductPromotionService {

    @Autowired
    private ProductPromotionRepository productRepository;

    public List<TableKhuyenmai> getTableKhuyenMai() {
        return productRepository.findAll();
    }
}
