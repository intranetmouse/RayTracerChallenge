package org.intranet.graphics.raytrace.puttingItTogether.projector;

import java.util.List;

public final class ProjectorGroup
{
	private String name;
	public String getName() { return name; }

	private Projector[] projectors;
	public Projector[] getProjectors() { return projectors; }

	public ProjectorGroup(String name, List<Projector> projectors)
	{
		this(name, projectors.toArray(new Projector[projectors.size()]));
	}
	public ProjectorGroup(String name, Projector... projectors)
	{
		this.name = name;
		this.projectors = projectors;
	}

	@Override
	public String toString() { return name; }
}