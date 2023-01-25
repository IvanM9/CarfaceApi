package com.app.tddt4iots.entities;
 
import com.app.tddt4iots.enums.*;
import lombok.Data;
 import lombok.NoArgsConstructor;
 
import javax.persistence.*;
 import java.util.ArrayList;
import java.util.ArrayList;
import java.util.ArrayList;
 
@Entity
 @Table(name = "Vehiculo")
 @Data
 @NoArgsConstructor
 public class Vehiculo {
     
    @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     private Long id;
     
    @Column(name = "placa", nullable = true, unique = false, length = 30) 
    private String placa; 
 
    @Column(name = "marca", nullable = true, unique = false, length = 30) 
    private String marca; 
 
    @Column(name = "modelo", nullable = true, unique = false, length = 30) 
    private String modelo; 
 
    @Column(name = "color", nullable = true, unique = false, length = 30) 
    private String color; 
 
    @Column(name = "anio", nullable = false, unique = false, length = 30) 
    private String anio; 
 
   @OneToMany(mappedBy = "id") 
    private ArrayList<Chofer> chofer; 
 
   @OneToMany(mappedBy = "id") 
    private ArrayList<Fotovehiculo> fotovehiculo; 
 
   @OneToMany(mappedBy = "id") 
    private ArrayList<Registro> registro; 
 
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null ) ? 0 : this.id.hashCode());
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
