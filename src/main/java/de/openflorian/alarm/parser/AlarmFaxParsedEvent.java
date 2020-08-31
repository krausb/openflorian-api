package de.openflorian.alarm.parser;

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

import de.openflorian.alarm.AlarmFaxEvent;

/**
 * Alarm Fax Parsed Event<Br/>
 * <br/>
 * Event will be triggered if an alarm fax file is successfuly parsed in the observed directory
 * @author Bastian Kraus <bofh@k-hive.de>
 *
 */
public class AlarmFaxParsedEvent extends AlarmFaxEvent {

	public AlarmFaxParsedEvent(File result) {
		super(result);
	}
	
}
