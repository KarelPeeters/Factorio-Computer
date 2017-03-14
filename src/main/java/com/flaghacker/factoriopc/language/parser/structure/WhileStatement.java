package com.flaghacker.factoriopc.language.parser.structure;

public class WhileStatement implements Statement
{
	private Expression condition;
	private Block body;

	public WhileStatement(Expression condition, Block body)
	{
		this.condition = condition;
		this.body = body;
	}

	public Expression getCondition()
	{
		return condition;
	}

	public Block getBody()
	{
		return body;
	}

	@Override
	public String toString()
	{
		return String.format("while (%s)\n{\n%s}", condition, body);
	}

	@Override
	public <R> R accept(StructureVisitor<R> visitor)
	{
		return visitor.visit(this);
	}
}
