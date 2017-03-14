package com.flaghacker.factoriopc.commandline.handlers;

import com.flaghacker.factoriopc.commandline.Converter;
import com.flaghacker.factoriopc.language.compiler.Program;

public class InstructionsHandler implements LevelHandler
{
	@Override
	public void parseAndSet(String source, Converter converter)
	{
		InstructionListParser parser = new InstructionListParser(source);
		converter.setProgram(new Program(parser.parse(), -1));
	}

	@Override
	public String getAndSerialize(Converter converter)
	{
		return HandlerUtils.toMultiLineString(converter.getProgram().getInstructions());
	}
}
