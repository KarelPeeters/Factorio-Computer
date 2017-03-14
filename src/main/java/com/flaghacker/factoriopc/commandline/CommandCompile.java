package com.flaghacker.factoriopc.commandline;

import com.beust.jcommander.IValueValidator;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;
import com.flaghacker.factoriopc.commandline.handlers.LevelHandler;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

@Parameters(commandDescription = "Compile code to a lower level.")
public class CommandCompile implements Consumer<JCommander>
{
	@Parameter(names = "-src", required = true, validateValueWith = FileExistsValidator.class, description = "The source file path.")
	private File srcFile;

	@Parameter(names = "-srcLevel", converter = CodeLevelConverter.class, description = "The source level.")
	private CodeLevel srcLevel = CodeLevel.SOURCE;

	@Parameter(names = "-tar", validateValueWith = FileValidator.class, description = "The target file path. When blank the output is printed to the console.")
	private File tarFile;

	@Parameter(names = "-tarLevel", converter = CodeLevelConverter.class, description = "The target level, must be lower than the source level.")
	private CodeLevel tarLevel = CodeLevel.BLUEPRINT;

	@Override
	public void accept(JCommander jCommander)
	{
		try
		{
			if (!srcLevel.canCompileTo(tarLevel))
				throw new ParameterException(String.format("%s can't be compiled to %s", srcLevel, tarLevel));

			Converter converter = new Converter();

			//source
			LevelHandler srcHandler = srcLevel.getHandler();
			srcHandler.parseAndSet(FileUtils.readFileToString(srcFile), converter);

			//target
			LevelHandler tarHandler = tarLevel.getHandler();
			String result = tarHandler.getAndSerialize(converter);

			if (tarFile != null)
				FileUtils.write(tarFile, result);
			else
				System.out.println(result);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	public static class FileExistsValidator extends FileValidator
	{
		@Override
		public void validate(String name, File value) throws ParameterException
		{
			if (!value.exists())
				throw new ParameterException(name + " must exist");
			super.validate(name, value);
		}
	}

	public static class FileValidator implements IValueValidator<File>
	{
		@Override
		public void validate(String name, File value) throws ParameterException
		{
			if (value.isDirectory())
				throw new ParameterException(name + " can't be a directory");
		}
	}
}
