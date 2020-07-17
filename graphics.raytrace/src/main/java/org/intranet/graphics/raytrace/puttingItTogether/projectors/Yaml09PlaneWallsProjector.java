package org.intranet.graphics.raytrace.puttingItTogether.projectors;

import java.io.File;
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
		String ballsPlaneWallsYml = "/org/intranet/graphics/raytrace/yml/09-3_balls_plane_walls.yml";
		InputStream ymlStream = getClass().getResourceAsStream(
			ballsPlaneWallsYml);
		File parentFolder = new File(getClass().getResource(ballsPlaneWallsYml).getFile()).getParentFile();
		YamlWorldParser parser = new YamlWorldParser(ymlStream, parentFolder);
		world = parser.getWorld();
	}
}