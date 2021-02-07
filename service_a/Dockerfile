FROM debian:latest

RUN apt-get update -y && \
    apt-get install -y python-pip python-dev

# We copy just the requirements.txt first to leverage Docker cache
COPY ./requirements.txt /app/requirements.txt

WORKDIR /app

RUN pip install -r requirements.txt

COPY . /app

# Create regular user
RUN addgroup --gid 1000 neonomics && \
    yes | adduser --uid 1000 --shell=/bin/bash --disabled-password --gid 1000 neonomics

# Disable root login
RUN sed -i -e 's/\/root\:\/bin\/bash/\/root\:\/sbin\/nologin/g' /etc/passwd

RUN chown 1000:1000 -R /app

USER neonomics

EXPOSE 5000

ENTRYPOINT [ "python" ]

CMD [ "run.py" ]
