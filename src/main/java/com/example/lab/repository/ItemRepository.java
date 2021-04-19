package com.example.lab.repository;

import com.example.lab.domain.Item;
import com.example.lab.repository.base.SimpleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Arman
 * Date: 4/3/21
 * Time: 1:14 AM
 **/
@Repository
public interface ItemRepository extends SimpleRepository<Item> {
    Page<Item> findAllByCategory_IdOrderByIdAsc(Long categoryId, Pageable pageable);

}
