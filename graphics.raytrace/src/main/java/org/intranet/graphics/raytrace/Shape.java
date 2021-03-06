package org.intranet.graphics.raytrace;

import org.intranet.graphics.raytrace.primitive.BoundingBox;
import org.intranet.graphics.raytrace.primitive.Color;
import org.intranet.graphics.raytrace.primitive.Matrix;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Ray;
import org.intranet.graphics.raytrace.primitive.Transformable;
import org.intranet.graphics.raytrace.primitive.Vector;
import org.intranet.graphics.raytrace.surface.Material;
import org.intranet.graphics.raytrace.surface.Pattern;

public abstract class Shape
	implements Transformable
{
	private boolean castShadow = true;
	public boolean isCastShadow() { return castShadow; }
	public void setCastShadow(boolean value) { castShadow = value; }

	private ShapeParent parent;
	public ShapeParent getParent() { return parent; }
	public void setParent(ShapeParent value) { parent = value; }

	public BoundingBox getParentSpaceBounds()
	{
		return getBoundingBox().transform(getTransform());
	}

	private Ray savedRay;
	public Ray getSavedRay() { return savedRay; }

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

	public boolean includes(Shape s)
	{
		return s == this;
	}

	// MATERIAL ----------------------------------------------------------------
	private Material material = new Material();
	public final Material getMaterial() { return material; }
	public final void setMaterial(Material value) { material = value; }

	public Color colorAt(Pattern pattern, Point pt)
	{
		Point objectPt = worldToObject(pt);
		Point patternPt = pattern.getTransform().inverse().multiply(objectPt);
		return pattern.colorAt(patternPt);
	}

	// NORMALS -----------------------------------------------------------------
	protected abstract Vector localNormalAt(Point point, Intersection intersection);

	public final Vector normalAt(Point worldPoint, Intersection hit)
	{
		Point localPoint = worldToObject(worldPoint);

		Vector localNormalVector = localNormalAt(localPoint, hit);

		return normalToWorld(localNormalVector);
	}

	public Point worldToObject(Point point)
	{
		if (parent != null)
			point = parent.worldToObject(point);

		return transform.inverse().multiply(point);
	}

	public final Vector normalToWorld(Vector normal)
	{
		normal = transform.inverse().transpose().multiply(normal);
		normal = normal.withW(0).normalize();

		if (parent != null)
			normal = parent.normalToWorld(normal);

		return normal;
	}

	public final Vector testLocalNormalAt(Point p)
	{
		return localNormalAt(p, null);
	}

	// INTERSECTIONS -----------------------------------------------------------
	public abstract IntersectionList localIntersections(Ray ray);

	public final IntersectionList intersections(Ray ray)
	{
		Matrix inverse = transform.inverse();
		Ray localRay = ray.transform(inverse);
		savedRay = localRay;

		return localIntersections(localRay);
	}

	// BOUNDING BOX
	private BoundingBox boundingBox;
	public final BoundingBox getBoundingBox()
	{
		if (boundingBox == null)
			boundingBox = createBoundingBox();
		return boundingBox;
	}

	protected BoundingBox createBoundingBox()
	{
		return new BoundingBox(new Point(-1, -1, -1), new Point(1, 1, 1));
	}


	public void divide(int subdivisions)
	{
		return;
	}

	protected void deepCopyFrom(Shape other)
	{
		material = other.material.duplicate();
		transform = other.getTransform();
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
