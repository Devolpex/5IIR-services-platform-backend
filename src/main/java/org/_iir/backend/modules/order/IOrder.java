package org._iir.backend.modules.order;

import java.util.List;

import org._iir.backend.interfaces.IService;

public interface IOrder<E, DTO, CREQ, UREQ, ID> extends IService<E, DTO, CREQ, UREQ, ID> {

    /**
     * Fetch orders by user ID.
     *
     * @return a list of DTOs representing the orders of the user
     */
    List<DTO> fetchOrdersByUser();

    /**
     * Confirm an order.
     *
     * @param orderId the ID of the order to confirm
     * @return the updated DTO of the confirmed order
     */
    DTO confirmOrder(ID orderId);

    /**
     * Cancel an order.
     *
     * @param orderId the ID of the order to cancel
     * @return the updated DTO of the canceled order
     */
    DTO cancelOrder(ID orderId);

}
