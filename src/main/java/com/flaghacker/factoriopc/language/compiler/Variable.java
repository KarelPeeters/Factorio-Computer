package com.flaghacker.factoriopc.language.compiler;

public class Variable
{
	private int address;
	private String name;

	public Variable(int address, String name)
	{
		this.address = address;
		this.name = name;
	}

	public int getAddress()
	{
		return address;
	}

	public String getName()
	{
		return name;
	}
}
