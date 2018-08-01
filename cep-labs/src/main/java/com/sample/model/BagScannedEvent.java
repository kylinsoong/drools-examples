package com.sample.model;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Role.Type;
import org.kie.api.definition.type.Timestamp;

/**
 * Event fired when a bag is scanned.
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */

@Role(Type.EVENT)
@Timestamp("timestamp")
@Expires("5d")
public class BagScannedEvent extends AbstractFact implements Event {

	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	private Date timestamp;
	
	private final BagTag bagTag;
	
	private final Location location;
	
	private final double weight;
	
	public BagScannedEvent(BagTag bagTag, Location location, double weight) {
		this(bagTag, location, weight, new Date());
	}

	public BagScannedEvent(BagTag bagTag, Location location, double weight, Date eventTimestamp) {
		this(UUID.randomUUID().toString(), bagTag, location, weight, eventTimestamp);
	}

	public BagScannedEvent(String id, BagTag bagTag, Location location, double weight, Date eventTimestamp) {
		super(id);
		this.bagTag = bagTag;
		this.location = location;
		this.weight = weight;
		this.timestamp = eventTimestamp;
	}

	public Location getLocation() {
		return location;
	}

	public BagTag getBagTag() {
		return bagTag;
	}
	
	public double getWeight() {
		return weight;
	}

	@Override
	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date eventTimestamp) {
		this.timestamp = eventTimestamp;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("bagTag", bagTag).append("location", location).append("timestamp", timestamp).toString();
	}

}
