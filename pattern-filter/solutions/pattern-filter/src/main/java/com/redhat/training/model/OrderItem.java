package com.redhat.training.model;

import java.io.Serializable;
import java.math.BigDecimal;


public class OrderItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer quantity;
	private BigDecimal extPrice;
	private CatalogItem item;

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getExtPrice() {
		return extPrice;
	}

	public void setExtPrice(BigDecimal extPrice) {
		this.extPrice = extPrice;
	}

	public CatalogItem getItem() {
		return item;
	}

	public void setItem(CatalogItem item) {
		this.item = item;
	}

	public Integer getId() {
		return id;
	}

    public void setId(Integer i) {
        this.id = i;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((extPrice == null) ? 0 : extPrice.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		OrderItem other = (OrderItem) obj;
		if (extPrice == null) {
			if (other.extPrice != null)
				return false;
		} else if (!extPrice.equals(other.extPrice))
			return false;

		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;

		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;

		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
			
		return true;
	}
}