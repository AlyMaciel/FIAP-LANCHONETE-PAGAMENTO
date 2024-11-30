# language: pt
Funcionalidade: API - Pagamentos

  Cenário: Registrar um novo pagamento
    Quando submeter um novo pagamento
    Então o pagamento é registrado com sucesso

  Cenário: Buscar o estado de um pagamento existente
    Dado que um pagamento já foi realizado
    Quando requisitar a busca do estado do pagamento
    Então o estado do pagamento é exibido com sucesso