package com.rippling.test.model;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Pattern;

@Data
@Slf4j
public class User {
    @NonNull
    private String userName;
    @NonNull
    private String emailId;

    public User(@NonNull String userName, @NonNull String emailId) {
      if(!isValidEmail(emailId)){
          throw new IllegalArgumentException("Invalid Email Address: "+emailId);
      }
        this.userName = userName;
        this.emailId = emailId;
    }

    private boolean isValidEmail(String email){
        String emailRegex = "^(.+)@(\\S+)$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }
}
