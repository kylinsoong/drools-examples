package org.kie.examples.phreak;

import java.util.LinkedList;
import java.util.List;

import org.kie.api.runtime.KieSession;
import org.kie.examples.phreak.domain.Account;
import org.kie.examples.phreak.domain.Person;
import org.kie.examples.phreak.domain.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class generating test facts and inserting them into given KieSession:
 * <ul>
 * <li>2 facts of type Person,</li>
 * <li>each having 10 Accounts,</li>
 * <li>each Account is associated with a number of Transactions (see below).</li>
 * </ul>
 * 
 * The number of transactions associated with each account is specified using
 * <em>phreak.examples.transactions</em> system property (default 10100).
 */
public class DataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataGenerator.class);

    private final Long transactionsPerAccount;

    private static final int ACCOUNTS_PER_PERSON = 10;

    private final List<Object> facts = new LinkedList<Object>();

    public DataGenerator(final long transactionsPerAccount) {
        if (transactionsPerAccount < 0) {
            this.transactionsPerAccount = Long.valueOf(System.getProperty("phreak.examples.transactions", "10100"));
        } else {
            this.transactionsPerAccount = transactionsPerAccount;
        }
        DataGenerator.LOGGER.info("Data generator will use {} transactions per account.", this.transactionsPerAccount);
    }

    public DataGenerator() {
        this(-1);
    }

    public void fillWithTestFacts() {
        final Person person1 = this.insertPerson("Carl");
        final Person person2 = this.insertPerson("Egon");

        this.insertPersonAccounts(person1, person2);
        this.insertPersonAccounts(person2, person1);
    }

    private Person insertPerson(final String name) {
        final Person person = new Person(name);
        this.facts.add(person);
        return person;
    }

    private void insertPersonAccounts(final Person owner, final Person partner) {
        for (long i = 0; i < DataGenerator.ACCOUNTS_PER_PERSON; i++) {
            this.insertAccount(i, owner, partner);
        }
    }

    private void insertAccount(final long id, final Person owner, final Person partner) {
        final Account account = new Account(id, owner);
        this.facts.add(account);

        final Account partnerAccount = new Account(id, partner);
        this.facts.add(partnerAccount);

        for (long t = 0; t < this.transactionsPerAccount; t++) {
            final Transaction transaction = new Transaction(account, partnerAccount, 10000);
            this.facts.add(transaction);
        }
    }

    public void insertInto(final KieSession session) {
        for (final Object o : this.facts) {
            session.insert(o);
        }
    }
}
