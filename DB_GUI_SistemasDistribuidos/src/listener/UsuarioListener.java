package listener;

import java.util.ArrayList;
import java.util.List;

public class UsuarioListener {
    private List<UsuarioListenerCallback> listeners;

    public UsuarioListener() {
        listeners = new ArrayList<>();
    }

    public void addListener(UsuarioListenerCallback listener) {
        listeners.add(listener);
    }

    public void notifyUsuariosLogadosChanged() {
        for (UsuarioListenerCallback listener : listeners) {
            listener.onUsuariosLogadosChanged();
        }
    }
}