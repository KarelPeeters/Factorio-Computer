package com.flaghacker.factoriopc.language.compiler;

import com.flaghacker.factoriopc.language.parser.structure.Addressable;
import com.flaghacker.factoriopc.language.parser.structure.ArrayInitialization;
import com.flaghacker.factoriopc.language.parser.structure.Assignment;
import com.flaghacker.factoriopc.language.parser.structure.Block;
import com.flaghacker.factoriopc.language.parser.structure.CallExpression;
import com.flaghacker.factoriopc.language.parser.structure.CallStatement;
import com.flaghacker.factoriopc.language.parser.structure.EmptyStatement;
import com.flaghacker.factoriopc.language.parser.structure.Expression;
import com.flaghacker.factoriopc.language.parser.structure.ExpressionList;
import com.flaghacker.factoriopc.language.parser.structure.ForStatement;
import com.flaghacker.factoriopc.language.parser.structure.GotoStatement;
import com.flaghacker.factoriopc.language.parser.structure.IfStatement;
import com.flaghacker.factoriopc.language.parser.structure.IncrementStatement;
import com.flaghacker.factoriopc.language.parser.structure.Invert;
import com.flaghacker.factoriopc.language.parser.structure.LabelStatement;
import com.flaghacker.factoriopc.language.parser.structure.NamedArrayAccess;
import com.flaghacker.factoriopc.language.parser.structure.NamedVariable;
import com.flaghacker.factoriopc.language.parser.structure.Not;
import com.flaghacker.factoriopc.language.parser.structure.Number;
import com.flaghacker.factoriopc.language.parser.structure.Operation;
import com.flaghacker.factoriopc.language.parser.structure.OperationType;
import com.flaghacker.factoriopc.language.parser.structure.Statement;
import com.flaghacker.factoriopc.language.parser.structure.StructureVisitor;
import com.flaghacker.factoriopc.language.parser.structure.SugarAssignment;
import com.flaghacker.factoriopc.language.parser.structure.WhileStatement;

import java.util.ArrayDeque;
import java.util.Deque;

import static com.flaghacker.factoriopc.language.compiler.GotoCommand.Type.ALWAYS;
import static com.flaghacker.factoriopc.language.compiler.GotoCommand.Type.FALSE;
import static com.flaghacker.factoriopc.language.compiler.GotoCommand.Type.TRUE;
import static com.flaghacker.factoriopc.language.compiler.InstructionType.DECR;
import static com.flaghacker.factoriopc.language.compiler.InstructionType.INCR;
import static com.flaghacker.factoriopc.language.compiler.InstructionType.INV;
import static com.flaghacker.factoriopc.language.compiler.InstructionType.LOADa;
import static com.flaghacker.factoriopc.language.compiler.InstructionType.LOADb;
import static com.flaghacker.factoriopc.language.compiler.InstructionType.NOT;
import static com.flaghacker.factoriopc.language.compiler.InstructionType.PUTa;
import static com.flaghacker.factoriopc.language.compiler.InstructionType.PUTb;
import static com.flaghacker.factoriopc.language.compiler.InstructionType.SAVE;
import static com.flaghacker.factoriopc.language.compiler.Register.A;
import static com.flaghacker.factoriopc.language.compiler.Register.B;
import static com.flaghacker.factoriopc.language.compiler.Register.C;
import static com.flaghacker.factoriopc.language.compiler.Register.D;

public class Compiler implements StructureVisitor<CommandList>
{
	private Environment env;
	private Deque<Register> targetRegisters;

	public Compiler()
	{
		env = new Environment();
		targetRegisters = new ArrayDeque<>();
	}

	@Override
	public CommandList visit(Number node)
	{
		return CommandList.of(new Instruction(getTarget().put(), node.getValue()));
	}

	@Override
	public CommandList visit(Operation node)
	{
		Expression left = node.getLeft();
		Expression right = node.getRight();
		OperationType type = node.getType();

		setTarget(B);
		CommandList leftB = left.accept(this);
		setTarget(C);
		CommandList rightC = right.accept(this);

		CommandList result = new CommandList();

		if (!rightC.affects(B))
		{
			//default
			result.add (
					leftB,
					rightC,
					type.getInstructionType()
			);
		}
		else
		{
			setTarget(C);
			CommandList leftC = left.accept(this);

			if (type.hasMirror() && !leftC.affects(B))
			{
				//swap order
				setTarget(B);
				CommandList rightB = right.accept(this);

				result.add (
						rightB,
						leftC,
						type.getMirror().getInstructionType()
				);
			}
			else
			{
				setTarget(D);
				CommandList leftD = left.accept(this);

				Variable tmp = env.claimTmpVariable();

				result.add (
						leftD,
						new Instruction(PUTa, tmp.getAddress()),
						SAVE,
						rightC
				);

				if (rightC.affects(A))
					result.add(new Instruction(PUTa, tmp.getAddress()));

				result.add (
						LOADb,
						type.getInstructionType()
				);
			}
		}

		result.add(moveToTarget());

		return result;
	}

	@Override
	public CommandList visit(ArrayInitialization node)
	{
		CommandList result = new CommandList();

		if (node.isLengthInitializer())
		{
			env.declareArray(node.getName(), node.getLength());
		}
		else if (node.isListInitializer())
		{
			ExpressionList values = node.getValues();
			Array arr = env.declareArray(node.getName(), values.size());

			for (int i = 0; i < values.size(); i++)
			{
				setTarget(D);
				result.add(
						values.getExpressions().get(i).accept(this),
						new Instruction(PUTa, arr.getAddress() + i),
						SAVE
				);
			}
		}

		return result;
	}

	@Override
	public CommandList visit(Invert node)
	{
		setTarget(D);
		return CommandList.of (
				node.getExpression().accept(this),
				INV,
				moveToTarget()
		);
	}

	@Override
	public CommandList visit(NamedVariable node)
	{
		setTarget(A);
		return CommandList.of(
				visitAsAddressable(node),
				getTarget().load()
		);
	}

	@Override
	public CommandList visit(Not node)
	{
		setTarget(D);
		return CommandList.of (
				node.getExpression().accept(this),
				NOT,
				moveToTarget()
		);
	}

	@Override
	public CommandList visit(WhileStatement node)
	{
		CommandList result = new CommandList();
		Label headerLbl = env.getTmpLabel();
		Label bodyLbl = env.getTmpLabel();

		result.add(
				new GotoCommand(ALWAYS, headerLbl),
				bodyLbl,
				node.getBody().accept(this),
				headerLbl
		);
		setTarget(D);
		result.add(
				node.getCondition().accept(this),
				new GotoCommand(TRUE, bodyLbl)
		);

		return result;
	}

	@Override
	public CommandList visit(NamedArrayAccess node)
	{
		setTarget(A);
		return CommandList.of(
				visitAsAddressable(node),
				getTarget().load()
		);
	}

	@Override
	public CommandList visit(LabelStatement node)
	{
		return CommandList.of(env.getLabel(node.getName()));
	}

	@Override
	public CommandList visit(GotoStatement node)
	{
		if (node.getCondition() == null)
			return CommandList.of(new GotoCommand(ALWAYS, env.getLabel(node.getName())));
		else
			return CommandList.of(
					node.getCondition(),
					new GotoCommand(TRUE, env.getLabel(node.getName()))
			);
	}

	@Override
	public CommandList visit(Assignment node)
	{
		CommandList result = new CommandList();

		setTarget(A);
		CommandList addressA = visitAsAddressable(node.getTarget());
		setTarget(D);
		CommandList value = node.getValue().accept(this);

		if (value.affects(A))
		{
			if (addressA.affects(D))
			{
				Variable tmp = env.claimTmpVariable();

				setTarget(D);
				result.add(
						visitAsAddressable(node.getTarget()),
						new Instruction(PUTa, tmp.getAddress()),
						SAVE,
						value,
						new Instruction(PUTa, tmp.getAddress()),
						LOADa,
						SAVE
				);

				env.releaseTmpVariable(tmp);
			}
			else
			{
				result.add(
						value,
						addressA,
						SAVE
				);
			}
		}
		else
		{
			result.add(
					addressA,
					value,
					SAVE
			);
		}

		return result;
	}

	@Override
	public CommandList visit(IfStatement node)
	{
		Expression condition = node.getCondition();
		Block mainBody = node.getMainBody();
		Block elseBody = node.getElseBody();

		CommandList list = new CommandList();

		if (elseBody != null)
		{
			Label mainLbl = env.getTmpLabel();
			Label elseLbl = env.getTmpLabel();

			setTarget(D);
			list.add(
					condition.accept(this),
					new GotoCommand(TRUE, mainLbl),
					elseBody.accept(this),
					new GotoCommand(ALWAYS, elseLbl),
					mainLbl,
					mainBody.accept(this),
					elseLbl
			);
		}
		else
		{
			Label endLbl = env.getTmpLabel();

			setTarget(D);
			list.add(
					condition.accept(this),
					new GotoCommand(FALSE, endLbl),
					mainBody.accept(this),
					endLbl
			);
		}

		return list;
	}

	@Override
	public CommandList visit(ForStatement node)
	{
		CommandList result = new CommandList();

		Label headerLbl = env.getTmpLabel();
		Label endLbl = env.getTmpLabel();

		result.add(
				node.getStart().accept(this),
				headerLbl
		);
		setTarget(D);
		result.add(
				node.getCondition().accept(this),
				new GotoCommand(FALSE, endLbl),
				node.getBody().accept(this),
				node.getStep().accept(this),
				new GotoCommand(ALWAYS, headerLbl),
				endLbl
		);

		return result;
	}

	@Override
	public CommandList visit(Block node)
	{
		CommandList result = new CommandList();

		for (Statement statement : node.getStatements())
			result.add(statement.accept(this));

		return result;
	}

	@Override
	public CommandList visit(SugarAssignment node)
	{
		return new Assignment(node.getTarget(), new Operation(node.getTarget(), node.getType(), node.getValue())).accept(this);
	}

	@Override
	public CommandList visit(IncrementStatement node)
	{
		setTarget(D);
		return CommandList.of(
				node.getTarget().accept(this),
				node.isIncrement() ? INCR : DECR,
				SAVE
		);
	}

	@Override
	public CommandList visit(EmptyStatement node)
	{
		return new CommandList();
	}

	@Override
	public CommandList visit(CallExpression node)
	{
		String name = node.getCall().getName();
		ExpressionList args = node.getCall().getArguments();

		if (name.equals("input"))
		{
			if (args.size() != 1)
				throw new CompilerException("wrong number of arguments for input(): expected 1, got '" + args.size() + "'");

			setTarget(A);
			return CommandList.of(
					new Invert(args.get(0)).accept(this),
					getTarget().load()
			);
		}

		throw new CompilerException("unknown expression function '" + name + "'");
	}

	@Override
	public CommandList visit(CallStatement node)
	{
		String name = node.getCall().getName();
		ExpressionList args = node.getCall().getArguments();

		if (name.equals("output"))
		{
			if (args.size() != 2)
				throw new CompilerException("wrong number of arguments for output(): expected 2, got '" + args.size() + "'");

			return new Assignment(new OutputAddressable(args.get(0)), args.get(1)).accept(this);
		}

		throw new CompilerException("unknown statement function '" + name + "'");
	}

	private CommandList visitAsAddressable(Addressable node)
	{
		if (node instanceof NamedVariable)
		{
			Variable var = env.getVariable(((NamedVariable) node).getName());
			return CommandList.of(new Instruction(getTarget().put(), var.getAddress()));
		}
		else if (node instanceof NamedArrayAccess)
		{
			NamedArrayAccess access = (NamedArrayAccess) node;

			CommandList result = new CommandList();
			Array arr = env.getArray(access.getName());

			setTarget(C);
			result.add(
					access.getIndex().accept(this),
					new Instruction(PUTb, arr.getAddress()),
					InstructionType.ADD,
					moveToTarget()
			);

			return result;
		}
		else if (node instanceof OutputAddressable)
		{
			setTarget(D);
			return CommandList.of(
					((OutputAddressable) node).getAddress().accept(this),
					INV,
					moveToTarget()
			);
		}
		else
			throw new ClassCastException();
	}

	private Register getTarget()
	{
		return targetRegisters.pop();
	}

	private void setTarget(Register target)
	{
		targetRegisters.push(target);
	}

	private CommandList moveToTarget()
	{
		Register target = getTarget();

		if (target != D)
			return CommandList.of(new Instruction(target.move()));

		return new CommandList();
	}

	public Environment getEnv()
	{
		return env;
	}

	private class OutputAddressable implements Addressable
	{
		private Expression address;

		public OutputAddressable(Expression address)
		{
			this.address = address;
		}

		public Expression getAddress()
		{
			return address;
		}

		@Override
		public String toString()
		{
			return "IO[" + address + "]";
		}

		@Override
		public <R> R accept(StructureVisitor<R> visitor)
		{
			throw new RuntimeException("this method should never be called on this class");
		}
	}
}
