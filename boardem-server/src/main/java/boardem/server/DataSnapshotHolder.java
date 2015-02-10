package boardem.server;

import com.firebase.client.DataSnapshot;

/**
Holds a com.firebase.client.DataSnapshot for use in
anonymous inner classes.
*/
public class DataSnapshotHolder
{
	private DataSnapshot snapshot;

	public void setSnapshot(DataSnapshot snap)
	{
		snapshot = snap;
	}

	public DataSnapshot getSnapshot()
	{
		return snapshot;
	}
}