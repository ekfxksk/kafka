## Kafka Connect

1. 프로듀서와 컨슈머 양쪽 모두에 구성이 가능하다.
2. 카프카는 특성상 다양한 시스템과 연계하기 때문에 Kafka Connect에서는 다른 시스템과 연결하는 부분을    
   '컨넥터(Connector)' 라는 플러그인으로 구현하는 방식을 가진다.  
3. 카프카에 데이터를 넣는 프로듀서 쪽 컨넥터를 '소스(Source)' 라고 부른다.
4. 카프카에서 데이터를 출력하는 컨슈머 쪽의 컨테러를 '싱크(Sink)'라고 부른다.



## 실행 명령어
   - ${KAFKA_HOME}/bin/connect-distributed.sh config/connect-distributed.properties
   

## API 명령어
   - https://docs.confluent.io/platform/current/connect/references/restapi.html




## 파일 컨넥트
   1) source
   echo '
      {
         "name" : "test-file-source", 
         "config" : {  
            "connector.class" : "org.apache.kafka.connect.file.FileStreamSourceConnector",     
            "file" : "/test/data/ddd.txt",  
            "topic" : "test-file"  
         }  
      }  
   ' | curl -X POST -d @- http://broker:8083/connectors --header \  
   "content-Type:application/json"  

   2) sink
   echo '
      {
         "name" : "test-file-sink", 
         "config" : {  
            "connector.class" : "org.apache.kafka.connect.file.FileStreamSinkConnector",     
            "file" : "/test/data/sink.txt",  
            "topics" : "test-file"  
         }  
      }  
   ' | curl -X POST -d @- http://broker:8083/connectors --header \  
   "content-Type:application/json"        

## JDBC 컨넥트
   1) connect 설치
      a. 다운로드
         - https://packages.confluent.io/archive 에서 필요한 파일 받기  
           ex) https://packages.confluent.io/archive/7.0/confluent-community-7.0.0.tar.gz

      b. jdbc 파일추가
         - {CONNECT 설치 경로}/share/java/kafka 에 추가


   2) 플러그인 설치
      a. 다운로드
         - https://www.confluent.io/hub/
           ex) https://d1i4a15mxbxib1.cloudfront.net/api/plugins/confluentinc/kafka-connect-jdbc/versions/10.3.x/confluentinc-kafka-connect-jdbc-10.3.x.zip

      b. 플러그인 연동
         - connect-distributed.properties 에 plugin.path={플러그인 설치경로}/lib

   3) source 추가
      echo '
      {
         "name" : "mariadb-soruce-connect",
         "config" : {
            "connector.class" : "io.confluent.connect.jdbc.JdbcSourceConnector",
            "connection.url" : "jdbc:mysql://mariadb_conn:3306/mydb",
            "connection.user": "root",
            "connection.password" : "root",
            "mode" : "incrementing",
            "incrementing.column.name" : "id",
            "table.whitelist" : "TB_USER",
            "topic.prefix" : "my_mariadb_",
            "tasks.max" : "1"
         }
      }
      ' | curl -X POST -d @- http://localhost:8083/connectors --header "content-Type:application/json"

   4) sink 추가

   echo '
   {
    "name" : "mariadb-sink-connect",
    "config" : {
        "connector.class" : "io.confluent.connect.jdbc.JdbcSinkConnector",
        "connection.url" : "jdbc:mysql://mariadb_conn:3306/mydb",
        "connection.user": "root",
        "connection.password" : "root",
        "auto.create" : "true",
        "auto.evolve" : "true",
        "delete.enabled" : "false",
        "tasks.max" : "1",
        "topics" : "my_mariadb_TB_USER"
    }
   }
   ' | curl -X POST -d @- http://localhost:8083/connectors --header "content-Type:application/json"


   // topics 명이 테이블명이다.