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

	@Override
	public String toString()
	{
		return String.format(
			"%s:[color=%s,ambient=%.2f,diffuse=%.2f,specular=%.2f,shininess=%.2f,reflective=%.2f,refractive=%.2f,transparency=%.2f]",
			getClass().getSimpleName(), color, ambient, diffuse, specular,
			shininess, reflective, refractive, transparency);
	}
}
