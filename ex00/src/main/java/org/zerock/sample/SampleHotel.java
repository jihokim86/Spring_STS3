package org.zerock.sample;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.ToString;

@Component
@ToString
@Getter
public class SampleHotel {
	//@Autowired가 생략해도 묵시적으로 주입된거라고 봄. 하지만 되도록이면 생략하지 말자.
	private Chef chef;
	
	public SampleHotel(Chef chef) {
		this.chef=chef;
	}
}
