package com.flaghacker.factoriopc.circuits;

import org.json.JSONObject;

public abstract class Condition
{
	public final Signal firstSignal;
	public final Signal secondSignal;
	public final int constant;			//replaces secondSignal if (secondSignal == null)
	public final Signal outputSignal;

	public Condition(Signal firstSignal, Signal secondSignal, Signal outputSignal)
	{
		this.firstSignal = firstSignal;
		this.secondSignal = secondSignal;
		constant = 0;
		this.outputSignal = outputSignal;
	}

	public Condition(Signal firstSignal, int constant, Signal outputSignal)
	{
		this.firstSignal = firstSignal;
		secondSignal = null;
		this.constant = constant;
		this.outputSignal = outputSignal;
	}

	public abstract JSONObject toJSON();

	protected JSONObject getSignalJSON()
	{
		JSONObject json = new JSONObject();

		json.put("first_signal", firstSignal.toJSON());
		if (secondSignal == null)
			json.put("constant", constant);
		else
			json.put("second_signal", secondSignal.toJSON());

		if (outputSignal != null)
			json.put("output_signal", outputSignal.toJSON());

		return json;
	}

	public abstract String getCombinatorType();

	@Override
	public String toString()
	{
		return "Condition{" +
				"firstSignal=" + firstSignal +
				(secondSignal != null ? ", secondSignal=" + secondSignal :
				", constant=" + constant) +
				", outputSignal=" + outputSignal +
				'}';
	}
}
