package fr.uge.poo.uberclient.ex3;

import java.util.List;
import java.util.function.DoubleSupplier;

/* On utilise un DoubleSupplier pour éviter de calculer la moyenne si on ne l'affiche pas.
 * Le calcul sera effectué quand on fait appel au getAsDouble() du Supplier et pas directement à la création de l'objet info.
 */
public record UberClientInfo(String firstName, String lastName, List<String> emails, DoubleSupplier averageGrade) {

}
