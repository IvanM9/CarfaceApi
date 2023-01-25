package com.app.tddt4iots.entities;

import com.app.tddt4iots.enums.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.ArrayList;
import java.util.ArrayList;

@Entity
@Table(name = "chofer")
@Data
@NoArgsConstructor
public class Chofer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "chofer")
    private ArrayList<Vehiculo> vehiculo;

    @JoinColumn(name = "chofer", referencedColumnName = "id")
    @OneToOne
    private Usuario usuario;

    @OneToMany(mappedBy = "chofer")
    private ArrayList<Fotochofer> fotochofer;

    @OneToMany(mappedBy = "chofer")
    private ArrayList<Registro> registro;


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
}    
