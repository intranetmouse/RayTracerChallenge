package org.intranet.graphics.raytrace.steps;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

@RunWith(value = Cucumber.class)
@CucumberOptions(monochrome = true, plugin = "pretty", strict = true,
	glue = "classpath:/org/intranet/graphics/raytrace/steps",
	features = "classpath:/features", snippets = SnippetType.CAMELCASE)
public class CucumberTests
{

}
