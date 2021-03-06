/***********************************************************************************
 * Copyright (c) 2018 /// Project SWG /// www.projectswg.com                       *
 *                                                                                 *
 * ProjectSWG is the first NGE emulator for Star Wars Galaxies founded on          *
 * July 7th, 2011 after SOE announced the official shutdown of Star Wars Galaxies. *
 * Our goal is to create an emulator which will provide a server for players to    *
 * continue playing a game similar to the one they used to play. We are basing     *
 * it on the final publish of the game prior to end-game events.                   *
 *                                                                                 *
 * This file is part of PSWGCommon.                                                *
 *                                                                                 *
 * --------------------------------------------------------------------------------*
 *                                                                                 *
 * PSWGCommon is free software: you can redistribute it and/or modify              *
 * it under the terms of the GNU Affero General Public License as                  *
 * published by the Free Software Foundation, either version 3 of the              *
 * License, or (at your option) any later version.                                 *
 *                                                                                 *
 * PSWGCommon is distributed in the hope that it will be useful,                   *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of                  *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                   *
 * GNU Affero General Public License for more details.                             *
 *                                                                                 *
 * You should have received a copy of the GNU Affero General Public License        *
 * along with PSWGCommon.  If not, see <http://www.gnu.org/licenses/>.             *
 ***********************************************************************************/
package com.projectswg.common.network.packets.swg.zone.spatial;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.projectswg.common.network.NetBuffer;
import com.projectswg.common.network.packets.SWGPacket;

public class AttributeListMessage extends SWGPacket {
	public static final int CRC = getCrc("AttributeListMessage");
	
	private long objectId;
	private String staticItemName;
	private Map <String, String> attributes;
	private int serverRevision;
	
	public AttributeListMessage() {
		this(0, new LinkedHashMap<>(), 0);
	}
	
	public AttributeListMessage(String staticItemName, Map <String, String> attributes, int serverRevision) {
		this.objectId = 0;
		this.staticItemName = staticItemName;
		this.attributes = new LinkedHashMap<>(attributes);
		this.serverRevision = serverRevision;
	}
	
	public AttributeListMessage(long objectId, Map <String, String> attributes, int serverRevision) {
		this.objectId = objectId;
		this.staticItemName = "";
		this.attributes = new LinkedHashMap<>(attributes);
		this.serverRevision = serverRevision;
	}
	
	public AttributeListMessage(NetBuffer data) {
		decode(data);
	}
	
	@Override
	public void decode(NetBuffer data) {
		if (!super.checkDecode(data, CRC))
			return;
		objectId = data.getLong();
		staticItemName = data.getAscii();
		int count = data.getInt();
		for (int i = 0; i < count; i++) {
			String name = data.getAscii();
			String attr = data.getUnicode();
			attributes.put(name, attr);
		}
		serverRevision = data.getInt();
	}
	
	@Override
	public NetBuffer encode() {
		int size = 0;
		for (Entry <String, String> e : attributes.entrySet()) {
			size += 6 + e.getKey().length() + (e.getValue().length() * 2);
		}
		NetBuffer data = NetBuffer.allocate(24 + size);
		data.addShort(3);
		data.addInt(CRC);
		data.addLong(objectId);
		data.addAscii(staticItemName);
		data.addInt(attributes.size());
		for (Entry <String, String> e : attributes.entrySet()) {
			data.addAscii(e.getKey());
			data.addUnicode(e.getValue());
		}
		data.addInt(serverRevision);
		return data;
	}
	
	public long getObjectId() {
		return objectId;
	}
	
	public String getStaticItemName() {
		return staticItemName;
	}
	
	public Map<String, String> getAttributes() {
		return Collections.unmodifiableMap(attributes);
	}
	
	public int getServerRevision() {
		return serverRevision;
	}
	
	public void setObjectId(long objectId) {
		this.objectId = objectId;
	}
	
	public void setStaticItemName(String staticItemName) {
		this.staticItemName = staticItemName;
	}
	
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = new HashMap<>(attributes);
	}
	
	public void setServerRevision(int serverRevision) {
		this.serverRevision = serverRevision;
	}
}
