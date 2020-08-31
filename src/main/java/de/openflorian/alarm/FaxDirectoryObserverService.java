package de.openflorian.alarm;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

import de.openflorian.OpenflorianApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.openflorian.EventBusAddresses;
import de.openflorian.config.OpenflorianConfig;

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
import io.vertx.core.Future;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Alarm Telefax Directory Observer<br/>
 * <br/>
 * Observes a given directory for
 * 
 * @author Bastian Kraus <bofh@k-hive.de>
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class FaxDirectoryObserverService extends AbstractVerticle {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final File observingDirectory;

	private final long incurreDelay;

	private WatchService watcher;
	private WatchKey key;

	public FaxDirectoryObserverService() {
		log.info("Registering " + this.getClass().getName() + " to Vert.X context.");
		OpenflorianApplication.vertx().deployVerticle(this);

		final String dir = OpenflorianApplication.getConfig().getFaxObserver().getObserverDir();
		log.debug("Fax observing directory: " + dir);
		this.observingDirectory = new File(dir);

		if (this.observingDirectory == null || !this.observingDirectory.isDirectory())
			throw new IllegalStateException("Configuration does not contain '" + dir + "'");
		else
			log.info("Observing directory: " + this.observingDirectory.getAbsolutePath());

		incurreDelay = OpenflorianApplication.getConfig().getFaxObserver().getIncurreDelay();

		if (this.incurreDelay == 0) {
			throw new IllegalStateException(
					"Malformed configuration: faxObserver.incurreDelay must be set and may not be 0.");
		}
		else {
			log.info("Fax incurre delay: " + this.incurreDelay);
		}
	}

	@Override
	public void start(Future<Void> startFuture) {

		log.info("Starting Directory Observer Verticle...");

		// SETUP
		watcher = null;
		try {
			watcher = FileSystems.getDefault().newWatchService();
			this.observingDirectory.toPath().register(watcher, StandardWatchEventKinds.ENTRY_CREATE);
		}
		catch (final IOException e) {
			log.error(e.getMessage(), e);
		}

		if (watcher == null)
			throw new IllegalStateException("No FileSystem WatcherService could be created.");

		vertx.setPeriodic(500, arg0 -> {
			try {
				processEvents();
			}
			catch (final IOException e1) {
				log.error(e1.getMessage(), e1);
			}
			catch (final Exception e2) {
				log.error(e2.getMessage(), e2);
			}
		});

		startFuture.complete();
	}

	@Override
	public void stop() {
		log.info("Stopping Directory Observer Verticle...");
		try {
			if (key != null) {
				key.cancel();
				key.pollEvents();
			}
			watcher.close();
		}
		catch (final IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * Helper: Process directory events
	 */
	private synchronized void processEvents() throws IOException, Exception {

		while (true) {
			// wait for key to be signaled
			key = watcher.poll();
			if (key == null)
				break;

			final List<WatchEvent<?>> events = key.pollEvents();
			for (final WatchEvent<?> event : events) {
				final WatchEvent.Kind<?> kind = event.kind();

				log.debug("Directory Event incurred: " + kind.name());

				if (kind == StandardWatchEventKinds.OVERFLOW) {
					continue;
				}
				else if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
					log.debug("ENTRY_CREATE event triggered...");
					@SuppressWarnings("unchecked")
					final WatchEvent<Path> ev = (WatchEvent<Path>) event;
					final Path filename = ev.context();

					final Path child = this.observingDirectory.toPath().resolve(filename);
					log.debug("File: " + child.toFile().getAbsolutePath());

					final String contentType = Files.probeContentType(child);
					if (contentType != null && contentType.startsWith("image/")) {
						vertx.setTimer(this.incurreDelay, timerId -> {
							final AlarmFaxEvent alarmFaxEvent = new AlarmFaxEvent(child.toFile());
							vertx.eventBus().send(EventBusAddresses.ALARMFAX_NEWFAX, alarmFaxEvent);
							log.debug("Successfuly published to " + EventBusAddresses.ALARMFAX_NEWFAX + ".");
						});
						break;
					}
				}
			}

			if (key != null) {
				final boolean valid = key.reset();
				key.pollEvents();
				if (!valid) {
					break;
				}
			}
		}
	}

}
