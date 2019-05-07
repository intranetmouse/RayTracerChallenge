package org.intranet.graphics.raytrace.puttingItTogether;

import org.intranet.graphics.raytrace.primitive.Vector;

public class Environment
{
	private Vector gravity;
	public Vector getGravity() { return gravity; }

	private Vector wind;
	public Vector getWind() { return wind; }

	public Environment(Vector gravity, Vector wind)
	{
		this.gravity = gravity;
		this.wind = wind;
	}
}
