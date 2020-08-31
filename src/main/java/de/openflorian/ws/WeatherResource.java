package de.openflorian.ws;

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

import de.openflorian.weather.Weather;
import de.openflorian.weather.WeatherProvisioningService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * REST Resource for Weather Handlinng
 * 
 * @author Bastian kraus <bofh@k-hive.de>
 */
public interface WeatherResource {

	/**
	 * REST Resource to access the current {@link Weather} provided by {@link WeatherProvisioningService}<br/>
	 * <br/>
	 * HTTP Method: GET
	 *
	 * @return
	 */
	@ApiOperation(httpMethod="GET", value = "Get current weather", response = Weather.class)
	@GetMapping(path="/current")
	@CrossOrigin
	Weather current();

}
