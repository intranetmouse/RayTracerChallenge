package org.intranet.graphics.raytrace.puttingItTogether.projectors;

import java.io.InputStream;

import org.intranet.graphics.raytrace.persistence.YamlWorldParser;
import org.intranet.graphics.raytrace.puttingItTogether.worldProjector.WorldProjector;

public final class Yaml07SphereWallsProjector
	extends WorldProjector
{
	public Yaml07SphereWallsProjector()
	{ super("07-Sphere Walls"); }


	@Override
	protected void fillWorld()
	{
		InputStream ymlStream = getClass().getResourceAsStream(
			"/org/intranet/graphics/raytrace/yml/07-3_balls_sphere_walls.yml");
		YamlWorldParser parser = new YamlWorldParser();
		world = parser.parse(ymlStream);
	}
}