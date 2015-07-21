package somossuinos.aptcomplex.domain.finance.payment;

import somossuinos.aptcomplex.domain.finance.bank.Account;

/**
 * @author ceballos
 * @since 2015-07-20
 */
public class CheckPayment extends Payment {

    private Account account;
    private String series;
    private String number;
}
