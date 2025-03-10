package com.goods.utils;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class SimpleDocumentUtil implements DocumentListener {
    private Runnable runnable;

    SimpleDocumentUtil(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        runnable.run();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        runnable.run();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        runnable.run();
    }
}
