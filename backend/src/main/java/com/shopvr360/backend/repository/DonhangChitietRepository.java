package com.shopvr360.backend.repository;

import com.shopvr360.backend.entity.TableChitietdonhang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonhangChitietRepository extends JpaRepository<TableChitietdonhang,Integer> {
    void deleteAllByIdDonhang(Long  idDonhang);
    List<TableChitietdonhang> findByMadonhang(String madonhang);


}
