package de.flower.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.UrlResource;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * Class with some usefull helper methods related to I/O operations.
 *
 * @author flowerrrr
 */
public class IO {

    /**
     * The Constant log.
     */
    private final static Logger log = LoggerFactory.getLogger(IO.class);

    // prefix for all property keys
    /**
     * The Constant KEYPREFIX.
     */
    private static final String KEYPREFIX = IO.class.getName() + ".";

    /**
     * By setting this sytem property you can control the connection timeout that is used in the method {@link #loadResourceAsStream(String)}. If not set, the system default is used. Usefull for testing, if you don't want to wait for ages for a timeout to occur.
     */
    public static final String URLCONNECTION_TIMEOUT = KEYPREFIX + "urlconnection.timeout";

    /**
     * The Enum CharacterEncoding.
     */
    public enum CharacterEncoding {

        /**
         * The ASCII.
         */
        ASCII("US-ASCII"),

        /**
         * The IS o8859.
         */
        ISO8859("ISO-8859-1"),

        /**
         * The UT f8.
         */
        UTF8("UTF-8");

        /**
         * The encoding.
         */
        private String encoding;

        /**
         * Instantiates a new character encoding.
         *
         * @param encoding the encoding
         */
        CharacterEncoding(String encoding) {
            this.encoding = encoding;
        }

        /*
           * (non-Javadoc)
           *
           * @see java.lang.Enum#toString()
           */
        @Override
        public String toString() {
            return encoding;
        }

        /**
         * From string.
         *
         * @param encoding the encoding
         * @return the character encoding
         */
        public static CharacterEncoding fromString(String encoding) {
            for (CharacterEncoding ce : CharacterEncoding.values()) {
                if (ce.toString().equals(encoding)) {
                    return ce;
                }
            }
            throw new IllegalArgumentException("invalid encoding [" + encoding
                    + "]");
        }
    }

    ;

    private IO() {
    }

    /**
     * Prints formatted representations of objects to an PrintStream. Can be
     * used to dump objects recursively to log target.
     *
     * @param o  -
     *           Object to print
     * @param ps -
     *           printstream used to print the object (System.out for example).
     */
    public static void print(Object o, PrintStream ps) {
        ps.println(toString(o).replaceAll(", ", ",\n "));
    }

    /**
     * Returns formatted representations of objects.
     *
     * @param o -
     *          Object to print
     * @return - String holding formatted representation of the object.
     */
    public static String toString(Object o) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        pw.print(o);
        pw.flush();
        return sw.toString();
    }

    /**
     * Saves the content to a text file. This style of implementation throws all
     * exceptions to the caller.
     *
     * @param fileName    the file name
     * @param content     string to save
     * @param charsetName the charset name
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void saveToFile(String fileName, String content,
                                  CharacterEncoding charsetName) throws IOException {
        // declared here only to make visible to finally clause; generic
        // reference
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fileName), charsetName.toString()));
            writer.write(content);
            writer.close();
        } finally {
            // always close the streams otherwise we would leak memory.
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * Read content of a text file. This style of implementation throws all
     * exceptions to the caller.
     *
     * @param fileName    the file name
     * @param charsetName the charset name
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static String readFromFile(String fileName, String charsetName) {
        log.debug("Trying to load file [" + System.getProperty("user.dir")
                + "/" + fileName);
        try {
            InputStream in = new FileInputStream(fileName);
            return readFromInputStream(in, charsetName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Here is a useful method to convert from InputStream to String.
     *
     * @param in          InputStream
     * @param charsetName the charset name
     * @return string
     * @throws IOException Signals that an I/O exception has occurred.
     * @see <a
     *      href="http://www.bubble-media.com/cgi-bin/articles/archives/000038.html">here</a>
     */
    public static String readFromInputStream(java.io.InputStream in,
                                             String charsetName) {
        StringBuilder contents = new StringBuilder();

        if (in == null) {
            throw new IllegalArgumentException("inputstream cannot be null.");
        }

        try (BufferedReader input = new BufferedReader(new InputStreamReader(in, charsetName)))
        {

            String line = null; // not declared within while loop
            /*
                * readLine is a bit quirky : it returns the content of a line MINUS the
                * newline. it returns null only for the END of the stream. it returns
                * an empty String if two newlines appear in a row.
                */
            while ((line = input.readLine()) != null) {
                contents.append(line);
                contents.append(System.getProperty("line.separator"));
            }
            return contents.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads a stream into a byte arry. Does not work for reading inputstreams
     * based on external links.
     *
     * @param in the in
     * @return the byte[]
     * @throws IOException Signals that an I/O exception has occurred.
     * @see <a
     *      href="http://javaalmanac.com/egs/java.io/File2ByteArray.html">http://javaalmanac.com/egs/java.io/File2ByteArray.html</a>
     */
    public static byte[] readFromInputStream(InputStream in) throws IOException {
        if (in == null) {
            throw new IllegalArgumentException("inputstream cannot be null.");
        }

        // Get the size of the file
        long length = in.available();

        if (log.isDebugEnabled()) {
            log.debug("readFromInputStream(): Inputstream.available() = "
                    + length);
        }

        if (length > Integer.MAX_VALUE) {
            // Stream is too large
            throw new IOException("stream is too large to read.");
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int) length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = in.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read stream.");
        }

        if (log.isDebugEnabled()) {
            log.debug("readFromInputStream(): Read [" + bytes.length
                    + "] bytes from input stream.");
        }
        return bytes;
    }

    /**
     * Try to locate the resource within the classpath by name as is. Does not
     * require leading slash (fully qualified path). Method will use the
     * classloader of the method-invoker or if <code>frame</code> is greater
     * than zero it walks up the call-stack to find the appropiate object.
     *
     * @param resource classpath resource name
     * @param frame    the frame
     * @return the InputStream of the requested resource file or null if it does
     *         not exist
     */
    public static InputStream loadClasspathResourceAsStream(String resource,
                                                            int frame) {
        /**
         * The Class CurrentClassGetter.
         *
         * use this trick to get the class loader of the calling method.
         * this allows to load resources with relative paths to the loading
         * class file.
         */
        class CurrentClassGetter extends SecurityManager {

            public Class<?> getClass(int frame) {
                return getClassContext()[frame];
            }
        }
        CurrentClassGetter ccg = new CurrentClassGetter();
        Class<?> claz = ccg.getClass(2 + frame); // get class of method
        // invoker.
        return loadClasspathResourceAsStream(resource, claz);
    }

    /**
     * Load classpath resource as stream using class loader <code>claz</code>.
     * Method was introduced as workaround for javarebel error.
     *
     * @param resource the resource
     * @param claz     the claz used as loader.
     * @return the input stream
     */
    public static InputStream loadClasspathResourceAsStream(String resource, Class<?> claz) {

        if (resource == null) {
            throw new IllegalArgumentException("Resource id must not be <null>");
        }

        InputStream is = claz.getResourceAsStream(resource);
        if (is == null) {
            log.error("could not load resource [" + resource
                    + "] using classpath loader of class [" + claz.getName()
                    + "]");
        }
        return is;
    }

    /**
     * Convienience method for calling loadResourceAsStream without specifying
     * the frame of the call stack. Uses the classloader of the method-invoker.
     * Does not work when javarebel is used together with relative resource paths.
     *
     * @param resource the resource
     * @return the input stream
     */
    public static InputStream loadClasspathResourceAsStream(String resource) {
        return loadClasspathResourceAsStream(resource, 1);
    }

    /**
     * Wrapper for {@link #loadResourceAsStream(String, int)}. Uses the system
     * default timeout when using an URLConnection.
     *
     * @param resourcePath the resource path
     * @return the input stream
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static InputStream loadResourceAsStream(String resourcePath)
            throws IOException {
        int timeoutMs = -1; // if -1 the system default is used.
        String tmp = System.getProperty(URLCONNECTION_TIMEOUT, "" + timeoutMs);
        timeoutMs = Integer.parseInt(tmp);

        return loadResourceAsStream(resourcePath, timeoutMs, null);
    }

    /**
     * Load resource as stream.
     *
     * @param resourcePath the resource path
     * @param timeoutMs    connection timeout in milliseconds if &lt; 0 the java default
     *                     timeout is used.
     * @param postBody     the post body
     * @return the input stream
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static InputStream loadResourceAsStream(String resourcePath,
                                                   int timeoutMs, String postBody) throws IOException {
        InputStream is = null;

        log.debug("Fetching resource from " + resourcePath);
        switch (getResourceType(resourcePath)) {
            case URL: {
                URL u = (new UrlResource(resourcePath)).getURL();
                URLConnection con = u.openConnection();
                con.setUseCaches(false);
                if (timeoutMs >= 0) {
                    con.setConnectTimeout(timeoutMs);
                }
                if (postBody != null) {
                    // if requestProperties is set then post request to
                    // webserver
                    con.setDoOutput(true);
                    // OutputStreamWriter out = new
                    // OutputStreamWriter(con.getOutputStream());
                    PrintWriter out = new PrintWriter(con.getOutputStream());
                    out.println(postBody);
                    out.close();
                }
                is = con.getInputStream();
                break;
            }
            case URLFILE: {
                UrlResource urlResource = new UrlResource(resourcePath);
                is = new BufferedInputStream(new FileInputStream(urlResource
                        .getFile()));
                break;
            }
            case CLASSPATH: {
                // the url is a classpath resource
                is = loadClasspathResourceAsStream(resourcePath.replace(
                        "classpath:", ""));
                break;
            }
            case FILE: {
                // url is a file name
                is = new BufferedInputStream(new FileInputStream(resourcePath));
                break;
            }
            default:
                throw new IllegalArgumentException("unknown resourceType for url "
                        + resourcePath);
        }
        return is;
    }

    /**
     * The Enum ResourceType.
     */
    public enum ResourceType {

        /**
         * The URL.
         */
        URL,

        /**
         * The URLFILE.
         */
        URLFILE,

        /**
         * The FILE.
         */
        FILE,

        /**
         * The CLASSPATH.
         */
        CLASSPATH
    }

    ;

    /**
     * Gets the resource type.
     *
     * @param url the url
     * @return the resource type
     */
    public static ResourceType getResourceType(String url) {

        if (url == null || url.equals("")) {
            throw new IllegalArgumentException("invalid argument: url");
        }

        if (url.startsWith("http:") || url.startsWith("https:")) {
            return ResourceType.URL;
        } else if (url.startsWith("file:")) {
            return ResourceType.URLFILE;
        } else if (url.startsWith("classpath:")) {
            // the url is a classpath resource
            return ResourceType.CLASSPATH;
        } else {
            // url is a file name
            return ResourceType.FILE;
        }
    }

    /**
     * Try to locate the resource within the classpath by name as is. Does not
     * require leading slash (fully qualified path).
     *
     * @param resource resource name
     * @param frame    frame of callstack. used to retrieve the classloader of the
     *                 calling method.
     * @return the InputStream of the requested resource file or null if it does
     *         not exist
     */
    public static URL loadResource(String resource, int frame) {
        /**
         * The Class CurrentClassGetter.
         *
         * use this trick to get the class loader of the calling method.
         * this allows to load resources with relative paths to the loading
         * class file.
         */
        class CurrentClassGetter extends SecurityManager {

            public Class<?> getClass(int frame) {
                return getClassContext()[frame];
            }
        }
        CurrentClassGetter ccg = new CurrentClassGetter();
        Class<?> claz = ccg.getClass(2 + frame); // get class of method
        // invoker.

        if (resource == null) {
            throw new IllegalArgumentException("Resource id must not be <null>");
        }

        URL url = claz.getResource(resource);
        if (url == null) {
            log.error("could not load resource [" + resource
                    + "] using classpath loader of class [" + claz.getName()
                    + "]");
        }
        return url;
    }

    /**
     * Convienience method for calling loadResource without specifying the frame
     * of the call stack. Uses the classloader of the method-invoker.
     *
     * @param resource the resource
     * @return the URL
     */
    public static URL loadResource(String resource) {
        return loadResource(resource, 1);
    }

    /**
     * Encodes an input stream as base64 string.
     *
     * @param path the path
     * @param force the force
     *
     * @return the base64 encoded input stream as string
     */
/*
	public static String classPathResourceToBase64(
			ClassPathResource classPathResource) {
		try {
			final int chunkSize = 2048;
			byte[] buf = new byte[chunkSize];
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream(
					chunkSize);
			int count;

			InputStream inputStream = classPathResource.getInputStream();
			while ((count = inputStream.read(buf)) != -1) {
				byteStream.write(buf, 0, count);
			}

			return new String(Base64.encodeBase64(byteStream.toByteArray()));
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}
*/


}
