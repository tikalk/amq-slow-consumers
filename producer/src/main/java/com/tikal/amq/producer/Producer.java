package com.tikal.amq.producer;

import org.slf4j.LoggerFactory;
import org.springframework.jms.core.*;

import javax.annotation.PostConstruct;
import javax.jms.*;
import java.util.Date;


/**
 * Created by IntelliJ IDEA.
 * User: shalom
 * Date: Nov 22, 2009
 * Time: 3:11:41 PM
 */
public class Producer {

    private JmsTemplate jmsTemplate;


    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }


    @PostConstruct
    public void init(){
    }



    public void process() {

        final Date date = new Date();
        LoggerFactory.getLogger(getClass()).debug("Sending a message at "+date);
        
        jmsTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                Message message = session.createTextMessage("Message from producer at "+date);
                message.setJMSDeliveryMode(DeliveryMode.NON_PERSISTENT);
                return message;
            }
        });

    }


}
