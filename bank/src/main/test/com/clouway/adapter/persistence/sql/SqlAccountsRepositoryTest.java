package com.clouway.adapter.persistence.sql;

import com.clouway.adapter.persistence.sql.util.DatabaseCleaner;
import com.clouway.core.InsufficientAvailability;
import com.clouway.core.User;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class SqlAccountsRepositoryTest {
  private MysqlConnectionPoolDataSource dataSource;

  @Before
  public void setUp() {
    dataSource = new MysqlConnectionPoolDataSource();
    dataSource.setURL("jdbc:mysql://localhost:3306/banktests");
    dataSource.setUser("root");
    dataSource.setPassword("clouway.com");
    new DatabaseCleaner(dataSource, "users", "sessions", "accounts").cleanUp();
  }

  @Test
  public void registerNewAccount() {
    SqlAccountsRepository accountsRepository = new SqlAccountsRepository(dataSource);
    User user = new User("ivan", "ivan123", "ivan@abv.bg", "ivan123", "sliven", 23);
    accountsRepository.register(user);
    Double balance = accountsRepository.getBalance(user);
    assertThat(balance,is(equalTo(0.0)));
  }

  @Test
  public void depositToAccount() {
    SqlAccountsRepository accountsRepository = new SqlAccountsRepository(dataSource);
    User user = new User("ivan", "ivan123", "ivan@abv.bg", "ivan123", "sliven", 23);
    accountsRepository.register(user);

    accountsRepository.deposit(user,new Double(20));
    Double balance = accountsRepository.getBalance(user);

    assertThat(balance,is(equalTo(20.0)));
  }

  @Test (expected = InsufficientAvailability.class)
  public void withdrawFromZeroAmount() throws InsufficientAvailability {
    SqlAccountsRepository accountsRepository = new SqlAccountsRepository(dataSource);
    User user = new User("ivan", "ivan123", "ivan@abv.bg", "ivan123", "sliven", 23);
    accountsRepository.register(user);
    accountsRepository.withdraw(user,new Double(20));
  }

  @Test
  public void withdrawFromNotZeroAmount() throws InsufficientAvailability {
    SqlAccountsRepository accountsRepository = new SqlAccountsRepository(dataSource);
    User user = new User("ivan", "ivan123", "ivan@abv.bg", "ivan123", "sliven", 23);
    accountsRepository.register(user);

    accountsRepository.deposit(user,new Double(30));
    Double balance = accountsRepository.getBalance(user);
    assertThat(balance,is(equalTo(30.0)));

    accountsRepository.withdraw(user,new Double(20));
    Double newBalance = accountsRepository.getBalance(user);
    assertThat(newBalance,is(equalTo(10.0)));
  }
}