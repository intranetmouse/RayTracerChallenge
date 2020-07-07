package org.intranet.graphics.raytrace.puttingItTogether.projectors;

import java.io.File;
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
		String ballsSphereWalls = "/org/intranet/graphics/raytrace/yml/07-3_balls_sphere_walls.yml";
		InputStream ymlStream = getClass().getResourceAsStream(
			ballsSphereWalls);
		File parentFolder = new File(getClass().getResource(ballsSphereWalls).getFile()).getParentFile();
		YamlWorldParser parser = new YamlWorldParser();
		world = parser.parse(ymlStream, parentFolder);
	}
}