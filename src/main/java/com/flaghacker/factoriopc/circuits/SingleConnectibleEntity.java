package com.flaghacker.factoriopc.circuits;

public abstract class SingleConnectibleEntity extends ConnectibleEntity implements SingleConnectible
{
	public SingleConnectibleEntity(int x, int y)
	{
		super(x, y, 1);
	}

	@Override
	public ConnectionPoint getConnection()
	{
		return getConnection(1);
	}
}
