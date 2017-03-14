package com.flaghacker.factoriopc.language.parser.structure;

public class GotoStatement implements Statement
{
	private String name;
	private Expression condition;	//can be null

	public GotoStatement(String name, Expression condition)
	{
		this.name = name;
		this.condition = condition;
	}

	public String getName()
	{
		return name;
	}

	public Expression getCondition()
	{
		return condition;
	}

	@Override
	public String toString()
	{
		return "goto " + name + (condition == null ? "" : " (" + condition + ")");
	}

	@Override
	public <R> R accept(StructureVisitor<R> visitor)
	{
		return visitor.visit(this);
	}
}
