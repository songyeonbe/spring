# spring
토큰을 이용한 로그인 구현

## Spring Security 를 이용하여 토큰 방식의 로그인을 구현해본다.

### Spring MVC를 이용하여 사용자 가입 기능을 만든다.


1) User 스키마는 기본적으로 uuid, username(email 형식), password, name은 필수로 가지고 나머지는 자신이 필요하다고 생각하는 필드를 추가해서 구현한다.

[유저 스키마 구현](https://github.com/songyeonbe/spring/blob/master/src/main/java/com/jocoos/spring/domain/users/Users.java)

2) Spring MVC를 이용하여 사용자 가입 기능을 만든다.

[로그인 기능](https://github.com/songyeonbe/spring/blob/master/src/main/java/com/jocoos/spring/controller/UsersController.java)

3) User 저장은 Docker 로 띄운 PostgreSQL에 저장한다.
         ⇒ 신규로 저장되는 사용자는 기본적으로 USER role을 가진다. 

- H2로 연결해서 기능 구현 후, Docker로 PostgreSQL 연결 
         
4) Data Access 는 JPA를 이용한다.

[JPA Repository](https://github.com/songyeonbe/spring/blob/master/src/main/java/com/jocoos/spring/repository/UsersRepository.java) 

5) 샘플 어플리케이션 구동 시 기본적으로 Admin 사용자를 초기화한다.

7) 인증 방식은 username / password 로 하고, 인증이 완료되면 Spring Security가 제공하는 토큰 제너레이터를 이용하고, 토큰 생성 방식은 RSA 비대칭키를 이용한다.

- 토큰 생성 

8) 로그인한 사용자는 토큰을 가지고 이후 인증이 이루어지고, 이것을 테스트 하기 위해서 다음의 API를 추가적으로 구현한다.
9) 사용자 정보 조회 : 자기 정보만 조회가능, Admin은 자신은 물론 다른 사용자 정보도 조회 가능
10) 사용자 목록 조회 : Admin 만 조회가능
