package org.intranet.graphics.raytrace;

public class Ray
{
	private Point origin;
	public Point getOrigin() { return origin; }

	private Vector direction;
	public Vector getDirection() { return direction; }

	public Ray(Point origin, Vector direction)
	{
		this.origin = origin;
		this.direction = direction;
	}

	public Point position(double t)
	{
		return origin.add(direction.multiply(t));
	}

	public Ray transform(Matrix t)
	{
		Point newOrigin = t.multiply(origin);
		Vector newDirection = t.multiply(direction);
		return new Ray(newOrigin, newDirection);
	}
}
