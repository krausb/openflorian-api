package de.openflorian.config;

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

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Configuration Wrapper for Openflorian
 *
 * @author Bastian Kraus <bofh@k-hive.de>
 */
@Component
@ConfigurationProperties("openflorian")
public class OpenflorianConfig {

    private CipherKeys cipherKeys;
    private FaxObserver faxObserver;
    private FaxTransformer faxTransformer;
    private FaxParser faxParser;
    private List<UrlAlerter> urlAlerter;
    private Weather weather;

	public CipherKeys getCipherKeys() {
		return cipherKeys;
	}

	public void setCipherKeys(CipherKeys cipherKeys) {
		this.cipherKeys = cipherKeys;
	}

	public FaxObserver getFaxObserver() {
		return faxObserver;
	}

	public void setFaxObserver(FaxObserver faxObserver) {
		this.faxObserver = faxObserver;
	}

	public FaxTransformer getFaxTransformer() {
		return faxTransformer;
	}

	public void setFaxTransformer(FaxTransformer faxTransformer) {
		this.faxTransformer = faxTransformer;
	}

	public FaxParser getFaxParser() {
		return faxParser;
	}

	public void setFaxParser(FaxParser faxParser) {
		this.faxParser = faxParser;
	}

	public List<UrlAlerter> getUrlAlerter() {
		return urlAlerter;
	}

	public void setUrlAlerter(List<UrlAlerter> urlAlerter) {
		this.urlAlerter = urlAlerter;
	}

	public Weather getWeather() {
		return weather;
	}

	public void setWeather(Weather weather) {
		this.weather = weather;
	}

	public static class UrlAlerter {
        private String protocol;
        private String host;
        private int port;
        private String path;
        private String authkey;
        private boolean encryptPayload;

		public String getProtocol() {
			return protocol;
		}

		public void setProtocol(String protocol) {
			this.protocol = protocol;
		}

		public String getHost() {
			return host;
		}

		public void setHost(String host) {
			this.host = host;
		}

		public int getPort() {
			return port;
		}

		public void setPort(int port) {
			this.port = port;
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		public String getAuthkey() {
			return authkey;
		}

		public void setAuthkey(String authkey) {
			this.authkey = authkey;
		}

		public boolean isEncryptPayload() {
			return encryptPayload;
		}

		public void setEncryptPayload(boolean encryptPayload) {
			this.encryptPayload = encryptPayload;
		}
	}

	public static class Weather {
        private int loadPeriod;
        private YahooWeatherApi yahooWeatherApi;
        private OpenWeatherMapApi openWeatherMapApi;

		public int getLoadPeriod() {
			return loadPeriod;
		}

		public void setLoadPeriod(int loadPeriod) {
			this.loadPeriod = loadPeriod;
		}

		public YahooWeatherApi getYahooWeatherApi() {
			return yahooWeatherApi;
		}

		public void setYahooWeatherApi(YahooWeatherApi yahooWeatherApi) {
			this.yahooWeatherApi = yahooWeatherApi;
		}

		public OpenWeatherMapApi getOpenWeatherMapApi() {
			return openWeatherMapApi;
		}

		public void setOpenWeatherMapApi(OpenWeatherMapApi openWeatherMapApi) {
			this.openWeatherMapApi = openWeatherMapApi;
		}
	}

	public static class YahooWeatherApi {
        private String consumerKey;
        private String consumerSecret;
        private String woeid;

		public String getConsumerKey() {
			return consumerKey;
		}

		public void setConsumerKey(String consumerKey) {
			this.consumerKey = consumerKey;
		}

		public String getConsumerSecret() {
			return consumerSecret;
		}

		public void setConsumerSecret(String consumerSecret) {
			this.consumerSecret = consumerSecret;
		}

		public String getWoeid() {
			return woeid;
		}

		public void setWoeid(String woeid) {
			this.woeid = woeid;
		}
	}

	public static class OpenWeatherMapApi {
        private String apiKey;
        private int cityId;

		public String getApiKey() {
			return apiKey;
		}

		public void setApiKey(String apiKey) {
			this.apiKey = apiKey;
		}

		public int getCityId() {
			return cityId;
		}

		public void setCityId(int cityId) {
			this.cityId = cityId;
		}
	}

	public static class CipherKeys {

        private String blowfish;
        private String xor;

		public String getBlowfish() {
			return blowfish;
		}

		public void setBlowfish(String blowfish) {
			this.blowfish = blowfish;
		}

		public String getXor() {
			return xor;
		}

		public void setXor(String xor) {
			this.xor = xor;
		}
	}

	public static class FaxObserver {

        private String observerDir;
        private String archiveDir;
        private long incurreDelay;

		public String getObserverDir() {
			return observerDir;
		}

		public void setObserverDir(String observerDir) {
			this.observerDir = observerDir;
		}

		public String getArchiveDir() {
			return archiveDir;
		}

		public void setArchiveDir(String archiveDir) {
			this.archiveDir = archiveDir;
		}

		public long getIncurreDelay() {
			return incurreDelay;
		}

		public void setIncurreDelay(long incurreDelay) {
			this.incurreDelay = incurreDelay;
		}
	}

	public static class FaxParser {

        private String currentStation;
        private Patterns patterns;
        private Geocoordinates geocoordinates;

		public String getCurrentStation() {
			return currentStation;
		}

		public void setCurrentStation(String currentStation) {
			this.currentStation = currentStation;
		}

		public Patterns getPatterns() {
			return patterns;
		}

		public void setPatterns(Patterns patterns) {
			this.patterns = patterns;
		}

		public Geocoordinates getGeocoordinates() {
			return geocoordinates;
		}

		public void setGeocoordinates(Geocoordinates geocoordinates) {
			this.geocoordinates = geocoordinates;
		}
	}

	public static class FaxTransformer {

        private String cmd;
        private Vars vars;

		public String getCmd() {
			return cmd;
		}

		public void setCmd(String cmd) {
			this.cmd = cmd;
		}

		public Vars getVars() {
			return vars;
		}

		public void setVars(Vars vars) {
			this.vars = vars;
		}
	}

	public static class Geocoordinates {

        private String source;
        private String target;
        private String targetParams;

		public String getSource() {
			return source;
		}

		public void setSource(String source) {
			this.source = source;
		}

		public String getTarget() {
			return target;
		}

		public void setTarget(String target) {
			this.target = target;
		}

		public String getTargetParams() {
			return targetParams;
		}

		public void setTargetParams(String targetParams) {
			this.targetParams = targetParams;
		}
	}

	public static class Patterns {

        private String operationNrPattern;
        private String objectPattern;
        private String streetPattern;
        private String crosswayPattern;
        private String cityPattern;
        private String geocoordinatesPattern;
        private String priorityPattern;
        private String keywordPattern;
        private String buzzwordPattern;
        private String resourcesPattern;
        private String stationresourcePattern;
        private String resourcePurposeSplitPattern;
        private String resourcecallnamePattern;

		public String getOperationNrPattern() {
			return operationNrPattern;
		}

		public void setOperationNrPattern(String operationNrPattern) {
			this.operationNrPattern = operationNrPattern;
		}

		public String getObjectPattern() {
			return objectPattern;
		}

		public void setObjectPattern(String objectPattern) {
			this.objectPattern = objectPattern;
		}

		public String getStreetPattern() {
			return streetPattern;
		}

		public void setStreetPattern(String streetPattern) {
			this.streetPattern = streetPattern;
		}

		public String getCrosswayPattern() {
			return crosswayPattern;
		}

		public void setCrosswayPattern(String crosswayPattern) {
			this.crosswayPattern = crosswayPattern;
		}

		public String getCityPattern() {
			return cityPattern;
		}

		public void setCityPattern(String cityPattern) {
			this.cityPattern = cityPattern;
		}

		public String getGeocoordinatesPattern() {
			return geocoordinatesPattern;
		}

		public void setGeocoordinatesPattern(String geocoordinatesPattern) {
			this.geocoordinatesPattern = geocoordinatesPattern;
		}

		public String getPriorityPattern() {
			return priorityPattern;
		}

		public void setPriorityPattern(String priorityPattern) {
			this.priorityPattern = priorityPattern;
		}

		public String getKeywordPattern() {
			return keywordPattern;
		}

		public void setKeywordPattern(String keywordPattern) {
			this.keywordPattern = keywordPattern;
		}

		public String getBuzzwordPattern() {
			return buzzwordPattern;
		}

		public void setBuzzwordPattern(String buzzwordPattern) {
			this.buzzwordPattern = buzzwordPattern;
		}

		public String getResourcesPattern() {
			return resourcesPattern;
		}

		public void setResourcesPattern(String resourcesPattern) {
			this.resourcesPattern = resourcesPattern;
		}

		public String getStationresourcePattern() {
			return stationresourcePattern;
		}

		public void setStationresourcePattern(String stationresourcePattern) {
			this.stationresourcePattern = stationresourcePattern;
		}

		public String getResourcePurposeSplitPattern() {
			return resourcePurposeSplitPattern;
		}

		public void setResourcePurposeSplitPattern(String resourcePurposeSplitPattern) {
			this.resourcePurposeSplitPattern = resourcePurposeSplitPattern;
		}

		public String getResourcecallnamePattern() {
			return resourcecallnamePattern;
		}

		public void setResourcecallnamePattern(String resourcecallnamePattern) {
			this.resourcecallnamePattern = resourcecallnamePattern;
		}
	}

	public static class Vars {

        private String inputVar;
        private String outputVar;

		public String getInputVar() {
			return inputVar;
		}

		public void setInputVar(String inputVar) {
			this.inputVar = inputVar;
		}

		public String getOutputVar() {
			return outputVar;
		}

		public void setOutputVar(String outputVar) {
			this.outputVar = outputVar;
		}
	}
}