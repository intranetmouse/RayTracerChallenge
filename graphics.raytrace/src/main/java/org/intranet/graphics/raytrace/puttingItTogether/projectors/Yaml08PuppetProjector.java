package org.intranet.graphics.raytrace.puttingItTogether.projectors;

import java.io.File;
import java.io.InputStream;

import org.intranet.graphics.raytrace.persistence.YamlWorldParser;
import org.intranet.graphics.raytrace.puttingItTogether.worldProjector.WorldProjector;

public final class Yaml08PuppetProjector
	extends WorldProjector
{
	public Yaml08PuppetProjector()
	{ super("Puppets"); }


	@Override
	protected void fillWorld()
	{
		String puppetsYml = "/org/intranet/graphics/raytrace/yml/puppets.yml";
		InputStream ymlStream = getClass().getResourceAsStream(
			puppetsYml);
		File parentFolder = new File(getClass().getResource(puppetsYml).getFile()).getParentFile();
		YamlWorldParser parser = new YamlWorldParser();
		world = parser.parse(ymlStream, parentFolder);
	}
}