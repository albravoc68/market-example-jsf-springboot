package cl.example.entities.entities.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserVO implements Serializable {

    private Integer id;
    private String username;
    private String password;
    private String fullName;
    private boolean enable;
    private int clientId;

}
