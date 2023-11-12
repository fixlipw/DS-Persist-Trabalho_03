package com.ufc.dspersist;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.ufc.dspersist.view.LoginFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.swing.*;

@SpringBootApplication(scanBasePackages = "com.ufc.dspersist")
@EnableJpaRepositories("com.ufc.dspersist.repository")
@EntityScan("com.ufc.dspersist.model")
@Slf4j
public class Main implements CommandLineRunner {

    private LoginFrame loginFrame;

    @Autowired
    public void setLoginFrame(LoginFrame loginFrame) {
        this.loginFrame = loginFrame;
    }

    public static void main(String[] args) {
        FlatIntelliJLaf.setup();
        UIManager.put( "Button.arc", 20);
        UIManager.put( "Component.arc", 20);
        UIManager.put( "ProgressBar.arc", 20);
        UIManager.put( "TextComponent.arc", 20);
        SwingUtilities.invokeLater(() -> {
            SpringApplicationBuilder builder = new SpringApplicationBuilder(Main.class);
            builder.headless(false).run(args);
        });
    }

    @Override
    public void run(String... args) {
        loginFrame.setVisible(true);
    }
}
