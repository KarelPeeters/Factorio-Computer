package com.flaghacker.factoriopc.commandline;

import com.beust.jcommander.ParameterException;
import com.beust.jcommander.converters.BaseConverter;

public class CodeLevelConverter extends BaseConverter<CodeLevel>
{
	public CodeLevelConverter(String optionName)
	{
		super(optionName);
	}

	@Override
	public CodeLevel convert(String value)
	{
		try
		{
			return CodeLevel.valueOf(value.toUpperCase());
		}
		catch (IllegalArgumentException e)
		{
			//try first letter
			for (CodeLevel level : CodeLevel.values())
				if (level.toString().charAt(0) == Character.toUpperCase(value.charAt(0)))
					return level;

			throw new ParameterException(getErrorString(value, "a CodeLevel"));
		}
	}
}
