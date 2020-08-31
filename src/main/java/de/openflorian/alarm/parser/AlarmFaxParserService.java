package de.openflorian.alarm.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.openflorian.EventBusAddresses;
import de.openflorian.OpenflorianApplication;
import de.openflorian.alarm.AlarmFaxEvent;
import de.openflorian.data.model.Operation;

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

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Alarm Fax Parser<br/>
 * <br/>
 * Processes the given alarm fax with regex patterns and transforms it into tesseract.
 * 
 * @author Bastian Kraus <bofh@k-hive.de>
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AlarmFaxParserService extends AbstractVerticle {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private OperationNrParserResponsable firstParserResponsable;

	public AlarmFaxParserService() {
		log.info("Registering " + this.getClass().getName() + " to Vert.X context.");
		OpenflorianApplication.vertx().deployVerticle(this);
	}

	@Override
	public void start() {
		log.info("Starting " + getClass().getSimpleName() + " ...");

		initResponsables();

		if (firstParserResponsable == null)
			throw new IllegalStateException("No firstParserResponsable present/injected.");
		log.info(getClass().getSimpleName() + " started.");

		log.info("Registering EventBus consumer...");
		vertx.eventBus().consumer(EventBusAddresses.ALARMFAX_TRANSFORMED, msg -> parse(msg));
	}

	/**
	 * Helper: Init parser chain of responsibility
	 */
	private void initResponsables() {
		firstParserResponsable = new OperationNrParserResponsable();

		final CityParserResponsable cityParser = new CityParserResponsable();
		firstParserResponsable.setNext(cityParser);

		final StreetParserResponsable streetParser = new StreetParserResponsable();
		cityParser.setNext(streetParser);

		final CrosswayParserResponsable crosswayParser = new CrosswayParserResponsable();
		streetParser.setNext(crosswayParser);

		final PriorityParserResponsable priorityParser = new PriorityParserResponsable();
		crosswayParser.setNext(priorityParser);

		final ObjectParserResponsable objectParser = new ObjectParserResponsable();
		priorityParser.setNext(objectParser);

		final BuzzwordParserResponsable buzzwordParser = new BuzzwordParserResponsable();
		objectParser.setNext(buzzwordParser);

		final KeywordParserResponsable keywordParser = new KeywordParserResponsable();
		buzzwordParser.setNext(keywordParser);

		final GeoCoordinateParserResponsable coordinateParser = new GeoCoordinateParserResponsable();
		keywordParser.setNext(coordinateParser);

		final ResourcesParserResponsable resourcesParser = new ResourcesParserResponsable();
		coordinateParser.setNext(resourcesParser);
	}

	/**
	 * Parses given file from <code>event</code> and extracts
	 * 
	 * @param msg
	 * @throws FileNotFoundException
	 * @returns {@link Operation}
	 */
	public void parse(Message<Object> msg) {
		final AlarmFaxEvent event = (AlarmFaxEvent) msg.body();

		if (firstParserResponsable == null)
			throw new IllegalStateException("No alarm fax parser responsable chain available.");

		final File inputFile = event.getResultFile();
		if (inputFile.exists() && inputFile.canRead()) {
			try {
				log.debug("Parsing file: " + inputFile.getAbsolutePath());
				final Operation op = new Operation();

				final byte[] encoded = Files.readAllBytes(event.getResultFile().toPath());
				final String fax = new String(encoded, "UTF-8");
				parseFax(fax, op);

				OpenflorianApplication.vertx().eventBus().send(EventBusAddresses.ARCHIVE_FILE, inputFile.getAbsolutePath());

				OpenflorianApplication.vertx().eventBus().publish(EventBusAddresses.ALARM_INCURRED, op);
			}
			catch (final IOException e) {
				log.error(e.getMessage(), e);
			}
			catch (final Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		else {
			log.error("Given file '" + inputFile.getAbsolutePath() + "' is not readable or does not exist!");
		}
	}

	/**
	 * Helper: Parse given <code>fax</code> into <code>op</code>
	 * 
	 * @param fax
	 * @param op
	 */
	protected void parseFax(String fax, Operation op) {
		firstParserResponsable.parse(fax, op);
		log.debug("Parsed operation: " + op);

		op.setIncurredAt(new Date());

	}

}
