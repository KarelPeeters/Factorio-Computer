package com.flaghacker.factoriopc.language.parser.structure;

public class Assignment implements Statement
{
	private Addressable target;
	private Expression value;

	public Assignment(Addressable target, Expression value)
	{
		this.target = target;
		this.value = value;
	}

	public Addressable getTarget()
	{
		return target;
	}

	public Expression getValue()
	{
		return value;
	}

	@Override
	public String toString()
	{
		return target + " = " + value;
	}

	@Override
	public <R> R accept(StructureVisitor<R> visitor)
	{
		return visitor.visit(this);
	}
}
