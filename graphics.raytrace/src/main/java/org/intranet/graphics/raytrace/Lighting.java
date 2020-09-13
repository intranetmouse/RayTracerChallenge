package org.intranet.graphics.raytrace;

import java.util.List;

import org.intranet.graphics.raytrace.primitive.Color;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Ray;
import org.intranet.graphics.raytrace.primitive.Vector;
import org.intranet.graphics.raytrace.surface.Material;
import org.intranet.graphics.raytrace.surface.Pattern;

public final class Lighting
{
	private Lighting() { }

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

		Color sumDiffuseColor = Color.BLACK;
		Color sumSpecularColor = Color.BLACK;
		List<Point> samples = light.getSamples();
		for (Point sample : samples)
		{
			// find the direction to the light source
			Vector lightV = sample.subtract(position).normalize();

			// lightDotNormal represents the cosine of the angle between the
			// light vector and the normal vector. A negative number means the
			// light is on the other side of the surface.
			double lightDotNormal = lightV.dot(normalV);
			boolean backOfObject = lightDotNormal < 0;

			boolean inShadow = intensity == 0.0;
			if (backOfObject || inShadow)
				continue;

			// compute the diffuse contribution
			Color diffuseContribution = effectiveColor
				.multiply(m.getDiffuse() * intensity * lightDotNormal);
			sumDiffuseColor = sumDiffuseColor.add(diffuseContribution);

			// reflectDotEye represents the cosine of the angle between the
			// reflection vector and the eye vector. A negative number means the
			// light reflects away from the eye.
			Vector reflectV = lightV.negate().reflect(normalV);
			double reflectDotEye = reflectV.dot(eyeV);

			if (reflectDotEye < 0)
				continue;

			// compute the specular contribution
			double factor = Math.pow(reflectDotEye, m.getShininess());
			Color specularContribution = light.getIntensity()
				.multiply(m.getSpecular() * intensity * factor);
			sumSpecularColor = sumSpecularColor.add(specularContribution);
			//System.out.println("Material.lighting: position="+position+", factor="+factor+", specularColor="+specularColor+",reflectDotEye="+reflectDotEye);
		}

		Color avgDiffuseColor = sumDiffuseColor.divide(samples.size());
		Color avgSpecularColor = sumSpecularColor.divide(samples.size());

		return ambientColor.add(avgDiffuseColor).add(avgSpecularColor);
	}
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

	public static double isShadowed(World world, Point point, Light light)
	{
		return isShadowed(world, light.getSamples(), point);
	}

	public static double isShadowed(World world, List<Point> lightPositions,
		Point otherPoint)
	{
		return lightPositions.stream()
			.mapToInt(lightPosition -> {
				Vector v = lightPosition.subtract(otherPoint);
				double distance = v.magnitude();
				Vector direction = v.normalize();
				Ray r = new Ray(otherPoint, direction);
				IntersectionList intersections = new IntersectionList(
					world.getSceneObjects(), r, true);
				Intersection h = intersections.hit();
				return (h != null && h.getDistance() < distance) ? 0 : 1;
			})
			.average()
			.orElse(0.0);
	}
}