package com.mycompany.dsc.entities;
import org.springframework.beans.BeanUtils;

import com.mycompany.dsc.dataAccess.AluguelBD;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "aluguel")
public class Aluguel {
//=========== ATRIBUTOS ============================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int idAluguel;

    @ManyToOne // Relacionamento de MUITOS para UM com os Cliente
    @JoinColumn(name = "idCliente", nullable = false) //Mapea qual coluna no BD representa a chave estrangeira FK 
    protected Cliente cliente; // FK chave estrangeira

    @ManyToOne
    @JoinColumn(name = "idVeiculo", nullable = false)
    protected Veiculo veiculo;// FK Chave estrangeira
    
    @Column (nullable = false)  
    protected String dataInicio;
    @Column (nullable = false)
    protected String dataFim;
    @Column (nullable = false, precision = 10, scale = 2)
    protected Double valorTotal;
    @Column (nullable = false)
    protected String status; //Ativo - Finalizado - Cancelado
//=========== CONSTRUTORES ============================
    public Aluguel() {}
    public Aluguel(int idAluguel, int idCliente, int idVeiculo, String dataInicio, String dataFim, Double valorTotal, String status) {
        setIdAluguel(idAluguel);
        setCliente(cliente);
        setVeiculo(veiculo);
        setDataInicio(dataInicio);
        setDataFim(dataFim);
        setValorTotal(valorTotal);
        setStatus(status);
    }   
    public Aluguel(AluguelBD aluguelBD) {
        BeanUtils.copyProperties(aluguelBD, this);
    }
//=========== GETTERS E SETTERS ============================
    //ID Aluguel
    public void setIdAluguel(int idAluguel){this.idAluguel = idAluguel;}
    public int getIdAluguel(){return this.idAluguel; }
    //ID Cliente
    public void setCliente(Cliente cliente){this.cliente = cliente;}
    public Cliente getCliente(){return this.cliente; }
    //ID Veiculo
    public void setVeiculo(Veiculo veiculo){this.veiculo = veiculo;}
    public Veiculo getVeiculo(){return this.veiculo; }
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