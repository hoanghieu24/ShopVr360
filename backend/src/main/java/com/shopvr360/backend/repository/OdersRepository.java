package com.shopvr360.backend.repository;

import com.shopvr360.backend.entity.TableDonhang;
import com.shopvr360.backend.entity.TableTinh;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OdersRepository extends JpaRepository<TableDonhang,Integer> {
    List<TableDonhang> findAllByIdUser(Integer idUser);
}
