package de.openflorian;

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

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import de.openflorian.alarm.alert.AlerterFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.openflorian.alarm.*;
import de.openflorian.data.model.Operation;
import de.openflorian.alarm.alert.PowerAlarmAlerter;
import de.openflorian.config.OpenflorianConfig;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageCodec;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

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

/**
 * Openflorian Application<br/>
 * <br/>
 * Responsable for bootstrapping the whole SpringApplication context.
 * 
 * @author Bastian Kraus
 */
@SpringBootApplication
@EnableConfigurationProperties(OpenflorianConfig.class)
public class OpenflorianApplication implements ApplicationContextAware {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private ApplicationContext ctx;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ctx = applicationContext;
	}

	private static OpenflorianConfig config;

	public static OpenflorianConfig getConfig() {
		return config;
	}

	private static Vertx vertx;

	public static Vertx vertx() {
		return vertx;
	}

	public static void main(String[] args) {
		System.setProperty("server.servlet.context-path", "/api");
		ConfigurableApplicationContext context = SpringApplication.run(OpenflorianApplication.class, args);
	}

	@PostConstruct
	private void initialize() {
		log.info("Initializing OpenFlorian Context...");
		config = (OpenflorianConfig)ctx.getBean("openflorianConfig");

		log.info("Creating Vert.X context...");
		vertx = Vertx.vertx();

		registerMessageCodecs();
		deployVerticles();

		log.info("... Context initialized!");
	}

	@PreDestroy
	public void shutdown() {
		log.info("Shutting down OpenFlorian Context...");

		log.info("Releasing Vert.X resources...");
		vertx.close();

		vertx.close(new Handler<AsyncResult<Void>>() {

			@Override
			public void handle(AsyncResult<Void> event) {
				if (event.succeeded())
					log.info("Shutdown complete. Bye :-)");
				else
					log.error(event.cause().getMessage(), event.cause());
			}
		});

	}

	/**
	 * Helper: Deploy Verticles to VertX Context
	 */
	private void deployVerticles() {
		log.info("Deploying configured additional services...");

		if(!config.getAlerter().isEmpty()) {
			config.getAlerter().stream().forEach(a -> vertx.deployVerticle(AlerterFactory.alerterOf(a)));
		}
	}

	/**
	 * Helper: Register {@link MessageCodec}s to the {@link EventBus} for
	 * decoding and encoding custom message objects
	 */
	private void registerMessageCodecs() {
		log.info("Register MessageCodes for Vert.X EventBus Message Transport...");
		vertx.eventBus().registerDefaultCodec(Operation.class, new OperationMessageCodec());
		vertx.eventBus().registerDefaultCodec(AlarmFaxEvent.class, new AlarmFaxEventMessageCodec());
	}

}
