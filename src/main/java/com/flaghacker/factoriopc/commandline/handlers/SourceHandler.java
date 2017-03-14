package com.flaghacker.factoriopc.commandline.handlers;

import com.flaghacker.factoriopc.commandline.Converter;

public class SourceHandler implements LevelHandler
{
	@Override
	public void parseAndSet(String source, Converter converter)
	{
		converter.setSource(source);
	}

	@Override
	public String getAndSerialize(Converter converter)
	{
		return converter.getSource();
	}
}
