package com.mycompany.dsc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mycompany.dsc.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
