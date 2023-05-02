package com.example.hibernatepolymorph.repository;

import com.example.hibernatepolymorph.entity.IntegerProperty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IntegerPropertyRepository extends JpaRepository<IntegerProperty, Long> {

}
