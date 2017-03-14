package com.flaghacker.factoriopc.language.compiler;

public class Label implements Command
{
	private String name;

	public Label(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	@Override
	public String toString()
	{
		return "::" + (name == null ? Integer.toHexString(System.identityHashCode(this)) : name);
	}
}
