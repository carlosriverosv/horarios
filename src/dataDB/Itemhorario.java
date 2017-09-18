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
import javax.persistence.JoinColumns;
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
@Table(name = "itemhorario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Itemhorario.findAll", query = "SELECT i FROM Itemhorario i"),
    @NamedQuery(name = "Itemhorario.findByDia", query = "SELECT i FROM Itemhorario i WHERE i.itemhorarioPK.dia = :dia"),
    @NamedQuery(name = "Itemhorario.findByHoraInicio", query = "SELECT i FROM Itemhorario i WHERE i.itemhorarioPK.horaInicio = :horaInicio"),
    //@NamedQuery(name = "Itemhorario.findByIdProg", query = "SELECT i FROM Itemhorario i WHERE i.itemhorarioPK.idProg = :idProg"),
    @NamedQuery(name = "Itemhorario.findByIdProg", query = "SELECT i FROM Itemhorario i WHERE i.itemhorarioPK.idProg = :idProg AND i.oculto =:oculto"),
    @NamedQuery(name = "Itemhorario.findByNumeroGrupo", query = "SELECT i FROM Itemhorario i WHERE i.itemhorarioPK.numeroGrupo = :numeroGrupo"),
    //@NamedQuery(name = "Itemhorario.findByIdAsignatura", query = "SELECT i FROM Itemhorario i WHERE i.itemhorarioPK.idAsignatura = :idAsignatura"),
    @NamedQuery(name = "Itemhorario.findByIdAsignatura", query = "SELECT i FROM Itemhorario i WHERE i.itemhorarioPK.idAsignatura = :idAsignatura AND i.oculto =:oculto"),
    @NamedQuery(name = "Itemhorario.findByLimiteMax", query = "SELECT i FROM Itemhorario i WHERE i.limiteMax = :limiteMax"),
    @NamedQuery(name = "Itemhorario.findByCuotas", query = "SELECT i FROM Itemhorario i WHERE i.cuotas = :cuotas"),
    @NamedQuery(name = "Itemhorario.findByCapMax", query = "SELECT i FROM Itemhorario i WHERE i.capMax = :capMax"),
    @NamedQuery(name = "Itemhorario.findByCapMin", query = "SELECT i FROM Itemhorario i WHERE i.capMin = :capMin"),
    @NamedQuery(name = "Itemhorario.findByPrincipalOAsistente", query = "SELECT i FROM Itemhorario i WHERE i.principalOAsistente = :principalOAsistente"),
    //@NamedQuery(name = "Itemhorario.findIdProgByDocente", query = "SELECT DISTINCT i.itemhorarioPK.idProg FROM Itemhorario i WHERE i.idDocente.documentoDoc = :documentoDoc"),
    @NamedQuery(name = "Itemhorario.findIdProgByDocente", query = "SELECT DISTINCT i.itemhorarioPK.idProg FROM Itemhorario i WHERE i.idDocente.documentoDoc = :documentoDoc AND i.oculto =:oculto"),
    //@NamedQuery(name = "Itemhorario.findItemsByDocente", query = "SELECT i FROM Itemhorario i WHERE i.idDocente.documentoDoc = :documentoDoc"),
    @NamedQuery(name = "Itemhorario.findItemsByDocente", query = "SELECT i FROM Itemhorario i WHERE i.idDocente.documentoDoc = :documentoDoc AND i.oculto =:oculto"),
    //@NamedQuery(name = "Itemhorario.findIdProgBySalon", query = "SELECT DISTINCT i.itemhorarioPK.idProg FROM Itemhorario i WHERE i.idSalon = :idSalon AND i.idSalon.idEdificio = :idEdificio"),
    @NamedQuery(name = "Itemhorario.findIdProgBySalon", query = "SELECT DISTINCT i.itemhorarioPK.idProg FROM Itemhorario i WHERE i.idSalon = :idSalon AND i.idSalon.idEdificio = :idEdificio AND i.oculto =:oculto"),
    //@NamedQuery(name = "Itemhorario.findItemsBySalon", query = "SELECT i FROM Itemhorario i WHERE i.idSalon = :idSalon AND i.idSalon.idEdificio = :idEdificio"),
    @NamedQuery(name = "Itemhorario.findItemsBySalon", query = "SELECT i FROM Itemhorario i WHERE i.idSalon = :idSalon AND i.idSalon.idEdificio = :idEdificio AND i.oculto =:oculto"),
    //@NamedQuery(name = "Itemhorario.findIdProgByEdi", query = "SELECT DISTINCT i.itemhorarioPK.idProg FROM Itemhorario i WHERE i.idSalon.idEdificio = :idEdificio"),
    @NamedQuery(name = "Itemhorario.findIdProgByEdi", query = "SELECT DISTINCT i.itemhorarioPK.idProg FROM Itemhorario i WHERE i.idSalon.idEdificio = :idEdificio AND i.oculto =:oculto"),
    //@NamedQuery(name = "Itemhorario.findItemsByEdi", query = "SELECT i FROM Itemhorario i WHERE i.idSalon.idEdificio = :idEdificio"),
    @NamedQuery(name = "Itemhorario.findItemsByEdi", query = "SELECT i FROM Itemhorario i WHERE i.idSalon.idEdificio = :idEdificio AND i.oculto =:oculto"),
    //@NamedQuery(name = "Itemhorario.findIdProgByIdAsignatura", query = "SELECT DISTINCT i.itemhorarioPK.idProg FROM Itemhorario i WHERE i.itemhorarioPK.idAsignatura = :idAsignatura")})
    @NamedQuery(name = "Itemhorario.findIdProgByIdAsignatura", query = "SELECT DISTINCT i.itemhorarioPK.idProg FROM Itemhorario i WHERE i.itemhorarioPK.idAsignatura = :idAsignatura AND i.oculto = :oculto")})
    
public class Itemhorario implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ItemhorarioPK itemhorarioPK;
    
    @Basic(optional = false)
    @Column(name = "oculto")
    private boolean oculto;
     
    @Basic(optional = false)
    @Column(name = "limiteMax")
    private int limiteMax;
    @Basic(optional = false)
    @Column(name = "cuotas")
    private int cuotas;
    @Basic(optional = false)
    @Column(name = "capMax")
    private int capMax;
    @Basic(optional = false)
    @Column(name = "capMin")
    private int capMin;
    @Basic(optional = false)
    @Column(name = "principalOAsistente")
    private String principalOAsistente;
    @JoinColumn(name = "idSalon", referencedColumnName = "idSalon")
    @ManyToOne(optional = false)
    private Salon idSalon;
    @JoinColumns({
        @JoinColumn(name = "idProg", referencedColumnName = "idProg", insertable = false, updatable = false),
        @JoinColumn(name = "numeroGrupo", referencedColumnName = "numeroGrupo", insertable = false, updatable = false),
        @JoinColumn(name = "idAsignatura", referencedColumnName = "idAsignatura", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Grupo grupo;
    @JoinColumn(name = "idDocente", referencedColumnName = "idDocente")
    @ManyToOne(optional = false)
    private Docente idDocente;
    @JoinColumn(name = "horaInicio", referencedColumnName = "horaInicio", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Horainicio horainicio;
    @JoinColumn(name = "dia", referencedColumnName = "dia", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Dia dia1;

    public Itemhorario() {
    }

    public Itemhorario(ItemhorarioPK itemhorarioPK) {
        this.itemhorarioPK = itemhorarioPK;
    }

    public Itemhorario(ItemhorarioPK itemhorarioPK, int limiteMax, int cuotas, int capMax, int capMin, String principalOAsistente) {
        this.itemhorarioPK = itemhorarioPK;
        this.limiteMax = limiteMax;
        this.cuotas = cuotas;
        this.capMax = capMax;
        this.capMin = capMin;
        this.principalOAsistente = principalOAsistente;
    }

    public Itemhorario(String dia, int horaInicio, int idProg, int numeroGrupo, int idAsignatura) {
        this.itemhorarioPK = new ItemhorarioPK(dia, horaInicio, idProg, numeroGrupo, idAsignatura);
    }

    public ItemhorarioPK getItemhorarioPK() {
        return itemhorarioPK;
    }

    public void setItemhorarioPK(ItemhorarioPK itemhorarioPK) {
        this.itemhorarioPK = itemhorarioPK;
    }

    public int getLimiteMax() {
        return limiteMax;
    }

    public void setLimiteMax(int limiteMax) {
        this.limiteMax = limiteMax;
    }

    public int getCuotas() {
        return cuotas;
    }

    public void setCuotas(int cuotas) {
        this.cuotas = cuotas;
    }

    public int getCapMax() {
        return capMax;
    }

    public void setCapMax(int capMax) {
        this.capMax = capMax;
    }

    public int getCapMin() {
        return capMin;
    }
    
    public void setOculto(boolean oculto){
        this.oculto = oculto;
    }
    
    public boolean getOculto(){
        return oculto;
    }

    public void setCapMin(int capMin) {
        this.capMin = capMin;
    }

    public String getPrincipalOAsistente() {
        return principalOAsistente;
    }

    public void setPrincipalOAsistente(String principalOAsistente) {
        this.principalOAsistente = principalOAsistente;
    }

    public Salon getIdSalon() {
        return idSalon;
    }

    public void setIdSalon(Salon idSalon) {
        this.idSalon = idSalon;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Docente getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(Docente idDocente) {
        this.idDocente = idDocente;
    }

    public Horainicio getHorainicio() {
        return horainicio;
    }

    public void setHorainicio(Horainicio horainicio) {
        this.horainicio = horainicio;
    }

    public Dia getDia1() {
        return dia1;
    }

    public void setDia1(Dia dia1) {
        this.dia1 = dia1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itemhorarioPK != null ? itemhorarioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Itemhorario)) {
            return false;
        }
        Itemhorario other = (Itemhorario) object;
        if ((this.itemhorarioPK == null && other.itemhorarioPK != null) || (this.itemhorarioPK != null && !this.itemhorarioPK.equals(other.itemhorarioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data2.Itemhorario[ itemhorarioPK=" + itemhorarioPK + " ]";
    }
    
}
