package com.escola.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "matriz_curricular")
public class MatrizCurricular extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_matriz")
    public Long id;
    
    @ManyToOne
    @JoinColumn(name = "id_curso", nullable = false)
    public Curso curso;
    
    @ManyToOne
    @JoinColumn(name = "id_disciplina", nullable = false)
    public Disciplina disciplina;
    
    public Integer semestre;
    
    public Boolean obrigatoria = true;
}