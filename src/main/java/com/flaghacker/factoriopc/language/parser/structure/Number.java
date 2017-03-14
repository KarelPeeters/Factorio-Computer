package com.flaghacker.factoriopc.language.parser.structure;

public class Number implements Expression
{
	private int value;

	public Number(int value)
	{
		this.value = value;
	}

	public int getValue()
	{
		return value;
	}

	@Override
	public String toString()
	{
		return Integer.toString(value);
	}

	@Override
	public <R> R accept(StructureVisitor<R> visitor)
	{
		return visitor.visit(this);
	}
}
