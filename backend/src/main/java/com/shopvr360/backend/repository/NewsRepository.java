package com.shopvr360.backend.repository;

import com.shopvr360.backend.entity.TableBaiviet;
import com.shopvr360.backend.entity.TableProductList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepository extends JpaRepository<TableBaiviet,Integer> {
    List<TableBaiviet> findAllByTypeAndHienthi(String type, int hienthi);
    List<TableBaiviet> findAllByHienthi(int hienthi);



}
