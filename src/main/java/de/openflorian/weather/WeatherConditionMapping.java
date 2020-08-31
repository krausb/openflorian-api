package de.openflorian.weather;

import java.util.HashMap;
import java.util.Map;

/**
 * Mapping Factory<br/>
 * <br/>
 * Maps yahoo weather api condition codes to weather icon css classes.<br/>
 * <br/>
 * See: {@link https://developer.yahoo.com/weather/documentation.html#codes}
 * 
 * @author Bastian Kraus <bofh@k-hive.de>
 */
public class WeatherConditionMapping {

	private static Map<Integer, String> yahooMappingConditions;
	private static Map<Integer, String> openWeatherMapMappingConditions;

	public static String mapYahooCode(int conditionCode) {
		if (yahooMappingConditions == null)
			initYahooMapping();
		return yahooMappingConditions.get(conditionCode);
	}

	public static String mapOpenWeatherMapCode(int conditionCode) {
		if (openWeatherMapMappingConditions == null)
			initOpenWeatherMapMapping();
		return openWeatherMapMappingConditions.get(conditionCode);
	}

	/**
	 * Helper: Initialize mapping for weather yahooMappingConditions
	 */
	private static void initYahooMapping() {
		yahooMappingConditions = new HashMap<Integer, String>();
		yahooMappingConditions.put(0, "wi-tornado");
		yahooMappingConditions.put(1, "wi-hurricane");
		yahooMappingConditions.put(2, "wi-hurricane");
		yahooMappingConditions.put(3, "wi-thunderstorm");
		yahooMappingConditions.put(4, "wi-thunderstorm");
		yahooMappingConditions.put(5, "wi-sleet");
		yahooMappingConditions.put(6, "wi-sleet");
		yahooMappingConditions.put(7, "wi-sleet");
		yahooMappingConditions.put(8, "wi-sleet");
		yahooMappingConditions.put(9, "wi-sleet wi-snowflake-cold");
		yahooMappingConditions.put(10, "wi-showers wi-snowflake-cold");
		yahooMappingConditions.put(11, "wi-showers");
		yahooMappingConditions.put(12, "wi-showers");
		yahooMappingConditions.put(13, "wi-strong-wind wi-snow");
		yahooMappingConditions.put(14, "wi-snow");
		yahooMappingConditions.put(15, "wi-strong-wind wi-snow");
		yahooMappingConditions.put(16, "wi-snow");
		yahooMappingConditions.put(17, "wi-hail");
		yahooMappingConditions.put(18, "wi-sleet");
		yahooMappingConditions.put(19, "wi-dust");
		yahooMappingConditions.put(20, "wi-dust");
		yahooMappingConditions.put(21, "wi-dust");
		yahooMappingConditions.put(22, "wi-smog");
		yahooMappingConditions.put(23, "wi-cloudy-gusts");
		yahooMappingConditions.put(24, "wi-strong-wind");
		yahooMappingConditions.put(25, "wi-snowflake-cold");
		yahooMappingConditions.put(26, "wi-cloudy");
		yahooMappingConditions.put(27, "wi-night-alt-cloudy");
		yahooMappingConditions.put(28, "wi-day-cloudy");
		yahooMappingConditions.put(29, "wi-night-alt-cloudy");
		yahooMappingConditions.put(30, "wi-day-cloudy");
		yahooMappingConditions.put(31, "wi-stars");
		yahooMappingConditions.put(32, "wi-day-sunny");
		yahooMappingConditions.put(33, "wi-night-clear");
		yahooMappingConditions.put(34, "wi-day-sunny");
		yahooMappingConditions.put(35, "wi-hail");
		yahooMappingConditions.put(36, "wi-thermometer");
		yahooMappingConditions.put(37, "wi-storm-showers");
		yahooMappingConditions.put(38, "wi-storm-showers");
		yahooMappingConditions.put(39, "wi-storm-showers");
		yahooMappingConditions.put(40, "wi-showers");
		yahooMappingConditions.put(41, "wi-snow-wind");
		yahooMappingConditions.put(42, "wi-snow");
		yahooMappingConditions.put(43, "wi-snow");
		yahooMappingConditions.put(44, "wi-cloudy");
		yahooMappingConditions.put(45, "wi-storm-showers");
		yahooMappingConditions.put(46, "wi-snow");
		yahooMappingConditions.put(47, "wi-storm-showers");
	}

	private static void initOpenWeatherMapMapping() {
		openWeatherMapMappingConditions = new HashMap<Integer, String>();

		openWeatherMapMappingConditions.put(Integer.MIN_VALUE, "wi-na");

		openWeatherMapMappingConditions.put(200, "wi-thunderstorm");
		openWeatherMapMappingConditions.put(201, "wi-thunderstorm");
		openWeatherMapMappingConditions.put(202, "wi-thunderstorm");
		openWeatherMapMappingConditions.put(210, "wi-thunderstorm");
		openWeatherMapMappingConditions.put(211, "wi-thunderstorm");
		openWeatherMapMappingConditions.put(212, "wi-thunderstorm");
		openWeatherMapMappingConditions.put(221, "wi-thunderstorm");
		openWeatherMapMappingConditions.put(230, "wi-thunderstorm");
		openWeatherMapMappingConditions.put(231, "wi-thunderstorm");
		openWeatherMapMappingConditions.put(232, "wi-thunderstorm");

		openWeatherMapMappingConditions.put(300, "wi-rain-mix");
		openWeatherMapMappingConditions.put(301, "wi-rain-mix");
		openWeatherMapMappingConditions.put(302, "wi-rain-mix");
		openWeatherMapMappingConditions.put(310, "wi-rain-mix");
		openWeatherMapMappingConditions.put(311, "wi-rain-mix");
		openWeatherMapMappingConditions.put(312, "wi-rain-mix");
		openWeatherMapMappingConditions.put(321, "wi-rain-mix");

		openWeatherMapMappingConditions.put(600, "wi-snow");
		openWeatherMapMappingConditions.put(601, "wi-snow");
		openWeatherMapMappingConditions.put(602, "wi-strong-wind wi-snow");
		openWeatherMapMappingConditions.put(621, "wi-snow");
		openWeatherMapMappingConditions.put(611, "wi-sleet");

		openWeatherMapMappingConditions.put(701, "wi-dust");
		openWeatherMapMappingConditions.put(711, "wi-smog");
		openWeatherMapMappingConditions.put(721, "wi-dust");
		openWeatherMapMappingConditions.put(731, "wi-dust");
		openWeatherMapMappingConditions.put(741, "wi-dust");

		openWeatherMapMappingConditions.put(500, "wi-showers");
		openWeatherMapMappingConditions.put(501, "wi-showers");
		openWeatherMapMappingConditions.put(502, "wi-storm-showers");
		openWeatherMapMappingConditions.put(503, "wi-storm-showers");
		openWeatherMapMappingConditions.put(504, "wi-storm-showers");
		openWeatherMapMappingConditions.put(511, "wi-snow");
		openWeatherMapMappingConditions.put(520, "wi-showers");
		openWeatherMapMappingConditions.put(521, "wi-showers");
		openWeatherMapMappingConditions.put(522, "wi-storm-showers");

		openWeatherMapMappingConditions.put(800, "wi-day-sunny");
		openWeatherMapMappingConditions.put(801, "wi-cloud");
		openWeatherMapMappingConditions.put(802, "wi-cloudy");
		openWeatherMapMappingConditions.put(803, "wi-cloudy");
		openWeatherMapMappingConditions.put(804, "wi-cloudy");

		openWeatherMapMappingConditions.put(900, "wi-tornado");
		openWeatherMapMappingConditions.put(901, "wi-hurricane");
		openWeatherMapMappingConditions.put(902, "wi-hurricane");
		openWeatherMapMappingConditions.put(903, "wi-snowflake-cold");
		openWeatherMapMappingConditions.put(904, "wi-thermometer");
		openWeatherMapMappingConditions.put(905, "wi-strong-wind");
		openWeatherMapMappingConditions.put(906, "wi-hail");
	}

}
