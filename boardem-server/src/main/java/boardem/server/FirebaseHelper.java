package boardem.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Contains static methods for writing/reading data from Firebase
 */
public class FirebaseHelper
{
	/**
	 * Writes data to the Firebase reference
	 * @param ref Firebase reference
	 * @param data data to be written
	 */
	public static <T, G> void writeData(Firebase ref, Map<T, G> data)
	{
		final CountDownLatch writeLatch = new CountDownLatch(1);
		
		ref.setValue(data, new Firebase.CompletionListener()
		{
			@Override
			public void onComplete(FirebaseError error, Firebase fb)
			{
				//Notify that the write completed
				writeLatch.countDown();
			}
		});
		
		//Wait for the write to complete
		try
		{
			writeLatch.await();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Reads data from Firebase
	 * @param ref Reference for the location to read from
	 * @return DataSnapshot containing the data from Firebase
	 */
	public static DataSnapshot readData(Firebase ref)
	{
		final CountDownLatch readLatch = new CountDownLatch(1);
		final DataSnapshotHolder holder = new DataSnapshotHolder();
		
		ref.addListenerForSingleValueEvent(new ValueEventListener()
		{
			@Override
			public void onDataChange(DataSnapshot snapshot)
			{
				//Notify that the read completed
				readLatch.countDown();
				
				//Store the snapshot for return
				holder.setSnapshot(snapshot);
			}
			
			@Override
			public void onCancelled(FirebaseError error)
			{
				System.out.printf("FirebaseError: %s\n", error.getMessage());
			}
		});
		
		//Wait for the read to complete
		try
		{
			readLatch.await();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		
		return holder.getSnapshot();
	}
	
	/**
	 * Converts from a map containing generic Firebase data to a map containing specific Java objects
	 * @param data Map received from Firebase
	 * @param type Type to convert to
	 * @return Map containing Java objects
	 */
	public static <T> Map<String, T> convertToObjectMap(@SuppressWarnings("rawtypes") Map<String, HashMap> data, Class<T> type)
	{
		HashMap<String, T> map = new HashMap<String, T>();
		ObjectMapper mapper = new ObjectMapper();
		
		//Iterate through each key and convert the associated object
		Iterator<String> keyIterator = data.keySet().iterator();
		while(keyIterator.hasNext())
		{
			String key = keyIterator.next();
			T t = null;
			
			try
			{
				String json = mapper.writeValueAsString(data.get(key));
				t = mapper.readValue(json, type);
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			
			map.put(key, t);
		}
		
		return map;
	}
}
