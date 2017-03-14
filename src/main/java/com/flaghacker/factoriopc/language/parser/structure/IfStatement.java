package com.flaghacker.factoriopc.language.parser.structure;

public class IfStatement implements Statement
{
	private Expression condition;
	private Block mainBody;
	private Block elseBody;	//can be null

	public IfStatement(Expression condition, Block mainBody, Block elseBody)
	{
		this.condition = condition;
		this.mainBody = mainBody;
		this.elseBody = elseBody;
	}

	public Expression getCondition()
	{
		return condition;
	}

	public Block getMainBody()
	{
		return mainBody;
	}

	public Block getElseBody()
	{
		return elseBody;
	}

	@Override
	public String toString()
	{
		return String.format("if (%s)\n{\n%s}\nelse\n{\n%s}", condition, mainBody, elseBody);
	}

	@Override
	public <R> R accept(StructureVisitor<R> visitor)
	{
		return visitor.visit(this);
	}
}
