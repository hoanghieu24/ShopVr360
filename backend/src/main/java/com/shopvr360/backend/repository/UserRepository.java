package com.shopvr360.backend.repository;

import com.shopvr360.backend.entity.TableUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<TableUser, Long> {
    Optional<TableUser> findByUsername(String username);
    Optional<TableUser> findByEmail(String email);
    Optional<TableUser> findByUserToken(String token);
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
    boolean existsByDienthoai (String dienthoai);


}
