package com.flaghacker.factoriopc.circuits.entitygroups;

import com.flaghacker.factoriopc.circuits.ConnectionPoint;
import com.flaghacker.factoriopc.circuits.EntityGroup;
import com.flaghacker.factoriopc.circuits.Lamp;
import com.flaghacker.factoriopc.circuits.SingleConnectible;

public class InstructionArray extends EntityGroup implements SingleConnectible
{
	public final ConnectionPoint connection;

	public InstructionArray(int x, int y, int[][] data, int requiredRAM)
	{
		super(x, y);

		int length = data.length;
		if (length < 1)
			throw new IllegalArgumentException("data length can't be smaller than 1");

		InstructionCell firstCell = null;
		InstructionCell previousCell = null;

		for (int i = 0; i < length; i++)
		{
			InstructionCell cell = new InstructionCell(InstructionCell.WIDTH * i, 0, i, data[i]);

			if (previousCell == null)
				firstCell = cell;
			else
				cell.connection.connect(previousCell.connection);

			add(cell);
			previousCell = cell;
		}

		assert firstCell != null;
		connection = firstCell.connection;

		if (requiredRAM > 0)
		{
			Lamp lamp = new Lamp(-1, 3, String.format("[6] < %d", requiredRAM));
			lamp.connect(connection);
			add(lamp);
		}
	}

	@Override
	public ConnectionPoint getConnection()
	{
		return connection;
	}
}
