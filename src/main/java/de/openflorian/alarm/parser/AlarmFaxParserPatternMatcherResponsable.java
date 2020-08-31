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

import java.util.regex.Pattern;

import com.mysql.cj.x.protobuf.MysqlxCursor;
import de.openflorian.OpenflorianApplication;
import de.openflorian.config.OpenflorianConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.openflorian.data.model.Operation;

/**
 * A pattern matcher responsable for realizing a chain of responsibility to
 * match the elements of the alarm fax text.<br/>
 * <br/>
 * Each {@link AlarmFaxParserPatternMatcherResponsable} matches a single Alarm
 * Fax attribute, puts the result into the passed {@link Operation} and plays
 * the ball to the in chain.
 * 
 * @author Bastian Kraus <bofh@k-hive.de>
 */
public abstract class AlarmFaxParserPatternMatcherResponsable {

	protected final Logger log = LoggerFactory.getLogger(getClass());

	protected AlarmFaxParserPatternMatcherResponsable next = null;

	protected Pattern parserPattern = null;

	private OpenflorianConfig config;

	public OpenflorianConfig getConfig() {
		return this.config;
	}

	AlarmFaxParserPatternMatcherResponsable() {
		this.config = OpenflorianApplication.getConfig();
	}

	/**
	 * Gets the next {@link AlarmFaxParserPatternMatcherResponsable} in chain or
	 * null if the end of the chain is reached.
	 * 
	 * @return {@link AlarmFaxParserPatternMatcherResponsable}
	 */
	public AlarmFaxParserPatternMatcherResponsable getNext() {
		return next;
	}

	/**
	 * Sets the next {@link AlarmFaxParserPatternMatcherResponsable} in chain
	 * 
	 * @param next
	 */
	public void setNext(AlarmFaxParserPatternMatcherResponsable next) {
		this.next = next;
	}

	/**
	 * Getter for the parsing pattern.
	 * 
	 * @return
	 */
	public abstract Pattern getPattern();

	/**
	 * Processes the given <code>alarmfax</code> and stores the responsable
	 * attribute in the target <code>operation</code> entity.
	 * 
	 * @param alarmfax
	 *            {@link String}
	 * @param operation
	 *            {@link Operation}
	 */
	public abstract void parse(String alarmfax, Operation operation);

}
