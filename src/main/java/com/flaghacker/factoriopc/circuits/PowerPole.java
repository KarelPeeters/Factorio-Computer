package com.flaghacker.factoriopc.circuits;

import org.json.JSONObject;

public class PowerPole extends SingleConnectibleEntity
{
	private Type type;

	public PowerPole(int x, int y, Type type)
	{
		super(x, y);
		this.type = type;
	}

	@Override
	public JSONObject toJSON()
	{
		return getBaseJson(type.name);
	}

	public enum Type
	{
		SMALL("small-electric-pole"),
		MEDIUM("medium-electric-pole"),
		BIG("big-electric-pole"),
		SUBSTATION("substation");

		public final String name;

		Type(String name)
		{
			this.name = name;
		}
	}
}
