package com.mycompany.dsc.entities;

import org.springframework.beans.BeanUtils;

import com.mycompany.dsc.dataAccess.UserBD;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "users")
public class Users {
//=========== ATRIBUTOS ============================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     
    protected int idUser;

    @Column (nullable = true)
    protected String nome;

    @Column (nullable = true, unique = true)
    protected String cpf;
    
    @Column (nullable = true, unique = true)
    protected String login;
    
    @Column (nullable = true)
    protected String senha;
    
    @Column (nullable = false)
    protected String funcao;//Adm - Atenbdente ou Mecanico
    
    @Column (nullable = true, precision = 10, scale = 2)
    protected Double salario;

//=========== CONSTRUTORES ============================    
    public Users() {}
    public Users(UserBD userBD){ //Recebe um data acess object  e coipa suas props para a entitie
        BeanUtils.copyProperties(userBD, this);
    }
    public Users(int idUser, String nome, String cpf, String login, String senha, String funcao, Double salario) {
        setIdUSer(idUser);
        setNome(nome);
        setCpf(cpf);
        setLogin(login);
        setSenha(senha);
        setFuncao(funcao);
        setSalario(salario);
    }
    
//============ GETTERS E SETTERS ============================
    //idUser
    public void setIdUSer(int idUser){this.idUser = idUser;}
    public int getIdUser(){return this.idUser; }

    //Nome
    public void setNome(String nome){this.nome = nome;}
    public String getnome(){return this.nome;}

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