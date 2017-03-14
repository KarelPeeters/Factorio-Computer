package com.flaghacker.factoriopc.circuits.entitygroups;

import com.flaghacker.factoriopc.circuits.Combinator;
import com.flaghacker.factoriopc.circuits.ConnectionPoint;
import com.flaghacker.factoriopc.circuits.EntityGroup;
import com.flaghacker.factoriopc.circuits.SingleConnectible;

import static com.flaghacker.factoriopc.circuits.Color.GREEN;

public class MemoryCell extends EntityGroup implements SingleConnectible
{
	public static final int WIDTH = 2;
	public static final int HEIGHT = 4;

	public final ConnectionPoint connection;
	public final int address;

	public MemoryCell(int x, int y, int address)
	{
		super(x, y);
		this.address = address;

		Combinator ca = new Combinator(0, 0, String.format("A = %d -> RED = 1", address));
		Combinator ci = new Combinator(0, 1, "[9] * RED -> GREEN");
		Combinator co = new Combinator(0, 2, "GREEN * RED -> [8]");
		Combinator cm = new Combinator(0, 3, "GREEN * [7] -> GREEN");

		ca.input.connect(ci.input);
		ci.input.connect(co.output);
		ci.input.connect(cm.input);

		ca.output.connect(ci.input, GREEN);
		ci.output.connect(ci.input, GREEN);
		ci.input.connect(co.input, GREEN);
		co.input.connect(cm.input, GREEN);
		cm.input.connect(cm.output, GREEN);

		connection = ca.input;
		addAll(ca, ci, co, cm);
	}

	@Override
	public ConnectionPoint getConnection()
	{
		return connection;
	}
}
