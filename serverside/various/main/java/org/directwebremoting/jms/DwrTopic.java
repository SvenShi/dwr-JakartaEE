package org.directwebremoting.jms;

import jakarta.jms.JMSException;
import jakarta.jms.Topic;

/**
 * A {@link Topic} for DWR
 * @author Joe Walker [joe at getahead dot ltd dot uk]
 */
public class DwrTopic implements Topic
{
    /**
     * @see jakarta.jms.Session#createTopic(String)
     */
    public DwrTopic(String topicName)
    {
        this.topicName = topicName;
    }

    /* (non-Javadoc)
     * @see javax.jms.Topic#getTopicName()
     */
    public String getTopicName() throws JMSException
    {
        return topicName;
    }

    /**
     * The name of this topic
     */
    private String topicName;
}
