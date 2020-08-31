package de.openflorian.alarm.transform;

import de.openflorian.OpenflorianApplication;
import de.openflorian.alarm.AlarmFaxEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.openflorian.EventBusAddresses;
import de.openflorian.config.OpenflorianConfig;
import de.openflorian.util.StringUtils;

/*
 * This file is part of Openflorian.
 * 
 * Copyright (C) 2016  Bastian Kraus
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
import io.vertx.core.Future;
import io.vertx.core.eventbus.Message;

/**
 * Alarm Fax Transformator<br/>
 * <br/>
 * Processes the given alarm fax with an OCR binary like tesseract.
 * 
 * @author Bastian Kraus <bofh@k-hive.de>
 */
public abstract class AbstractAlarmFaxTransformator extends AbstractVerticle {

	protected final Logger log = LoggerFactory.getLogger(getClass());

	public static final String CONFIG_TRANSFORMATION_CMD = "alarm.transform.cmd";
	public static final String CONFIG_TRANSFORMATION_VAR_OUTPUT = "alarm.transform.var.output";
	public static final String CONFIG_TRANSFORMATION_VAR_INPUT = "alarm.transform.var.input";

	protected String transformationCmd;
	protected String faxObervationDirectory;

	protected String transformationCmdVarInput;
	protected String transformationCmdVarOutput;

	@Override
	public void start(Future<Void> startFuture) {
		log.info("Setting up Fax Transformator Verticle...");

		OpenflorianConfig config = OpenflorianApplication.getConfig();
		transformationCmd = config.getFaxTransformer().getCmd();
		transformationCmdVarInput = config.getFaxTransformer().getVars().getInputVar();
		transformationCmdVarOutput = config.getFaxTransformer().getVars().getOutputVar();
		faxObervationDirectory = config.getFaxObserver().getObserverDir();

		if (StringUtils.isEmpty(transformationCmd))
			throw new IllegalStateException("Transformation command configuration is missing.");
		else if (StringUtils.isEmpty(transformationCmdVarInput))
			throw new IllegalStateException("Input var configuration is missing.");
		else if (StringUtils.isEmpty(transformationCmdVarOutput))
			throw new IllegalStateException("Output var configuration is missing.");
		else if (StringUtils.isEmpty(faxObervationDirectory))
			throw new IllegalStateException("Fax observation dir configuration is missing.");
		else {
			log.info("Transformation cmd: " + transformationCmd);
			log.info("Input var pattern: " + transformationCmdVarInput);
			log.info("Output var pattern: " + transformationCmdVarOutput);
			log.info("Fax observation dir: " + faxObervationDirectory);
		}

		log.info("Register Bus Listener...");
		vertx.eventBus().consumer(EventBusAddresses.ALARMFAX_NEWFAX, msg -> transform(msg));

		startFuture.complete();
	}

	/**
	 * Transform the incoming file from
	 * {@link AlarmFaxEvent#getResultFile()} with
	 * 
	 * @param msg
	 */
	public abstract void transform(Message<Object> msg);

}