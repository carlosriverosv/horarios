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
public class GrupoPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "numeroGrupo")
    private int numeroGrupo;
    @Basic(optional = false)
    @Column(name = "idProg")
    private int idProg;
    @Basic(optional = false)
    @Column(name = "idAsignatura")
    private int idAsignatura;

    public GrupoPK() {
    }

    public GrupoPK(int numeroGrupo, int idProg, int idAsignatura) {
        this.numeroGrupo = numeroGrupo;
        this.idProg = idProg;
        this.idAsignatura = idAsignatura;
    }

    public int getNumeroGrupo() {
        return numeroGrupo;
    }

    public void setNumeroGrupo(int numeroGrupo) {
        this.numeroGrupo = numeroGrupo;
    }

    public int getIdProg() {
        return idProg;
    }

    public void setIdProg(int idProg) {
        this.idProg = idProg;
    }

    public int getIdAsignatura() {
        return idAsignatura;
    }

    public void setIdAsignatura(int idAsignatura) {
        this.idAsignatura = idAsignatura;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) numeroGrupo;
        hash += (int) idProg;
        hash += (int) idAsignatura;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrupoPK)) {
            return false;
        }
        GrupoPK other = (GrupoPK) object;
        if (this.numeroGrupo != other.numeroGrupo) {
            return false;
        }
        if (this.idProg != other.idProg) {
            return false;
        }
        if (this.idAsignatura != other.idAsignatura) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(numeroGrupo);
    }
    
}
