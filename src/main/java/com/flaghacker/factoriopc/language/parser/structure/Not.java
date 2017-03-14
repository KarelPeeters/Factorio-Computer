package com.flaghacker.factoriopc.language.parser.structure;

public class Not implements Expression
{
	private Expression expression;

	public Not(Expression expression)
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
		if (expression instanceof Operation)
			return "!(" + expression + ")";

		return "!" + expression;
	}

	@Override
	public <R> R accept(StructureVisitor<R> visitor)
	{
		return visitor.visit(this);
	}
}
