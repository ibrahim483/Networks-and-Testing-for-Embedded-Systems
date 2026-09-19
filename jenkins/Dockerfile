# Jenkins LTS med JDK 21 + Maven + Git förinstallerat.
FROM jenkins/jenkins:lts-jdk21

USER root
RUN apt-get update \
 && apt-get install -y --no-install-recommends maven git \
 && rm -rf /var/lib/apt/lists/*
USER jenkins

# Hoppa över "första gången"-guiden? Nej - vi vill se den en gång.
# Plugins som installeras automatiskt vid första start:
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli --plugin-file /usr/share/jenkins/ref/plugins.txt
