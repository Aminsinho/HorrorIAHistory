package com.aminsinho.prompt;

public class Prompt {

    public static final String INITIAL_PROMPT = """
                Genera una historia interactiva en la que el jugador se despierta 
                en una localizacion sin recordar cómo llegó ahí.
                No hay señales de vida, pero hay indicios de que alguien o algo lo está observando.
                El jugador debe explorar, encontrar suministros y tomar decisiones para sobrevivir.
                Hay amenazas como criaturas, trampas y estructuras inestables.
                Cada decisión puede llevarlo más cerca de la salida o de la muerte.
                Si muere, explícale qué salió mal y qué opción habría sido mejor.
                Usa descripciones inmersivas y genera una sensación de peligro constante.
                Comienza la historia describiendo el entorno y pregunta: "¿Qué haces?" quiero que las escenas sean de de 15 palabras
                No quiero que me des opciones, tengo que decirte yo lo que decido.
                Ademas de esto tenemos que tener en cuenta proponer una salida logica a la historia, como un destino o una meta.
                Deja clara cuál es la salida de la historia o que tenemos que hacer para sobrevivir.
                Solo podremos tomar 10 deciciones, cuando se lleguen a esas 10 decisiones la novena y la decima tienen que ser de vida o muerte.
                """;

    public static final String CONTINUE_PROMPT = """
                Genera una historia interactiva en la que el jugador se despierta 
                en una localizacion sin recordar cómo llegó ahí.
                No hay señales de vida, pero hay indicios de que alguien o algo lo está observando.
                El jugador debe explorar, encontrar suministros y tomar decisiones para sobrevivir.
                Hay amenazas como criaturas, trampas y estructuras inestables.
                Cada decisión puede llevarlo más cerca de la salida o de la muerte.
                Si muere, explícale qué salió mal y qué opción habría sido mejor.
                Usa descripciones inmersivas y genera una sensación de peligro constante.
                Comienza la historia describiendo el entorno y pregunta: "¿Qué haces?" quiero que las escenas sean de de 15 palabras
                No quiero que me des opciones, tengo que decirte yo lo que decido.
                Ademas de esto tenemos que tener en cuenta proponer una salida logica a la historia, como un destino o una meta.
                Deja clara cuál es la salida de la historia o que tenemos que hacer para sobrevivir.
                Solo podremos tomar 10 deciciones, cuando se lleguen a esas 10 decisiones la novena y la decima tienen que ser de vida o muerte.
                """;

    public String endPrompt(int numberOfMessages) {
        return """
                ] 
                Las decisiones restantes son """ + (10 - numberOfMessages) + """ 
                de 10.\\s
                ahora en base a nuestras decisiones y escenas tienes que seguir la historia.\s
                No quiero que me des opciones, tengo que decirte yo lo que decido.\s
                Las escenas tienen que poder describirse en 40 palabras.\s
                Si el jugador muere o llega al destino sin morir, debe ganar o perder respectivamente.\s
                quiero tambien que no pongas cosas tipo 'Entendido, continuemos con la historia.\s
                ' o 'Claro, sigamos con la historia.' o 'Entendido, continuemos con la historia.'\s
                o 'Claro, sigamos con la historia.' o 'Entendido, continuemos con la historia.'\s
                o 'Claro, sigamos con la historia.' o 'Entendido, continuemos con la historia.'\s
                o 'Claro, sigamos con la historia.' o 'Entendido, continuemos con la historia.'\s
                o 'Claro, sigamos con la historia.' o 'Entendido, continuemos con la historia.'\s
                o 'Claro, sigamos con la historia.' o 'Entendido, continuemos con la historia.'\s
                o 'Claro, sigamos con la historia.' directamente describe la escena.\s
                si el jugador muere se pone algo del estilo has terminado la historia,\s
                podrias haber hecho esto o lo otro par no morir, si el jugador gana\s
                se dice felicidades. ademas de esto tenemos que controlar donde esta\s
                nuestro perseguidor, tenemos que dar mas informacion al jugador.\s
                quiero que no repitas descripciones todo el rato, quiero que sea lo mas humano posible.\s
                quiero que la historia sea lo mas inmersiva posible, quiero que el jugador\s
                sienta que esta dentro de la historia, quiero que el jugador sienta que\s
                tiene que tomar decisiones y que esas decisiones tienen consecuencias.\s
                
                esta es la decision que ha elegido el jugador: Jugador dice:\s
                \s""";
    }
}
