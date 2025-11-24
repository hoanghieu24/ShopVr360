package com.shopvr360.backend.repository;

import com.shopvr360.backend.entity.TableProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<TableProduct,Long> {
    List<TableProduct> findAllByHienthi(int hienthi);
}
