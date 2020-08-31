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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.openflorian.config.OpenflorianConfig;
import de.openflorian.data.model.Operation;

/**
 * Keyword Parser Responsable
 * 
 * @author Bastian Kraus <bofh@k-hive.de>
 */
class KeywordParserResponsable extends AlarmFaxParserPatternMatcherResponsable {

	@Override
	public Pattern getPattern() {
		return Pattern.compile(this.getConfig().getFaxParser().getPatterns().getKeywordPattern(),
				Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.UNICODE_CASE);
	}

	@Override
	public void parse(String alarmfax, Operation operation) {
		final Matcher m = getPattern().matcher(alarmfax);
		if (m.find()) {
			operation.setKeyword(m.group(1).trim());
		}

		if (getNext() != null)
			getNext().parse(alarmfax, operation);
	}

}
