package com.shopvr360.backend.repository;

import com.shopvr360.backend.entity.TableChitietdonhang;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonhangChitietRepository extends JpaRepository<TableChitietdonhang,Integer> {
    void deleteAllByIdDonhang(Integer idDonhang);

}
