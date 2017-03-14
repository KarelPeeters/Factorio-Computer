package com.flaghacker.factoriopc.language.compiler;

import com.flaghacker.factoriopc.circuits.entitygroups.InstructionArray;

import java.util.List;

public class Program
{
	private List<Instruction> instructions;
	private int requiredRAM;

	public Program(List<Instruction> instructions, int requiredRAM)
	{
		this.instructions = instructions;
		this.requiredRAM = requiredRAM;
	}

	public List<Instruction> getInstructions()
	{
		return instructions;
	}

	public int getRequiredRAM()
	{
		return requiredRAM;
	}

	public InstructionArray toEntityGroup()
	{
		int[][] data = new int[instructions.size()][];

		for (int i = 0; i < instructions.size(); i++)
		{
			Instruction instruction = instructions.get(i);

			int[] instr = new int[instruction.getData().length + 1];
			instr[0] = instruction.getType().getId();
			System.arraycopy(instruction.getData(), 0, instr, 1, instruction.getData().length);

			data[i] = instr;
		}

		return new InstructionArray(0, 0, data, requiredRAM);
	}

	@Override
	public String toString()
	{
		return "Program{" +
				"instructions=" + instructions +
				", requiredRAM=" + requiredRAM +
				'}';
	}
}
