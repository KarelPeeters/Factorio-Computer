package com.flaghacker.factoriopc.language.compiler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommandList
{
	private List<Command> commands;

	public CommandList()
	{
		commands = new ArrayList<>();
	}

	public CommandList(List<Command> commands)
	{
		this.commands = commands;
	}

	public Set<Register> getAffectedRegisters()
	{
		Set<Register> registers = new HashSet<>();

		for (Command cmd : commands)
			registers.addAll(cmd.getAffectedRegisters());

		return registers;
	}

	public boolean affects(Register reg)
	{
		return getAffectedRegisters().contains(reg);
	}


	public void add(Object ... objects)
	{
		for (Object obj : objects)
		{
			if (obj instanceof Command)
				commands.add((Command) obj);
			else if (obj instanceof InstructionType)
				commands.add(new Instruction((InstructionType) obj));
			else if (obj instanceof CommandList)
				commands.addAll(((CommandList) obj).getList());
			else
				throw new ClassCastException(String.format("invalid class '%s'", (obj == null ? "null" : obj.getClass().toString())));
		}
	}

	public List<Command> getList()
	{
		return commands;
	}

	@Override
	public String toString()
	{
		String str = "";

		for (Command command : commands)
			str += command + "\n";

		return str;
	}

	public static CommandList of(Object ... objects)
	{
		CommandList commandList = new CommandList();
		commandList.add(objects);
		return commandList;
	}
}
