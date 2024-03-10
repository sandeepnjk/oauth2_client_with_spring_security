package com.example.client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

public class JwtUtilTest {

    @Test
    public void testCreateToken() throws Exception {
        JwtUtil jwtUtil = new JwtUtil();
        String token = jwtUtil.createToken();
        Assertions.assertNotNull(token);
    }
}
