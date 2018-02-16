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

/**
 * Detector for speech signal in provided chunk of audio samples.
 *
 * @author Vladimir Morosev (vladimir.morosev@telestax.com)
 */
public class NoiseThresholdDetector implements VoiceActivityDetector {

    private final int silenceLevel;

    public NoiseThresholdDetector(int silenceLevel) {
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
        final int[] correlation = new int[len];
        for (int i = offset; i < len - 1; i += 2) {
            correlation[i] = (data[i] & 0xff) | (data[i + 1] << 8);
        }

        double mean = mean(correlation);
        return mean > silenceLevel;
    }

    private double mean(int[] m) {
        double sum = 0;
        for (int i = 0; i < m.length; i++) {
            sum += m[i];
        }
        return sum / m.length;
    }

}
