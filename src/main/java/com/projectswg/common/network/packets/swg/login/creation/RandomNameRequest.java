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
package com.projectswg.common.network.packets.swg.login.creation;

import com.projectswg.common.network.NetBuffer;
import com.projectswg.common.network.packets.SWGPacket;


public class RandomNameRequest extends SWGPacket {
	public static final int CRC = getCrc("ClientRandomNameRequest");

	private String raceCrc = "object/creature/player/human_male.iff";
	
	public RandomNameRequest() {
		
	}
	
	public void decode(NetBuffer data) {
		if (!super.checkDecode(data, CRC))
			return;
		raceCrc = data.getAscii();
	}
	
	public NetBuffer encode() {
		NetBuffer data = NetBuffer.allocate(8 + raceCrc.length());
		data.addShort(2);
		data.addInt(CRC);
		data.addAscii(raceCrc);
		return data;
	}
	
	public String getRace() { return raceCrc; }
	public void setRace(String race) { this.raceCrc = race; }
}
