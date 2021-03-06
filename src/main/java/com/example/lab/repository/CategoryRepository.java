package com.example.lab.repository;

import com.example.lab.domain.Category;
import com.example.lab.domain.Item;
import com.example.lab.repository.base.SimpleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * @author Arman
 * Date: 4/06/21
 * Time: 1:14 AM
 **/
@Repository
public interface CategoryRepository extends SimpleRepository<Category> {
}
