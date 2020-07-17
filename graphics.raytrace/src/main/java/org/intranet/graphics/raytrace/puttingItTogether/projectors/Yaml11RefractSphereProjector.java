package org.intranet.graphics.raytrace.puttingItTogether.projectors;

import java.io.File;
import java.io.InputStream;

import org.intranet.graphics.raytrace.persistence.YamlWorldParser;
import org.intranet.graphics.raytrace.puttingItTogether.worldProjector.WorldProjector;

public final class Yaml11RefractSphereProjector
	extends WorldProjector
{
	public Yaml11RefractSphereProjector()
	{ super("11-Refraction glass sphere"); }


	@Override
	protected void fillWorld()
	{
		String refractGlassSphereYml = "/org/intranet/graphics/raytrace/yml/11b-refract-glass-sphere.yml";
		InputStream ymlStream = getClass().getResourceAsStream(
			refractGlassSphereYml);
		File parentFolder = new File(getClass().getResource(refractGlassSphereYml).getFile()).getParentFile();
		YamlWorldParser parser = new YamlWorldParser(ymlStream, parentFolder);
		world = parser.getWorld();
	}
}