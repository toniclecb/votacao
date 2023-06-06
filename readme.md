# Votação de pautas

Criar um sistema de votação de pautas.

A seguir são descritos em cada tópico separadamente as decisões de implementação, comentários e explicações.

## Execução

Aplicação utiliza Maven então os comandos para compilação e execução são baseados no Maven.
Antes da execução é necessário compilar para baixar todas as dependências com o comando:

> mvn install

A aplicação pode ser executada com o seguinte comando:

> mvn spring-boot:run

## Documentação

Para documentar a API foi utilizado a biblioteca Spring Doc que oferece documentação via página web no endereço:

> http://localhost:8080/swagger-ui/index.html

ou pelo formato JSON no endereço:

> http://localhost:8080/v3/api-docs

Quanto a documentação do código está foi adicionada apenas em ocasiões específicas, pois foi seguido a abordagem de utilizar nomes descritivos claros no código-fonte para facilitar sua compreensão.

## Customizando exceptions

Algumas exceptions foram selecionadas e a classe CustomizedResponseEntityExceptionHandler foi criada para customizar os retornos das exceptions.
Os retornos de erro da API se resumem a parâmetros com problemas (retorno 400) e registros não encontrados (retorno 404), além disso o retorno 409 foi escolhido para representar os conflitos nas lógicas de negócio que o usuário tentou inserir.

### Melhorias

Ainda se pode customizar outras exceptions, como por exemplo MethodArgumentTypeMismatchException.

## Versionamento

O versionamento da API foi executada por meio de número da versão na própria URL. Isso tem alguns benefícios como a simplicidade e fácil identificação via url, permite evolução independente das versões, permite a descontinuação de versões específicas e aumenta a flexibilidade para os usuários da API escolherem a versão a utilizar.

## Testes

Com auxílio do banco H2, que grava dados em memória, e do Rest Assured podemos fazer testes integrados nos Controles e avaliar todas as camadas da aplicação (controller, service, repository, DTO).
Já utilizando Mockito e o JUnit podemos fazer os testes unitários nos Services.

## DTO

A utilização de Data Transfer Objects tem alguns benefícios como a exposição menor de dados, mais flexibilidade para evolução, melhora o desempenho e reduz o acoplamento.
Para transferência de dados entre classes de diferentes tipos foi criada a interface Mapper assim como suas implementações, aqui foi dado enfase na transformação de entidades em DTO de resposta, mas também poderia ser implementado a transformação de objetos requests para entidades.

### Melhoria

Utilizar uma biblioteca para fazer o mapeamento de objetos Java. ModelMapper, Dozer, MapStruct são exemplos.

## Banco de dados

A fim de criar uma aplicação portátil que poderia ser executada em qualquer máquina optou-se pelo banco de dados H2 em arquivo.
Para execução de testes de performance ou envio da aplicação para produção é imprescindível a utilização de um banco de dados adequado para tal.
As informações de conexão que a aplicação necessita estão no arquivo application.properties

## Melhorias gerais

- Utilizar **HATEOAS** para ficar mais próximo de uma API Restful.

- A principal melhoria, que também traria bom ganhos em performace, é **agendar uma tarefa** que encerra a sessão de votação.
Com isso, ao invés de comparação entre datas, poderia haver uma comparação com um booleano para definir se o voto pode ser contabilizado naquela data.
Isso também contribuiria para facilitar a implementação de mensageria (outro tópico de melhoria).
Mas da mesma forma que traz benefícios essa mudança traz alterações de lógicas e cuidados grandes de implementação, por exemplo, precisa existir um lógica para toda vez que a aplicação ser iniciada ela buscar as sessões de votação e fazer o agendamento do fim da votação, assim como, finalizar as sessões que terminaram enquanto a aplicação estava desligada.

- Adicionar **mensageria**, dentre outros benefícios, é uma forma de acelerar a troca de informações entre aplicações, então postar uma mensagem informando o fim da votação e seu resultado pode beneficar os sistemas que recebem essa mensagem.

- O ponto mais crítico dessa aplicação é a inserção de um voto, pois existem diversas checagens que buscam informações no banco de dados para garantir a integridade dos dados. Neste ponto, poderia ser optado por utilização de uma tecnologia de **Cache**. Uma das opções, por exemplo, seria deixar em cache as sessões com votação em andamento, assim eliminaríamos a necessidade de busca no banco de dados a cada requisição.g

