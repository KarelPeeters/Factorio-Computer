package com.flaghacker.factoriopc.language.parser.structure;

public class CallExpression implements Expression
{
	private Call call;

	public CallExpression(Call call)
	{
		this.call = call;
	}

	public Call getCall()
	{
		return call;
	}

	@Override
	public String toString()
	{
		return call.toString();
	}

	@Override
	public <R> R accept(StructureVisitor<R> visitor)
	{
		return visitor.visit(this);
	}
}
