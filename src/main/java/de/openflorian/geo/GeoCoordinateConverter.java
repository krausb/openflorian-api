package de.openflorian.geo;

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

import org.osgeo.proj4j.ProjCoordinate;

/**
 * Immutable Converter for handling  X->Lat/Long projection / mapping.
 * <br/>
 * X is mapped by a given {@link ConversionStrategy} to Lat/Long for Google Maps API.
 * 
 * @author Bastian Kraus <bofh@k-hive.de>
 */
public class GeoCoordinateConverter {

	private final ConversionStrategy conversionStrategy;
	
	/**
	 * Private CTOR
	 * @param conversionStrategy
	 */
	private GeoCoordinateConverter(ConversionStrategy conversionStrategy) {
		this.conversionStrategy = conversionStrategy;
	}
	
	/**
	 * Create a {@link GeoCoordinateConverter} for a given {@link ConversionStrategy}.
	 * 
	 * @param conversionStrategy
	 */
	public static GeoCoordinateConverter converterForStrategy(ConversionStrategy conversionStrategy) {
		return new GeoCoordinateConverter(conversionStrategy);
	}
	
	/**
	 * Converts the given <code>point</code> by the set {@link ConversionStrategy}.
	 * 
	 * @param point
	 * @return
	 */
	public ProjCoordinate convert(ProjCoordinate point) {
		return this.conversionStrategy.convert(point);
	}
	
}
