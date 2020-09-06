package org.intranet.graphics.raytrace.surface;

import org.intranet.graphics.raytrace.primitive.Color;
import org.intranet.graphics.raytrace.primitive.DoublePair;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.surface.map.UvMap;
import org.intranet.graphics.raytrace.surface.pattern2d.UvPattern;

public class TextureMapPattern
	extends Pattern
{
	private final UvPattern uvPattern;
	private final UvMap uvMap;

	public TextureMapPattern(UvPattern uvPattern, UvMap uvMap)
	{
		this.uvPattern = uvPattern;
		this.uvMap = uvMap;
	}

	@Override
	public Color colorAt(Point point)
	{
		DoublePair uv = uvMap.map(point);
		double u = uv.getFirst();
		double v = uv.getSecond();
		return uvPattern.colorAt(u, v);
	}
}
