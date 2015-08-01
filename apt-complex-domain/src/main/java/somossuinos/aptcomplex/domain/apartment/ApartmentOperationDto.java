package somossuinos.aptcomplex.domain.apartment;

import somossuinos.aptcomplex.domain.finance.bill.BillItem;

import java.util.Set;

public class ApartmentOperationDto {
    private Set<BillItem> newItems;
    private Set<Long> removedItems;
    private Set<BillItem> updatedItems;

    public Set<BillItem> getNewItems() {
        return newItems;
    }

    public void setNewItems(Set<BillItem> newItems) {
        this.newItems = newItems;
    }

    public Set<Long> getRemovedItems() {
        return removedItems;
    }

    public void setRemovedItems(Set<Long> removedItems) {
        this.removedItems = removedItems;
    }

    public Set<BillItem> getUpdatedItems() {
        return updatedItems;
    }

    public void setUpdatedItems(Set<BillItem> updatedItems) {
        this.updatedItems = updatedItems;
    }
}
