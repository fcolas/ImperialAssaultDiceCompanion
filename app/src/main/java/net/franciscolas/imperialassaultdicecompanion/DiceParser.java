package net.franciscolas.imperialassaultdicecompanion;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Parser for the xml resource representing the different dice in Imperial Assault
 * Inspired from http://developer.android.com/training/basics/network-ops/xml.html
 */
public class DiceParser {
    // We don't use namespaces
    private static final String ns = null;

    public DiceSet parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readDie(parser);
        } finally {
            in.close();
        }
    }

    private DiceSet readDie(XmlPullParser parser) throws XmlPullParserException, IOException {
        DiceSet diceSet = new DiceSet();
        parser.require(XmlPullParser.START_TAG, ns, "dice");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Looks for a Die tag
            if (name.equals("die")) {
                diceSet.put(readDice(parser));
            } else {
                skip(parser);
            }
        }
        return diceSet;
    }

    private Die readDice(XmlPullParser parser) throws XmlPullParserException, IOException {
        Die die = new Die();
        parser.require(XmlPullParser.START_TAG, ns, "die");
        die.setName(parser.getAttributeValue(ns, "name"));
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Looks for a Face tag
            if (name.equals("face")) {
                die.addFace(readFace(parser));
            } else {
                skip(parser);
            }
        }
        return die;
    }

    private Face readFace(XmlPullParser parser) throws XmlPullParserException, IOException {
        Face face = new Face();
        parser.require(XmlPullParser.START_TAG, ns, "face");
        String buffer;
        buffer = parser.getAttributeValue(ns, "damage");
        if (buffer != null) {
            face.setDamage(Integer.parseInt(buffer));
        }
        buffer = parser.getAttributeValue(ns, "surge");
        if (buffer != null) {
            face.setSurge(Integer.parseInt(buffer));
        }
        buffer = parser.getAttributeValue(ns, "range");
        if (buffer != null) {
            face.setRange(Integer.parseInt(buffer));
        }
        buffer = parser.getAttributeValue(ns, "block");
        if (buffer != null) {
            face.setBlock(Integer.parseInt(buffer));
        }
        buffer = parser.getAttributeValue(ns, "evade");
        if (buffer != null) {
            face.setEvade(Integer.parseInt(buffer));
        }
        buffer = parser.getAttributeValue(ns, "dodge");
        if (buffer != null) {
            face.setDodge(Integer.parseInt(buffer));
        }
        parser.nextTag();
        parser.require(XmlPullParser.END_TAG, ns, "face");
        return face;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
