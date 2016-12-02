FROM ubuntu
RUN apt-get update -y
RUN apt-get install -y wget 
RUN wget https://raw.githubusercontent.com/technomancy/leiningen/stable/bin/lein
RUN chmod +x lein
RUN ./lein
