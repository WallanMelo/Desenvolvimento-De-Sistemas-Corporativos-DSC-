package com.mycompany.dsc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycompany.dsc.entities.Veiculo;

public interface  VeiculoRepository extends JpaRepository<Veiculo, Integer>{
    
}