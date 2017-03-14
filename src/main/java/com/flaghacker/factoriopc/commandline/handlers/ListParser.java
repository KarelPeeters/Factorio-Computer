package com.flaghacker.factoriopc.commandline.handlers;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class ListParser<E>
{
	private Scanner scanner;

	public ListParser(String source)
	{
		this.scanner = new Scanner(source);
	}

	public List<E> parse()
	{
		List<E> list = new ArrayList<>();

		int lineNumber = 1;
		while (scanner.hasNextLine())
		{
			String line = scanner.nextLine();

			if (! StringUtils.isEmpty(line))
			{
				E e = parseLine(line, lineNumber);
				list.add(e);
			}

			lineNumber++;
		}

		return list;
	}

	public abstract E parseLine(String line, int lineNumber);
}
