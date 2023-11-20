# 기본 이미지로 Node.js를 사용
FROM node:14 as build
# 작업 디렉토리 설정
WORKDIR /app
# 의존성 파일 복사
COPY package.json package-lock.json ./
# 의존성 설치
RUN npm install
# 소스 코드 복사
COPY . .
# 애플리케이션 빌드
RUN npm run build

# Nginx를 사용하여 React 애플리케이션 서빙
FROM nginx:alpine
# React 빌드 결과물을 Nginx 서버로 복사
COPY --from=build /app/build /usr/share/nginx/html
# 커스텀 Nginx 설정 파일 복사
COPY nginx/custom.conf /etc/nginx/conf.d/default.conf

# 서울 시간대로 설정
RUN apk --no-cache add tzdata
ENV TZ=Asia/Seoul

# 포트 80 노출
EXPOSE 80
# Nginx 실행
CMD ["nginx", "-g", "daemon off;"]
