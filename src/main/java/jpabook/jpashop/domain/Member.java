package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address Address;

    // order table 에 있는 member에 의해 mapping 됨! 주인이 order이다
    @OneToMany(mappedBy = "member") // member가 주인이 아니라는걸 말함
    private List<Order> orders = new ArrayList<>();



}
