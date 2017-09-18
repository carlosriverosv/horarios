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
@Table(name = "docente", uniqueConstraints={@UniqueConstraint(columnNames={"documentoDoc"})})
@XmlRootElement
@NamedQueries({
    //@NamedQuery(name = "Docente.findAll", query = "SELECT d FROM Docente d ORDER BY D.nombreDocente ASC"),
    @NamedQuery(name = "Docente.findAll", query = "SELECT d FROM Docente d wHERE d.oculto =:oculto ORDER BY D.nombreDocente ASC"),
    @NamedQuery(name = "Docente.findByDocumentoDoc", query = "SELECT d FROM Docente d WHERE d.documentoDoc = :documentoDoc"),
    @NamedQuery(name = "Docente.findByNombreDocente", query = "SELECT d FROM Docente d WHERE d.nombreDocente = :nombreDocente"),
    @NamedQuery(name = "Docente.findByActivo", query = "SELECT d FROM Docente d WHERE d.activo = :activo  ORDER BY D.nombreDocente ASC"),
    @NamedQuery(name = "Docente.findByCorreoElectronico", query = "SELECT d FROM Docente d WHERE d.correoElectronico = :correoElectronico"),
    @NamedQuery(name = "Docente.findByIdDocente", query = "SELECT d FROM Docente d WHERE d.idDocente = :idDocente"),
    @NamedQuery(name = "Docente.findByIdTipoContrato", query = "SELECT d FROM Docente d WHERE d.idTipoContrato = :idTipoContrato AND d.oculto =:oculto"),
    @NamedQuery(name = "Docente.findByOculto", query = "SELECT d FROM Docente d WHERE d.oculto = :oculto AND d.activo = :activo  ORDER BY D.nombreDocente ASC" )})
public class Docente implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Basic(optional = false)
    @Column(name = "oculto")
    private boolean oculto;
    
    @Basic(optional = false)
    @Column(name = "documentoDoc")
    private int documentoDoc;
    @Basic(optional = false)
    @Column(name = "nombreDocente")
    private String nombreDocente;
    @Basic(optional = false)
    @Column(name = "activo")
    private boolean activo;
    @Column(name = "correoElectronico")
    private String correoElectronico;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDocente")
    private Integer idDocente;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDocente")
    private List<Itemhorario> itemhorarioList;
    @JoinColumn(name = "idTipoContrato", referencedColumnName = "idTipoContrato")
    @ManyToOne(optional = false)
    private Tipocontrato idTipoContrato;
    public static final String COLUMNAS_ARCHIVO = "Documento;Nombre;Correo electrónico;Tipo de contrato;Activo/No activo";

    public Docente() {
    }

    public Docente(Integer idDocente) {
        this.idDocente = idDocente;
    }

    public Docente(Integer idDocente, int documentoDoc, String nombreDocente, boolean activo) {
        this.idDocente = idDocente;
        this.documentoDoc = documentoDoc;
        this.nombreDocente = nombreDocente;
        this.activo = activo;
    }

    public int getDocumentoDoc() {
        return documentoDoc;
    }

    public void setDocumentoDoc(int documentoDoc) {
        this.documentoDoc = documentoDoc;
    }

    public String getNombreDocente() {
        return nombreDocente;
    }

    public void setNombreDocente(String nombreDocente) {
        this.nombreDocente = nombreDocente;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }
    
    public void setOculto(boolean oculto){
        this.oculto = oculto;
    }
    
    public boolean getOculto(){
        return oculto;
    }

    public Integer getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(Integer idDocente) {
        this.idDocente = idDocente;
    }

    @XmlTransient
    public List<Itemhorario> getItemhorarioList() {
        return itemhorarioList;
    }

    public void setItemhorarioList(List<Itemhorario> itemhorarioList) {
        this.itemhorarioList = itemhorarioList;
    }

    public Tipocontrato getIdTipoContrato() {
        return idTipoContrato;
    }

    public void setIdTipoContrato(Tipocontrato idTipoContrato) {
        this.idTipoContrato = idTipoContrato;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDocente != null ? idDocente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Docente)) {
            return false;
        }
        Docente other = (Docente) object;
        if ((this.idDocente == null && other.idDocente != null) || (this.idDocente != null && !this.idDocente.equals(other.idDocente))) {
            return false;
        }
        return true;
    }

     @Override
    public String toString() {
        return nombreDocente;
    }
    
}
