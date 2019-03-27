package org.intranet.graphics.raytrace;

public class Material
{
	private Color color = new Color(1, 1, 1);
	public Color getColor() { return color; }
	public void setColor(Color value) { color = value; }

	private double ambient = 0.1;
	public double getAmbient() { return ambient; }
	public void setAmbient(double value) { ambient = value; }

	private double diffuse = 0.9;
	public double getDiffuse() { return diffuse; }
	public void setDiffuse(double value) { diffuse = value; }

	private double specular = 0.9;
	public double getSpecular() { return specular; }
	public void setSpecular(double value) { specular = value; }

	private double shininess = 200.0;
	public double getShininess() { return shininess; }

	private double reflective = 0.0;
	public double getReflective() { return reflective; }

	private double refractive = 1.0;
	public double getRefractive() { return refractive; }

	private double transparency = 0.0;
	public double getTransparency() { return transparency; }

	public Color lighting(PointLight light, Point position, Vector eyeV,
		Vector normalV)
	{
		// combine the surface color with the light's color/intensity
		Color effectiveColor = color.multiply(light.getIntensity());

		// compute the ambient contribution
		Color ambientColor = effectiveColor.multiply(ambient);

		// find the direction to the light source
		Vector lightV = light.getPosition().subtract(position).normalize();

		// lightDotNormal represents the cosine of the angle between the
		// light vector and the normal vector. A negative number means the
		// light is on the other side of the surface.
		double lightDotNormal = lightV.dot(normalV);

		if (lightDotNormal < 0)
			return ambientColor;

		// compute the diffuse contribution
		Color diffuseColor = effectiveColor.multiply(diffuse)
			.multiply(lightDotNormal);
		Color ambientDiffuseColor = ambientColor.add(diffuseColor);

		// reflectDotEye represents the cosine of the angle between the
		// reflection vector and the eye vector. A negative number means the
		// light reflects away from the eye.
		Vector reflectV = lightV.negate().reflect(normalV);
		double reflectDotEye = reflectV.dot(eyeV);

//		if (reflectDotEye < 0)
			return ambientDiffuseColor;

//		// compute the specular contribution
//		double factor = Math.pow(reflectDotEye, shininess);
//		Color specularColor = light.getIntensity().multiply(specular)
//			.multiply(factor);
//System.out.println("Material.lighting: position="+position+", factor="+factor+", specularColor="+specularColor+",reflectDotEye="+reflectDotEye);
//
//		return ambientDiffuseColor.add(specularColor);
	}

	@Override
	public boolean equals(Object other)
	{
		if (other == this)
			return true;
		if (!(other instanceof Material))
			return false;
		Material otherMaterial = (Material)other;
		if (!color.equals(otherMaterial.getColor()))
			return false;
		if (!Tuple.dblEqual(ambient, otherMaterial.getAmbient()))
			return false;
		if (!Tuple.dblEqual(diffuse, otherMaterial.getDiffuse()))
			return false;
		if (!Tuple.dblEqual(specular, otherMaterial.getSpecular()))
			return false;
		if (!Tuple.dblEqual(shininess, otherMaterial.getShininess()))
			return false;
		if (!Tuple.dblEqual(reflective, otherMaterial.getReflective()))
			return false;
		if (!Tuple.dblEqual(refractive, otherMaterial.getRefractive()))
			return false;
		if (!Tuple.dblEqual(transparency, otherMaterial.getTransparency()))
			return false;
		return true;
	}
}
