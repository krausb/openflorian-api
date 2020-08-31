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

import org.osgeo.proj4j.ProjCoordinate;

import de.openflorian.config.OpenflorianConfig;
import de.openflorian.data.model.Operation;
import de.openflorian.geo.GaussKruegerConversionStrategy;
import de.openflorian.geo.GeoCoordinateConverter;

/**
 * Geo Coordinate Parser Responsable
 * 
 * @author Bastian Kraus <bofh@k-hive.de>
 */
class GeoCoordinateParserResponsable extends AlarmFaxParserPatternMatcherResponsable {

	@Override
	public Pattern getPattern() {
		return Pattern.compile(this.getConfig().getFaxParser().getPatterns().getGeocoordinatesPattern(),
				Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.UNICODE_CASE);
	}

	@Override
	public void parse(String alarmfax, Operation operation) {
		Matcher m = getPattern().matcher(alarmfax);
		if (m.find()) {
			Double y = Double.valueOf(m.group(2));
			Double x = Double.valueOf(m.group(1));

			GeoCoordinateConverter converter = GeoCoordinateConverter
					.converterForStrategy(new GaussKruegerConversionStrategy());
			ProjCoordinate p = converter.convert(new ProjCoordinate(x, y));

			operation.setPositionLongitude(p.x);
			operation.setPositionLatitude(p.y);
		}

		if (getNext() != null)
			getNext().parse(alarmfax, operation);
	}

}
