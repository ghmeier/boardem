package boardem.experiment.fbdw;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

/*
The Saying class is a representation of a JSON object representation of a hello world saying
{
	"id":1,
	"content":"Hi!"
}
*/
public class Saying
{
	private long id;

	@Length(max = 3)
	private String content;

	public Saying() { }

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