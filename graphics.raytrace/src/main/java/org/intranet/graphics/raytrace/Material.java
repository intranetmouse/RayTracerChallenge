package org.intranet.graphics.raytrace;

public class Material
{
	private Color color = new Color(1, 1, 1);
	public Color getColor() { return color; }

	private double ambient = 0.1;
	public double getAmbient() { return ambient; }
	public void setAmbient(double value) { ambient = value; }

	private double diffuse = 0.9;
	public double getDiffuse() { return diffuse; }

	private double specular = 0.9;
	public double getSpecular() { return specular; }

	private double shininess = 200.0;
	public double getShininess() { return shininess; }

	private double reflective = 0.0;
	public double getReflective() { return reflective; }

	private double refractive = 1.0;
	public double getRefractive() { return refractive; }

	private double transparency = 0.0;
	public double getTransparency() { return transparency; }

	public Color lighting(PointLight pointLight, Point position, Vector eyev,
		Vector normalV)
	{
		Color effectiveColor = color.multiply(pointLight.getIntensity());
		Vector lightV = pointLight.getPosition().subtract(position).normalize();
		Color ambientColor = effectiveColor.multiply(ambient);
		double lightDotNormal = lightV.dot(normalV);

		if (lightDotNormal < 0)
			return ambientColor;

		Color diffuseColor = effectiveColor.multiply(diffuse).multiply(lightDotNormal);

		Vector reflectV = lightV.negate().reflect(normalV);
		double reflectDotEye = reflectV.dot(eyev);

		Color specularColor;
		if (reflectDotEye < 0)
			specularColor = new Color(0, 0, 0);
		else
		{
			double factor = Math.pow(reflectDotEye, shininess);
			specularColor = pointLight.getIntensity().multiply(specular)
				.multiply(factor);
		}

		return ambientColor.add(diffuseColor).add(specularColor);
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
