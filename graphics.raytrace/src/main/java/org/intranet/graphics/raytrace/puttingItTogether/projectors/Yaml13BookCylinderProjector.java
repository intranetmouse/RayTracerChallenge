package org.intranet.graphics.raytrace.puttingItTogether.projectors;

import java.io.File;
import java.io.InputStream;

import org.intranet.graphics.raytrace.persistence.YamlWorldParser;
import org.intranet.graphics.raytrace.puttingItTogether.worldProjector.WorldProjector;

public final class Yaml13BookCylinderProjector
	extends WorldProjector
{
	public Yaml13BookCylinderProjector()
	{ super("13-Book Cylinders"); }


	@Override
	protected void fillWorld()
	{
		String cylindersYml = "/org/intranet/graphics/raytrace/yml/cylinders.yml";
		InputStream ymlStream = getClass().getResourceAsStream(
			cylindersYml);
		YamlWorldParser parser = new YamlWorldParser();
		File parentFolder = new File(getClass().getResource(cylindersYml).getFile()).getParentFile();
		world = parser.parse(ymlStream, parentFolder);
	}
}