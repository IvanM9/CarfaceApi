package com.app.tddt4iots.entities;
 
import com.app.tddt4iots.enums.*;
import jakarta.persistence.*;
import lombok.Data;
 import lombok.NoArgsConstructor;
 

 
@Entity
 @Table(name = "fotovehiculo")
 @Data
 @NoArgsConstructor
 public class Fotovehiculo {
     
    @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     private Long id;
     
    @Column(name = "url", nullable = true, unique = false, length = 30) 
    private String url; 
 
    @Column(name = "nombre", nullable = true, unique = false, length = 30) 
    private String nombre; 
 
    @Column(name = "descripcion", nullable = false, unique = false, length = 30) 
    private String descripcion; 
 
    @Column(name = "fechacreacion", nullable = true, unique = false) 
    private String fechacreacion; 
 
    @Column(name = "fechamodificacion", nullable = true, unique = false) 
    private String fechamodificacion; 
 
   @ManyToOne()
    private Vehiculo vehiculo;
 
    
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
        if (!(object instanceof Fotovehiculo)) {
            return false;
        }
        Fotovehiculo other = (Fotovehiculo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "com.app.tddt4iots.entities.Fotovehiculo[ id=" + id + " ]";
    }
}    
