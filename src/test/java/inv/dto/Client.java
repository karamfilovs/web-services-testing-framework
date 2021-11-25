package inv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Client {
    private String name;
    private String town;
    private String address;
    private boolean is_reg_vat;
    private String mol;
    private String bulstat;
}
