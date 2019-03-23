package org.intranet.graphics.raytrace;

public class PointLight
	implements Light
{
	private Point position;
	public Point getPosition() { return position; }

	private Color intensity;
	public Color getIntensity() { return intensity; }

	public PointLight(Point position, Color intensity)
	{
		this.position = position;
		this.intensity = intensity;
	}
}
