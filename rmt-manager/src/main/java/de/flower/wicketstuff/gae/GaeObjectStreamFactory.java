package de.flower.wicketstuff.gae;

import org.apache.wicket.util.io.IObjectStreamFactory;

import java.io.*;

public class GaeObjectStreamFactory implements IObjectStreamFactory
{

	public ObjectInputStream newObjectInputStream(InputStream in) throws IOException
	{

		return new ObjectInputStream(in);
	}

	public ObjectOutputStream newObjectOutputStream(OutputStream out) throws IOException
	{

		return new ObjectOutputStream(out);
	}

}
