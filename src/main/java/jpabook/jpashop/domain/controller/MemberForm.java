package jpabook.jpashop.domain.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "회원이름은 필수입니다.")
    private String name;
    @NotEmpty(message = "거주하고 있는 도시를 입력해주세요")
    private String city;
    @NotEmpty(message = "거주중인 도시의 거리를 입력하세요")
    private String street;
    @NotEmpty(message = "우편번호를 입력 하세요")
    private String zipcode;
}
