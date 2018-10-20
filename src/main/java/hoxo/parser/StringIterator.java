package hoxo.parser;

import java.util.Iterator;
import java.util.Optional;

public class StringIterator implements Iterator<Character> {
    private String text;
    private int pos;
    private boolean eos;
    private boolean bos;

    public StringIterator(String text) {
        this.text = text;
        pos = -1;
    }

    @Override
    public boolean hasNext() {
        return pos < text.length() - 1;
    }

    public boolean hasPrev() {
        return pos > 0;
    }

    @Override
    public Character next() {
        eos = !hasNext();
        if (hasNext()) {
            return text.charAt(++pos);
        } else {
            pos = text.length();
            return null;
        }
    }

    public Character prev() {
        bos = !hasPrev();
        if (hasPrev()) {
            return text.charAt(--pos);
        } else {
            pos = -1;
            return null;
        }
    }

    public Character current() {
        if (!bos || !eos) {
            return text.charAt(pos);
        } else {
            return null;
        }
    }

    public Character peekNext() {
        return hasNext() ? text.charAt(pos + 1) : null;
    }

    public Character peekPrev() {
        return hasPrev() ? text.charAt(pos - 1) : null;
    }

    public boolean afterLast() {
        return eos;
    }

    public boolean behindFirst() {
        return bos;
    }
}
