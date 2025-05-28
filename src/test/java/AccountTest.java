
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class AccountTest {

  /** US 1: */
  @Test
  public void givenAmount_whenTryingToDepositToEmptyAccount_thenShouldUpdateAccountBalanceAndAccountStatements() {
    //given
    Account account = Account.createAccount();
    Amount money = new Amount(BigDecimal.valueOf(1500), CurrencyEnum.EURO);

    //when
    account.deposit(money);

    //then
    Assert.assertEquals(1, account.getAccountStatements().size());
    Assert.assertEquals(money, account.getBalance());
  }


  @Test
  public void givenAmount_whenTryingToDepositToAccount_thenShouldUpdateAccountBalanceAndAccountStatements() {
    //given
    BigDecimal inputValue = BigDecimal.valueOf(2500);
    Account account = mockAccountWithAccountStatements();
    Amount money = new Amount(inputValue, CurrencyEnum.EURO);
    Amount expectedMockedAfterBalance = new Amount(BigDecimal.valueOf(3500), CurrencyEnum.EURO);

    //when
    account.deposit(money);

    //then
    Assert.assertEquals(4, account.getAccountStatements().size());
    Assert.assertEquals(expectedMockedAfterBalance, account.getBalance());
  }

  /** US 2: */
  @Test
  public void givenAmount_whenTryingToWithdrawPossibleAmount_thenShouldUpdateAccountBalanceAndAccountStatementsAndReturnWithdrawAmount() {
    //given
    BigDecimal inputValue = BigDecimal.valueOf(200);
    Account account = mockAccountWithAccountStatements();
    Amount money = new Amount(inputValue, CurrencyEnum.EURO);
    Amount expectedMockedAfterBalance = new Amount(BigDecimal.valueOf(800), CurrencyEnum.EURO);

    //when
    Amount amount = account.withdraw(money);

    //then
    Assert.assertEquals(4, account.getAccountStatements().size());
    Assert.assertEquals(expectedMockedAfterBalance, account.getBalance());
    Assert.assertEquals(money, amount);
  }

  @Test
  public void givenAmount_whenTryingToWithdrawTooBigAmount_thenShouldReturnZeroAmountAndDoNotUpdateAccountBalanceNorAccountStatements() {
    //given
    BigDecimal inputValue = BigDecimal.valueOf(10_000);
    Account account = mockAccountWithAccountStatements();
    Amount money = new Amount(inputValue, CurrencyEnum.EURO);
    Amount expectedMockedAfterBalance = new Amount(BigDecimal.valueOf(1000), CurrencyEnum.EURO);

    //when
    Amount amount = account.withdraw(money);

    //then
    Assert.assertEquals(3, account.getAccountStatements().size());
    Assert.assertEquals(expectedMockedAfterBalance, account.getBalance());
    Assert.assertEquals(Amount.zero(CurrencyEnum.EURO), amount);
  }


  /** US 3: */
  @Test
  public void givenNoAccountStatements_whenTryingCheckAccountOperations_thenShouldNotPrintAnyStatement() {
    //given
    Account account = Account.createAccount();

    //when
    account.printStatements();

    //then
    Assert.assertEquals(0, account.getAccountStatements().size());
  }

  @Test
  public void givenAccountStatementsOnAccount_whenTryingToCheckOperations_thenShouldPrintAllStatements() {
    //given
    Account account = mockAccountWithAccountStatements();

    //when
    account.printStatements();

    //then
    Assert.assertEquals(3, account.getAccountStatements().size());
  }


  /* @Mocks@ */
  Account mockAccountWithAccountStatements() {
    return new Account(mockAccountStatements(), new Amount(BigDecimal.valueOf(1000), CurrencyEnum.EURO));
  }

  List<AccountStatement> mockAccountStatements() {
    AccountStatement accountStatement1 = AccountStatement.create(
        AccountOperationEnum.DEPOSIT,
        new Amount(BigDecimal.valueOf(500), CurrencyEnum.EURO),
        new Amount(BigDecimal.valueOf(500), CurrencyEnum.EURO)
    );
    AccountStatement accountStatement2 = AccountStatement.create(
        AccountOperationEnum.WITHDRAWAL,
        new Amount(BigDecimal.valueOf(300), CurrencyEnum.EURO),
        new Amount(BigDecimal.valueOf(200), CurrencyEnum.EURO)
    );
    AccountStatement accountStatement3 = AccountStatement.create(
        AccountOperationEnum.DEPOSIT,
        new Amount(BigDecimal.valueOf(800), CurrencyEnum.EURO),
        new Amount(BigDecimal.valueOf(1000), CurrencyEnum.EURO)
    );
    List<AccountStatement> resultList = new ArrayList<>();
    resultList.add(accountStatement1);
    resultList.add(accountStatement2);
    resultList.add(accountStatement3);
    return resultList;
  }

}
