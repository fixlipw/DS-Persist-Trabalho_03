package com.ufc.dspersist;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.ufc.dspersist.view.LoginFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import javax.swing.*;

@SpringBootApplication(scanBasePackages = "com.ufc.dspersist")
@EnableJpaRepositories("com.ufc.dspersist.repository.jpa")
@EnableMongoRepositories("com.ufc.dspersist.repository.mongo")
@EntityScan("com.ufc.dspersist.model")
public class Main implements CommandLineRunner {

    private LoginFrame loginFrame;

    public static void main(String[] args) {
        FlatIntelliJLaf.setup();
        UIManager.put("Button.arc", 20);
        UIManager.put("Component.arc", 20);
        UIManager.put("ProgressBar.arc", 20);
        UIManager.put("TextComponent.arc", 20);
        SwingUtilities.invokeLater(() -> {
            SpringApplicationBuilder builder = new SpringApplicationBuilder(Main.class);
            builder.headless(false).run(args);
        });
    }

    @Autowired
    public void setLoginFrame(LoginFrame loginFrame) {
        this.loginFrame = loginFrame;
    }

    @Override
    public void run(String... args) {
        loginFrame.setVisible(true);
    }
}
