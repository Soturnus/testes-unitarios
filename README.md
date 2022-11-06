# Guia Basico para Testes Unitários

Guia Básico para apoio a testes unitários construido 

<ln>
<hr>

> Geralmente eu penso em 3 coisas quando crio um teste, o que eu testarei "DADO", o que eu preciso testar "QUANDO", e o que eu espero dos testes "ENTÂO", 
no inicio pode ser complicado pensar como testar metodos de forma unitarias mas se guardamos "Dado, Quando, Então" as coisas começam a ficar mais claras.
Não economizar na nomenclatura dos testes, descreva ao maximo o que o teste está fazendo, e caso precise "perfumar" o teste na hora da execução, é possivel personalizar o nome que será exibido
- Exemplos
```
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



> ## Ciclo de Vida de Testes 
> - @BeforeAll <br> 
> Use de preferencia em metodo static antes que os testes iniciem de fato, geralmente utilizado para pré carregar dados, abrir conexão, <br>
> - @BeforeEach<br> Use para executar algo antes de cada teste, se existirem 5 testes, esse metodo executara 5 vezes antes de cada um deles iniciarem, exemplo pratico seria um logger informando o inicio do teste por exemplo<br>
> - @Test<br> Não esqueça de inserir essa anotação em cada um dos seus testes<br>
> - @AfterEach<br> Use para executar algo depois de cada teste, por exemplo, um logger para informar a finalização de cada teste<br>
> - @AfterAll<br> metodo executado após a finalização de todos os testes, pode ser usado para fechar conexões por exemplo.<br>

<img src="https://mermaid.ink/svg/pako:eNptjk0OgkAMha9CuoYLzMIEoieQ5WyamY4Q58eUEmMId7dIoixsN9_re2m7gCuewECI5ekGZKn6zuZKq6NQmNoYm-a08wXdcPQ2rWZPk-zjjXTQBiH-hb_yz6Kj92G9Z_PeUEMiTjh6_W_Z8hZkoEQWjKJHvluwedUczlKur-zACM9Uw_zwKHQe8caYwASME61va2BN4Q" style="display: flex; text-align: center; width: 10%; height: 10%;"> 
