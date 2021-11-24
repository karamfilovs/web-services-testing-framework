package inv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Client {
    private String firm_name;
    private String firm_town;
    private String firm_addr;
    private boolean firm_is_reg_vat;
    private String firm_mol;
}
