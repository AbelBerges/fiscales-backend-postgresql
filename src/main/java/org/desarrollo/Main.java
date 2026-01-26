package org.desarrollo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.out.println(">>>>>>> Sistema backend fiscales iniciado <<<<<<<");
        //String raw = "1234";
        //String hash = "$2a$10$Dow1fSJhgxqFwSgusIsy0u1BUpfk0u6El3arhCY3yc5T4HBP71l1C"; // el de tu DB

        //System.out.println(new BCryptPasswordEncoder().matches(raw, hash));
        //System.out.println(new BCryptPasswordEncoder().encode("1234"));
    }
}