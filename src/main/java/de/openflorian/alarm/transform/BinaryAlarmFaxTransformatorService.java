package de.openflorian.alarm.transform;

import java.io.*;
import java.util.stream.Collectors;

import de.openflorian.EventBusAddresses;
import de.openflorian.OpenflorianApplication;
import de.openflorian.alarm.AlarmFaxEvent;

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

import io.vertx.core.eventbus.Message;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Binary Alarm Fax Transformator Verticle<br/>
 * <br/>
 * Processes the given alarm fax with an OCR binary like tesseract.
 * 
 * @author Bastian Kraus <bofh@k-hive.de>
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class BinaryAlarmFaxTransformatorService extends AbstractAlarmFaxTransformator {

	public BinaryAlarmFaxTransformatorService() {
		log.info("Registering " + this.getClass().getName() + " to Vert.X context.");
		OpenflorianApplication.vertx().deployVerticle(this);
	}

	@Override
	public void transform(Message<Object> msg) {
		log.debug("Start transforming an alarm fax TIF file...");

		Process ps = null;
		try {
			AlarmFaxEvent event = (AlarmFaxEvent) msg.body();

			Runtime rt = Runtime.getRuntime();

			String resultTextFile = String.format("%s/%s", faxObervationDirectory, event.getResultFile().getName());

			String cmd = "";
			cmd = transformationCmd.replaceAll(transformationCmdVarInput,
					event.getResultFile().getAbsolutePath().replace("\\", "/"));
			cmd = cmd.replaceAll(transformationCmdVarOutput, resultTextFile);

			log.debug("Running cmd: " + cmd);

			ps = rt.exec(cmd);

			int exitCode = ps.waitFor();
			if (exitCode == 0) {
				log.debug(String.format("Successful transformed '%s' to '%s'. Exit code: %d",
						event.getResultFile().getAbsolutePath(), resultTextFile, exitCode));

				vertx.eventBus().send(EventBusAddresses.ARCHIVE_FILE, event.getResultFile().getAbsolutePath());

				vertx.eventBus().send(EventBusAddresses.ALARMFAX_TRANSFORMED,
						new AlarmFaxEvent(new File(resultTextFile + ".txt")));
			} else {
				log.error(String.format("An error occured transforming '%s'. Exit code: %d",
						event.getResultFile().getAbsolutePath(), exitCode));
				String errorMsg = new BufferedReader(new InputStreamReader(ps.getErrorStream())).lines()
						.collect(Collectors.joining(System.getProperty("line.separator")));
				log.error(errorMsg);
			}
			ps.destroy();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		} finally {
			if (ps != null)
				ps.destroy();
		}
	}

}
