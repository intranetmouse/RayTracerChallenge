package org.intranet.graphics.raytrace.puttingItTogether.projectors;

import java.io.InputStream;

import org.intranet.graphics.raytrace.puttingItTogether.projector.WorldProjector;

public final class Yaml13BookCylinderProjector
	extends WorldProjector
{
	public Yaml13BookCylinderProjector()
	{ super("13-Book Cylinders"); }


	@Override
	protected void fillWorld()
	{
		InputStream ymlStream = getClass().getResourceAsStream(
			"/org/intranet/graphics/raytrace/yml/cylinders.yml");
		YamlWorldParser parser = new YamlWorldParser();
		world = parser.parse(ymlStream);
	}
}