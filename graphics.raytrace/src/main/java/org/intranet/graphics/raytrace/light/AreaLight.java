package org.intranet.graphics.raytrace.light;

import java.util.ArrayList;
import java.util.List;

import org.intranet.graphics.raytrace.Light;
import org.intranet.graphics.raytrace.Tracer;
import org.intranet.graphics.raytrace.World;
import org.intranet.graphics.raytrace.primitive.Color;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Vector;
import org.intranet.graphics.raytrace.shape.FixedSequence;
import org.intranet.graphics.raytrace.shape.Sequence;

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

	private Color intensity;
	@Override
	public Color getIntensity() { return intensity; }

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

		this.intensity = color;

		resetPositions();
	}

	private void resetPositions()
	{
		positions = calculatePositions();
	}

	private List<Point> calculatePositions()
	{
		List<Point> positions = new ArrayList<>();
		positions.clear();
		for (int v = 0; v < vsteps; v++)
		{
			for (int u = 0; u < usteps; u++)
			{
				Point light_position = pointOnLight(u, v);
				positions.add(light_position);
			}
		}
		return positions;
	}

	private List<Point> positions = new ArrayList<>();
	@Override
	public List<Point> getSamples()
	{
		return calculatePositions();
	}

	public Point pointOnLight(int u, int v)
	{
		return corner.add(uvec.multiply(u + sequence.next())).add(vvec.multiply(v + sequence.next()));
	}

	@Override
	public double intensityAt(Point pt, World world)
	{
		resetPositions();
		return Tracer.isShadowed(world, positions, pt);
	}

	private Sequence sequence = new FixedSequence(0.5);
	public void setJitterBy(Sequence s)
	{
		this.sequence = s;
		resetPositions();
	}
}
