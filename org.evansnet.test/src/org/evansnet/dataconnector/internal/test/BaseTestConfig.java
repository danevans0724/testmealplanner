package org.evansnet.dataconnector.internal.test;

import org.junit.After;
import org.junit.Before;
import static org.powermock.api.easymock.PowerMock.createNiceMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import java.security.cert.Certificate;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.easymock.EasyMock;
import org.evansnet.dataconnector.internal.core.Credentials;
import org.evansnet.dataconnector.internal.core.DBMS;
import org.evansnet.dataconnector.internal.core.Host;
import org.evansnet.dataconnector.internal.dbms.ConnectionStrFactory;
import org.powermock.api.easymock.PowerMock;
import org.powermock.api.easymock.annotation.Mock;


public class BaseTestConfig {
	
	//Mocks
	@Mock public Host hostMock;
	@Mock public DBMS dbmsMock;
	@Mock public Credentials credentialsMock;
	@Mock public Connection connectionMock;
	@Mock public ConnectionStrFactory connectionStringFactoryMock;
	@Mock public DriverManager driverManagerMock;
	@Mock public Certificate certificateMock;
	@Mock public Properties parmlistMock;

	String pwd = "thePwd";
	

	@Before
	public void setUp() throws Exception {
		
		//Mocks construction
		hostMock = createNiceMock(Host.class);
		credentialsMock = createNiceMock(Credentials.class);
		connectionMock = createNiceMock(Connection.class);
		connectionStringFactoryMock = PowerMock.createNiceMock(ConnectionStrFactory.class);
		PowerMock.mockStaticPartial(DriverManager.class, "getConnection", String.class);
		certificateMock = createNiceMock(Certificate.class);
		parmlistMock = createNiceMock(Properties.class);
		
		//Mock expects
		//Host
		EasyMock.expect(hostMock.getHostName()).andReturn("localhost").anyTimes();
		EasyMock.expect(hostMock.getPort()).andReturn(1433).anyTimes();
		hostMock.setHostName(EasyMock.anyString());
		expectLastCall();
		hostMock.setPort(EasyMock.anyInt());
		expectLastCall();
		
		//DriverManager
		EasyMock.expect(DriverManager.getConnection(EasyMock.anyString())).andReturn(connectionMock);
		
		//Credentials
		EasyMock.expect(credentialsMock.getUserID()).andReturn("Dan".toCharArray()).anyTimes();
		EasyMock.expect(credentialsMock.getPassword(EasyMock.anyObject())).andReturn(pwd.toCharArray()).anyTimes();

		PowerMock.replayAll();
	}

	@After
	public void tearDown() throws Exception {
		hostMock = null;
		credentialsMock = null;
		connectionMock = null;
		certificateMock = null;
		connectionStringFactoryMock = null;
		driverManagerMock = null;
		parmlistMock = null;
	}
}
