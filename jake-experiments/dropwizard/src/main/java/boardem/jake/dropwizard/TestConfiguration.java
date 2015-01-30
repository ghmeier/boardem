package boardem.jake.dropwizard;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

/*
The configuration subclass is where environment specific settings are stored.
In this case, its just defining the template and default name for the message to be displayed
*/
public class TestConfiguration extends Configuration
{
	@NotEmpty
	private String template;

	@NotEmpty
	private String defaultName = "Stranger";

	@JsonProperty
	public String getTemplate()
	{
		return template;
	}

	@JsonProperty
	public void setTemplate(String template)
	{
		this.template = template;
	}

	@JsonProperty
	public String getDefaultName()
	{
		return defaultName;
	}
}