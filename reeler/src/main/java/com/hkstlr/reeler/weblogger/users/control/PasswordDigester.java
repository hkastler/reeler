/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.reeler.weblogger.users.control;

import com.hkstlr.reeler.weblogger.weblogs.control.config.WebloggerConfig;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordDigester {
       
    
    public static String getDigestedPassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        
        String encyptionAlgo = WebloggerConfig.getProperty("passwds.encryption.algorithm");
        
        MessageDigest md = MessageDigest.getInstance(encyptionAlgo);
        md.update(password.getBytes());
 
        byte byteData[] = md.digest();
 
        //convert the byte to hex 
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
 
        return sb.toString();
 
    }
}
