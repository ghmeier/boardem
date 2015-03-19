package boardem.server;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.FilterRegistration.Dynamic;

import org.eclipse.jetty.servlets.CrossOriginFilter;

import io.dropwizard.Application;
import io.dropwizard.setup.*;
import boardem.server.resource.*;

public class BoardemApplication extends Application<BoardemConfiguration>
{
	
	public static final String FIREBASE_URL = "https://boardem.firebaseio.com";
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
		
    	final FilterRegistration.Dynamic cors = env.servlets().addFilter("CORS", CrossOriginFilter.class);

    	// Configure CORS parameters
    	cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
   	    cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

  	    // Add URL mapping
   	    cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    	    
		//Register resources and health checks here
		final SignUpResource signUp = new SignUpResource();
		final SignInResource signIn = new SignInResource();
		final EventResource event = new EventResource();
		final UsersResource users = new UsersResource();
		final SearchResource search = new SearchResource();
		final GameResource game = new GameResource();
		
		//configureCors(env);
		
		env.jersey().register(signUp);
		env.jersey().register(signIn);
		env.jersey().register(event);
		env.jersey().register(users);
		env.jersey().register(search);
		env.jersey().register(game);
	}
	
/*	  private void configureCors(Environment environment) {
		    Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
		    filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
		    filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
		    filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
		    filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
		    filter.setInitParameter("allowedHeaders", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
		    filter.setInitParameter("allowCredentials", "true");
	}*/
}