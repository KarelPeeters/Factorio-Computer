package com.flaghacker.factoriopc.circuits;

import org.json.JSONObject;

import java.util.Collections;
import java.util.List;

public abstract class Entity extends Node
{
	private int id = -1;

	public Entity(int x, int y)
	{
		super(x, y);
	}

	protected JSONObject getBaseJson(String name)
	{
		JSONObject json = new JSONObject();
		//id (entity_number) omitted in JSON because it is equivalent to the array index in the blueprint
		json.put("name", name);
		json.put("position", getAbsolutePosition().toJSON());
		return json;
	}

	@Override
	public List<Entity> getEntities()
	{
		return Collections.singletonList(this);
	}

	public abstract JSONObject toJSON();

	public int getId()
	{
		if (id == -1)
			throw new IllegalStateException("Id has not yet been set");

		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}
}
