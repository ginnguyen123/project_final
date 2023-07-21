package cg.dto.customerDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerInfoDTO {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String dateOfBirth;
}
