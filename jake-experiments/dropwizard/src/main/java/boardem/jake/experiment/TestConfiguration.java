package boardem.jake.experiment;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;

/*
 * Configuration class specifies environment-specific parameters
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

    @JsonProperty
    public void setDefaultName(String name) 
    {
        this.defaultName = name;
    }
}
