package com.mycompany.korisnickiservis.resources;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author 
 */
@Path("javaee8")
@Stateless
public class JavaEE8Resource {
   
    //@Resource(lookup = "jms/__defaultConnectionFactory")
    static ConnectionFactory connectionFactory;
    //@Resource(lookup = "yootubeQueue")
    static Queue yootubeQueue;
    //@Resource(lookup = "redZaIstoriju")
    static Queue redZaIstoriju;
    static Queue alarmRed;
    static Queue planerRed;
    static Queue odgovorZaPlaner;
    static JMSContext context ;
    static JMSContext context2;
    static JMSConsumer consumer ;
    static JMSProducer producer ;
    boolean initializedQueues = false;
    static String zvuk = "memories";

    private void initJMS() {
        try {
            if (initializedQueues)
                return;
            javax.naming.Context ctx = new javax.naming.InitialContext();
            yootubeQueue = (Queue) ctx.lookup("yootubeQueue");
            redZaIstoriju = (Queue) ctx.lookup("redZaIstoriju");
            alarmRed= (Queue) ctx.lookup("alarmRed");
            planerRed = (Queue) ctx.lookup("planerRed");
            odgovorZaPlaner = (Queue) ctx.lookup("odgovorZaPlaner");
            connectionFactory = (ConnectionFactory) ctx.lookup("jms/__defaultConnectionFactory");
            context = connectionFactory.createContext();
            context2 = connectionFactory.createContext();
            producer = context.createProducer();
            // this.producer2 = jmsContext.createProducer();
            consumer = context2.createConsumer(redZaIstoriju);
            initializedQueues = true;
            
        } catch (NamingException ex) {
            Logger.getLogger(JavaEE8Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @POST
    @Path("{text}")
    public Response post(@PathParam("text") String text){
        initJMS();
        try {
            
            TextMessage poruka = context.createTextMessage();
            poruka.setText(text);
            //String pom = "7@naziv@202102251414@202102281515@negde";
            String[] str = text.split("@");
            //String[] str = pom.split("@");
            if(Integer.parseInt(str[0])<2){
                producer.send(yootubeQueue,poruka);
            }
            else{
                if (Integer.parseInt(str[0])<6){
                   producer.send(alarmRed,poruka);
                }
                else{
                    
                        producer.send(planerRed,poruka);
                    
                }
            }
            
            
        } catch (JMSException ex) {
            Logger.getLogger(JavaEE8Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok("Postovano").build();
    }
   
}
