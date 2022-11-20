# 서비스 소개

## 1. 서비스 설명

**개요**

- 한줄소개 : 아이들이 스마트폰을 이용하여 친구들과 밖에서 뛰어놀 수 있는 액티비티 컨텐츠를 제공하는 App
- 서비스명 : **나와, 놀자!**

**타켓**

- 스마트폰을 이용하여 친구들과 밖에서 뛰어놀고 싶은 아이들
- 정적인 게임 어플이 지루한 아이들

## 2. 기획 배경

**배경**

코로나 팬데믹 이후, 아이들의 스마트폰 의존도가 갈수록 높아지고 있습니다. 이로 인해 아이들의 활동량이 현저히 줄어들어 체력 저하, 소아비만 비율이 늘어나는 등의 문제가 발생하고 있습니다.

**목적**

아이들이 스마트폰을 이용하여 밖에서 친구들과 활동적인 게임을 할 수 있도록 다양한 액티비티 게임 컨텐츠를 제공함으로써 아이들의 야외 활동을 장려하기 위해 프로젝트를 기획하였습니다.

**설명**

- 기존 숨바꼭질 놀이에 GPS 기반 게임적 요소를 추가하여 스마트폰이 익숙한 아이들의 흥미를 유발시키면서 미션을 통한 색다른 즐거움 제공합니다.
- 실시간 위치를 기반으로 현재 내 주변의 귀여운 캐릭터를 수집하며 다양한 미니게임을 제공합니다.
- 추가적으로 휴대성이 좋은 갤럭시 워치를 연동하여 보다 게임을 편하게 진행할 수 있도록 하였습니다.
- '나와, 놀자!' 어플을 통해 아이들이 밖에서 활동적으로 움직이며 활동량을 늘림과 동시에 아이스 브레이킹, 사회성 향상의 효과를 기대하고 있습니다.

## 3. 서비스 화면

1. 로그인 & 메인화면

![로그인메인화면](/uploads/f8a41223c120f280dbf338bc27958b45/로그인메인화면.png)

2. 숨바꼭질 

![게임_설정](/uploads/1651fa1c1cf47eac03104875cc9c2e4f/게임_설정.jpg)
![입장](/uploads/79a43ed6d795d7cb99df7a85fb0ed6ea/입장.jpg)

![술래](/uploads/3a5b97d9a261483777a7edb5f522ca1f/술래.jpg)
![숨는팀_화면](/uploads/4e75f86f1b3d564186d363f9c5654bd3/숨는팀_화면.jpg)

![결과](/uploads/b0055bff156378ddd5753366e766db12/결과.jpg)
![워치](/uploads/b5848b096fb1d32009d0597e4b08ee53/워치.png)

3. 캐릭터 수집

![케릭터_도감](/uploads/91785030d384413bb9e04209aaf475c3/케릭터_도감.jpg)
![캐릭터수집](/uploads/3bdf34e4109d788e2c205402e25395ac/캐릭터수집.jpg)

![게임1](/uploads/bf5380b143919b7649d135bce5f0fce3/게임1.jpg)
![게임2](/uploads/fa451654ef1972f4f2b732c32cc6b2c8/게임2.jpg)

![게임3](/uploads/80c9923a4f524e394a5952e4bd4d2a95/게임3.jpg)
![게임4](/uploads/f756812181cab7bcbe11edf45a5d6b88/게임4.jpg)

# 프로젝트 아키택쳐

## 1.  아키텍쳐 - ERD

![erd](/uploads/96c901218572d67ec47d6c9f18e13848/erd.png)

## 2. 아키텍쳐 - PROJECT 

![아키텍처](/uploads/e54b2a418fa78c273e4babd4fc93da80/아키텍처.png)

## 3. 웹 소켓 (STOMP)

![웹소켓](/uploads/5f1a3411cbb0ee5fea4ee60916362a94/웹소켓.png)

## 4. 기술 스택

![기술스택](/uploads/0039bc91ab5351a00180d239f753d958/기술스택.png)

# 프로젝트 진행

Agile 방법으로 프로젝트를 진행했으며 Jira를 사용하여 프로젝트 일정 및 이슈를 관리하였습니다. 또한, Scrum 방식을 채택하여 일주일 단위의 스프린트를 통해 커뮤니케이션 리소스를 줄였습니다.

## 1. Git Flow

Git Flow는 최종 배포를 위한 Master, 배포 및 테스트를 위한 develop, 세부 기능을 개발할 수 잇는 feature/(기능명) 브랜치로 구성하여 Git Flow를 구성하였습니다.

![깃플로우](/uploads/0e1fe99c7c9f55deda38c413cb6432a0/깃플로우.png)

## 2. Jira

매주 월요일에 스프린트 시작, 데일리 스크럼을 통해 팀원들의 진행상황 공유하였습니다.

- 에픽은 가장 큰 단위인 공통일정, 기획, 설계, 홈 화면, 숨바꼭질, 캐릭터 수집, CI/CD 등으로 구성하였습니다.
- 스토리는 해야 할 기능을 적었으며 그 밑에 sub task를 두어 세부적으로 할 일을 적었습니다.

![번다운차트](/uploads/a67c40cf6593b5413eb307cbf4094a97/번다운차트.png)

## 3. Notion

데일리 스크럼 , API문서, 발표정리 등 문서들에 대한 자료는 Notion을 통해서 작성했습니다.

![노션1](/uploads/2bd61db566574e322c21c7445c1a512a/노션1.png)

![노션2](/uploads/ebebbc14bb88dbc0f24638dfc1b7d660/노션2.png)

![노션3](/uploads/cce91e5ceb3b0f9cd30f32086b2d5dc2/노션3.png)

# 배포

포팅메뉴얼에 포함되어있습니다. 참고하세요!

# 팀원 소개

![팀원소개1](/uploads/9db4dcc5b3856a21efa3fb64f8367f49/팀원소개1.png)

![팀원소개2](/uploads/8b78c591bfa3f9a87b0cecd4d877ac8b/팀원소개2.png)
