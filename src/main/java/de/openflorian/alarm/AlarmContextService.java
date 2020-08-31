package de.openflorian.alarm;

/*
 * This file is part of Openflorian.
 * 
 * Copyright (C) 2015  Bastian Kraus
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

import java.util.Date;

import de.openflorian.OpenflorianApplication;
import de.openflorian.data.OperationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.openflorian.EventBusAddresses;
import de.openflorian.data.model.Operation;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Open Florian Alarm Context {@link ServletContextListener} (Singleton)<br/>
 * <br/>
 * {@link InitializingBean} bootstraps the alarming context by starting a listener for monitoring a given directory for
 * new alarming files.
 * 
 * @author Bastian Kraus <bofh@k-hive.de>
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public final class AlarmContextService extends AbstractVerticle {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private static AlarmContextService instance;

	@Autowired
	private OperationRepository operationRepository;

	public static AlarmContextService getInstance() {
		if (instance == null)
			throw new IllegalStateException("No AlarmContext instance available.");
		return instance;
	}

	public AlarmContextService() {
		log.info("Registering " + this.getClass().getName() + " to Vert.X context.");
		OpenflorianApplication.vertx().deployVerticle(this);
	}

	public Operation getCurrentOperation() {
		return this.currentOperation;
	}

	private Operation currentOperation = null;

	@Override
	public void start() {
		AlarmContextService.instance = this;

		log.info("Initialize " + getClass().getSimpleName() + "...");

		log.info("Listening to: " + EventBusAddresses.ALARM_INCURRED);
		vertx.eventBus().consumer(EventBusAddresses.ALARM_INCURRED, msg -> handleAlarm(msg));
		log.info("Listening to: " + EventBusAddresses.ALARM_TAKENOVER);
		vertx.eventBus().consumer(EventBusAddresses.ALARM_TAKENOVER, msg -> handleTakenOver(msg));
		log.info("Listening to: " + EventBusAddresses.ALARM_DISPATCHED);
		vertx.eventBus().consumer(EventBusAddresses.ALARM_DISPATCHED, msg -> handleDispatched(msg));

		log.info(getClass().getSimpleName() + " started!");
	}

	/**
	 * Queue Handler: {@link EventBusAddresses.ALARM_INCURRED}
	 * 
	 * @param msg
	 */
	private void handleAlarm(Message<Object> msg) {
		final Operation operationToAlarm = (Operation) msg.body();
		if (currentOperation != null && !currentOperation.equals(operationToAlarm)) {
			log.warn("Another operation is active - dispatching to done: " + currentOperation);
			currentOperation.setDispatchedAt(new Date());
			operationRepository.save(currentOperation);
			currentOperation = null;
		}
		currentOperation = operationToAlarm;
		if (currentOperation.getId() == 0) {
			try {
				currentOperation.setIncurredAt(new Date());
				currentOperation = operationRepository.save(currentOperation);
			}
			catch (final Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		log.info("Alarm incurred: " + currentOperation);
	}

	/**
	 * Queue Handler: {@link EventBusAddresses.ALARM_INCURRED}
	 * 
	 * @param msg
	 */
	private void handleTakenOver(Message<Object> msg) {
		final Operation o = (Operation) msg.body();
		if (currentOperation.equals(o)) {
			try {
				currentOperation.setTakenOverAt(new Date());
				operationRepository.save(currentOperation);

				log.info("Current operation successfuly resetted.");
			}
			catch (final Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		else {
			if (currentOperation == null)
				log.error("Operation recieved does not match the currently alarmed operation");
			else
				log.error("No operation alarmed currently.");
		}
	}

	/**
	 * Queue Handler: {@link EventBusAddresses.ALARM_INCURRED}
	 * 
	 * @param msg
	 */
	private void handleDispatched(Message<Object> msg) {
		final Operation o = (Operation) msg.body();
		if (currentOperation.equals(o)) {
			try {
				currentOperation.setDispatchedAt(new Date());
				operationRepository.save(currentOperation);

				this.currentOperation = null;
				log.info("Current operation successfuly resetted.");
			}
			catch (final Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		else {
			if (currentOperation == null)
				log.error("Operation recieved does not match the currently alarmed operation");
			else
				log.error("No operation alarmed currently.");
		}
	}

	@Override
	public void stop() throws Exception {
		log.info("Shutting down AlarmContext...");
	}

}
