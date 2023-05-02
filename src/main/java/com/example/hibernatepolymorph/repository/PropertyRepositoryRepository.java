package com.example.hibernatepolymorph.repository;

import com.example.hibernatepolymorph.entity.PropertyRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepositoryRepository extends JpaRepository<PropertyRepository, Long> {

}
