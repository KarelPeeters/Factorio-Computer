package com.flaghacker.factoriopc.circuits;

import java.util.List;

public abstract class Node
{
	private EntityGroup parent;
	private Position relativePosition;

	public Node(int x, int y)
	{
		this(new Position(x, y));
	}

	private Node(Position relativePosition)
	{
		this.relativePosition = relativePosition;
	}

	public EntityGroup getParent()
	{
		return parent;
	}

	public void setParent(EntityGroup parent)
	{
		this.parent = parent;
	}

	public Position getAbsolutePosition()
	{
		if (parent == null)
			return relativePosition;

		return parent.getAbsolutePosition().offSet(relativePosition);
	}

	public abstract List<Entity> getEntities();
}
