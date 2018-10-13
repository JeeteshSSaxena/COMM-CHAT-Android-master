package com.jeetesh.flashchatnewfirebase;

/**
 * Created by Jeetesh on 7/8/2018.
 */

public class Messenger {
    private String msg;
    private String sender;

    public Messenger(String msg,String sender) {
        this.sender=sender;
        this.msg = msg;
    }

    public Messenger() {
    }

    public String getSender() {
        return sender;
    }
    public String getMsg(){
        return msg;
    }
}
