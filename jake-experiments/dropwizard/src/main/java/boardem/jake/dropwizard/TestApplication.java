package boardem.jake.dropwizard;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/*
The application class pulls everything together into a full application.
*/
public class TestApplication extends Application<TestConfiguration>
{
	public static void main(String args[]) throws Exception
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
		final TestResource res = new TestResource(
			config.getTemplate(),
			config.getDefaultName());
		final TemplateHealthCheck healthCheck = new TemplateHealthCheck(config.getTemplate());

		env.healthChecks().register("template", healthCheck);
		env.jersey().register(res);
	}
}