package com.flaghacker.factoriopc.language.parser.structure;

public interface StructureVisitor<R>
{
	R visit(Number node);

	R visit(Operation node);

	R visit(ArrayInitialization node);

	R visit(Invert node);

	R visit(NamedVariable node);

	R visit(Not node);

	R visit(WhileStatement node);

	R visit(NamedArrayAccess node);

	R visit(LabelStatement node);

	R visit(GotoStatement node);

	R visit(Assignment node);

	R visit(IfStatement node);

	R visit(ForStatement node);

	R visit(Block node);

	R visit(SugarAssignment node);

	R visit(IncrementStatement node);

	R visit(EmptyStatement node);

	R visit(CallExpression node);

	R visit(CallStatement node);
}
