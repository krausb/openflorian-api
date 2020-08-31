package de.openflorian.weather.impl;

import com.github.fedy2.weather.YahooWeatherService;
import com.github.fedy2.weather.data.Channel;
import com.github.fedy2.weather.data.unit.BarometricPressureState;
import com.github.fedy2.weather.data.unit.DegreeUnit;

import de.openflorian.config.OpenflorianConfig;
import de.openflorian.weather.Weather;
import de.openflorian.weather.Weather.PressureState;
import de.openflorian.weather.Weather.Source;
import de.openflorian.weather.WeatherSource;
import org.springframework.beans.factory.annotation.Autowired;

public class YahooWeatherSource implements WeatherSource {

	@Autowired
	private OpenflorianConfig config;
	
	private final String consumerKey;
	private final String consumerSecret;
	private final String woeid;

	public YahooWeatherSource() {
		consumerKey = config.getWeather().getYahooWeatherApi().getConsumerKey();
		consumerSecret = config.getWeather().getYahooWeatherApi().getConsumerSecret();
		woeid = config.getWeather().getYahooWeatherApi().getWoeid();
	}

	@Override
	public Weather getCurrentWeather() throws Exception {
		Weather w = new Weather();

		YahooWeatherService service = new YahooWeatherService();

		Channel c = service.getForecast(woeid, DegreeUnit.CELSIUS);

		if (c.getItem().getCondition() != null) {
			w.setTemperature(c.getItem().getCondition().getTemp());
			w.setConditionCode(c.getItem().getCondition().getCode());
		}
		if (c.getAtmosphere() != null && c.getAtmosphere().getHumidity() != null)
			w.setHumidity(c.getAtmosphere().getHumidity());
		if (c.getAtmosphere() != null && c.getAtmosphere().getPressure() != null)
			w.setPressure(c.getAtmosphere().getPressure());

		if (c.getAtmosphere().getRising() == BarometricPressureState.RISING) {
			w.setPressureState(PressureState.RISING);
		} else if (c.getAtmosphere().getRising() == BarometricPressureState.FALLING) {
			w.setPressureState(PressureState.FALLING);
		} else if (c.getAtmosphere().getRising() == BarometricPressureState.STEADY) {
			w.setPressureState(PressureState.STEADY);
		}

		if (c.getWind() != null && c.getWind().getSpeed() != null)
			w.setWindSpeed(c.getWind().getSpeed());
		if (c.getWind() != null && c.getWind().getDirection() != null)
			w.setWindDirection(c.getWind().getDirection());

		w.setSource(Source.YAHOO);
		w.setTimestamp(System.currentTimeMillis());

		return w;
	}

}
