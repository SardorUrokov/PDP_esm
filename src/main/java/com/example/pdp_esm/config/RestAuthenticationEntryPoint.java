package com.example.pdp_esm.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        Map<String, Object> map = new HashMap<>();
        String code = httpServletResponse.getHeader("code");
        if (code == null){
            map.put("message", "Required Authentication");
            map.put("code", 3);
        } else {
            switch (code){
                case "1" -> {
                    map.put("message" , "access_token_expired");
                    map.put("code", 1);
                }
                case "2" -> {
                    map.put("message" , "refresh_token_expired");
                    map.put("code", 2);
                }
                default -> {
                    map.put("message" , "Bad Credentials");
                    map.put("code", 3);
                }
            }
        }

        httpServletResponse.setStatus(401);
        httpServletResponse.setContentType("application/json");
        OutputStream out = httpServletResponse.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, map);
        out.flush();
    }
}
