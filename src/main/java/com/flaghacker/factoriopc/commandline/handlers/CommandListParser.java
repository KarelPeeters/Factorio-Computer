package com.flaghacker.factoriopc.commandline.handlers;

import com.flaghacker.factoriopc.language.compiler.Command;
import com.flaghacker.factoriopc.language.compiler.Environment;
import com.flaghacker.factoriopc.language.compiler.GotoCommand;
import com.flaghacker.factoriopc.language.compiler.Instruction;
import com.flaghacker.factoriopc.language.compiler.Label;
import com.flaghacker.factoriopc.language.parser.ParserException;
import com.flaghacker.factoriopc.language.parser.TokenType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandListParser extends ListParser<Command>
{
	private static final Pattern gotoPattern;
	private static final Pattern labelPattern;

	static
	{
		String gotoRegex = "^(?:GOTO(T|F|) ([IDEN]))$";
		String labelRegex = "^(?:::([IDEN]))$";

		gotoRegex = gotoRegex.replace("[IDEN]", TokenType.IDENTIFIER.getRegex());
		labelRegex = labelRegex.replace("[IDEN]", TokenType.IDENTIFIER.getRegex());

		gotoPattern = Pattern.compile(gotoRegex);
		labelPattern = Pattern.compile(labelRegex);
	}

	private Environment env = new Environment();

	public CommandListParser(String source)
	{
		super(source);
	}

	@Override
	public Command parseLine(String line, int lineNumber)
	{
		Matcher gotoMatcher = gotoPattern.matcher(line);
		if (gotoMatcher.matches())
		{
			GotoCommand.Type type = GotoCommand.Type.fromSymbol(gotoMatcher.group(1));
			Label label = env.getLabel(gotoMatcher.group(2));

			return new GotoCommand(type, label);
		}

		Matcher labelMatcher = labelPattern.matcher(line);
		if (labelMatcher.matches())
		{
			return env.getLabel(labelMatcher.group(1));
		}

		try
		{
			Instruction instr = HandlerUtils.parseInstruction(line);
			return instr;
		}
		catch (ParserException e)
		{
			//throw ParserException below
		}

		throw new ParserException(String.format("invalid command '%s'", line));
	}
}
