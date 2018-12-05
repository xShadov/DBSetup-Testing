package com.shadov.test.dbsetup;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import com.shadov.test.dbsetup.access.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;

@RunWith(SpringRunner.class)
@DataJpaTest
@PropertySource("classpath:application-test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserDaoTest {

	@Value("${spring.datasource.url}")
	private String datasourceUrl;

	@Value("${spring.datasource.username}")
	private String datasourceUsername;

	@Value("${spring.datasource.password}")
	private String datasourcePassword;

	private DbSetupTracker dbSetupTracker = new DbSetupTracker();

	@Before
	public void prepare() {
		Operation operation =
				sequenceOf(
						CommonOperations.DELETE_ALL,
						insertInto("USER")
								.columns("ID", "NICK", "NAME", "LAST_NAME")
								.values(1L, "Johnny", "John", "Smith")
								.values(2L, "Another", "Something", "Testing")
								.build()
				);

		final DbSetup dbSetup = new DbSetup(new DriverManagerDestination(datasourceUrl, datasourceUsername, datasourcePassword), operation);
		dbSetupTracker.launchIfNecessary(dbSetup);
	}

	@Autowired
	private UserRepository repository;

	@Test
	public void findByNick() {
		assert !repository.findByNick("Johnny").isEmpty();
	}
}
