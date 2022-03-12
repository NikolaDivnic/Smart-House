/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Nikola
 */
@Entity
@Table(name = "obavezenove")
@NamedQueries({
    @NamedQuery(name = "Obavezenove.findAll", query = "SELECT o FROM Obavezenove o")})
public class Obavezenove implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idObaveze")
    private Integer idObaveze;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "destinacija")
    private String destinacija;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "pocetak")
    private String pocetak;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "trajanje")
    private String trajanje;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "naziv")
    private String naziv;

    public Obavezenove() {
    }

    public Obavezenove(Integer idObaveze) {
        this.idObaveze = idObaveze;
    }

    public Obavezenove(Integer idObaveze, String destinacija, String pocetak, String trajanje, String naziv) {
        this.idObaveze = idObaveze;
        this.destinacija = destinacija;
        this.pocetak = pocetak;
        this.trajanje = trajanje;
        this.naziv = naziv;
    }

    public Integer getIdObaveze() {
        return idObaveze;
    }

    public void setIdObaveze(Integer idObaveze) {
        this.idObaveze = idObaveze;
    }

    public String getDestinacija() {
        return destinacija;
    }

    public void setDestinacija(String destinacija) {
        this.destinacija = destinacija;
    }

    public String getPocetak() {
        return pocetak;
    }

    public void setPocetak(String pocetak) {
        this.pocetak = pocetak;
    }

    public String getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(String trajanje) {
        this.trajanje = trajanje;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idObaveze != null ? idObaveze.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Obavezenove)) {
            return false;
        }
        Obavezenove other = (Obavezenove) object;
        if ((this.idObaveze == null && other.idObaveze != null) || (this.idObaveze != null && !this.idObaveze.equals(other.idObaveze))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Obavezenove[ idObaveze=" + idObaveze + " ]";
    }
    
}
