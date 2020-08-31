package de.openflorian.alarm.alert.impl;

/*
 * This file is part of Openflorian.
 *
 * Copyright (C) 2015 - 2018  Bastian Kraus
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
import de.openflorian.alarm.alert.AbstractAlerter;
import de.openflorian.config.OpenflorianConfig;
import de.openflorian.crypt.CryptCipherService;
import de.openflorian.data.model.Operation;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static de.openflorian.OpenflorianGlobals.HTTP_USER_AGENT;

/**
 * URL UrlAlerter
 *
 * Alerts / sends serialized {@link Operation} to a configured URL.
 */
public class UrlAlerterVerticle extends AbstractAlerter {

    private static final Logger log = LoggerFactory.getLogger(UrlAlerterVerticle.class);

    private final HttpClient httpClient = new DefaultHttpClient();

    private final OpenflorianConfig.UrlAlerter alerter;
    private final HttpPost postRequest;

    /**
     * CTOR for configuring an URL to alert {@link Operation}s to.
     *
     * @param alerter
     */
    public UrlAlerterVerticle(OpenflorianConfig.UrlAlerter alerter) {
        this.alerter = alerter;
        postRequest = new HttpPost(getAlertingUrl());
        // add header
        postRequest.setHeader("User-Agent", HTTP_USER_AGENT);

        log.info("Initializing alerter for URL: " + this.getAlertingUrl());
    }

    @Override
    public void alert(Operation operation) throws Exception {

        log.info("Alerting incurred operation to URL: " + getAlertingUrl());

        // Building HTTP Post Request
        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(operation);

        if(alerter.isEncryptPayload()) {
            payload = CryptCipherService.service().encrypt(
                    CryptCipherService.service().encrypt(
                            payload,
                            CryptCipherService.CipherTarget.Blowfish
                    ),
                    CryptCipherService.CipherTarget.Xor
            );
        }

        postRequest.setEntity(new StringEntity(payload));

        // Executing HTTP Post Request
        HttpResponse response = httpClient.execute(postRequest);
        log.info("Response status: " + response.toString());

        postRequest.releaseConnection();
    }

    /**
     * Create URL from {@link OpenflorianConfig.UrlAlerter}
     * @return
     */
    private String getAlertingUrl() {
        return String.format(
            "%s://%s:%d%s",
                this.alerter.getProtocol(),
                this.alerter.getHost(),
                this.alerter.getPort(),
                (this.alerter.getAuthkey() != null ? this.alerter.getPath().replace("{authKey}", this.alerter.getAuthkey()) : this.alerter.getPath())
        );
    }

}
