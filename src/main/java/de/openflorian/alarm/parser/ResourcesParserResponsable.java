package de.openflorian.alarm.parser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.openflorian.config.OpenflorianConfig;
import de.openflorian.data.model.Operation;
import de.openflorian.data.model.OperationResource;
import de.openflorian.util.StringUtils;

/**
 * Street Parser Responsable
 * 
 * @author Bastian Kraus <bofh@k-hive.de>
 */
class ResourcesParserResponsable extends AlarmFaxParserPatternMatcherResponsable {

	protected final Pattern callNamePattern;
	protected final String stationresourcePattern;
	protected final String resourcePurposeSplitPattern;

	public ResourcesParserResponsable() {
		final String callNamePatternStr = this.getConfig().getFaxParser().getPatterns().getResourcecallnamePattern();
		if (!StringUtils.isEmpty(callNamePatternStr)) {
			callNamePattern = Pattern.compile(callNamePatternStr);
			log.info("Resource callName pattern: " + callNamePatternStr);
		}
		else {
			throw new IllegalStateException("Resource callName pattern not set in configuration file.");
		}

		stationresourcePattern = this.getConfig().getFaxParser().getPatterns().getStationresourcePattern();
		if (StringUtils.isEmpty(stationresourcePattern))
			throw new IllegalStateException("Station resource pattern not set in configuration file.");
		else
			log.info("Station resource pattern: " + stationresourcePattern);

		resourcePurposeSplitPattern = this.getConfig().getFaxParser().getPatterns().getResourcePurposeSplitPattern();
		if (StringUtils.isEmpty(resourcePurposeSplitPattern))
			throw new IllegalStateException("Resource purpose split pattern not set in configuration file.");
		else
			log.info("Resource purpose split pattern: " + resourcePurposeSplitPattern);
	}

	@Override
	public Pattern getPattern() {
		return Pattern.compile(this.getConfig().getFaxParser().getPatterns().getResourcesPattern(),
				Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.UNICODE_CASE);
	}

	@Override
	public void parse(String alarmfax, Operation operation) {
		final Matcher m = getPattern().matcher(alarmfax);
		final StringBuffer sb = new StringBuffer();
		while (m.find()) {
			sb.append(m.group(1));
			sb.append(System.getProperty("line.separator"));

			final Matcher callNameMatcher = callNamePattern.matcher(m.group(1));
			if (callNameMatcher.find()) {
				if (operation.getResources() == null)
					operation.setResources(new ArrayList<OperationResource>());

				final OperationResource resource = new OperationResource();
				resource.setCrew("tbd.");
				resource.setDescription("tbd.");
				resource.setLicensePlate("tbd.");
				resource.setType("tbd.");

				final String[] aRes = m.group(1).split(resourcePurposeSplitPattern);
				if (aRes.length > 1 && !StringUtils.isEmpty(aRes[1].trim()))
					resource.setPurpose(aRes[1].trim());

				if (m.group(1).contains(stationresourcePattern)) {
					resource.setExternal(false);
					resource.setCallName(callNameMatcher.group(1));
				}
				else {
					resource.setExternal(true);
					resource.setCallName(aRes[0]);
				}

				operation.getResources().add(resource);
			}
		}
		operation.setResourcesRaw(sb.toString());

		if (getNext() != null)
			getNext().parse(alarmfax, operation);
	}

}
