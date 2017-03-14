package com.flaghacker.factoriopc.circuits;

import java.util.ArrayList;
import java.util.List;

public class EntityGroup extends Node
{
	private List<Node> nodes = new ArrayList<>();

	public EntityGroup(int x, int y)
	{
		super(x, y);
	}

	public void add(Node node)
	{
		if (node.getParent() != null)
			throw new IllegalStateException("Node already has a parent");

		nodes.add(node);
		node.setParent(this);
	}

	public void addAll(Node ... nodes)
	{
		for (Node node : nodes)
			add(node);
	}

	@Override
	public List<Entity> getEntities()
	{
		List<Entity> entities = new ArrayList<>();

		for (Node node:nodes)
			entities.addAll(node.getEntities());

		return entities;
	}
}
