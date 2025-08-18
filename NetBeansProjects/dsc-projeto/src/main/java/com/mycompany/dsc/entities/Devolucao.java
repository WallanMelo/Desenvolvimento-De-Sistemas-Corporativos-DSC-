package com.mycompany.dsc.entities;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.BeanUtils;

import com.mycompany.dsc.dataAccess.DevolucaoBD;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "devolucao")
public class Devolucao {
//========== ATRIBUTOS ================================================================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int idDevolucao;
    
    @Column(name = "data_devolucao", nullable = false)
    protected LocalDate dataDevolucao;
    
    @Column(name = "observacoes", nullable = true, length = 255)
    protected String observacoes;

    @Column(name = "valor_multa", nullable = true, precision = 10, scale = 2)
    protected Double valorMulta;//No caso de atrasar a devolução
//========== CONTRUTORES ================================================================
    public Devolucao() {}
    public Devolucao(int idDevolucao, LocalDate dataDevolucao, String observacoes, Double valorMulta) {
        setIdDevolucao(idDevolucao);
        setDataDevolucao(dataDevolucao);
        setObservacoes(observacoes);
        setValorMulta(valorMulta);
    }
    public Devolucao(DevolucaoBD devolucaoBD) {
        BeanUtils.copyProperties(devolucaoBD, this);
    }

//========== METODOS ================================================================
    // Verifica se houve atraso na devolução se tiver retorna true
    public boolean houveAtraso(LocalDate dataPrevista) {
        return dataDevolucao.isAfter(dataPrevista);
    }
    
    // Calcula a multa baseada no atraso, FORMULA = dias de atraso * valor por dia()
    public Double calcularMulta(LocalDate dataPrevista) {
        double valorPorDia = 25.0; //Valor Fixo por dia de atraso na devolução
        if (houveAtraso(dataPrevista)) {
            long diasAtraso = ChronoUnit.DAYS.between(dataPrevista, dataDevolucao);
            this.valorMulta = diasAtraso * valorPorDia;
            return this.valorMulta;
        } else 
            return  this.valorMulta = 0.0;
    }
    
    //Adiciona observações sobre o veiculo devolvido
    public String adicionarObservacao(String obs) {
        if (this.observacoes == null || this.observacoes.isBlank()) {
            this.observacoes = obs;
        } else {
            this.observacoes = String.format("%s | %s", this.observacoes, obs);
        }
        return this.observacoes;
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