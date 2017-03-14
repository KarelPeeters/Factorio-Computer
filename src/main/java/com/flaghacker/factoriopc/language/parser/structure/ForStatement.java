package com.flaghacker.factoriopc.language.parser.structure;

public class ForStatement implements Statement
{
	private Statement start;
	private Expression condition;
	private Statement step;
	private Block body;

	public ForStatement(Statement start, Expression condition, Statement step, Block body)
	{
		this.start = start;
		this.condition = condition;
		this.step = step;
		this.body = body;
	}

	public Statement getStart()
	{
		return start;
	}

	public Expression getCondition()
	{
		return condition;
	}

	public Statement getStep()
	{
		return step;
	}

	public Block getBody()
	{
		return body;
	}

	@Override
	public String toString()
	{
		return String.format("for (%s; %s; %s)\n{\n%s}", start, condition, step, body);
	}

	@Override
	public <R> R accept(StructureVisitor<R> visitor)
	{
		return visitor.visit(this);
	}
}
