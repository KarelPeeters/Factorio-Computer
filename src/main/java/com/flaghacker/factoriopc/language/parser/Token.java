package com.flaghacker.factoriopc.language.parser;

import java.util.Objects;

public class Token
{
	public final int line;

	public final TokenType type;
	public final String content;

	public Token(int line, TokenType type, String content)
	{
		this.line = line;
		this.type = type;
		this.content = content;
	}

	public boolean contentEquals(String string)
	{
		return Objects.equals(this.content, string);
	}

	@Override
	public String toString()
	{
		return "Token{" +
				"line=" + line +
				", type=" + type +
				", content='" + content + '\'' +
				'}';
	}
}
