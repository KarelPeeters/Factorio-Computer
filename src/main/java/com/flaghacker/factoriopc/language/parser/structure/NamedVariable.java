package com.flaghacker.factoriopc.language.parser.structure;

public class NamedVariable implements Addressable
{
	private String name;

	public NamedVariable(String name)
	{
		this.name = name;
	}

	@Override
	public String toString()
	{
		return name;
	}

	public String getName()
	{
		return name;
	}

	@Override
	public <R> R accept(StructureVisitor<R> visitor)
	{
		return visitor.visit(this);
	}
}
