# 👀 C'YES Porting Manual

### 1. **Develop Environment**

> #### 1.1 MICRO SERVICE

    1. 모놀리식 젠킨스 파이프라인

```
> jenkins 파일
       pipeline {
       agent any
       stages {
         //백엔드
               stage('BE build') {
                   steps {
                       dir('Server/webserver'){
                           sh '''
                           pwd
                           echo 'springboot build'
                           chmod +x gradlew
                           ./gradlew clean build -x test
                           '''
                       }
                   }
               }
               stage('BE Dockerimage build') {
                   steps {
                       dir('Server/webserver'){
                           sh '''
                           echo 'Dockerimage build'
                           docker build -t docker-springboot:0.0.1 .
                           '''
                       }
                   }
               }
               stage('BE Deploy') {
                   steps {
                       dir('Server/webserver'){

                           sh '''
                           echo 'Deploy'

                           result=$( docker container ls -a --filter "name=cyes_back" -q )
                           if [ -n "$result" ]; then
                                   docker stop $result
                                   docker rm $result

                               else
                                   echo "No such containers"
                               fi
                           docker run -d -p 127.0.0.1:5000:5000 -p 1026:5000 --name cyes_back -e JAVA_OPTS="-Duser.timezone=Asia/Seoul" docker-springboot:0.0.1


                           docker images -f "dangling=true" -q | xargs -r docker rmi
                           '''
                       }
                   }
               }


               //프론트 엔드
               stage('FE build') {
                   steps {
                       dir('Front/cyesfront'){
                           sh '''
                               pwd
                               echo 'Frontend build'
                                DEBIAN_FRONTEND=noninteractive apt install -y npm

                               npm install
                               CI=false npm run build
                           '''
                       }
                   }
               }
               stage('FE Dockerimage build') {
                   steps {
                       dir('Front/cyesfront'){
                           sh '''
                               echo 'Dockerimage build'
                               docker build --no-cache -t cyes_front:0.0.1 .
                           '''
                       }
                   }
               }
               stage('FE Deploy') {
                   steps {
                       dir('Front/cyesfront'){

                           sh '''
                               echo 'FE Deploy'

                           result=$( docker container ls -a --filter "name=cyes_front" -q )
                           if [ -n "$result" ]; then
                                   docker stop $result
                                   docker rm $result

                               else
                                   echo "No such containers"
                               fi

                           docker run -d -p 127.0.0.1:9510:80 --name cyes_front cyes_front:0.0.1
                           docker images -f "dangling=true" -q | xargs -r docker rmi
                           '''
                       }
                   }
               }
           }
       }


```

    1. 모놀리식 젠킨스 파이프라인

```
> 마이크로 서비스로 변경한 후 젠킨스 파일

pipeline {
    agent any

stages {


      //백엔드
            stage('BE build') {

                steps {

                    dir('Server/webserver'){
                        sh '''
                        pwd
                        echo 'springboot build'
                        chmod +x gradlew
                        ./gradlew clean build -x test
                        '''
                    }
                }
            }


            stage('BE Dockerimage build') {

                steps {

                    dir('Server/webserver'){
                        sh '''
                        echo 'Dockerimage build'
                        docker build -t docker-springboot:0.0.1 .
                        '''
                    }
                }
            }


            stage('BE Deploy') {

                steps {

                    dir('Server/webserver'){

                        sh '''
                        echo 'Deploy'

                        result=$( docker container ls -a --filter "name=cyes_back" -q )
                        if [ -n "$result" ]; then
                                docker stop $result
                                docker rm $result

                            else
                                echo "No such containers"
                            fi

                                echo "gogo"

                        docker images -f "dangling=true" -q | xargs -r docker rmi
                        '''
                    }
                }
            }


            //프론트 엔드
            stage('FE build') {
                steps {
                    dir('Front/cyesfront'){
                        sh '''
                            pwd
                            echo 'Frontend build'
                             DEBIAN_FRONTEND=noninteractive apt install -y npm

                            npm install
                            CI=false npm run build
                        '''
                    }
                }
            }
            stage('FE Dockerimage build') {
                steps {
                    dir('Front/cyesfront'){
                        sh '''
                            echo 'Dockerimage build'
                            docker build --no-cache -t cyes_front:0.0.1 .
                        '''
                    }
                }
            }
            stage('FE Deploy') {
                steps {
                    dir('Front/cyesfront'){

                        sh '''
                            echo 'FE Deploy'

                        result=$( docker container ls -a --filter "name=cyes_front" -q )
                        if [ -n "$result" ]; then
                                docker stop $result
                                docker rm $result

                            else
                                echo "No such containers"
                            fi



                        echo "gogo"
                        docker images -f "dangling=true" -q | xargs -r docker rmi
                        '''
                    }
                }
            }


              stage('MSA Container backend') {
                    steps {
                        dir('/'){

                          script {
                    def fileName = 'spring-boot.tar'

                    // 파일이 존재하는지 확인
                    if (fileExists(fileName)) {
                        echo "Deleting ${fileName}"
                        // 파일 삭제
                        sh "rm ${fileName}"
                    } else {
                        echo "${fileName} does not exist. Skipping deletion."
                    }



                }


                sh '''
                        docker save -o spring-boot.tar docker-springboot:0.0.1

                        result=$(ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker container ls -a --filter 'name=cyes_back' -q")
                        if [ -n "$result" ]; then

                                ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "rm spring-boot.tar"
                                scp -i /jenkins_key /spring-boot.tar ubuntu@k9b103a.p.ssafy.io:/home/ubuntu
                                ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker stop cyes_back"
                                ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker rm cyes_back"
                                ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker rmi docker-springboot:0.0.1"

                            else
                                echo "No such containers"
                            fi

                            ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker load -i spring-boot.tar"
                            ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker run -d -p 127.0.0.1:5000:5000 -p 1026:5000 --name cyes_back -e JAVA_OPTS="-Duser.timezone=Asia/Seoul" docker-springboot:0.0.1"
                    '''
                        }

                        }
                    }

                 stage('MSA Container frontend') {
                    steps {
                        dir('/'){

                script {
                    def fileName = 'cyes_front.tar'

                    // 파일이 존재하는지 확인
                    if (fileExists(fileName)) {
                        echo "Deleting ${fileName}"
                        // 파일 삭제
                        sh "rm ${fileName}"
                    } else {
                        echo "${fileName} does not exist. Skipping deletion."
                    }

                }

                sh '''

                        docker save -o cyes_front.tar cyes_front:0.0.1


                            result=$(ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker container ls -a --filter 'name=cyes_front' -q")
                        if [ -n "$result" ]; then

                                ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "rm cyes_front.tar"
                                ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker stop cyes_front"
                                ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker rm cyes_front"
                                ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker rmi cyes_front:0.0.1"

                            else
                                echo "No such containers"
                            fi
                            scp -i /jenkins_key /cyes_front.tar ubuntu@k9b103a.p.ssafy.io:/home/ubuntu
                            ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker load -i cyes_front.tar"
                            ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker run -d -p 127.0.0.1:9510:80 --name cyes_front cyes_front:0.0.1"
                    '''
                        }

                        }
                    }
        }
    }
```

> ### 1.2 Nginx Setting

    모놀리식 아키텍처 경우 nginx 설정

```
> nginx -> site-enabled -> default

server
{
        listen 80 default_server;
        listen [::]:80 default_server;

        server_name cyes.site k9b103.p.ssafy.io;
        return 301 https://$server_name$request_uri; # HTTP를 HTTPS로 리다이렉트
}

server
{
        listen 11111 default_server;
        listen [::]:11111 default_server;
        server_name cyes.site k9b103.p.ssafy.io;

        location /metrics {

                stub_status on;

        }

}

server {
    listen 443 ssl default_server;
    listen [::]:443 ssl default_server;

    server_name cyes.site k9b103.p.ssafy.io;

    ssl_certificate /etc/letsencrypt/live/cyes.site/fullchain.pem;
    ssl_certificate_key /etc/letsencrypt/live/cyes.site/privkey.pem;

    location / {
        proxy_pass http://localhost:9510;
        add_header 'Access-Control-Allow-Origin' '*';
        add_header 'Access-Control-Expose-Headers' 'Authorization, Authorizationrefresh';
        include /etc/nginx/proxy_params;

        proxy_buffer_size          128k;
        proxy_buffers              4 256k;
        proxy_busy_buffers_size    256k;
    }
    location /api {
        proxy_pass http://localhost:5000;
        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection 'upgrade';
        proxy_set_header Host $host;
        proxy_cache_bypass $http_upgrade;
    }
    location /monitor/ {
        proxy_pass http://localhost:19999/;
        proxy_set_header Host $host;

        # WebSocket 지원을 위한 추가 설정
        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection "upgrade";
    }

    location /api-docs/ {
        proxy_pass_request_headers on;
        proxy_set_header Host $host;
        proxy_http_version 1.1;
        proxy_pass http://localhost:5000/api-docs/;
    }
}
```

> ### work 서버 nginx default 파일

    마이크로 서비스의 경우  nginx deafalt 설정.

```
server
{
        listen 80 default_server;
        listen [::]:80 default_server;

        server_name cyes.site k9b103a.p.ssafy.io;
        return 308 https://$server_name$request_uri; # HTTP를 HTTPS로 리다이렉트
}

server
{
        listen 11111 default_server;
        listen [::]:11111 default_server;
        server_name cyes.site k9b103.p.ssafy.io;

        location /nginx_sub_metrics {

                stub_status on;
        }

}


server {
    listen 443 ssl default_server;
    listen [::]:443 ssl default_server;

    server_name cyes.site k9b103a.p.ssafy.io;

    ssl_certificate /etc/letsencrypt/live/cyes.site/fullchain.pem;
    ssl_certificate_key /etc/letsencrypt/live/cyes.site/privkey.pem;

    location / {
        proxy_pass http://localhost:9510;
        add_header 'Access-Control-Allow-Origin' '*';
        add_header 'Access-Control-Expose-Headers' 'Authorization, Authorizationrefresh';
        include /etc/nginx/proxy_params;

        proxy_buffer_size          128k;
        proxy_buffers              4 256k;
        proxy_busy_buffers_size    256k;
    }

    location /api {
        proxy_pass http://localhost:5000;
        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection 'upgrade';
        proxy_set_header Host $host;
        proxy_cache_bypass $http_upgrade;
    }

}
```

---

> 소켓만 떼어놓은 코드

```
pipeline {
    agent any

stages {

    stage('MSA Container Deploy') {
                    steps {
                        dir('/'){

                          script {
                    def fileName = 'spring-boot.tar'

                    // 파일이 존재하는지 확인
                    if (fileExists(fileName)) {
                        echo "Deleting ${fileName}"
                        // 파일 삭제
                        sh "rm ${fileName}"
                    } else {
                        echo "${fileName} does not exist. Skipping deletion."
                    }



                }
                sh '''
                        docker save -o spring-boot.tar docker-springboot:0.0.1

                        ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "rm spring-boot.tar"

                        scp -i /jenkins_key /spring-boot.tar ubuntu@k9b103a.p.ssafy.io:/home/ubuntu

                        result=$(ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker container ls -a --filter 'name=cyes_back' -q")
                        if [ -n "$result" ]; then
                                ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker stop cyes_back"
                                ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker rm cyes_back"
                                ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker rmi docker-springboot:0.0.1"

                            else
                                echo "No such containers"
                            fi





                            ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker load -i spring-boot.tar"
                            ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker run -d -p 127.0.0.1:5000:5000 -p 1026:5000 --name cyes_back -e JAVA_OPTS="-Duser.timezone=Asia/Seoul" docker-springboot:0.0.1"
                    '''
                        }

                        }
                    }


      //백엔드
            stage('BE build') {

                steps {

                    dir('Server/webserver'){
                        sh '''
                        pwd
                        echo 'springboot build'
                        chmod +x gradlew
                        ./gradlew clean build -x test
                        '''
                    }
                }
            }


            stage('BE Dockerimage build') {

                steps {

                    dir('Server/webserver'){
                        sh '''
                        echo 'Dockerimage build'
                        docker build -t docker-springboot:0.0.1 .
                        '''
                    }
                }
            }


            stage('BE Deploy') {

                steps {

                    dir('Server/webserver'){

                        sh '''
                        echo 'Deploy'

                        result=$( docker container ls -a --filter "name=cyes_back" -q )
                        if [ -n "$result" ]; then
                                docker stop $result
                                docker rm $result

                            else
                                echo "No such containers"
                            fi
                        docker run -d -p 127.0.0.1:5000:5000 -p 1026:5000 --name cyes_back -e JAVA_OPTS="-Duser.timezone=Asia/Seoul" docker-springboot:0.0.1


                        docker images -f "dangling=true" -q | xargs -r docker rmi
                        '''
                    }
                }
            }


            //프론트 엔드
            stage('FE build') {
                steps {
                    dir('Front/cyesfront'){
                        sh '''
                            pwd
                            echo 'Frontend build'
                             DEBIAN_FRONTEND=noninteractive apt install -y npm

                            npm install
                            CI=false npm run build
                        '''
                    }
                }
            }
            stage('FE Dockerimage build') {
                steps {
                    dir('Front/cyesfront'){
                        sh '''
                            echo 'Dockerimage build'
                            docker build --no-cache -t cyes_front:0.0.1 .
                        '''
                    }
                }
            }
            stage('FE Deploy') {
                steps {
                    dir('Front/cyesfront'){

                        sh '''
                            echo 'FE Deploy'

                        result=$( docker container ls -a --filter "name=cyes_front" -q )
                        if [ -n "$result" ]; then
                                docker stop $result
                                docker rm $result

                            else
                                echo "No such containers"
                            fi

                        docker run -d -p 127.0.0.1:9510:80 --name cyes_front cyes_front:0.0.1
                        docker images -f "dangling=true" -q | xargs -r docker rmi
                        '''
                    }
                }
            }
        }
    }

```

---

### 스프링, 레디스, 프론트 옮기기

```
pipeline {
    agent any

stages {


      //백엔드
            stage('BE build') {

                steps {

                    dir('Server/webserver'){
                        sh '''
                        pwd
                        echo 'springboot build'
                        chmod +x gradlew
                        ./gradlew clean build -x test
                        '''
                    }
                }
            }


            stage('BE Dockerimage build') {

                steps {

                    dir('Server/webserver'){
                        sh '''
                        echo 'Dockerimage build'
                        docker build -t docker-springboot:0.0.1 .
                        '''
                    }
                }
            }


            stage('BE Deploy') {

                steps {

                    dir('Server/webserver'){

                        sh '''
                        echo 'Deploy'

                        result=$( docker container ls -a --filter "name=cyes_back" -q )
                        if [ -n "$result" ]; then
                                docker stop $result
                                docker rm $result

                            else
                                echo "No such containers"
                            fi
                        <!-- docker run -d -p 127.0.0.1:5000:5000 -p 1026:5000 --name cyes_back -e JAVA_OPTS="-Duser.timezone=Asia/Seoul" docker-springboot:0.0.1 -->
                                echo "gogo"

                        docker images -f "dangling=true" -q | xargs -r docker rmi
                        '''
                    }
                }
            }


            //프론트 엔드
            stage('FE build') {
                steps {
                    dir('Front/cyesfront'){
                        sh '''
                            pwd
                            echo 'Frontend build'
                             DEBIAN_FRONTEND=noninteractive apt install -y npm

                            npm install
                            CI=false npm run build
                        '''
                    }
                }
            }
            stage('FE Dockerimage build') {
                steps {
                    dir('Front/cyesfront'){
                        sh '''
                            echo 'Dockerimage build'
                            docker build --no-cache -t cyes_front:0.0.1 .
                        '''
                    }
                }
            }
            stage('FE Deploy') {
                steps {
                    dir('Front/cyesfront'){

                        sh '''
                            echo 'FE Deploy'

                        result=$( docker container ls -a --filter "name=cyes_front" -q )
                        if [ -n "$result" ]; then
                                docker stop $result
                                docker rm $result

                            else
                                echo "No such containers"
                            fi

                        <!-- docker run -d -p 127.0.0.1:9510:80 --name cyes_front cyes_front:0.0.1 -->

                        echo "gogo"
                        docker images -f "dangling=true" -q | xargs -r docker rmi
                        '''
                    }
                }
            }


              stage('MSA Container backend') {
                    steps {
                        dir('/'){

                          script {
                    def fileName = 'spring-boot.tar'

                    // 파일이 존재하는지 확인
                    if (fileExists(fileName)) {
                        echo "Deleting ${fileName}"
                        // 파일 삭제
                        sh "rm ${fileName}"
                    } else {
                        echo "${fileName} does not exist. Skipping deletion."
                    }



                }


                sh '''
                        docker save -o spring-boot.tar docker-springboot:0.0.1

                        result=$(ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker container ls -a --filter 'name=cyes_back' -q")
                        if [ -n "$result" ]; then

                                ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "rm spring-boot.tar"
                                scp -i /jenkins_key /spring-boot.tar ubuntu@k9b103a.p.ssafy.io:/home/ubuntu
                                ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker stop cyes_back"
                                ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker rm cyes_back"
                                ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker rmi docker-springboot:0.0.1"

                            else
                                echo "No such containers"
                            fi

                            ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker load -i spring-boot.tar"
                            ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker run -d -p 127.0.0.1:5000:5000 -p 1026:5000 --name cyes_back -e JAVA_OPTS="-Duser.timezone=Asia/Seoul" docker-springboot:0.0.1"
                    '''
                        }

                        }
                    }

                 stage('MSA Container frontend') {
                    steps {
                        dir('/'){

                script {
                    def fileName = 'cyes_front.tar'

                    // 파일이 존재하는지 확인
                    if (fileExists(fileName)) {
                        echo "Deleting ${fileName}"
                        // 파일 삭제
                        sh "rm ${fileName}"
                    } else {
                        echo "${fileName} does not exist. Skipping deletion."
                    }

                }

                sh '''

                        docker save -o cyes_front.tar cyes_front:0.0.1


                            result=$(ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker container ls -a --filter 'name=cyes_front' -q")
                        if [ -n "$result" ]; then

                                ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "rm cyes_front.tar"
                                scp -i /jenkins_key /cyes_front.tar ubuntu@k9b103a.p.ssafy.io:/home/ubuntu
                                ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker stop cyes_front"
                                ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker rm cyes_front"
                                ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker rmi cyes_front:0.0.1"

                            else
                                echo "No such containers"
                            fi

                            ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker load -i cyes_front.tar"
                            docker run -d -p 127.0.0.1:9510:80 --name cyes_front cyes_front:0.0.1
                    '''
                        }

                        }
                    }
        }
    }

```

---

### 스프링, 소켓 스프링, 레디스, 프론트 옮기기

```
pipeline {
    agent any

stages {


      // webserver 백엔드
            stage('webserver BE build') {

                steps {

                    dir('Server/webserver'){
                        sh '''
                        pwd
                        echo 'springboot build'
                        chmod +x gradlew
                        ./gradlew clean build -x test
                        echo 'Dockerimage build'
                        docker build -t docker-springboot:0.0.1 .

                        echo 'Deploy'

                        result=$( docker container ls -a --filter "name=cyes_back" -q )
                        if [ -n "$result" ]; then
                                docker stop $result
                                docker rm $result

                            else
                                echo "No such containers"
                            fi

                                echo "gogo"

                        docker images -f "dangling=true" -q | xargs -r docker rmi
                        '''
                    }
                }
            }


            // socketserver 백엔드
            stage('socketserver BE build') {

                steps {

                    dir('Server/socketserver'){
                        sh '''
                        pwd
                        echo 'springboot build'
                        chmod +x gradlew
                        ./gradlew clean build -x test
                        '''
                    }
                }
            }


            stage('socketserver Dockerimage build') {

                steps {

                    dir('Server/socketserver'){
                        sh '''
                        echo 'Dockerimage build'
                        docker build -t socket-springboot:0.0.1 .
                        '''
                    }
                }
            }


            stage('socketserver BE Deploy') {

                steps {

                    dir('Server/socketserver'){

                        sh '''
                        echo 'Deploy'

                        result=$( docker container ls -a --filter "name=cyes_socket" -q )
                        if [ -n "$result" ]; then
                                docker stop $result
                                docker rm $result

                            else
                                echo "No such containers"
                            fi

                                echo "gogo"

                        docker images -f "dangling=true" -q | xargs -r docker rmi
                        '''
                    }
                }
            }


            //프론트 엔드
            stage('FE build') {
                steps {
                    dir('Front/cyesfront'){
                        sh '''
                            pwd
                            echo 'Frontend build'
                             DEBIAN_FRONTEND=noninteractive apt install -y npm

                            npm install
                            CI=false npm run build

                            echo 'Dockerimage build'
                            docker build --no-cache -t cyes_front:0.0.1 .

                            echo 'FE Deploy'

                        result=$( docker container ls -a --filter "name=cyes_front" -q )
                        if [ -n "$result" ]; then
                                docker stop $result
                                docker rm $result

                            else
                                echo "No such containers"
                            fi



                        echo "gogo"
                        docker images -f "dangling=true" -q | xargs -r docker rmi
                        '''
                    }
                }
            }


              stage('MSA webserver backend') {
                    steps {
                        dir('/'){

                    // webserver 파일이 존재하는 지 확인

                          script {
                    def fileName = 'spring-boot.tar'

                    // 파일이 존재하는지 확인
                    if (fileExists(fileName)) {
                        echo "Deleting ${fileName}"
                        // 파일 삭제
                        sh "rm ${fileName}"
                    } else {
                        echo "${fileName} does not exist. Skipping deletion."
                    }

                }

                sh '''

                        docker save -o spring-boot.tar docker-springboot:0.0.1

                        result=$(ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker container ls -a --filter 'name=cyes_back' -q")
                        if [ -n "$result" ]; then

                                ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "rm spring-boot.tar"
                                ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker stop cyes_back"
                                ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker rm cyes_back"
                                ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker rmi docker-springboot:0.0.1"

                            else
                                echo "No such containers"
                            fi
                            scp -i /jenkins_key /spring-boot.tar ubuntu@k9b103a.p.ssafy.io:/home/ubuntu
                            ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker load -i spring-boot.tar"
                            ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker run -d -p 127.0.0.1:5000:5000 -p 1026:5000 --name cyes_back -e JAVA_OPTS="-Duser.timezone=Asia/Seoul" docker-springboot:0.0.1"
                    '''
                        }

                        }
                    }


                stage('MSA socketserver backend') {
                    steps {
                        dir('/'){

                    // websocket  파일이 존재하는 지 확인

                        script {
                    def fileName = 'socket-boot.tar'

                    // 파일이 존재하는지 확인
                    if (fileExists(fileName)) {
                        echo "Deleting ${fileName}"
                        // 파일 삭제
                        sh "rm ${fileName}"
                    } else {
                        echo "${fileName} does not exist. Skipping deletion."
                    }

                }


                sh '''

                        docker save -o socket-boot.tar socket-springboot:0.0.1

                        ls -a

                        result=$(ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker container ls -a --filter 'name=cyes_socket' -q")
                        if [ -n "$result" ]; then

                                ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "rm socket-boot.tar"
                                ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker stop cyes_socket"
                                ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker rm cyes_socket"
                                ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker rmi socket-springboot:0.0.1"

                            else
                                echo "No such containers"
                            fi

                            scp -i /jenkins_key /socket-boot.tar ubuntu@k9b103a.p.ssafy.io:/home/ubuntu
                            ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker load -i socket-boot.tar"
                            ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker run -d -p 127.0.0.1:5001:5001 -p 1027:5001 --name cyes_back -e JAVA_OPTS="-Duser.timezone=Asia/Seoul" socket-springboot:0.0.1"
                    '''
                        }

                        }
                    }

                 stage('MSA Container frontend') {
                    steps {
                        dir('/'){

                script {
                    def fileName = 'cyes_front.tar'

                    // 파일이 존재하는지 확인
                    if (fileExists(fileName)) {
                        echo "Deleting ${fileName}"
                        // 파일 삭제
                        sh "rm ${fileName}"
                    } else {
                        echo "${fileName} does not exist. Skipping deletion."
                    }

                }

                sh '''

                        docker save -o cyes_front.tar cyes_front:0.0.1


                            result=$(ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker container ls -a --filter 'name=cyes_front' -q")
                        if [ -n "$result" ]; then

                                ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "rm cyes_front.tar"
                                ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker stop cyes_front"
                                ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker rm cyes_front"
                                ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker rmi cyes_front:0.0.1"

                            else
                                echo "No such containers"
                            fi
                            scp -i /jenkins_key /cyes_front.tar ubuntu@k9b103a.p.ssafy.io:/home/ubuntu
                            ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker load -i cyes_front.tar"
                            ssh -i /jenkins_key ubuntu@k9b103a.p.ssafy.io "docker run -d -p 127.0.0.1:9510:80 --name cyes_front cyes_front:0.0.1"
                    '''
                        }

                        }
                    }
        }
    }

```

---

> ### 모놀리식 마스터 nginx default

```
# Default server configuration
server
{
        listen 80 default_server;
        listen [::]:80 default_server;

        server_name k9b103.p.ssafy.io;
        return 308 https://$server_name$request_uri; # HTTP를 HTTPS로 리다이렉트
}

server
{
        listen 11111 default_server;
        listen [::]:11111 default_server;
        server_name k9b103.p.ssafy.io;

        location /metrics {

                stub_status on;

        }

}

server {
    listen 443 ssl default_server;
    listen [::]:443 ssl default_server;

    server_name k9b103.p.ssafy.io;

    ssl_certificate /etc/letsencrypt/live/k9b103.p.ssafy.io/fullchain.pem;
    ssl_certificate_key /etc/letsencrypt/live/k9b103.p.ssafy.io/privkey.pem;

    location /monitor/ {
        proxy_pass http://localhost:19999/;
        proxy_set_header Host $host;

        # WebSocket 지원을 위한 추가 설정
        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection "upgrade";
    }
}



```
