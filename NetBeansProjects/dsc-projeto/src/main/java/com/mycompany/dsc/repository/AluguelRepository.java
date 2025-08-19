package com.mycompany.dsc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycompany.dsc.entities.Aluguel;

public interface  AluguelRepository extends JpaRepository<Aluguel, Integer>{
    
}
