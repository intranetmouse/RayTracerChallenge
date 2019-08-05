package org.intranet.graphics.raytrace;

import java.util.ArrayList;
import java.util.List;

import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Tuple;
import org.intranet.graphics.raytrace.primitive.Vector;
import org.intranet.graphics.raytrace.surface.Color;
import org.intranet.graphics.raytrace.surface.Material;

public final class IntersectionComputations
{
	public double getDistance() { return intersection.getDistance(); }

	public Shape getObject() { return intersection.getObject(); }

	private Point point;
	public Point getPoint() { return point; }

	private Vector eyeVector;
	public Vector getEyeVector() { return eyeVector; }

	private Vector normalVector;
	public Vector getNormalVector() { return normalVector; }

	private Vector reflectVector;
	public Vector getReflectVector() { return reflectVector; }

	private Intersection intersection;

	private boolean inside;
	public boolean isInside() { return inside; }

	private Point overPoint;
	public Point getOverPoint() { return overPoint; }

	private Point underPoint;
	public Point getUnderPoint() { return underPoint; }

	private double n1;
	public double getN1() { return n1; }
	private double n2;
	public double getN2() { return n2; }

	public Color shadeHit(World world, int remaining)
	{
		List<Light> lightSources = world.getLightSources();
		Color surfaceColor = lightSources.stream()
			.map(lightSource -> Tracer.lighting(getObject().getMaterial(),
				getObject(), lightSource, overPoint, eyeVector, normalVector,
				Tracer.isShadowed(world, overPoint)))
			.reduce((a, b) -> a.add(b)).orElse(new Color(0, 0, 0));
//String indent = "       ".substring(remaining);
//System.out.printf("%ssurface=%s\n", indent, surfaceColor);

		Color reflectedColor = Tracer.reflectedColor(world, this, remaining);
//System.out.printf("%sreflected=%s\n", indent, reflectedColor);

		Color refractedColor = Tracer.refractedColor(world, this, remaining);
//System.out.printf("%srefracted=%s\n", indent, refractedColor);

		Material mat = getObject().getMaterial();
		if (mat.getReflective() > 0 && mat.getTransparency() > 0)
		{
			double reflectance = schlick();
			reflectedColor = reflectedColor.multiply(reflectance);
			refractedColor = refractedColor.multiply(1 - reflectance);
		}

		Color result = surfaceColor
			.add(reflectedColor)
			.add(refractedColor);

//System.out.printf("%sresult=%s\n", indent, result);
//System.out.println("surface="+surfaceColor+", reflected="+reflectedColor+", refracted="+refractedColor+",result="+result+",remaining="+remaining);
		return result;
	}

	public IntersectionComputations(Intersection hit,
		Ray ray, List<Intersection> allIntersections)
	{
		this.intersection = hit;
		// copy the intersection's properties, for convenience
		// precompute some useful values
		this.point = ray.position(getDistance());
		this.eyeVector = ray.getDirection().normalize().negate();
		this.normalVector = getObject().normalAt(point);

		if (normalVector.dot(eyeVector) < 0)
		{
			inside = true;
			normalVector = normalVector.negate();
		}

		Vector normalFraction = normalVector.multiply(Tuple.EPSILON);
		this.overPoint = point.add(normalFraction);
		this.underPoint = point.subtract(normalFraction);

		reflectVector = ray.getDirection().reflect(normalVector);

		List<Shape> containers = new ArrayList<>();
		for (Intersection i : allIntersections)
		{
			if (i == hit)
			{
				if (containers.isEmpty())
					n1 = 1.0;
				else
					n1 = containers.get(containers.size() - 1).getMaterial().getRefractive();
			}
			if (containers.contains(i.getObject()))
			{
				containers.remove(i.getObject());
			}
			else
			{
				containers.add(i.getObject());
			}
			if (i == hit)
			{
				if (containers.isEmpty())
					n2 = 1.0;
				else
					n2 = containers.get(containers.size() - 1).getMaterial().getRefractive();
				break;
			}
		}

	}

	public double schlick()
	{
		double cos = eyeVector.dot(normalVector);

		// total internal reflection can only occur if n1 > n2
		if (n1 > n2)
		{
			double n = n1 / n2;
			double sin2_t = n*n * (1.0 - cos*cos);
			if (sin2_t > 1.0)
				return 1.0;
			cos = Math.sqrt(1.0 - sin2_t);
		}

		double r0 = Math.pow((n1 - n2) / (n1 + n2), 2);

		return r0 + (1 - r0) * Math.pow(1 - cos, 5);
	}
}