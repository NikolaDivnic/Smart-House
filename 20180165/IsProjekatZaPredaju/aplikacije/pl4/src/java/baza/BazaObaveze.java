/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baza;
import distanca2.distanca2;
import entiteti.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.*;

/**
 *
 * @author Nikola
 */
public class BazaObaveze {
    private EntityManagerFactory emf ; 
    private EntityManager em ;
    private int max;
    private String s ="Beograd";
    private static int br = 0;
    public BazaObaveze(){
            
        emf = Persistence.createEntityManagerFactory("ime");
        em = emf.createEntityManager();
        max = dohvatiBroj()+1;
        
        
        
    }
    public void postaviAdresu(String sa){
        s = sa;
    }
    public void novaObaveza(String naziv, String datumPOc, String kraj1,String lokacija){
        try {
            em.getEntityManagerFactory().getCache().evict(Obavezenove.class);
            TypedQuery<Obavezenove> q = em.createQuery("Select e from Obavezenove e" , Obavezenove.class);
            
            List<Obavezenove> lista = q.getResultList();
            for (Obavezenove obaveze : lista) {
                String p = obaveze.getPocetak();
                String k = obaveze.getTrajanje();
                if ((p.compareTo(kraj1)>0 )|| (datumPOc.compareTo(k)>0)){
                    
                }
                else{
                    return ;
                }
            }
            
            /*Obaveze o = new Obaveze();
            o.setIdObaveze(br);
            o.setNaziv(naziv);
            o.setPocetak(datumPOc);
            o.setTrajanje(kraj1);*/
            String pom = "";
            if ("-".equals(lokacija)){
                pom=s;
            }
            else{
                pom=lokacija;
            }
            em.getEntityManagerFactory().getCache().evict(Obavezenove.class);
            UserTransaction ut = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
            ut.begin();
            em.joinTransaction();
            em.createNativeQuery("INSERT INTO Obavezenove( destinacija, pocetak , trajanje , naziv) VALUES (?2,?3,?4,?5)")
            .setParameter(2, pom).setParameter(3, datumPOc).setParameter(4, kraj1)
            .setParameter(5, naziv).executeUpdate();
            /*Query q1 = em.createNativeQuery("INSERT INTO Obaveze(idObaveze , destinacija, pocetak , trajanje , naziv) Values ("+ br + "," + pom+""
                    + "," +datumPOc + "," + kraj1 + "," + naziv + ")");*/
            //q1.executeUpdate();
            ut.commit();
            //em.getTransaction().begin();
            //em.persist(o);
            //em.getTransaction().commit();
        } catch (NamingException ex) {
            Logger.getLogger(BazaObaveze.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotSupportedException ex) {
            Logger.getLogger(BazaObaveze.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SystemException ex) {
            Logger.getLogger(BazaObaveze.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackException ex) {
            Logger.getLogger(BazaObaveze.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HeuristicMixedException ex) {
            Logger.getLogger(BazaObaveze.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HeuristicRollbackException ex) {
            Logger.getLogger(BazaObaveze.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(BazaObaveze.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(BazaObaveze.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
    
    public int dohvatiBroj(){
        Query query = em.createQuery("Select count(e) from Obavezenove e");
        
        Long l = (Long) query.getSingleResult();
        return l.intValue();
    }
    
    public int dohvatiMax(){
        Query query = em.createQuery("Select max(e.idObaveze) from Obavezenove e");
        Integer i = (Integer) query.getSingleResult();
        return i.intValue();
    }
    public String dohvatiSve(){
        em.clear();
        em.getEntityManagerFactory().getCache().evict(Obavezenove.class);
        Query query = em.createQuery("Select e from Obavezenove e");
        List<Obavezenove> list = (List<Obavezenove>) query.getResultList();
        StringBuilder sb = new StringBuilder();
        System.out.println("Nesto");
        for (Obavezenove o : list) {
            String ss1 = o.getPocetak() ;
            ss1 = ss1.substring(0, 4) + "-" + ss1.substring(4, ss1.length());
            ss1 = ss1.substring(0, 7) + "-" + ss1.substring(7, ss1.length());
            ss1 = ss1.substring(0, 10) + " " + ss1.substring(10, ss1.length());
            ss1 = ss1.substring(0, 13) + ":" + ss1.substring(13, ss1.length());
            String ss2 = o.getTrajanje();
            ss2 = ss2.substring(0, 4) + "-" + ss2.substring(4, ss2.length());
            ss2 = ss2.substring(0, 7) + "-" + ss2.substring(7, ss2.length());
            ss2 = ss2.substring(0, 10) + " " + ss2.substring(10, ss2.length());
            ss2 = ss2.substring(0, 13) + ":" + ss2.substring(13, ss2.length());
            System.out.println(ss1 + " " + ss2);
            sb.append(o.getIdObaveze()).append(" ").append(o.getNaziv()+ " ").append( ss1 + " ").append(ss2 + " " ).append(o.getDestinacija())
                    .append(System.lineSeparator());
        }
        return sb.toString();
    }

    public void obrisi(String x) {
        try {
            // Obaveze o = em.find(Obaveze.class, x);
            em.getEntityManagerFactory().getCache().evict(Obavezenove.class);
            UserTransaction ut = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
            
                ut.begin();
          
            em.joinTransaction();
            em.createNativeQuery("DELETE FROM Obavezenove o WHERE o.idObaveze =?1").setParameter(1, x).executeUpdate();
            /*Query q1 = em.createNativeQuery("INSERT INTO Obaveze(idObaveze , destinacija, pocetak , trajanje , naziv) Values ("+ br + "," + pom+""
            + "," +datumPOc + "," + kraj1 + "," + naziv + ")");*/
            //q1.executeUpdate();
            ut.commit();
            
        } catch (NamingException ex) {
            Logger.getLogger(BazaObaveze.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackException ex) {
            Logger.getLogger(BazaObaveze.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HeuristicMixedException ex) {
            Logger.getLogger(BazaObaveze.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HeuristicRollbackException ex) {
            Logger.getLogger(BazaObaveze.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(BazaObaveze.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(BazaObaveze.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SystemException ex) {
            Logger.getLogger(BazaObaveze.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotSupportedException ex) {
            Logger.getLogger(BazaObaveze.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String dohvatiPocetak(int x){
        Obavezenove o = em.find(Obavezenove.class, x);
        return o.getPocetak();
    }
    public String dohvatiDestinaciju(int x){
        Obavezenove o = em.find(Obavezenove.class, x);
        return o.getDestinacija();
    }
    public String dohvatiKraj(int x){
        Obavezenove o = em.find(Obavezenove.class, x);
        return o.getTrajanje();
    }
    public Date podsetnik(String s1){
        
        try {
            String s2 = this.dohvatiPocetak(Integer.parseInt(s1));
            s2+="00";
            String text = s2.substring(0, 4) + "-" + s2.substring(4, s2.length());
            
            text = text.substring(0, 7) + "-" + text.substring(7, text.length());
            text = text.substring(0, 10) + " " + text.substring(10, text.length());
            text = text.substring(0, 13) + ":" + text.substring(13, text.length());
            text = text.substring(0, 16) + ":" + text.substring(16, text.length());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
            Date date = formatter.parse(text);
            String vr = distanca2.vremePutovanja(distanca2.matrica(s,dohvatiDestinaciju(Integer.parseInt(s1))));
            String[] vr2 = vr.split(" ");
            String sss = "0000-00-00 " ;
            if ("hours".equals(vr2[1])){
                sss+=""+(Integer.parseInt(vr2[0])+1) + ":" +vr2[2]+ ":00"; 
            }
            else{
                sss+="00:" +vr2[2]+ ":00"; 
            }
            SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
            Date date2 = formatter2.parse(sss);
            //System.out.println(text);
            
            
            //System.out.println(sss);
            
            
            Date diff = new Date(date.getTime() - date2.getTime());
           // System.out.println(diff);
            
       
            return diff;
        } catch (ParseException ex) {
            Logger.getLogger(BazaObaveze.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public void promeni(String z,String naziv, String datumPOc, String kraj1,String lokacija) {
        obrisi(z);
        novaObaveza(naziv, datumPOc, kraj1, lokacija);
        Query query = em.createQuery("Select e from Obavezenove e");
        List<Obavezenove> list = (List<Obavezenove>) query.getResultList();
        list.get(list.size()-1).setIdObaveze(Integer.parseInt(z));
        
        
        /*try {
            TypedQuery<Obaveze> q = em.createQuery("Select e from Obaveze e" , Obaveze.class);
            
            List<Obaveze> lista = q.getResultList();
            for (Obaveze obaveze : lista) {
                String p = obaveze.getPocetak();
                String k = obaveze.getTrajanje();
                if ((p.compareTo(kraj1)>0 )|| (datumPOc.compareTo(k)>0)){
                    
                }
                else{
                    return ;
                }
            }
            
           
           
            String pom = ""; 
            if ("-".equals(lokacija)){
                pom=s;
            }
            else{
                pom=lokacija;
            }
            em.clear();
            UserTransaction ut = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
            ut.begin();
            em.joinTransaction();
            em.createNativeQuery("UPDATE Obaveze  SET destinacija = ?1, pocetak = ?2, trajanje = ?3 , naziv = ?4 WHERE idObaveze = ?5")
            .setParameter(1, pom).setParameter(2, datumPOc).setParameter(3, kraj1)
            .setParameter(4, naziv).setParameter(5, z).executeUpdate();
            //Query q1 = em.createNativeQuery("INSERT INTO Obaveze(idObaveze , destinacija, pocetak , trajanje , naziv) Values ("+ br + "," + pom+""
              //      + "," +datumPOc + "," + kraj1 + "," + naziv + ")");
            //q1.executeUpdate();
            ut.commit();
            //em.getTransaction().begin();
            //em.persist(o);
            //em.getTransaction().commit();
        } catch (NamingException ex) {
            Logger.getLogger(BazaObaveze.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotSupportedException ex) {
            Logger.getLogger(BazaObaveze.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SystemException ex) {
            Logger.getLogger(BazaObaveze.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackException ex) {
            Logger.getLogger(BazaObaveze.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HeuristicMixedException ex) {
            Logger.getLogger(BazaObaveze.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HeuristicRollbackException ex) {
            Logger.getLogger(BazaObaveze.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(BazaObaveze.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(BazaObaveze.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }

    public String getDestinacija(int x) {
        
        Obavezenove o = em.find(Obavezenove.class, x);
        return o.getDestinacija();
    }
}
