package bank.bankieren;

import org.junit.Before;
import org.junit.Test;

import java.util.IllegalFormatException;

import static org.junit.Assert.*;

/**
 * Created by Dennis on 14/06/16
 * Package: bank.bankieren
 */
public class BankTest {
    String henkNaam;
    String henkPlaats;
    Klant henk;
    IBank bank;

    @Before
    public void setUp() throws Exception {
        henkNaam = "Henk";
        henkPlaats = "Eindhoven";
        henk = new Klant(henkNaam, henkPlaats);
        bank = new Bank("testBank");
    }

    @Test
    public void testOpenRekeningHappyFlow() throws Exception {
        //creatie van een nieuwe bankrekening met een identificerend rekeningnummer;
        //alleen als de klant, geidentificeerd door naam en plaats, nog niet bestaat
        //wordt er ook een nieuwe klant aangemaakt

        int henkRekening = 0;
        int pietRekening = 0;
        henkRekening = bank.openRekening(henkNaam, henkPlaats);
        pietRekening = bank.openRekening("Piet", "Veldhoven");

        assertNotEquals("henkRekening not created correctly", henkRekening, 0); //in case method did nothing
        assertNotEquals("henkRekening not created correctly", henkRekening, -1); //in case method went wrong
        assertNotEquals("pietRekening not created correctly", pietRekening, 0);
        assertNotEquals("pietRekening not created correctly", pietRekening, -1);
    }

    @Test
    public void testOpenRekeningEmptyString() throws Exception{
        //@return -1 zodra naam of plaats een lege string en anders het nummer van de
        //        gecreeerde bankrekening

        int emptyNaam = 0;
        int emptyPlaats = 0;
        int emptyEverything = 0;

        emptyNaam = bank.openRekening("", "test");
        emptyPlaats = bank.openRekening("test", "");
        emptyEverything = bank.openRekening("test", "test");

        assertNotEquals("emptyNaam not caught", emptyNaam, 0); //in case method did nothing
        assertEquals("emptyNaam not caught", emptyNaam, -1); // method should return -1
        assertNotEquals("emptyPlaats not caught", emptyPlaats, 0);
        assertEquals("emptyPlaats not caught", emptyPlaats, -1);
        assertNotEquals("emptyEverything not caught", emptyEverything, 0);
        assertEquals("emptyEverything not caught", emptyEverything, -1);
    }

    @Test
    public void testMaakOver() throws Exception {

    }

    @Test
    public void testGetRekening() throws Exception {

    }

    @Test
    public void testGetName() throws Exception {

    }
}