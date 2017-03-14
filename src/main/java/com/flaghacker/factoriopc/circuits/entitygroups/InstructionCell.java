package com.flaghacker.factoriopc.circuits.entitygroups;

import com.flaghacker.factoriopc.circuits.Color;
import com.flaghacker.factoriopc.circuits.Combinator;
import com.flaghacker.factoriopc.circuits.ConnectionPoint;
import com.flaghacker.factoriopc.circuits.ConstantCombinator;
import com.flaghacker.factoriopc.circuits.EntityGroup;
import com.flaghacker.factoriopc.circuits.Filter;
import com.flaghacker.factoriopc.circuits.Signal;
import com.flaghacker.factoriopc.circuits.SingleConnectible;

import java.util.ArrayList;
import java.util.List;

import static com.flaghacker.factoriopc.circuits.Direction.WEST;

public class InstructionCell extends EntityGroup implements SingleConnectible
{
	public static final int WIDTH = 2;
	public static final int HEIGHT = 4;

	public final ConnectionPoint connection;

	public InstructionCell(int x, int y, int address, int[] data)
	{
		super(x, y);

		if (data.length > 3)
			throw new IllegalArgumentException("Data can't be larger than 3");

		Combinator ca = new Combinator(0, 0, String.format("E = %d -> C = 1", address));
		Combinator co = new Combinator(0, 1, WEST, "EACH * C -> EACH");
		Combinator cn = new Combinator(0, 2, WEST, "C * -1 -> C");

		List<Filter> filters = new ArrayList<>();
		for (int i = 0; i < data.length; i++)
			filters.add(new Filter(Signal.fromNumber(i), data[i]));
		ConstantCombinator cc = new ConstantCombinator(0, 3, filters);

		ca.input.connect(co.output);
		co.output.connect(cn.output);
		ca.output.connect(co.input, Color.GREEN);
		co.input.connect(cn.input, Color.GREEN);
		co.input.connect(cc);

		connection = ca.input;
		addAll(ca, co, cn, cc);
	}

	@Override
	public ConnectionPoint getConnection()
	{
		return connection;
	}
}
