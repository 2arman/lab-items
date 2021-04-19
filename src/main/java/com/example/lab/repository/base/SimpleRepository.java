package com.example.lab.repository.base;

import com.example.lab.domain.base.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Arman
 * Date: 4/3/21
 * Time: 12:51 PM
 **/
public interface SimpleRepository<T extends BaseEntity> extends JpaRepository<T, Long> {


}
