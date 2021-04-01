## 1. EC2 인스턴스 생성하기
* EC2(Elastic Computer Cloud) : AWS에서 제공하는 성능, 용량등을 유동적으로 사용할 수 있는 서버
* 리전 : AWS의 서비스가 구동될 지역. AWS는 도시별로 클라우드 센터를 지어 해당 센터에서 구축된 가상머신들을 사용할 수 있다.
* 인스턴스 : EC2서비스에 생성된 가상머신

1) EC2 대시보드에서 인스턴스 시작 클릭
2) AMI 선택
    * AMI(Amazom Machine Image, 아마존 머신 이미지) : EC2 인스턴스를 시작하는데 필요한 정보를 이미지로 만들어 둔 것,인스턴스라는 
    가상머신에 운영체제등을 설치할 수 있게 구워 넣은 이미지
    * Amazon Linux 2 AMI (HVM), SSD Volume Type 을 선택한다
        - Amazon Linux AMI 은 서비스 종료
        - 리눅스1은 CentOS6버전으로 진행되는 자룔를 사용할 수 있다
        - 리눅스2는 CentOS7버전 자료들을 그대로 사용할 수 있다
        - CentOS AMI를 사용하지않고 Linux AMI를 사용하는 이유 : 아마존이 개발하고있기때문에 지원받기 쉽다
3) 인스턴스 유형 선택 : __t2.micro__ 선택
![1](https://user-images.githubusercontent.com/58330668/112711657-5ea2b380-8f0d-11eb-8654-ceed9d4e8465.PNG)

    * t2 : 요금 타입, micro : 사양        
    * t2외에 t3도 있으며, 보통 이들을 T시리즈라고 한다. (*다른 시리즈는 nano, micro등의 저사양이 존재하지않는다)        
    * __크레딧__ 이란 일종의 CPU를 사용할 수 있는 포인트 개념이 있다. 
    <br> 인스턴스 크기에 따라 정해진 비율로 CPU크레딧을 계속 받게 되며, 사용하지 않을 때는 크레딧을 축적하고, 사용할 때 이 크레딧을 사용한다.
    <br> 정해진 사양보다 높은 트래픽이 오면 크레딧을 좀 더 적극적으로 사용하며 트레픽을 처리하지만
    __크레딧이 모두 사용되면 더 이상 EC2를 사용할 수 없다.__
    * 트래픽이 높은 서비스들은 T시리즈를 사용하지않고 다른 시리즈를 사용한다

4) 인스턴스 세부 정보 구성
VPC와 서브넷등은 AWS 서비스들의 네트워크 환경을 구성하는 정도로만 이해하고 넘어가자(일단은..)

5) 스토리지 추가
    * 스토리지 : 하드디스크라고 부르는 서버의 디스크(SSD 포함)를 이야기하며, 서버의 용량을 얼마나 정할지 선택하는 단계
    * 설정의 기본값은 8GB이고 30CB까지 프리티어로 가능하다(그 이상은 비용청구 됨)
    * 크기를 30GB로 변경해준다

6) 태그 추가
    * 웹 콘솔에서 표시 될 태그인 Name 태그를 등록한다.(EC2에 이름을 붙인다고 생각하면 된다)
![3](https://user-images.githubusercontent.com/58330668/112712159-f9e95800-8f10-11eb-9d0d-6cdb72e86180.PNG)

7) 보안 그룹 구성
    * 보안그룹 : __방화벽__
    * __'서버로 80포트 외에는 허용하지 않는다'는__ 역할을 하는 방화벽이 AWS에서는 보안그룹으로 사용된다
    * 기존에 생성된 보안그룹이 없으므로 __유의미한 이름__으로 변경한다
    ![파일_000](https://user-images.githubusercontent.com/58330668/112712272-c0651c80-8f11-11eb-985a-ca85e0dbc67c.jpeg)
    *유형항목에서 SSH면서 포트 범위가 22인 경우 __AWS EC2에 터미널로 접속__ 할 때를 이야기한다
    <br> pem키가 없으면 접속이 안되니 전체오픈(0.0.0.0/0, ::/0)하는 경우가 종종 있는데, 실수로 pem키가 노출되는 순간 서버에서 
    가상화폐가 채굴되는 것을 볼 수 있다.
    <br> 보안을 위해 pem키 관리와 __지정된 IP에서만 SSH접속이 가능__ 하도록 구성하는것이 안전하다
    <br> 본인 집 IP를 기본적으로 추가하고, 카페와 같이 집 외의 장소에서 접속할 때 SSH규칙에 추가하는 것이 안전하다
    * 현재 프로젝트의 기본포트를 추가한다

8) 인스턴스 검토
    * 검토 화면에서 경고창이 나오는데 이는 기본포트가 전체오픈 되어서 발생하는 것이다. 
    기본포트를 열어놓는 것은 위험한 일이 아니므로 바로 시작하기 버튼을 클릭한다
    
9) pem 키 발급
    * 인스턴스로 접근하기 위해서는 pem키(비밀키)가 필요하다. 
    인스턴스는 pem키와 매칭되는 공개키를 가지고 있어, 해당 pem키 외에는 접근을 허용하지 않는다.   
    일종의 마스터키이기때문에 절대절대 유출되면 안된다.
    * 키 페어 이름을 성정 한 후 키페어 다운로드를 클릭한다

10) 인스턴스를 생성한다

11) 고정IP 할당
    * 인스턴스 생성시와 같은 인스턴스를 중지하고 다시 시작할 떄 새 IP가 할당된다
    * 인스턴스의 IP가 매번 변경되지 않고 고정 IP를 가지게 해야한다 -> 고정IP/EIP 할당
    * AWS의 고정 IP를 Elastic IP(EIP, 탄력적 IP)라고 한다.
        1. EC2 서비스 / 네트워크 및 보안 / 탄력적 IP를 클릭하여 새 주소를 할당 받아 EIP를 생성한다
        2. EIP와 EC2를 연결한다 : EIP페이지의 작업탭에서 주소연결을 클릭하여 인스턴스와 연결한다
        3. EC2 서비스 / 인스턴스 메뉴를 클릭하여 EIP가 잘 할당 됐는지 확인한다.
    * EIP를 생성하고 EC2 서버에 연결하지않으면 비용이 발생한다. 즉 생성한 EIP는 무조건 바로 EC2에 연결해야한다

12) 인스턴스 생성 완료!

## 2. EC2 서버에 접속

1) 실행파일 2가지를 내려받는다
    + putty.exe
    + puttygen.exe : pem키를 ppk파일로 변환해주는 클라이언트

2) puttygen으로 pem키를 ppk파일로 변환해준다
3) putty에 접속 설정을 등록한다
<br>
![파일_000](https://user-images.githubusercontent.com/58330668/112713242-99114e00-8f17-11eb-8345-2e3a2c4a705c.png)
     
    + HostName : username@public_IP를 등록한다. Amazon Linux는 ec2-user가 username이기때문에 ec2-user@EIP 주소를 등록하면 된다
    + PORT : SSH 접속 포트인 22를 등록한다
    + Connect type : SSH를 등록한다
 <br>   
![putty2](https://user-images.githubusercontent.com/58330668/113257554-bbcfa800-9305-11eb-996b-00dc53c02d09.png)
    
    + 카테고리에서 Connection/SSH/Auth를 클릭한다
    + 생성한 ppk파일을 선택하여 불러온다
    + session 탭으로 이동하여 Saved Session에 현재 설정들을 저장할 이름을 등록하고 save 버튼을 클릭한다.
    
4) open 클릭하여 접속되는지 확인하기!

## 3. 인스턴스 생성 후 추가 설정
1) JAVA8 설치
    * EC2에서 다음 명령어를 실행한다
    <br>`sudo yum instal -y java-1.8.0-openjdk-devel.x86_64`
    * 설치 완료 후 인스턴스의 java 버전을 8로 변경한다
    <br>`sudo /usr/sbin/alternatives --config java`
        - 1.8 버전만 화면에 나와서 1.7삭제는 진행안했다. 만약 삭제해야한다면
        <br>`sudo yum remove java-1.7.0-openjdk`
    * java 버전 확인하기
    <br>`java -version`    
2) 타임존 변경 : 기본 서버의 시간은 미국시간대이기때문에 한국시간으로 변경
    * EC2 서버의 기본 타임존은 UTC이다. 이것은 세계 표진 시간으로 한국 시간대가 아니다(9시간 차이남)
    * 다음 명령어를 차례로 수행한다
    <br>`sudo rm /etc/localtiome`
    <br>`sudo ln -s /usr/share/zoneinfo/Asia/Seoul /ect/localtime`
    * `date`명령어로 확인하기
3) 호스트네임 변경 : 현재 접속한 서버의 별명 등록(IP로 구분하기 어려움)
    * 여러 서버를 관리할 경우 IP만으로 어떤 서비스의 서버인지 확인이 어렵다. 어느 서비스인지 HOSTNAME을 변경한다
    * [Amazon Linux 인스턴스에서 호스트 이름 변경](https://docs.aws.amazon.com/ko_kr/AWSEC2/latest/UserGuide/set-hostname.html)
    * 변경 후 재접속하면 hostname이 변경된것을 확인할 수 있다
    * `curl 등록한 호스트이름` : 잘못 등록되었으면 찾을 수 없는 주소라는 에러 발생/ 잘 등록되었다면 80 포트로 접근이 안된다는 에러 발생
    <br> 이것은 앚기 80포트로 실행된 서비스가 없음을 의미한다 -> 즉, curl 호스트 이름으로 실행은 잘 되었음을 의미