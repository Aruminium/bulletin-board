ARG JDK_VERSION=11

FROM gradle:jdk${JDK_VERSION}-alpine as gradle-builder

FROM openjdk:${JDK_VERSION}-slim-stretch as development
ENV TZ=Asia/Tokyo

COPY --from=gradle-builder /opt/gradle /opt/gradle
RUN ln -s /opt/gradle/bin/gradle /usr/bin/gradle

COPY ./app /var/tmp/app
WORKDIR /var/tmp/app
RUN gradle build && rm -rf /var/tmp/app/*
