package com.flaghacker.factoriopc.language.parser.structure;

public class IncrementStatement implements Statement
{
	private Addressable target;
	private boolean increment;

	public IncrementStatement(Addressable target, boolean increment)
	{
		this.target = target;
		this.increment = increment;
	}

	public Addressable getTarget()
	{
		return target;
	}

	public boolean isIncrement()
	{
		return increment;
	}

	@Override
	public String toString()
	{
		return target.toString() + (increment ? "++" : "--");
	}

	@Override
	public <R> R accept(StructureVisitor<R> visitor)
	{
		return visitor.visit(this);
	}
}
