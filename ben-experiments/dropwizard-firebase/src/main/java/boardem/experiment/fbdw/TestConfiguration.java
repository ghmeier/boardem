package boardem.experiment.fbdw;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.*;
import javax.validation.constraints.NotNull;

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

	@NotNull
	private int defaultYear = 1492;

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

	@JsonProperty
	public int getDefaultYear()
	{
		return defaultYear;
	}
}