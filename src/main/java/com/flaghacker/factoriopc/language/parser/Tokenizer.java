package com.flaghacker.factoriopc.language.parser;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;

import static com.flaghacker.factoriopc.language.parser.TokenType.COMMENT;

public class Tokenizer
{
	private String input;
	private int line = 1;

	public Tokenizer(String input)
	{
		this.input = input.replace("\r\n", "\n");
	}

	/**
	 * Attempts to parse the next token in the input. Returns EOF tokens when the input is empty.
	 *
	 * @return the next token
	 * @throws TokenizeException when no token matches
	 */
	public Token next() throws TokenizeException
	{
		//skip newlines
		while (input.length() > 0 && input.charAt(0) == '\n')
		{
			line++;
			input = input.substring(1);
		}

		//match next token
		for (TokenType tokenType : TokenType.values())
		{
			Matcher matcher = tokenType.getPattern().matcher(input);
			if (!matcher.find())
				continue;

			String content = matcher.group();

			if (tokenType == COMMENT)
				line += StringUtils.countMatches(content, "\n");

			input = input.substring(matcher.end());
			return new Token(line, tokenType, content);
		}

		//no valid token found
		throw new TokenizeException("unexpected character '" + input.charAt(0) + "' at line " + line);
	}
}
