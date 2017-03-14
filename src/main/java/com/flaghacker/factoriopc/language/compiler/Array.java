package com.flaghacker.factoriopc.language.compiler;

public class Array
{
	private int address;
	private String name;
	private int length;

	public Array(int address, String name, int length)
	{
		this.address = address;
		this.name = name;
		this.length = length;
	}

	public int getAddress()
	{
		return address;
	}

	public String getName()
	{
		return name;
	}

	public int getLength()
	{
		return length;
	}
}
