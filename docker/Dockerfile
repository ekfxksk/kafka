FROM centos:7

# 빌드 시점에 사용되는 변수
ARG kafka_version=2.13-3.0.0

# 환경변수
ENV KAFKA_VERSION=$kafka_version \
    KAFKA_HOME=/app/kafka_${kafka_version}

#ENV PATH=${PATH}:${KAFKA_HOME}/bin

# centos7 기본 모듈 설치
RUN yum -y update \ 
    && yum -y install net-tools \
    && yum -y install java-11-openjdk-devel.x86_64 \
    && yum -y install wget


## app 경로 생성
RUN mkdir -p /app

# app 경로로 이동
WORKDIR /app

# 설정 파일 처리 쉘 파일 복사
ADD config.sh /app

# kafka 다운로드
RUN chmod a+x config.sh \
    && wget https://dlcdn.apache.org/kafka/3.0.0/kafka_${KAFKA_VERSION}.tgz \
    && tar -xzf kafka_${KAFKA_VERSION}.tgz


VOLUME ["/data"]

CMD [ "/bin/bash", "config.sh" ]