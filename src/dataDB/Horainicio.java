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
import javax.persistence.Id;
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
@Table(name = "horainicio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Horainicio.findAll", query = "SELECT h FROM Horainicio h"),
    @NamedQuery(name = "Horainicio.findByHoraInicio", query = "SELECT h FROM Horainicio h WHERE h.horaInicio = :horaInicio")})
public class Horainicio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "horaInicio")
    private Integer horaInicio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "horainicio")
    private List<Itemhorario> itemhorarioList;

    public Horainicio() {
    }

    public Horainicio(Integer horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Integer getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Integer horaInicio) {
        this.horaInicio = horaInicio;
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
        hash += (horaInicio != null ? horaInicio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Horainicio)) {
            return false;
        }
        Horainicio other = (Horainicio) object;
        if ((this.horaInicio == null && other.horaInicio != null) || (this.horaInicio != null && !this.horaInicio.equals(other.horaInicio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data2.Horainicio[ horaInicio=" + horaInicio + " ]";
    }
    
}
