FROM openjdk:8
LABEL maintainer="SAMATE, NIST"
WORKDIR /sard
RUN apt update && apt install -y --no-install-recommends curl make unzip ant
COPY . .
