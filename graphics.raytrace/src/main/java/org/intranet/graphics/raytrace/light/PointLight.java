package org.intranet.graphics.raytrace.light;

import org.intranet.graphics.raytrace.Light;
import org.intranet.graphics.raytrace.Tracer;
import org.intranet.graphics.raytrace.World;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.surface.Color;

public class PointLight
	implements Light
{
	private Point position;
	public Point getPosition() { return position; }

	private Color intensity;
	public Color getIntensity() { return intensity; }

	public PointLight(Point position, Color intensity)
	{
		this.position = position;
		this.intensity = intensity;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null || !(obj instanceof PointLight))
			return false;
		PointLight other = (PointLight) obj;
		if (!position.equals(other.position))
			return false;
		if (!intensity.equals(other.intensity))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return String.format("[light:position=%s,intensity=%s]", position,
			intensity);
	}

	@Override
	public double intensityAt(Point pt, World world)
	{
		return Tracer.isShadowed(world, getPosition(), pt) ? 0.0 : 1.0;
	}
}
