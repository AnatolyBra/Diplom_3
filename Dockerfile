FROM jenkins/jenkins:lts
# if we want to install via apt
USER root
# Install "software-properties-common" (for the "add-apt-repository")

# Add the "JAVA" ppa
RUN apt-get install -y --no-install-recommends software-properties-common
RUN add-apt-repository -y ppa:webupd8team/java
RUN add-apt-repository -y ppa:openjdk-r/ppa
RUN apt-get update
RUN apt-get install -y openjdk-8-jdk

USER jenkins/jenkins:lts # drop back to the regular jenkins user - good practice