package com.flaghacker.factoriopc.circuits;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public abstract class ConnectibleEntity extends Entity
{
	private List<ConnectionPoint> connections;

	public ConnectibleEntity(int x, int y, int connectionCount)
	{
		super(x, y);

		connections = new ArrayList<>(connectionCount);
		for (int i = 0; i < connectionCount; i++)
			connections.add(new ConnectionPoint(this, i + 1));
	}

	@Override
	protected JSONObject getBaseJson(String name)
	{
		JSONObject json =  super.getBaseJson(name);

		JSONObject connectionsJSON = new JSONObject();
		for (int i = 0; i < connections.size(); i++)
			connectionsJSON.put(Integer.toString(i + 1), connections.get(i).toJSON());
		json.put("connections", connectionsJSON);

		return json;
	}

	public int getConnectionCount()
	{
		return connections.size();
	}

	public ConnectionPoint getConnection(int circuitId)
	{
		return connections.get(circuitId - 1);
	}
}
