package org.intranet.graphics.raytrace;

import org.intranet.graphics.raytrace.primitive.Matrix;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Ray;
import org.intranet.graphics.raytrace.primitive.Transformable;
import org.intranet.graphics.raytrace.primitive.Vector;
import org.intranet.graphics.raytrace.shape.BoundingBox;
import org.intranet.graphics.raytrace.surface.Color;
import org.intranet.graphics.raytrace.surface.Material;
import org.intranet.graphics.raytrace.surface.Pattern;

public abstract class Shape
	implements Transformable
{
	public final Vector normalAt(Point worldPoint)
	{
		Point localPoint = worldToObject(worldPoint);

		Vector localNormalVector = localNormalAt(localPoint);

		return normalToWorld(localNormalVector);
	}

	public final IntersectionList intersections(Ray ray)
	{
		Ray localRay = ray.transform(transform.inverse());
		savedRay = localRay;
		return localIntersections(localRay);
	}

	private boolean castShadow = true;
	public boolean isCastShadow() { return castShadow; }
	public void setCastShadow(boolean value) { castShadow = value; }

	private ShapeParent parent;
	public ShapeParent getParent() { return parent; }
	public void setParent(ShapeParent value) { parent = value; }

	private Ray savedRay;
	public Ray getSavedRay() { return savedRay; }

	public abstract IntersectionList localIntersections(Ray ray);
	protected abstract Vector localNormalAt(Point point);

	public final Vector testLocalNormalAt(Point p)
	{
		return localNormalAt(p);
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
		if (!(other instanceof Shape))
			return false;
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
		Point objectPt = worldToObject(pt);
		Point patternPt = pattern.getTransform().inverse().multiply(objectPt);
		return pattern.colorAt(patternPt);
	}

	public Point worldToObject(Point point)
	{
		if (parent != null)
			point = parent.worldToObject(point);

		return transform.inverse().multiply(point);
	}

	public Vector normalToWorld(Vector normal)
	{
		normal = transform.inverse().transpose().multiply(normal);
		normal = normal.withW(0).normalize();

		if (parent != null)
			normal = parent.normalToWorld(normal);

		return normal;
	}

	public BoundingBox getBoundingBox()
	{
		return new BoundingBox(new Point(-1, -1, -1), new Point(1, 1, 1));
	}

	protected void deepCopyFrom(Shape other)
	{
		material = other.material.duplicate();
		transform = other.transform.inverse().inverse();
		// Rays are immutable
		savedRay = other.savedRay;
	}

	public abstract Shape deepCopy();

	@Override
	public String toString()
	{
		String parentName = parent == null ? "none" : parent.getClass().getName();
		return getClass().getSimpleName() + " [parent=" + parentName + ", savedRay=" + savedRay
			+ ", material=" + material + ", transform=" + transform + "]";
	}
}
