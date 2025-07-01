package de.epax.inputManager;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class KeyboardInputHandler implements KeyListener {

    private enum KeyState {
        RELEASED,
        PRESSED,
        HELD
    }

    private static final Map<Integer, KeyState> keyStates = new HashMap<>();

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        KeyState state = keyStates.get(code);
        if (state == null || state == KeyState.RELEASED) {
            keyStates.put(code, KeyState.PRESSED);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyStates.put(e.getKeyCode(), KeyState.RELEASED);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // not neded
    }


    public static boolean isKeyPressed(int keyCode) {
        KeyState state = keyStates.get(keyCode);
        if (state == KeyState.PRESSED) {
            keyStates.put(keyCode, KeyState.HELD);
            return true;
        }
        return false;
    }
    public static boolean isKeyHeld(int keyCode) {
        KeyState state = keyStates.get(keyCode);
        return state == KeyState.PRESSED || state == KeyState.HELD;
    }


    public static void attachTo(Component component) {
        KeyboardInputHandler input = new KeyboardInputHandler();
        component.addKeyListener(input);
        component.setFocusable(true);
        component.requestFocus();
    }
}
