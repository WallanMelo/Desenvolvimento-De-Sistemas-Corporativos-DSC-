package com.mycompany.dsc.dataAccess;
import org.springframework.beans.BeanUtils;

import com.mycompany.dsc.entities.Aluguel;

public class AluguelBD {
//=========== ATRIBUTOS ============================
    protected int idAluguel;
    protected int idCliente; // FK chave estrangeira
    protected int idVeiculo;// FK Chave estrangeira
    protected String dataInicio;
    protected String dataFim;
    protected Double valorTotal;
    protected String status; //Ativo - Finalizado - Cancelado
//=========== CONSTRUTORES ============================
    public AluguelBD() {}
    public AluguelBD(int idAluguel, int idCliente, int idVeiculo, String dataInicio, String dataFim, Double valorTotal, String status) {
        setIdAluguel(idAluguel);
        setIdCliente(idCliente);
        setIdVeiculo(idVeiculo);
    
/*this.idCliente = aluguel.getCliente() != null ? aluguel.getCliente().getIdCliente() : 0;
this.idVeiculo = aluguel.getVeiculo() != null ? aluguel.getVeiculo().getIdVeiculo() : 0;
 */               

        setDataInicio(dataInicio);
        setDataFim(dataFim);
        setValorTotal(valorTotal);
        setStatus(status);
    }   
    public AluguelBD(Aluguel aluguelEntitie) {
        BeanUtils.copyProperties(aluguelEntitie, this);
        this.idAluguel = aluguelEntitie.getIdAluguel();
        this.idCliente = aluguelEntitie.getCliente() != null ? aluguelEntitie.getCliente().getIdCliente() : 0; // Se exiister um cliente ele pega o seu ID
        this.idVeiculo = aluguelEntitie.getVeiculo() != null ? aluguelEntitie.getVeiculo().getIdVeiculo() : 0; // se tiver um veiclo ele pega o seu ID isso evita o NullPointerException pois se n tiver o objeto então retorna 0
        this.dataInicio = aluguelEntitie.getDataInicio();
        this.dataFim = aluguelEntitie.getDataFim();
        this.valorTotal = aluguelEntitie.getValorTotal();
        this.status = aluguelEntitie.getStatus();
    }
//=========== GETTERS E SETTERS ============================
    //ID Aluguel
    public void setIdAluguel(int idAluguel){this.idAluguel = idAluguel;}
    public int getIdAluguel(){return this.idAluguel; }
    //ID Cliente
    public void setIdCliente(int idCliente){this.idCliente = idCliente;}
    public int getIdCliente(){return this.idCliente; }
    //ID Veiculo
    public void setIdVeiculo(int idVeiculo){this.idVeiculo = idVeiculo;}
    public int getIdVeiculo(){return this.idVeiculo; }
    //Data Inicio
    public void setDataInicio(String dataInicio){this.dataInicio = dataInicio;}
    public String getDataInicio(){return this.dataInicio; }
    //Data Fim
    public void setDataFim(String dataFim){this.dataFim = dataFim;}
    public String getDataFim(){return this.dataFim; }
    //Valor Total
    public void setValorTotal(Double valorTotal){this.valorTotal = valorTotal;}
    public Double getValorTotal(){return this.valorTotal; }
    //Status
    public void setStatus(String status){this.status = status;}
    public String getStatus(){return this.status; }    
}
