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
@Table(name = "planestudio", uniqueConstraints={@UniqueConstraint(columnNames={"codigoPlan"}), @UniqueConstraint(columnNames={"nombrePlan"})})
@XmlRootElement
@NamedQueries({
    //@NamedQuery(name = "Planestudio.findAll", query = "SELECT p FROM Planestudio p"),
    @NamedQuery(name = "Planestudio.findAll", query = "SELECT p FROM Planestudio p WHERE p.oculto =:oculto ORDER BY P.nombrePlan ASC"),
    @NamedQuery(name = "Planestudio.findByCodigoPlan", query = "SELECT p FROM Planestudio p WHERE p.codigoPlan = :codigoPlan"),
    @NamedQuery(name = "Planestudio.findByNombrePlan", query = "SELECT p FROM Planestudio p WHERE p.nombrePlan = :nombrePlan"),
    @NamedQuery(name = "Planestudio.findByAnno", query = "SELECT p FROM Planestudio p WHERE p.anno = :anno"),
    @NamedQuery(name = "Planestudio.findByIdPlan", query = "SELECT p FROM Planestudio p WHERE p.idPlan = :idPlan")})
public class Planestudio implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Basic(optional = false)
    @Column(name = "oculto")
    private boolean oculto;
    
    @Basic(optional = false)
    @Column(name = "codigoPlan")
    private String codigoPlan;
    @Basic(optional = false)
    @Column(name = "nombrePlan")
    private String nombrePlan;
    @Basic(optional = false)
    @Column(name = "anno")
    private int anno;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPlan")
    private Integer idPlan;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "planestudio")
    private List<Asignaturaenplanestudio> asignaturaenplanestudioList;

    public Planestudio() {
    }

    public Planestudio(Integer idPlan) {
        this.idPlan = idPlan;
    }

    public Planestudio(Integer idPlan, String codigoPlan, String nombrePlan, int anno) {
        this.idPlan = idPlan;
        this.codigoPlan = codigoPlan;
        this.nombrePlan = nombrePlan;
        this.anno = anno;
    }

    public String getCodigoPlan() {
        return codigoPlan;
    }

    public void setCodigoPlan(String codigoPlan) {
        this.codigoPlan = codigoPlan;
    }

    public String getNombrePlan() {
        return nombrePlan;
    }

    public void setNombrePlan(String nombrePlan) {
        this.nombrePlan = nombrePlan;
    }

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }
    
    public void setOculto(boolean oculto){
        this.oculto = oculto;
    }
    
    public boolean getOculto(){
        return oculto;
    }

    public Integer getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(Integer idPlan) {
        this.idPlan = idPlan;
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
        hash += (idPlan != null ? idPlan.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Planestudio)) {
            return false;
        }
        Planestudio other = (Planestudio) object;
        if ((this.idPlan == null && other.idPlan != null) || (this.idPlan != null && !this.idPlan.equals(other.idPlan))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(codigoPlan) +" - "+ this.nombrePlan;
    }
    
}
