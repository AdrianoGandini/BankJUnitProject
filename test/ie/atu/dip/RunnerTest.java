package ie.atu.dip;


import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;



@Suite
@SelectClasses({
	BankImplTest.class,
	AccountRepositoryImplTest.class,
	AccountTest.class,
	TransactionServiceImplTest.class,
	LoanServiceImplTest.class
	
})
class RunnerTest {
}
