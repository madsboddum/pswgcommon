/***********************************************************************************
* Copyright (c) 2015 /// Project SWG /// www.projectswg.com                        *
*                                                                                  *
* ProjectSWG is the first NGE emulator for Star Wars Galaxies founded on           *
* July 7th, 2011 after SOE announced the official shutdown of Star Wars Galaxies.  *
* Our goal is to create an emulator which will provide a server for players to     *
* continue playing a game similar to the one they used to play. We are basing      *
* it on the final publish of the game prior to end-game events.                    *
*                                                                                  *
* This file is part of Holocore.                                                   *
*                                                                                  *
* -------------------------------------------------------------------------------- *
*                                                                                  *
* Holocore is free software: you can redistribute it and/or modify                 *
* it under the terms of the GNU Affero General Public License as                   *
* published by the Free Software Foundation, either version 3 of the               *
* License, or (at your option) any later version.                                  *
*                                                                                  *
* Holocore is distributed in the hope that it will be useful,                      *
* but WITHOUT ANY WARRANTY; without even the implied warranty of                   *
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                    *
* GNU Affero General Public License for more details.                              *
*                                                                                  *
* You should have received a copy of the GNU Affero General Public License         *
* along with Holocore.  If not, see <http://www.gnu.org/licenses/>.                *
*                                                                                  *
***********************************************************************************/
package com.projectswg.common.network.packets.swg.zone;

import com.projectswg.common.network.NetBuffer;
import com.projectswg.common.network.packets.SWGPacket;

public class PlayMusicMessage extends SWGPacket {
	public static final int CRC = getCrc("PlayMusicMessage");
	
	private long objectId;
	private String soundFile;
	private int repititions;	// playType?
	private boolean loop;
	
	/**
	 * 
	 * @param objectId is the ID for the object where this sound originates from.
	 * Use an object ID of 0 if the sound doesn't originate from anywhere.
	 * @param soundFile is the full path to the .snd file to play
	 * @param repititions TODO
	 * @param loop can be set to true if this sound should keep looping (TODO ?)
	 */
	public PlayMusicMessage(long objectId, String soundFile, int repititions, boolean loop) {
		this.objectId = objectId;
		this.soundFile = soundFile;
		this.repititions = repititions;
		this.loop = loop;
	}
	
	@Override
	public void decode(NetBuffer data) {
		if (!super.checkDecode(data, CRC))
			return;
		soundFile = data.getAscii();
		objectId = data.getLong();
		repititions = data.getInt();
		loop = data.getBoolean();
	}
	
	@Override
	public NetBuffer encode() {
		NetBuffer data = NetBuffer.allocate(21 + soundFile.length());
		data.addShort(5);
		data.addInt(CRC);
		data.addAscii(soundFile);
		data.addLong(objectId);
		data.addInt(repititions);
		data.addBoolean(loop);
		return data;
	}
}