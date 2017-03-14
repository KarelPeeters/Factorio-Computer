package com.flaghacker.factoriopc.circuits;

import org.json.JSONObject;

public class Filter
{
	private final Signal signal;
	private final int count;

	public Filter(Signal signal, int count)
	{
		this.signal = signal;
		this.count = count;
	}

	public JSONObject toJSON(int index)
	{
		JSONObject json = new JSONObject();

		json.put("count", count);
		json.put("index", index);
		json.put("signal", signal.toJSON());

		return json;
	}

	public int getCount()
	{
		return count;
	}

	@Override
	public String toString()
	{
		return "Filter{" +
				"signal=" + signal +
				", count=" + count +
				'}';
	}
}
