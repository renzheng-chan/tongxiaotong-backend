package scu.train.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import scu.train.backend.utils.JwtUtil;

@SpringBootTest
public class JwtTest {
    @Autowired
    private JwtUtil jwtUtil;

    @Test
    public void TestBCryptPasswordEncoder(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("123"));
        System.out.println(bCryptPasswordEncoder.matches("123", "$2a$10$.88ELpu8LVexAEAuwY9kY.nznlq4GD8m.S/qzJr8iC1STF26IHEGy"));
    }
    @Test
    public void TestJwt(){

    }
}
