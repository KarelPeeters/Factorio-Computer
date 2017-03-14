package com.flaghacker.factoriopc.circuits.entitygroups;

import com.flaghacker.factoriopc.circuits.ConnectionPoint;
import com.flaghacker.factoriopc.circuits.ConstantCombinator;
import com.flaghacker.factoriopc.circuits.EntityGroup;
import com.flaghacker.factoriopc.circuits.PowerPole;
import com.flaghacker.factoriopc.circuits.SingleConnectible;

import static com.flaghacker.factoriopc.circuits.PowerPole.Type.SUBSTATION;

public class MemoryBlock extends EntityGroup implements SingleConnectible
{
	public static final int CELL_COUNT = 20;

	public final ConnectionPoint connection;

	public MemoryBlock(int x, int y, int startAddress)
	{
		super(x, y);

		PowerPole pole = new PowerPole(6, 7, SUBSTATION);
		ConstantCombinator cc = new ConstantCombinator(6, 8, "[6] = " + CELL_COUNT);
		cc.connect(pole);

		connection = pole.getConnection();
		addAll(pole, cc);

		MemoryCell previousCell = null;
		int address = startAddress;

		for (int cellY = 0; cellY < 3; cellY++)
		{
			for (int cellX = 0; cellX < 7; cellX++)
			{
				if (cellX == 3 && cellY == 1)
				{
					previousCell = null;
					continue;
				}

				int posX = MemoryCell.WIDTH *cellX;
				int posY = 1 + MemoryCell.HEIGHT *cellY;

				MemoryCell cell = new MemoryCell(posX, posY, address);

				if (previousCell != null)
					cell.connection.connect(previousCell.connection);
				if (cellX == 3 || (cellY == 1 && (cellX == 2 || cellX == 4)))
					pole.connect(cell.connection);

				previousCell = cell;
				add(cell);
				address++;
			}

			previousCell = null;
		}
	}

	@Override
	public ConnectionPoint getConnection()
	{
		return connection;
	}
}
