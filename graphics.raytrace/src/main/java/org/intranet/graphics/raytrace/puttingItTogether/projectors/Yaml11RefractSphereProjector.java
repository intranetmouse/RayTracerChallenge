package org.intranet.graphics.raytrace.puttingItTogether.projectors;

import java.io.InputStream;

import org.intranet.graphics.raytrace.puttingItTogether.projector.WorldProjector;

public final class Yaml11RefractSphereProjector
	extends WorldProjector
{
	public Yaml11RefractSphereProjector()
	{ super("11-Refraction glass sphere"); }


	@Override
	protected void fillWorld()
	{
		InputStream ymlStream = getClass().getResourceAsStream(
			"/org/intranet/graphics/raytrace/yml/11-patterns-glass-sphere.yml");
		YamlWorldParser parser = new YamlWorldParser();
		world = parser.parse(ymlStream);
	}
}