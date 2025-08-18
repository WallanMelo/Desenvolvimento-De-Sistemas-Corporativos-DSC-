package com.mycompany.dsc.service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.dsc.dataAccess.AluguelBD;
import com.mycompany.dsc.entities.Aluguel;
import com.mycompany.dsc.entities.Cliente;
import com.mycompany.dsc.entities.Veiculo;
import com.mycompany.dsc.exception.ResourceNotFoundException;
import com.mycompany.dsc.repository.AluguelRepository;
import com.mycompany.dsc.repository.ClienteRepository;
import com.mycompany.dsc.repository.VeiculoRepository;

@Service
public class AluguelService {

    @Autowired
    private AluguelRepository aluguelRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private VeiculoRepository veiculoRepository;

    public AluguelBD realizarAluguel(AluguelBD aluguelBD) {
        //faz a busca das entidades atraves dos id
        Cliente cliente = clienteRepository.findById(aluguelBD.getIdCliente())
            .orElseThrow(() -> new ResourceNotFoundException("Cliente Busvado não encontrado"));
            
        Veiculo veiculo = veiculoRepository.findById(aluguelBD.getIdVeiculo())
            .orElseThrow(() -> new ResourceNotFoundException("Veículo Busvado não encontrado"));

        //Checa a disponibilidae do veiculo q será alugado
        if (!veiculo.getStatus().equals("Disponível")) {
            throw new IllegalStateException("Veículo Busvado não está disponível para aluguel");
        }
        
        // Criando umaa entidade do tipo Aluguel
        Aluguel aluguel = new Aluguel();
        aluguel.setCliente(cliente);
        aluguel.setVeiculo(veiculo);
        aluguel.setDataInicio(aluguelBD.getDataInicio());
        aluguel.setDataFim(aluguelBD.getDataFim());
        aluguel.setStatus("Ativo");
        
        // Calcular valor do aluguel do veiculo
        aluguel.setValorTotal(calcularValorTotal(veiculo, aluguelBD.getDataInicio(), aluguelBD.getDataFim()));

        // Atualiza o status do veículo após o aluguel e salva no repositório
        veiculo.setStatus("Alugado");
        veiculoRepository.save(veiculo);
        
        // salva o aluguel e retorna com sendo umo DTO(a pas ta dataAccess)
        Aluguel aluguelSalvo = aluguelRepository.save(aluguel);
            return new AluguelBD(aluguelSalvo);
        }

        //função para calcular o valor total do aluguel formula = valorDiaria * dias(dataFim - dataInicio)
        private Double calcularValorTotal(Veiculo veiculo, LocalDate dataInicio, LocalDate dataFim) {
            long dias = ChronoUnit.DAYS.between(dataInicio, dataFim);
            return veiculo.getValorDiaria() * dias;
        }
}