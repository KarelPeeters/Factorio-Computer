package com.flaghacker.factoriopc.circuits;

public interface SingleConnectible
{
	ConnectionPoint getConnection();

	default void connect(ConnectionPoint other)
	{
		this.getConnection().connect(other);
	}

	default void connect(ConnectionPoint other, Color color)
	{
		this.getConnection().connect(other, color);
	}

	default void connect(SingleConnectible other)
	{
		this.getConnection().connect(other.getConnection());
	}

	default void connect(SingleConnectible other, Color color)
	{
		this.getConnection().connect(other.getConnection(), color);
	}
}
