package com.app.tddt4iots.entities;

import com.app.tddt4iots.enums.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombres", nullable = false, unique = false, length = 30)
    private String nombres;

    @Column(name = "apellidos", nullable = false, unique = false, length = 30)
    private String apellidos;

    @Column(name = "cedula", nullable = false, unique = true, length = 10, updatable = false)
    private String cedula;

    @Column(name = "telefono", nullable = true, unique = false, length = 10)
    private String telefono;

    @Column(name = "direccion", nullable = true, unique = false, length = 30)
    private String direccion;

    @Column(name = "correo", nullable = false, unique = true, length = 50, updatable = false)
    private String correo;

    @Column(name = "clave", nullable = false, unique = false)
    private String clave;

    @Column(name = "fechacreacion", nullable = false, unique = false, updatable = false)
    private Date fechacreacion;

    @Column(name = "fechamodificacion", nullable = false, unique = false)
    private Date fechamodificacion;


    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Chofer chofer;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Guardia guardia;


    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false, updatable = false)
    private Rol rol;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.app.tddt4iots.entities.Usuario[ id=" + id + " ]";
    }
}    
