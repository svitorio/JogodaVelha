
<h1>Jogo da Velha Distribuído em Android.</h1>

Este jogo faz parte de um projeto que foi desenvolvido para a disciplina de Sistemas Distribuidos, o projeto consiste em um jogo da velha distribuido utilizando Web Services e Arquitetura SOA, ou seja, os processos realizados na aplicação estão sendo executados em outras maquinas.

<h2>Ferramentas Necessaria</h4>
<li>Android Studio(Versão usada - 2.3.3)<br></li>
<li>Netbeans<br></li>
<li>Banco de Dados Mysql</li>
<li><h href="https://github.com/svitorio/WebServiceGerenciaJogoVelha">Web Service Controle de Jogo</a></li>
<li><h href="https://github.com/svitorio/WebServiceControleBanco">Web Service Controle de Banco</a></li>
<li>2 Aparelhos Android</li>
<li>2 Computadores para rodar os servidores(Web Services)</li>


<h4>Instalação</h4>

Este jogo necessita de dois servidores para ser executado, no caso desse projeto está sendo utilizado duas web services,
<a href="https://github.com/svitorio/WebServiceGerenciaJogoVelha">Controle de Jogo</a> e <a href="https://github.com/svitorio/WebServiceControleBanco">Controle de Banco</a>, as duas estão disponiveis nos links(basta apenas clicar), e o modo de usa-las estão presentes nos Arquivos README no repositorio git de cada uma das Web Services.<br><br>

Antes de Iniciar a aplicação é necessario abrir a mesma através do Android Studio e mudar o endereo de IP, setar o ip correnpondente a maquina que está executando a <b>Web Service Controle de Jogo</b>.
.
<h4>Restrições</h4>

<li>Impossivel jogar localmente</li>
<li>Devido ao numero de Requisições feitas aos Servidores, talvez a execução comece a ficar lenta, para resolver isso limpe e construa novamente as web services, se estiver usando o netbeans para executar as web services, faça os seuguites passos:</li>
<ul>
  <li>Clique no projeto com o botão esquerdo</li>
  <li>Clique em Limpar e Construir</li>
</ul>
<h4>Como Jogar</h4>

Depois de cumpridas todos os pré requistos anteriores inicie o app, na tela inicial irá mostrar dois botões, o de Criar Jogo e Jogar Online, um dos dois aparelhos irá ter que criar um jogo depois de feito isso, vai passar para outra tela e onde irá ter um <b>id</b>, o aparelho que iniciar pelo Jogar Online deverá inserir esse id para entrar na mesma partida.

<h4>Informações adicionais</h4>

<b>Autor:</b>Vitório Silva<br>
<b>Instituição:</b>Univesirdade Federal do Piauí - CSHNB<br>
<b>Curso:</b>Sistemas de Informação<br>
<b>Disciplina:</b>Sistema Distribuidos - 2017.2<br>
