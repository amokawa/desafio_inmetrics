@webapp
Feature: Testes funcionais da página de Funcionários

  Scenario Outline: Registra um funcionário
    Given o formulário de cadastro é enviado com os seguintes dados
      | USER       | PASSWORD   | CONFIRM_PASS |
      | <username> | <password> | <password>   |
    And o formulário de login é enviado com os valores abaixo
      | USER       | PASSWORD   |
      | <username> | <password> |
    And o usuário clica no link "Novo Funcionário"
    When o formulário de cadastro de funcionário é enviado com os valores abaixo
      | NAME           | POSITION           | DOCUMENT           | SALARY           | GENDER           | TYPE           | ADMISSION           |
      | <employeeName> | <employeePosition> | <employeeDocument> | <employeeSalary> | <employeeGender> | <employeeType> | <employeeAdmission> |
    Then a página "empregados" é carregada
    And é verificado que o alerta de sucesso é exibido
    Examples:
      | username | password | employeeName | employeePosition | employeeDocument | employeeSalary | employeeGender | employeeType | employeeAdmission |
      | username | password | João         | Analista         | 050.679.420-29   | 1234,56        | Masculino      | CLT          | 01/01/2021        |
      | username | password | Maria        | Gerente          | 137.757.980-80   | 2345,78        | Feminino       | PJ           | 31/12/2020        |

  Scenario: Edita um funcionário
    Given o formulário de cadastro é enviado com os seguintes dados
      | USER     | PASSWORD | CONFIRM_PASS |
      | username | password | password     |
    And o formulário de login é enviado com os valores abaixo
      | USER     | PASSWORD |
      | username | password |
    And o usuário busca pelo funcionário chamado "João"
    And o usuário clica no ícone "Editar" do primeiro resultado listado
    When o formulário de cadastro de funcionário é enviado com os valores abaixo
      | NAME         | POSITION    | DOCUMENT       | SALARY  | GENDER    | TYPE | ADMISSION  |
      | João Editado | Analista Sr | 687.235.680-46 | 2222,22 | Masculino | CLT  | 02/01/2021 |
    Then a página "empregados" é carregada
    And é verificado que o alerta de sucesso é exibido

  Scenario: Remover um funcionário
    Given o formulário de cadastro é enviado com os seguintes dados
      | USER     | PASSWORD | CONFIRM_PASS |
      | username | password | password     |
    And o formulário de login é enviado com os valores abaixo
      | USER     | PASSWORD |
      | username | password |
    And o usuário busca pelo funcionário chamado "João"
    When o usuário clica no ícone "Remover" do primeiro resultado listado
    Then é verificado que o alerta de sucesso é exibido