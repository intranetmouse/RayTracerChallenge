package org.intranet.graphics.raytrace;

import org.intranet.graphics.raytrace.primitive.Matrix;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Vector;
import org.intranet.graphics.raytrace.surface.Color;
import org.intranet.graphics.raytrace.surface.Material;
import org.intranet.graphics.raytrace.surface.Pattern;

public abstract class Shape
	implements Transformable
{
	final public Vector normalAt(Point worldPoint)
	{
		Matrix inverse = transform.inverse();

		Point localPoint = inverse.multiply(worldPoint);

		Vector localNormalVector = localNormalAt(localPoint, inverse);

		Vector worldNormal = inverse.transpose().multiply(localNormalVector);

		Vector v = new Vector(worldNormal.getX(), worldNormal.getY(),
			worldNormal.getZ());
System.out.println("worldNormal = " + worldNormal+", v="+v);
		return v.normalize();
//		return worldNormal.normalize();
	}

	public final IntersectionList intersections(Ray ray)
	{
		Ray localRay = ray.transform(transform.inverse());
		savedRay = localRay;
		return localIntersections(localRay);
	}

	private Ray savedRay;
	public Ray getSavedRay() { return savedRay; }

	public abstract IntersectionList localIntersections(Ray ray);
	protected abstract Vector localNormalAt(Point point, Matrix inverse);

	public final Vector testLocalNormalAt(Point p)
	{
		return localNormalAt(p, Matrix.identity(4));
	}

	private Material material = new Material();
	public final Material getMaterial() { return material; }
	public final void setMaterial(Material value) { material = value; }

	private Matrix transform = Matrix.identity(4);
	@Override
	public final Matrix getTransform() { return transform; }
	@Override
	public final void setTransform(Matrix value) { transform = value; }

	@Override
	public final boolean equals(Object other)
	{
		Shape otherSphere = (Shape)other;
		if (!transform.equals(otherSphere.transform))
			return false;
		if (!getMaterial().equals(otherSphere.getMaterial()))
			return false;
		return true;
	}
	protected abstract boolean shapeEquals(Object other);

	public Color colorAt(Pattern pattern, Point pt)
	{
		Point objectPt = transform.inverse().multiply(pt);
		Point patternPt = pattern.getTransform().inverse().multiply(objectPt);
		return pattern.colorAt(patternPt);
	}
}
