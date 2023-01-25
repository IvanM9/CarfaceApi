package com.app.tddt4iots.entities;
 
import com.app.tddt4iots.enums.*;
import lombok.Data;
 import lombok.NoArgsConstructor;
 
import javax.persistence.*;
 import java.util.ArrayList;
import java.util.ArrayList;
import java.util.ArrayList;
 
@Entity
 @Table(name = "Chofer")
 @Data
 @NoArgsConstructor
 public class Chofer {
     
    @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     private Long id;
     
   @OneToMany(mappedBy = "id") 
    private ArrayList<Vehiculo> vehiculo; 
 
   @JoinColumn(name = "id", referencedColumnName = "id") 
   @OneToOne 
    private Usuario usuario; 
 
   @OneToMany(mappedBy = "id") 
    private ArrayList<Fotochofer> fotochofer; 
 
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
