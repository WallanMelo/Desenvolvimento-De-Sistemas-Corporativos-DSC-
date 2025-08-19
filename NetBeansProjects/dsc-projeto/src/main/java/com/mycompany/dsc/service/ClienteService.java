package com.mycompany.dsc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.dsc.entities.Cliente;
import com.mycompany.dsc.exception.ResourceNotFoundException;
import com.mycompany.dsc.repository.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    
    public Cliente cadastrarCliente(Cliente cliente) {        
        //checando se ñ existe um client c esse msm cpf o q n pode ter pois o cpf é unique
        if (clienteRepository.findByCpf(cliente.getCpf()).isPresent()) {
            throw new IllegalArgumentException("CPF já cadastrado para outro cliente.");
        }
        return clienteRepository.save(cliente);
    }

    
    public Cliente buscarClientePorId(Integer id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + id));
    }

    //Funcao p listar tds os CLientes
    public List<Cliente> listarTodosClientes() {
        return clienteRepository.findAll();
    }

    //Funcao p edt um clientee  atualizar os seus dados no bd
    public Cliente editarCliente(Integer id, Cliente clienteAtualizado) {
        //Checa se o cliente existe
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente Buscado não encontrado com o ID: " + id));

        //Edita os dados do cliente existente com os dados do que serão atualizados do clienteAtualizado
        clienteExistente.setNome(clienteAtualizado.getNome());
        clienteExistente.setCpf(clienteAtualizado.getCpf());
        clienteExistente.setDataNascimento(clienteAtualizado.getDataNascimento());
        clienteExistente.setCnh(clienteAtualizado.getCnh());
        clienteExistente.setTelefone(clienteAtualizado.getTelefone());
        clienteExistente.setEndereco(clienteAtualizado.getEndereco());
        return clienteRepository.save(clienteExistente);
    }

    //funcao p exlcuir um cliente do BD
    public void excluirCliente(Integer id) {
        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente não encontrado com ID: " + id);
        }
        clienteRepository.deleteById(id);
    }
}