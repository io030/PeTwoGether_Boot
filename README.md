# PeTwoGether_SpringBoot
펫 케어 서비스 웹 사이트 프로젝트 PeTwoGether(Spring 구현 부분) Java11, STS4, Oracle, Thymeleaf

추가적으로 테이블 정의서 및 쿼리문 정의서 파일 내 쿼리를 통해 DB 구축

관리자, 펫 시터, 일반회원으로 역할을 분리하여 각 기능을 구현

1. 전문 펫시터에게 가능한 날짜 및 시간에 돌봄 신청
2. 해당 돌봄 기간동안의 특이사항을 기록한 돌봄 일지 작성 및 조회 
3. 펫시터의 정보 및 후기 기능
4. 유기동물 입양 및 잃어버린 반려동물 찾기 기능
5. 전문가에게 질문 기능 및 답변 작성 

개요
- 전문 펫시터들한테 믿고 반려동물을 맡길 수 있다,입양 가능한 유기동물들을 조회하고 입양할 수 있으며 잃어버린 반려동물을 찾는 게시물을 작성할 수 있다
- 일반회원의 기능(유기동물 입양 + 찾기, 질문게시판, 의료정보 게시판, 입양 후기) 구현,나머지는 Spring에서 구현

Language
- Java 11
- JavaScript
- SQL/PL
  
Development tool
- Oracle SQL Developer
- STS4
 
Skills
- Thymeleaf
- jQuery
- Ajax
- OpenAPI
- Mybatis

프로젝트 참여 인원
- 팀장: 임동균
- 팀원: 5명
 
개발 기간
- 2023.01.16 ~ 2023.01.25

ERD
![image](https://github.com/io030/PeTwoGether_Boot/assets/114460492/836504b1-3478-434f-8fb5-107074b9d38c)

|담당 기능|내용|
|:--|---|
|질문 게시판|이용자가 반려동물을 키우면서 궁금했던 것들을 전문가한테 질문을 작성할 수 있는 게시판이다. 사진과 내용을 입력하여 작성할 수 있다. 작성된 질문은 다른 이용자와 전문가들이 조회할 수 있다.|
|답변 기능|질문 게시판에 작성된 질문들에 대한 답변을 작성할 수 있다. 전문가만이 이용할 수 있는 기능이다. 질문 당 오직 하나의 답변을 달 수 있도록 구현했다.|

<details>
    <summary>구현기능 화면</summary>

![image](https://github.com/io030/PeTwoGether_Boot/assets/114460492/ceec1cf2-9e43-40f7-91a2-18d91dc539ae)

![image](https://github.com/io030/PeTwoGether_Boot/assets/114460492/30ff7908-94c9-4532-b2a5-323c40b763a2)

</details>
