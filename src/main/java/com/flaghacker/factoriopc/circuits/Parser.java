package com.flaghacker.factoriopc.circuits;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser
{
	private static final Pattern intPattern;
	private static final Pattern signalPattern;
	private static final Pattern filterPattern;
	private static final Pattern conditionPattern;
	private static final Pattern lampPattern;

	static
	{
		//-1097183583 @-620, 42, 516
		String comparisonRegex	= "(>|<|=)";
		String operationRegex	= "(" + comparisonRegex + "|\\+|\\-|\\*|/)";
		String intRegex			= "(-?\\d+)";
		String signalRegex 		= "(([A-Z]+)|(\\[\\d\\]))";
		String signalOrIntRegex = "(" + signalRegex + "|" + intRegex + ")";

		String namedSignalRegex = "(?<name>[A-Z]+)|(\\[(?<number>\\d)\\])";

		String filterRegex		= String.format("(?<signal>%s)=(?<value>%s)", signalRegex, intRegex);
		String conditionRegex 	= String.format("(?<left>%s)(?<operation>%s)(?<right>%s)->(?<output>%s)(?<setOne>=1)?", signalRegex, operationRegex, signalOrIntRegex, signalRegex);
		String lampRegex		= String.format("(?<left>%s)(?<comparison>%s)(?<right>%s)", signalRegex, comparisonRegex, signalOrIntRegex);

		intPattern = Pattern.compile(intRegex);
		signalPattern = Pattern.compile(namedSignalRegex);
		filterPattern = Pattern.compile(filterRegex);
		conditionPattern = Pattern.compile(conditionRegex);
		lampPattern = Pattern.compile(lampRegex);
	}

	public static Lamp.Condition parseLampCondition(String string)
	{
		string = sanetize(string);

		Matcher matcher = lampPattern.matcher(string);
		if (!matcher.matches())
			throw new IllegalArgumentException(String.format("invalid condition string '%s'", string));

		Signal left = parseSignal(matcher.group("left"));
		char comparison = matcher.group("comparison").charAt(0);
		Pair<Signal, Integer> right = parseSignalOrInt(matcher.group("right"));

		if (right.getKey() != null)
			return new Lamp.Condition(left, comparison, right.getKey());

		return new Lamp.Condition(left, comparison, right.getValue());
	}

	public static List<Filter> parseFilterList(String string)
	{
		string = sanetize(string);

		List<Filter> list = new ArrayList<>();

		for (String filter : string.split(","))
			list.add(parseFilter(filter));

		return list;
	}

	public static Condition parseCondition(String string)
	{
		string = sanetize(string);

		Matcher matcher = conditionPattern.matcher(string);
		if (!matcher.matches())
			throw new IllegalArgumentException(String.format("invalid condition string '%s'", string));

		Signal left = parseSignal(matcher.group("left"));
		char operation = matcher.group("operation").charAt(0);
		Pair<Signal, Integer> right = parseSignalOrInt(matcher.group("right"));
		Signal output = parseSignal(matcher.group("output"));
		boolean setOne = matcher.group("setOne") != null;

		return buildCondition(left, operation, right.getKey(), right.getValue(), output, !setOne);
	}

	private static Filter parseFilter(String string)
	{
		Matcher matcher = filterPattern.matcher(string);
		if (!matcher.matches())
			throw new IllegalArgumentException(String.format("invalid filter string '%s'", string));

		Signal signal = parseSignal(matcher.group("signal"));
		int value = Integer.parseInt(matcher.group("value"));

		return new Filter(signal, value);
	}

	private static Pair<Signal, Integer> parseSignalOrInt(String string)
	{
		if (intPattern.matcher(string).matches())
			return Pair.of(null, Integer.parseInt(string));
		if (signalPattern.matcher(string).matches())
			return Pair.of(parseSignal(string), 0);

		throw new IllegalArgumentException(String.format("invalid signal or int string '%s'", string));
	}

	private static Signal parseSignal(String string)
	{
		Matcher matcher = signalPattern.matcher(string);
		if (!matcher.matches())
			throw new IllegalArgumentException(String.format("invalid signal string '%s'", string));

		String signalName = matcher.group("name");
		if (signalName == null)
			signalName = matcher.group("number");

		return Signal.fromString(signalName);
	}

	private static Condition buildCondition(Signal firstSignal, char operation, Signal secondSignal, int constant, Signal outputSignal, boolean copyInputToOutput)
	{
		if (ArithmeticCondition.operations.indexOf(operation) != - 1)
		{
			if (!copyInputToOutput)
				throw new IllegalArgumentException("an arithmetic condition can't have an output =1");

			if (secondSignal == null)
				return new ArithmeticCondition(firstSignal, operation, constant, outputSignal);
			else
				return new ArithmeticCondition(firstSignal, operation, secondSignal, outputSignal);
		}
		else if (DeciderCondition.comparators.indexOf(operation) != - 1)
		{
			if (secondSignal == null)
				return new DeciderCondition(firstSignal, operation, constant, outputSignal, copyInputToOutput);
			else
				return new DeciderCondition(firstSignal, operation, secondSignal, outputSignal, copyInputToOutput);
		}
		else
			throw new IllegalArgumentException("invalid operation " + operation);
	}

	private static String sanetize(String string)
	{
		return string.replace(" ", "");
	}
}
