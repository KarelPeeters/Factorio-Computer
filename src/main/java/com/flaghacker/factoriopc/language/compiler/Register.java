package com.flaghacker.factoriopc.language.compiler;

import static com.flaghacker.factoriopc.language.compiler.InstructionType.LOADa;
import static com.flaghacker.factoriopc.language.compiler.InstructionType.LOADb;
import static com.flaghacker.factoriopc.language.compiler.InstructionType.LOADc;
import static com.flaghacker.factoriopc.language.compiler.InstructionType.LOADd;
import static com.flaghacker.factoriopc.language.compiler.InstructionType.MOVEa;
import static com.flaghacker.factoriopc.language.compiler.InstructionType.MOVEb;
import static com.flaghacker.factoriopc.language.compiler.InstructionType.MOVEc;
import static com.flaghacker.factoriopc.language.compiler.InstructionType.PUTa;
import static com.flaghacker.factoriopc.language.compiler.InstructionType.PUTb;
import static com.flaghacker.factoriopc.language.compiler.InstructionType.PUTc;
import static com.flaghacker.factoriopc.language.compiler.InstructionType.PUTd;
import static com.flaghacker.factoriopc.language.compiler.InstructionType.PUTm;
import static com.flaghacker.factoriopc.language.compiler.InstructionType.SAVE;

public enum Register
{
	A, B, C, D, M;

	public InstructionType put()
	{
		switch (this)
		{
			case A:
				return PUTa;
			case B:
				return PUTb;
			case C:
				return PUTc;
			case D:
				return PUTd;
			case M:
				return PUTm;
		}

		throw new RuntimeException(String.format("register %s has no put instructionType", this));
	}

	public InstructionType load()
	{
		switch (this)
		{
			case A:
				return LOADa;
			case B:
				return LOADb;
			case C:
				return LOADc;
			case D:
				return LOADd;
		}

		throw new RuntimeException(String.format("register %s has no load instructionType", this));
	}

	public InstructionType move()
	{
		switch (this)
		{
			case A:
				return MOVEa;
			case B:
				return MOVEb;
			case C:
				return MOVEc;
			case M:
				return SAVE;
		}

		throw new RuntimeException(String.format("register %s has no move instructionType", this));
	}
}
