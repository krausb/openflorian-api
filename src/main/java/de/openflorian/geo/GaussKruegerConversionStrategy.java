package de.openflorian.geo;

/*
 * This file is part of Openflorian.
 * 
 * Copyright (C) 2016  Bastian Kraus
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

import org.osgeo.proj4j.CRSFactory;
import org.osgeo.proj4j.CoordinateReferenceSystem;
import org.osgeo.proj4j.CoordinateTransform;
import org.osgeo.proj4j.CoordinateTransformFactory;
import org.osgeo.proj4j.ProjCoordinate;

/**
 * Gauss-Krueger to Lat/Long Mapping Strategy
 * 
 * @author Bastian Kraus <bofh@k-hive.de>
 */
public class GaussKruegerConversionStrategy implements ConversionStrategy {

	private CRSFactory crsFactory = new CRSFactory();
	
	private static final String WGS84_PARAM = "+title=long/lat:WGS84 +proj=longlat +datum=WGS84 +units=degrees";
	
	@Override
	public ProjCoordinate convert(ProjCoordinate source) {
		
		CoordinateReferenceSystem gaussZone4Crs = crsFactory.createFromName("epsg:31468");
		CoordinateReferenceSystem wgs84Crs = crsFactory.createFromParameters("WGS84", WGS84_PARAM);
		
		CoordinateTransform ct = new CoordinateTransformFactory().createTransform(gaussZone4Crs, wgs84Crs);
		
		ProjCoordinate target = new ProjCoordinate();
		ct.transform(source, target);
		
		return target;
	}

}
