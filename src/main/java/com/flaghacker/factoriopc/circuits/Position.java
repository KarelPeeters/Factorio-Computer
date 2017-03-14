package com.flaghacker.factoriopc.circuits;

import org.json.JSONObject;

public class Position
{
	public static final Position ORIGIN = new Position(0, 0);

	public final int x;
	public final int y;

	public Position(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public Position offSet(Position position)
	{
		return offSet(position.x, position.y);
	}
	
	public Position offSet(int x, int y)
	{
		return new Position(this.x + x, this.y + y);
	}

	public JSONObject toJSON()
	{
		JSONObject json = new JSONObject();
		json.put("x", x);
		json.put("y", y);
		return json;
	}
}
