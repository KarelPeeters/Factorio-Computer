package com.flaghacker.factoriopc.language.parser.structure;

public class Invert implements Expression
{
	private Expression expression;

	public Invert(Expression expression)
	{
		this.expression = expression;
	}

	public Expression getExpression()
	{
		return expression;
	}

	@Override
	public String toString()
	{
		return "-(" + expression + ")";
	}

	@Override
	public <R> R accept(StructureVisitor<R> visitor)
	{
		return visitor.visit(this);
	}
}
