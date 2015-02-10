package boardem.server;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.firebase.client.*;

import javax.swing.JOptionPane;

@Path("/signup")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SignUpResource
{
	@GET
	public String getUsers()
	{
		Firebase fb = new Firebase("https://boardem.firebaseio.com/users");
		fb.addListenerForSingleValueEvent(new ValueEventListener()
		{
			@Override
			public void onDataChange(DataSnapshot snapshot)
			{
				JOptionPane.showMessageDialog(null, snapshot.getValue().toString(), "Hey", JOptionPane.INFORMATION_MESSAGE);
			}

			@Override
			public void onCancelled(FirebaseError firebaseError)
			{

			}
		});

		try
		{
			Thread.sleep(5000);
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}

		return "users";
	}

	@POST
	@Path("username")
	/**
	Adds a new user using a username/password combo instead of Facebook login
	@return 
	*/
	public int addUser(@QueryParam("username") String username, @QueryParam("password") String password)
	{
		return UserCreator.addUser(username, password);
	}
}