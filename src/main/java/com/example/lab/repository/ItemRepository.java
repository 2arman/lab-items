package com.example.lab.repository;

import com.example.lab.domain.Item;
import com.example.lab.repository.base.SimpleRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Arman
 * Date: 4/3/21
 * Time: 1:14 AM
 **/
@Repository
public interface ItemRepository extends SimpleRepository<Item> {
}
