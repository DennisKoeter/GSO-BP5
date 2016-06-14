package bank.bankieren;

import fontys.util.*;

import java.util.*;

public class Bank implements IBank {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8728841131739353765L;
	private Map<Integer,IRekeningTbvBank> accounts;
	private Collection<IKlant> clients;
	private int nieuwReknr;
	private String name;

	public Bank(String name) {
		accounts = new HashMap<Integer,IRekeningTbvBank>();
		clients = new ArrayList<IKlant>();
		nieuwReknr = 100000000;	
		this.name = name;	
	}

	public int openRekening(String name, String city) {
		if (name.equals("") || city.equals(""))
			return -1;

		IKlant klant = getKlant(name, city);
		IRekeningTbvBank account = new Rekening(nieuwReknr, klant, Geld.EURO);
		accounts.put(nieuwReknr,account);
		nieuwReknr++;
		return nieuwReknr-1;
	}

	private IKlant getKlant(String name, String city) {
		for (IKlant k : clients) {
			if (k.getNaam().equals(name) && k.getPlaats().equals(city))
				return k;
		}
		IKlant klant = new Klant(name, city);
		clients.add(klant);
		return klant;
	}

	public IRekening getRekening(int nr) {
		return accounts.get(nr);
	}

	public boolean maakOver(int source, int destination, Geld geld)
			throws NumberDoesntExistException {
		if (source == destination)
			throw new RuntimeException(
					"cannot transfer geld to your own account");
		if (!geld.isPositive())
			throw new RuntimeException("geld must be positive");

		IRekeningTbvBank source_account = (IRekeningTbvBank) getRekening(source);
		if (source_account == null)
			throw new NumberDoesntExistException("account " + source
					+ " unknown at " + name);

		Geld negative = Geld.difference(new Geld(0, geld.getCurrency()),
				geld);
		boolean success = source_account.muteer(negative);
		if (!success)
			return false;

		IRekeningTbvBank dest_account = (IRekeningTbvBank) getRekening(destination);
		if (dest_account == null) 
			throw new NumberDoesntExistException("account " + destination
					+ " unknown at " + name);
		success = dest_account.muteer(geld);

		if (!success) // rollback
			source_account.muteer(geld);
		return success;
	}

	@Override
	public String getName() {
		return name;
	}

}
