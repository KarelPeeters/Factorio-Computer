package com.flaghacker.factoriopc.circuits;

import org.json.JSONObject;

public enum Signal
{
	SIGNAL_0,
	SIGNAL_1,
	SIGNAL_2,
	SIGNAL_3,
	SIGNAL_4,
	SIGNAL_5,
	SIGNAL_6,
	SIGNAL_7,
	SIGNAL_8,
	SIGNAL_9,

	SIGNAL_A,
	SIGNAL_B,
	SIGNAL_C,
	SIGNAL_D,
	SIGNAL_E,
	SIGNAL_F,

	SIGNAL_BLUE,
	SIGNAL_GREEN,
	SIGNAL_RED,
	SIGNAL_YELLOW,

	SIGNAL_EACH,
	SIGNAL_ANYTHING,
	SIGNAL_EVERYTHING;

	private final String name;
	private final JSONObject json;

	Signal()
	{
		String id = this.name().replace("SIGNAL_", "");
		if (id.length() > 1)
			id = id.toLowerCase();
		name = "signal-" + id;

		json = new JSONObject();
		json.put("type", "virtual");
		json.put("name", name);
	}

	public static Signal fromNumber(int number)
	{
		return fromString(Integer.toString(number));
	}

	public static Signal fromString(String string)
	{
		string = "SIGNAL_" + string.toUpperCase();

		for (Signal signal:values())
			if (signal.name().equals(string))
				return signal;

		throw new IllegalArgumentException("invalid string " + string);
	}

	@Override
	public String toString()
	{
		return name;
	}

	public JSONObject toJSON()
	{
		return json;
	}
}
