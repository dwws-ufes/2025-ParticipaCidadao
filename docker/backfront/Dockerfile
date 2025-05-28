FROM ubuntu:24.04
RUN apt-get update && apt-get upgrade -y

RUN apt-get install curl -y
RUN curl -sL https://deb.nodesource.com/setup_20.x | bash -
RUN apt-get install nodejs -y
RUN npm install -g @angular/cli@19.2.14

RUN apt-get install git maven nano tmux openjdk-21-jdk -y

RUN mkdir /app

WORKDIR /app