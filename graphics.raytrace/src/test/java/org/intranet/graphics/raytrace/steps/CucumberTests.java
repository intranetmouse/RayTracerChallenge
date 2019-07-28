package org.intranet.graphics.raytrace.steps;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;


@RunWith(value = Cucumber.class)
@CucumberOptions(monochrome = true, plugin = {"pretty", "summary"},
	strict = true,
	glue = "classpath:/org/intranet/graphics/raytrace/steps",
	features = "classpath:/features", snippets = SnippetType.CAMELCASE)
public class CucumberTests
{

}
