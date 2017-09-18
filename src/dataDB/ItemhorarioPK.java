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
public class ItemhorarioPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "dia")
    private String dia;
    @Basic(optional = false)
    @Column(name = "horaInicio")
    private int horaInicio;
    @Basic(optional = false)
    @Column(name = "idProg")
    private int idProg;
    @Basic(optional = false)
    @Column(name = "numeroGrupo")
    private int numeroGrupo;
    @Basic(optional = false)
    @Column(name = "idAsignatura")
    private int idAsignatura;

    public ItemhorarioPK() {
    }

    public ItemhorarioPK(String dia, int horaInicio, int idProg, int numeroGrupo, int idAsignatura) {
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.idProg = idProg;
        this.numeroGrupo = numeroGrupo;
        this.idAsignatura = idAsignatura;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public int getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(int horaInicio) {
        this.horaInicio = horaInicio;
    }

    public int getIdProg() {
        return idProg;
    }

    public void setIdProg(int idProg) {
        this.idProg = idProg;
    }

    public int getNumeroGrupo() {
        return numeroGrupo;
    }

    public void setNumeroGrupo(int numeroGrupo) {
        this.numeroGrupo = numeroGrupo;
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
        hash += (dia != null ? dia.hashCode() : 0);
        hash += (int) horaInicio;
        hash += (int) idProg;
        hash += (int) numeroGrupo;
        hash += (int) idAsignatura;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItemhorarioPK)) {
            return false;
        }
        ItemhorarioPK other = (ItemhorarioPK) object;
        if ((this.dia == null && other.dia != null) || (this.dia != null && !this.dia.equals(other.dia))) {
            return false;
        }
        if (this.horaInicio != other.horaInicio) {
            return false;
        }
        if (this.idProg != other.idProg) {
            return false;
        }
        if (this.numeroGrupo != other.numeroGrupo) {
            return false;
        }
        if (this.idAsignatura != other.idAsignatura) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data2.ItemhorarioPK[ dia=" + dia + ", horaInicio=" + horaInicio + ", idProg=" + idProg + ", numeroGrupo=" + numeroGrupo + ", idAsignatura=" + idAsignatura + " ]";
    }
    
}
