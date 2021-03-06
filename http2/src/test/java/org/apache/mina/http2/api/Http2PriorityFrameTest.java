/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *  
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License. 
 *  
 */
package org.apache.mina.http2.api;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.nio.ByteBuffer;

import org.apache.mina.http2.Http2Test;
import org.apache.mina.http2.TestMessages;
import org.apache.mina.http2.impl.Http2Connection;
import org.junit.Test;

/**
 * 
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class Http2PriorityFrameTest extends Http2Test {

    @Test
    public void decodeNoExclusive() {
        Http2Connection connection = new Http2Connection();
        ByteBuffer buffer = ByteBuffer.wrap(TestMessages.PRIORITY_NO_EXCLUSIVE_MODE_BUFFER);
        Http2PriorityFrame frame = (Http2PriorityFrame) connection.decode(buffer);
        assertNotNull(frame);
        assertEquals(5, frame.getLength());
        assertEquals(2, frame.getType());
        assertEquals(0x00, frame.getFlags());
        assertEquals(32, frame.getStreamID());
        assertEquals(256, frame.getStreamDependencyID());
        assertFalse(frame.getExclusiveMode());
        assertEquals(2, frame.getWeight());
    }
    
    @Test
    public void encodeNoExclusive() {
        Http2PriorityFrame frame = TestMessages.PRIORITY_NO_EXCLUSIVE_MODE_FRAME;
        assertArrayEquals(TestMessages.PRIORITY_NO_EXCLUSIVE_MODE_BUFFER, toByteArray(frame.toBuffer()));
    }

    @Test
    public void decodeExclusive() {
        Http2Connection connection = new Http2Connection();
        ByteBuffer buffer = ByteBuffer.wrap(TestMessages.PRIORITY_EXCLUSIVE_MODE_BUFFER);
        Http2PriorityFrame frame = (Http2PriorityFrame) connection.decode(buffer);
        assertNotNull(frame);
        assertEquals(5, frame.getLength());
        assertEquals(2, frame.getType());
        assertEquals(0x00, frame.getFlags());
        assertEquals(32, frame.getStreamID());
        assertEquals(256, frame.getStreamDependencyID());
        assertTrue(frame.getExclusiveMode());
        assertEquals(2, frame.getWeight());
    }
 
    @Test
    public void encodeExclusive() {
        Http2PriorityFrame frame = TestMessages.PRIORITY_EXCLUSIVE_MODE_FRAME;
        assertArrayEquals(TestMessages.PRIORITY_EXCLUSIVE_MODE_BUFFER, toByteArray(frame.toBuffer()));
    }
}
