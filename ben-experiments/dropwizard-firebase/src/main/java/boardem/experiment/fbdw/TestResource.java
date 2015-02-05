package boardem.experiment.fbdw;

import com.google.common.base.Optional;
import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;

import javax.swing.JOptionPane;

@Path("/test") //Specifies the path to locate the resource at
@Produces(MediaType.APPLICATION_JSON) //Specifies the type of resource represented here
public class TestResource
{
    private final String template;
    private final String defaultName;
    private final int defaultYear;

    private Main main;

    //The counter is used to generate unique IDs for the sayings
    private final AtomicLong counter;

    public TestResource(String template, String defaultName, int defaultYear) 
    {
        this.template = template;
        this.defaultName = defaultName;
        this.defaultYear = defaultYear;
        this.counter = new AtomicLong();

        main = new Main();
    }

    @GET
    @Timed
    public Saying sayHello(@QueryParam("name") Optional<String> name, @QueryParam("year") Optional<Integer> year) 
    {

        //Generate the hello message from the template
        final String value = String.format(template, name.or(defaultName), year.or(defaultYear));
        int intVal = year.or(defaultYear);

        main.runFB(value, intVal);

        //Display the hello message in a dialog box
        JOptionPane.showMessageDialog(null, value, "Hello", JOptionPane.INFORMATION_MESSAGE);

        return new Saying(counter.incrementAndGet(), value);
    }
}