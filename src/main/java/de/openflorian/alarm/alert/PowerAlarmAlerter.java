package de.openflorian.alarm.alert;

/*
 * This file is part of Openflorian.
 *
 * Copyright (C) 2015 - 2020 Bastian Kraus
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

import com.fasterxml.jackson.databind.ObjectMapper;
import de.openflorian.config.OpenflorianConfig;
import de.openflorian.crypt.CryptCipherService;
import de.openflorian.data.model.Operation;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ValidationException;

import java.net.URLEncoder;
import java.util.Map;

import static de.openflorian.OpenflorianGlobals.HTTP_USER_AGENT;

/**
 * Alerter: Power Alarm
 *
 * This alerter is dedicated to push an incoming {@link Operation} towards
 * FITT GmbH Power Alarm. PowerAlarm provides a JSON REST interface which can
 * be triggered by a simple HTTP GET request. The {@link Operation} details are
 * handed over trough query parameters:
 *
 * Example request:
 * https://www.poweralarm.de/api/custom/?
 * apikey=Pac0test&action=triggergroupalarm&kuerzel=GR01&text=Achtung
 * %20Probealarm&lat=48.1234&long=10.1234&land=D&plz=12345&ort=Musterstadt&strasse=Dorfstr&
 * hsnr=9a&zusatz=2.OG
 *
 * For more details {@see https://www.fitt-gmbh.de/fileadmin/fittcom/pdf/poweralarm/hilfe_poweralarm.pdf}
 * chapter 9 - JSON interface
 */
public class PowerAlarmAlerter extends Alerter {

    private static final Logger log = LoggerFactory.getLogger(PowerAlarmAlerter.class);

    private final HttpClient httpClient = new DefaultHttpClient();

    private final OpenflorianConfig.Alerter alerter;

    /**
     * CTOR for configuring an URL to alert {@link Operation}s to.
     *
     * @param alerter
     */
    public PowerAlarmAlerter(OpenflorianConfig.Alerter alerter) {
        this.alerter = alerter;
        log.debug("Starting alerter with configuration: " + alerter.toString());
    }

    @Override
    public void validate() throws ValidationException{
        if(!alerter.getParameter().containsKey("alertgroup"))
            throw new ValidationException("Missing parameter: alertgroup");
        if(!alerter.getParameter().containsKey("protocol"))
            throw new ValidationException("Missing parameter: protocol");
        if(!alerter.getParameter().containsKey("host"))
            throw new ValidationException("Missing parameter: host");
        if(!alerter.getParameter().containsKey("port"))
            throw new ValidationException("Missing parameter: port");
        if(!alerter.getParameter().containsKey("apikey"))
            throw new ValidationException("Missing parameter: apikey");
    }

    @Override
    public void alert(Operation operation) throws Exception {
        String alertUrl = getAlertingUrl(operation);
        log.info("Alerting incurred operation to URL: " + alertUrl);

        HttpGet req = new HttpGet(getAlertingUrl(operation));
        // Add header
        req.setHeader("User-Agent", HTTP_USER_AGENT);

        // Executing HTTP Request
        HttpResponse response = httpClient.execute(req);
        log.info("Response status: " + response.toString());

        req.releaseConnection();
    }

    /**
     * Create URL from {@link OpenflorianConfig.Alerter}
     * @return
     */
    private String getAlertingUrl(Operation o) throws Exception {
        Map<String, String> params = this.alerter.getParameter();
        return String.format(
            "%s://%s:%s/api/custom/?apikey=%s&action=triggergroupalarm&kuerzel=%s",
                params.get("protocol"),
                params.get("host"),
                params.get("port"),
                params.get("apikey"),
                params.get("alertgroup")
            ).concat("&text=" + URLEncoder.encode(params.get("alarmtextPrefix") + o.getBuzzword(),"UTF-8"))
                .concat("&lat=" + URLEncoder.encode(String.valueOf(o.getPositionLatitude()), "UTF-8"))
                .concat("&long" + URLEncoder.encode(String.valueOf(o.getPositionLongitude()), "UTF-8"))
                .concat("&zusatz" + URLEncoder.encode(String.valueOf(o.getKeyword()), "UTF-8"));
    }

}
