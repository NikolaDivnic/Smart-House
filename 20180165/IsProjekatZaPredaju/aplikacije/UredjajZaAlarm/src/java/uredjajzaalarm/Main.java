/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uredjajzaalarm;

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
    @Resource(lookup = "yootubeQueue")
    static Queue yootubeQueue;
    @Resource(lookup = "redZaIstoriju")
    static Queue redZaIstoriju;     
    @Resource(lookup = "alarmRed")
    static Queue alarmRed;
    static JMSContext context ;
    static JMSConsumer consumer ;
    static JMSProducer producer;
    static String zvuk = "memories";
    public static void main(String[] args) {   
        
        context = connectionFactory.createContext();
        consumer = context.createConsumer(alarmRed);
        producer = context.createProducer(); 
        System.out.println("Started");
        while (true){
            Message por = consumer.receive();
            
            if (por instanceof TextMessage){
                try {
                    TextMessage poruka = (TextMessage) por;
                    String str = poruka.getText().toString();
                    //System.out.println(str);
                    String [] string = str.split("@");                    
                    int ind = Integer.parseInt(string[0]);
                    //String s = "21-02-2021-16-48-00";
                    String s = string[1];
                    if (ind == 4){
                        zvuk = string[1];
                    }
                    else{
                        Timer timer = new Timer();
                        TimerTask task=new TimerTask(){
                            @Override
                            public void run() {
                                TextMessage textMessage = context.createTextMessage();
                                try {
                                    textMessage.setText("3@pera@"+zvuk);
                                    producer.send(yootubeQueue, textMessage);
                                } catch (JMSException ex) {}
                            }      
                        };
                        if (ind == 5){   
                            long l0 = System.currentTimeMillis();
                            long l1 = System.currentTimeMillis()+Integer.parseInt(string[1])*1000;
                            //System.out.println("Vreme " + l0  + "   " + l1);
                            Date date2 = new Date(System.currentTimeMillis()+Integer.parseInt(string[1])*1000);
                            timer.schedule(task, date2);
                        }
                        else{
                            try {
                                
                                String text = s.substring(0, 2) + "/" + s.substring(2, s.length());
                                
                                text = text.substring(0, 5) + "/" + text.substring(5, text.length());
                                text = text.substring(0, 10) + " " + text.substring(10, text.length());
                                text = text.substring(0, 13) + ":" + text.substring(13, text.length());
                                text = text.substring(0, 16) + ":" + text.substring(16, text.length());
                                
                                //System.out.println(text);
                                
                                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                Date date = formatter.parse(text);
                                
                                if (ind == 2){
                                    timer.schedule(task, date);
                                }
                                if (ind == 3){
                                    int period = Integer.parseInt(string[2]);
                                    timer.schedule(task, date,period*1000);
                                }
                            } catch (ParseException ex) {
                                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                            }
                                
                                
                            //System.out.println("Alarm ukljucen u " + string[1]);
                        }
                                
                        }
                    }catch (JMSException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
 
            }
        }
    }
}
