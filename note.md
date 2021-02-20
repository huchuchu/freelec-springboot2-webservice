## 21/02/14
### 프로젝트 기본환경 셋팅
* 인텔리제이에서 git remote/commit
* alt+insert 새파일 / ctrl+k 커밋 / ctrl+shift+k 푸시

## 21/02/20
### 단위테스트
* @SpringBootApplication : 스프링부트의 자동 설정, 스프링 Bean읽기와 생성을 모두 자동으로 설정되게 해준다.<br>
  @SpringBootApplication이 있는 위치부터 설정을 읽어가기때문에 __이 클래스는 항상 프로젝트의 최 상단에 위치__해야한다<br>
  SpringApplication.run으로 내장 was를 실행한다
* @RestController : 컨트롤러를 JSON을 반환하는 컨트롤러로 만들어준다
  예전에 @ResponseBody를 각 메소드마다 선언했던것을 한번에 사용할 수 있게 해준다
* @GetMapping : HTTP Method인 Get 요청을 받을 수 있는 API를 만들어준다
* Test클래스에서 `MockMvcRequestBuilders.get` 과 `MockMvcResultMatchers.content/status` 가 계속 import가 되지않았다.. 찾아보니 도구들의 버전 변경으로 수정해야 할 코드들이 많았다. 
  저자 블로그에 나온대로 build.gradle을 고쳤지만 계속 안돼서 아래처럼 고쳤더니 import 됐다!
  
  
__as-is__
```
testImplementation('org.springframework.boot:spring-boot-starter-test')
```
__to-be__ <br>
```
    testImplementation('org.springframework.boot:spring-boot-starter-test'){
        exclude group: 'org.junit.vintage', module: 'junit-vintage-engine'
    }
    testImplementation('org.springframework.restdocs:spring-restdocs-mockmvc')
    testImplementation('org.junit.jupiter:junit-jupiter-api')
    testRuntime('org.junit.jupiter:junit-jupiter-engine')
```
   
  * @Runwith => @ExtendWith (SpringRunner => SpringExtension) : 
    - 테스트를 진행할 때 JUnit에 내장된 실행자 외에 다른 실행자를 실행시킨다
    - 여기서는 SpringExtension 이라는 스프링 실행자를 사용한다
    - 즉, 스프링 부트 테스트와 JUnit 사이에 연결자 역할을 한다
  * @WebMvcTest
    - 선언시 @Controller, @CoontrollerAdvice등을 사용할 수 있다
    - 단 @Service, @Conponetnt, @Repository는 사용불가
    - 여기서는 컨트롤러만 사용하기때문에 선언한다
  * @privateMockMvc mvc 
    - 웹 API를 테스트할 때 사용한다
    - 이 클래스를 통해 HTTP GET, POST등에 대한 API테스트를 할 수 있다
  * mvc.perform(get("/hello")) : Mockmvc를 통해 /hello 주소로 HTTP GET 요청을 함, 체이닝이 지원되어 여러 검증 기능을 이어서 선언 할 수 있다
    - .andExpect(status().isOk()) : 
      + mvc.perform의 결과를 검정한다
      + HTTP Header의 Status를 검증한다 (흔히 알고있는 200, 404, 500등의 상태 검증)
      + 여기서는 .isOk 즉 200인지 아닌지를 검증한다
    - .andExpect(content().string(hello))
      + 응답 본문의 내용을 검증한다
      + Controller에서 "hello"를 리턴하기 때문에 이 값이 맞는지 검증한다
      
  ### Lombok
   롬복: Getter, Setter, 기본생성자, toString등을 어노테이션으로 자동생성해준다
   * @Getter : 선언된 모든 필드의 get메소드를 생성해준다
   * @RequiredArgsContructor : 선언된 모든 final필드가 포함된 생성자를 생성해준다(final이 없느 필드생성자는 포함x)

  
  
  
  
