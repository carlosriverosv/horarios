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
@Table(name = "tipocontrato", uniqueConstraints={@UniqueConstraint(columnNames={"tipoContrato"})})
@XmlRootElement
@NamedQueries({
    //@NamedQuery(name = "Tipocontrato.findAll", query = "SELECT t FROM Tipocontrato t"),
    @NamedQuery(name = "Tipocontrato.findAll", query = "SELECT t FROM Tipocontrato t WHERE t.oculto =:oculto ORDER BY T.tipoContrato ASC"),
    @NamedQuery(name = "Tipocontrato.findByTipoContrato", query = "SELECT t FROM Tipocontrato t WHERE t.tipoContrato = :tipoContrato"),
    @NamedQuery(name = "Tipocontrato.findByIdTipoContrato", query = "SELECT t FROM Tipocontrato t WHERE t.idTipoContrato = :idTipoContrato")})
public class Tipocontrato implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Basic(optional = false)
    @Column(name = "oculto")
    private boolean oculto;
    
    @Basic(optional = false)
    @Column(name = "tipoContrato")
    private String tipoContrato;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTipoContrato")
    private Integer idTipoContrato;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoContrato")
    private List<Docente> docenteList;

    public Tipocontrato() {
    }

    public Tipocontrato(Integer idTipoContrato) {
        this.idTipoContrato = idTipoContrato;
    }

    public Tipocontrato(Integer idTipoContrato, String tipoContrato) {
        this.idTipoContrato = idTipoContrato;
        this.tipoContrato = tipoContrato;
    }

    public String getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(String tipoContrato) {
        this.tipoContrato = tipoContrato;
    }
    
    public void setOculto(boolean oculto){
        this.oculto = oculto;
    }
    
    public boolean getOculto(){
        return oculto;
    }

    public Integer getIdTipoContrato() {
        return idTipoContrato;
    }

    public void setIdTipoContrato(Integer idTipoContrato) {
        this.idTipoContrato = idTipoContrato;
    }

    @XmlTransient
    public List<Docente> getDocenteList() {
        return docenteList;
    }

    public void setDocenteList(List<Docente> docenteList) {
        this.docenteList = docenteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoContrato != null ? idTipoContrato.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipocontrato)) {
            return false;
        }
        Tipocontrato other = (Tipocontrato) object;
        if ((this.idTipoContrato == null && other.idTipoContrato != null) || (this.idTipoContrato != null && !this.idTipoContrato.equals(other.idTipoContrato))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return tipoContrato;
    }
    
}
