package somossuinos.aptcomplex.domain.person;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

/**
 * @since 2015-07-19
 */
public class PersonNameTest {

    @Test
    public void testGetFirstNameFromNull() throws Exception {
        final PersonName pn = new PersonName();
        Assert.assertThat(pn.getFirstName(), CoreMatchers.nullValue());
    }

    @Test
    public void testGetFirstNameSimple() throws Exception {
        final PersonName pn = new PersonName("First");
        Assert.assertThat(pn.getFirstName(), CoreMatchers.is("First"));
    }

    @Test
    public void testGetFirstNameWithBlanks() throws Exception {
        final PersonName pn = new PersonName(" First ");
        Assert.assertThat(pn.getFirstName(), CoreMatchers.is("First"));
    }

    @Test
    public void testGetFirstNameAllBlank() throws Exception {
        final PersonName pn = new PersonName(" ");
        Assert.assertThat(pn.getFirstName(), CoreMatchers.nullValue());
    }

    @Test
    public void testGetFirstNameWithSurname() throws Exception {
        final PersonName pn = new PersonName("First Last");
        Assert.assertThat(pn.getFirstName(), CoreMatchers.is("First"));
    }

    @Test
    public void testGetFirstNameWithSurnameAndBlanks() throws Exception {
        final PersonName pn = new PersonName(" First  Last ");
        Assert.assertThat(pn.getFirstName(), CoreMatchers.is("First"));
    }

    @Test
    public void testGetFirstNameWithMiddleNameAndSurnameAndBlanks() throws Exception {
        final PersonName pn = new PersonName(" First  Middle   Last ");
        Assert.assertThat(pn.getFirstName(), CoreMatchers.is("First"));
    }

    @Test
    public void testGetLastNameFromNull() throws Exception {
        final PersonName pn = new PersonName();
        Assert.assertThat(pn.getLastName(), CoreMatchers.nullValue());
    }

    @Test
    public void testGetLastNameSimple() throws Exception {
        final PersonName pn = new PersonName("First Last");
        Assert.assertThat(pn.getLastName(), CoreMatchers.is("Last"));
    }

    @Test
    public void testGetLastNameWithBlanks() throws Exception {
        final PersonName pn = new PersonName(" First  Last ");
        Assert.assertThat(pn.getLastName(), CoreMatchers.is("Last"));
    }

    @Test
    public void testGetLastNameWithMiddlenameAndBlanks() throws Exception {
        final PersonName pn = new PersonName(" First  Middle   Last ");
        Assert.assertThat(pn.getLastName(), CoreMatchers.is("Last"));
    }

    @Test
    public void testGetMiddleNameFromNull() throws Exception {
        final PersonName pn = new PersonName();
        Assert.assertThat(pn.getMiddleName(), CoreMatchers.nullValue());
    }

    @Test
    public void testGetMiddleName() throws Exception {
        final PersonName pn = new PersonName(" First  Middle   Last ");
        Assert.assertThat(pn.getMiddleName(), CoreMatchers.is("Middle"));
    }

    @Test
    public void testGetMiddleNameFromBasicConstructor() throws Exception {
        final PersonName pn = new PersonName("First", "Middle", "Last");
        Assert.assertThat(pn.getMiddleName(), CoreMatchers.is("Middle"));
    }

    @Test
    public void testGetMiddleNameFromFullName() throws Exception {
        final PersonName pn = new PersonName(" First  Middle   Last ");
        Assert.assertThat(pn.getMiddleName(), CoreMatchers.is("Middle"));
    }

    @Test
    public void testGetMiddleNameFromMoreThanThreeNames() throws Exception {
        final PersonName pn = new PersonName(" First  Middle Middle2  Middle3  Last ");
        Assert.assertThat(pn.getMiddleName(), CoreMatchers.is("Middle Middle2 Middle3"));
    }

    @Test
    public void testGetNameFromBasicConstructor() throws Exception {
        final PersonName pn = new PersonName(" First  ", "   Middle  ", "  Last  ");
        Assert.assertThat(pn.getName(), CoreMatchers.is("First Middle Last"));
    }

    @Test
    public void testGetNameFromFullName() throws Exception {
        final PersonName pn = new PersonName(" First  Middle   Last ");
        Assert.assertThat(pn.getName(), CoreMatchers.is("First Middle Last"));
    }
}