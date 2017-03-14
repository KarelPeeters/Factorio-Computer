package com.flaghacker.factoriopc.commandline;

import com.flaghacker.factoriopc.circuits.Blueprint;
import com.flaghacker.factoriopc.circuits.Icons;
import com.flaghacker.factoriopc.language.compiler.CommandList;
import com.flaghacker.factoriopc.language.compiler.Compiler;
import com.flaghacker.factoriopc.language.compiler.Instructionizer;
import com.flaghacker.factoriopc.language.compiler.Program;
import com.flaghacker.factoriopc.language.parser.Parser;
import com.flaghacker.factoriopc.language.parser.Tokenizer;
import com.flaghacker.factoriopc.language.parser.structure.Block;

public class Converter
{
	private String source;
	private CommandList commands;
	private Program program;
	private Blueprint blueprint;

	private int requiredRAM = -1;

	public Converter()
	{
	}

	public void setSource(String source)
	{
		this.source = source;
	}

	public void setCommands(CommandList commands)
	{
		this.commands = commands;
	}

	public void setProgram(Program program)
	{
		this.program = program;
	}

	public void setBlueprint(Blueprint blueprint)
	{
		this.blueprint = blueprint;
	}

	public void setRequiredRAM(int requiredRAM)
	{
		this.requiredRAM = requiredRAM;
	}

	public String getSource()
	{
		if (source == null)
			throw new IllegalStateException();

		return source;
	}

	public CommandList getCommands()
	{
		if (commands == null)
		{
			getSource();

			Tokenizer tokenizer = new Tokenizer(source);
			Parser parser = new Parser(tokenizer);
			Block block = parser.parse();

			Compiler compiler = new Compiler();
			commands = compiler.visit(block);
			requiredRAM = compiler.getEnv().getRequiredRAM();
		}

		return commands;
	}

	public Program getProgram()
	{
		if (program == null)
		{
			getCommands();

			Instructionizer instructionizer = new Instructionizer(commands.getList());
			instructionizer.toInstructions();
			program =  new Program(instructionizer.getResult(), requiredRAM);
		}

		return program;
	}

	public Blueprint getBlueprint()
	{
		if (blueprint == null)
		{
			getProgram();

			blueprint = new Blueprint(program.toEntityGroup(), new Icons("arithmetic-combinator"));
		}

		return blueprint;
	}
}
