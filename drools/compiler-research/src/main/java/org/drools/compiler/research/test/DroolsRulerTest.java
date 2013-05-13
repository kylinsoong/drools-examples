package org.drools.compiler.research.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Formatter;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderResult;
import org.drools.builder.KnowledgeBuilderResults;
import org.drools.builder.ResultSeverity;
import org.drools.compiler.research.DroolsRuler;
import org.drools.compiler.research.Model;
import org.drools.compiler.research.NameValue;
import org.drools.definition.KnowledgePackage;
import org.drools.definition.rule.Rule;
import org.drools.io.Resource;
import org.drools.io.ResourceFactory;
import org.junit.Before;
import org.junit.Test;

public class DroolsRulerTest {

	private DroolsRuler ruler;
	
	@Before
	public void begin() {
		ruler = new DroolsRuler();
	}
	
	@Test
	public void testThreeDrls_TwoDuplicatesOneUnique() throws Exception {
		out("testThreeDrls_TwoDuplicatesOneUnique():\n");

		List<Resource> drlResources = generateRuleResources("RuleOne.drl", "RuleTwo.drl", "RuleThree.drl");

		out("Compiling rules...");
		ruler.compile(drlResources);		// Compile the three rules
		out("...compilation complete");

		KnowledgeBuilder knowledgeBuilder = ruler.getKnowledgeBuilder();

		out("knowledgeBuilder.hasErrors() = \"%1$s\"", knowledgeBuilder.hasErrors());
		out("knowledgeBuilder.hasResults(ResultSeverity.ERROR, ResultSeverity.WARNING, ResultSeverity.INFO) = \"%1$s\"", knowledgeBuilder.hasResults(ResultSeverity.ERROR, ResultSeverity.WARNING, ResultSeverity.INFO));

		/*
		 * Here is the heart of the issue. The result has been stashed in the collection with a *null* ResultSeverity.
		 */
		out("knowledgeBuilder.hasResults((ResultSeverity)null) = %1$s", knowledgeBuilder.hasResults((ResultSeverity)null));
		KnowledgeBuilderResults knowledgeBuilderResults = knowledgeBuilder.getResults((ResultSeverity)null);
		out("knowledgeBuilderResults.size() = %1$s", knowledgeBuilderResults.size());
		assertEquals(1, knowledgeBuilderResults.size());

		KnowledgeBuilderResult knowledgeBuilderResult = knowledgeBuilderResults.iterator().next();
		out("knowledgeBuilderResult.getSeverity() = %1$s", knowledgeBuilderResult.getSeverity());
		assertEquals(null, knowledgeBuilderResult.getSeverity());
		out("knowledgeBuilderResult.getMessage() = \"%1$s\"", knowledgeBuilderResult.getMessage());
		assertEquals("Rule name Duplicated Rule Name already exists in package  org.drools.compiler.research", knowledgeBuilderResult.getMessage());
		Collection<KnowledgePackage> knowledgePackages = knowledgeBuilder.getKnowledgePackages();
		out("knowledgePackages.size() = %1$s", knowledgePackages.size());
		assertEquals(1, knowledgePackages.size());
		KnowledgePackage knowledgePackage = knowledgePackages.iterator().next();

		out("knowledgePackage.getRules().size() = %1$s", knowledgePackage.getRules().size());

		Collection<Rule> rules = knowledgePackage.getRules();

		final List<String> ruleNames = new ArrayList<String>();
		ruleNames.add("Unique Rule Name");
		ruleNames.add("Duplicated Rule Name");

		for (Rule rule : rules) {
			out("Rule name = \"%1$s\"", rule.getName());
			assertTrue(ruleNames.contains(rule.getName()));
		}

		assertEquals(2, rules.size());

		out("Firing all rules with Model...");
		Model model = new Model();
		ruler.run(model);
		out("...all rules fired");

		final List<String> ruleValues = new ArrayList<String>();
		ruleValues.add("From RuleOne");
		ruleValues.add("From RuleTwo");
		ruleValues.add("From RuleThree");

		/*
		 * Finally, from the rule execution results, we find that, of the rules with duplicate names, the last one declared wins.
		 */
		for (NameValue nameValue : model.getNameValues()) {
			assertTrue(ruleNames.contains(nameValue.getName()));
			assertTrue(ruleValues.contains(nameValue.getValue()));
			out("Model: rule name = \"%1$s\"   rule value = \"%2$s\"", nameValue.getName(), nameValue.getValue());
		}

		out("\nTest complete.");
	}

	private List<Resource> generateRuleResources(String... ruleFileNames) {
		List<Resource> drlResources = new ArrayList<Resource>();
		for (String ruleFileName : ruleFileNames) {
			out("Generating resource for rule \"%1$s\"", ruleFileName);
			Resource drlResource = ResourceFactory.newClassPathResource(ruleFileName, this.getClass().getClassLoader());
			drlResources.add(drlResource);
		}
		return drlResources;
	}

	private void out(String pattern, Object... parameters) {
		System.err.println(new Formatter().format(pattern, parameters));
	}
}
