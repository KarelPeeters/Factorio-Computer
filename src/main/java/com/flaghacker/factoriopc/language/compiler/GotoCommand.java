package com.flaghacker.factoriopc.language.compiler;

import static com.flaghacker.factoriopc.language.compiler.InstructionType.JUMP;
import static com.flaghacker.factoriopc.language.compiler.InstructionType.JUMPF;
import static com.flaghacker.factoriopc.language.compiler.InstructionType.JUMPT;

public class GotoCommand implements Command
{
	private Type type;
	private Label label;

	public GotoCommand(Type type, Label label)
	{
		this.type = type;
		this.label = label;
	}

	public enum Type
	{
		ALWAYS(JUMP),
		TRUE(JUMPT),
		FALSE(JUMPF);

		private InstructionType instructionType;

		Type(InstructionType instructionType)
		{
			this.instructionType = instructionType;
		}

		public InstructionType getInstructionType()
		{
			return instructionType;
		}

		public String getSymbol()
		{
			if (this == ALWAYS)
				return "";

			return Character.toString(toString().charAt(0));
		}

		public static Type fromSymbol(String symbol)
		{
			if (symbol.equals(""))
				return ALWAYS;

			for (Type type : values())
			{
				if (symbol.equals(Character.toString(type.toString().charAt(0))))
					return type;
			}

			throw new IllegalArgumentException(String.format("invalid symbol '%s'", symbol));
		}
	}

	public Type getType()
	{
		return type;
	}

	public Label getLabel()
	{
		return label;
	}

	@Override
	public String toString()
	{
		String str = "GOTO";

		str += type.getSymbol();

		str += " " + label.toString();
		return str;
	}
}
