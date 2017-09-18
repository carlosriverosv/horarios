/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataDB;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Carlos
 */
@Embeddable
public class AsignaturaenplanestudioPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "idAsignatura")
    private int idAsignatura;
    @Basic(optional = false)
    @Column(name = "idPlanEstudio")
    private int idPlanEstudio;

    public AsignaturaenplanestudioPK() {
    }

    public AsignaturaenplanestudioPK(int idAsignatura, int idPlanEstudio) {
        this.idAsignatura = idAsignatura;
        this.idPlanEstudio = idPlanEstudio;
    }

    public int getIdAsignatura() {
        return idAsignatura;
    }

    public void setIdAsignatura(int idAsignatura) {
        this.idAsignatura = idAsignatura;
    }

    public int getIdPlanEstudio() {
        return idPlanEstudio;
    }

    public void setIdPlanEstudio(int idPlanEstudio) {
        this.idPlanEstudio = idPlanEstudio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idAsignatura;
        hash += (int) idPlanEstudio;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AsignaturaenplanestudioPK)) {
            return false;
        }
        AsignaturaenplanestudioPK other = (AsignaturaenplanestudioPK) object;
        if (this.idAsignatura != other.idAsignatura) {
            return false;
        }
        if (this.idPlanEstudio != other.idPlanEstudio) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data2.AsignaturaenplanestudioPK[ idAsignatura=" + idAsignatura + ", idPlanEstudio=" + idPlanEstudio + " ]";
    }
    
}
