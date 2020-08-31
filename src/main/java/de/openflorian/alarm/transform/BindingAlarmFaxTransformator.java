package de.openflorian.alarm.transform;

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

import io.vertx.core.eventbus.Message;

/**
 * Alarm Fax Transformator<br/>
 * <br/>
 * Processes the given alarm fax with an OCR binary like
 * tesseract.
 * 
 * @author Bastian Kraus <bofh@k-hive.de>
 */
public class BindingAlarmFaxTransformator extends AbstractAlarmFaxTransformator {

	@Override
	public void transform(Message<Object> msg) {
		// TODO: Implement as fallback or alternative for {@link BinaryAlarmFaxTransformator
		// Use DLL / LIB Binding to OCR capi
	}
	
}
