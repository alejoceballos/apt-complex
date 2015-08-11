package somossuinos.aptcomplex.service;

import java.util.Set;

public class ApartmentOperationDTO<T> {
    private Set<T> newItems;
    private Set<Long> removedItems;
    private Set<T> updatedItems;

    public Set<T> getNewItems() {
        return newItems;
    }

    public void setNewItems(Set<T> newItems) {
        this.newItems = newItems;
    }

    public Set<Long> getRemovedItems() {
        return removedItems;
    }

    public void setRemovedItems(Set<Long> removedItems) {
        this.removedItems = removedItems;
    }

    public Set<T> getUpdatedItems() {
        return updatedItems;
    }

    public void setUpdatedItems(Set<T> updatedItems) {
        this.updatedItems = updatedItems;
    }
}
