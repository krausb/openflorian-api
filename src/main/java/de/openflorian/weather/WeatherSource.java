package de.openflorian.weather;

/**
 * Abstract Weather Source for grepping weather data
 * 
 * @author Bastian Kraus <bofh@k-hive.de>
 */
public interface WeatherSource {

	/**
	 * Method for grepping weather state from implementing source.
	 * 
	 * @return {@link Weather}
	 * @throws Exception
	 */
	Weather getCurrentWeather() throws Exception;

}
