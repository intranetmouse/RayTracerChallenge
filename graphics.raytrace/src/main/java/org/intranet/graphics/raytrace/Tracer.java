package org.intranet.graphics.raytrace;

import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Vector;

public final class Tracer
{
	public static Color lighting(Material m, Light light, Point position,
		Vector eyeV, Vector normalV, boolean inShadow)
	{
		Pattern p = m.getPattern();
		Color c = p != null ? ((StripePattern)p).stripeAt(position) : m.getColor();
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

	public static Color colorAt(World world, Ray ray)
	{
		IntersectionList intersectionList = world.intersect(ray);
		Intersection hit = intersectionList.hit();
		if (hit == null)
			return new Color(0, 0, 0);
		IntersectionComputations comps = new IntersectionComputations(hit, ray);
		return comps.shadeHit(world);
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
}