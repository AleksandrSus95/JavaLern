package org.examples.optionalClass;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderAction {
    /**
     * Поиск заказа без использования Optional
     * @param orders
     * @param id
     * @return Order
     */
    public static Order findById(List<Order> orders, long id){
        Order order = null;
        List<Order> result = orders.stream().filter(o -> id == o.getId()).collect(Collectors.toList());
        if(result.size() != 0){
            order = result.get(0);
        }
        return order;
    }

    /**
     * Поиск заказа с использованием Optional
     * @param orders
     * @param id
     * @return Optional\<Order\>
     */
    public static Optional<Order> findByIdOptional(List<Order> orders, long id){
        Order order = null;
        List<Order> result = orders.stream().filter(o -> id == o.getId()).collect(Collectors.toList());
        Optional<Order> optionalOrder = result.size() != 0 ? Optional.of(result.get(0)) : Optional.empty();
        return optionalOrder;
    }
}
