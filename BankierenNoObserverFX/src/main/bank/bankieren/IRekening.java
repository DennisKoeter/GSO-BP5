package bank.bankieren;

import java.io.Serializable;

public interface IRekening extends Serializable {
  int getNr();
  Geld getSaldo();
  IKlant getEigenaar();
  int getKredietLimietInCenten();
}

