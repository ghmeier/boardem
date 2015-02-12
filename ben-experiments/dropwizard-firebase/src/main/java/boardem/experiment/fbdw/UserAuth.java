package boardem.experiment.fbdw;

import com.firebase.client.Firebase;
import com.firebase.security.token;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

public class UserAuth
{
	private Firebase fb;

	// public static void main(String[] args)
	// {
	// 	//Run the "run" method of "Main" at program start
	// 	new Main().run("ten", 1500);
	// }

	public UserAuth()
	{
		//Create new Firebase object to interface with later
		//JOptionPane.showMessageDialog(null, "Main()", "Hello", JOptionPane.INFORMATION_MESSAGE);

		fb = new Firebase("https://glaring-heat-4154.firebaseio.com/");

		Object uid = "Ben";
		Object password = "correcthorsebatterystaple";

		authUser(uid, password);
	}

	public String generateToken (Object uid, Object password) {
		Map<String, Object> payload = new HashMap<String, Object>();
			payload.put("uid", uid);
			payload.put("password", password);

		TokenGenerator tokengenerator = new TokenGenerator("PUT FIREBASE SECRET HERE");
		String token = tokengenerator.createtoken(payload);
		return token;
	}

	public void authUser(Object uid, Object password) {
		fb.authWithCustomToken(generateToken(uid, password), new Firebase.AuthResultHandler() {
			@Override
			public void onAuthenticationError(FirebaseError error) {
				System.err.println("Login failed! " + error.getMessage());
			}

			@Override
			public void onAuthenticated(AuthData authData) {
				System.out.println("Login Succeeded!");
			}
		});
	}
}