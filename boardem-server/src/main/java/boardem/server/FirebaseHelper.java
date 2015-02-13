package boardem.server;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

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
}
