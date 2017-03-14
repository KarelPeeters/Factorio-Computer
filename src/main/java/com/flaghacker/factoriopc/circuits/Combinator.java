package com.flaghacker.factoriopc.circuits;

import org.json.JSONObject;

public class Combinator extends ConnectibleEntity
{
	private static final Direction DEFAULT_DIRECTION = Direction.EAST;

	public final ConnectionPoint input = getConnection(1);
	public final ConnectionPoint output = getConnection(2);

	private final Direction direction;
	private final Condition condition;
	private final String type;

	public Combinator(int x, int y, String condition)
	{
		this(x, y, DEFAULT_DIRECTION, Parser.parseCondition(condition));
	}

	public Combinator(int x, int y, Direction direction, String condition)
	{
		this(x, y, direction, Parser.parseCondition(condition));
	}

	public Combinator(int x, int y, Direction direction, Condition condition)
	{
		super(x, y, 2);
		this.direction = direction;
		this.condition = condition;
		this.type = condition.getCombinatorType();
	}

	@Override
	public JSONObject toJSON()
	{
		JSONObject json = getBaseJson(type + "-combinator");
		json.put("direction", direction.toNumber());
		json.put("conditions", condition.toJSON());
		return json;
	}
}
