package org.intranet.graphics.raytrace.puttingItTogether.projectors;

import java.io.InputStream;

import org.intranet.graphics.raytrace.puttingItTogether.projector.WorldProjector;

public final class Yaml11ReflectRefractProjector
	extends WorldProjector
{
	public Yaml11ReflectRefractProjector()
	{ super("11-Pattern Reflection & Refraction"); }


	@Override
	protected void fillWorld()
	{
		InputStream ymlStream = getClass().getResourceAsStream(
			"/org/intranet/graphics/raytrace/yml/11-patterns-reflect_refract.yml");
		YamlWorldParser parser = new YamlWorldParser();
		world = parser.parse(ymlStream);
	}
}