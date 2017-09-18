/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataDB;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "edificio", uniqueConstraints={@UniqueConstraint(columnNames={"numeroEd"}), @UniqueConstraint(columnNames={"nombre"})})
@XmlRootElement
@NamedQueries({
    //@NamedQuery(name = "Edificio.findAll", query = "SELECT e FROM Edificio e ORDER BY E.numeroEd ASC"),
    @NamedQuery(name = "Edificio.findAll", query = "SELECT e FROM Edificio e WHERE e.oculto =:oculto ORDER BY E.numeroEd ASC"),
    @NamedQuery(name = "Edificio.findByNumeroEd", query = "SELECT e FROM Edificio e WHERE e.numeroEd = :numeroEd"),
    @NamedQuery(name = "Edificio.findByNombre", query = "SELECT e FROM Edificio e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "Edificio.findByActivo", query = "SELECT e FROM Edificio e WHERE e.activo = :activo ORDER BY E.numeroEd ASC"),
    @NamedQuery(name = "Edificio.findByIdEdificio", query = "SELECT e FROM Edificio e WHERE e.idEdificio = :idEdificio"),
    @NamedQuery(name = "Edificio.findByOculto", query = "SELECT e FROM Edificio e WHERE e.oculto = :oculto AND e.activo = :activo ORDER BY E.numeroEd ASC")})
public class Edificio implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Basic(optional = false)
    @Column(name = "oculto")
    private boolean oculto;
    
    @Basic(optional = false)
    @Column(name = "numeroEd")
    private String numeroEd;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "activo")
    private boolean activo;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idEdificio")
    private Integer idEdificio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEdificio")
    private List<Salon> salonList;

    public Edificio() {
    }

    public Edificio(Integer idEdificio) {
        this.idEdificio = idEdificio;
    }

    public Edificio(Integer idEdificio, String numeroEd, String nombre, boolean activo) {
        this.idEdificio = idEdificio;
        this.numeroEd = numeroEd;
        this.nombre = nombre;
        this.activo = activo;
    }

    public String getNumeroEd() {
        return numeroEd;
    }

    public void setNumeroEd(String numeroEd) {
        this.numeroEd = numeroEd;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Integer getIdEdificio() {
        return idEdificio;
    }

    public void setIdEdificio(Integer idEdificio) {
        this.idEdificio = idEdificio;
    }
    
    public void setOculto(boolean oculto){
        this.oculto = oculto;
    }
    
    public boolean getOculto(){
        return oculto;
    }

    @XmlTransient
    public List<Salon> getSalonList() {
        return salonList;
    }

    public void setSalonList(List<Salon> salonList) {
        this.salonList = salonList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEdificio != null ? idEdificio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Edificio)) {
            return false;
        }
        Edificio other = (Edificio) object;
        if ((this.idEdificio == null && other.idEdificio != null) || (this.idEdificio != null && !this.idEdificio.equals(other.idEdificio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(numeroEd);
    }
    
}
