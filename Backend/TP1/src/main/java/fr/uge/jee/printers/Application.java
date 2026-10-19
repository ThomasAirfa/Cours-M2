package fr.uge.jee.printers;

import fr.uge.jee.printers.SimpleMessagePrinter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {
  public static void main(String[] args) {
    ApplicationContext context = new ClassPathXmlApplicationContext("config-printers.xml");
    // var printer = context.getBean("printer", MessagePrinter.class);
    var simplePrinter = context.getBean(fr.uge.jee.printers.SimpleMessagePrinter.class);
    simplePrinter.printMessage();
    var frenchPrinter = context.getBean(fr.uge.jee.printers.FrenchMessagePrinter.class);
    frenchPrinter.printMessage();

    /* Changez votre config-printers.xml pour rajouter un bean pour CustomizableMessagePrinter.
    Dans votre classe Application, instanciez le bean correspondant à la classe CustomizableMessagePrinter.
    Quel constructeur est appelé ?
    *
    * Il n'a pas d'argument, du coup, il appelle le constructeur sans arg on modifie le fichier de config pour pouvoir
    donner un arg.
    */
    var customizablePrinter = context.getBean(fr.uge.jee.printers.CustomizableMessagePrinter.class);
    customizablePrinter.printMessage();

    /* Expliquez l'affichage ci-dessous ? Quel design pattern est mis en oeuvre par le conteneur IoC ?
     *
     * Spring crée une instance de CountMessagePrinter et la renvoie à chaque getBean, il utilise le Design Pattern
     * Singleton.
     *
     * Changez votre fichier de configuration pour qu'un CountMessagePrinter différent soit crée à chaque appel à getBean.
     *
     * On ajoute le scope prototype à notre bean pour qu'une nouvelle instance soit créée à chaque getBean.
     * */
    var countPrinter = context.getBean(fr.uge.jee.printers.CountMessagePrinter.class);
    countPrinter.printMessage();
    countPrinter.printMessage();
    countPrinter.printMessage();
    var countPrinter2 = context.getBean(fr.uge.jee.printers.CountMessagePrinter.class);
    countPrinter2.printMessage();

    /* A quel moment sont instanciés les beans ?
     *
     * Ils sont instanciés lors de la création du context.
     *
     * Quel est l'inconvéniant majeur de déférer l'instantition des beans ?
     *
     * On peut louper une erreur de configuration.
     * Le premier utilisateur de slowPrinter doit attendre 5s au lieu que ces 5s soit perdu au démarrage.
    * */
    var slowPrinter = context.getBean(fr.uge.jee.printers.SlowContructionMessagePrinter.class);
    slowPrinter.printMessage();
    slowPrinter.printMessage();
  }
}