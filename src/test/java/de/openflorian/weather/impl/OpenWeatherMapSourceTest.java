package de.openflorian.weather.impl;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.openflorian.weather.Weather;

public class OpenWeatherMapSourceTest {

	private static final Logger log = LoggerFactory.getLogger(OpenWeatherMapSourceTest.class);

	@Test
	public void test() throws Exception {
		Weather w = new OpenWeatherMapSource().getCurrentWeather();
		log.info(w.toString());
		System.out.print(w.toString());
	}

}
