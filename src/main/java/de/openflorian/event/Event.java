package de.openflorian.event;

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

/**
 * Base Event<br/>
 * <br/>
 * An event for OpenFlorian Platform.
 * 
 * @author Bastian Kraus <bofh@k-hive.de>
 */
public class Event {

	protected final String name;
	protected Object data = null;
	protected Object target = null;
	
	/**
	 * Constructs a simple event with a given <code>name</code>
	 * 
	 * @param name
	 */
	public Event(String name) {
		this.name = name;
	}
	
	/**
	 * Constructs a simple event with a given <code>name</code> and <code>data</code>
	 * @param name
	 * @param data
	 */
	public Event(String name, Object data) {
		this(name);
		this.data = data;
	}
	
	/**
	 * Constructs a simple event with a given <code>name</code> and <code>data</code> related to
	 * a <code>target</code>
	 * 
	 * @param name
	 * @param data
	 * @param target
	 */
	public Event(String name, Object data, Object target) {
		this(name, data);
		this.target = target;
	}
	
	/**
	 * Returns the data accompanies with this event, or null if not available.
	 * 
	 * @return
	 * 		{@link Object}
	 */
	public Object getData() {
		return this.data;
	}
	
	/**
	 * Returns the target that receives this event, or null if broadcast.
	 * 
	 * @return
	 */
	public Object getTarget() {
		return this.target;
	}
	
}
