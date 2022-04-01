# LossLeader

## 기능
- 일반 회원 회원가입 및 로그인
- 일반 회원정보 수정
- 간편 로그인(네이버, 카카오)
- 마이페이지(회원정보 수정, 주문내역, 주문정보, 리뷰보기)
- 리뷰 등록
- 쿠폰 주문
- 업체 정보 리스팅(등급순 or 별점순 or 등록순)
- 업체 상세 보기
## BackEnd 개발환경
![image](https://user-images.githubusercontent.com/67584874/161248342-09f73242-9ca6-466f-b7fd-1482425a8aa7.png)

## 개발환경 버전
- Spring Boot :  2.6.3
- OpenJdk : 11.0.1
- gradle : 7.3.3
- docker : 20.10.7
- docker-compose : 2.2.3
- Ubuntu : 18.0.4

## 프로젝트 시작 방법 및 세팅 방법

### 세팅 방법
- mysql 세팅 방법 : docker-compose.yml 파일
```
version: '2'
services:
  db:
    image: mysql:latest
    container_name: lossleader-mysql
    ports:
      - "3306:3306"
    environment:
      - MYSQL_DATABASE=lossleader
      - MYSQL_ROOT_PASSWORD=lossleaderBack12!
      - TZ=Asia/Seoul
    command:
      - --character-set-server=utf8mb4
      - --collation-server=utf8mb4_unicode_ci
    volumes:
      - ./lossleader-mysql/origin-data:/var/lib/mysql
```
- minio 세팅 방법 : docker-compose.yml 파일
```
version: '2'
services:
 minio:
  image: minio/minio:RELEASE.2021-06-17T00-10-46Z.hotfix.35a0912ff
  container_name: myminio
  environment:
   MINIO_ACCESS_KEY: lossleader
   MINIO_SECRET_KEY: lossleader123
  volumes:
    - ./storage/origin-data:/data
  ports:
   - "9000:9000"
  command: server /data
 mc:
  image: minio/mc:latest
  depends_on:
   - minio
  entrypoint: >
    /bin/sh -c "
    /usr/bin/mc config host rm local;
    /usr/bin/mc config host add --quiet --api s3v4 local http://myminio:9000/ lossleader  lossleader123;
    /usr/bin/mc mb --quiet local/store;
    /usr/bin/mc mb --quiet local/review;
    /usr/bin/mc policy set public local/store;
    /usr/bin/mc policy set public local/review;
    "
```

### 시작 방법
1. 프로젝트를 git clone 명령어를 사용하여 내려받는다.
2. 젠킨스 빌드 명령어
```
```
3. git 연결
4. 프로젝트 빌드 명령어
```
./gradlew clean build -x test -Pprofile=prod
```
5. docker 파일 빌드 명령어
```
docker build -t jenkins/lossleader .
```
6. 기존 컨테이너 삭제
```
docker ps -q --filter "name=jenkins-lossleader" && docker stop jenkins-lossleader && docker rm jenkins-lossleader | true
```
7. 도커 컨테이너 실행
```
docker run -p 8000:8000 -d --name=jenkins-lossleader jenkins/lossleader
```

8. 필요없는 이미지 제거
```
docker rmi -f $(docker images -f "dangling=true" -q) || true
```

*참고 
- 명령어는 Build - Excute shell에 작성

