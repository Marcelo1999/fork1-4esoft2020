package observer.cofre;

import java.util.ArrayList;
import java.util.List;

public class Cofre {

    private int senha;
    private boolean aberto;
    private List<AbrirCofreListener> abrirListeners = new ArrayList<>();
    private List<FecharCofreListener> fecharListeners = new ArrayList<>();

    public Cofre(int senha) {

        this.senha = senha;
        this.aberto = true;
    }

    public boolean isAberto() {

        return this.aberto;
    }

    public void fechar() {

        this.aberto = false;
        this.fecharListeners.forEach(FecharCofreListener::cofreFoiFechado);
    }

    public void abrir(int senha) {

        if (this.senha == senha) {
            this.aberto = true;
            this.abrirListeners.forEach(AbrirCofreListener::cofreFoiAberto);
        } else {
            throw new RuntimeException("Senha incorreta, tente novamente.");
        }
    }

    public void addAbrirListener(AbrirCofreListener listener) {

        this.abrirListeners.add(listener);
    }

    public void addFecharListener(FecharCofreListener listener) {

        this.fecharListeners.add(listener);
    }
}
