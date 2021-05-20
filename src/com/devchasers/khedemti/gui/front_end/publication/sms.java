/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devchasers.khedemti.gui.front_end.publication;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
/**
 *
 * @author Maher
 */
public class sms {
         // Find your Account Sid and Auth Token at twilio.com/console
    public static final String ACCOUNT_SID =
            "AC0ccb5056af6624a63e7ce3f142589fcb";
    public static final String AUTH_TOKEN =
            "0d7fe38aabdd41d8075c3321b18f0547";


    public void send(String s,String x){
         Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
      String y="+21623292574";
        Message message = Message 
                .creator(new PhoneNumber(y), // to
                        new PhoneNumber("+12677871462"), // from
                       ""+s)
                .create();
  System.out.println("aaslema");
        System.out.println(message.getSid());
    }
    
    
}
