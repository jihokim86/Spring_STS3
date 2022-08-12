package org.zerock.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Component
@ToString
@Getter
@AllArgsConstructor //생성자를 만들어주는 역할, 생성자가 있으면 중복되서 오류발생
public class SampleHotel {
	//@Autowired가 생략해도 묵시적으로 주입된거라고 봄. 하지만 되도록이면 생략하지 말자.
	@Autowired
	private Chef chef;
	@Autowired
	private String chef2;

	

}
