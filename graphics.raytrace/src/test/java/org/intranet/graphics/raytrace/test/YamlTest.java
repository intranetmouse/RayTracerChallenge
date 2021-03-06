package org.intranet.graphics.raytrace.test;

import java.io.File;
import java.io.InputStream;

import org.intranet.graphics.raytrace.Camera;
import org.intranet.graphics.raytrace.Light;
import org.intranet.graphics.raytrace.Shape;
import org.intranet.graphics.raytrace.World;
import org.intranet.graphics.raytrace.persistence.YamlWorldParser;
import org.intranet.graphics.raytrace.puttingItTogether.projectors.Yaml14BookGroupProjector;
import org.junit.Assert;
import org.junit.Test;

public class YamlTest
{
	@Test
	public void testGroups()
	{
		InputStream ymlStream = YamlTest.class
			.getResourceAsStream(Yaml14BookGroupProjector.GROUP_YML);
		String fileName = YamlTest.class.getResource(Yaml14BookGroupProjector.GROUP_YML).getFile();
		File relativePath = new File(fileName).getParentFile();
		YamlWorldParser parser = new YamlWorldParser(ymlStream, relativePath);
		World w = parser.getWorld();

		Assert.assertNotNull(w);

		printWorld(w);
	}

	private void printWorld(World w)
	{
		printCamera(w.getCamera());
		for (Light light : w.getLightSources())
			printLightSource(light);
		for (Shape s : w.getSceneObjects())
			printShape(s);
	}

	private void printShape(Shape s)
	{
		System.out.println(s);
	}

	private void printLightSource(Light light)
	{
		System.out.println(light);
	}

	private void printCamera(Camera camera)
	{
		System.out.println(camera);
	}
}
