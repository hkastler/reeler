/*
 * Copyright 2017 Henry Kastler <hkastler+github@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
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
