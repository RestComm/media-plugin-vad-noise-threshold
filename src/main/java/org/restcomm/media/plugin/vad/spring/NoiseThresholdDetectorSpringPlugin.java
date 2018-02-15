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

package org.restcomm.media.plugin.vad.spring;

import org.restcomm.media.plugin.vad.NoiseThresholdDetector;
import org.restcomm.media.plugin.vad.NoiseThresholdDetectorPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Noise Threshold voice activity detector implemented as Spring Boot plugin component.
 *
 * @author Vladimir Morosev (vladimir.morosev@telestax.com)
 *
 */
@Order(0)
@Component
@PropertySource("classpath:media-plugin-vad-noise-threshold.properties")
@ConditionalOnProperty(prefix = "media-plugin-vad-noise-threshold", value = "enabled", havingValue = "true", matchIfMissing = true)
public class NoiseThresholdDetectorSpringPlugin extends NoiseThresholdDetector implements NoiseThresholdDetectorPlugin {

    @Autowired(required=true)
    public NoiseThresholdDetectorSpringPlugin(NoiseThresholdDetectorSpringConfiguration config) {
        super(config.getSilenceLevel());
    }

    int getSilenceLevel() {
        return this.silenceLevel;
    }

}

