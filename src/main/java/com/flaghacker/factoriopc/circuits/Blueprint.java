package com.flaghacker.factoriopc.circuits;

import org.json.JSONArray;
import org.json.JSONObject;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.util.List;

public class Blueprint
{
	private Icons icons;
	private EntityGroup content;

	public Blueprint(EntityGroup content, Icons icons)
	{
		this.content = content;
		this.icons = icons;
	}

	private void setIds()
	{
		List<Entity> entities = content.getEntities();

		for (int i = 0; i < entities.size(); i++)
			entities.get(i).setId(i + 1);
	}

	public JSONObject toJSON()
	{
		setIds();

		JSONArray entities = new JSONArray();
		for (Entity e:content.getEntities())
			entities.put(e.toJSON());

		JSONObject json = new JSONObject();
		json.put("entities", entities);
		json.put("icons", icons.toJSON());
		return json;
	}

	public String toBlueprintString()
	{
		return LuaCompression.compressJSON(this.toJSON());
	}
}
