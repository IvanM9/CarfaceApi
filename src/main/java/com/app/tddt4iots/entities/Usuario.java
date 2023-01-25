package com.app.tddt4iots.entities;

import com.app.tddt4iots.enums.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Entity
@Table(name = "Usuario")
@Data
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nombres", nullable = true, unique = false, length = 30)
    private String nombres;

    @Column(name = "apellidos", nullable = true, unique = false, length = 30)
    private String apellidos;

    @Column(name = "cedula", nullable = true, unique = false, length = 30)
    private String cedula;

    @Column(name = "telefono", nullable = false, unique = false, length = 30)
    private String telefono;

    @Column(name = "direccion", nullable = false, unique = false, length = 30)
    private String direccion;

    @Column(name = "correo", nullable = true, unique = false, length = 30)
    private String correo;

    @Column(name = "clave", nullable = true, unique = false, length = 30)
    private String clave;

    @Column(name = "fechacreacion", nullable = true, unique = false)
    private Date fechacreacion;

    @Column(name = "fechamodificacion", nullable = true, unique = false, length = 30)
    private Date fechamodificacion;

    @JoinColumn(name = "id", referencedColumnName = "id")
    @OneToOne
    private Chofer chofer;

    @JoinColumn(name = "id", referencedColumnName = "id") //TODO: Cambiar el joincolum al objeto destino
    @OneToOne
    private Guardia guardia;


    @Column()
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
