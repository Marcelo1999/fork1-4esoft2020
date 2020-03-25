package observer.cofre;

import java.time.LocalDate;

public class AbrirCofreListenerConsole implements AbrirCofreListener {

    @Override
    public void cofreFoiAberto() {
        System.out.println("O cofre foi aberto: " + LocalDate.now().toString());
    }
}
