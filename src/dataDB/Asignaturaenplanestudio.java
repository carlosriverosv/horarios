/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataDB;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "asignaturaenplanestudio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Asignaturaenplanestudio.findAll", query = "SELECT a FROM Asignaturaenplanestudio a"),
    //@NamedQuery(name = "Asignaturaenplanestudio.findByIdAsignatura", query = "SELECT a FROM Asignaturaenplanestudio a WHERE a.asignaturaenplanestudioPK.idAsignatura = :idAsignatura"),
    @NamedQuery(name = "Asignaturaenplanestudio.findByIdAsignatura", query = "SELECT a FROM Asignaturaenplanestudio a WHERE a.asignaturaenplanestudioPK.idAsignatura = :idAsignatura AND a.oculto =:oculto"),
    //@NamedQuery(name = "Asignaturaenplanestudio.findByIdPlanEstudio", query = "SELECT a FROM Asignaturaenplanestudio a WHERE a.asignaturaenplanestudioPK.idPlanEstudio = :idPlanEstudio ORDER BY a.asignatura.nombreAsig ASC"),
    @NamedQuery(name = "Asignaturaenplanestudio.findByIdPlanEstudio", query = "SELECT a FROM Asignaturaenplanestudio a WHERE a.asignaturaenplanestudioPK.idPlanEstudio = :idPlanEstudio AND a.oculto =:oculto ORDER BY a.asignatura.nombreAsig ASC"),
    //@NamedQuery(name = "Asignaturaenplanestudio.findByIdTipologia", query = "SELECT a FROM Asignaturaenplanestudio a WHERE a.idTipologia = :idTipologia"),
    @NamedQuery(name = "Asignaturaenplanestudio.findByIdTipologia", query = "SELECT a FROM Asignaturaenplanestudio a WHERE a.idTipologia = :idTipologia AND a.oculto =:oculto")})

public class Asignaturaenplanestudio implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AsignaturaenplanestudioPK asignaturaenplanestudioPK;
    @JoinColumn(name = "idTipologia", referencedColumnName = "idTipologia")
    @ManyToOne(optional = false)
    private Tipologia idTipologia;
    @JoinColumn(name = "semestre", referencedColumnName = "numSemestre")
    @ManyToOne(optional = false)
    private Semestre semestre;
    @JoinColumn(name = "idPlanEstudio", referencedColumnName = "idPlan", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Planestudio planestudio;
    @JoinColumn(name = "idAsignatura", referencedColumnName = "idAsignatura", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Asignatura asignatura;
    
    @Basic(optional = false)
    @Column(name = "oculto")
    private boolean oculto;
    
    public static final String COLUMNAS_ARCHIVO = "Código;Nombre;Semestre;Tipología";

    public Asignaturaenplanestudio() {
    }

    public Asignaturaenplanestudio(AsignaturaenplanestudioPK asignaturaenplanestudioPK) {
        this.asignaturaenplanestudioPK = asignaturaenplanestudioPK;
    }

    public Asignaturaenplanestudio(int idAsignatura, int idPlanEstudio) {
        this.asignaturaenplanestudioPK = new AsignaturaenplanestudioPK(idAsignatura, idPlanEstudio);
    }

    public AsignaturaenplanestudioPK getAsignaturaenplanestudioPK() {
        return asignaturaenplanestudioPK;
    }

    public void setAsignaturaenplanestudioPK(AsignaturaenplanestudioPK asignaturaenplanestudioPK) {
        this.asignaturaenplanestudioPK = asignaturaenplanestudioPK;
    }

    public Tipologia getIdTipologia() {
        return idTipologia;
    }

    public void setIdTipologia(Tipologia idTipologia) {
        this.idTipologia = idTipologia;
    }

    public Semestre getSemestre() {
        return semestre;
    }

    public void setSemestre(Semestre semestre) {
        this.semestre = semestre;
    }

    public Planestudio getPlanestudio() {
        return planestudio;
    }

    public void setPlanestudio(Planestudio planestudio) {
        this.planestudio = planestudio;
    }

    public Asignatura getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }
    
    public void setOculto(boolean oculto){
        this.oculto = oculto;
    }
    
    public boolean getOculto(){
        return oculto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (asignaturaenplanestudioPK != null ? asignaturaenplanestudioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Asignaturaenplanestudio)) {
            return false;
        }
        Asignaturaenplanestudio other = (Asignaturaenplanestudio) object;
        if ((this.asignaturaenplanestudioPK == null && other.asignaturaenplanestudioPK != null) || (this.asignaturaenplanestudioPK != null && !this.asignaturaenplanestudioPK.equals(other.asignaturaenplanestudioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data2.Asignaturaenplanestudio[ asignaturaenplanestudioPK=" + asignaturaenplanestudioPK + " ]";
    }
    
}
