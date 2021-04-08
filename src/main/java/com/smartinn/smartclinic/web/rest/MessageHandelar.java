package com.smartinn.smartclinic.web.rest;

import com.smartinn.smartclinic.web.rest.vm.Message;
import com.smartinn.smartclinic.web.rest.vm.OutputMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageHandelar {

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public OutputMessage send(Message message) throws Exception {
        System.out.println("---------------------------------------------------------------------");
        System.out.println();
        System.out.println();
        System.out.println("send --------->");
        System.out.println();
        System.out.println("---------------------------------------------------------------------");

        String time = new SimpleDateFormat("HH:mm").format(new Date());
        return new OutputMessage(message.getFrom(), message.getText(), time);
    }
}
