package org.intranet.graphics.raytrace;

import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Tuple;
import org.intranet.graphics.raytrace.primitive.Vector;
import org.intranet.graphics.raytrace.surface.Color;
import org.intranet.graphics.raytrace.surface.Material;
import org.intranet.graphics.raytrace.surface.Pattern;

public final class Tracer
{
	public static Color lighting(Material m, Shape shape, Light light, Point position,
		Vector eyeV, Vector normalV, boolean inShadow)
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

		if (lightDotNormal < 0 || inShadow)
			return ambientColor;

		// compute the diffuse contribution
		Color diffuseColor = effectiveColor.multiply(m.getDiffuse())
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
		Color specularColor = light.getIntensity().multiply(m.getSpecular())
			.multiply(factor);
		//System.out.println("Material.lighting: position="+position+", factor="+factor+", specularColor="+specularColor+",reflectDotEye="+reflectDotEye);

		return ambientDiffuseColor.add(specularColor);
	}

	public static Color colorAt(World world, Ray ray, int remaining)
	{
		IntersectionList intersectionList = world.intersect(ray);
		Intersection hit = intersectionList.hit();
		if (hit == null)
			return new Color(0, 0, 0);
		IntersectionComputations comps = new IntersectionComputations(hit, ray,
			intersectionList.getIntersections());
		return comps.shadeHit(world, remaining);
	}

	public static boolean isShadowed(World world, Point point)
	{
		Vector v = world.getLightSources().get(0).getPosition().subtract(point);
		double distance = v.magnitude();
		Vector direction = v.normalize();
		Ray r = new Ray(point, direction);
		IntersectionList intersections = world.intersect(r);
		Intersection h = intersections.hit();
		return h != null && h.getDistance() < distance;
	}

	public static Color reflectedColor(World world,
		IntersectionComputations comps, int remaining)
	{
		if (remaining <= 0 || comps.getObject().getMaterial().getReflective() < Tuple.EPSILON)
			return new Color(0, 0, 0);

		Ray reflectRay = new Ray(comps.getOverPoint(), comps.getReflectVector());
		Color color = colorAt(world, reflectRay, remaining - 1);
		return color.multiply(comps.getObject().getMaterial().getReflective());
	}

	public static Color refractedColor(World world,
		IntersectionComputations comps, int remaining)
	{
		if (remaining <= 0 || comps.getObject().getMaterial().getTransparency() < Tuple.EPSILON)
			return new Color(0, 0, 0);

		return new Color(1, 1, 1);
//		Ray reflectRay = new Ray(comps.getOverPoint(), comps.getReflectVector());
//		Color color = colorAt(world, reflectRay, remaining - 1);
//		return color.multiply(comps.getObject().getMaterial().getReflective());
	}
}