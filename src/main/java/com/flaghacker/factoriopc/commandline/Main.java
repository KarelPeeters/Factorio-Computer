package com.flaghacker.factoriopc.commandline;

import com.beust.jcommander.JCommander;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Main
{
	private static final String PROGRAM_NAME = "factoriopc";

	public static void main(String[] args)
	{
		Map<String, Consumer<JCommander>> commands = new HashMap<>();

		commands.put("compile", new CommandCompile());
		commands.put("blueprint", new CommandBlueprint());
		commands.put("help", new CommandHelp());

		//setup
		JCommander jc = new JCommander();
		jc.setProgramName(PROGRAM_NAME);

		for (Map.Entry<String, Consumer<JCommander>> command : commands.entrySet())
			jc.addCommand(command.getKey(), command.getValue());

		jc.parse(args);
		commands.get(jc.getParsedCommand()).accept(jc);
	}
}
