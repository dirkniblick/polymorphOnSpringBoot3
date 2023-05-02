package com.example.hibernatepolymorph.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BasicRepository<E> extends JpaRepository<E, Long> {

}
