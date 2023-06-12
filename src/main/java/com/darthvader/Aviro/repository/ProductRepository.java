package com.darthvader.Aviro.repository;

import com.darthvader.Aviro.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategory_Id(Integer id);
}
