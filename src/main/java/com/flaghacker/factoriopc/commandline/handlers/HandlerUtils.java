package com.flaghacker.factoriopc.commandline.handlers;

import com.flaghacker.factoriopc.language.compiler.Instruction;
import com.flaghacker.factoriopc.language.compiler.InstructionType;
import com.flaghacker.factoriopc.language.parser.ParserException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HandlerUtils
{
	public static <E> String toMultiLineString(List<E> list)
	{
		String string = "";

		for (int i = 0; i < list.size(); i++)
		{
			string += list.get(i);

			if (i != list.size() - 1)
				string += System.lineSeparator();
		}

		return string;
	}

	private static final String instructionRegex = "^(?:([A-Za-z]+)( \\d+(?:, ?\\d+)*)?)$";
	private static final Pattern instructionPattern = Pattern.compile(instructionRegex);

	public static Instruction parseInstruction(String source)
	{
		Matcher instructionMatcher = instructionPattern.matcher(source);
		if (instructionMatcher.matches())
		{
			InstructionType type;
			try
			{
				type = InstructionType.valueOf(instructionMatcher.group(1));
			}
			catch (IllegalArgumentException e)
			{
				throw new ParserException(String.format("illegal instruction type '%s'", instructionMatcher.group(1)));
			}

			String[] args = instructionMatcher.group(2).replace(" ", "").split(",");
			int[] intArgs = new int[args.length];

			for (int i = 0; i < args.length; i++)
				intArgs[i] = Integer.parseInt(args[i]);

			return new Instruction(type, intArgs);
		}

		throw new ParserException(String.format("invalid instruction '%s'", source));
	}
}
