package com.fullstack.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fullstack.model.SampleDTO;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/sample")//컨텓스트 패스맵핑을 컨트롤러에 적용하는 어노테이션입니다
//이렇게 하게되면 localhost/sample 들어오는 요청은 모두 이 클래스가 받아냅니다
@Log4j2
public class SampleController {
	@GetMapping({"/ex1"})//여기는 컨텍스트 하위 요청 정보를 처리하는 메서드의 맵퍼입니다
	//위의 맵퍼를 적용시 Viewer를 따로 지정하지 않게되면 같은 이름의 Viewer를 호출해서 사용합니다
	public void ex1() {
		log.info("ex1메서드가 실행되었습니다. ex1요청 들어옴");
	}
	//하위의 메서드에는 Viewer 에서 처리할 Model을 파라미터로 전달해서
	//View인 타임리프에서 태그를 통해 모델의 정보를 출력하도록 합니다
	//모델을 파라미터로 줄떄는 반드시 메서드에 모델 정보를 줘야 합니다
	@GetMapping({"/ex2","/link"})
	public void ex2(Model model) {
		//List<SampleDTO>
		List<SampleDTO> list = IntStream.rangeClosed(1, 20).asLongStream().mapToObj(i->{
			SampleDTO dto = SampleDTO.builder().sno(i).first("심"+i).last("지석"+i).regTime(LocalDateTime.now()).build();
			return dto;
			
		}).collect(Collectors.toList());
		model.addAttribute("list",list);
	}
	/*
	 * 이번엔 틀 내용운 컨트롤러에서 리다이렉션을 시키는 방법과 이때 파라미터를 전달하는 방법을 볼 예정입니다
	 * 
	 * 호출되는 메서드에는 파라미터로 RedirectAttributes를 넣어주면
	 * 호출시 위 객체를 사용할 수 있도록 자동 주입(Inject)됩니다
	 * 이후 아래와 같은 형식으로 데이터를 보낼수 있습니다
	 * 리다이렉트는 기본적으로 요청을 재작성 하기 때문에 meodel.addAttribute("list",list);처럼 할 수 있습니다
	 * 저넹 jsp에서 봤듯이
	 * 
	 * 해서 전달할 값을 세션 영역에 넣어서 보냐려 헙니다
	 * 이때 사용하는 메서드가 addFlashAttribute(key, value) 입니다
	 * 
	 * 또한 컨트롤러의 매서드가 응답후 리다이렉트를 하려면 반드시 리턴타입이 String 이어야하고
	 * return "redirect:URL Path";형태를 전송해야 합나다
	 */
	@GetMapping("/exInline")
	public String exInline(RedirectAttributes redirectAttributes) {
		
		SampleDTO dto = SampleDTO.builder().sno(77L).first("심").last("지석").regTime(LocalDateTime.now()).build();
		
		redirectAttributes.addFlashAttribute("result","sucess");
		redirectAttributes.addFlashAttribute("dto", dto);
		return "redirect:/sample/ex3";
	}

	@GetMapping({"/ex4"})
	public void ex4(Model model) {
		List<SampleDTO> list = IntStream.rangeClosed(1, 20).asLongStream().mapToObj(i->{
			SampleDTO dto = SampleDTO.builder().sno(i).first("심"+i).last("지석"+i).regTime(LocalDateTime.now()).build();
			return dto;
			
		}).collect(Collectors.toList());
		model.addAttribute("list",list);
	}
	
	/*
	 * 페이지 layout을 적용하는 include를 위한 메서드 정의 
	 */
	@GetMapping({"/exLayout1","/exLayout2"})
	public void exLayout1() {
		log.info("레이아웃1 메서드 호출");
	}
	
	
}
