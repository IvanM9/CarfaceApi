package com.app.tddt4iots.entities;

import com.app.tddt4iots.enums.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Entity
@Table(name = "guardia")
@Data
@NoArgsConstructor
public class Guardia {

    @Id
    private Long id;

    @Column(name = "empresa", nullable = false, unique = false, length = 30)
    private String empresa;

    @Column(name = "estado", nullable = false)
    private Boolean estado;
    @Column(name = "fecha_incio", nullable = false, unique = false, length = 30)
    private Date fecha_inicio;
    @Column(name = "fecha_fin", nullable = false, unique = false, length = 30)
    private Date fecha_fin;


    @MapsId
    @OneToOne
    @JsonBackReference
    private Usuario usuario;

    @OneToMany(mappedBy = "guardia", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Registro> registros;


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
        if (!(object instanceof Guardia)) {
            return false;
        }
        Guardia other = (Guardia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.app.tddt4iots.entities.Guardia[ id=" + id + " ]";
    }
}    
