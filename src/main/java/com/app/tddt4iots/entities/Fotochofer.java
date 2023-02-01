package com.app.tddt4iots.entities;

import com.app.tddt4iots.enums.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Entity
@Table(name = "fotochofer")
@NoArgsConstructor
public class Fotochofer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "url", nullable = false, unique = false)
    private String url;

    @Column(name = "nombre", nullable = false, unique = false, length = 30)
    private String nombre;

    @Column(name = "descripcion", nullable = true, unique = false)
    private String descripcion;

    @ManyToOne()
    @JoinColumn(name = "foto_chofer_id")
    @JsonBackReference
    private Chofer chofer;


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
        if (!(object instanceof Fotochofer)) {
            return false;
        }
        Fotochofer other = (Fotochofer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.app.tddt4iots.entities.Fotochofer[ id=" + id + " ]";
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Chofer getChofer() {
        return chofer;
    }

    public void setChofer(Chofer chofer) {
        this.chofer = chofer;
    }
}
