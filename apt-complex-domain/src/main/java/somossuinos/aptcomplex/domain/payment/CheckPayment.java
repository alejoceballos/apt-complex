package somossuinos.aptcomplex.domain.payment;

import somossuinos.aptcomplex.domain.bank.Account;

/**
 * @author ceballos
 * @since 2015-07-20
 */
public class CheckPayment extends Payment {

    private Account account;
    private String series;
    private String number;
}
