package org.intranet.graphics.raytrace;

public class Material
{
	private Color color = new Color(1, 1, 1);
	public Color getColor() { return color; }

	private double ambient = 0.1;
	public double getAmbient() { return ambient; }

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

	@Override
	public boolean equals(Object other)
	{
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
