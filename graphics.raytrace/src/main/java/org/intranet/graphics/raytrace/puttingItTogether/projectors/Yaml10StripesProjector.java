package org.intranet.graphics.raytrace.puttingItTogether.projectors;

import java.io.File;
import java.io.InputStream;

import org.intranet.graphics.raytrace.persistence.YamlWorldParser;
import org.intranet.graphics.raytrace.puttingItTogether.worldProjector.WorldProjector;

public final class Yaml10StripesProjector
	extends WorldProjector
{
	public Yaml10StripesProjector()
	{ super("10-Pattern Striped"); }


	@Override
	protected void fillWorld()
	{
		String patternsStripedYml = "/org/intranet/graphics/raytrace/yml/10-patterns-striped.yml";
		InputStream ymlStream = getClass().getResourceAsStream(
			patternsStripedYml);
		File parentFolder = new File(getClass().getResource(patternsStripedYml).getFile()).getParentFile();
		YamlWorldParser parser = new YamlWorldParser(ymlStream, parentFolder);
		world = parser.getWorld();
	}
}