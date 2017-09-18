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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "salon", uniqueConstraints={@UniqueConstraint(columnNames={"numeroSalon","idEdificio"})})
@XmlRootElement
@NamedQueries({
    //@NamedQuery(name = "Salon.findAll", query = "SELECT s FROM Salon s ORDER BY S.numeroSalon ASC"),
    @NamedQuery(name = "Salon.findAll", query = "SELECT s FROM Salon s WHERE s.oculto =:oculto ORDER BY S.numeroSalon ASC"),
    @NamedQuery(name = "Salon.findByNumeroSalon", query = "SELECT s FROM Salon s WHERE s.numeroSalon = :numeroSalon"),
    @NamedQuery(name = "Salon.findByCapacidad", query = "SELECT s FROM Salon s WHERE s.capacidad = :capacidad"),
    @NamedQuery(name = "Salon.findByObservaciones", query = "SELECT s FROM Salon s WHERE s.observaciones = :observaciones"),
    @NamedQuery(name = "Salon.findByActivo", query = "SELECT s FROM Salon s WHERE s.activo = :activo ORDER BY S.numeroSalon ASC"),
    @NamedQuery(name = "Salon.findByIdSalon", query = "SELECT s FROM Salon s WHERE s.idSalon = :idSalon"),
    //@NamedQuery(name = "Salon.findByIdEdificio", query = "SELECT s FROM Salon s WHERE s.idEdificio = :idEdificio ORDER BY S.numeroSalon ASC"),
    @NamedQuery(name = "Salon.findByIdEdificio", query = "SELECT s FROM Salon s WHERE s.idEdificio = :idEdificio AND s.oculto =:oculto ORDER BY S.numeroSalon ASC"),
    @NamedQuery(name = "Salon.findByOculto", query = "SELECT s FROM Salon s WHERE s.oculto = :oculto AND s.activo = :activo ORDER BY S.numeroSalon ASC")})
public class Salon implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Basic(optional = false)
    @Column(name = "oculto")
    private boolean oculto;
    
    @Basic(optional = false)
    @Column(name = "numeroSalon")
    private String numeroSalon;
    @Basic(optional = false)
    @Column(name = "capacidad")
    private int capacidad;
    @Column(name = "observaciones")
    private String observaciones;
    @Basic(optional = false)
    @Column(name = "activo")
    private boolean activo;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idSalon")
    private Integer idSalon;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSalon")
    private List<Itemhorario> itemhorarioList;
    @JoinColumn(name = "idEdificio", referencedColumnName = "idEdificio")
    @ManyToOne(optional = false)
    private Edificio idEdificio;
    public static final String COLUMNAS_ARCHIVO = "Número de salón;Número de edificio;Capacidad;Observaciones;Activo/No activo";

    public Salon() {
    }

    public Salon(Integer idSalon) {
        this.idSalon = idSalon;
    }

    public Salon(Integer idSalon, String numeroSalon, int capacidad, boolean activo) {
        this.idSalon = idSalon;
        this.numeroSalon = numeroSalon;
        this.capacidad = capacidad;
        this.activo = activo;
    }

    public String getNumeroSalon() {
        return numeroSalon;
    }

    public void setNumeroSalon(String numeroSalon) {
        this.numeroSalon = numeroSalon;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    public void setOculto(boolean oculto){
        this.oculto = oculto;
    }
    
    public boolean getOculto(){
        return oculto;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Integer getIdSalon() {
        return idSalon;
    }

    public void setIdSalon(Integer idSalon) {
        this.idSalon = idSalon;
    }

    @XmlTransient
    public List<Itemhorario> getItemhorarioList() {
        return itemhorarioList;
    }

    public void setItemhorarioList(List<Itemhorario> itemhorarioList) {
        this.itemhorarioList = itemhorarioList;
    }

    public Edificio getIdEdificio() {
        return idEdificio;
    }

    public void setIdEdificio(Edificio idEdificio) {
        this.idEdificio = idEdificio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSalon != null ? idSalon.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Salon)) {
            return false;
        }
        Salon other = (Salon) object;
        if ((this.idSalon == null && other.idSalon != null) || (this.idSalon != null && !this.idSalon.equals(other.idSalon))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(numeroSalon);
    }
    
}
