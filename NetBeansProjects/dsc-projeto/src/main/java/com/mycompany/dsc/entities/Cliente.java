package com.mycompany.dsc.entities;
import org.springframework.beans.BeanUtils;

import com.mycompany.dsc.dataAccess.ClienteBD;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cliente")
public class Cliente {
//========== ATRIBUTOS ================================================================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int idCliente;

    @Column (nullable = false)
    protected String nome;

    @Column (nullable = false, unique = true)
    protected String cpf;

    @Column (nullable = false)
    protected String dataNascimento;

    @Column (nullable = false, unique = true)
    protected String cnh;

    @Column (nullable = false)
    protected String contato;//Telefone ou Celular ou Email
    
    @Column (nullable = false)
    protected String endereco;//Cidade, Estado, Bairro, Rua, Numero, Complemento 
//========== CONSTRUTORES ================================================================
    public Cliente() {}
    public Cliente(int idCliente, String nome, String cpf, String dataNascimento, String cnh, String contato, String endereco) {
        setIdCliente(idCliente);
        setNome(nome);
        setCpf(cpf);
        setDataNascimento(dataNascimento);
        setCnh(cnh);
        setContato(contato);
        setEndereco(endereco);
    }
    public Cliente(ClienteBD clienteBD) {
        BeanUtils.copyProperties(clienteBD, this);
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
    public void setDataNascimento(String dataNascimento) { this.dataNascimento = dataNascimento; }
    public String getDataNascimento() { return this.dataNascimento; }   

    //CNH
    public void setCnh(String cnh) { this.cnh = cnh; }
    public String getCnh() { return this.cnh; }

    //Contato
    public void setContato(String contato) { this.contato = contato; }
    public String getContato() { return this.contato; }

    //Endereco
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public String getEndereco() { return this.endereco; }
}