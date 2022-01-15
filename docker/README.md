## Kafka Docker


 - confluent Kafka를 참조하여 진행한다.   
   https://developer.confluent.io/quickstart/kafka-docker/

     
## 1. 구성도

- 개발 환경이기에 Zookeeper 1대, Kafka 1대로 구성

    
## 2. Kafka Docker 시작/종료 명령어
- 시작 : docker-compose up -d 
- 종료 : docker-compose down

## 3. kafka 명령어
1. Topic 생성 : 
    - docker exec broker kafka-topics --bootstrap-server broker:9092 --create --topic quickstart
    
2. 메시지 발행
    - docker exec --interactive --tty broker kafka-console-producer --bootstrap-server broker:9092 --topic quickstart

3. 메시지 수신
    - docker exec --interactive --tty broker kafka-console-consumer --bootstrap-server broker:9092 --topic quickstart --from-beginning
