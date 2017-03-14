package com.flaghacker.factoriopc.commandline.handlers;

import com.flaghacker.factoriopc.commandline.CodeLevel;
import com.flaghacker.factoriopc.commandline.Converter;

public interface LevelHandler
{
	void parseAndSet(String source, Converter converter);

	String getAndSerialize(Converter converter);

	static LevelHandler getHandler(CodeLevel sourceLevel)
	{
		switch (sourceLevel)
		{
			case SOURCE:
				return new SourceHandler();
			case COMMANDS:
				return new CommandsHandler();
			case INSTRUCTIONS:
				return new InstructionsHandler();
			case BLUEPRINT:
				return new BlueprintHandler();
			default:
				throw new IllegalArgumentException(String.format("unknown sourceLevel '%s'", sourceLevel));
		}
	}
}
