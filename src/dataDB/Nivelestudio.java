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
@Table(name = "nivelestudio", uniqueConstraints={@UniqueConstraint(columnNames={"nivelEstudio"})})
@XmlRootElement
@NamedQueries({
    //@NamedQuery(name = "Nivelestudio.findAll", query = "SELECT n FROM Nivelestudio n"),
    @NamedQuery(name = "Nivelestudio.findAll", query = "SELECT n FROM Nivelestudio n WHERE n.oculto =:oculto" ),
    @NamedQuery(name = "Nivelestudio.findByNivelEstudio", query = "SELECT n FROM Nivelestudio n WHERE n.nivelEstudio = :nivelEstudio"),
    @NamedQuery(name = "Nivelestudio.findByIdNivelEstudio", query = "SELECT n FROM Nivelestudio n WHERE n.idNivelEstudio = :idNivelEstudio")})
public class Nivelestudio implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Basic(optional = false)
    @Column(name = "oculto")
    private boolean oculto;
    
    @Basic(optional = false)
    @Column(name = "nivelEstudio")
    private String nivelEstudio;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idNivelEstudio")
    private Integer idNivelEstudio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idNivelEstudio")
    private List<Asignatura> asignaturaList;

    public Nivelestudio() {
    }

    public Nivelestudio(Integer idNivelEstudio) {
        this.idNivelEstudio = idNivelEstudio;
    }

    public Nivelestudio(Integer idNivelEstudio, String nivelEstudio) {
        this.idNivelEstudio = idNivelEstudio;
        this.nivelEstudio = nivelEstudio;
    }

    public String getNivelEstudio() {
        return nivelEstudio;
    }

    public void setNivelEstudio(String nivelEstudio) {
        this.nivelEstudio = nivelEstudio;
    }

    public Integer getIdNivelEstudio() {
        return idNivelEstudio;
    }

    public void setIdNivelEstudio(Integer idNivelEstudio) {
        this.idNivelEstudio = idNivelEstudio;
    }
    
    public void setOculto(boolean oculto){
        this.oculto = oculto;
    }
    
    public boolean getOculto(){
        return oculto;
    }

    @XmlTransient
    public List<Asignatura> getAsignaturaList() {
        return asignaturaList;
    }

    public void setAsignaturaList(List<Asignatura> asignaturaList) {
        this.asignaturaList = asignaturaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNivelEstudio != null ? idNivelEstudio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Nivelestudio)) {
            return false;
        }
        Nivelestudio other = (Nivelestudio) object;
        if ((this.idNivelEstudio == null && other.idNivelEstudio != null) || (this.idNivelEstudio != null && !this.idNivelEstudio.equals(other.idNivelEstudio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nivelEstudio;
    }
    
}
