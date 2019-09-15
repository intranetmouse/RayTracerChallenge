package org.intranet.graphics.raytrace.shape;

import java.util.List;

import org.intranet.graphics.raytrace.Intersection;
import org.intranet.graphics.raytrace.Ray;
import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Tuple;
import org.intranet.graphics.raytrace.primitive.Vector;

public final class Cone
	extends TubeLike
{
	public Cone()
	{
	}

	@Override
	protected void intersectSides(Ray ray, List<Intersection> xs)
	{
		Vector rayDirection = ray.getDirection();
		double directionX = rayDirection.getX();
		double directionY = rayDirection.getY();
		double directionZ = rayDirection.getZ();

		double a = directionX * directionX -
			directionY * directionY +
			directionZ * directionZ;

		Point rayOrigin = ray.getOrigin();
		double originX = rayOrigin.getX();
		double originY = rayOrigin.getY();
		double originZ = rayOrigin.getZ();

		double b = 2 * originX * directionX -
			2 * originY * directionY +
			2 * originZ * directionZ;
		double c = originX * originX -
			originY * originY +
			originZ * originZ;

		boolean aIsZero = Tuple.isZero(a);

		if (aIsZero && !Tuple.isZero(b))
		{
			double t = -c / (2 * b);
			xs.add(new Intersection(t, this));
		}
		else if (!aIsZero)
		{
			calcSideIntersections(xs, a, b, c, originY, directionY);
		}
	}

	@Override
	protected double calcNormalY(double pointY, double dist)
	{
		double y = Math.sqrt(dist);
		if (pointY > 0)
			y = -y;
		return y;
	}

	@Override
	protected boolean shapeEquals(Object other)
	{
		if (other == null || !(other instanceof Cone))
			return false;
		return super.equals(other);
	}


	@Override
	public Shape deepCopy()
	{
		Cone shape = new Cone();
		shape.deepCopyFrom(this);
		return shape;
	}

	@Override
	protected double getMinimumRadius() { return minimum; }

	@Override
	protected double getMaximumRadius() { return maximum; }
}
