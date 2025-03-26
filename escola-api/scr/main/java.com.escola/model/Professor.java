package com.escola.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "professor")
public class Professor extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prof")
    public Long id;
    
    public String nome;
    
    @Column(unique = true, nullable = false)
    public String email;
    
    public String telefone;
    
    @Column(name = "area_atuacao")
    public String areaAtuacao;
}