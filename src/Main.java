import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        ClienteGestor gestor = new ClienteGestor();
        Cliente natalia = gestor.registrar("natalia", "2345", "2345na");
        Cliente jose = gestor.registrar("Jose", "6789", "12uyna");

        List<Cliente> resultados = gestor.buscar("jo");

        for (Cliente cliente : resultados) {

            System.out.println(cliente.getNombre());


        }

    }
}