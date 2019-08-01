FROM gradle:alpine

USER root
RUN apk update && \
    apk add --no-cache bash git

RUN echo "Type something random in this string everytime you want to do a git clone"

# TODO: CRIO_TASK_MODULE_RESTAPI: Type in your git repository url.
# Need to do this only if you plan to deploy your code in cloud/Docker.
# RUN git clone <repository_url> --branch <branch_name or tag> /var/local/git/qride-server


RUN cd /var/local/git/qride-server && gradle build --parallel

EXPOSE 8080

CMD ["/bin/sh", "-c", \
"gradle bootrun --parallel -p /var/local/git/qride-server/qridecontroller 2>&1 | \
tee /tmp/qride-server.log"]

# sudo docker build -t qride-server .
# sudo docker run -m 800m -v /tmp/container:/tmp:rw -p 80:8080 qride-server
