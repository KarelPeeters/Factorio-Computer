package com.flaghacker.factoriopc.commandline.handlers;

import com.flaghacker.factoriopc.commandline.Converter;
import com.flaghacker.factoriopc.language.compiler.CommandList;

public class CommandsHandler implements LevelHandler
{
	@Override
	public void parseAndSet(String source, Converter converter)
	{
		CommandListParser parser = new CommandListParser(source);
		CommandList list = new CommandList(parser.parse());

		converter.setCommands(list);
	}

	@Override
	public String getAndSerialize(Converter converter)
	{
		return HandlerUtils.toMultiLineString(converter.getCommands().getList());
	}
}
