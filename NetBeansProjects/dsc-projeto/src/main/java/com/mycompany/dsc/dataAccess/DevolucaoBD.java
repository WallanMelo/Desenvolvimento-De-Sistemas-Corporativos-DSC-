package com.mycompany.dsc.dataAccess;
import com.mycompany.dsc.entities.Devolucao;
import org.springframework.beans.BeanUtils;

public class DevolucaoBD {
//========== ATRIBUTOS ================================================================
    protected int idDevolucao;
    protected String dataDevolucao;
    protected String observacoes;
    protected Double valorMulta;//No caso de atrasar a devolução
//========== CONTRUTORES ================================================================
    public DevolucaoBD() {}
    public DevolucaoBD(int idDevolucao, String dataDevolucao, String observacoes, Double valorMulta) {
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
    public void setDataDevolucao(String dataDevolucao) { this.dataDevolucao = dataDevolucao; }
    public String getDataDevolucao() { return this.dataDevolucao; }
    
    //Obs
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
    public String getObservacoes() { return this.observacoes; }

    //Valor Multa
    public void setValorMulta(Double valorMulta) { this.valorMulta = valorMulta; }
    public Double getValorMulta() { return this.valorMulta; }
}
