package com.app.tddt4iots.entities;

import com.app.tddt4iots.enums.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.Date;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vehiculo")
@Data
@NoArgsConstructor
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_vehiculo", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoVehiculo tipoVehiculo;

    @Column(name = "placa", nullable = false, unique = true, length = 15)
    private String placa;

    @Column(name = "marca", nullable = false, unique = false, length = 30)
    private String marca;

    @Column(name = "modelo", nullable = true, unique = false, length = 30)
    private String modelo;

    @Column(name = "color", nullable = false, unique = false, length = 30)
    private String color;

    @Column(name = "anio", nullable = true, unique = false, length = 30)
    private String anio;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "chofer_id")
    @JsonBackReference
    private Chofer chofer;

    @OneToMany(mappedBy = "vehiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Fotovehiculo> fotosvehiculos;

    @OneToMany(mappedBy = "vehiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Registro> registros;

    @Column(name = "fechacreacion", nullable = false, unique = false, updatable = false)
    private Date fechacreacion;

    @Column(name = "fechamodificacion", nullable = false, unique = false)
    private Date fechamodificacion;


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
        if (!(object instanceof Vehiculo)) {
            return false;
        }
        Vehiculo other = (Vehiculo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.app.tddt4iots.entities.Vehiculo[ id=" + id + " ]";
    }
}    
