package org.intranet.graphics.raytrace.shape;

import org.intranet.graphics.raytrace.Light;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Vector;
import org.intranet.graphics.raytrace.surface.Color;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class AreaLight
	implements Light
{
	private Point corner;
	public Point getCorner() { return corner; }

	private Vector uvec;
	public Vector getUvec() { return uvec; }

	private int usteps;
	public int getUsteps() { return usteps; }

	private Vector vvec;
	public Vector getVvec() { return vvec; }

	private int vsteps;
	public int getVsteps() { return vsteps; }

	private int numSamples;
	public int getNumSamples() { return numSamples; }

	private Point position;
	@Override
	public Point getPosition() { return position; }

	public AreaLight(Point cornerPoint, Vector unscaledUvector,
		int unscaledUvectorSteps, Vector unscaledVvector,
		int unscaledVvectorSteps, Color color)
	{
		this.corner = cornerPoint;

		uvec = unscaledUvector.divide(unscaledUvectorSteps);
		this.usteps = unscaledUvectorSteps;

		vvec = unscaledVvector.divide(unscaledVvectorSteps);
		this.vsteps = unscaledVvectorSteps;

		numSamples = usteps * vsteps;

		Vector averageVector = unscaledUvector.add(unscaledVvector).divide(2);
		position = new Point(averageVector.getX(), averageVector.getY(), averageVector.getZ());
	}

	@Override
	public Color getIntensity()
	{
		throw new NotImplementedException();
	}

	public Point pointOnLight(int u, int v)
	{
		return corner.add(uvec.multiply(u + 0.5)).add(vvec.multiply(v + 0.5));
	}
}
