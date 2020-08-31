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

import de.openflorian.data.model.Operation;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * REST Resource for Alarm Handlinng
 * 
 * @author Bastian kraus <bofh@k-hive.de>
 */
public interface AlarmResource {

	/**
	 * REST Resource to incurre an {@link Operation} with given <code>operationId</code><br/>
	 * <br/>
	 * HTTP Method: POST
	 * 
	 * @param operationId
	 * @return
	 */
	@ApiOperation(httpMethod="POST", value = "Incurre alarm")
	@PostMapping(path="/")
	@CrossOrigin
	void incurre(@RequestParam("operationId") long operationId);

	/**
	 * REST Resource to incurre the last incurred operation
	 * 
	 * HTTP Method: POST
	 * 
	 * @return
	 */
	@ApiOperation(httpMethod="POST", value = "Re-incurre last incurred alarm")
	@PostMapping(path="/last")
	@CrossOrigin
	void incurreLast();

	/**
	 * REST Resource to load all running content crawler available<br/>
	 * <br/>
	 * HTTP Method: GET
	 * 
	 * @return {@link Operation}
	 */
	@ApiOperation(httpMethod="GET", value = "Get the current incurred operation", response = Operation.class)
	@GetMapping(path="/current", produces= MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin
	public Operation getCurrentOperation();

	/**
	 * REST Resource to takeOver the current {@link Operation}<br/>
	 * <br/>
	 * HTTP Method: PUT
	 *
	 * @return
	 */
	@ApiOperation(httpMethod = "PUT", value = "Takeover current incurred operation")
	@PutMapping(path="/current")
	@CrossOrigin
	public void takeOver();

	/**
	 * REST Resource to takeOver an {@link Operation} with given <code>operationId</code><br/>
	 * <br/>
	 * HTTP Method: DELETE <br/>
	 * HTTP Response:<br/>
	 * 200 - Successfuly dispatched 404 - Currently no operation
	 *
	 * @return
	 */
	@ApiOperation(httpMethod = "DELETE", value = "Dispatch current incurred operation")
	@DeleteMapping("/current")
	@CrossOrigin
	public void dispatch();

}
