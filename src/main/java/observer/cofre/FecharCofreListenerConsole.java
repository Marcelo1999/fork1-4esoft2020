package observer.cofre;

import java.time.LocalDate;

public class FecharCofreListenerConsole implements FecharCofreListener {

    @Override
    public void cofreFoiFechado() {
        System.out.println("O cofre foi fechado: " + LocalDate.now().toString());
    }
}
