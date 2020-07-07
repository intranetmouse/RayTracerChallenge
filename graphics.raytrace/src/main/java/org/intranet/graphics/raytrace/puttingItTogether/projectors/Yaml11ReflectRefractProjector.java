package org.intranet.graphics.raytrace.puttingItTogether.projectors;

import java.io.File;
import java.io.InputStream;

import org.intranet.graphics.raytrace.persistence.YamlWorldParser;
import org.intranet.graphics.raytrace.puttingItTogether.worldProjector.WorldProjector;

public final class Yaml11ReflectRefractProjector
	extends WorldProjector
{
	public Yaml11ReflectRefractProjector()
	{ super("11-Reflection & Refraction"); }


	@Override
	protected void fillWorld()
	{
		String reflectRefractYml = "/org/intranet/graphics/raytrace/yml/11a-reflect-refract.yml";
		InputStream ymlStream = getClass().getResourceAsStream(
			reflectRefractYml);
		YamlWorldParser parser = new YamlWorldParser();
		File parentFolder = new File(getClass().getResource(reflectRefractYml).getFile()).getParentFile();
		world = parser.parse(ymlStream, parentFolder);
	}
}