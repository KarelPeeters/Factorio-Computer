package com.flaghacker.factoriopc.language.parser.structure;

public class Operation implements Expression
{
	private Expression left;
	private OperationType type;
	private Expression right;

	public Operation(Expression left, OperationType type, Expression right)
	{
		this.left = left;
		this.type = type;
		this.right = right;
	}

	public Expression getLeft()
	{
		return left;
	}

	public OperationType getType()
	{
		return type;
	}

	public Expression getRight()
	{
		return right;
	}

	@Override
	public String toString()
	{
		return toContainedString(left) + " " + type.getSymbol() + " " + toContainedString(right);
	}

	private String toContainedString(Expression expr)
	{
		if (expr instanceof Operation && ((Operation) expr).type.getOrder() < type.getOrder())
			return "(" + expr + ")";
		else
			return expr.toString();
	}

	@Override
	public <R> R accept(StructureVisitor<R> visitor)
	{
		return visitor.visit(this);
	}
}
