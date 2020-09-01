package de.openflorian.alarm.alert;

/*
 * This file is part of Openflorian.
 *
 * Copyright (C) 2015 - 2020 Bastian Kraus
 *
 * Openflorian is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version)
 *
 * Openflorian is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Openflorian.  If not, see <http://www.gnu.org/licenses/>.
 */

import de.openflorian.config.OpenflorianConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory: Alerter Factory
 *
 * This factory is dedicated to create instances of concrete classes implementing
 * {@link Alerter} type.
 */
public class AlerterFactory {

    private static final Logger log = LoggerFactory.getLogger(AlerterFactory.class);

    /**
     * Create concrete {@link Alerter}
     * @return
     */
    public static Alerter alerterOf(OpenflorianConfig.Alerter alerter) throws UnsupportedOperationException {

        log.info("Create alerter of type: " + alerter.getType());
        log.debug(alerter.toString());

        switch(alerter.getType()) {
            case "PowerAlarmAlerter": return new PowerAlarmAlerter(alerter);
            default:
                throw new UnsupportedOperationException("Configured alerter not implemented: " + alerter.getType());
        }

    }

}
