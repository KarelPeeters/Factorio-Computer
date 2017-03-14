package com.flaghacker.factoriopc.language.parser.structure;

public class LabelStatement implements Statement
{
	private String name;

	public LabelStatement(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	@Override
	public String toString()
	{
		return name;
	}

	@Override
	public <R> R accept(StructureVisitor<R> visitor)
	{
		return visitor.visit(this);
	}
}
