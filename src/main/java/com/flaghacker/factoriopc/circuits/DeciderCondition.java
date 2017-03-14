package com.flaghacker.factoriopc.circuits;

import org.json.JSONObject;

public class DeciderCondition extends Condition
{
	public static final String comparators = "><=";

	private final char comparator;
	private final boolean copyInputToOutput;	//output = (copyInputToOutput ? input : '1')

	public DeciderCondition(Signal firstSignal, char comparator, Signal secondSignal, Signal outputSignal, boolean copyInputToOutput)
	{
		super(firstSignal, secondSignal, outputSignal);

		checkValidComparator(comparator);
		this.comparator = comparator;
		this.copyInputToOutput = copyInputToOutput;
	}

	public DeciderCondition(Signal firstSignal, char comparator, int constant, Signal outputSignal, boolean copyInputToOutput)
	{
		super(firstSignal, constant, outputSignal);

		checkValidComparator(comparator);
		this.comparator = comparator;
		this.copyInputToOutput = copyInputToOutput;
	}

	public static void checkValidComparator(char comparator)
	{
		if (comparators.indexOf(comparator) == -1)
			throw new IllegalArgumentException("Invalid comparator " + comparator);
	}

	@Override
	public JSONObject toJSON()
	{
		JSONObject json = new JSONObject();

		JSONObject decider = getSignalJSON();
		decider.put("comparator", Character.toString(comparator));
		decider.put("copy_count_from_input", copyInputToOutput);

		json.put("decider", decider);
		return json;
	}

	@Override
	public String getCombinatorType()
	{
		return "decider";
	}

	@Override
	public String toString()
	{
		return "DeciderCondition{" +
				"comparator=" + comparator +
				", copyInputToOutput=" + copyInputToOutput +
				"} " + super.toString();
	}
}