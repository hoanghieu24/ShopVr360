package com.shopvr360.backend.repository;

import com.shopvr360.backend.entity.TableDonhang;
import com.shopvr360.backend.entity.TableQuan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WardRepository extends JpaRepository<TableQuan,Integer> {
    List<TableQuan> findAllByHienthi(Boolean hienthi);
    List<TableQuan> findAllByIdCat(Integer idCat);
}
