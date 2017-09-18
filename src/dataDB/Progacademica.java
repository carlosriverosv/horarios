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
@Table(name = "progacademica", uniqueConstraints={@UniqueConstraint(columnNames={"numeroProg"})})
@XmlRootElement
@NamedQueries({
    //@NamedQuery(name = "Progacademica.findAll", query = "SELECT p FROM Progacademica p"),
    @NamedQuery(name = "Progacademica.findAll", query = "SELECT p FROM Progacademica p WHERE p.oculto =:oculto"),
    @NamedQuery(name = "Progacademica.findByNumeroProg", query = "SELECT p FROM Progacademica p WHERE p.numeroProg = :numeroProg"),
    @NamedQuery(name = "Progacademica.findByIdProg", query = "SELECT p FROM Progacademica p WHERE p.idProg = :idProg"),
    @NamedQuery(name = "Progacademica.findNameByIdProg", query = "SELECT p.numeroProg FROM Progacademica p WHERE p.idProg = :idProg")})
public class Progacademica implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Basic(optional = false)
    @Column(name = "oculto")
    private boolean oculto;
    
    @Basic(optional = false)
    @Column(name = "numeroProg")
    private String numeroProg;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idProg")
    private Integer idProg;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "progacademica")
    private List<Grupo> grupoList;
    public final static String nombreColumnas[] = {"CÓDIGO", "ASIGNATURA", "GRUPO", "LÍMITE-MAX", "LIMITANTES", "CUOTAS", "DÍA", "HORA INICIO",
            "HORA FINAL", "EDIFICIO", "SALÓN", "NÚMERO DE IDENTIFICACIÓN", "NOMBRE", "P/A", "DEDICACIÓN", "CAP-MAX", "CAP-MIN",
            "NOM_DÍA", "GR-ASIGNATURA", "NUM_DÍA", "FRANJA", "ESPACIO", "TIPOLOGÍA", "FACULTAD", "ACCIÓN /A/E/M"};
    public final static String columnasArchivo = "CÓDIGO;ASIGNATURA;GRUPO;LÍMITE-MAX;LIMITANTES;CUOTAS;DÍA;Hora Inicio;"+
            "HORA FINAL;EDIFICIO;SALÓN;NÚMERO DE IDENTIFICACIÓN;NOMBRE;P/A;DEDICACIÓN;CAP-MAX;CAP-MIN;"+
            "NOM_DÍA;GR-ASIGNATURA;NUM_DÍA;FRANJA;ESPACIO;TIPOLOGÍA;FACULTAD;ACCIÓN /A/E/M";
    
    public Progacademica() {
    }

    public Progacademica(Integer idProg) {
        this.idProg = idProg;
    }

    public Progacademica(Integer idProg, String numeroProg) {
        this.idProg = idProg;
        this.numeroProg = numeroProg;
    }

    public String getNumeroProg() {
        return numeroProg;
    }

    public void setNumeroProg(String numeroProg) {
        this.numeroProg = numeroProg;
    }

    public Integer getIdProg() {
        return idProg;
    }

    public void setIdProg(Integer idProg) {
        this.idProg = idProg;
    }
    
    public void setOculto(boolean oculto){
        this.oculto = oculto;
    }
    
    public boolean getOculto(){
        return oculto;
    }

    @XmlTransient
    public List<Grupo> getGrupoList() {
        return grupoList;
    }

    public void setGrupoList(List<Grupo> grupoList) {
        this.grupoList = grupoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProg != null ? idProg.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Progacademica)) {
            return false;
        }
        Progacademica other = (Progacademica) object;
        if ((this.idProg == null && other.idProg != null) || (this.idProg != null && !this.idProg.equals(other.idProg))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(numeroProg);
    }
    
}
