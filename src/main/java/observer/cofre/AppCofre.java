package observer.cofre;

public class AppCofre {

    public static void main(String[] args) {
        Cofre daSala = new Cofre(123456);

        daSala.addAbrirListener(new AbrirCofreListenerConsole());
        daSala.addFecharListener(new FecharCofreListenerConsole());

        System.out.println(daSala.isAberto());

        daSala.fechar();
        System.out.println(daSala.isAberto());

        //Tratamento de senha incorreta no método abrir
        //daSala.abrir(111);
        System.out.println(daSala.isAberto());

        daSala.abrir(123456);
        System.out.println(daSala.isAberto());

        System.out.println("Fim");
    }
}
