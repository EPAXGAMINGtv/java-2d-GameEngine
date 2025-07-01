package de.epax.inputManager;


import de.epax.vector.Vector2;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashSet;
import java.util.Set;

public class MouseInputHandler implements MouseListener, MouseMotionListener {

    private static final Set<Integer> pressedButtons = new HashSet<>();
    private static Vector2 mousePosition = new Vector2(0, 0);
    private static Component attachedComponent;

    @Override
    public void mousePressed(MouseEvent e) {
        pressedButtons.add(e.getButton());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        pressedButtons.remove(e.getButton());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePosition.set(e.getX(), e.getY());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mousePosition.set(e.getX(), e.getY());
    }

    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    public static boolean isButtonPressed(int button) {
        return pressedButtons.contains(button);
    }

    public static Vector2 getMousePosition() {
        return mousePosition;
    }

    public static void attachTo(Component component) {
        MouseInputHandler mouseInput = new MouseInputHandler();
        component.addMouseListener(mouseInput);
        component.addMouseMotionListener(mouseInput);
        component.setFocusable(true);
        component.requestFocus();
        attachedComponent = component;
    }

    public static void setCursorVisible(boolean visible) {
        if (attachedComponent == null) return;

        if (visible) {
            attachedComponent.setCursor(Cursor.getDefaultCursor());
        } else {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Image image = toolkit.createImage(new byte[0]);
            Cursor invisibleCursor = toolkit.createCustomCursor(image, new Point(0, 0), "invisibleCursor");
            attachedComponent.setCursor(invisibleCursor);
        }
    }
}
