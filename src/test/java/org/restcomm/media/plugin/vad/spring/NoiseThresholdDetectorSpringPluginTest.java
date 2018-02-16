package org.restcomm.media.plugin.vad.spring;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.restcomm.media.plugin.vad.NoiseThresholdDetector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Henrique Rosa (henrique.rosa@telestax.com) created on 15/02/2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        properties = {
                "media-plugin-vad-noise-threshold.enabled=true",
                "media-plugin-vad-noise-threshold.silenceLevel=42"
        }
)
public class NoiseThresholdDetectorSpringPluginTest {

    @Autowired
    private NoiseThresholdDetector detector;

    @Test
    public void testConfigurationLoading() {
        Assert.assertEquals(42, detector.getSilenceLevel());
    }
}