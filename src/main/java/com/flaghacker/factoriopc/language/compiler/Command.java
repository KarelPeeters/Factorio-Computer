package com.flaghacker.factoriopc.language.compiler;

import java.util.Collections;
import java.util.Set;

public interface Command
{
	default Set<Register> getAffectedRegisters()
	{
		return Collections.emptySet();
	}
}
