package de.openflorian.weather;

import de.openflorian.weather.impl.OpenWeatherMapSource;
import de.openflorian.weather.impl.YahooWeatherSource;

/**
 * Factory for creating a {@link WeatherSource} impl instance.
 * 
 * @author Bastian Kraus <bofh@k-hive.de>
 */
public class WeatherSourceFactory {

	/**
	 * Create a {@link WeatherSource} instance by given <code>source</code>
	 * 
	 * @param source
	 * @return {@link WeatherSource}
	 */
	public static WeatherSource get(Weather.Source source) {
		switch (source) {
		case YAHOO:
			return new YahooWeatherSource();
		case OPENWEATHERMAP:
		default:
			return new OpenWeatherMapSource();
		}
	}

}
