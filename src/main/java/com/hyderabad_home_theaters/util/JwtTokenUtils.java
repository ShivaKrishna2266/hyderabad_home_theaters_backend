package com.hyderabad_home_theaters.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class JwtTokenUtils {

    public  static String getLoggedInUserEmail() {
        Authentication authenticationToken = SecurityContextHolder.getContext().getAuthentication();
        return authenticationToken.getName();
    }

    public  static  String getLoggedInUserName(){
        Authentication authenticationToken = SecurityContextHolder.getContext().getAuthentication();
        return authenticationToken.getName();
    }
}
