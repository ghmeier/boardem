package boardem.jake.experiment;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class TestApplication extends Application<TestConfiguration>
{
	public static void main(String[] args) throws Exception
	{
		new TestApplication().run(args);
	}
	
	@Override
	public String getName()
	{
		return "test";
	}
	
	@Override
	public void initialize(Bootstrap<TestConfiguration> bs)
	{
		
	}
	
	@Override
	public void run(TestConfiguration config, Environment env)
	{
		//Create and register the resource and health check
		final TestResource resource = new TestResource(
				config.getTemplate(),
				config.getDefaultName());
		final TemplateHealthCheck healthCheck = new TemplateHealthCheck(config.getTemplate());
		
		env.healthChecks().register("template", healthCheck);
		env.jersey().register(resource);
	}
}
