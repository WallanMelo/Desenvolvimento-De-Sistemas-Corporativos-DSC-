package com.mycompany.dsc.entities;
import org.springframework.beans.BeanUtils;

import com.mycompany.dsc.dataAccess.VeiculoBD;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "veiculo")
public class Veiculo {
//========== ATRIBUTOS =======================================================================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int idVeiculo;

    @Column(nullable = false, unique = true)
    protected String placa; 

    /*@Column(nullable = false, unique = true)
    protected String chassi;*/ 

    @Column(nullable = false)
    protected String modelo;

    @Column(nullable = false)
    protected String marca;// Exemplo: Ford - Chevolet - BMW - ...

    @Column(nullable = false)
    protected String cor;// Exemplo: preto - vermehlho - azul - ...

    @Column(nullable = false)
    protected String ano;// Exemplo: 2020 - 2021 - 2022 - ...

    @Column(nullable = false)   
    protected String tipoVeiculo;// SUV - Esportivo - ...

    @Column(nullable = false, precision = 10, scale = 2)
    protected Double valorDiaria;

    @Column(nullable = false)
    protected String status;// Disponivel - Alugado - Manutencao
//========== CONSTRUTORES =======================================================================
    public Veiculo() {}
    public Veiculo(int idVeiculo, String placa, String chassi, String modelo, String marca, String cor, String ano, String tipoVeiculo, Double valorDiaria, String status) {
        setIdVeiculo(idVeiculo);
        setPlaca(placa);
        //setChassi(chassi);
        setModelo(modelo);
        setMarca(marca);
        setCor(cor);    
        setAno(ano);
        setTipoVeiculo(tipoVeiculo);
        setValorDiaria(valorDiaria);
        setStatus(status);
    }
    public Veiculo(VeiculoBD veiculoBD) {
        BeanUtils.copyProperties(veiculoBD, this);
    }
//========== GETTERS AND SETTERS =======================================================================
    //ID
    public void setIdVeiculo(int idVeiculo) { this.idVeiculo = idVeiculo; }
    public int getIdVeiculo() { return this.idVeiculo; }
    
    //Placa
    public void setPlaca(String placa) { this.placa = placa; }
    public String getPlaca() { return this.placa; }
    
    //Chassi
    /*public void setChassi(String chassi) { this.chassi = chassi; }
    public String getChassi() { return this.chassi; }*/

    //Modelo
    public void setModelo(String modelo) { this.modelo = modelo; }
    public String getModelo() { return this.modelo; }
    
    //Marca
    public void setMarca(String marca) { this.marca = marca; }
    public String getMarca() { return this.marca; }
    
    //Cor
    public void setCor(String cor) { this.cor = cor; }
    public String getCor() { return this.cor; }
    
    //Ano
    public void setAno(String ano) { this.ano = ano; }
    public String getAno() { return this.ano; }
    
    //TipoVeiculo
    public void setTipoVeiculo(String tipoVeiculo) { this.tipoVeiculo = tipoVeiculo; }
    public String getTipoVeiculo() { return this.tipoVeiculo; }
    
    //ValorDiaria
    public void setValorDiaria(Double valorDiaria) { this.valorDiaria = valorDiaria; }
    public Double getValorDiaria() { return this.valorDiaria; }
    
    //Status
    public void setStatus(String status) { this.status = status; }
    public String getStatus() { return this.status; }
}