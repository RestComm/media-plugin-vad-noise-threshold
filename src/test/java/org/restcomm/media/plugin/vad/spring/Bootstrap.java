package org.restcomm.media.plugin.vad.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author oleg.agafonov@telestax.com (Oleg Agafonov)
 */
@SpringBootApplication(scanBasePackages = "org.restcomm.media.plugin.vad")
public class Bootstrap {

    public static void main(String[] args) {
        new SpringApplication(Bootstrap.class).run(args);
    }
}
