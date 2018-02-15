package org.restcomm.media.plugin.vad.spring;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Henrique Rosa (henrique.rosa@telestax.com) created on 15/02/2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {NoiseThresholdDetectorSpringPlugin.class, NoiseThresholdDetectorSpringConfiguration.class})
public class NoiseThresholdDetectorSpringPluginTest {

    @Autowired
    private NoiseThresholdDetectorSpringPlugin plugin;

    @Test
    public void testConfigurationLoading() {
        Assert.assertEquals(93, plugin.getSilenceLevel());
    }

}