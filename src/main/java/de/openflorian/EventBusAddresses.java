package de.openflorian;

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

import io.vertx.core.eventbus.EventBus;

/**
 * {@link EventBus} adresses for Openflorian
 * 
 * @author Bastian Kraus <bofh@k-hive.de>
 */
public class EventBusAddresses {

	public static final String ALARMFAX_NEWFAX = "alarmfax.newFax";
	public static final String ALARMFAX_TRANSFORMED = "alarmfax.transformedFax";
	public static final String ALARMFAX_PARSED = "alarmfax.parsedFax";
	public static final String ALARMFAX_ARCHIVED = "alarmfax.archived";

	public static final String ARCHIVE_FILE = "archive.file";

	public static final String ALARM_INCURRED = "alarm.incurred";
	public static final String ALARM_TAKENOVER = "alarm.takenover";
	public static final String ALARM_DISPATCHED = "alarm.dispatched";

}
