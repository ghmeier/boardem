package boardem.server;

import io.dropwizard.Application;
import io.dropwizard.setup.*;

public class BoardemApplication extends Application<BoardemConfiguration>
{
	public static void main(String[] args) throws Exception
	{
		new BoardemApplication().run(args);
	}

	@Override
	public String getName()
	{
		return "boardem";
	}

	@Override
	public void initialize(Bootstrap<BoardemConfiguration> bootstrap)
	{

	}

	@Override
	public void run(BoardemConfiguration config, Environment env)
	{
		//Register resources and health checks here
		final SignUpResource signUp = new SignUpResource();

		env.jersey().register(signUp);
	}
}