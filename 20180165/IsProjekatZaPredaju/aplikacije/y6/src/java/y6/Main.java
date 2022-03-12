/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package y6;

import entiteti.RadSaBazom;
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
import youtubee.Search;

/**
 *
 * @author Nikola
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    @Resource(lookup = "jms/__defaultConnectionFactory")
    static ConnectionFactory connectionFactory;
    static JMSContext context;
    static JMSConsumer consumer ;
    static JMSProducer producer;
    @Resource(lookup = "yootubeQueue")
    static Queue yootubeQueue;
    @Resource(lookup = "redZaIstoriju")
    static Queue redZaIstoriju;
    public static void main(String[] args) {
        // TODO code application logic here
        RadSaBazom b = new RadSaBazom();
        context = connectionFactory.createContext();
        consumer = context.createConsumer(yootubeQueue);
        producer = context.createProducer();
        while (true){
            Message por = consumer.receive();
            
            if (por instanceof TextMessage){
             
                try {
                    TextMessage poruka = (TextMessage) por;
                    String str = poruka.getText().toString();
                //    System.out.println(str);
                    String [] string = str.split("@");
                  //  System.out.println("usao u while ");
                    if (Integer.parseInt(string[0]) == 0){
                     //   System.out.println("usao u if 0 ");
                        String istorija = b.dohvatiIstoriju(string[1]);
                    //    System.out.println(istorija);
                        producer.send(redZaIstoriju,istorija);
                    }
                    if (Integer.parseInt(string[0]) == 1){
                      //  System.out.println("usao u if 1 ");
                        Search.runBrowser(string[2]);
                        b.dodaj(string);
                    }
                    if (Integer.parseInt(string[0]) == 3){
                     //   System.out.println("usao u if 3 ");
                        Search.runBrowser(string[2]);
                    }
                   // System.out.println("izasao");
                    
                   
                } catch (JMSException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
