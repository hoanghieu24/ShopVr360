package com.shopvr360.backend.repository;

import com.shopvr360.backend.entity.TableBaiviet;
import com.shopvr360.backend.entity.TableProduct;
import com.shopvr360.backend.entity.TableTinh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvinceRepository extends JpaRepository<TableTinh, Long> {
    public TableTinh findByPhivanchuyen(Integer Phivanchuyen);
    List<TableTinh> findAllByHienthi(int hienthi);

;
}
