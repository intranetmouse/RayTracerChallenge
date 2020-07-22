package org.intranet.graphics.raytrace;

import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Ray;
import org.intranet.graphics.raytrace.primitive.Vector;
import org.intranet.graphics.raytrace.surface.Color;
import org.intranet.graphics.raytrace.surface.Material;
import org.intranet.graphics.raytrace.surface.Pattern;

public final class Tracer
{
	// OLD method
	public static Color lighting(Material m, Shape shape, Light light,
		Point position, Vector eyeV, Vector normalV, boolean inShadow)
	{
		return lighting(m, shape, light, position, eyeV, normalV,
			inShadow ? 0.0 : 1.0);
	}

	public static Color lighting(Material m, Shape shape, Light light,
		Point position, Vector eyeV, Vector normalV, double intensity)
	{
		Pattern p = m.getPattern();
		Color c = p != null ? shape.colorAt(p, position) : m.getColor();
		// combine the surface color with the light's color/intensity
		Color effectiveColor = c.multiply(light.getIntensity());

		// compute the ambient contribution
		Color ambientColor = effectiveColor.multiply(m.getAmbient());

		// find the direction to the light source
		Vector lightV = light.getPosition().subtract(position).normalize();

		// lightDotNormal represents the cosine of the angle between the
		// light vector and the normal vector. A negative number means the
		// light is on the other side of the surface.
		double lightDotNormal = lightV.dot(normalV);

		boolean inShadow = intensity == 0.0;
		if (lightDotNormal < 0 || inShadow)
			return ambientColor;

		// compute the diffuse contribution
		Color diffuseColor = effectiveColor.multiply(m.getDiffuse() * intensity)
			.multiply(lightDotNormal);
		Color ambientDiffuseColor = ambientColor.add(diffuseColor);

		// reflectDotEye represents the cosine of the angle between the
		// reflection vector and the eye vector. A negative number means the
		// light reflects away from the eye.
		Vector reflectV = lightV.negate().reflect(normalV);
		double reflectDotEye = reflectV.dot(eyeV);

		if (reflectDotEye < 0)
			return ambientDiffuseColor;

		// compute the specular contribution
		double factor = Math.pow(reflectDotEye, m.getShininess());
		Color specularColor = light.getIntensity().multiply(m.getSpecular() * intensity)
			.multiply(factor);
		//System.out.println("Material.lighting: position="+position+", factor="+factor+", specularColor="+specularColor+",reflectDotEye="+reflectDotEye);

		return ambientDiffuseColor.add(specularColor);
	}

	public static boolean isShadowed(World world, Point point, Light light)
	{
		return isShadowed(world, light.getPosition(), point);
	}

	public static boolean isShadowed(World world, Point lightPosition,
		Point otherPoint)
	{
		Vector v = lightPosition.subtract(otherPoint);
		double distance = v.magnitude();
		Vector direction = v.normalize();
		Ray r = new Ray(otherPoint, direction);
		IntersectionList intersections = world.intersect(r, true);
		Intersection h = intersections.hit();
		return h != null && h.getDistance() < distance;
	}

	public static double intensityAt(Light l, Point pt, World w)
	{
		return isShadowed(w, l.getPosition(), pt) ? 0.0 : 1.0;
	}
}