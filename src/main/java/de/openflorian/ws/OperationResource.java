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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * REST Resource for Operation Handlinng
 * 
 * @author Bastian kraus <bofh@k-hive.de>
 */
public interface OperationResource {

	/**
	 * REST Resource to load a {@link List}<{@link Operation}>
	 * 
	 * @return {@link List}<{@link Operation}>
	 */
	@ApiOperation(httpMethod="GET", value = "List all operations", response = Operation.class, responseContainer = "List")
	@GetMapping(path="/all", produces= MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin
	List<Operation> getAllOperations();

	/**
	 * REST Resource to load a {@link List}<{@link Operation}>
	 * 
	 * @return {@link List}<{@link Operation}>
	 */
	@ApiOperation(httpMethod="GET", value = "List all operations sorted by id descending with given limit", response = Operation.class, responseContainer = "List")
	@GetMapping(path="/last", produces= MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin
	List<Operation> getLastOperations(@RequestParam("limit") int limit);

}
