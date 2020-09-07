package org.intranet.graphics.raytrace.surface;

import org.intranet.graphics.raytrace.primitive.Color;
import org.intranet.graphics.raytrace.primitive.DoublePair;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.surface.map.CubeSide;
import org.intranet.graphics.raytrace.surface.pattern2d.UvPattern;

public final class CubeMapPattern
	extends Pattern
{
	private final UvPattern left;
	private final UvPattern front;
	private final UvPattern right;
	private final UvPattern back;
	private final UvPattern up;
	private final UvPattern down;

	public CubeMapPattern(UvPattern left, UvPattern front, UvPattern right,
		UvPattern back, UvPattern up, UvPattern down)
	{
		super();
		this.left = left;
		this.right = right;
		this.front = front;
		this.back = back;
		this.up = up;
		this.down = down;
	}

	@Override
	public Color colorAt(Point point)
	{
		CubeSide face = CubeSide.faceFromPoint(point);
		DoublePair uv = face.map(point);

		UvPattern pattern = patternForFace(face);
		return pattern.colorAt(uv.getFirst(), uv.getSecond());
	}

	public UvPattern patternForFace(CubeSide face)
	{
		switch (face)
		{
			case FRONT:
				return front;
			case BACK:
				return back;
			case UP:
				return up;
			case DOWN:
				return down;
			case LEFT:
				return left;
			case RIGHT:
				return right;
			default:
				throw new IllegalArgumentException("Unknown face " + face);
		}
	}
}