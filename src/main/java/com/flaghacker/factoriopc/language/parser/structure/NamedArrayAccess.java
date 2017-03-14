package com.flaghacker.factoriopc.language.parser.structure;

public class NamedArrayAccess implements Addressable
{
	private String name;
	private Expression index;

	public NamedArrayAccess(String name, Expression index)
	{
		this.name = name;
		this.index = index;
	}

	public String getName()
	{
		return name;
	}

	public Expression getIndex()
	{
		return index;
	}

	@Override
	public String toString()
	{
		return name + "[" + index + "]";
	}

	@Override
	public <R> R accept(StructureVisitor<R> visitor)
	{
		return visitor.visit(this);
	}
}
