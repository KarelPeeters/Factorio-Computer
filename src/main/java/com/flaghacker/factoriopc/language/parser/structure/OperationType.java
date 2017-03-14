package com.flaghacker.factoriopc.language.parser.structure;

import com.flaghacker.factoriopc.language.compiler.InstructionType;

public enum OperationType
{
	AND	("&&",	0),
	OR	("||",	0),
	GT	(">",	1),
	LT	("<",	1),
	EQ	("==",	1),
	NEQ	("!=",	1),
	GTE	(">=",	1),
	LTE	("<=",	1),
	ADD	("+",	2),
	SUB	("-",	2),
	MUL	("*",	3),
	DIV	("/",	3),
	MOD	("%", 	4),
	;

	static
	{
		AND.mirror = AND;
		OR.mirror = OR;

		GT.mirror = LT;
		LT.mirror = GT;

		EQ.mirror = EQ;
		NEQ.mirror = NEQ;

		GTE.mirror = LTE;
		LTE.mirror = GTE;

		ADD.mirror = ADD;
		MUL.mirror = MUL;
	}

	private String symbol;
	private int order;	//order(*) > order(+)
	private OperationType mirror;

	private InstructionType instructionType;

	OperationType(String symbol, int order)
	{
		this.symbol = symbol;
		this.order = order;

		this.instructionType = InstructionType.valueOf(this.name());
	}

	public static OperationType fromSymbol(String symbol)
	{
		for (OperationType type : values())
			if (type.symbol.equals(symbol))
				return type;

		throw new IllegalArgumentException("no type found for symbol '" + symbol + "'");
	}

	public String getSymbol()
	{
		return symbol;
	}

	public int getOrder()
	{
		return order;
	}

	public boolean hasMirror()
	{
		return mirror != null;
	}

	public OperationType getMirror()
	{
		return mirror;
	}

	public InstructionType getInstructionType()
	{
		return instructionType;
	}

	@Override
	public String toString()
	{
		return getSymbol();
	}
}