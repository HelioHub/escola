package com.escola.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "matricula")
public class Matricula extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_matricula")
    public Long id;
    
    @ManyToOne
    @JoinColumn(name = "id_aluno")
    public Aluno aluno;
    
    @ManyToOne
    @JoinColumn(name = "id_curso")
    public Curso curso;
    
    @Column(name = "data_matricula")
    public Instant dataMatricula;
    
    @Enumerated(EnumType.STRING)
    public StatusMatricula status = StatusMatricula.ATIVO;
    
    public enum StatusMatricula {
        ATIVO, INATIVO, CONCLUIDO
    }
}