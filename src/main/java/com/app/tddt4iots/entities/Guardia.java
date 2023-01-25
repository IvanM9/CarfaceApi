package com.app.tddt4iots.entities;

import com.app.tddt4iots.enums.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "guardia")
@Data
@NoArgsConstructor
public class Guardia {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "empresa", nullable = true, unique = false, length = 30)
    private String empresa;


    @JoinColumn(name = "guardia", referencedColumnName = "id")
    @OneToOne
    private Usuario usuario;


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
