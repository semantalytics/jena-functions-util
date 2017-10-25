package com.semantalytics.stardog.kibble.file;

import com.complexible.common.protocols.server.Server;
import com.complexible.stardog.Stardog;
import com.complexible.stardog.api.Connection;
import com.complexible.stardog.api.ConnectionConfiguration;
import com.complexible.stardog.api.admin.AdminConnection;
import com.complexible.stardog.api.admin.AdminConnectionConfiguration;
import com.complexible.stardog.protocols.snarl.SNARLProtocolConstants;
import com.google.common.io.Resources;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openrdf.query.TupleQueryResult;

import java.net.URI;

import static org.junit.Assert.*;

public class TestCat {

    private static Server SERVER = null;

    private static final String DB = "test";

    @BeforeClass
    public static void beforeClass() throws Exception {
        SERVER = Stardog.buildServer()
                .bind(SNARLProtocolConstants.EMBEDDED_ADDRESS)
                .start();

        final AdminConnection aConn = AdminConnectionConfiguration.toEmbeddedServer()
                .credentials("admin", "admin")
                .connect();

        try {
            if (aConn.list().contains(DB)) {
                aConn.drop(DB);
            }

            aConn.createMemory(DB);
        }
        finally {
            aConn.close();
        }
    }

    @AfterClass
    public static void afterClass() {
        if (SERVER != null) {
            SERVER.stop();
        }
    }

    @Test
    public void testSha1() throws Exception {
        final Connection aConn = ConnectionConfiguration.to(DB)
                .credentials("admin", "admin")
                .connect();

        try {

            URI file = Resources.getResource("test-target.txt").toURI();

            final String aQuery = "prefix file: <http://semantalytics.com/2016/04/ns/stardog/udf/file/> " +
                    "select ?cat where { bind(file:cat(<" + file.toString() + ">) as ?cat) }";

            final TupleQueryResult aResult = aConn.select(aQuery).execute();

            try {
                assertTrue("Should have a result", aResult.hasNext());

                final String aValue = aResult.next().getValue("cat").stringValue();

                assertEquals("Stardog test file", aValue);

                assertFalse("Should have no more results", aResult.hasNext());
            }
            finally {
                aResult.close();
            }
        }
        finally {
            aConn.close();
        }
    }
}