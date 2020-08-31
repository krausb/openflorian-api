package de.openflorian.weather;

import de.openflorian.OpenflorianApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Service for provisioning weather
 * 
 * @author Bastian Kraus <bofh@k-hive.de>
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class WeatherProvisioningService	 extends AbstractVerticle {

	private static final Logger log = LoggerFactory.getLogger(WeatherProvisioningService.class);

	private WeatherSource weatherSource;

	private static Weather currentWeather = null;

	public static Weather getCurrentWeather() {
		return currentWeather;
	}

	public WeatherProvisioningService() {
		log.info("Registering " + this.getClass().getName() + " to Vert.X context.");
		OpenflorianApplication.vertx().deployVerticle(this);
	}

	@Override
	public void start() throws Exception {
		log.info("Initialize " + getClass().getSimpleName() + "...");

		// start weather polling
		weatherSource = WeatherSourceFactory.get(Weather.Source.OPENWEATHERMAP);
		currentWeather = weatherSource.getCurrentWeather();

		vertx.setPeriodic(OpenflorianApplication.getConfig().getWeather().getLoadPeriod() * 1000, new Handler<Long>() {

			@Override
			public void handle(Long event) {
				if (weatherSource == null) {
					log.error("No weather source present.");
					return;
				}
				try {
					if (log.isDebugEnabled())
						log.debug("Loading weather with " + weatherSource.getClass().getSimpleName());
					currentWeather = weatherSource.getCurrentWeather();
					if (log.isDebugEnabled())
						log.debug("Current weather: " + currentWeather);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		});

		log.info(getClass().getSimpleName() + " started!");

		super.start();
	}

}
