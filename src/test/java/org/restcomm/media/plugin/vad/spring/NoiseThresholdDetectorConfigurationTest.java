package org.restcomm.media.plugin.vad.spring;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Henrique Rosa (henrique.rosa@telestax.com) created on 01/03/2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {NoiseThresholdDetectorConfiguration.class}, properties = {"media-plugin-vad-noise-threshold.silenceLevel=55"})
public class NoiseThresholdDetectorConfigurationTest {

    @Autowired
    private NoiseThresholdDetectorConfiguration configuration;

    @Test
    public void testConfigurationValues() {
        Assert.assertEquals(55, configuration.getSilenceLevel());
    }

}
