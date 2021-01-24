package cl.example.entities.entities;

import cl.example.entities.entities.vo.UserVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "_user")
@NoArgsConstructor
@Setter
@Getter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "username", length = 45, nullable = false)
    private String username;

    @Basic
    @Column(name = "_password", columnDefinition = "text", nullable = false)
    private String password;

    @Basic
    @Column(name = "fullname", length = 50, nullable = false)
    private String fullName;

    @Basic
    @Column(name = "enable", nullable = false)
    private Boolean enable;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false)
    private ClientEntity client;

    public UserVO toVO() {
        UserVO vo = new UserVO();
        BeanUtils.copyProperties(this, vo);
        vo.setClientId(client.getId());

        return vo;
    }

}
