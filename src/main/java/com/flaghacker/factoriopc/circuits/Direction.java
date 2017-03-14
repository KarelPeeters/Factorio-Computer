package com.flaghacker.factoriopc.circuits;

public enum Direction
{
	NORTH,
	EAST,
	SOUTH,
	WEST;

	public int toNumber()
	{
		return ordinal() * 2;
	}
}
