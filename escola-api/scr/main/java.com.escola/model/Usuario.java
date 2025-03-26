package com.escola.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    public Long id;
    
    @Column(unique = true, nullable = false)
    public String email;
    
    @Column(name = "senha_hash", nullable = false)
    public String senhaHash;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public TipoUsuario tipo;
    
    @Column(name = "id_relacion")
    public Long idRelacion;
    
    @Column(name = "data_criacao", updatable = false)
    public java.time.Instant dataCriacao;
    
    public enum TipoUsuario {
        ADMIN, COORDENADOR, PROFESSOR, ALUNO
    }
}