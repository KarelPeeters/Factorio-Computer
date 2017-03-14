package com.flaghacker.factoriopc.language.parser.structure;

public class EmptyStatement implements Statement
{
	public EmptyStatement()
	{
	}

	@Override
	public <R> R accept(StructureVisitor<R> visitor)
	{
		return visitor.visit(this);
	}
}
