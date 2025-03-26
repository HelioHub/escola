package com.escola.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "aluno")
public class Aluno extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aluno")
    public Long id;
    
    public String nome;
    
    @Column(unique = true, nullable = false)
    public String email;
    
    public String telefone;
    
    @Column(name = "data_nasc")
    public LocalDate dataNascimento;
}