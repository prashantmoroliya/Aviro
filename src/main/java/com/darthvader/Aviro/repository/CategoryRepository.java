package com.darthvader.Aviro.repository;

import com.darthvader.Aviro.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
