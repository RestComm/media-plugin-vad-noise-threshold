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

import org.restcomm.media.core.resource.vad.VoiceActivityDetector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Detector for speech signal in provided chunk of audio samples.
 *
 * @author Vladimir Morosev (vladimir.morosev@telestax.com)
 */
@Order(0)
@Component
@ConditionalOnProperty(value = "media-plugin-vad-noise-threshold.enabled", havingValue = "true")
public class NoiseThresholdDetector implements VoiceActivityDetector {

    protected final int silenceLevel;

    public NoiseThresholdDetector(@Value("${media-plugin-vad-noise-threshold.silenceLevel}") final int silenceLevel) {
        this.silenceLevel = silenceLevel;
    }

    /**
     * Checks the sample buffer for speech signal.
     *
     * @param data   buffer with samples
     * @param offset the position of first sample in buffer
     * @param len    the number of samples
     * @return true if silence detected
     */
    @Override
    public boolean detect(byte[] data, int offset, int len) {
        int[] correllation = new int[len];
        for (int i = offset; i < len - 1; i += 2) {
            correllation[i] = (data[i] & 0xff) | (data[i + 1] << 8);
        }

        double mean = mean(correllation);
        return mean > silenceLevel;
    }

    private double mean(int[] m) {
        double sum = 0;
        for (int i = 0; i < m.length; i++) {
            sum += m[i];
        }
        return sum / m.length;
    }

    public int getSilenceLevel() {
        return silenceLevel;
    }
}
