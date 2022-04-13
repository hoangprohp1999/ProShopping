package com.example.project_final_2.service.Impl;

import java.util.Random;

public class EmailSendServiceImpl {
    String otp;
    public static Random random = new Random();
    public String RandomOTP(){

        int i;
        i = random.nextInt(900000) + 100000;
        otp = String.valueOf(i);
        return otp;
    }
}
