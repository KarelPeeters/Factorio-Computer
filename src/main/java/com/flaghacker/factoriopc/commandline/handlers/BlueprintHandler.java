package com.flaghacker.factoriopc.commandline.handlers;

import com.flaghacker.factoriopc.commandline.Converter;

public class BlueprintHandler implements LevelHandler
{
	@Override
	public void parseAndSet(String source, Converter converter)
	{
		throw new RuntimeException("a blueprint can't be parsed");
	}

	@Override
	public String getAndSerialize(Converter converter)
	{
		return converter.getBlueprint().toBlueprintString();
	}
}
