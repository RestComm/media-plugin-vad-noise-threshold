/*
 * TeleStax, Open Source Cloud Communications
 * Copyright 2011-2018, Telestax Inc and individual contributors
 * by the @authors tag.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.restcomm.media.plugin.vad;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.restcomm.media.core.resource.vad.VoiceActivityDetector;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.*;

/**
  * @author Vladimir Morosev (vladimir.morosev@telestax.com)
  */
public class NoiseThresholdDetectorTest {

    private static final Logger log = Logger.getLogger(NoiseThresholdDetectorTest.class);

    @Test
    public void testSilence() {
        // given
        final URL inputFileUrl = this.getClass().getResource("/test_sound_mono_48_silence.pcm");
        final int packetSize = 480;
        final VoiceActivityDetector detector = new NoiseThresholdDetector(10);

        // when
        boolean detected = false;
        try (FileInputStream inputStream = new FileInputStream(inputFileUrl.getFile())) {
            byte[] input = new byte[2 * packetSize];
            while (inputStream.read(input) == 2 * packetSize) {
                detected = detected || detector.detect(input, 0, input.length);
            }
        } catch (IOException e) {
            log.error("Could not read file", e);
            fail("Speech Detector test file access error");
        }

        // then
        assertFalse(detected);
    }

    @Test
    public void testNoiseOverSilenceThreshold() {
        // given
        final URL inputFileUrl = this.getClass().getResource("/test_sound_mono_48_speech.pcm");
        final int silenceLevel = 327;
        final VoiceActivityDetector detector = new NoiseThresholdDetector(silenceLevel);

        // when
        boolean detected = false;
        final int packetSize = 480;
        try (FileInputStream inputStream = new FileInputStream(inputFileUrl.getFile())) {
            byte[] input = new byte[2 * packetSize];
            while (inputStream.read(input) == 2 * packetSize) {
                detected = detected || detector.detect(input, 0, input.length);
            }
        } catch (IOException exc) {
            log.error("IOException: " + exc.getMessage());
            fail("Speech Detector test file access error");
        }

        // then
        assertTrue(detected);
    }

    @Test
    public void testNoiseUnderSilenceThreshold() {
        // given
        final URL inputFileUrl = this.getClass().getResource("/test_sound_mono_48_speech.pcm");
        final int silenceLevel = 328;
        final VoiceActivityDetector detector = new NoiseThresholdDetector(silenceLevel);

        // when
        boolean detected = false;
        final int packetSize = 480;
        try (FileInputStream inputStream = new FileInputStream(inputFileUrl.getFile())) {
            byte[] input = new byte[2 * packetSize];
            while (inputStream.read(input) == 2 * packetSize) {
                detected = detected || detector.detect(input, 0, input.length);
            }
        } catch (IOException exc) {
            log.error("IOException: " + exc.getMessage());
            fail("Speech Detector test file access error");
        }

        // then
        assertFalse(detected);
    }

}
