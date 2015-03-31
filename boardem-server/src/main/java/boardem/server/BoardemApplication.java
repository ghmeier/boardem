package boardem.server;

import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import boardem.server.resource.EventResource;
import boardem.server.resource.ExperienceResource;
import boardem.server.resource.GameResource;
import boardem.server.resource.MessageResource;
import boardem.server.resource.SearchResource;
import boardem.server.resource.SignInResource;
import boardem.server.resource.SignUpResource;
import boardem.server.resource.UsersResource;

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
		Scheduler sched = null;
		try
		{
			sched = StdSchedulerFactory.getDefaultScheduler();
			JobDetail job = newJob(UpdateEventJob.class)
					.withIdentity("updateEventJob", "eventGroup")
					.build();
			Trigger trigger = newTrigger()
					.withIdentity("updateEventTrigger", "eventGroup")
					.startNow()
					.withSchedule(dailyAtHourAndMinute(0,0)) //Once a day at midnight
					.build();
			sched.scheduleJob(job, trigger);
		}
		catch (SchedulerException e)
		{
			e.printStackTrace();
		}
		
		//Manage the Quartz job scheduler
		QuartzManager quartzManager = new QuartzManager(sched);
		env.lifecycle().manage(quartzManager);
		
    	final FilterRegistration.Dynamic cors = env.servlets().addFilter("CORS", CrossOriginFilter.class);

    	// Configure CORS parameters
    	cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
   	    cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

  	    // Add URL mapping
   	    cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

   	    //Load the game cache
   	    GameCache.load();
   	    
		//Register resources and health checks here
		final SignUpResource signUp = new SignUpResource();
		final SignInResource signIn = new SignInResource();
		final EventResource event = new EventResource();
		final UsersResource users = new UsersResource();
		final SearchResource search = new SearchResource();
		final GameResource game = new GameResource();
		final MessageResource messages = new MessageResource();
		final ExperienceResource experience = new ExperienceResource();
		
		//configureCors(env);
		
		env.jersey().register(signUp);
		env.jersey().register(signIn);
		env.jersey().register(event);
		env.jersey().register(users);
		env.jersey().register(search);
		env.jersey().register(game);
		env.jersey().register(messages);
		env.jersey().register(experience);
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