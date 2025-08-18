package com.mycompany.dsc.dataAccess;
import org.springframework.beans.BeanUtils;

import com.mycompany.dsc.entities.Veiculo;

public class VeiculoBD {
//========== ATRIBUTOS =======================================================================
    private int idVeiculo;
    private String placa;   
    //private String chassi;
    private String modelo;
    private String marca;   
    private String cor;
    private String ano;
    private String tipoVeiculo;
    private Double valorDiaria;
    private String status;

//========== CONSTRUTORES =======================================================================
    public VeiculoBD() {}
    public VeiculoBD(Veiculo veiculoEntitie) {
        BeanUtils.copyProperties(veiculoEntitie, this);
    }   
    public VeiculoBD(int idVeiculo, String placa, String modelo, String marca, String cor, String ano, String tipoVeiculo, Double valorDiaria, String status) {
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

//========== GETTERS AND SETTERS =======================================================================
    //ID
    public void setIdVeiculo(int idVeiculo) { this.idVeiculo = idVeiculo; }
    public int getIdVeiculo() { return this.idVeiculo; }
    
    //Placa
    public void setPlaca(String placa) { this.placa = placa; }
    public String getPlaca() { return this.placa; }
    
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
