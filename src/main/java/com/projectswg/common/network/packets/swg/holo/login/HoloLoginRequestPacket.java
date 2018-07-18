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

package com.projectswg.common.network.packets.swg.holo.login;

import com.projectswg.common.network.NetBuffer;
import com.projectswg.common.network.packets.swg.holo.HoloPacket;

public class HoloLoginRequestPacket extends HoloPacket {
	
	public static final int CRC = getCrc("HoloLoginRequestPacket");
	
	private String username;
	private String password;
	
	public HoloLoginRequestPacket() {
		this.username = "";
		this.password = "";
	}
	
	public HoloLoginRequestPacket(NetBuffer data) {
		decode(data);
	}
	
	public HoloLoginRequestPacket(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	@Override
	public final void decode(NetBuffer data) {
		if (!super.checkDecode(data, CRC))
			return;
		this.username = data.getAscii();
		this.password = data.getAscii();
	}
	
	@Override
	public final NetBuffer encode() {
		NetBuffer data = NetBuffer.allocate(10 + username.length() + password.length());
		data.addShort(3);
		data.addInt(CRC);
		data.addAscii(username);
		data.addAscii(password);
		return data;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}
