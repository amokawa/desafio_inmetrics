@webapp
Feature: testes da página de login

  Scenario: Cadastro de usuário
    When o formulário de cadastro é enviado com os seguintes dados
      | USER     | PASSWORD | CONFIRM_PASS |
      | username | password | password     |
    Then a página "login" é carregada

  Scenario: Login
    Given o formulário de cadastro é enviado com os seguintes dados
      | USER     | PASSWORD | CONFIRM_PASS |
      | username | password | password     |
    When o formulário de login é enviado com os valores abaixo
      | USER     | PASSWORD |
      | username | password |
    Then a página "empregados" é carregada