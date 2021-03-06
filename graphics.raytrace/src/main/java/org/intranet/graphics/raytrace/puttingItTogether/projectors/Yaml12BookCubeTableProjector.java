package org.intranet.graphics.raytrace.puttingItTogether.projectors;

import java.io.File;
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
		String tableYml = "/org/intranet/graphics/raytrace/yml/table.yml";
		InputStream ymlStream = getClass().getResourceAsStream(
			tableYml);
		File parentFolder = new File(getClass().getResource(tableYml).getFile()).getParentFile();
		YamlWorldParser parser = new YamlWorldParser(ymlStream, parentFolder);
		world = parser.getWorld();
	}
}