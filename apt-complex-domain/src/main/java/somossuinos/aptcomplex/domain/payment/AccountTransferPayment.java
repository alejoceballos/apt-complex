package somossuinos.aptcomplex.domain.payment;

import somossuinos.aptcomplex.domain.bank.Account;

/**
 * @author ceballos
 * @since 2015-07-20
 */
public class AccountTransferPayment extends Payment {

    private Account from;
    private Account to;
    private String transferId;

}
