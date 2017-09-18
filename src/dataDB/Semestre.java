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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "semestre")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Semestre.findAll", query = "SELECT s FROM Semestre s ORDER BY S.numSemestre ASC"),
    @NamedQuery(name = "Semestre.findByNumSemestre", query = "SELECT s FROM Semestre s WHERE s.numSemestre = :numSemestre")})
public class Semestre implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "numSemestre")
    private Integer numSemestre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "semestre")
    private List<Asignaturaenplanestudio> asignaturaenplanestudioList;

    public Semestre() {
    }

    public Semestre(Integer numSemestre) {
        this.numSemestre = numSemestre;
    }

    public Integer getNumSemestre() {
        return numSemestre;
    }

    public void setNumSemestre(Integer numSemestre) {
        this.numSemestre = numSemestre;
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
        hash += (numSemestre != null ? numSemestre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Semestre)) {
            return false;
        }
        Semestre other = (Semestre) object;
        if ((this.numSemestre == null && other.numSemestre != null) || (this.numSemestre != null && !this.numSemestre.equals(other.numSemestre))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(numSemestre);
    }
    
}
