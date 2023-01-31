package com.app.tddt4iots.entities;

import com.app.tddt4iots.enums.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chofer")
@NoArgsConstructor
public class Chofer {

    @Id
    private Long id;

    @Column(nullable = false, name = "licencia")
    @Enumerated(EnumType.STRING)
    private TipoLicencia licencia;

    @OneToMany(mappedBy = "chofer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Vehiculo> vehiculo;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Usuario usuario;

    @OneToMany(mappedBy = "chofer",  cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Fotochofer> fotochofer;

    @OneToMany(mappedBy = "chofer",  cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Registro> registro;


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
        if (!(object instanceof Chofer)) {
            return false;
        }
        Chofer other = (Chofer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.app.tddt4iots.entities.Chofer[ id=" + id + " ]";
    }

    public TipoLicencia getLicencia() {
        return licencia;
    }

    public void setLicencia(TipoLicencia licencia) {
        this.licencia = licencia;
    }

    public List<Vehiculo> getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(ArrayList<Vehiculo> vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Fotochofer> getFotochofer() {
        return fotochofer;
    }

    public void setFotochofer(ArrayList<Fotochofer> fotochofer) {
        this.fotochofer = fotochofer;
    }

    public List<Registro> getRegistro() {
        return registro;
    }

    public void setRegistro(ArrayList<Registro> registro) {
        this.registro = registro;
    }

}
