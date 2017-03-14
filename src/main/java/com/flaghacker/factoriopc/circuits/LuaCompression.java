package com.flaghacker.factoriopc.circuits;

import org.json.JSONObject;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JseBaseLib;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.io.InputStream;

public class LuaCompression
{
	private static final LuaValue compressProgram;
	private static final LuaValue decompressProgram;

	static
	{
		Globals globals = JsePlatform.standardGlobals();
		globals.load(new PrefixLoader("/lua/"));

		compressProgram = globals.loadfile("compress.lua");
		decompressProgram = globals.loadfile("decompress.lua");
	}

	public static String compressJSON(JSONObject json)
	{
		return compressProgram.call(json.toString()).tojstring();
	}

	public static JSONObject decompressString(String string)
	{
		return new JSONObject(decompressProgram.call(string).tojstring());
	}

	private static class PrefixLoader extends JseBaseLib
	{
		private String prefix;

		public PrefixLoader(String prefix)
		{
			this.prefix = prefix;
		}

		@Override
		public InputStream findResource(String filename)
		{
			return super.findResource(prefix + filename);
		}
	}
}
