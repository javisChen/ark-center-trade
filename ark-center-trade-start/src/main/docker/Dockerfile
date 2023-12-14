FROM openjdk:17-oraclelinux8

#RUN microdnf install -y iputils

WORKDIR /app

COPY ark-center-trade.jar /app

EXPOSE 8085

ENV SYS_OPT=""

ENTRYPOINT java $SYS_OPT -jar /app/ark-center-trade.jar