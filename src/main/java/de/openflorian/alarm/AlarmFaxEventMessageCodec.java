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

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageCodec;
import io.vertx.core.json.JsonObject;

import java.io.File;

/**
 * Alarm Fax Event {@link MessageCodec}<br/>
 * <br/>
 * The codec is responsible for {@link AlarmFaxEvent} message delivery object
 * encoding and decoding for the {@link EventBus}
 * 
 * @author Bastian Kraus <bofh@k-hive.de>
 */
public class AlarmFaxEventMessageCodec implements
		MessageCodec<AlarmFaxEvent, AlarmFaxEvent> {

	public static final String JSON_PROPERTY_FILENAME = "fileName";
	
	@Override
	public AlarmFaxEvent decodeFromWire(int position, Buffer buffer) {
		// My custom message starting from this *position* of buffer
		int _pos = position;

		// Length of JSON
		int length = buffer.getInt(_pos);

	    String jsonStr = buffer.getString(_pos+=4, _pos+=length);
	    JsonObject contentJson = new JsonObject(jsonStr);
	    
	    String fileName = contentJson.getString(JSON_PROPERTY_FILENAME);
		
		return new AlarmFaxEvent(new File(fileName));
	}

	@Override
	public void encodeToWire(Buffer buffer, AlarmFaxEvent event) {
	    JsonObject jsonToEncode = new JsonObject();
	    jsonToEncode.put(JSON_PROPERTY_FILENAME, event.getResultFile().getAbsolutePath());

	    // Encode object to string
	    String jsonToStr = jsonToEncode.encode();
	    // Length of JSON: is NOT characters count
	    int length = jsonToStr.getBytes().length;

	    // Write data into given buffer
	    buffer.appendInt(length);
	    buffer.appendString(jsonToStr);
	}

	@Override
	public String name() {
		return AlarmFaxEventMessageCodec.class.getSimpleName();
	}

	@Override
	public byte systemCodecID() {
		return -1;
	}

	@Override
	public AlarmFaxEvent transform(AlarmFaxEvent event) {
		return event;
	}
}
