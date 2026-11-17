import java.util.Random;
import java.util.Scanner;

public class Main {

    public static int inimigosMortos = 0;
    public static int moedas = 0;
    public static int experiencia = 0;
    public static int nivel = 1;
    public static boolean bossDerrotado = false;

    public static void mostrarInicio() {
        System.out.println("*******************");
        System.out.println("     JOGO RPG      ");
        System.out.println("*******************");
    }

    public static int escolhaPersonagem(Scanner sc) {
        int opcao;

        do {
            System.out.println("Escolha sua classe:");
            System.out.println("1- Guerreiro");
            System.out.println("2- Arqueiro");
            System.out.println("3- Shaman");
            System.out.println("4- Mago");
            opcao = sc.nextInt();
        } while (opcao < 1 || opcao > 4);

        return opcao;
    }

    public static int definirVida(int opcao) {
        if (opcao == 1) return 120;
        else if (opcao == 2) return 100;
        else if (opcao == 3) return 80;
        else return 90;
    }

    public static int definirAtaque(int opcao) {
        if (opcao == 1) return 80;
        else if (opcao == 2) return 70;
        else if (opcao == 3) return 50;
        else return 40;
    }

    public static int definirDefesa(int opcao) {
        if (opcao == 1) return 50;
        else if (opcao == 2) return 40;
        else if (opcao == 3) return 60;
        else return 20;
    }

    public static int sortearDadoD20() {
        Random random = new Random();
        return random.nextInt(20) + 1;
    }

    public static int explorar() {
        int sorteio = new Random().nextInt(100) + 1;

        if (sorteio <= 40) return 1;
        else if (sorteio <= 60) return 2;
        else if (sorteio <= 95) return 3;
        else return 4;
    }

    public static void mostrarMenuJogo() {
        System.out.println("\nInicio da jornada");
        System.out.println("1- Explorar");
        System.out.println("2- Ver Status");
        System.out.println("3- Recuperar");
        System.out.println("4- Loja");
        System.out.println("5- Enfrentar Chefão");
        System.out.println("0- Sair");
    }

    public static void mostrarMenuCombate() {
        System.out.println("O que deseja fazer?");
        System.out.println("1- Atacar");
        System.out.println("2- Defender");
        System.out.println("3- Fugir");
    }

    public static int atacarMonstro(int vidaMonstro, int ataque) {
        int dado = sortearDadoD20();

        if (dado <= 7) {
            System.out.println("Você errou!");
        } else if (dado <= 14) {
            vidaMonstro -= ataque / 2;
            System.out.println("Metade do dano");
        } else if (dado <= 19) {
            vidaMonstro -= ataque;
            System.out.println("Ataque total");
        } else {
            vidaMonstro -= ataque * 2;
            System.out.println("ATAQUE CRITICO!");
        }

        return vidaMonstro;
    }

    public static int receberAtaque(int vida, int ataqueMonstro) {
        int dado = sortearDadoD20();

        if (dado <= 7) {
            System.out.println("O monstro errou!");
        } else if (dado <= 14) {
            vida -= ataqueMonstro / 2;
            System.out.println("Ataque pela metade");
        } else if (dado <= 19) {
            vida -= ataqueMonstro;
            System.out.println("Você recebeu o ataque total");
        } else {
            vida -= ataqueMonstro * 2;
            System.out.println("Você recebeu um crítico");
        }

        return vida;
    }

    public static void mostrarVidas(int vida, int vidaMonstro) {
        System.out.println("Vida Personagem: " + vida);
        System.out.println("Vida Monstro: " + vidaMonstro);
    }

    public static int executarCombate(int vida, int ataque, int defesa,
                                      boolean temArma, boolean temArmadura, int vidaMonstro,
                                      int ataqueMonstro, String nomeMonstro) {

        if (temArmadura) defesa += 20;
        if (temArma) ataque += 20;

        System.out.println("Seu inimigo é: " + nomeMonstro);
        System.out.println("Vida: " + vidaMonstro);
        System.out.println("Ataque: " + ataqueMonstro);

        while (vidaMonstro > 0 && vida > 0) {
            vidaMonstro = atacarMonstro(vidaMonstro, ataque);
            mostrarVidas(vida, vidaMonstro);

            if (vidaMonstro > 0) {
                vida = receberAtaque(vida, ataqueMonstro);
                mostrarVidas(vida, vidaMonstro);
            }
        }

        if (vidaMonstro > 0) {
            System.out.println("O " + nomeMonstro + " te venceu!");
            return 0;
        }

        System.out.println("Você derrotou " + nomeMonstro + "!");
        return vida;
    }

    public static int combater(int vida, int ataque, int defesa,
                               boolean temArma, boolean temArmadura) {

        Random random = new Random();
        int vidaMonstro = random.nextInt(80) + 40 + random.nextInt(20) + 1;
        int ataqueMonstro = random.nextInt(30) + 5;

        if (vidaMonstro >= 100) {
            System.out.println("Você encontrou o Marcelo Goblin!");
        }

        return executarCombate(vida, ataque, defesa, temArma,
                temArmadura, vidaMonstro, ataqueMonstro, "Goblin");
    }

    public static int enfrentarGoblin(Scanner sc, int vida, int ataque,
                                      int defesa, boolean temArma, boolean temArmadura) {

        System.out.println("Você encontrou um Goblin!");
        mostrarMenuCombate();

        int opcao = sc.nextInt();

        if (opcao == 1) {
            vida = combater(vida, ataque, defesa, temArma, temArmadura);

            if (vida > 0) {
                inimigosMortos++;
                moedas += 20;
                experiencia += 50;
                System.out.println("Você ganhou 20 moedas!");
                System.out.println("Você ganhou 50 de experiência!");
            }
        } else if (opcao == 2) {
            System.out.println("Você escolheu defender!");
        } else if (opcao == 3) {
            System.out.println("Corrrreeeuuuu!!");
        } else {
            System.out.println("Opção inválida!");
        }

        return vida;
    }

    public static int enfrentarBoss(int vida, int ataque, int defesa,
                                    boolean temArma, boolean temArmadura) {

        System.out.println("VOCÊ ENCONTROU O CHEFÃO!");
        vida = executarCombate(vida, ataque, defesa, temArma,
                temArmadura, 200, 25, "CHEFÃO");

        if (vida > 0) {
            bossDerrotado = true;
            moedas += 100;
            experiencia += 100;
            System.out.println("Você derrotou o CHEFÃO!");
            System.out.println("Ganhou 100 moedas!");
            System.out.println("Ganhou 100 de experiência!");
        }

        return vida;
    }

    public static int encontrarEnergia(int energia) {
        System.out.println("Você encontrou um baú de energia!");

        if (energia < 9) {
            energia++;
        } else {
            System.out.println("Energia cheia!");
        }

        return energia;
    }

    public static int sortearItemRaro() {
        return new Random().nextInt(2) + 1;
    }

    public static void mostrarStatus(String nome, int ataque, int defesa,
                                     int vida, int energia, boolean temArmadura, boolean temArma) {

        System.out.println("Olá " + nome);
        System.out.println("Ataque: " + ataque);
        System.out.println("Defesa: " + defesa);
        System.out.println("Vida: " + vida);
        System.out.println("Energia: " + energia);
        System.out.println("Armadura: " + (temArmadura ? "Sim" : "Não"));
        System.out.println("Arma: " + (temArma ? "Sim" : "Não"));
        System.out.println("Nível: " + nivel);
        System.out.println("Experiência: " + experiencia);
        System.out.println("Moedas: " + moedas);
        System.out.println("Inimigos derrotados: " + inimigosMortos);
    }

    public static int recuperarVida(int vida, int vidaMaxima) {
        vida += 10;

        if (vida >= vidaMaxima) {
            vida = vidaMaxima;
            System.out.println("Vida máxima atingida!");
        } else {
            System.out.println("Vida aumentada!");
        }

        return vida;
    }

    public static int recuperarEnergia(int energia)
            throws InterruptedException {

        System.out.println("Vamos recuperar sua energia...");
        Thread.sleep(2000);
        System.out.println("Ainda estamos recuperando...");
        Thread.sleep(3000);
        System.out.println("Energia recuperada!");

        return energia + 1;
    }

    public static int verificarNivel() {
        int niveisGanhos = 0;

        while (experiencia >= 100) {
            experiencia -= 100;
            nivel++;
            niveisGanhos++;

            System.out.println("\nPARABÉNS! VOCÊ SUBIU DE NÍVEL!");
            System.out.println("Seu nível atual é: " + nivel);
        }

        return niveisGanhos;
    }

    public static int loja(Scanner sc, int vida, int vidaMaxima) {
        System.out.println("\n===== LOJA =====");
        System.out.println("Suas moedas: " + moedas);
        System.out.println("1- Comprar poção (30 moedas)");
        System.out.println("0- Sair da loja");

        int opcao = sc.nextInt();

        if (opcao == 1) {
            if (moedas >= 30) {
                if (vida == vidaMaxima) {
                    System.out.println("Sua vida já está cheia!");
                } else {
                    moedas -= 30;
                    vida += 30;

                    if (vida > vidaMaxima) vida = vidaMaxima;

                    System.out.println("Poção comprada!");
                    System.out.println("Vida recuperada!");
                }
            } else {
                System.out.println("Moedas insuficientes!");
            }
        } else {
            System.out.println("Você saiu da loja.");
        }

        return vida;
    }

    public static void mostrarRegrasBoss() {
        System.out.println("CHEFÃO");

        if (inimigosMortos < 5) {
            System.out.println("Derrote 5 Goblins primeiro!");
            System.out.println("Inimigos derrotados: " + inimigosMortos);
        } else if (bossDerrotado) {
            System.out.println("Você já derrotou o Chefão!");
        } else {
            System.out.println("O Chefão está disponível!");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        mostrarInicio();

        Scanner sc = new Scanner(System.in);

        System.out.println("Informe o nome do seu personagem:");
        String nome = sc.next();

        int opcao = escolhaPersonagem(sc);
        int vida = definirVida(opcao);
        int ataque = definirAtaque(opcao);
        int defesa = definirDefesa(opcao);
        int energia = 0;
        int vidaMaxima = vida;

        boolean temArma = false;
        boolean temArmadura = false;

        System.out.println("Seja bem-vindo " + nome + "!");

        int opcaoJogo = -1;

        while (opcaoJogo != 0) {
            mostrarMenuJogo();
            opcaoJogo = sc.nextInt();

            if (opcaoJogo == 1) {
                int evento = explorar();

                if (evento == 1) {
                    vida = enfrentarGoblin(sc, vida, ataque, defesa,
                            temArma, temArmadura);

                    if (vida <= 0) {
                        System.out.println("VOCÊ MORREU!");
                        System.out.println("Inimigos derrotados: " + inimigosMortos);
                        break;
                    }

                } else if (evento == 2) {
                    energia = encontrarEnergia(energia);

                } else if (evento == 3) {
                    System.out.println("Você deve seguir sua vidinha!");

                } else {
                    System.out.println("Você encontrou um item raro!");
                    int item = sortearItemRaro();

                    if (item == 1) {
                        if (temArmadura) {
                            System.out.println("Você já tem armadura!");
                        } else {
                            temArmadura = true;
                            System.out.println("Você ganhou uma armadura!");
                        }
                    } else {
                        if (temArma) {
                            System.out.println("Você já tem uma arma!");
                        } else {
                            temArma = true;
                            System.out.println("Você ganhou uma arma!");
                        }
                    }
                }

                int ganhos = verificarNivel();

                for (int i = 0; i < ganhos; i++) {
                    ataque += 10;
                    vidaMaxima += 20;
                    vida += 20;
                    System.out.println("Ataque aumentado em 10!");
                    System.out.println("Vida máxima aumentada em 20!");
                }

            } else if (opcaoJogo == 2) {
                mostrarStatus(nome, ataque, defesa, vida, energia,
                        temArmadura, temArma);

            } else if (opcaoJogo == 3) {
                vida = recuperarVida(vida, vidaMaxima);
                energia = recuperarEnergia(energia);

            } else if (opcaoJogo == 4) {
                vida = loja(sc, vida, vidaMaxima);

            } else if (opcaoJogo == 5) {
                mostrarRegrasBoss();

                if (inimigosMortos >= 5 && !bossDerrotado) {
                    vida = enfrentarBoss(vida, ataque, defesa,
                            temArma, temArmadura);

                    if (vida <= 0) {
                        System.out.println("VOCÊ MORREU!");
                        System.out.println("Inimigos derrotados: " + inimigosMortos);
                        break;
                    }

                    int ganhos = verificarNivel();

                    for (int i = 0; i < ganhos; i++) {
                        ataque += 10;
                        vidaMaxima += 20;
                        vida += 20;
                        System.out.println("Ataque aumentado em 10!");
                        System.out.println("Vida máxima aumentada em 20!");
                    }
                }

            } else if (opcaoJogo == 0) {
                System.out.println("Saindo do jogo...");
            } else {
                System.out.println("Opção inválida!");
            }
        }
    }
}