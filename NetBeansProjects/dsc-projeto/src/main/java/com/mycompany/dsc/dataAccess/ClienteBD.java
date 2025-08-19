package com.mycompany.dsc.dataAccess;
import java.time.LocalDate;

import org.springframework.beans.BeanUtils;

import com.mycompany.dsc.entities.Cliente;


public class ClienteBD {
//========== ATRIBUTOS ================================================================
    protected int idCliente;
    protected String nome;
    protected String cpf;
    protected LocalDate dataNascimento;
    protected String cnh;
    protected String telefone;
    protected String endereco;//Cidade, Estado, Bairro, Rua, Numero, Complemento 
//========== CONSTRUTORES ================================================================
    public ClienteBD() {}
    public ClienteBD(int idCliente, String nome, String cpf, LocalDate dataNascimento, String cnh, String telefone, String endereco) {
        setIdCliente(idCliente);
        setNome(nome);
        setCpf(cpf);
        setDataNascimento(dataNascimento);
        setCnh(cnh);
        setTelefone(telefone);
        setEndereco(endereco);
    }
    public ClienteBD(Cliente clienteEntitie) {
        BeanUtils.copyProperties(clienteEntitie, this);
    }
//========== GETTERS AND SETTERS ================================================================
    //ID
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
    public int getIdCliente() { return this.idCliente; }

    //Nome
    public void setNome(String nome) { this.nome = nome; }
    public String getNome() { return this.nome; }
    
    //CPF
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getCpf() { return this.cpf; }
    
    //Data Nascimento
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }
    public LocalDate getDataNascimento() { return this.dataNascimento; }   

    //CNH
    public void setCnh(String cnh) { this.cnh = cnh; }
    public String getCnh() { return this.cnh; }

    //Contato
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getTelefone() { return this.telefone; }

    //Endereco
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public String getEndereco() { return this.endereco; }
}