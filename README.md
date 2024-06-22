# 전체 개요  
## https://github.com/code-4-devdoc/DevDoc  
## https://github.com/code-4-devdoc/DevDoc-FE  
## https://github.com/code-4-devdoc/DevDoc-BE  
<br/>

---

# 개발환경
### 프론트
`$ npm install` : vulnerability 무시 (라이브러리 제작자쪽 문제)
<br/>
`$ npm start` : `.env.development` ~ baseUrl = localhost:8080 설정
<br/>

### 백엔드
`$ gradlew clean build`
<br/>
`$ cd build/libs/`
<br/>
`$ java -jar backend-0.0.1.jar --spring.profiles.active=dev` : DB = H2 설정
<br/>

### DB(H2)
`/src/main/resources/init.sql` : database 생성
<br/>
<br/>

# 배포환경(도커 내에서 실행)
### 프론트
`$ npm run build` : `.env.production` ~ baseUrl = 크램폴린배포주소 설정
<br/>

### 백엔드
`$ java -jar -Dspring.profiles.active=prod backend-0.0.1.jar` : DB = MariaDB 설정
<br/>

### DB(MariaDB)
`/k8s/configs/init.sql` : database 생성
<br/>
<br/>

---

# 배포순서
### 1. FE/BE/DB 각각 따로 GitHub 개인 레포지토리에 푸시
- 팀 레포지토리에서는 유효하지 않은 요청으로 뜸 ..
- MySQL 컨테이너 생성에 권한? 문제로 인해 ImagePullBackOff 에러 무한 반복 ..
- MariaDB 이미지 사용 (db: krampoline / root-password: root) ~ 컨테이너 생성
- 레포지토리 각각에서의 컨테이너가 필요 ~ 프론트/백 레퍼지토리 분리 필요
- 브랜치명 main 확인
<br/>

### 2. D2Hub 레포지토리 생성 - 빌드 - 이미지 주소 복사  
![스크린샷 2024-06-09 162200](https://github.com/code-4-devdoc/dev-prod-test/assets/130027416/646d290e-fdc3-44c8-a99b-594f2317fa6d)
<br/>
<br/>

### 3. `/k8s/frontend.yaml` `/k8s/backend.yaml` `/k8s/mariadb.yaml` 파일 수정  
- 도커 이미지 주소 붙여넣고 GitHub 푸시
- Kakao API / Kakao OAuth / JWT 사용 시 주석 해제 ~ kubectl 명령어로 secret-key 입력 필요
<br/>

### 4. (필요 시) Kargo 앱 배포 전 kubectl 으로 Secret-Key 등록 (최초 1회)  
> kubectl create secret generic kakao-api-secret --from-literal=KAKAO_API_KEY=your_kakao_api_key
<br/>

> kubectl create secret generic oauth-secret --from-literal=KAKAO_OAUTH_CLIENT_ID=your_kakao_oauth_client_id --from-literal=KAKAO_OAUTH_CLIENT_SECRET=your_kakao_oauth_client_secret
<br/>

> kubectl create secret generic jwt-secret --from-literal=JWT_SECRETKEY=your_jwt_secret_key
<br/>

### 5. Kargo 앱 배포 (실패되는게 정상) - 외부 URL 주소 복사
![스크린샷 2024-06-09 163507](https://github.com/code-4-devdoc/dev-prod-test/assets/130027416/f38cd61d-20e3-4a78-8655-e562fc793b59)
<br/>
<br/>

### 6. `FE/../.env.production` `BE/../src/main/../config/WebConfig.java` 파일 수정
- 외부 URL 주소 붙여넣고 GitHub 푸시
<br/>

### 7. D2Hub 재빌드
- 레퍼지토리 그대로 사용 (이미지 주소도 그대로)
- 이미지만 새로 생성하면됨
<br/>

### 8. 앱 배포
- URL 재발급 X -> 재발급 시 5번 단계부터 다시 진행
<br/>

### 9. 메모용 Kubectl 명령어
`$ kubectl get secrets`: 모든 secret-key 조회
<br/>
`$ kubectl delete secret {secret-key}`: 해당 secret-key 삭제
<br/>
`$ kubectl get pods`: 모든 pod 조회
<br/>
`$ kubectl logs {pod}`: 해당 pod 로그 조회
<br/>
`$ kubectl describe pod {pod}`: 해당 pod 상세정보 조회
<br/>
`$ kubectl logs -f {pod}`: 해당 pod 실시간 로그 조회
