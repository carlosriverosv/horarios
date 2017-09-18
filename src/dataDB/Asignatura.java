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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "asignatura", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"codigoAsig"})})
@XmlRootElement
@NamedQueries({
    //@NamedQuery(name = "Asignatura.findAll", query = "SELECT a FROM Asignatura a ORDER BY A.nombreAsig ASC"),
    @NamedQuery(name = "Asignatura.findAll", query = "SELECT a FROM Asignatura a WHERE a.oculto = :oculto ORDER BY A.nombreAsig ASC"),
    @NamedQuery(name = "Asignatura.findByCodigoAsig", query = "SELECT a FROM Asignatura a WHERE a.codigoAsig = :codigoAsig"),
    @NamedQuery(name = "Asignatura.findByNombreAsig", query = "SELECT a FROM Asignatura a WHERE a.nombreAsig = :nombreAsig"),
    @NamedQuery(name = "Asignatura.findByActivo", query = "SELECT a FROM Asignatura a WHERE a.activo = :activo ORDER BY A.nombreAsig ASC"),
    @NamedQuery(name = "Asignatura.findByIdAsignatura", query = "SELECT a FROM Asignatura a WHERE a.idAsignatura = :idAsignatura"),
    //@NamedQuery(name = "Asignatura.findByIdNivelEstudio", query = "SELECT a FROM Asignatura a WHERE a.idNivelEstudio = :idNivelEstudio ORDER BY A.nombreAsig ASC"),
    @NamedQuery(name = "Asignatura.findByIdNivelEstudio", query = "SELECT a FROM Asignatura a WHERE a.idNivelEstudio = :idNivelEstudio AND a.oculto =:oculto ORDER BY A.nombreAsig ASC"),
    @NamedQuery(name = "Asignatura.findByOculto", query = "SELECT a FROM Asignatura a WHERE a.oculto = :oculto AND a.activo =:activo ORDER BY A.nombreAsig ASC")})
public class Asignatura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "oculto")
    private boolean oculto;
    
    @Basic(optional = false)
    @Column(name = "codigoAsig")
    private int codigoAsig;
    @Basic(optional = false)
    @Column(name = "nombreAsig")
    private String nombreAsig;
    @Basic(optional = false)
    @Column(name = "activo")
    private boolean activo;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idAsignatura")
    private Integer idAsignatura;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "asignatura")
    private List<Grupo> grupoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "asignatura")
    private List<Asignaturaenplanestudio> asignaturaenplanestudioList;
    @JoinColumn(name = "idNivelEstudio", referencedColumnName = "idNivelEstudio")
    @ManyToOne(optional = false)
    private Nivelestudio idNivelEstudio;
    public static final String COLUMNAS_ARCHIVO = "Código;Nombre;Nivel de estudio;Activa/No activa";

    public Asignatura() {
    }

    public Asignatura(Integer idAsignatura) {
        this.idAsignatura = idAsignatura;
    }

    public Asignatura(Integer idAsignatura, int codigoAsig, String nombreAsig, boolean activo) {
        this.idAsignatura = idAsignatura;
        this.codigoAsig = codigoAsig;
        this.nombreAsig = nombreAsig;
        this.activo = activo;
    }

    public int getCodigoAsig() {
        return codigoAsig;
    }

    public void setCodigoAsig(int codigoAsig) {
        this.codigoAsig = codigoAsig;
    }

    public String getNombreAsig() {
        return nombreAsig;
    }

    public void setNombreAsig(String nombreAsig) {
        this.nombreAsig = nombreAsig;
    }
    
    public void setOculto(boolean oculto){
        this.oculto = oculto;
    }
    
    public boolean getOculto(){
        return oculto;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Integer getIdAsignatura() {
        return idAsignatura;
    }

    public void setIdAsignatura(Integer idAsignatura) {
        this.idAsignatura = idAsignatura;
    }

    @XmlTransient
    public List<Grupo> getGrupoList() {
        return grupoList;
    }

    public void setGrupoList(List<Grupo> grupoList) {
        this.grupoList = grupoList;
    }

    @XmlTransient
    public List<Asignaturaenplanestudio> getAsignaturaenplanestudioList() {
        return asignaturaenplanestudioList;
    }

    public void setAsignaturaenplanestudioList(List<Asignaturaenplanestudio> asignaturaenplanestudioList) {
        this.asignaturaenplanestudioList = asignaturaenplanestudioList;
    }

    public Nivelestudio getIdNivelEstudio() {
        return idNivelEstudio;
    }

    public void setIdNivelEstudio(Nivelestudio idNivelEstudio) {
        this.idNivelEstudio = idNivelEstudio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAsignatura != null ? idAsignatura.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Asignatura)) {
            return false;
        }
        Asignatura other = (Asignatura) object;
        if ((this.idAsignatura == null && other.idAsignatura != null) || (this.idAsignatura != null && !this.idAsignatura.equals(other.idAsignatura))) {
            return false;
        }
        return true;
    }

    public String getPlanesEstudioVinculados() {
        String planesEstudioVinculados = "";
        String cod;
        List<Asignaturaenplanestudio> asignaturaEnPlan = this.getAsignaturaenplanestudioList();
        for (Asignaturaenplanestudio cadaAsigEnPlan : asignaturaEnPlan) {
            cod = cadaAsigEnPlan.getPlanestudio().getOculto() ? "" : cadaAsigEnPlan.getPlanestudio().getCodigoPlan();
            planesEstudioVinculados = cod != "" ? cod + ", " + planesEstudioVinculados : planesEstudioVinculados;
        }
        if (!planesEstudioVinculados.isEmpty()) {
            planesEstudioVinculados = planesEstudioVinculados.substring(0, planesEstudioVinculados.length() - 2);
        }
        return planesEstudioVinculados;
    }

    public String getTipologias() {
        String tipologias = "";
        List<Asignaturaenplanestudio> asignaturaEnPlan = this.getAsignaturaenplanestudioList();
        for (Asignaturaenplanestudio cadaAsigEnPlan : asignaturaEnPlan) {
            tipologias = cadaAsigEnPlan.getIdTipologia().getTipologia() + ", " + tipologias;
        }
        if (!tipologias.isEmpty()) {
            tipologias = tipologias.substring(0, tipologias.length() - 2);
        }
        return tipologias;
    }

    @Override
    public String toString() {
        return codigoAsig + " - " + nombreAsig;
    }
}
