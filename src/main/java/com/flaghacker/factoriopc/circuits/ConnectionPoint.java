package com.flaghacker.factoriopc.circuits;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class ConnectionPoint
{
	private Entity owner;
	private int circuitID;

	private Map<ConnectionPoint, Color> connections = new HashMap<>();

	public ConnectionPoint(Entity owner, int circuitID)
	{
		this.owner = owner;
		this.circuitID = circuitID;
	}

	public int getCircuitID()
	{
		return circuitID;
	}

	public int getEntityID()
	{
		return owner.getId();
	}

	public void connect(SingleConnectible other, Color color)
	{
		connect(other.getConnection(), color);
	}

	public void connect(SingleConnectible other)
	{
		connect(other.getConnection(), Color.defaultColor);
	}

	public void connect(ConnectionPoint other)
	{
		connect(other, Color.defaultColor);
	}

	public void connect(ConnectionPoint other, Color color)
	{
		this.connections.put(other, color);
		other.connections.put(this, color);
	}

	public JSONObject toJSON()
	{
		Map<Color, JSONArray> colorArrays = new EnumMap<>(Color.class);

		for (ConnectionPoint key : connections.keySet())
		{
			Color color = connections.get(key);

			if (!colorArrays.containsKey(color))
				colorArrays.put(color, new JSONArray());

			JSONObject connection = new JSONObject();
			connection.put("entity_id", key.getEntityID());
			if (key.getCircuitID() != 0)
				connection.put("circuit_id", key.getCircuitID());
			colorArrays.get(color).put(connection);
		}

		JSONObject json = new JSONObject();
		for (Color color : colorArrays.keySet())
			json.put(color.toString(), colorArrays.get(color));
		return json;
	}
}
