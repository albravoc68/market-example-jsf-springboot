package cl.example.entities.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Table(name = "_admin")
@NoArgsConstructor
@Setter
@Getter
public class AdminEntity {

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

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false)
    private ClientEntity client;

}
