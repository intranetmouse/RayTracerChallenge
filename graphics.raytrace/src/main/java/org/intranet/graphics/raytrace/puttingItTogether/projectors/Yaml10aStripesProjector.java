package org.intranet.graphics.raytrace.puttingItTogether.projectors;

import java.io.InputStream;

import org.intranet.graphics.raytrace.puttingItTogether.projector.WorldProjector;

public final class Yaml10aStripesProjector
	extends WorldProjector
{
	public Yaml10aStripesProjector()
	{ super("10a-Pattern Striped"); }


	@Override
	protected void fillWorld()
	{
		InputStream ymlStream = getClass().getResourceAsStream(
			"/org/intranet/graphics/raytrace/yml/10a-patterns-striped.yml");
		YamlWorldParser parser = new YamlWorldParser();
		world = parser.parse(ymlStream);
	}
}