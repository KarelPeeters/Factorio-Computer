package com.flaghacker.factoriopc.language.parser.structure;

public interface StructureNode
{
	<R> R accept(StructureVisitor<R> visitor);
}
