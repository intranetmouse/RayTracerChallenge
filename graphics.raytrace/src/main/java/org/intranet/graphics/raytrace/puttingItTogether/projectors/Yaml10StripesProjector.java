package org.intranet.graphics.raytrace.puttingItTogether.projectors;

import java.io.InputStream;

import org.intranet.graphics.raytrace.puttingItTogether.projector.WorldProjector;

public final class Yaml10StripesProjector
	extends WorldProjector
{
	public Yaml10StripesProjector()
	{ super("10-Pattern Striped"); }


	@Override
	protected void fillWorld()
	{
		InputStream ymlStream = getClass().getResourceAsStream(
			"/org/intranet/graphics/raytrace/yml/10-patterns-striped.yml");
		YamlWorldParser parser = new YamlWorldParser();
		world = parser.parse(ymlStream);
	}
}