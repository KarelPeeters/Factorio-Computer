package com.flaghacker.factoriopc.language.parser.structure;

import java.util.List;

public class Block implements StructureNode
{
	List<Statement> statements;

	public Block(List<Statement> statements)
	{
		this.statements = statements;
	}

	public List<Statement> getStatements()
	{
		return statements;
	}

	@Override
	public String toString()
	{
		String str = "";

		for (Statement statement : statements)
		{
			str += statement + ";\n";
		}

		return str;
	}

	@Override
	public <R> R accept(StructureVisitor<R> visitor)
	{
		return visitor.visit(this);
	}
}
