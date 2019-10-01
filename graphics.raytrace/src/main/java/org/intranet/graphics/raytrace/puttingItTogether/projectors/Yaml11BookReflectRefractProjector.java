package org.intranet.graphics.raytrace.puttingItTogether.projectors;

import java.io.InputStream;

import org.intranet.graphics.raytrace.persistence.YamlWorldParser;
import org.intranet.graphics.raytrace.puttingItTogether.worldProjector.WorldProjector;

public final class Yaml11BookReflectRefractProjector
	extends WorldProjector
{
	public Yaml11BookReflectRefractProjector()
	{ super("11-Book Reflection & Refraction"); }


	@Override
	protected void fillWorld()
	{
		InputStream ymlStream = getClass().getResourceAsStream(
			"/org/intranet/graphics/raytrace/yml/reflect-refract.yml");
		YamlWorldParser parser = new YamlWorldParser();
		world = parser.parse(ymlStream);
	}
}