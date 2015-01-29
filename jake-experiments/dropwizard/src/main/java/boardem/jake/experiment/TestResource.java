package boardem.jake.experiment;


import com.google.common.base.Optional;
import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;

/*
 * Resource classes are used by multiple threads concurrently
 */

@Path("/test") //Tells Jersey that this resource is available at the URI /test
@Produces(MediaType.APPLICATION_JSON) //Tells Jersey that this resource produces representations which are application/json
public class TestResource
{
	private final String template;
	private final String defaultName;
	//counter is for a thread-safe way to generate unique IDs
	private final AtomicLong counter;
	
	public TestResource(String template, String defaultName)
	{
		this.template = template;
		this.defaultName = defaultName;
		this.counter = new AtomicLong();
	}
	
	@GET
	@Timed
	public Saying sayHello(@QueryParam("name") Optional<String> name)
	{
		final String value = String.format(template, name.or(defaultName));
		return new Saying(counter.incrementAndGet(), value);
	}
}
