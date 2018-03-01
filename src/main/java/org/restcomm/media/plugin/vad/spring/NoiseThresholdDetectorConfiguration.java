package org.restcomm.media.plugin.vad.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Henrique Rosa (henrique.rosa@telestax.com) created on 01/03/2018
 */
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "media-plugin-vad-noise-threshold")
public class NoiseThresholdDetectorConfiguration {

    private int silenceLevel;

    public NoiseThresholdDetectorConfiguration() {
        this.silenceLevel = 10;
    }

    public int getSilenceLevel() {
        return silenceLevel;
    }

    public void setSilenceLevel(int silenceLevel) {
        this.silenceLevel = silenceLevel;
    }

}
