package de.openflorian.ws.impl;

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

import javax.persistence.EntityNotFoundException;

import de.openflorian.EventBusAddresses;
import de.openflorian.OpenflorianApplication;
import de.openflorian.alarm.AlarmContextService;
import de.openflorian.data.OperationRepository;
import de.openflorian.data.model.Operation;
import de.openflorian.ws.AlarmResource;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerErrorException;

import java.util.Optional;

/**
 * Impl of {@link AlarmResource}
 * 
 * @author Bastian Kraus <bofh@k-hive.de>
 */
@Api(value = "/alarm", description = "Operations for alarm handling")
@RestController
@RequestMapping("alarm")
public class AlarmResourceImpl implements AlarmResource {

	private static final Logger log = LoggerFactory.getLogger(OperationResourceImpl.class);

	@Autowired
	private OperationRepository operationRepository;

	public AlarmResourceImpl() {
		log.info("Registering " + this.getClass().getName() + " to Spring context.");
	}

	@Override
	public Operation getCurrentOperation() {
		final Operation current = AlarmContextService.getInstance().getCurrentOperation();
		if(current != null)
			return current;
		else
			throw new ResponseStatusException(HttpStatus.NO_CONTENT);
	}

	@Override
	public void incurre(long operationId) {
		try {
			final Optional<Operation> o = operationRepository.findById((int) operationId);
			OpenflorianApplication.vertx().eventBus().publish(
					EventBusAddresses.ALARM_INCURRED,
					o.orElseThrow(EntityNotFoundException::new)
			);
		}
		catch (final EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No operation with ID " + operationId + " found.");
		}
		catch (final Exception e) {
			throw new ServerErrorException(e.getMessage(), e);
		}
	}

	@Override
	public void incurreLast() {
		try {
			final Optional<Operation> o = operationRepository.findFirstByOrderByIdDesc();
			OpenflorianApplication.vertx().eventBus().publish(
					EventBusAddresses.ALARM_INCURRED,
					o.orElseThrow(EntityNotFoundException::new)
			);
		}
		catch (final EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No operation found.");
		}
		catch (final Exception e) {
			throw new ServerErrorException(e.getMessage(), e);
		}
	}

	@Override
	public void takeOver() {
		try {
			final Operation o = AlarmContextService.getInstance().getCurrentOperation();
			if (o != null)
				OpenflorianApplication.vertx().eventBus().publish(EventBusAddresses.ALARM_TAKENOVER, o);
			else
				throw new EntityNotFoundException();
		}
		catch (final EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No operation in progress.");
		}
		catch (final Exception e) {
			throw new ServerErrorException(e.getMessage(), e);
		}
	}

	@Override
	public void dispatch() {
		try {
			final Operation o = AlarmContextService.getInstance().getCurrentOperation();
			if (o != null)
				OpenflorianApplication.vertx().eventBus().publish(EventBusAddresses.ALARM_DISPATCHED, o);
			else
				throw new EntityNotFoundException();
		}
		catch (final EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No operation in progress.");
		}
		catch (final Exception e) {
			throw new ServerErrorException(e.getMessage(), e);
		}
	}

}
