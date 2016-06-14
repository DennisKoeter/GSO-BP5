package bank.internettoegang;

import bank.bankieren.Bank;
import bank.bankieren.IBank;
import bank.bankieren.IRekening;
import fontys.util.InvalidSessionException;
import fontys.util.NumberDoesntExistException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;

import static org.junit.Assert.*;

/**
 * @author Edwin
 *         Created on 6/14/2016
 */
public class BankiersessieTest {

    // Constants
    private static final String BANK_NAAM = "Rabobank";
    private static final String HENK_NAAM = "Henk";
    private static final String HENK_PLAATS = "Eindhoven";

    // Variables
    private IBank bank;
    private IBankiersessie bankiersessie;
    private int rekeningNummer;

    @Before
    public void setUp() throws Exception {
        // Build all variables up
        bank = new Bank(BANK_NAAM);
        rekeningNummer = bank.openRekening(HENK_NAAM, HENK_PLAATS);
        bankiersessie = new Bankiersessie(rekeningNummer, bank);
    }

    @After
    public void tearDown() throws Exception {
        // Clear all variables
        bank = null;
        rekeningNummer = 0;
        bankiersessie = null;
    }

    @Test
    public void isGeldig() throws Exception {
        /**
         * @returns true als de laatste aanroep van getRekening of maakOver voor deze
         *          sessie minder dan GELDIGHEIDSDUUR geleden is
         *          en er geen communicatiestoornis in de tussentijd is opgetreden
         */

        // Happy flow
        // Unused rekening, just calling getrekening for the geldigheidsduur update
        IRekening dummyRekening = bankiersessie.getRekening();
        boolean actual = bankiersessie.isGeldig();
        assertTrue("Bankiersessie zou nog geldig moeten zijn, er is zojuist nog een getrekening aangevraagd", actual);

        // Sad flow
        /**
         * @returns FALSE als de laatste aanroep van getRekening of maakOver
         * voor deze sessie gelijk aan of groter dan GELDIGHEIDSDUUR geleden is
         */
        new Thread(() -> {
            try {
                // Refresh geldigheidsduur
                bankiersessie.getRekening();
                Thread.sleep(IBankiersessie.GELDIGHEIDSDUUR);
                assertNotEquals("Bankiersessie zou nu niet meer geldig moeten zijn. " +
                        "Geldigheidsduur is bereikt.", bankiersessie.isGeldig());
            } catch (InterruptedException | RemoteException | InvalidSessionException e) {
                e.printStackTrace();
            }
        }).start();

        // Sleep some time just to be sure the other thread has finished before
        // finishing the whole unit test.
        Thread.sleep(IBankiersessie.GELDIGHEIDSDUUR + IBankiersessie.GELDIGHEIDSDUUR);
    }

    @Test
    public void maakOver() throws Exception {
        /**
         * er wordt bedrag overgemaakt van de bankrekening met het nummer bron naar
         * de bankrekening met nummer bestemming
         *
         * @param bestemming
         *            is ongelijk aan rekeningnummer van deze bankiersessie
         * @param bedrag
         *            is groter dan 0
         * @return <b>true</b> als de overmaking is gelukt, anders <b>false</b>
         * @throws NumberDoesntExistException
         *             als bestemming onbekend is
         * @throws InvalidSessionException
         *             als sessie niet meer geldig is
         */

    }

    @Test
    public void getRekening() throws Exception {
        /**
         * @return de rekeninggegevens die horen bij deze sessie
         * @throws InvalidSessionException
         *             als de sessieId niet geldig of verlopen is
         * @throws RemoteException
         */

    }

    @Test
    public void logUit() throws Exception {
        /**
         * sessie wordt beeindigd
         */

    }
}