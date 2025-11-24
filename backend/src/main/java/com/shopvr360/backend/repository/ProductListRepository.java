package com.shopvr360.backend.repository;

import com.shopvr360.backend.entity.TableBaiviet;
import com.shopvr360.backend.entity.TableProductList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductListRepository extends JpaRepository<TableProductList,Long> {
    List<TableProductList> findAllByHienthi(Integer hienthi);
    List<TableProductList> findByType(String type);
}
