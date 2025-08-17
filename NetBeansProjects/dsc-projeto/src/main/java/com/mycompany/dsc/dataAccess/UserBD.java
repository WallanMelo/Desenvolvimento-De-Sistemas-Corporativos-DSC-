package com.mycompany.dsc.dataAccess;
import  org.springframework.beans.BeanUtils;

import com.mycompany.dsc.entities.Users;
public class UserBD {
//========== ATRIBUTOS =======================================================================
    private int id;
    private String nome;
    private String cpf;
    private String login;
    private String senha;
    private String funcao;//Adm - Atendente - MEcanico
    private Double salario;
//========== CONSTRUTORES ====================================================================
    public UserBD() {}
    public UserBD(Users userEntitie) {
        BeanUtils.copyProperties(userEntitie, this);
    }
    public UserBD(int id, String nome, String cpf, String login, String senha, String funcao, Double salario) {
        setId(id);
        setNome(nome);
        setCpf(cpf);
        setLogin(login);
        setSenha(senha);
        setFuncao(funcao);
        setSalario(salario);    
    }
//========== GETTERS E SETTERS ==============================================================
    //ID
    public void setId(int id){this.id = id;}
    public int getId(){return this.id;}
    
    //Nome
    public void setNome(String nome){this.nome = nome;}
    public String getNome(){return this.nome;}
   
    //CPF
    public void setCpf(String cpf){this.cpf = cpf;}
    public String getCpf(){return this.cpf;}
    
    //Login
    public void setLogin(String login){this.login = login;}
    public String getLogin(){return this.login;}
    
    //Senha
    public void setSenha(String senha){this.senha = senha;}
    public String getSenha(){return this.senha;}    

    //Função
    public void setFuncao(String funcao){this.funcao = funcao;}
    public String getFuncao(){return this.funcao;}

    //Salario    
    public void setSalario(Double salario){this.salario = salario;}
    public Double getSalario(){return this.salario;}
}