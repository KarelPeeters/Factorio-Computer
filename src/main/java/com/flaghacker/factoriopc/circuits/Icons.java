package com.flaghacker.factoriopc.circuits;

import org.json.JSONArray;
import org.json.JSONObject;

public class Icons
{
	private final String[] iconNames = new String[4];

	public Icons(String... iconNames)
	{
		if (iconNames.length > 4)
			throw new IllegalArgumentException("maximum icon count is 4");

		System.arraycopy(iconNames, 0, this.iconNames, 0, iconNames.length);
	}

	public JSONArray toJSON()
	{
		JSONArray arr = new JSONArray();

		for (int i = 0; i < iconNames.length; i++)
		{
			if (iconNames[i] == null)
				continue;

			JSONObject icon = new JSONObject();
			icon.put("index", i + 1);
			icon.put("name", iconNames[i]);
			arr.put(icon);
		}

		return arr;
	}
}
