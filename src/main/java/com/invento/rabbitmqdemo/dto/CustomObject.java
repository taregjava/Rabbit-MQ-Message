package com.invento.rabbitmqdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.Random;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomObject {

    private int messageId;
    private String message;
    private Date messageDate;


    public void generateRandomMessage() {
        // Length of the random string
        int length = 10;

        // Characters that can be used in the random string
        String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(allowedChars.length());
            char randomChar = allowedChars.charAt(randomIndex);
            sb.append(randomChar);
        }

        this.message = sb.toString();
    }
}
