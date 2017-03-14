package com.flaghacker.factoriopc.circuits;

import org.json.JSONObject;

public class ArithmeticCondition extends Condition
{
	public static final String operations = "+-*/";

	public final char operation;

	public ArithmeticCondition(Signal firstSignal, char operation, Signal secondSignal, Signal outputSignal)
	{
		super(firstSignal, secondSignal, outputSignal);

		checkValidOperation(operation);
		this.operation = operation;
	}

	public ArithmeticCondition(Signal firstSignal, char operation, int constant, Signal outputSignal)
	{
		super(firstSignal, constant, outputSignal);

		checkValidOperation(operation);
		this.operation = operation;
	}

	public static void checkValidOperation(char operation)
	{
		if (operations.indexOf(operation) == -1)
			throw new IllegalArgumentException("invalid operation " + operation);
	}

	@Override
	public JSONObject toJSON()
	{
		JSONObject json = new JSONObject();

		JSONObject arithmetic = getSignalJSON();
		arithmetic.put("operation", Character.toString(operation));

		json.put("arithmetic", arithmetic);
		return json;
	}

	@Override
	public String getCombinatorType()
	{
		return "arithmetic";
	}

	@Override
	public String toString()
	{
		return "ArithmeticCondition{" +
				"operation=" + operation +
				"} " + super.toString();
	}
}