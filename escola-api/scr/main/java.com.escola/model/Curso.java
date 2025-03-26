package com.escola.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "curso")
public class Curso extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_curso")
    public Long id;
    
    public String nome;
    
    public String descricao;
    
    @Column(name = "carga_horaria")
    public Integer cargaHoraria;
}