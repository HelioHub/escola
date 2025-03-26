package com.escola.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "disciplina")
public class Disciplina extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_disciplina")
    public Long id;
    
    public String nome;
    
    @Column(name = "carga_horaria", nullable = false)
    public Integer cargaHoraria;
    
    public String ementa;
    
    @ManyToOne
    @JoinColumn(name = "id_professor")
    public Professor professor;
}