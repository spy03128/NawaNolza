## 프론트 필독
1. 로컬용 데이터베이스 실행법
   1. ./server 경로에서 터미널 명령어 `docker-compose -f docker-compose-local.yml up --build` 입력
   2. DB들 올라가는거 확인!
   3. ServerApplication 클릭한 뒤 메인 메서드 옆에 재생버튼 클릭
   4. 스프링 실행되는거 확인
   5. 스프링이 실행 안된다면 https://jojoldu.tistory.com/547 여기 참고
      - profiles를 "local"로 해야됨!!!!!!! 
2. 로컬용 데이터베이스 종료법
   1. /server 경로에서 터미널 명령어 `docker-compose -f docker-compose-local.yml' down 입력
   2. (하고싶으면 해도됨) `docker ps` 명령어 치고 mysql, redis가 있는지 확인 