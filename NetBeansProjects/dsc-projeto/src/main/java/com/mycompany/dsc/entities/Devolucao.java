package com.mycompany.dsc.entities;

import com.mycompany.dsc.dataAccess.DevolucaoBD;
import org.springframework.beans.BeanUtils;
import jakarta.persistence.*;

@Entity
@Table(name = "devolucao")
public class Devolucao {
//========== ATRIBUTOS ================================================================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int idDevolucao;
    
    @Column(name = "data_devolucao", nullable = false)
    protected String dataDevolucao;
    
    @Column(name = "observacoes", nullable = true, length = 255)
    protected String observacoes;

    @Column(name = "valor_multa", nullable = true, precision = 10, scale = 2)
    protected Double valorMulta;//No caso de atrasar a devolução
//========== CONTRUTORES ================================================================
    public Devolucao() {}
    public Devolucao(int idDevolucao, String dataDevolucao, String observacoes, Double valorMulta) {
        setIdDevolucao(idDevolucao);
        setDataDevolucao(dataDevolucao);
        setObservacoes(observacoes);
        setValorMulta(valorMulta);
    }
    public Devolucao(DevolucaoBD devolucaoBD) {
        BeanUtils.copyProperties(devolucaoBD, this);
    }
//========== GETTERS AND SETTERS ================================================================
    //ID
    public void setIdDevolucao(int idDevolucao) { this.idDevolucao = idDevolucao; }
    public int getIdDevolucao() { return this.idDevolucao; }

    //Data Devolução
    public void setDataDevolucao(String dataDevolucao) { this.dataDevolucao = dataDevolucao; }
    public String getDataDevolucao() { return this.dataDevolucao; }
    
    //Obs
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
    public String getObservacoes() { return this.observacoes; }

    //Valor Multa
    public void setValorMulta(Double valorMulta) { this.valorMulta = valorMulta; }
    public Double getValorMulta() { return this.valorMulta; }
}