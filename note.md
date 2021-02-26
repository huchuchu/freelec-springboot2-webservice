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
      
   ## 21/02/24
   ### Lombok
   * 롬복: Getter, Setter, 기본생성자, toString등을 어노테이션으로 자동생성해준다
   * @Getter : 선언된 모든 필드의 get메소드를 생성해준다
   * @RequiredArgsContructor : 선언된 모든 final필드가 포함된 생성자를 생성해준다(final이 없느 필드생성자는 포함x)<br>
   __Junit5에서 assertThat() 사용하기__
     ```
        import org.assertj.core.api.Assertions; <-- 이걸 import 해야함
        Assertion.assertThat()           
     ```
     안돼서 찾아보면 `hamcrest`를 import하라는데.. import자체가 안됐다..ㅜ
   * @assertThat : 테스트 검증 라이브러리. 검증하고 싶은 대상을 메소드 인자로 받는다
   * @isEqualTo : assertThat에 있는 값과 isEqualTo의 값을 비교해서 같을때만 성공
   * @RequestParam : 외부에서 API로 넘긴 파라미터를 가져오는 어노테이션

        ```
        @RequestParam("name") String name 
       => @RequestParam("name") 이름으로 넘긴 데이터를 String name에 저장한다
       ```
       ```
        mvc.perform(get("/hello/dto")
                                            .param("name", name)
                                            .param("amount", String.valueOf(amount)))
                    .andExpect(status().isOk())
                    .andExpect( jsonPath("name", is(name)))
                    .andExpect(jsonPath("amount", is(amount)));
       ```
   * param : API테스트 할 때 사용 될 요청 파라미터를 설정한다(값은 String만 가능)
   * JSON 응답값을 필드별로 검증할 수 있는 메소드. $를 기준으로 필드명을 명시한다
   * is : `import static org.hamcrest.Matchers.is`
   
   ## 21/02/25
   ### JPA
   JPA = 자바 표준 ORM(Object Realational Mapping) : 객체 매핑
   MyBits, iBatis = SQL Mapper : 쿼리 매핑
   RDBMS(관계형 데이터베이스) : 어떻게 데이터를 저장할지 에 초점
   객체지향 프로그래밍언어: 기능과 속성을 한 곳에서 관리
   ==> 패러다임 불일치
   ```
    JAP <- Hibernate <- Spring Data JPA
   ```
   Hibernate를 쓰지 않고 Spring Data JAP를 쓰는 이유
   * 구현체 교체의 용이성 : Hibernate 외에 다른 구현체로 쉽게 교체하기 위함 (Sprign Data JPA에서 구현체 매핑 지원)
   * 저장소 교체의 용이성 : 관계형 데이터메이스 외에 다른 저장소로 쉽게 교체하기 위함 (기본 CRUD 인터페이스가 같기때문에 저장소 교체가 용이하다)
   
   * 이 프로젝트에서 domain : 게시글, 댓글, 회원, 정산, 결제 등 소프트웨어에 대한 요구사항 혹은 문제영역
   ```
    @Getter
    @NoArgsConstructor
    @Entity
    class Posts {
    
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
    
        @Column(length = 500, nullable = false)
        private String title;
    
        @Column(columnDefinition = "TEXT", nullable = false)
        private String content;
    
    
        private String author;
    
        @Builder
        public Posts (String title, String content, String author){
            this.title = title;
            this.content = content;
            this.author = author;
    }
}
   ```
   * Posts 클래스는 실제 DB와 매칭될 클래스이며 Entity클래스라고도 한다. JPA를 사용하면 보통 실제 쿼리를 날리기보다 Entity클래스 수정을 통해 작업한다
   * @Entity : 테이블과 링크 될 클래스임을 나타냄
   * @Id : 해당 테이블의 PK필드를 나타냄
   * @GeneratedValue : PK생성규칙. 스프링부트 2.0에서는 GenerationType.IDENTITY를 추가해야 auto_imcrement가 된다
   * @Column : 테이블의 칼럼을 나타내며 굳이 선언하지않아도 해당 클래스의 필드는 모두 칼럼이 된다.<br>
               기본값 외에 추가로 변경이 필요한 옵션이 있으면 사용함.<br>
               문자열의 경우 VARCHAR(255)가 기본값인데 <br>
                 - 사이즈를 500으로 늘리고싶거나 <br>
                 - 타입을 TEXT로 변경하고싶거나 할 때 사용된다<br>
   * NoArgsConstructor : 기본생성자 자동 추가
   * Buider : 해당 클래스의 빌더 패턴 클래스를 생성. 생성자에 포함된 필드만 빌더에 포함
   * Entity클래스에는 __setter 메소드를 만들지 않는다.__ 값 변경이 필요할 경우 그 목적과 의도를 명확히하는 메소드를 추가한다.<br>
     ==> DB에 값을 채우는것은 기본적으로 생성자를 통하여 / 값 변경이 필요한경우는 public 메소드를 호출하여
   * Builder 클래스를 사용하면 어느 필드에 어떤값을 채워야하는지 명확하게 인지할 수 있다.  
   * repository : Dao 역할. 인터페이스로 생성 후 JpaRepository<Entity클래스, PK 타입>을 상속하면 기본적인 CRUD메소드가 생성된다.
    <br> Entity클래스와 기본 Entity Repository는 함께 위치해야한다!
   
   * repositoryTest
        - postRepository.save : 테이블 post에 insert/update 쿼리를 실행한다
        - id값이 있으면 update, 있으면 insert
   
   * 어떻게 쿼리 날리는지 보는 방법: application.properties에 `spring.jpa.show_sql=true` 추가하기

   ## 21/02/26
   ### 등록/수정/조회 API만들기
   API를 만들기 위해서는 총 세개의 클래스가 필요하다
```aidl
    * Request 데이터를 받을 Dto
    * API 요청을 받을 Controller
    * 트랜잭션, 도메인 기능 간의 순서를 보장하는 Service
```
   * service : 비즈니스로직을 처리하는것이 아니다. 트랜잭션, 도메인간 순서 보장의 역할만 한다
   * Dto(Data Transfer Object) : 계층 간에 데이터 교환을 위한 객체
   * Domain Model : 도메인이라 불리는 개발 대상을 모든 사람이 동일한 관점에서 이해할 수 있고 공유할 수 있도록 단순화 시킨 것
     <br>무조건 DB 테이블과 관계있어야하는 것은 아니고 VO처럼 값 객체들도 이영역에 해당한다
     <br>비즈니스 로직 처리
   
   * 생성자를 직접 만들지 않고 @RequiredArgsConstructor(롬복 어노테이션) 사용이유: 클래스의 의존성 관계가 변경될 때마다 생성자 코드를 계속 수정하는 번거로움을 해결할 수 있다
   
   * Entity 클래스와 거의 유사한 형태임에도 Dto클래스를 추가로 생성했다. 절대로 __Entity 클래스를 Request/Response 클래스로 사용해선 안된다!!__
     <br> Entity클래스는 DB와 맞닿은 핵심클래스이다. Entity를 기준으로 테이블이 생성되고, 스키마가 변경되기때문이다!
     <br> View Layer와 DB Layer의 역할 분리를 철저하게 하는 게 좋다!
     
   * JPA테스트토 함께 하는경우네는 @WebMvcTest를 사용하지않고 @SpringBootTest와 TestRestTemplate를 사용한다
   * [TestRestTemplate](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/test/web/client/TestRestTemplate.html)
     <br>[Static import](https://docs.oracle.com/javase/8/docs/technotes/guides/language/static-import.html) 
     <br>[ResponseEntity](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.html)
   
   * update 처리할 때 : 쿼리를 날리는 부분이 없는데 처리된다. 이것은 __JPA의 영속성 컨텍스트__ 때문이다. 
     <br>__영속성 컨텍스트__ : 엔티티를 영구 저장하는 환경.(JPA의 핵심내용은 엔티티가 영속성 컨텍스트에 포함되어 있냐 아니냐로 갈린다)
     <br> JPA의 엔티티 매니저(Spring Data JPA의 기본옵션)가 활성화된 상태로 __트랜잭션 안에서 DB데이터를 가져오면__ 데이터는 영속성 컨텍스트가 유지된 상태이다
     <br> 이 상태에서 해당 데이터의 값을 변경하면 __트랜잭션이 끝나는 시점에 해당 테이블에 변경분을 반영__한다.
     <br> 즉 Entity 객체의 값만 변경하면 별도로 UPDATE 쿼리를 날릴 필요가 없다는 것!!! <-- 더티 체킹(dirty checking)이라고 한다
     
     