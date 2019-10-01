package org.intranet.graphics.raytrace.puttingItTogether.projectors;

import java.io.InputStream;

import org.intranet.graphics.raytrace.persistence.YamlWorldParser;
import org.intranet.graphics.raytrace.puttingItTogether.worldProjector.WorldProjector;

public final class Yaml12BookCubeTableProjector
	extends WorldProjector
{
	public Yaml12BookCubeTableProjector()
	{ super("12-Book Cube Table"); }


	@Override
	protected void fillWorld()
	{
		InputStream ymlStream = getClass().getResourceAsStream(
			"/org/intranet/graphics/raytrace/yml/table.yml");
		YamlWorldParser parser = new YamlWorldParser();
		world = parser.parse(ymlStream);
	}
}