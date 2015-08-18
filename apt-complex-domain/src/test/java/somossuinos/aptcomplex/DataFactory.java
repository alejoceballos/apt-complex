package somossuinos.aptcomplex;

import org.joda.time.DateTime;
import somossuinos.aptcomplex.domain.apartment.Apartment;
import somossuinos.aptcomplex.domain.balance.ApartmentBalance;
import somossuinos.aptcomplex.domain.balance.ApartmentBalanceGroup;
import somossuinos.aptcomplex.domain.balance.BalanceDivergence;
import somossuinos.aptcomplex.domain.balance.DivergenceType;
import somossuinos.aptcomplex.domain.balance.MonthlyBalance;
import somossuinos.aptcomplex.domain.balance.ReferenceMonth;
import somossuinos.aptcomplex.domain.bill.BillItem;
import somossuinos.aptcomplex.domain.bill.BillItemType;
import somossuinos.aptcomplex.domain.bill.BillWithDueDate;
import somossuinos.aptcomplex.domain.payment.Payment;
import somossuinos.aptcomplex.domain.payment.TransactionType;
import somossuinos.aptcomplex.domain.person.Person;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class DataFactory {

    public static Map<String, Person> people() {
        final Map<String, Person> people = new HashMap<>();

        people.put("101", Person.Builder.get("MOACIR FERREIRA CHAVES JUNIOR").build());
        people.put("102", Person.Builder.get("DANIELA BEZERRA DE MENEZES ?").build());
        people.put("103", Person.Builder.get("FRANCISCA DAS CHAGAS NUNES").build());

        people.put("201", Person.Builder.get("JOSE NUNES DE LIMA").build());
        people.put("202", Person.Builder.get("LUDMILA ALMEIDA SILVA MIRANDA").build());
        people.put("203", Person.Builder.get("ANTONIA GONCALVES DE CARVALHO").build());

        people.put("301", Person.Builder.get("FULANO ??? DO 301").build());
        people.put("302", Person.Builder.get("FRANCISCO ARILO MAGALHAES").build());
        people.put("303", Person.Builder.get("ANA GEORGINA ALVES DIAS").build());

        people.put("401", Person.Builder.get("GEORGE SILVEIRA SOSTENES").build());
        people.put("402", Person.Builder.get("JOSE ADNORT ROBERTO").build());
        people.put("403", Person.Builder.get("MARCIA DE OLIVEIRA COSTA").build());

        people.put("501", Person.Builder.get("MARIA DE LOURDES DOS SANTOS").build());
        people.put("502", Person.Builder.get("GERALDO JOSE MOURA DE ALMEIDA").build());
        people.put("503", Person.Builder.get("KATIA MARQUES TRINDADE").build());

        people.put("601", Person.Builder.get("MARIA DALVA BAISLIO DE LIMA").build());
        people.put("602", Person.Builder.get("OTACILIO BATISTA DE SOUZA").build());
        people.put("603", Person.Builder.get("JOSE SALVIANO DE SOUZA MELO").build());

        people.put("701", Person.Builder.get("ALEXANDRE AUGUSTO VAZ MACH").build());
        people.put("702", Person.Builder.get("CARMEM LUCIA NEVES DE LUNA").build());
        people.put("703", Person.Builder.get("MARIA DE JESUS FARIAS DA SILVA").build());

        people.put("801", Person.Builder.get("EDNA LINS DO NASCIMENTO").build());
        people.put("802", Person.Builder.get("ALEJO CEBALLOS").build());
        people.put("803", Person.Builder.get("MARIA JOSE BORGES GOMES").build());

        people.put("901", Person.Builder.get("ANDRE MACHADO").build());
        people.put("902", Person.Builder.get("LUIZ CARLOS MACIERE DE ?").build());
        people.put("903", Person.Builder.get("ZAIRA MAURICIO MACHADO").build());

        people.put("1001", Person.Builder.get("HELENA EDUARDO VARELA ASFORA").build());
        people.put("1002", Person.Builder.get("ROGERIO GONCALVES MIRO").build());
        people.put("1003", Person.Builder.get("HELENA CAMPOS CAVALCANTI").build());

        people.put("1101", Person.Builder.get("SOSTENES GOMES CAVALCANTI").build());
        people.put("1102", Person.Builder.get("MARIA AMELIA V FALCAO").build());
        people.put("1103", Person.Builder.get("JOSE AUGUSTO DOS SANTOS NETO").build());

        people.put("1201", Person.Builder.get("FULANO ??? DO 1201").build());
        people.put("1202", Person.Builder.get("ROBERTO JOSE DE ARAUJO LIMA").build());
        people.put("1203", Person.Builder.get("MARLENE BARBOSA PONTES").build());

        return people;
    }

    public static Set<Apartment> apartments() {
        final Set<Apartment> apartments = new HashSet<>(36);

        int aptNumber = 100;

        final Map<String, Person> people = people();

        // Create apartments
        for (int aptCount = 0; aptCount < 36; aptCount++) {
            if (aptCount % 3 == 0) {
                aptNumber = (((aptCount / 3) + 1) * 100) + 1;
            } else {
                aptNumber++;
            }

            apartments.add(Apartment.Builder.get(String.valueOf(aptNumber))
                    .withResident(people.get(String.valueOf(aptNumber)))
                    .build());
        }

        return apartments;
    }

    public static Set<MonthlyBalance> monthlyBalances(final Collection<Apartment> apartments) {
        final BigDecimal feeValue = BigDecimal.valueOf(800.00);
        final BigDecimal surchargeValue = BigDecimal.valueOf(250.00);

        final short year = 2015;
        final short firstMonth = 3;
        final short lastMonth = 7;
        final short dueDay = 10;

        final Random randomInt = new Random();

        final Set<MonthlyBalance> monthlyBalances = new HashSet<>(lastMonth);

        // For each month in year...
        for (short month = firstMonth; month <= lastMonth; month++) {
            if (month != 6) {
                final Set<ApartmentBalance> apartmentBalances = new HashSet<>(apartments.size());

                // ... Create a set of Apartment Balances
                for (final Apartment apartment : apartments) {
                    // With basic condo fees
                    final BillWithDueDate.Builder feeBuilder = BillWithDueDate.Builder.get()
                            .withDueDate(new DateTime(year, month, dueDay, 0, 0))
                            .withItem(BillItem.Builder.get()
                                    .withType(BillItemType.CONDOMINIUM_FEE)
                                    .withValue(feeValue)
                                    .build())
                            .withItem(BillItem.Builder.get()
                                    .withType(BillItemType.CONDOMINIUM_SURCHARGE)
                                    .withValue(surchargeValue)
                                    .build());

                    final int random = randomInt.nextInt(3);

                    // With or without payments
                    if (random > 0) {
                        final BigDecimal paymentVal = random == 1 ? BigDecimal.valueOf(950.00) : BigDecimal.valueOf(1050.00);

                        feeBuilder.withPayment(Payment.Builder.get()
                                .withValue(paymentVal)
                                .withType(TransactionType.INCOME)
                                .withWhen(new DateTime(year, month, 11, 0, 0))
                                .withNote("DO NOT KNOW THE EXACT DATE OF PAYMENT. WILL ASSUME AFTER DAY 10, SO NO DISCOUNT")
                                .build());
                    }

                    apartmentBalances.add(ApartmentBalance.Builder.get(apartment)
                            .withFee(feeBuilder.build())
                            .build());
                }

                monthlyBalances.add(MonthlyBalance.Builder.get(new ReferenceMonth(year, month))
                        .withBalanceGroup(ApartmentBalanceGroup.Builder.get()
                                .withApartmentBalances(apartmentBalances)
                                .withDivergence(BalanceDivergence.Builder.get(DivergenceType.FEES_PAYMENTS)
                                        .withDescription("SOME SMALL DEVIATION FROM THE WHOLE VALUE (WTF?)")
                                        .withValue(BigDecimal.valueOf(500.00))
                                        .build())
                                .build())
                        .build());
            }

            monthlyBalances.add(
                    MonthlyBalance.Builder.get(new ReferenceMonth(year, (short) 6))
                            .withBalanceGroup(
                                    ApartmentBalanceGroup.Builder.get()
                                            .withApartmentBalances(juneOf2015Balances(apartments))
                                            .build())
                            .build());
        }

        return monthlyBalances;
    }

    public static Set<ApartmentBalance> juneOf2015Balances(final Collection<Apartment> apartments) {
        final BigDecimal feeValue = BigDecimal.valueOf(800.00);
        final BigDecimal surchargeValue = BigDecimal.valueOf(250.00);
        final BigDecimal discountValue = BigDecimal.valueOf(-50.00);

        final short year = 2015;
        final short month = 6;
        final short dueDay = 10;

        final Set<ApartmentBalance> apartmentBalances = new HashSet<>(apartments.size());

        for (final Apartment apartment : apartments) {
            final BillWithDueDate.Builder feeBuilder;

            if (apartment.getNumber().equals("703")) {
                feeBuilder = BillWithDueDate.Builder.get()
                        .withDueDate(new DateTime(year, month, 20, 0, 0));

            } else {
                feeBuilder = BillWithDueDate.Builder.get()
                        .withDueDate(new DateTime(year, month, dueDay, 0, 0));
            }

            feeBuilder
                    .withItem(
                            BillItem.Builder.get()
                                    .withType(BillItemType.CONDOMINIUM_SURCHARGE)
                                    .withValue(surchargeValue)
                                    .build());


            if (apartment.getNumber().equals("101")) {
                feeBuilder
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_FEE)
                                        .withValue(feeValue)
                                        .build())
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_DISCOUNT)
                                        .withValue(discountValue)
                                        .build())
                        .withPayment(
                                Payment.Builder.get()
                                        .withType(TransactionType.INCOME)
                                        .withValue(BigDecimal.valueOf(750 + 250))
                                        .withWhen(new DateTime(year, month, 10, 0, 0))
                                        .withNote("PAID UNTIL DUE DATE. 50.00 DISCOUNT")
                                        .build()
                        );

            } else if (apartment.getNumber().equals("102")) {
                feeBuilder
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_FEE)
                                        .withValue(feeValue)
                                        .build())
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_DISCOUNT)
                                        .withValue(discountValue)
                                        .build())
                        .withPayment(
                                Payment.Builder.get()
                                        .withType(TransactionType.INCOME)
                                        .withValue(BigDecimal.valueOf(750 + 250))
                                        .withWhen(new DateTime(year, month, 10, 0, 0))
                                        .withNote("PAID UNTIL DUE DATE. 50.00 DISCOUNT")
                                        .build()
                        );

            } else if (apartment.getNumber().equals("103")) {
                feeBuilder
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_FEE)
                                        .withValue(feeValue)
                                        .build())
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_DISCOUNT)
                                        .withValue(discountValue)
                                        .build())
                        .withPayment(
                                Payment.Builder.get()
                                        .withType(TransactionType.INCOME)
                                        .withValue(BigDecimal.valueOf(750 + 250))
                                        .withWhen(new DateTime(year, month, 10, 0, 0))
                                        .withNote("PAID UNTIL DUE DATE. 50.00 DISCOUNT")
                                        .build()
                        );

            } else if (apartment.getNumber().equals("201")) {
                // No condo fee (condo admin), but no discount
                feeBuilder.withPayment(
                        Payment.Builder.get()
                                .withType(TransactionType.INCOME)
                                .withValue(BigDecimal.valueOf(250))
                                .withWhen(new DateTime(year, month, 10, 0, 0))
                                .withNote("NO CONDO FEE (CONDO ADMIN), BUT NO DISCOUNT")
                                .build()
                );

            } else if (apartment.getNumber().equals("202")) {
                feeBuilder.withItem(
                        BillItem.Builder.get()
                                .withType(BillItemType.CONDOMINIUM_FEE)
                                .withValue(BigDecimal.valueOf(1100 + 250))
                                .build())
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_DISCOUNT)
                                        .withValue(discountValue)
                                        .build())
                        .withPayment(
                                Payment.Builder.get()
                                        .withType(TransactionType.INCOME)
                                        .withValue(BigDecimal.valueOf(1050))
                                        .withWhen(new DateTime(year, month, 3, 0, 0))
                                        .withNote("PAID UNTIL DUE DATE. 50.00 DISCOUNT")
                                        .build()
                );

            } else if (apartment.getNumber().equals("203")) {
                feeBuilder
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_FEE)
                                        .withValue(feeValue)
                                        .build())
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_DISCOUNT)
                                        .withValue(discountValue)
                                        .build())
                        .withPayment(
                                Payment.Builder.get()
                                        .withType(TransactionType.INCOME)
                                        .withValue(BigDecimal.valueOf(750 + 250))
                                        .withWhen(new DateTime(year, month, 8, 0, 0))
                                        .withNote("PAID UNTIL DUE DATE. 50.00 DISCOUNT")
                                        .build()
                        );

            } else if (apartment.getNumber().equals("301")) {
                feeBuilder.withItem(BillItem.Builder.get()
                        .withType(BillItemType.CONDOMINIUM_FEE)
                        .withValue(feeValue)
                        .build());

            } else if (apartment.getNumber().equals("302")) {
                feeBuilder
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_FEE)
                                        .withValue(feeValue)
                                        .build())
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_DISCOUNT)
                                        .withValue(discountValue)
                                        .build())
                        .withPayment(
                                Payment.Builder.get()
                                        .withType(TransactionType.INCOME)
                                        .withValue(BigDecimal.valueOf(750 + 250))
                                        .withWhen(new DateTime(year, month, 10, 0, 0))
                                        .withNote("PAID UNTIL DUE DATE. 50.00 DISCOUNT")
                                        .build()
                        );

            } else if (apartment.getNumber().equals("303")) {
                feeBuilder
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_FEE)
                                        .withValue(feeValue)
                                        .build())
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_DISCOUNT)
                                        .withValue(discountValue)
                                        .build())
                        .withPayment(
                                Payment.Builder.get()
                                        .withType(TransactionType.INCOME)
                                        .withValue(BigDecimal.valueOf(750 + 250))
                                        .withWhen(new DateTime(year, month, 10, 0, 0))
                                        .withNote("PAID UNTIL DUE DATE. 50.00 DISCOUNT")
                                        .build()
                        );

            } else if (apartment.getNumber().equals("401")) {
                feeBuilder
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_FEE)
                                        .withValue(feeValue)
                                        .build())
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_DISCOUNT)
                                        .withValue(discountValue)
                                        .build())
                        .withPayment(
                                Payment.Builder.get()
                                        .withType(TransactionType.INCOME)
                                        .withValue(BigDecimal.valueOf(750 + 250))
                                        .withWhen(new DateTime(year, month, 3, 0, 0))
                                        .withNote("PAID UNTIL DUE DATE. 50.00 DISCOUNT")
                                        .build()
                        );

            } else if (apartment.getNumber().equals("402")) {
                feeBuilder
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_FEE)
                                        .withValue(BigDecimal.valueOf(1000))
                                        .build())
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_DISCOUNT)
                                        .withValue(discountValue)
                                        .build())
                        .withPayment(
                                Payment.Builder.get()
                                        .withType(TransactionType.INCOME)
                                        .withValue(BigDecimal.valueOf(950 + 250))
                                        .withWhen(new DateTime(year, month, 10, 0, 0))
                                        .withNote("PAID UNTIL DUE DATE. 50.00 DISCOUNT")
                                        .build()
                        );

            } else if (apartment.getNumber().equals("403")) {
                feeBuilder
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_FEE)
                                        .withValue(feeValue)
                                        .build())
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_DISCOUNT)
                                        .withValue(discountValue)
                                        .build())
                        .withPayment(
                                Payment.Builder.get()
                                        .withType(TransactionType.INCOME)
                                        .withValue(BigDecimal.valueOf(750 + 250))
                                        .withWhen(new DateTime(year, month, 10, 0, 0))
                                        .withNote("PAID UNTIL DUE DATE. 50.00 DISCOUNT")
                                        .build()
                        );

            } else if (apartment.getNumber().equals("501")) {
                feeBuilder
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_FEE)
                                        .withValue(feeValue)
                                        .build())
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_DISCOUNT)
                                        .withValue(discountValue)
                                        .build())
                        .withPayment(
                                Payment.Builder.get()
                                        .withType(TransactionType.INCOME)
                                        .withValue(BigDecimal.valueOf(750 + 250))
                                        .withWhen(new DateTime(year, month, 10, 0, 0))
                                        .withNote("PAID UNTIL DUE DATE. 50.00 DISCOUNT")
                                        .build()
                        );

            } else if (apartment.getNumber().equals("502")) {
                feeBuilder
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_FEE)
                                        .withValue(feeValue)
                                        .build())
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_DISCOUNT)
                                        .withValue(discountValue)
                                        .build())
                        .withPayment(
                                Payment.Builder.get()
                                        .withType(TransactionType.INCOME)
                                        .withValue(BigDecimal.valueOf(750 + 250))
                                        .withWhen(new DateTime(year, month, 10, 0, 0))
                                        .withNote("PAID UNTIL DUE DATE. 50.00 DISCOUNT")
                                        .build()
                        );

            } else if (apartment.getNumber().equals("503")) {
                feeBuilder
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_FEE)
                                        .withValue(feeValue)
                                        .build())
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_DISCOUNT)
                                        .withValue(discountValue)
                                        .build())
                        .withPayment(
                                Payment.Builder.get()
                                        .withType(TransactionType.INCOME)
                                        .withValue(BigDecimal.valueOf(750 + 250))
                                        .withWhen(new DateTime(year, month, 8, 0, 0))
                                        .withNote("PAID UNTIL DUE DATE. 50.00 DISCOUNT")
                                        .build()
                        );

            } else if (apartment.getNumber().equals("601")) {
                feeBuilder
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_FEE)
                                        .withValue(feeValue)
                                        .build())
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_DISCOUNT)
                                        .withValue(discountValue)
                                        .build())
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CUSTOM)
                                        .withDescription("TEST BILLET")
                                        .withValue(discountValue)
                                        .build())
                        .withPayment(
                                Payment.Builder.get()
                                        .withType(TransactionType.INCOME)
                                        .withValue(BigDecimal.valueOf(750 + 250))
                                        .withWhen(new DateTime(year, month, 8, 0, 0))
                                        .withNote("PAID UNTIL DUE DATE. 50.00 DISCOUNT. TEST BILLET SHOULD NOT BE PAID!")
                                        .build()
                        );

            } else if (apartment.getNumber().equals("602")) {
                feeBuilder
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_FEE)
                                        .withValue(feeValue)
                                        .build())
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_DISCOUNT)
                                        .withValue(discountValue)
                                        .build())
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CUSTOM)
                                        .withDescription("TEST BILLET")
                                        .withValue(discountValue)
                                        .build())
                        .withPayment(
                                Payment.Builder.get()
                                        .withType(TransactionType.INCOME)
                                        .withValue(BigDecimal.valueOf(750 + 250))
                                        .withWhen(new DateTime(year, month, 10, 0, 0))
                                        .withNote("PAID UNTIL DUE DATE. 50.00 DISCOUNT. TEST BILLET SHOULD NOT BE PAID!")
                                        .build()
                        );

            } else if (apartment.getNumber().equals("603")) {
                feeBuilder
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_FEE)
                                        .withValue(feeValue)
                                        .build())
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_DISCOUNT)
                                        .withValue(discountValue)
                                        .build())
                        .withPayment(
                                Payment.Builder.get()
                                        .withType(TransactionType.INCOME)
                                        .withValue(BigDecimal.valueOf(750 + 250))
                                        .withWhen(new DateTime(year, month, 10, 0, 0))
                                        .withNote("PAID UNTIL DUE DATE. 50.00 DISCOUNT")
                                        .build()
                        );

            } else if (apartment.getNumber().equals("701")) {
                feeBuilder
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_FEE)
                                        .withValue(feeValue)
                                        .build())
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_DISCOUNT)
                                        .withValue(discountValue)
                                        .build())
                        .withPayment(
                                Payment.Builder.get()
                                        .withType(TransactionType.INCOME)
                                        .withValue(BigDecimal.valueOf(750 + 250))
                                        .withWhen(new DateTime(year, month, 10, 0, 0))
                                        .withNote("PAID UNTIL DUE DATE. 50.00 DISCOUNT")
                                        .build()
                        );

            } else if (apartment.getNumber().equals("702")) {
                feeBuilder
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_FEE)
                                        .withValue(feeValue)
                                        .build())
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_DISCOUNT)
                                        .withValue(discountValue)
                                        .build())
                        .withPayment(
                                Payment.Builder.get()
                                        .withType(TransactionType.INCOME)
                                        .withValue(BigDecimal.valueOf(750 + 250))
                                        .withWhen(new DateTime(year, month, 10, 0, 0))
                                        .withNote("PAID UNTIL DUE DATE. 50.00 DISCOUNT")
                                        .build()
                        );

            } else if (apartment.getNumber().equals("703")) {
                feeBuilder
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_FEE)
                                        .withValue(feeValue)
                                        .build())
                        .withPayment(
                                Payment.Builder.get()
                                        .withType(TransactionType.INCOME)
                                        .withValue(BigDecimal.valueOf(750 + 250))
                                        .withWhen(new DateTime(year, month, 22, 0, 0))
                                        .withNote("PAID AFTER DUE DATE. NO DISCOUNT")
                                        .build()
                        );

            } else if (apartment.getNumber().equals("801")) {
                feeBuilder
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_FEE)
                                        .withValue(feeValue)
                                        .build())
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_DISCOUNT)
                                        .withValue(discountValue)
                                        .build())
                        .withPayment(
                                Payment.Builder.get()
                                        .withType(TransactionType.INCOME)
                                        .withValue(BigDecimal.valueOf(750 + 250))
                                        .withWhen(new DateTime(year, month, 5, 0, 0))
                                        .withNote("PAID UNTIL DUE DATE. 50.00 DISCOUNT")
                                        .build()
                        );

            } else if (apartment.getNumber().equals("802")) {
                feeBuilder
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_FEE)
                                        .withValue(feeValue)
                                        .build())
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_DISCOUNT)
                                        .withValue(discountValue)
                                        .build())
                        .withPayment(
                                Payment.Builder.get()
                                        .withType(TransactionType.INCOME)
                                        .withValue(BigDecimal.valueOf(750 + 250))
                                        .withWhen(new DateTime(year, month, 2, 0, 0))
                                        .withNote("PAID UNTIL DUE DATE. 50.00 DISCOUNT")
                                        .build()
                        );

            } else if (apartment.getNumber().equals("803")) {
                feeBuilder
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_FEE)
                                        .withValue(feeValue)
                                        .build())
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_DISCOUNT)
                                        .withValue(discountValue)
                                        .build())
                        .withPayment(
                                Payment.Builder.get()
                                        .withType(TransactionType.INCOME)
                                        .withValue(BigDecimal.valueOf(750 + 250))
                                        .withWhen(new DateTime(year, month, 10, 0, 0))
                                        .withNote("PAID UNTIL DUE DATE. 50.00 DISCOUNT")
                                        .build()
                        );

            } else if (apartment.getNumber().equals("901")) {
                feeBuilder
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_FEE)
                                        .withValue(feeValue)
                                        .build())
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_DISCOUNT)
                                        .withValue(discountValue)
                                        .build())
                        .withPayment(
                                Payment.Builder.get()
                                        .withType(TransactionType.INCOME)
                                        .withValue(BigDecimal.valueOf(750 + 250))
                                        .withWhen(new DateTime(year, month, 9, 0, 0))
                                        .withNote("PAID UNTIL DUE DATE. 50.00 DISCOUNT")
                                        .build()
                        );

            } else if (apartment.getNumber().equals("902")) {
                feeBuilder
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_FEE)
                                        .withValue(feeValue)
                                        .build())
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_DISCOUNT)
                                        .withValue(discountValue)
                                        .build())
                        .withPayment(
                                Payment.Builder.get()
                                        .withType(TransactionType.INCOME)
                                        .withValue(BigDecimal.valueOf(750 + 250))
                                        .withWhen(new DateTime(year, month, 5, 0, 0))
                                        .withNote("PAID UNTIL DUE DATE. 50.00 DISCOUNT")
                                        .build()
                        );

            } else if (apartment.getNumber().equals("903")) {
                feeBuilder
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_FEE)
                                        .withValue(feeValue)
                                        .build())
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_DISCOUNT)
                                        .withValue(discountValue)
                                        .build())
                        .withPayment(
                                Payment.Builder.get()
                                        .withType(TransactionType.INCOME)
                                        .withValue(BigDecimal.valueOf(750 + 250))
                                        .withWhen(new DateTime(year, month, 9, 0, 0))
                                        .withNote("PAID UNTIL DUE DATE. 50.00 DISCOUNT")
                                        .build()
                        );

            } else if (apartment.getNumber().equals("1001")) {
                feeBuilder
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_FEE)
                                        .withValue(feeValue)
                                        .build())
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_DISCOUNT)
                                        .withValue(discountValue)
                                        .build())
                        .withPayment(
                                Payment.Builder.get()
                                        .withType(TransactionType.INCOME)
                                        .withValue(BigDecimal.valueOf(750 + 250))
                                        .withWhen(new DateTime(year, month, 2, 0, 0))
                                        .withNote("PAID UNTIL DUE DATE. 50.00 DISCOUNT")
                                        .build()
                        );

            } else if (apartment.getNumber().equals("1002")) {
                feeBuilder
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_FEE)
                                        .withValue(feeValue)
                                        .build())
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_DISCOUNT)
                                        .withValue(discountValue)
                                        .build())
                        .withPayment(
                                Payment.Builder.get()
                                        .withType(TransactionType.INCOME)
                                        .withValue(BigDecimal.valueOf(750 + 250))
                                        .withWhen(new DateTime(year, month, 10, 0, 0))
                                        .withNote("PAID UNTIL DUE DATE. 50.00 DISCOUNT")
                                        .build()
                        );

            } else if (apartment.getNumber().equals("1003")) {
                feeBuilder
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_FEE)
                                        .withValue(feeValue)
                                        .build())
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_DISCOUNT)
                                        .withValue(discountValue)
                                        .build())
                        .withPayment(
                                Payment.Builder.get()
                                        .withType(TransactionType.INCOME)
                                        .withValue(BigDecimal.valueOf(750 + 250))
                                        .withWhen(new DateTime(year, month, 10, 0, 0))
                                        .withNote("PAID UNTIL DUE DATE. 50.00 DISCOUNT")
                                        .build()
                        );

            } else if (apartment.getNumber().equals("1101")) {
                feeBuilder
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_FEE)
                                        .withValue(feeValue)
                                        .build())
                        .withPayment(
                                Payment.Builder.get()
                                        .withType(TransactionType.INCOME)
                                        .withValue(BigDecimal.valueOf(800 + 250))
                                        .withWhen(new DateTime(year, month, 9, 0, 0))
                                        .withNote("THERE IS A MISTAKE! HE SHOULD HAVE GOTTEN THE DISCOUNT!")
                                        .build()
                        );

            } else if (apartment.getNumber().equals("1102")) {
                feeBuilder
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_FEE)
                                        .withValue(feeValue)
                                        .build())
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_DISCOUNT)
                                        .withValue(discountValue)
                                        .build())
                        .withPayment(
                                Payment.Builder.get()
                                        .withType(TransactionType.INCOME)
                                        .withValue(BigDecimal.valueOf(750 + 250))
                                        .withWhen(new DateTime(year, month, 8, 0, 0))
                                        .withNote("PAID UNTIL DUE DATE. 50.00 DISCOUNT")
                                        .build()
                        );

            } else if (apartment.getNumber().equals("1103")) {
                feeBuilder
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_FEE)
                                        .withValue(feeValue)
                                        .build())
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_DISCOUNT)
                                        .withValue(discountValue)
                                        .build())
                        .withPayment(
                                Payment.Builder.get()
                                        .withType(TransactionType.INCOME)
                                        .withValue(BigDecimal.valueOf(750 + 250))
                                        .withWhen(new DateTime(year, month, 10, 0, 0))
                                        .withNote("PAID UNTIL DUE DATE. 50.00 DISCOUNT")
                                        .build()
                        );

            } else if (apartment.getNumber().equals("1201")) {
                feeBuilder.withItem(BillItem.Builder.get()
                        .withType(BillItemType.CONDOMINIUM_FEE)
                        .withValue(feeValue)
                        .build());

            } else if (apartment.getNumber().equals("1202")) {
                feeBuilder
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_FEE)
                                        .withValue(feeValue)
                                        .build())
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_DISCOUNT)
                                        .withValue(discountValue)
                                        .build())
                        .withPayment(
                                Payment.Builder.get()
                                        .withType(TransactionType.INCOME)
                                        .withValue(BigDecimal.valueOf(750 + 250))
                                        .withWhen(new DateTime(year, month, 10, 0, 0))
                                        .withNote("PAID UNTIL DUE DATE. 50.00 DISCOUNT")
                                        .build()
                        );

            } else if (apartment.getNumber().equals("1203")) {
                feeBuilder
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_FEE)
                                        .withValue(feeValue)
                                        .build())
                        .withItem(
                                BillItem.Builder.get()
                                        .withType(BillItemType.CONDOMINIUM_DISCOUNT)
                                        .withValue(discountValue)
                                        .build())
                        .withPayment(
                                Payment.Builder.get()
                                        .withType(TransactionType.INCOME)
                                        .withValue(BigDecimal.valueOf(750 + 250))
                                        .withWhen(new DateTime(year, month, 5, 0, 0))
                                        .withNote("PAID UNTIL DUE DATE. 50.00 DISCOUNT")
                                        .build()
                        );
            }

            apartmentBalances.add(
                    ApartmentBalance.Builder.get(apartment)
                            .withFee(feeBuilder.build()).build());
        }

        return apartmentBalances;

    }

}
