/**
 * openHAB, the open Home Automation Bus.
 * Copyright (C) 2010-2013, openHAB.org <admin@openhab.org>
 *
 * See the contributors.txt file in the distribution for a
 * full listing of individual contributors.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses>.
 *
 * Additional permission under GNU GPL version 3 section 7
 *
 * If you modify this Program, or any covered work, by linking or
 * combining it with Eclipse (or a modified version of that library),
 * containing parts covered by the terms of the Eclipse Public License
 * (EPL), the licensors of this Program grant you additional permission
 * to convey the resulting work.
 */
package org.openhab.binding.digitalstrom.internal.client.entity.impl;

import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openhab.binding.digitalstrom.internal.client.constants.JSONApiResponseKeysEnum;
import org.openhab.binding.digitalstrom.internal.client.entity.DetailedGroupInfo;
import org.openhab.binding.digitalstrom.internal.client.entity.Device;
import org.openhab.binding.digitalstrom.internal.client.entity.Zone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 	Alexander Betker
 * @since 1.3.0
 */
public class JSONZoneImpl implements Zone {
	
	private static final Logger logger = LoggerFactory.getLogger(JSONZoneImpl.class);
	
	private int zoneId = 0;
	private String name = null;
	
	private List<DetailedGroupInfo> groupList = null;
	private List<Device> deviceList	= null;
	
	public JSONZoneImpl(JSONObject object) {
		this.groupList = new LinkedList<DetailedGroupInfo>();
		this.deviceList = new LinkedList<Device>();
		
		if (object.get(JSONApiResponseKeysEnum.APARTMENT_GET_STRUCTURE_ZONES_NAME.getKey()) != null) {
			this.name = object.get(JSONApiResponseKeysEnum.APARTMENT_GET_STRUCTURE_ZONES_NAME.getKey()).toString();//getValue(object, DigitalSTROMJSONApiResultKeysEnum.APARTMENT_GET_STRUCTURE_ZONES_NAME.getKey());
		}
		
		String zoneIdStr = null;
		if (object.get(JSONApiResponseKeysEnum.APARTMENT_GET_STRUCTURE_ZONES_ID.getKey()) != null) {
			zoneIdStr = object.get(JSONApiResponseKeysEnum.APARTMENT_GET_STRUCTURE_ZONES_ID.getKey()).toString();
		}
		if (zoneIdStr == null) {
			if (object.get(JSONApiResponseKeysEnum.QUERY_ZONE_ID.getKey()) != null) {
				zoneIdStr = object.get(JSONApiResponseKeysEnum.QUERY_ZONE_ID.getKey()).toString();
			}
		}
		
		if (zoneIdStr != null) {
			try {
				this.zoneId = Integer.parseInt(zoneIdStr);
			} catch (java.lang.NumberFormatException e) {
				logger.error("NumberFormatException by getting zoneID: "+zoneIdStr);
			}
		}
		
		if (object.get(JSONApiResponseKeysEnum.APARTMENT_GET_STRUCTURE_ZONES_DEVICES.getKey()) instanceof org.json.simple.JSONArray) {
			JSONArray list = (JSONArray)object.get(JSONApiResponseKeysEnum.APARTMENT_GET_STRUCTURE_ZONES_DEVICES.getKey());
			for (int i=0; i< list.size(); i++) {
				this.deviceList.add(new JSONDeviceImpl((JSONObject)list.get(i)));
			}
		}
		
		if (object.get(JSONApiResponseKeysEnum.APARTMENT_GET_STRUCTURE_ZONES_GROUPS.getKey()) instanceof org.json.simple.JSONArray) {
			JSONArray groupList = (JSONArray)object.get(JSONApiResponseKeysEnum.APARTMENT_GET_STRUCTURE_ZONES_GROUPS.getKey());
			for (int i=0; i< groupList.size(); i++) {
				this.groupList.add(new JSONDetailedGroupInfoImpl((JSONObject)groupList.get(i)));
			}
		}
		
	}
	
	@Override
	public int getZoneId() {
		return zoneId;
	}
	
	
	@Override
	public synchronized void setZoneId(int id) {
		if (id > 0) {
			this.zoneId = id;
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public synchronized void setName(String name) {
		this.name = name;
	}

	@Override
	public List<DetailedGroupInfo> getGroups() {
		return groupList;
	}

	@Override
	public void addGroup(DetailedGroupInfo group) {
		if (group != null) {
			synchronized(groupList) {
				if (!groupList.contains(group)) {
					groupList.add(group);
				}
			}
		}
	}

	@Override
	public List<Device> getDevices() {
		return deviceList;
	}

	@Override
	public void addDevice(Device device) {
		if (device != null) {
			synchronized(deviceList) {
				if (!deviceList.contains(device)) {
					deviceList.add(device);
				}
			}
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Zone) {
			Zone other = (Zone)obj;
			return (other.getZoneId() == this.getZoneId());
		}
		return false;
	}
	
}
