package com.flaghacker.factoriopc.circuits;

import org.json.JSONObject;

public class Lamp extends SingleConnectibleEntity
{
	private final Condition condition;

	public Lamp(int x, int y, String condition)
	{
		this(x, y, Parser.parseLampCondition(condition));
	}

	public Lamp(int x, int y, Condition condition)
	{
		super(x, y);
		this.condition = condition;
	}

	@Override
	public JSONObject toJSON()
	{
		JSONObject json = super.getBaseJson("small-lamp");

		JSONObject conditions = new JSONObject();
		conditions.put("circuit", condition.toJSON());
		json.put("conditions", conditions);

		return json;
	}

	public static class Condition
	{
		public final Signal firstSignal;
		public final char comparator;
		public final Signal secondSignal;
		public final int constant;

		public Condition(Signal firstSignal, char comparator, Signal secondSignal)
		{
			DeciderCondition.checkValidComparator(comparator);

			this.firstSignal = firstSignal;
			this.comparator = comparator;
			this.secondSignal = secondSignal;
			constant = 0;
		}

		public Condition(Signal firstSignal, char comparator, int constant)
		{
			DeciderCondition.checkValidComparator(comparator);

			this.firstSignal = firstSignal;
			this.comparator = comparator;
			secondSignal = null;
			this.constant = constant;
		}

		public JSONObject toJSON()
		{
			JSONObject json = new JSONObject();
			json.put("comparator", Character.toString(comparator));
			json.put("first_signal", firstSignal.toJSON());
			if (secondSignal != null)
				json.put("second_signal", secondSignal.toJSON());
			else
				json.put("constant", constant);

			return json;
		}

		@Override
		public String toString()
		{
			return "Condition{" +
					"firstSignal=" + firstSignal +
					", comparator=" + comparator +
					(
							secondSignal != null
							? ", secondSignal=" + secondSignal
							: ", constant=" + constant
					) +
					'}';
		}
	}
}
