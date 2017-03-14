package com.flaghacker.factoriopc.circuits;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class ConstantCombinator extends SingleConnectibleEntity
{
	private final List<Filter> filters;

	public ConstantCombinator(int x, int y, String filters)
	{
		this(x, y, Parser.parseFilterList(filters));
	}

	public ConstantCombinator(int x, int y, List<Filter> filters)
	{
		super(x, y);
		this.filters = filters;
	}

	@Override
	public JSONObject toJSON()
	{
		JSONObject json = super.getBaseJson("constant-combinator");
		JSONArray filtersJSON = new JSONArray();
		for (int i = 0; i < filters.size(); i++)
		{
			Filter filter = filters.get(i);

			if(filter.getCount() != 0)
				filtersJSON.put(filter.toJSON(i + 1));
		}
		json.put("filters", filtersJSON);
		return json;
	}
}
