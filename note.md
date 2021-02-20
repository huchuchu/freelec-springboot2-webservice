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
* Test클래스에서 `MockMvcRequestBuilders.get` 이 계속 import가 되지않았다.. 찾아보던 중 도구들의 버전 변경으로 수정해야 할 코드들이 많았다. 
  저자 블로그에 나온대로 build.gradle을 고쳤지만 계속 안됨..<br>
__as-is__
```
   testImplementation('org.springframework.boot:spring-boot-starter-test')

```
__to-be__
```
    testImplementation('org.springframework.boot:spring-boot-starter-test'){
        exclude group: 'org.junit.vintage', module: 'junit-vintage-engine'
    }

```
  로 고쳐주니 import되었다.
