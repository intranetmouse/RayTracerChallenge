package org.intranet.graphics.raytrace.puttingItTogether.projectors;

import java.io.InputStream;

import org.intranet.graphics.raytrace.persistence.YamlWorldParser;
import org.intranet.graphics.raytrace.puttingItTogether.worldProjector.WorldProjector;

public final class Yaml09PlaneWallsProjector
	extends WorldProjector
{
	public Yaml09PlaneWallsProjector()
	{ super("09-Plane Walls"); }


	@Override
	protected void fillWorld()
	{
		InputStream ymlStream = getClass().getResourceAsStream(
			"/org/intranet/graphics/raytrace/yml/09-3_balls_plane_walls.yml");
		YamlWorldParser parser = new YamlWorldParser();
		world = parser.parse(ymlStream);
	}
}