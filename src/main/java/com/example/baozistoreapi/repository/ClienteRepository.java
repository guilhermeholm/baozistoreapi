package com.example.baozistoreapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.baozistoreapi.model.Cliente;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
