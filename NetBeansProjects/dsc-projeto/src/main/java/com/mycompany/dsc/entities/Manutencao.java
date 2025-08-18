package com.mycompany.dsc.entities;
import com.mycompany.dsc.dataAccess.ManutencaoBD;

import jakarta.persistence.*;
import jakarta.persistence.Table;

import org.springframework.beans.BeanUtils;

@Entity
@Table(name="manutencao")
public class Manutencao {
//========== ATRIBUTOS ================================================================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int idManutencao;
    
    @Column (name = "descricao", nullable = false, length = 255)
    protected String descricao;
    
    @Column (name = "custo", nullable = false)
    protected Double custo;

    @Column (name = "data_inicio", nullable = false)
    protected String dataInicio;//Inicio da Manutenção 

    @Column (name = "data_fim", nullable = true)
    protected String dataFim;//Fim da Manutenção 

    @Column (name = "status", nullable = false, length = 50)
    protected String status;//Andamento - Concluída - Cancelada
//========== CONTRUTORES ================================================================
    public Manutencao() {}
    public Manutencao(int idManutencao, String descricao, Double custo, String dataInicio, String dataFim, String status) {
        setIdManutencao(idManutencao);
        setDescricao(descricao);
        setCusto(custo);
        setDataInicio(dataInicio);
        setDataFim(dataFim);
        setStatus(status);
    }
    public Manutencao(ManutencaoBD manutencaoBD) {
        BeanUtils.copyProperties(manutencaoBD, this);
    }
//========== GETTERS AND SETTERS ================================================================
    //ID
    public void setIdManutencao(int idManutencao) { this.idManutencao = idManutencao; } 
    public int getIdManutencao() { return this.idManutencao; }

    //Descricao
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getDescricao() { return this.descricao; }
    
    //Custo
    public void setCusto(Double custo) { this.custo = custo; }
    public Double getCusto() { return this.custo; }

    //Data Inicio
    public void setDataInicio(String dataInicio) { this.dataInicio = dataInicio; }
    public String getDataInicio() { return this.dataInicio; }

    //Data Fim
    public void setDataFim(String dataFim) { this.dataFim = dataFim; }
    public String getDataFim() { return this.dataFim; }

    //Status
    public void setStatus(String status) { this.status = status; }
    public String getStatus() { return this.status; }
}
