/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Nikola
 */
public class RadSaBazom {
     private EntityManagerFactory emf ; 
    private EntityManager em ;
        
    public RadSaBazom(){
            
        emf = Persistence.createEntityManagerFactory("imeVasePerzistentneJedinice");
        em = emf.createEntityManager();
    }
    public String dohvatiIstoriju(String name){
        Query query = em.createQuery("Select e from Pesme e");
        List<Pesme> list = (List<Pesme>) query.getResultList();
        StringBuilder sb = new StringBuilder();
        for (Pesme p : list) {
            if (name.equals(p.getKorisnik())) {
                sb.append(p.getPesma()).append(System.lineSeparator());
            }
        }
        
      
        return sb.toString();
    }
    public void dodaj(String[] string) {
       
        Pesme pesma = new Pesme();
        pesma.setKorisnik(string[1]);
        string[2] = string[2].replace('-', ' ');
        pesma.setPesma(string[2]);
        Query query = em.createQuery("Select e from Pesme e");
        List<Pesme> list = (List<Pesme>) query.getResultList();
        pesma.setIdpesme(list.size()+1);
        em.getTransaction().begin();
        em.persist(pesma);
        em.getTransaction().commit();
    }
}
