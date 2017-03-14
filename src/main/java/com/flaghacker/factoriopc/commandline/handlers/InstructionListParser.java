package com.flaghacker.factoriopc.commandline.handlers;

import com.flaghacker.factoriopc.language.compiler.Instruction;

public class InstructionListParser extends ListParser<Instruction>
{
	public InstructionListParser(String source)
	{
		super(source);
	}

	@Override
	public Instruction parseLine(String line, int lineNumber)
	{
		return HandlerUtils.parseInstruction(line);
	}
}
