package inv.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Item {
    private String name;
    private double price_for_quantity;
    private String quantity_unit;
    private double price;
    private String currency;

}
