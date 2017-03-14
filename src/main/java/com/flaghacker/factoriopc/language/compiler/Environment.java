package com.flaghacker.factoriopc.language.compiler;

import java.util.ArrayList;
import java.util.List;

public class Environment
{
	private int nextAddress = 0;

	private List<Variable> variables = new ArrayList<>();
	private List<Array> arrays = new ArrayList<>();
	private List<Label> labels = new ArrayList<>();

	private List<Variable> freeTmpVariables = new ArrayList<>();
	private List<Variable> claimedTmpVariables = new ArrayList<>();

	public Variable getVariable(String name)
	{
		for (Variable var : variables)
			if (var.getName().equals(name))
				return var;

		Variable var = new Variable(nextAddress ++, name);
		variables.add(var);
		return var;
	}

	public Array declareArray(String name, int length)
	{
		for (Array array : arrays)
			if (array.getName().equals(name))
				throw new CompilerException(String.format("the array '%s' was already declared declared", name));

		Array arr = new Array(nextAddress, name, length);
		nextAddress += length;
		arrays.add(arr);
		return arr;
	}

	public Array getArray(String name)
	{
		for (Array arr : arrays)
			if (arr.getName().equals(name))
				return arr;

		throw new CompilerException(String.format("the array '%s' was not yet declared", name));
	}

	public Label getLabel(String name)
	{
		for (Label lbl : labels)
			if (lbl.getName().equals(name))
				return lbl;

		Label lbl = new Label(name);
		labels.add(lbl);
		return lbl;
	}

	public Variable claimTmpVariable()
	{
		Variable var;

		if (freeTmpVariables.size() > 0)
			var = freeTmpVariables.remove(0);
		else
			var = new Variable(nextAddress++, null);

		claimedTmpVariables.add(var);
		return var;
	}

	public void releaseTmpVariable(Variable var)
	{
		if (!claimedTmpVariables.contains(var))
			throw new RuntimeException(String.format("the variable %s was not claimed in this environment", var));

		claimedTmpVariables.remove(var);
		freeTmpVariables.add(var);
	}

	public Label getTmpLabel()
	{
		return new Label(null);
	}

	public int getRequiredRAM()
	{
		return nextAddress;
	}
}
