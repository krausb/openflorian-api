package de.openflorian.alarm;

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

import de.openflorian.event.Event;

/**
 * Alarm Fax Event
 * 
 * @author Bastian Kraus <bofh@k-hive.de>
 */
public class AlarmFaxEvent extends Event {
	
	public AlarmFaxEvent(File result) {
		super("AlarmFaxEvent", result);
	}

	/**
	 * Get the resulting file of the triggered event.
	 * 
	 * @return
	 * 		{@link File}
	 */
	public File getResultFile() {
		return (File)this.data;
	}
	
}
