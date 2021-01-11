@api
Feature: Testa as funcionalidades de Empregado Controller

  Scenario: Cadastrar usuário (POST)
    Given um payload
    """
    {
        "admissao": "$dd_mm_yyyy",
        "cargo": "QA",
        "comissao": "9.999,99",
        "cpf": "891.453.390-58",
        "departamentoId": 1,
        "nome": "Tester",
        "salario": "15.000,00",
        "sexo": "m",
        "tipoContratacao": "clt"
    }
    """
    When é enviado uma requisição "POST" para "/empregado/cadastrar"
    Then a resposta tem status 202
    And a resposta segue o schema
    """
    {
      "$schema": "http://json-schema.org/draft-04/schema#",
      "type": "object",
      "properties": {
        "empregadoId": {
          "type": "integer"
        },
        "nome": {
          "type": "string"
         },
        "sexo": {
          "type": "string"
        },
        "cpf": {
          "type": "string"
        },
        "cargo": {
          "type": "string"
        },
        "admissao": {
          "type": "string"
        },
        "salario": {
          "type": "string"
        },
        "comissao": {
          "type": "string"
        },
        "tipoContratacao": {
          "type": "string"
        }
      }
    }
    """

  Scenario: Listar usuário cadastrado (GET)
    When é enviado uma requisição "GET" para "/empregado/list/$empregadoId"
    Then a resposta tem status 202
    And a resposta segue o schema
    """
    {
      "$schema": "http://json-schema.org/draft-04/schema#",
      "type": "object",
      "properties": {
        "empregadoId": {
          "type": "integer"
        },
        "nome": {
          "type": "string"
         },
        "sexo": {
          "type": "string"
        },
        "cpf": {
          "type": "string"
        },
        "cargo": {
          "type": "string"
        },
        "admissao": {
          "type": "string"
        },
        "salario": {
          "type": "string"
        },
        "comissao": {
          "type": "string"
        },
        "tipoContratacao": {
          "type": "string"
        }
      }
    }
    """

  Scenario: Listar todos usuários (GET)
    When é enviado uma requisição "GET" para "/empregado/list_all"
    Then a resposta tem status 200
    And a resposta segue o schema
    """
    {
      "$schema": "http://json-schema.org/draft-04/schema#",
      "type": "array",
      "items": {
        "type": "object",
        "properties": {
          "empregadoId": {
            "type": "integer"
          },
          "nome": {
            "type": "string"
           },
          "sexo": {
            "type": "string"
          },
          "cpf": {
            "type": "string"
          },
          "cargo": {
            "type": "string"
          },
          "admissao": {
            "type": "string"
          },
          "salario": {
            "type": "string"
          },
          "comissao": {
            "type": "string"
          },
          "tipoContratacao": {
            "type": "string"
          }
        }
      }
    }
    """

  Scenario: Alterar usuário (PUT)
    Given um payload
    """
    {
        "admissao": "$dd_mm_yyyy",
        "cargo": "QA",
        "comissao": "9.999,99",
        "cpf": "891.453.390-58",
        "departamentoId": 1,
        "nome": "Tester",
        "salario": "15.000,00",
        "sexo": "m",
        "tipoContratacao": "clt"
    }
    """
    And é enviado uma requisição "POST" para "/empregado/cadastrar"
    Given um payload
    """
    {
        "admissao": "$dd_mm_yyyy",
        "cargo": "Gerente",
        "comissao": "9.999,99",
        "cpf": "891.453.390-58",
        "departamentoId": 2,
        "nome": "Edit Tester",
        "salario": "99.999,99",
        "sexo": "m",
        "tipoContratacao": "pj"
    }
    """
    When é enviado uma requisição "PUT" para "/empregado/alterar/$empregadoId"
    Then a resposta tem status 202
    And a resposta segue o schema
    """
    {
      "$schema": "http://json-schema.org/draft-04/schema#",
      "type": "object",
      "properties": {
        "empregadoId": {
          "type": "integer"
        },
        "nome": {
          "type": "string"
         },
        "sexo": {
          "type": "string"
        },
        "cpf": {
          "type": "string"
        },
        "cargo": {
          "type": "string"
        },
        "admissao": {
          "type": "string"
        },
        "salario": {
          "type": "string"
        },
        "comissao": {
          "type": "string"
        },
        "tipoContratacao": {
          "type": "string"
        }
      }
    }
    """