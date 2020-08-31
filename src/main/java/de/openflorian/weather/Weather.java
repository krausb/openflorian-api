package de.openflorian.weather;

import java.io.Serializable;

/**
 * Weather DTO
 * 
 * @author Bastian Kraus <bofh@k-hive.de>
 */
public class Weather implements Serializable {
	private static final long serialVersionUID = -2663924000477491462L;

	private double temperature;
	private int conditionCode;
	private double humidity;
	private double pressure;
	private PressureState pressureState;
	private double windSpeed;
	private int windDirection;

	private long timestamp;
	private Source source;

	/**
	 * Barometric Pressure State
	 * 
	 * @author Bastian Kraus <bofh@k-hive.de>
	 */
	public static enum PressureState {
		RISING, FALLING, STEADY
	}

	/**
	 * Weather Source
	 * 
	 * @author Bastian Kraus <bofh@k-hive.de>
	 */
	public static enum Source {
		YAHOO, OPENWEATHERMAP
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public int getConditionCode() {
		return conditionCode;
	}

	public void setConditionCode(int conditionCode) {
		this.conditionCode = conditionCode;
	}

	public double getHumidity() {
		return humidity;
	}

	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}

	public double getPressure() {
		return pressure;
	}

	public void setPressure(double pressure) {
		this.pressure = pressure;
	}

	public PressureState getPressureState() {
		return pressureState;
	}

	public void setPressureState(PressureState pressureState) {
		this.pressureState = pressureState;
	}

	public double getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(double windSpeed) {
		this.windSpeed = windSpeed;
	}

	public int getWindDirection() {
		return windDirection;
	}

	public void setWindDirection(int windDirection) {
		this.windDirection = windDirection;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + conditionCode;
		long temp;
		temp = Double.doubleToLongBits(humidity);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(pressure);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((pressureState == null) ? 0 : pressureState.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		temp = Double.doubleToLongBits(temperature);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + windDirection;
		temp = Double.doubleToLongBits(windSpeed);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Weather))
			return false;
		Weather other = (Weather) obj;
		if (conditionCode != other.conditionCode)
			return false;
		if (Double.doubleToLongBits(humidity) != Double.doubleToLongBits(other.humidity))
			return false;
		if (Double.doubleToLongBits(pressure) != Double.doubleToLongBits(other.pressure))
			return false;
		if (pressureState != other.pressureState)
			return false;
		if (source != other.source)
			return false;
		if (Double.doubleToLongBits(temperature) != Double.doubleToLongBits(other.temperature))
			return false;
		if (windDirection != other.windDirection)
			return false;
		if (Double.doubleToLongBits(windSpeed) != Double.doubleToLongBits(other.windSpeed))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Weather [temperature=");
		builder.append(temperature);
		builder.append(", conditionCode=");
		builder.append(conditionCode);
		builder.append(", humidity=");
		builder.append(humidity);
		builder.append(", pressure=");
		builder.append(pressure);
		builder.append(", pressureState=");
		builder.append(pressureState);
		builder.append(", windSpeed=");
		builder.append(windSpeed);
		builder.append(", windDirection=");
		builder.append(windDirection);
		builder.append(", source=");
		builder.append(source);
		builder.append("]");
		return builder.toString();
	}

}
