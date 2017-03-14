package com.flaghacker.factoriopc.language.compiler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Instructionizer
{
	private LinkedList<Command> source;
	private Map<Label, Integer> labelAddressMap = new HashMap<>();

	private LinkedList<Instruction> result = new LinkedList<>();

	public Instructionizer(List<Command> source)
	{
		this.source = new LinkedList<>(source);
	}

	public void toInstructions()
	{
		handleLabels();

		//compile
		for (int index = 0; index < source.size(); index++)
		{
			Command cmd = source.get(index);

			if (cmd instanceof Instruction)
			{
				result.add((Instruction) cmd);
			}
			else if (cmd instanceof GotoCommand)
			{
				GotoCommand gotoCmd = (GotoCommand) cmd;

				InstructionType type = gotoCmd.getType().getInstructionType();
				int indexDiff = labelAddressMap.get(gotoCmd.getLabel()) - index - 1;

				result.add(new Instruction(type, indexDiff));
			}
			else
				throw new RuntimeException("unexpected command class '" + (cmd == null ? "null" : cmd.getClass()) + "'");
		}
	}

	private void handleLabels()
	{
		int index = 0;
		Iterator<Command> iterator = source.iterator();

		while (iterator.hasNext())
		{
			Command cmd = iterator.next();

			if (cmd instanceof Label)
			{
				labelAddressMap.put((Label) cmd, index);
				iterator.remove();
			}
			else
			{
				index++;
			}
		}
	}

	public LinkedList<Instruction> getResult()
	{
		return result;
	}
}
