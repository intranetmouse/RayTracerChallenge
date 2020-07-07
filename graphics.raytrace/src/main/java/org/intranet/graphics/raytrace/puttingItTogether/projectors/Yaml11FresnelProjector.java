package org.intranet.graphics.raytrace.puttingItTogether.projectors;

import java.io.File;
import java.io.InputStream;

import org.intranet.graphics.raytrace.persistence.YamlWorldParser;
import org.intranet.graphics.raytrace.puttingItTogether.worldProjector.WorldProjector;

public final class Yaml11FresnelProjector
	extends WorldProjector
{
	public Yaml11FresnelProjector()
	{ super("11-Refraction glass sphere"); }


	@Override
	protected void fillWorld()
	{
		String refractFresnelYml = "/org/intranet/graphics/raytrace/yml/11c-refract-fresnel.yml";
		InputStream ymlStream = getClass().getResourceAsStream(
			refractFresnelYml);
		YamlWorldParser parser = new YamlWorldParser();
		File parentFolder = new File(getClass().getResource(refractFresnelYml).getFile()).getParentFile();
		world = parser.parse(ymlStream, parentFolder);
	}
}