package model;

import model.unimplemented.PaymentMethod;

public class CreditCardPayment implements PaymentMethod{

	private int credit = 100;
	@Override
	public boolean pay(double price) {
		credit = credit-=price;
		return credit>0;
	}

}
