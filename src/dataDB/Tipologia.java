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
@Table(name = "tipologia", uniqueConstraints={@UniqueConstraint(columnNames={"tipologia"})})
@XmlRootElement
@NamedQueries({
    //@NamedQuery(name = "Tipologia.findAll", query = "SELECT t FROM Tipologia t"),
    @NamedQuery(name = "Tipologia.findAll", query = "SELECT t FROM Tipologia t WHERE t.oculto =:oculto"),
    @NamedQuery(name = "Tipologia.findByTipologia", query = "SELECT t FROM Tipologia t WHERE t.tipologia = :tipologia"),
    @NamedQuery(name = "Tipologia.findByIdTipologia", query = "SELECT t FROM Tipologia t WHERE t.idTipologia = :idTipologia")})
public class Tipologia implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Basic(optional = false)
    @Column(name = "oculto")
    private boolean oculto;
    
    @Basic(optional = false)
    @Column(name = "tipologia")
    private String tipologia;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTipologia")
    private Integer idTipologia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipologia")
    private List<Asignaturaenplanestudio> asignaturaenplanestudioList;

    public Tipologia() {
    }

    public Tipologia(Integer idTipologia) {
        this.idTipologia = idTipologia;
    }

    public Tipologia(Integer idTipologia, String tipologia) {
        this.idTipologia = idTipologia;
        this.tipologia = tipologia;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public Integer getIdTipologia() {
        return idTipologia;
    }

    public void setIdTipologia(Integer idTipologia) {
        this.idTipologia = idTipologia;
    }
    
    public void setOculto(boolean oculto){
        this.oculto = oculto;
    }
    
    public boolean getOculto(){
        return oculto;
    }

    @XmlTransient
    public List<Asignaturaenplanestudio> getAsignaturaenplanestudioList() {
        return asignaturaenplanestudioList;
    }

    public void setAsignaturaenplanestudioList(List<Asignaturaenplanestudio> asignaturaenplanestudioList) {
        this.asignaturaenplanestudioList = asignaturaenplanestudioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipologia != null ? idTipologia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipologia)) {
            return false;
        }
        Tipologia other = (Tipologia) object;
        if ((this.idTipologia == null && other.idTipologia != null) || (this.idTipologia != null && !this.idTipologia.equals(other.idTipologia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return tipologia;
    }
    
}
