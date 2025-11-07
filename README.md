# Weather App

Weather App é um aplicativo Android para consulta de previsão do tempo. Desenvolvido em Java, o app permite ao usuário visualizar as condições climáticas de diferentes cidades, alternando entre elas através de uma lista ou escaneando um QR Code.

O projeto utiliza componentes modernos do Android Jetpack e bibliotecas populares para criar uma experiência de usuário fluida e funcional.

## Funcionalidades

- Previsão do Tempo: Exibe informações detalhadas do tempo, como temperatura, umidade, velocidade do vento e ícone de condição climática.
- Navegação por Abas: Interface organizada com ViewPager2 e TabLayout para alternar facilmente entre a tela de previsão e o mapa
- Seleção de Cidades: Gerencie e selecione cidades a partir de uma lista em um RecyclerView.
- Leitura de QR Code: Altere a cidade exibida escaneando um QR Code que contenha o nome da cidade.
- Mapa Interativo: Exibe a localização da cidade selecionada em um mapa.

## Tecnologias Utilizadas

- Linguagem: Java
- Arquitetura: ViewModel compartilhado para comunicação entre Activity e Fragments.
- UI (Interface de Usuário): RecyclerView, ViewPager2, TabLayout, Material Design.
- Comunicação de Rede: Retrofit para consumo de API de previsão do tempo.
- Navegação: FragmentStateAdapter para gerenciar os fragmentos.
- QR Code: Biblioteca ZXing ("Zebra Crossing").

  
