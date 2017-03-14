package com.flaghacker.factoriopc.language.parser.structure;

public class CallStatement implements Statement
{
	private Call call;

	public CallStatement(Call call)
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
