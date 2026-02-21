# HackHub

HackHub è un sistema di gestione degli Hackaton dove utenti possono creare, iscriversi e gestire hackaton

Il sistema è sviluppato nel contesto del corso di Ingegneria del Software all'Università degli Studi di Camerino (A.A. 2025-2026)

## Build e avvio del sistema
### Prerequisiti
Il progetto può essere avviato direttamente tramite il wrapper di Maven, oppure eseguendo la build e lanciando il .jar generato. In entrambi i casi sono necessari i seguenti requisiti:
* Java JDK versione 25 installata
* Variabile di ambiente `JAVA_HOME` impostata correttamente su Java JDK 25

Scaricare il progetto:  
`git clone https://github.com/SimGovUnicam/HackHub.git`  
`cd HackHub`

Effettuare la build e l'avvio del sistema con il wrapper
- Su Windows (PowerShell):  
  `& ./mvnw.cmd spring-boot:run`
- Su Windows (cmd):  
  `mvnw.cmd spring-boot:run`
- Su Linux  
  `./mvnw spring-boot:run`

In alternativa, effettuare la build con Maven (che deve essere installato localmente):  
`mvn clean install`  
altrimenti usare il wrapper:
- Su Windows (PowerShell):  
  `& ./mvnw.cmd clean install`
- Su Windows (cmd):  
  `mvnw.cmd clean install`
- Su Linux  
  `./mvnw clean install`

Eseguire il `.jar` generato dal precedente comando:  
`java -jar target/hackhub-1.0-SNAPSHOT.jar`


## Utilizzo
Utilizzare un client per le richieste HTTP per accedere alle API esposte dal sistema. Il sistema avvia un server locale sulla porta 8080, che non deve essere occupata da altri processi in esecuzione.
È disponibile una [collezione Postman](TODO link file) con degli esempi che utilizzano l'indirizzo locale e la porta di default.