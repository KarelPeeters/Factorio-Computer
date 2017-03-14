package com.flaghacker.factoriopc.language.parser.structure;

import com.flaghacker.factoriopc.language.parser.ParserException;

public class ArrayInitialization implements Statement
{
	private String name;

	//(length == -1 xor values == null) == true
	private int length = -1;
	private ExpressionList values = null;

	public ArrayInitialization(String name, int length)
	{
		if (length < 1)
			throw new ParserException("an array must have a length >= 1");

		this.name = name;
		this.length = length;
	}

	public ArrayInitialization(String name, ExpressionList values)
	{
		if (values.size() < 1)
			throw new ParserException("an array must have a length >= 1");

		this.name = name;
		this.values = values;
	}

	public String getName()
	{
		return name;
	}

	public int getLength()
	{
		return length;
	}

	public ExpressionList getValues()
	{
		return values;
	}

	public boolean isLengthInitializer()
	{
		return length != -1;
	}

	public boolean isListInitializer()
	{
		return values != null;
	}

	@Override
	public String toString()
	{
		if (isLengthInitializer())
			return name + " = [" + length + "]";
		
		return name + " = {" + values + "}";
	}

	@Override
	public <R> R accept(StructureVisitor<R> visitor)
	{
		return visitor.visit(this);
	}
}
