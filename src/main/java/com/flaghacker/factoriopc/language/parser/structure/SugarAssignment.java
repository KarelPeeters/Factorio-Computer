package com.flaghacker.factoriopc.language.parser.structure;

public class SugarAssignment implements Statement
{
	private Addressable target;
	private OperationType type;
	private Expression value;

	public SugarAssignment(Addressable target, OperationType type, Expression value)
	{
		this.target = target;
		this.type = type;
		this.value = value;
	}

	public Addressable getTarget()
	{
		return target;
	}

	public OperationType getType()
	{
		return type;
	}

	public Expression getValue()
	{
		return value;
	}

	@Override
	public String toString()
	{
		return String.format("%s %s= %s", target, type, value);
	}

	@Override
	public <R> R accept(StructureVisitor<R> visitor)
	{
		return visitor.visit(this);
	}
}
