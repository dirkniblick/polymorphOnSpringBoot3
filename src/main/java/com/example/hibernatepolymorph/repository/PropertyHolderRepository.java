package com.example.hibernatepolymorph.repository;

import com.example.hibernatepolymorph.entity.PropertyHolder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyHolderRepository extends JpaRepository<PropertyHolder, Long> {

}
