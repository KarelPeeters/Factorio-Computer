package com.flaghacker.factoriopc.language.parser.structure;

public class Call
{
	private String name;
	private ExpressionList arguments;

	public Call(String name, ExpressionList arguments)
	{
		this.name = name;
		this.arguments = arguments;
	}

	public String getName()
	{
		return name;
	}

	public ExpressionList getArguments()
	{
		return arguments;
	}

	public CallStatement asStatement()
	{
		return new CallStatement(this);
	}

	public CallExpression asExpression()
	{
		return new CallExpression(this);
	}

	@Override
	public String toString()
	{
		return name + "(" + arguments + ")";
	}
}
