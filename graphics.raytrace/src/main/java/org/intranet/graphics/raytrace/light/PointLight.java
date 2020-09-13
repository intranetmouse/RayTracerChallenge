package org.intranet.graphics.raytrace.light;

import java.util.Arrays;
import java.util.List;

import org.intranet.graphics.raytrace.Light;
import org.intranet.graphics.raytrace.Lighting;
import org.intranet.graphics.raytrace.World;
import org.intranet.graphics.raytrace.primitive.Color;
import org.intranet.graphics.raytrace.primitive.Point;

public class PointLight
	implements Light
{
	private List<Point> positions;
	@Override
	public List<Point> getSamples() { return positions; }

	private Color intensity;
	public Color getIntensity() { return intensity; }

	public PointLight(Point position, Color intensity)
	{
		this.positions = Arrays.asList(position);
		this.intensity = intensity;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null || !(obj instanceof PointLight))
			return false;
		PointLight other = (PointLight) obj;
		if (!positions.equals(other.positions))
			return false;
		if (!intensity.equals(other.intensity))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return String.format("[light:positions=%s,intensity=%s]", positions,
			intensity);
	}

	@Override
	public double intensityAt(Point pt, World world)
	{
		return Lighting.isShadowed(world, positions, pt);
	}
}
