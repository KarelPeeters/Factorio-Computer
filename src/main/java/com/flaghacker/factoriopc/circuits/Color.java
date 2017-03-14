package com.flaghacker.factoriopc.circuits;

public enum Color
{
	RED,
	GREEN;

	public static final Color defaultColor = RED;

	@Override
	public String toString()
	{
		return name().toLowerCase();
	}
}
