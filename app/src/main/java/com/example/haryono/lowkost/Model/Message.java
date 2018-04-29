package com.example.haryono.lowkost.Model;

import java.util.Date;

/**
 * Created by haryono on 4/22/2018.
 */


public class Message {
    private String messageText;
    private String messageUser;
    private long messageTime;

    public Message(String messageText, String messageUser) {
        this.messageText = messageText;
        this.messageUser = messageUser;

        messageTime = new Date().getTime();
    }

    public Message() {
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}


//public class Message {

//    private String content;
//    private String email;
//    private long messageTime;
//    public Message(){
//
//    }
//    public Message(String content, String email){
//        this.content=content;
//        this.email=email;
//        messageTime = new Date().getTime();
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    public long getMessageTime() {
//        return messageTime;
//    }
//
//    public void setMessageTime(long messageTime) {
//        this.messageTime = messageTime;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//}
