package com.example.hibernatepolymorph.repository;

import com.example.hibernatepolymorph.entity.StringProperty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StringPropertyRepository extends JpaRepository<StringProperty, Long> {

}
