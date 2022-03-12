/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl4;


import distanca2.*;
import baza.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;

/**
 *
 * @author Nikola
 */
public class Main {
    
    @Resource(lookup = "jms/__defaultConnectionFactory")
    static ConnectionFactory connectionFactory;
    static JMSContext context;
    static JMSConsumer consumer ;
    static JMSProducer producer ;
    @Resource(lookup = "planerRed")
    static Queue planerRed;
    @Resource(lookup = "odgovorZaPlaner")
    static Queue odgovorZaPlaner;
    @Resource(lookup = "yootubeQueue")
    static Queue yootubeQueue;
    
    public static void main(String[] args){
        context = connectionFactory.createContext();
        consumer = context.createConsumer(planerRed);
        producer = context.createProducer();
        BazaObaveze b = new BazaObaveze();
        System.out.println("usao  ");
        while (true){
            Message por = consumer.receive();
            
            if (por instanceof TextMessage){
               
                try {
                    TextMessage poruka = (TextMessage) por;
                    String str = poruka.getText().toString();
                    //System.out.println(str);
                    String [] string = str.split("@");
                    //System.out.println("usao u while ");
                    if (Integer.parseInt(string[0]) == 6){
                        //System.out.println("usao u if 6 ");
                        b.postaviAdresu(string[1]);
                    }
                    if (Integer.parseInt(string[0]) == 7){
                        //System.out.println("usao u if 7");
                        b.novaObaveza(string[1],string[2],string[3],string[4]);
                    }
                    if (Integer.parseInt(string[0]) == 8){
                        //System.out.println("usao u if 8 ");
                        producer.send(odgovorZaPlaner,b.dohvatiSve() );
                        //System.out.println("izasao u if 8 ");
                    }
                    if (Integer.parseInt(string[0]) == 9){
                        //System.out.println("usao u if 9 ");
                        b.promeni(string[1],string[2],string[3],string[4],string[5]);
                    }
                    if (Integer.parseInt(string[0]) == 10){
                        //System.out.println("usao u if 10 ");
                        b.obrisi(string[1]);
                    }
                    if (Integer.parseInt(string[0]) == 11){
                        //System.out.println("usao u if 11 ");
                        producer.send(odgovorZaPlaner, distanca2.distancaUMetrima(distanca2.matrica(string[1],string[2])));
                    }
                    if (Integer.parseInt(string[0]) == 12){
                        //System.out.println("usao u if 12 ");
                        producer.send(odgovorZaPlaner, distanca2.vremePutovanja(distanca2.matrica(string[1],string[2])));
                    }
                    if (Integer.parseInt(string[0]) == 13){
                        //System.out.println("usao u if 13 ");
                        Date dat = b.podsetnik(string[1]);
                        System.out.println(dat);
                        Timer timer = new Timer();
                        TimerTask task=new TimerTask(){
                            @Override
                            public void run() {
                                TextMessage textMessage = context.createTextMessage();
                                try {
                                    textMessage.setText("3@pera@"+"nevermind");
                                    producer.send(yootubeQueue, textMessage);
                                } catch (JMSException ex) {}
                            }      
                        };
                        timer.schedule(task, dat);
                    }
                } catch (JMSException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                     
        
            }
        }
             
        
            
                
        
        
        
           
        
    }
   
    
}
