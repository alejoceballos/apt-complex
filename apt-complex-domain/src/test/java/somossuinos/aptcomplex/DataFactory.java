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

        people.put("201", Person.Builder.get("FULANO ??? DO 201").build());
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
        final short firstMonth = 5;
        final short lastMonth = 6;
        final short dueDay = 10;

        final Random randomInt = new Random();

        final Set<MonthlyBalance> monthlyBalances = new HashSet<>(lastMonth);

        // For each month in year...
        for (short month = firstMonth; month <= lastMonth; month++) {
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

        return monthlyBalances;
    }

}
