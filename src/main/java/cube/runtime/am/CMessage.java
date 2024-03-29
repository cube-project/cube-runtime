/*
 * Copyright 2012 Adele Team LIG (http://www-adele.imag.fr/)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cube.runtime.am;

import cube.runtime.metamodel.ManagedElement;

import java.io.Serializable;
import java.util.Date;
import java.util.Properties;


/**
 * Used to exchange messages between the different Cubes. 
 * 
 * Example:
 *   CMessage msg = new CMessage();
 *   msg.setTo("cube://localhost:2828/archetype/C0/0/1");
 *   msg.setFrom("...");
 *   msg.setReplyTo("...");
 *   msg.setBody("something...");
 *   
 *   getCubeInstance().getCommunicator().send(msg);
 *   
 * @author debbabi
 *
 */
public class CMessage implements Cloneable, Serializable {

	/**
	 * Define serialVersionUID for interoperability
	 */
	private static final long serialVersionUID = 1L;

	/** The message identifier. */
	private transient String id = null;

	/** the message object. */
	private String object = null;
	
	/** The message body. */
	private Object body = null;

	/** The message header headers. */
	private Properties headers = null;

	/** The message expiration time, by default 0 for infinite time-to-live. */
	private long expiration = 0;

	/** The message time stamp. */
	private long timestamp;
	
	/** The message correlation id */
	private long correlation = 0;	

	/** The send to destination's cube identifier. */
	private String to = null;
	
	/** The send to destination's cube identifierproperties. */
	private String from = null;

	/** The reply to destination's cube identifier. */
	private String replyTo = null;

    private ManagedElement attachment;

	/**
	 * Constructs a new <code>Message</code>
	 */
	public CMessage() {
		this.timestamp = new Date().getTime();
        this.attachment = new ManagedElement();
		
	}

	public void setTo(String to) {
		this.to = to;
	}
	
	public String getTo() {
		return this.to;
	}
	
	public void setFrom(String from) {
		this.from = from;
	}
	
	public String getFrom() {
		return this.from;
	}
	
	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}
	
	public String getReplyTo() {
		return this.replyTo;
	}
	
	/**
	 * Gets a header entry value.
	 * 
	 * @param name
	 *            The property name.
	 * @return
	 */
	public Object getHeader(String name) {
		if (headers != null) {
			return headers.get(name);
		}
		return null;
	}

	/**
	 * Sets a header property value. If the value is not a Java primitive object
	 * its string representation is used.
	 * 
	 * @param name
	 *            The property name.
	 * @param value
	 *            The property value.
	 * 
	 * @exception IllegalArgumentException
	 *                If the key name is illegal (null or empty string).
	 */
	public void addHeader(String name, Object value) {
		if (name == null || name.equals(""))
			throw new IllegalArgumentException("Invalid property name: " + name);
		if (headers == null)
			headers = new Properties();

		if (value instanceof Boolean || value instanceof Number
				|| value instanceof String) {
			headers.put(name, value);
		} else {
			headers.put(name, value.toString());
		}
	}

	public void setObject(String object) {
		this.object = object;
	}
	
	public String getObject() {
		return this.object;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	public Properties getHeaders() {
		return headers;
	}

	public void setHeaders(Properties headers) {
		this.headers = headers;
	}
	
	public long getExpiration() {
		return expiration;
	}
		
	public void setExpiration(long expiration) {
		this.expiration = expiration;
	}
	
	public long getTimestamp() {
		return timestamp;
	}

	public long getCorrelation() {
		return this.correlation;
	}
	
	public void setCorrelation(long correlation) {
		this.correlation = correlation;
	}
	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

    public void setAttachment(ManagedElement managedElement) {
        this.attachment = managedElement;
    }

    public ManagedElement getAttachment(){
        return this.attachment;
    }

	@Override
	public String toString() {
		String tmp = "\n";
		tmp += "from:" + this.getFrom() + "\n";
		tmp += "to:" + this.getTo() + "\n";
		tmp += "replyto:" + this.getReplyTo() + "\n";
		tmp += "correlation:" + this.getCorrelation() + "\n";
		if (headers != null) {
			for (Object key : headers.keySet()) {
				tmp += key + ":" + headers.get(key) + "\n";
			}
		}
		tmp += "object:" + this.getObject() + "\n";
		tmp += "body:\n" + this.getBody() + "\n";
        tmp += "attachment:" + this.getAttachment() + "\n";

        return tmp;
	}


}
