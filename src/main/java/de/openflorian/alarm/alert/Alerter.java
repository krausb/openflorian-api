package de.openflorian.alarm.alert;

/*
 * This file is part of Openflorian.
 *
 * Copyright (C) 2015 - 2018  Bastian Kraus
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

import de.openflorian.EventBusAddresses;
import de.openflorian.config.OpenflorianConfig;
import de.openflorian.data.model.Operation;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ValidationException;

/**
 * Abstract UrlAlerter
 *
 * Provides basic capabilities for implementation of a concrete urlAlerter.
 */
public abstract class Alerter extends AbstractVerticle {

    private static final Logger log = LoggerFactory.getLogger(Alerter.class);

    @Override
    public void start() {
        log.info("Starting " + getClass().getSimpleName() + " ...");

        log.info("Validating configuration...");
        validate();

        log.info("Registering EventBus consumer...");
        vertx.eventBus().consumer(EventBusAddresses.ALARM_INCURRED, msg -> alert(msg));
    }

    /**
     * Alerting delegate for VertX messages.
     *
     * @param msg
     */
    public void alert(Message<Object> msg) {
        try {
            final Operation operation = (Operation) msg.body();
            alert(operation);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * Implementation of a concrete UrlAlerter.
     *
     * @param operation
     */
    public abstract void alert(Operation operation) throws Exception;

    /**
     * Validate the provided configuration. This method will be called upfront EventBus registration
     * on {@link Alerter#start()}
     *
     * @throws ValidationException
     */
    public abstract void validate() throws ValidationException;

}
