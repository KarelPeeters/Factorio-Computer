package com.flaghacker.factoriopc.language.compiler;

import static com.flaghacker.factoriopc.language.compiler.Register.A;
import static com.flaghacker.factoriopc.language.compiler.Register.B;
import static com.flaghacker.factoriopc.language.compiler.Register.C;
import static com.flaghacker.factoriopc.language.compiler.Register.D;
import static com.flaghacker.factoriopc.language.compiler.Register.M;


public enum InstructionType
{
	//order is significant!

	NOP,
	PUTa(A, 1),
	PUTb(B, 1),
	PUTc(C, 1),
	PUTd(D, 1),
	LOADa(A),
	LOADb(B),
	LOADc(C),
	LOADd(D),
	MOVEa(A),
	MOVEb(B),
	MOVEc(C),
	PUTm(M, 1),
	SAVE(M),
	JUMP(1),
	JUMPT(1),
	JUMPF(1),
	ADD(D),
	SUB(D),
	MUL(D),
	DIV(D),
	GT(D),
	LT(D),
	EQ(D),
	GTE(D),
	LTE(D),
	NEQ(D),
	NOT(D),
	AND(D),
	OR(D),
	XOR(D),
	NORM(D),
	INCR(D),
	DECR(D),
	INV(D),
	MOD(D),
	;

	private Register affectedRegister;
	private int dataCount;

	InstructionType(Register affectedRegister, int dataCount)
	{
		this.affectedRegister = affectedRegister;
		this.dataCount = dataCount;
	}

	InstructionType(Register affectedRegister)
	{
		this(affectedRegister, 0);
	}

	InstructionType()
	{
		this(null, 0);
	}

	InstructionType(int dataCount)
	{
		this(null, dataCount);
	}

	public Register getAffectedRegister()
	{
		return affectedRegister;
	}

	public int getDataLength()
	{
		return dataCount;
	}

	public int getDataCount()
	{
		return dataCount;
	}

	public int getId()
	{
		return ordinal();
	}
}
