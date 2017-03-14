package com.flaghacker.factoriopc.circuits.entitygroups;

import com.flaghacker.factoriopc.circuits.ConnectionPoint;
import com.flaghacker.factoriopc.circuits.EntityGroup;
import com.flaghacker.factoriopc.circuits.SingleConnectible;

public class IOPort extends EntityGroup implements SingleConnectible
{
	public ConnectionPoint connection;

	public IOPort(int x, int y, int length)
	{
		super(x, y);

		if (length < 1)
			throw new IllegalArgumentException("length can't be less than 1");

		MemoryCell firstCell = null;
		MemoryCell previousCell = null;

		for (int i = 0; i < length; i++)
		{
			MemoryCell cell = new MemoryCell(i * (MemoryCell.WIDTH + 1), 0, -(i + 1));

			if (firstCell == null)
				firstCell = cell;
			else
				previousCell.connection.connect(cell.connection);

			previousCell = cell;
			add(cell);
		}

		this.connection = firstCell.connection;
	}

	@Override
	public ConnectionPoint getConnection()
	{
		return connection;
	}
}
