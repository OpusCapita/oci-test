FROM opuscapita/minsk-core-maven:3.3.9

# maintainer
LABEL maintainer="egor.stambakio@opuscapita.com"

# Installing
# - nvm (https://github.com/creationix/nvm)
# - node
ENV NVM_DIR /usr/local/nvm
ENV NVM_VERSION 0.35.1
ENV NODE_VERSION 12.13.0

RUN mkdir -p $NVM_DIR && \
    curl https://raw.githubusercontent.com/creationix/nvm/v$NVM_VERSION/install.sh | bash && \
    /bin/bash -c "source $NVM_DIR/nvm.sh && \
    nvm install $NODE_VERSION && \
    nvm alias default $NODE_VERSION && \
    nvm use default"

# Set up our PATH correctly so we don't have to long-reference npm, node, &c.
ENV NODE_PATH $NVM_DIR/v$NODE_VERSION/lib/node_modules
ENV PATH $NVM_DIR/versions/node/v$NODE_VERSION/bin:$PATH

# install docker according to https://docs.docker.com/install/linux/docker-ce/debian/
RUN apt-get update && \
    apt-get install -y \
      unzip \
      rsync \
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
