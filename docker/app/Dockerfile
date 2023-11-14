ARG JDK_VERSION=11

FROM gradle:jdk${JDK_VERSION}-alpine as gradle-builder

FROM openjdk:${JDK_VERSION}-slim-stretch

COPY --from=gradle-builder /opt/gradle /opt/gradle
RUN ln -s /opt/gradle/bin/gradle /usr/bin/gradle
