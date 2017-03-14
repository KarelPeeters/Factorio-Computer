package com.flaghacker.factoriopc.commandline;

import com.flaghacker.factoriopc.commandline.handlers.LevelHandler;

public enum CodeLevel
{
	SOURCE,
	COMMANDS,
	INSTRUCTIONS,
	BLUEPRINT,
	;

	public boolean canCompileTo(CodeLevel level)
	{
		return level.ordinal() >= ordinal();
	}

	public LevelHandler getHandler()
	{
		return LevelHandler.getHandler(this);
	}
}
