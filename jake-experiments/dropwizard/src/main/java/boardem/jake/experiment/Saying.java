package boardem.jake.experiment;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

/*
 * A representation class. In this case, a representation of
 * a Hello World saying
 */
public class Saying
{
	private long id;
	
	@Length(max = 3)
	private String content;
	
	public Saying()
	{
		//Jackson deserialization
	}
	
	public Saying(long id, String content)
	{
		this.id = id;
		this.content = content;
	}
	
	@JsonProperty
	public long getId()
	{
		return id;
	}
	
	@JsonProperty
	public String getContent()
	{
		return content;
	}
}
