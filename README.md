# projeto-sistemas-distribuidos
Repositório criado para facilitar o acesso aos arquivos criados para o projeto desenvolvido na matéria de Sistemas Distribuídos da UTFPR - PG

# Funcionamento do projeto
  Atualmente estão implementadas seis funcionalidades (Cadastro, Atualizar Cadastro, Login, Reportar Incidente, Ver Lista de Incidentes e Logout) com acesso à base de dados e interface, além do aparente funcionamento correto do Socket.
  
# Características Específicas
- Código desenvolvido por Igor Castro, tendo início em 03/2023;
- Utilização da biblioteca GSON da Google, para manipulação de Json;
- O método de token utilizado se apresenta dentro do código, sendo um método simples e randômico de criação de valores;
- O método de HASH utilizado para a senha foi escolhido juntamente dos outros colegas de clase.tdec
- É necessário a inserção via teclado da porta e do IP, conforme conversado durante às aulas.
- Na atualização do cadastro é possível alterar nome, e-mail e senha.
- Por enquanto, ao retornar a Lista de Incidentes, está retornando todos os incidentes e não somente os especificados, mas, até a próxima semana (01/06/2023) este detalhe já terá sido resolvido.