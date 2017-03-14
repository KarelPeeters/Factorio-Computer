package com.flaghacker.factoriopc.language.compiler;

import java.util.Collections;
import java.util.Set;

public class Instruction implements Command
{
	private InstructionType type;
	private int[] data;

	public Instruction(InstructionType type, int[] data)
	{
		if (type.getDataLength() != data.length)
			throw new IllegalArgumentException(String.format("instructionType type %s requires %d ints as data instead of %d", type, type.getDataLength(), data.length));

		this.type = type;
		this.data = data;
	}
	@Override
	public Set<Register> getAffectedRegisters()
	{
		return Collections.singleton(type.getAffectedRegister());
	}

	public Instruction(InstructionType type)
	{
		this(type, new int[]{});
	}

	public Instruction(InstructionType type, int data)
	{
		this(type, new int[]{data});
	}

	public InstructionType getType()
	{
		return type;
	}

	public int[] getData()
	{
		return data;
	}

	@Override
	public String toString()
	{
		String str = type.toString();

		if (data.length > 0)
		{
			str += " ";

			for (int i = 0; i < data.length; i++)
			{
				str += data[i];
				if (i != data.length - 1)
					str += ", ";
			}
		}

		return str;
	}
}
