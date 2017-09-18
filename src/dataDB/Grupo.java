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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "grupo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Grupo.findAll", query = "SELECT g FROM Grupo g"),
    @NamedQuery(name = "Grupo.findByNumeroGrupo", query = "SELECT g FROM Grupo g WHERE g.grupoPK.numeroGrupo = :numeroGrupo"),
    @NamedQuery(name = "Grupo.findByInscritos", query = "SELECT g FROM Grupo g WHERE g.inscritos = :inscritos"),
    //@NamedQuery(name = "Grupo.findByIdProg", query = "SELECT g FROM Grupo g WHERE g.grupoPK.idProg = :idProg"),
    @NamedQuery(name = "Grupo.findByIdProg", query = "SELECT g FROM Grupo g WHERE g.grupoPK.idProg = :idProg AND g.oculto =:oculto"),
    //@NamedQuery(name = "Grupo.findByIdAsignatura", query = "SELECT g FROM Grupo g WHERE g.grupoPK.idAsignatura = :idAsignatura")})
    @NamedQuery(name = "Grupo.findByIdAsignatura", query = "SELECT g FROM Grupo g WHERE g.grupoPK.idAsignatura = :idAsignatura AND g.oculto =:oculto")})
public class Grupo implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GrupoPK grupoPK;
    
    @Basic(optional = false)
    @Column(name = "oculto")
    private boolean oculto;
    
    @Basic(optional = false)
    @Column(name = "inscritos")
    private int inscritos;
    @JoinColumn(name = "idAsignatura", referencedColumnName = "idAsignatura", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Asignatura asignatura;
    @JoinColumn(name = "idProg", referencedColumnName = "idProg", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Progacademica progacademica;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grupo")
    private List<Itemhorario> itemhorarioList;

    public Grupo() {
    }

    public Grupo(GrupoPK grupoPK) {
        this.grupoPK = grupoPK;
    }

    public Grupo(GrupoPK grupoPK, int inscritos) {
        this.grupoPK = grupoPK;
        this.inscritos = inscritos;
    }

    public Grupo(int numeroGrupo, int idProg, int idAsignatura) {
        this.grupoPK = new GrupoPK(numeroGrupo, idProg, idAsignatura);
    }

    public GrupoPK getGrupoPK() {
        return grupoPK;
    }

    public void setGrupoPK(GrupoPK grupoPK) {
        this.grupoPK = grupoPK;
    }

    public int getInscritos() {
        return inscritos;
    }

    public void setInscritos(int inscritos) {
        this.inscritos = inscritos;
    }
    
    public void setOculto(boolean oculto){
        this.oculto = oculto;
    }
    
    public boolean getOculto(){
        return oculto;
    }

    public Asignatura getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    public Progacademica getProgacademica() {
        return progacademica;
    }

    public void setProgacademica(Progacademica progacademica) {
        this.progacademica = progacademica;
    }

    @XmlTransient
    public List<Itemhorario> getItemhorarioList() {
        return itemhorarioList;
    }

    public void setItemhorarioList(List<Itemhorario> itemhorarioList) {
        this.itemhorarioList = itemhorarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (grupoPK != null ? grupoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Grupo)) {
            return false;
        }
        Grupo other = (Grupo) object;
        if ((this.grupoPK == null && other.grupoPK != null) || (this.grupoPK != null && !this.grupoPK.equals(other.grupoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data2.Grupo[ grupoPK=" + grupoPK + " ]";
    }
    
}
