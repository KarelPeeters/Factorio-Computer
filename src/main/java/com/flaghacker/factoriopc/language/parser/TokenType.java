package com.flaghacker.factoriopc.language.parser;

import java.util.regex.Pattern;

public enum TokenType
{
	EOF			("^$"),
	WHITESPACE	("( |\t)+"),
	COMMENT		("(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|(?://.*)"),	//credits to http://blog.ostermiller.org/find-comment

	OPENB		("\\("),
	CLOSEB		("\\)"),
	OPENS		("\\["),
	CLOSES		("\\]"),
	OPENC		("\\{"),
	CLOSEC		("\\}"),

	S_ASSIGN	("(\\+|\\-|\\*|/)="),
	INCR_DECR	("\\+\\+|\\-\\-"),

	AND_OR		("(&&)|(\\|\\|)"),
	COMP		(">=|<=|==|>|<|!="),
	ADD_SUB		("\\+|-"),
	MUL_DIV		("\\*|/"),
	MOD			("%"),
	NOT			("!"),

	ASSIGN		("="),

	SEMICOLON 	(";"),
	COMMA	 	(","),

	KEYWORD		("for|while|if|else|goto|::"),
	BOOLEAN		("true|false"),
	IDENTIFIER	("[A-Za-z_][A-Za-z_0-9]*"),
	NUMBER 		("[0-9]+"),
	;

	private final Pattern pattern;
	private final String regex;

	TokenType(String regex)
	{
		this.regex = regex;
		this.pattern = Pattern.compile("^(" + regex + ")");
	}

	public Pattern getPattern()
	{
		return pattern;
	}

	public String getRegex()
	{
		return regex;
	}
}
