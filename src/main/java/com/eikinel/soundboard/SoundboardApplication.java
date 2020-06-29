package com.eikinel.soundboard;

import com.eikinel.soundboard.fileManager.models.FileManagerProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        FileManagerProperties.class
})
public class SoundboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoundboardApplication.class, args);
    }

}
