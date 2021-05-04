package br.com.leomanzini.entities;

public enum OrderStatus {
	PENDING(0), DELIVERED(1);
	
	@SuppressWarnings("unused")
	private int value;
	
	private OrderStatus(int value) {
		this.value = value;
	}
}
