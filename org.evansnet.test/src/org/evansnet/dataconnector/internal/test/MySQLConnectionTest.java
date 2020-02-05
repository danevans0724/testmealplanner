package org.evansnet.dataconnector.internal.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.powermock.api.easymock.PowerMock.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.evansnet.dataconnector.internal.core.DBType;
import org.evansnet.dataconnector.internal.dbms.ConnectionStrFactory;
import org.evansnet.dataconnector.internal.dbms.MySQLConnection;

import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.logging.Logger;

@PowerMockIgnore({"javax.net.ssl.*","javax.security.*"})
@RunWith(PowerMockRunner.class)
@PrepareForTest({ MySQLConnection.class, DriverManager.class, ConnectionStrFactory.class})
public class MySQLConnectionTest extends BaseTestConfig {
	
	public static final Logger mySQLConnectionTestLogger = Logger.getLogger("MySQLConnectionTestLogger");

	MySQLConnection dbInst;
	
	// Format: jdbc:mysql://<<Host>>:3306/<<database>>
	String expected = "jdbc:mysql://localhost:1433/DCEDB01?user=Dan&password=thePwd";


	@Before
	public void setUp() throws Exception {
		super.setUp();
		dbInst = new MySQLConnection(hostMock);
		dbInst.getHost().setHostName("localhost");
		dbInst.getHost().setPort(1433);
		dbInst.setDatabaseName("DCEDB01");
		dbInst.setCredentials(credentialsMock);	
	}

	@Test
	public void testBuildConnectionString() {
		try {
			String result = dbInst.buildConnectionString(DBType.MySQL);
			assertTrue(result.equals(expected));
			PowerMock.verify(connectionStringFactoryMock);
		} catch (Exception e) {
			fail("Build connection string test failed! Cause: " + e.getCause());
		}		
	}

	@Test
	public void testGetConnection() {
		try {
			Connection testConnection = dbInst.connect(expected);
			Connection theSetConnection = dbInst.getConnection();
			assertTrue(testConnection == connectionMock);
			assertTrue(theSetConnection == connectionMock);
			verify(driverManagerMock);
		} catch (SQLException e) {
			fail("Test of MySQLConnector.connect() failed! SQLException thrown: " + e.getMessage());
		} catch (Exception e) {
			fail("Test of MySQLConnection.connect() failed! A general exception was thrown: " + e.getMessage());
		}
	}

}
