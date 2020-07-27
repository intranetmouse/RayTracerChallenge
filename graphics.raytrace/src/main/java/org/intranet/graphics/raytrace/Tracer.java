package org.intranet.graphics.raytrace;

import java.util.List;

import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Ray;
import org.intranet.graphics.raytrace.primitive.Vector;
import org.intranet.graphics.raytrace.shape.AreaLight;
import org.intranet.graphics.raytrace.shape.PointLight;
import org.intranet.graphics.raytrace.surface.Color;
import org.intranet.graphics.raytrace.surface.Material;
import org.intranet.graphics.raytrace.surface.Pattern;

public final class Tracer
{
//	public static Color lighting(Shape shape, List<Light> lightSources,
//		Point position, Vector eyeVector, Vector normalVector, World world)
//	{
//		Material m = shape.getMaterial();
//		Pattern p = m.getPattern();
//		Color c = p != null ? shape.colorAt(p, position) : m.getColor();
//
//		// compute the ambient contribution
//		Color ambientColor = c.multiply(m.getAmbient());
//
//		Color totalColor = ambientColor;
//
//		for (Light light : lightSources)
//		{
//			// Previously isShadowed
//			double intensity = Tracer.intensityAt(light, position, world);
//
//			// combine the surface color with the light's color/intensity
//			Color effectiveColor = c.multiply(intensity);
//
//			// find the direction to the light source
//			Vector lightV = light.getPosition().subtract(position).normalize();
//
//			// lightDotNormal represents the cosine of the angle between the
//			// light vector and the normal vector. A negative number means the
//			// light is on the other side of the surface.
//			double lightDotNormal = lightV.dot(normalVector);
//			boolean backOfObject = lightDotNormal < 0;
//
//			boolean inShadow = intensity == 0.0;
//			if (backOfObject || inShadow)
//				continue;
//
//			// compute the diffuse contribution
//			Color diffuseColor = effectiveColor.multiply(m.getDiffuse() * intensity)
//				.multiply(lightDotNormal);
//			totalColor = totalColor.add(diffuseColor);
//
//			// reflectDotEye represents the cosine of the angle between the
//			// reflection vector and the eye vector. A negative number means the
//			// light reflects away from the eye.
//			Vector reflectV = lightV.negate().reflect(normalVector);
//			double reflectDotEye = reflectV.dot(eyeVector);
//
//			if (reflectDotEye < 0)
//				return ambientColor.add(diffuseColor);
//
//			// compute the specular contribution
//			double factor = Math.pow(reflectDotEye, m.getShininess());
//			Color specularColor = light.getIntensity()
//				.multiply(m.getSpecular() * intensity * factor);
//			totalColor = totalColor.add(specularColor);
//			//System.out.println("Material.lighting: position="+position+", factor="+factor+", specularColor="+specularColor+",reflectDotEye="+reflectDotEye);
//		}
//
//		return totalColor;
//	}

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
		boolean backOfObject = lightDotNormal < 0;

		boolean inShadow = intensity == 0.0;
		if (backOfObject || inShadow)
			return ambientColor;

		// compute the diffuse contribution
		Color diffuseColor = effectiveColor.multiply(m.getDiffuse() * intensity)
			.multiply(lightDotNormal);

		// reflectDotEye represents the cosine of the angle between the
		// reflection vector and the eye vector. A negative number means the
		// light reflects away from the eye.
		Vector reflectV = lightV.negate().reflect(normalV);
		double reflectDotEye = reflectV.dot(eyeV);

		if (reflectDotEye < 0)
			return ambientColor.add(diffuseColor);

		// compute the specular contribution
		double factor = Math.pow(reflectDotEye, m.getShininess());
		Color specularColor = light.getIntensity().multiply(m.getSpecular() * intensity)
			.multiply(factor);
		//System.out.println("Material.lighting: position="+position+", factor="+factor+", specularColor="+specularColor+",reflectDotEye="+reflectDotEye);

		return ambientColor.add(diffuseColor).add(specularColor);
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

	public static double intensityAt(Light light, Point pt, World world)
	{
		// FIXME: Explicit case analysis on Light
		if (light instanceof PointLight)
			return isShadowed(world, light.getPosition(), pt) ? 0.0 : 1.0;
		else if (light instanceof AreaLight)
		{
			double total = 0.0;
			AreaLight areaLight = (AreaLight)light;

			for (int v = 0; v < areaLight.getVsteps(); v++)
			{
				for (int u = 0; u < areaLight.getUsteps(); u++)
				{
					Point light_position = areaLight.pointOnLight(u, v);
					if (!isShadowed(world, light_position, pt))
						total += 1.0;
				}
			}

			return total / areaLight.getNumSamples();
		}
		throw new UnsupportedOperationException();
	}
}