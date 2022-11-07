# Guia Basico para Testes

Um dos principais focos aqui de inicio será o teste unitário.

## Mas então o que é um teste unitário?

<blockquote cite="https://carlosschults.net/en/unit-testing-for-beginners-part1/"> Os testes de unitários são normalmente testes automatizados escritos e executados por desenvolvedores de software para garantir que uma parte isolada de um aplicativo (conhecida como “unidade”) atenda ao seu design e se comporte como pretendido.<br></blockquote>

Geralmente ficamos com uma confusão na mente sobre como testar, e qual tipo de teste estamos fazendo, para exemplificar melhor vamos observer 2 casos, onde testamos praticamente a mesma coisa de formas diferentes;
- Primeiro vamos analisar o teste de cadastro na classe de serviços, nela testaremos apenas o METODO "cadastrarTrabalhador"; <br><br>
Respeitando algumas regras que o mesmo possui, criamos manualmente um dado a ser testado que no caso é um Trabalhador, verificamos pelo CPF se o mesmo já existe, porem estamos forçando o teste a nos dizer que não existe (willReturn(Optional.empty()) e podemos prosseguir;  <br><br>
Depois estamos dizendo que queremos que o repositorio do teste nos informe o objeto Trabalhador que instaciamos no inicio, para então chamar o nosso metodo na classe de serviço provendo o objeto para verificar se as informações pertencem a uma mesma classe, para isso verificamos se o nosso objeto não veio Null, e que o objeto que pedimos para salvar no repositorio pertende a classe de Trabalhador, tal qual o objeto que instanciamos no inicio.  

     <a href="https://imgur.com/4yiCMhJ"><img src="https://i.imgur.com/4yiCMhJ.png" target="_blank" title="Teste UNITARIO" style="width: 80%; height: 80%"/></a>
     
- Agora vamos analisar o teste de cadastro pelo Controller, aqui não estaremos fazendo teste unitario, e sim um teste de Integração, pois estamos emulando uma chamada no endpoint de cadastro e verificando qual resposta teremos, nesse tipo de teste podemos testar todos os aspectos de uma requisição: Body, Headers, Content-Type, Query etc.;  <br><br>
No exemplo abaixo podemos ver que para cadastrar um Trabalhador, criamos um trabalhador e simulamos uma chamada no serviço dizendo que estamos enviando um objeto da classe Trabalhador; <br><br>
Esperamos como retorno o nosso trabalhador instanciado no inicio, para então chamarmos um metodo da biblioteca Mockito que faz a simulação de uma chamada HTTP, estamos dizendo que na rota "/trabalhador/cadastrar" do nosso controller, estamos enviando um conteudo do tipo "application/json", e estamos convertendo nosso trabalhador pra JSON, e após a chamada ser realizada estamos esperando o StatusCode 201 CREATED

     <a href="https://imgur.com/3V5Ave8"><img src="https://i.imgur.com/3V5Ave8.png" target="_blank" title="Teste INTEGRAÇÂO" style="width: 80%; height: 80%"/></a>
     
## O que é um teste de integração
<blockquote =cite="https://kenzie.com.br/blog/teste-de-integracao/#:~:text=Teste%20de%20integração%20é%20quando,que%20está%20sendo%20integrado%20junto."> Teste de integração é quando os módulos (unidades de código) são testados em grupo para garantir que não haja nenhuma quebra naquilo que foi feito unitariamente e naquilo que está sendo integrado junto.</blockquote>


**O que faz o MockMvc que usamos para testar integração?**
<br><br>
O MockMvc fornece uma API elegante e fácil de usar para chamar endpoints da web e inspecionar e confirmar sua resposta ao mesmo tempo. Apesar de todos os seus > benefícios, tem algumas limitações.

Em primeiro lugar, ele usa uma subclasse do DispatcherServlet para lidar com solicitações de teste. Para ser mais específico, o TestDispatcherServlet é responsável por chamar os controladores e realizar toda a mágica familiar do Spring.

A classe MockMvc envolve este TestDispatcherServlet internamente. Portanto, toda vez que enviarmos uma solicitação usando o método perform(), o MockMvc usará o TestDispatcherServlet subjacente diretamente. Portanto, não há conexões de rede reais feitas e, consequentemente, não testaremos toda a pilha de rede ao usar o MockMvc.

Além disso, como o Spring prepara um contexto de aplicativo Web falso para simular as solicitações e respostas HTTP, ele pode não suportar todos os recursos de um aplicativo Spring completo.

Por exemplo, essa configuração simulada não oferece suporte a redirecionamentos HTTP. Isso pode não parecer tão significativo no início. No entanto, o Spring Boot lida com alguns erros redirecionando a solicitação atual para o endpoint /error. Portanto, se estivermos usando o MockMvc, talvez não possamos testar algumas falhas de API.

Como alternativa ao MockMvc, podemos configurar um contexto de aplicação mais real e então usar RestTemplate, ou mesmo com garantia de REST, para testar nossa aplicação.

<ln>
<hr>
<ln>
<em>Dica do Riva: "Durante a construção desse material, como tirei boa parte da cabeça acabei engalhando em algumas partes do teste, e foi justamente dai que eu percebi alguns vicios de praticas ruins que eu tenho, e outras pessoas podem ter, justamente por não ter o costume de escrever teste, não era o teste que estava errado, era o codigo, uma vez que eu sei que meu teste está bacana, ele FORÇA o desenvolvedor a usar corretamente os padrões de projeto" </em>
<ln>
<hr>
<ln>

Geralmente eu penso em 3 coisas quando crio um teste, "DADO" o que eu testarei, "QUANDO" o que eu preciso testar, "ENTÂO" e o que eu espero dos testes, 
no inicio pode ser complicado pensar como testar metodos de forma unitarias mas se guardamos "Dado, Quando, Então" as coisas começam a ficar mais claras.
Não economizar na nomenclatura dos testes, descreva ao maximo o que o teste está fazendo, e caso precise "perfumar" o teste na hora da execução, é possivel personalizar o nome que será exibido, abaixo segue alguns macetes: 

```
Exemplo Basico  
  
@Test
void deve_RetornarTrue_QuandoSomarCorretamente() {
  //dado
  int numero1 = 5;
  int numero2 = 5;
  //quando
  boolean somar = service.somar(numero1, numero2) == 10;
  //então
  assertTrue(somar);  
}
```
```
Anotações Uteis 
  
  - Teste Basico
  @Test
  
  - Para repetir 10 vezes o mesmo teste durante execução
  @RepeatedTest(10)
  
  - Para parametrizar um valor unico
  @ParametrizedTest
  @ValueSource(int = { 1, 2, 3})
  void testeExemplo(Integer param) { ... }
  
  - Para parametrizar multiplos valores 
  @ParametrizedTest(name = "n={0}, e={1)")
  @CsvSource(value = { "rivaldo, email@email.com", "teste, teste@teste.com })
  void testeDoisP(String nome, String email) { ... }
  
  - Para parametrizar a parti de um arquivo CSV ignorando o cabeçalho
  
  @ParametrizedTest
  @CsvSource(resources = "/meus-dados.csv",
  numLinesToSkip = 1
  )
  void testeArquivo(String nome, String email)  
  
  - Aninhar testes
  @Nested
  class testesConjugados { ... }
  
  - Trocar o nome exibido no console de testes
  @DisplayName("Teste Bacana")
  
  - Desabilitar um teste especifico
  @Disabled
  
  - Desabilitar o teste sob condição 
  inserir no ultimo bloco onde colocamos nossas assertivas
  por exemplo, sempre vai passar quando estivermos em ambiente de produção.
  assumeTrue(env.equals("prod"))
  
  { TIPOS DE ASSERTIVAS / A CHAVE DO TESTE }
  
  - Verificar se x é verdadeiro ou Falso
  assertTrue(x);
  assertFalse(x);
  
  - Verificar se Trabalhador é Null
  assertNull(Trabalhador);
  
  - Verificar se um tipo é igual ao outro (tipos primitivos apenas)
  Integer esperado = 1;
  Integer atual = 1;
  assertEquals(esperado, atual);
  
  - Verificar se uma lista é igual a outra
  assertArrayEquals(lista1, lista2);
  
  - Verificar se um metodo lança excessão
  Executable execucao = () -> fazAlgo();
  
  assertThrows(Exception.class, execucao);
  
  - Verificar multiplas assertivas 
  assertAll(
    () -> assertEquals(esperado1, atual1),
    () -> assertEquals(esperado2, atual2)
  );
  
  - Definir um tempo de execução maximo para o teste
  Executable execucao = () -> fazConsultaNoBanco();
  
  assertTimeout(
    Duration.ofMillis(500),
    execucao
  );
  
```
  

 ### Ciclo de Vida de Testes 
 - @BeforeAll <br> 
 Use de preferencia em metodo static antes que os testes iniciem de fato, geralmente utilizado para pré carregar dados, abrir conexão, <br>
 - @BeforeEach<br> Use para executar algo antes de cada teste, se existirem 5 testes, esse metodo executara 5 vezes antes de cada um deles iniciarem, exemplo pratico seria um logger informando o inicio do teste por exemplo<br>
 - @Test<br> Não esqueça de inserir essa anotação em cada um dos seus testes<br>
 - @AfterEach<br> Use para executar algo depois de cada teste, por exemplo, um logger para informar a finalização de cada teste<br>
 - @AfterAll<br> metodo executado após a finalização de todos os testes, pode ser usado para fechar conexões por exemplo.<br>

<img src="https://mermaid.ink/svg/pako:eNptjk0OgkAMha9CuoYLzMIEoieQ5WyamY4Q58eUEmMId7dIoixsN9_re2m7gCuewECI5ekGZKn6zuZKq6NQmNoYm-a08wXdcPQ2rWZPk-zjjXTQBiH-hb_yz6Kj92G9Z_PeUEMiTjh6_W_Z8hZkoEQWjKJHvluwedUczlKur-zACM9Uw_zwKHQe8caYwASME61va2BN4Q" style="display: flex; text-align: center; width: 10%; height: 10%;"> 
  
  
Links Utilizados como Referencia <br>  
[Basico de Testes em Controller - EN](https://howtodoinjava.com/spring-boot2/testing/rest-controller-unit-test-example/)<br>
[MockMvc - EN](https://www.baeldung.com/integration-testing-in-spring)<br>
[O Que é Teste Unitario - PT/BR](https://www.frameworkdemoiselle.gov.br/wikibe16.html?p_p_id=54_INSTANCE_22yPPz0LwGHG&p_p_lifecycle=0&p_p_state=pop_up&p_p_mode=view&controlPanelCategory=portlet_54_INSTANCE_22yPPz0LwGHG&_54_INSTANCE_22yPPz0LwGHG_struts_action=%2Fwiki_display%2Fview&_54_INSTANCE_22yPPz0LwGHG_nodeName=Main&_54_INSTANCE_22yPPz0LwGHG_title=Orientações%20técnicas%2FConceitos%20Gerais%20Testes%20Unitarios#:~:text=1.-,Testes%20Unitários,testes%20inclui%20construtores%20e%20destrutores.)<br>
[Teste de Integração](https://kenzie.com.br/blog/teste-de-integracao/#:~:text=Teste%20de%20integração%20é%20quando,que%20está%20sendo%20integrado%20junto.)
  
Material ainda em construção - PR são bem vindos,

Att. Rivaldo "Soturno" Oliveira. 

