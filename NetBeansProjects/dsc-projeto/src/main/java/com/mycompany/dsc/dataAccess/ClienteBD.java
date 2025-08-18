package com.mycompany.dsc.dataAccess;
import org.springframework.beans.BeanUtils;

import com.mycompany.dsc.entities.Cliente;


public class ClienteBD {
//========== ATRIBUTOS ================================================================
    protected int idCliente;
    protected String nome;
    protected String cpf;
    protected String dataNascimento;
    protected String cnh;
    protected String contato;//Telefone ou Celular ou Email
    protected String endereco;//Cidade, Estado, Bairro, Rua, Numero, Complemento 
//========== CONSTRUTORES ================================================================
    public ClienteBD() {}
    public ClienteBD(int idCliente, String nome, String cpf, String dataNascimento, String cnh, String contato, String endereco) {
        setIdCliente(idCliente);
        setNome(nome);
        setCpf(cpf);
        setDataNascimento(dataNascimento);
        setCnh(cnh);
        setContato(contato);
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