package com.flaghacker.factoriopc.language.parser;

import com.flaghacker.factoriopc.language.parser.structure.Addressable;
import com.flaghacker.factoriopc.language.parser.structure.ArrayInitialization;
import com.flaghacker.factoriopc.language.parser.structure.Assignment;
import com.flaghacker.factoriopc.language.parser.structure.Block;
import com.flaghacker.factoriopc.language.parser.structure.Call;
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
import com.flaghacker.factoriopc.language.parser.structure.SugarAssignment;
import com.flaghacker.factoriopc.language.parser.structure.WhileStatement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.flaghacker.factoriopc.language.parser.TokenType.ADD_SUB;
import static com.flaghacker.factoriopc.language.parser.TokenType.AND_OR;
import static com.flaghacker.factoriopc.language.parser.TokenType.ASSIGN;
import static com.flaghacker.factoriopc.language.parser.TokenType.BOOLEAN;
import static com.flaghacker.factoriopc.language.parser.TokenType.CLOSEB;
import static com.flaghacker.factoriopc.language.parser.TokenType.CLOSEC;
import static com.flaghacker.factoriopc.language.parser.TokenType.CLOSES;
import static com.flaghacker.factoriopc.language.parser.TokenType.COMMA;
import static com.flaghacker.factoriopc.language.parser.TokenType.COMMENT;
import static com.flaghacker.factoriopc.language.parser.TokenType.COMP;
import static com.flaghacker.factoriopc.language.parser.TokenType.EOF;
import static com.flaghacker.factoriopc.language.parser.TokenType.IDENTIFIER;
import static com.flaghacker.factoriopc.language.parser.TokenType.INCR_DECR;
import static com.flaghacker.factoriopc.language.parser.TokenType.KEYWORD;
import static com.flaghacker.factoriopc.language.parser.TokenType.MOD;
import static com.flaghacker.factoriopc.language.parser.TokenType.MUL_DIV;
import static com.flaghacker.factoriopc.language.parser.TokenType.NOT;
import static com.flaghacker.factoriopc.language.parser.TokenType.NUMBER;
import static com.flaghacker.factoriopc.language.parser.TokenType.OPENB;
import static com.flaghacker.factoriopc.language.parser.TokenType.OPENC;
import static com.flaghacker.factoriopc.language.parser.TokenType.OPENS;
import static com.flaghacker.factoriopc.language.parser.TokenType.SEMICOLON;
import static com.flaghacker.factoriopc.language.parser.TokenType.S_ASSIGN;
import static com.flaghacker.factoriopc.language.parser.TokenType.WHITESPACE;

public class Parser
{
	//boilerplate

	private Tokenizer tokenizer;

	private Token token;
	private Token lookahead;
	private List<TokenType> ignoredTokenTypes = Arrays.asList(WHITESPACE, COMMENT);

	public Parser(Tokenizer tokenizer)
	{
		this.tokenizer = tokenizer;

		next();
		next();
	}

	public Block parse()
	{
		return block(EOF);
	}

	//parsing

	private Block block(TokenType stop)
	{
		List<Statement> statements = new ArrayList<>();
		while (!accept(stop))
			statements.add(statement());
		return new Block(statements);
	}

	private Block containedBlock()
	{
		if (accept(OPENC))
			return block(CLOSEC);

		return new Block(Collections.singletonList(statement()));
	}

	private Statement statement()
	{
		if (tokenIs(KEYWORD))
			return keywordStatement();

		Statement statement = identifierStatement();
		expect(SEMICOLON);

		return statement;
	}

	private Statement identifierStatement()
	{
		if (lookahead.type == OPENB)
			return call().asStatement();
		else if (lookahead.type == ASSIGN)
			return assignment();
		else if (lookahead.type == INCR_DECR)
			return incrementStatement();
		else if (lookahead.type == S_ASSIGN)
			return sugarAssignment();
		else
			return new EmptyStatement();
	}

	private Statement sugarAssignment()
	{
		Addressable target = addressable();
		String operator = expect(S_ASSIGN);
		OperationType type = OperationType.fromSymbol(operator.substring(0, operator.length() - 1));
		Expression value = expression();
		
		return new SugarAssignment(target, type, value);
	}

	private Statement incrementStatement()
	{
		Addressable target = addressable();
		String operator = expect(INCR_DECR);
		boolean increment = operator.equals("++");
		
		return new IncrementStatement(target, increment);
	}

	private Statement assignment()
	{
		Addressable target = addressable();
		expect(ASSIGN);

		//a = ...
		if (target instanceof NamedVariable)
		{
			//a = [3]
			if (accept(OPENS))
			{
				int length = Integer.parseInt(expect(NUMBER));
				expect(CLOSES);
				return new ArrayInitialization(((NamedVariable) target).getName(), length);
			}
			//a = {1, 2, 3}
			else if (accept(OPENC))
			{
				ExpressionList values = expressionList(CLOSEC);
				return new ArrayInitialization(((NamedVariable) target).getName(), values);
			}
			//a = 3
			else
			{
				Expression value = expression();
				return new Assignment(target, value);
			}
		}
		//a[5] = 3;
		else if (target instanceof NamedArrayAccess)
		{
			Expression value = expression();
			return new Assignment(target, value);
		}

		throw new RuntimeException(String.format("unknown Addressable class '%s'", target.getClass()));
	}

	private Addressable addressable()
	{
		String name = expect(IDENTIFIER);

		if (accept(OPENS))
		{
			Expression index = expression();
			expect(CLOSES);
			return new NamedArrayAccess(name, index);
		}

		return new NamedVariable(name);
	}

	private Call call()
	{
		String name = expect(IDENTIFIER);
		expect(OPENB);
		ExpressionList args = expressionList(CLOSEB);

		return new Call(name, args);
	}

	private ExpressionList expressionList(TokenType stop)
	{
		List<Expression> list = new ArrayList<>();

		while (!accept(stop))
		{
			if (list.size() != 0)
				expect(COMMA);

			list.add(expression());
		}

		return new ExpressionList(list);
	}

	private Statement keywordStatement()
	{
		if (token.contentEquals("if"))
			return ifStatement();
		if (token.contentEquals("while"))
			return whileStatement();
		if (token.contentEquals("for"))
			return forStatement();
		if (token.contentEquals("goto"))
			return gotoStatement();
		if (token.contentEquals("::"))
			return labelStatement();

		error();
		return null;
	}

	private Statement labelStatement()
	{
		expect("::");
		return new LabelStatement(expect(IDENTIFIER));
	}

	private Statement gotoStatement()
	{
		expect("goto");
		String label = expect(IDENTIFIER);

		Expression condition = null;
		if (accept(OPENB))
		{
			condition = expression();
			expect(CLOSEB);
		}

		return new GotoStatement(label, condition);
	}

	private Statement forStatement()
	{
		expect("for");
		expect(OPENB);

		Statement start = identifierStatement();
		expect(SEMICOLON);
		Expression condition = expression();
		expect(SEMICOLON);
		Statement step = identifierStatement();
		expect(CLOSEB);

		Block block = containedBlock();

		return new ForStatement(start, condition, step, block);
	}

	private Statement whileStatement()
	{
		expect("while");
		expect(OPENB);
		Expression condition = expression();
		expect(CLOSEB);
		Block block = containedBlock();
		return new WhileStatement(condition, block);
	}

	private Statement ifStatement()
	{
		expect("if");
		expect(OPENB);
		Expression condition = expression();
		expect(CLOSEB);

		Block ifBlock = containedBlock();
		Block elseBlock = null;

		if (accept("else"))
			elseBlock = containedBlock();

		return new IfStatement(condition, ifBlock, elseBlock);
	}

	private Expression expression()
	{
		Expression left = comparison();

		if(tokenIs(AND_OR))
		{
			OperationType type = OperationType.fromSymbol(expect(AND_OR));
			Expression right = comparison();

			return new Operation(left, type, right);
		}

		return left;
	}

	private Expression comparison()
	{
		Expression left = summation();

		if (tokenIs(COMP))
		{
			OperationType type = OperationType.fromSymbol(expect(COMP));
			Expression right = summation();

			return new Operation(left, type, right);
		}

		return left;
	}

	private Expression summation()
	{
		Expression expr = accept("-") ? new Invert(term()) : term();

		while (tokenIs(ADD_SUB))
			expr = new Operation(expr, OperationType.fromSymbol(expect(ADD_SUB)), term());

		return expr;
	}

	private Expression term()
	{
		Expression expr = factor();

		while (tokenIs(MUL_DIV))
			expr = new Operation(expr, OperationType.fromSymbol(expect(MUL_DIV)), factor());

		return expr;
	}

	private Expression factor()
	{
		Expression expr = value();

		while (tokenIs(MOD))
			expr = new Operation(expr, OperationType.fromSymbol(expect(MOD)), value());

		return expr;
	}

	private Expression value()
	{
		boolean not = accept(NOT);
		Expression expr = null;

		//(...)
		if (accept(OPENB))
		{
			expr = expression();
			expect(CLOSEB);
		}
		//5
		else if (tokenIs(NUMBER))
		{
			expr = new Number(Integer.parseInt(expect(NUMBER)));
		}
		else if (tokenIs(BOOLEAN))
		{
			expr = new Number(Boolean.parseBoolean(expect(BOOLEAN)) ? 1 : 0);
		}
		//a...
		else if (tokenIs(IDENTIFIER))
		{
			//a(...)
			if (lookahead.type == OPENB)
				expr = call().asExpression();
			//a
			else
				expr = addressable();
		}
		else
			error();

		return not ? new Not(expr) : expr;
	}

	//util

	private boolean tokenIs(TokenType type)
	{
		return token.type == type;
	}

	private String expect(TokenType type)
	{
		Token expected = token;
		if (!accept(type))
			error();
		return expected.content;
	}

	private void expect(String string)
	{
		if (!accept(string))
			error();
	}

	private boolean accept(String string)
	{
		if (token.contentEquals(string))
		{
			next();
			return true;
		}

		return false;
	}

	private boolean accept(TokenType type)
	{
		if (tokenIs(type))
		{
			next();
			return true;
		}

		return false;
	}

	private void error() throws ParserException
	{
		throw new ParserException("Unexpected token '" + String.valueOf(token) + "'");
	}

	private void next()
	{
		Token next = tokenizer.next();

		while (ignoredTokenTypes.contains(next.type))
			next = tokenizer.next();

		token = lookahead;
		lookahead = next;
	}
}
