## Teste Inmetrics - Como executar o projeto
1. Instalar a _release_ mais recente do **chromedriver** conforme descrito na página [Chromedriver [Downloads]](https://chromedriver.chromium.org/downloads).
1. Realizar o setup do **chromedriver** conforme descrito na seção Setup, da página [Chromedriver [Getting started]](https://chromedriver.chromium.org/getting-started).
1. Instalar o maven conforme descrito na página [Installing Apache Maven](https://maven.apache.org/install.html).
2. Ir até a raiz do projeto **teste-inmetrics**: ```cd teste-inmetrics```
3. Executar: ```mvn clean install```

### Cucumber features
Encontre os cenários em Gherkin em ```teste-inmetrics/src/test/resources/features```.

### Evidências de teste
Todas as evidências de teste serão salvas em ```teste-inmetrics/target/test-evidences```