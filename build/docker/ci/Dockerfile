FROM opuscapita/minsk-core-maven:3.3.9

# maintainer
LABEL maintainer="egor.stambakio@opuscapita.com"

# installing
# - sdkman (https://sdkman.io/)
# And via sdkman installing
# - groovy
# - grails
ENV SDKMAN_DIR /usr/local/sdkman
RUN curl -s https://get.sdkman.io | bash && \
    set -x && \
    echo "sdkman_auto_answer=true" > $SDKMAN_DIR/etc/config && \
    echo "sdkman_auto_selfupdate=false" >> $SDKMAN_DIR/etc/config && \
    echo "sdkman_insecure_ssl=false" >> $SDKMAN_DIR/etc/config && \
    /bin/bash -c "source $SDKMAN_DIR/bin/sdkman-init.sh && \
       sdk install groovy 2.4.13 && \
       sdk install grails 2.4.4"

# add groovy, grails to PATH
ENV PATH $SDKMAN_DIR/candidates/groovy/current/bin:$SDKMAN_DIR/candidates/grails/current/bin:$PATH

# copy config files
COPY --from=opuscapita/minsk-core-ci:1 /root/.grails/settings.groovy /root/.grails/

# install docker according to https://docs.docker.com/install/linux/docker-ce/debian/
RUN apt-get update && \
    apt-get install -y \
      unzip \
      apt-transport-https \
      ca-certificates \
      curl \
      gnupg2 \
      software-properties-common && \
    curl -fsSL https://download.docker.com/linux/debian/gpg | apt-key add - && \
    add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/debian  $(lsb_release -cs) stable" && \
    apt-get update && \
    apt-cache policy docker-ce && \
    apt-get install -y docker-ce docker-ce-cli containerd.io

# install vault
RUN curl -fsSLk -o /tmp/vault.zip https://releases.hashicorp.com/vault/1.2.2/vault_1.2.2_linux_amd64.zip \
  && unzip /tmp/vault.zip -d /usr/local/bin/ \
  && rm -f /tmp/vault.zip

# Add 'properties' (https://github.com/OpusCapita/properties) binary file into '/usr/local/bin/'
RUN curl -sL https://git.io/oc-properties | PROPERTIES_TARGET_DIR=/usr/local/bin bash
