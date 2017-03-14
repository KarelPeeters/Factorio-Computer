package com.flaghacker.factoriopc.commandline;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameters;

import java.util.function.Consumer;

@Parameters(commandDescription = "Display this help page.")
public class CommandHelp implements Consumer<JCommander>
{
	@Override
	public void accept(JCommander jCommander)
	{
		jCommander.usage();
	}
}
