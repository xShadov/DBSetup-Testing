package com.shadov.test;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import com.shadov.test.database.access.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
		TransactionalTestExecutionListener.class})
@TestPropertySource(locations="classpath:test.properties")
public class UserDaoTest {
	private static DbSetupTracker dbSetupTracker = new DbSetupTracker();

	@Before
	public void prepare() throws Exception {
		Operation operation =
				sequenceOf(
						CommonOperations.DELETE_ALL,
						insertInto("USER")
								.columns("ID", "NICK", "NAME")
								.values(1L, "Shadov", "Amazon")
								.values(2L, "Wtf", "Dwa")
								.build());
		DbSetup dbSetup = new DbSetup(new DriverManagerDestination("jdbc:h2:mem:AZ;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE", "sa", ""), operation);

		dbSetupTracker.launchIfNecessary(dbSetup);
	}

	@Autowired
	private UserRepository repository;

	@Test
	public void findByNick() {
		assert !repository.findByNick("Shadov").isEmpty();
	}
}
