package ku;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;


public class Main {
    @Resource(lookup = "jms/__defaultConnectionFactory")
    static ConnectionFactory connectionFactory;
    @Resource(lookup = "redZaIstoriju")
    static Queue redZaIstoriju;
    @Resource(lookup = "odgovorZaPlaner")
    static Queue odgovorZaPlaner;
    static Scanner scanner = new Scanner(System.in);
    static JMSConsumer consumer;
    static JMSConsumer consumer2;
    static JMSContext context ;
    public static void main(String[] args){
     
        context = connectionFactory.createContext();
        consumer = context.createConsumer(redZaIstoriju);
        consumer2 = context.createConsumer(odgovorZaPlaner);
        while(true){
            /*while (true){
                        Message por = consumer2.receive();
            
                        /*if (por instanceof TextMessage){
                            try {
                                TextMessage poruka = (TextMessage) por;
                                String str = poruka.getText().toString();
                                System.out.println(str);
                            } catch (JMSException ex) {
                                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                            } 
                            
                        }   
                    }
        }*/
            System.out.println("-1 za kraj \n"
                    + "0 za istoriju korisnika\n"
                    + "1 za pustanje pesme\n"
                    + "2 za unosenje alarma\n"
                    + "3 za periodican alarmn\n"
                    + "4 za promenuu alarma\n"
                    + "5 za unosenje u jedan od ponudjenih trenutaka\n"
                    + "6 za promenu destinacije\n"
                    + "7 za nove obaveze\n"
                    + "8 za prikaz obaveza\n"
                    + "9 za promenu\n"
                    + "10 za brisanje\n"
                    + "11 za razdaljinu\n"
                    + "12 za vreme\n"
                    + "13 za podsetnik\n");
            try {
                
                String text = "";
                System.out.println("Unesite posao");
                String vrsta = scanner.nextLine();
                text = vrsta;
                if (Integer.parseInt(vrsta) == -1){
                    break;
                }
                if (Integer.parseInt(vrsta) == 0){
                    System.out.println("Unesite korisnika");
                    String korisnik = scanner.nextLine();
                    text = "" + vrsta + "@" + korisnik ;
                    text = text.replace(' ', '-');
                    
                }
                if (Integer.parseInt(vrsta) == 1){
                    System.out.println("Unesite korisnika");
                    String korisnik = scanner.nextLine();
                    System.out.println("Unesite pesmu");
                    String pesma = scanner.nextLine();
                    text = "" + vrsta + "@" + korisnik + "@" + pesma;
                    text = text.replace(' ', '-');
                }
                if (Integer.parseInt(vrsta) == 2){
                    System.out.println("Unesite vreme dd/MM/yyyy HH:mm:ss");
                    String vreme = scanner.nextLine();
                    text ="" + vrsta + "@" +vreme;
                    String t= "";
                    for(int i =  0; i<text.length();i++){
                        if (text.charAt(i)!=' '){
                            t+=text.charAt(i);
                        }
                    }
                    text = t;
                    //System.out.println(t);
                    text = text.replace(' ', '-');
                }
                if (Integer.parseInt(vrsta) == 3){
                    System.out.println("Unesite vreme dd/MM/yyyy HH:mm:ss");
                    String vreme = scanner.nextLine();
                    text ="" + vrsta + "@" +vreme;
                    String t= "";
                    for(int i =  0; i<text.length();i++){
                        if (text.charAt(i)!=' '){
                            t+=text.charAt(i);
                        }
                    }
                    text = t;
                    //System.out.println(t);
                    System.out.println("Unesite period");
                    String period = scanner.nextLine();
                    text = text + "@" + period;
                     text = text.replace(' ', '-');
                }
                if (Integer.parseInt(vrsta) == 4){
                    System.out.println("Unesite pesmu");
                    String pesma = scanner.nextLine();
                    text ="" + vrsta + "@" +pesma;
                    text = text.replace(' ', '-');
                }
                if (Integer.parseInt(vrsta) == 5){
                    System.out.println("Unesite vreme"
                            + "15 za 15 sec"
                            + "30 za 30 sec"
                            + "60 za 60 sec");
                    String vreme = scanner.nextLine();
                    text ="" + vrsta + "@" +vreme;
                }
                if (Integer.parseInt(vrsta) == 6){
                    System.out.println("Unesite lokaciju");
                    String korisnik = scanner.nextLine();
                    text = "" + vrsta + "@" + korisnik ;
                    text = text.replace(' ', '-');
                }
                if (Integer.parseInt(vrsta) == 7){
                    System.out.println("Unesite naziv");
                    String naziv = scanner.nextLine();
                    System.out.println("Unesite datum pocetka");
                    String datumPocetka = scanner.nextLine();
                    String t= "";
                    for(int i =  0; i<datumPocetka.length();i++){
                        if (datumPocetka.charAt(i)!=' '){
                            t+=datumPocetka.charAt(i);
                        }
                    }
                    System.out.println("Unesite datum kraja");
                    String datumKraja = scanner.nextLine();
                    String t2= "";
                    for(int i =  0; i<datumKraja.length();i++){
                        if (datumKraja.charAt(i)!=' '){
                            t2+=datumKraja.charAt(i);
                        }
                    }
                    System.out.println("Unesite lokaciju (- ako ne zelite) ");
                    String lokacija = scanner.nextLine();
                    text ="" + vrsta + "@" +naziv + "@" + t + "@" + t2 + "@" + lokacija;
                     text = text.replace(' ', '-');
                }
                if (Integer.parseInt(vrsta) == 9){
                    System.out.println("Unesite za brisanje");
                    String n1 = scanner.nextLine();
                    System.out.println("Unesite naziv");
                    String naziv = scanner.nextLine();
                    System.out.println("Unesite datum pocetka");
                    String datumPocetka = scanner.nextLine();
                    String t= "";
                    for(int i =  0; i<datumPocetka.length();i++){
                        if (datumPocetka.charAt(i)!=' '){
                            t+=datumPocetka.charAt(i);
                        }
                    }
                    System.out.println("Unesite datum kraja");
                    String datumKraja = scanner.nextLine();
                    String t2= "";
                    for(int i =  0; i<datumKraja.length();i++){
                        if (datumKraja.charAt(i)!=' '){
                            t2+=datumKraja.charAt(i);
                        }
                    }
                    System.out.println("Unesite lokaciju (- ako ne zelite) ");
                    String lokacija = scanner.nextLine();
                    text ="" + vrsta + "@" +n1+"@"+naziv + "@" + t + "@" + t2 + "@" + lokacija;
                    text = text.replace(' ', '-');
                }
                if (Integer.parseInt(vrsta) == 10){
                    System.out.println("Unesite sta zelite da obrisete");
                    String korisnik = scanner.nextLine();
                    text = "" + vrsta + "@" + korisnik ;
                    text = text.replace(' ', '-');
                }
                if (Integer.parseInt(vrsta) == 11){
                    System.out.println("Unesite odakle");
                    String korisnik = scanner.nextLine();
                    System.out.println("Unesite gde");
                    String pesma = scanner.nextLine();
                    text = "" + vrsta + "@" + korisnik + "@" + pesma;
                    text = text.replace(' ', '-');
                }    
                if (Integer.parseInt(vrsta) == 12){
                    System.out.println("Unesite odakle");
                    String korisnik = scanner.nextLine();
                    System.out.println("Unesite gde");
                    String pesma = scanner.nextLine();
                    text = "" + vrsta + "@" + korisnik + "@" + pesma;
                    text = text.replace(' ', '-');
                }     
                if (Integer.parseInt(vrsta) == 13){
                    System.out.println("Unesite obavezu");
                    String korisnik = scanner.nextLine();
                    
                    text = "" + vrsta + "@" + korisnik;
                    text = text.replace(' ', '-');
                }
                //System.out.println(text); 
                    
                URL url = new URL("http://127.0.0.1:8080/KorisnickiServis/resources/javaee8/" + text   );
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                    
                int responseCode = connection.getResponseCode();
                if (Integer.parseInt(vrsta) == 0){
                    while (true){
                        Message por = consumer.receive();
            
                        if (por instanceof TextMessage){
                            try {
                                TextMessage poruka = (TextMessage) por;
                                String str = poruka.getText().toString();
                                System.out.println(str);
                            } catch (JMSException ex) {
                                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                            } 
                            break;
                        }    
                    }
                }
                if ((Integer.parseInt(vrsta) == 11)||(Integer.parseInt(vrsta) == 12)){
                    while (true){
                        Message por = consumer2.receive();
            
                        if (por instanceof TextMessage){
                            try {
                                TextMessage poruka = (TextMessage) por;
                                String str = poruka.getText().toString();
                                System.out.println(str);
                            } catch (JMSException ex) {
                                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                            } 
                            break;
                        }    
                    }
                }
                if ((Integer.parseInt(vrsta) == 8)){
                    while (true){
                        Message por = consumer2.receive();
            
                        if (por instanceof TextMessage){
                            try {
                                TextMessage poruka = (TextMessage) por;
                                String text1 = poruka.getText().toString();
                                
                                System.out.println(text1);
                            } catch (JMSException ex) {
                                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                            } 
                            break;
                        }    
                    }
                }
                if (responseCode==404) {
                    System.out.println("Nije pronadjen resurs");
                }
                if (responseCode == 200) {
                    System.out.println("Uspesan zahtev");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line = "";
                    StringBuffer buffer = new StringBuffer();
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }
                    reader.close();
                    System.out.println("Odgovor:" + buffer.toString());
                }   } catch (MalformedURLException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
    }
            
}

 

