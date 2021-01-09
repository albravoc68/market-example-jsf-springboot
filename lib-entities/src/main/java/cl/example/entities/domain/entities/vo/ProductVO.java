package cl.example.entities.domain.entities.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductVO implements Serializable {

    private Integer id;
    private String name;
    private String pictureUrl;
    private int price;
    private int clientId;

}
