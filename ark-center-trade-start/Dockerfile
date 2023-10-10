FROM openjdk:17-oraclelinux8
WORKDIR /app
COPY ./target/ark-center-trade.jar /app
EXPOSE 8080
ENV SYS_OPT=""
ENTRYPOINT java $SYS_OPT -jar /app/ark-center-trade.jar
