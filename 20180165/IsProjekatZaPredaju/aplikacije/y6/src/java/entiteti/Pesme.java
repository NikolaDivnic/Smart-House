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
@Table(name = "pesme")
@NamedQueries({
    @NamedQuery(name = "Pesme.findAll", query = "SELECT p FROM Pesme p")})
public class Pesme implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpesme")
    private Integer idpesme;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "korisnik")
    private String korisnik;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "pesma")
    private String pesma;

    public Pesme() {
    }

    public Pesme(Integer idpesme) {
        this.idpesme = idpesme;
    }

    public Pesme(Integer idpesme, String korisnik, String pesma) {
        this.idpesme = idpesme;
        this.korisnik = korisnik;
        this.pesma = pesma;
    }

    public Integer getIdpesme() {
        return idpesme;
    }

    public void setIdpesme(Integer idpesme) {
        this.idpesme = idpesme;
    }

    public String getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(String korisnik) {
        this.korisnik = korisnik;
    }

    public String getPesma() {
        return pesma;
    }

    public void setPesma(String pesma) {
        this.pesma = pesma;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpesme != null ? idpesme.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pesme)) {
            return false;
        }
        Pesme other = (Pesme) object;
        if ((this.idpesme == null && other.idpesme != null) || (this.idpesme != null && !this.idpesme.equals(other.idpesme))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Pesme[ idpesme=" + idpesme + " ]";
    }
    
}
