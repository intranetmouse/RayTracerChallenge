package org.intranet.graphics.raytrace;

public final class Tracer
{
	public static Color lighting(Material m, PointLight light, Point position,
		Vector eyeV, Vector normalV)
	{
		// combine the surface color with the light's color/intensity
		Color effectiveColor = m.getColor().multiply(light.getIntensity());

		// compute the ambient contribution
		Color ambientColor = effectiveColor.multiply(m.getAmbient());

		// find the direction to the light source
		Vector lightV = light.getPosition().subtract(position).normalize();

		// lightDotNormal represents the cosine of the angle between the
		// light vector and the normal vector. A negative number means the
		// light is on the other side of the surface.
		double lightDotNormal = lightV.dot(normalV);

		if (lightDotNormal < 0)
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
}