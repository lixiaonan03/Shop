package com.xyyy.shop.model;

import java.io.Serializable;
import java.util.List;


public class EnnCartDTO implements Serializable {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private List<EnnCart> ennCarts;

public List<EnnCart> getEnnCart() {
	return ennCarts;
}

public void setEnnCart(List<EnnCart> ennCart) {
	this.ennCarts = ennCart;
}
  
}
