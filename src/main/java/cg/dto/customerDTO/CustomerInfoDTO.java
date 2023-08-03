package cg.dto.customerDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerInfoDTO {
    private Long id;
    private String currentUsername;
    private String fullName;
    private String email;
    private String phone;
    private String dateOfBirth;

    public CustomerInfoDTO (Long id, String fullName, String email, String phone, String strDateOfBirth){
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = strDateOfBirth;
    }
}
