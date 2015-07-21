package somossuinos.aptcomplex.domain.person.document;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import somossuinos.aptcomplex.domain.exception.AptComplexDomainException;

public class TaxAndCreditDocumentTest {

    private static final String WRONG_DOC = "11122233344";
    private static final String RIGHT_DOC = "00766331440";
    private static final String FORMATTED_DOC = "007.663.314-40";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testInstantiateSimple() {
        final TaxAndCreditDocument doc = new TaxAndCreditDocument();
        Assert.assertThat(doc, CoreMatchers.notNullValue());
    }

    @Test
    public void testInstantiateWithWrongNumber() {
        thrown.expect(AptComplexDomainException.class);
        thrown.expectMessage("Invalid document");
        final TaxAndCreditDocument doc = new TaxAndCreditDocument(WRONG_DOC);
    }

    @Test
    public void testInstantiateWithRightNumber() {
        final TaxAndCreditDocument doc = new TaxAndCreditDocument(RIGHT_DOC);
        Assert.assertThat(doc, CoreMatchers.notNullValue());
    }

    @Test
    public void testGetNumber() throws Exception {
        final TaxAndCreditDocument doc = new TaxAndCreditDocument(RIGHT_DOC);
        Assert.assertThat(doc.getNumber(), CoreMatchers.is(RIGHT_DOC));
    }

    @Test
    public void testGetFormattedNumber() throws Exception {
        final TaxAndCreditDocument doc = new TaxAndCreditDocument(RIGHT_DOC);
        Assert.assertThat(doc.getFormattedNumber(), CoreMatchers.is(FORMATTED_DOC));
    }
}