package com.mycompany.dsc.dataAccess;
import java.time.LocalDate;

import org.springframework.beans.BeanUtils;

import com.mycompany.dsc.entities.Devolucao;

public class DevolucaoBD {
//========== ATRIBUTOS ================================================================
    protected int idDevolucao;
    protected LocalDate dataDevolucao;
    protected String observacoes;
    protected Double valorMulta;//No caso de atrasar a devolução
//========== CONTRUTORES ================================================================
    public DevolucaoBD() {}
    public DevolucaoBD(int idDevolucao, LocalDate dataDevolucao, String observacoes, Double valorMulta) {
        setIdDevolucao(idDevolucao);
        setDataDevolucao(dataDevolucao);
        setObservacoes(observacoes);
        setValorMulta(valorMulta);
    }
    public DevolucaoBD(Devolucao devolucaoEntitie) {
        BeanUtils.copyProperties(devolucaoEntitie, this);
    }
//========== GETTERS AND SETTERS ================================================================
    //ID
    public void setIdDevolucao(int idDevolucao) { this.idDevolucao = idDevolucao; }
    public int getIdDevolucao() { return this.idDevolucao; }

    //Data Devolução
    public void setDataDevolucao(LocalDate dataDevolucao) { this.dataDevolucao = dataDevolucao; }
    public LocalDate getDataDevolucao() { return this.dataDevolucao; }
    
    //Obs
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
    public String getObservacoes() { return this.observacoes; }

    //Valor Multa
    public void setValorMulta(Double valorMulta) { this.valorMulta = valorMulta; }
    public Double getValorMulta() { return this.valorMulta; }
}
