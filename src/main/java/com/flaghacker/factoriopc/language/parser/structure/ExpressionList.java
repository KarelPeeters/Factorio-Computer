package com.flaghacker.factoriopc.language.parser.structure;

import java.util.List;

public class ExpressionList
{
	private List<Expression> expressions;

	public ExpressionList(List<Expression> expressions)
	{
		this.expressions = expressions;
	}

	@Override
	public String toString()
	{
		if (expressions.size() == 0)
			return "";

		String str = "";

		for (int i = 0; i < expressions.size(); i++)
		{
			if (i != 0)
				str += ",";

			str += expressions.get(i);
		}

		return str;
	}

	public int size()
	{
		return expressions.size();
	}

	public List<Expression> getExpressions()
	{
		return expressions;
	}

	public Expression get(int index)
	{
		return expressions.get(index);
	}
}
