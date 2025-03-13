package com.cv.resume.creator.feeder;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.cv.resume.creator.model.User;
import com.cv.resume.creator.repository.UserRepository;

@Component
public class DataLoader {
	

    private final PasswordEncoder passwordEncoder;

    public DataLoader(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void loadData() {
        String hashedPassword = passwordEncoder.encode("plainPassword");
        System.out.println("Hashed password: " + hashedPassword);
        
    }

    @Bean
    public CommandLineRunner loadData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Check if users already exist to prevent duplicate inserts
            if (userRepository.findAll().isEmpty()) {
                

//                // Create a regular user
//                User user = new User();
//                user.setUsername("Navanee");
//                user.setPassword(passwordEncoder.encode("1110"));
//                user.setRole("ROLE_USER");
//                userRepository.save(user);

//                System.out.println("Initial users created.");
            }
        };
    }
}


