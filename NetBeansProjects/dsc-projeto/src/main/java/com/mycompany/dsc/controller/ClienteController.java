package com.mycompany.dsc.controller;
import com.mycompany.dsc.dataAccess.ClienteBD;
import com.mycompany.dsc.entities.Cliente;
import com.mycompany.dsc.exception.ResourceNotFoundException;
import com.mycompany.dsc.service.ClienteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteBD> cadastrarCliente(@RequestBody ClienteBD clienteBD) {
        Cliente cliente = new Cliente();
        BeanUtils.copyProperties(clienteBD, cliente);

        Cliente clienteSalvo = clienteService.cadastrarCliente(cliente);

        ClienteBD responseClienteBD = new ClienteBD(clienteSalvo);
        return new ResponseEntity<>(responseClienteBD, HttpStatus.CREATED);
    }

   
    @GetMapping("/{id}")
    public ResponseEntity<ClienteBD> buscarClientePorId(@PathVariable Integer id) {
        try {
            Cliente cliente = clienteService.buscarClientePorId(id);
            ClienteBD clienteBD = new ClienteBD(cliente);
            return ResponseEntity.ok(clienteBD);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping
    public ResponseEntity<List<ClienteBD>> listarTodosClientes() {
        List<Cliente> clientes = clienteService.listarTodosClientes();
        List<ClienteBD> clientesBD = clientes.stream()
                                            .map(ClienteBD::new)
                                            .collect(Collectors.toList());
        return ResponseEntity.ok(clientesBD);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteBD> editarCliente(@PathVariable Integer id, @RequestBody ClienteBD clienteBD) {
        try {
            Cliente clienteParaAtualizar = new Cliente();
            BeanUtils.copyProperties(clienteBD, clienteParaAtualizar);

            Cliente clienteAtualizado = clienteService.editarCliente(id, clienteParaAtualizar);

            ClienteBD responseClienteBD = new ClienteBD(clienteAtualizado);
            return ResponseEntity.ok(responseClienteBD);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCliente(@PathVariable Integer id) {
        try {
            clienteService.excluirCliente(id);
            return ResponseEntity.noContent().build(); 
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build(); 
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); 
    }
}