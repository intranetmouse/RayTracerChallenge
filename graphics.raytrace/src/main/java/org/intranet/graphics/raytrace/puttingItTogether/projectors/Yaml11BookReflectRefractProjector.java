package org.intranet.graphics.raytrace.puttingItTogether.projectors;

import java.io.File;
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
		String reflectRefractYml = "/org/intranet/graphics/raytrace/yml/reflect-refract.yml";
		InputStream ymlStream = getClass().getResourceAsStream(
			reflectRefractYml);
		File parentFolder = new File(getClass().getResource(reflectRefractYml).getFile()).getParentFile();
		YamlWorldParser parser = new YamlWorldParser();
		world = parser.parse(ymlStream, parentFolder);
	}
}