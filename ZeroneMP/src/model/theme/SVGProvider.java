package model.theme;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Getachew on 5/26/2016.
 *
 * @author Getachew Mulat
 */
public class SVGProvider {

    private StringProperty pauseSvg = new SimpleStringProperty(this, null, "M12 38h8V10h-8v28zm16-28v28h8V10h-8z");
    private StringProperty playSvg = new SimpleStringProperty(this, null, "M16 10v28l22-14z");
    private StringProperty fastRewind = new SimpleStringProperty(this, null, "M22 36V12L5 24l17 12zm1-12l17 12V12L23 24z");
    private StringProperty fastForward = new SimpleStringProperty(this, null, "M8 36l17-12L8 12v24zm18-24v24l17-12-17-12z");
    private StringProperty stopSvg = new SimpleStringProperty(this, null, "M12 12h24v24H12z");
    private StringProperty repeatAllSvg = new SimpleStringProperty(this, null, "M14 14h20v6l8-8-8-8v6H10v12h4v-8zm20 20H14v-6l-8 8 8 8v-6h24V26h-4v8z");
    private StringProperty repeatOneSvg = new SimpleStringProperty(this, null, "M14 14h20v6l8-8-8-8v6H10v12h4v-8zm20 20H14v-6l-8 8 8 8v-6h24V26h-4v8zm-8-4V18h-2l-4 2v2h3v8h3z");
    private StringProperty fullscreenSvg = new SimpleStringProperty(this, null, "M14 28h-4v10h10v-4h-6v-6zm-4-8h4v-6h6v-4H10v10zm24 14h-6v4h10V28h-4v6zm-6-24v4h6v6h4V10H28z");

    private StringProperty volumeDown = new SimpleStringProperty(this, null, "M37 24c0-3.53-2.04-6.58-5-8.05v16.11c2.96-1.48 5-4.53 5-8.06zm-27-6v12h8l10 10V8L18 18h-8z");
    private StringProperty volumeUp = new SimpleStringProperty(this, null, "M6 18v12h8l10 10V8L14 18H6zm27 6c0-3.53-2.04-6.58-5-8.05v16.11c2.96-1.48 5-4.53 5-8.06zM28 6.46v4.13c5.78 1.72 10 7.07 10 13.41s-4.22 11.69-10 13.41v4.13c8.01-1.82 14-8.97 14-17.54S36.01 8.28 28 6.46z");
    private StringProperty volumeMute = new SimpleStringProperty(this, null, "M14 18v12h8l10 10V8L22 18h-8z");
    private StringProperty volumeOff = new SimpleStringProperty(this, null, "M33 24c0-3.53-2.04-6.58-5-8.05v4.42l4.91 4.91c.06-.42.09-.85.09-1.28zm5 0c0 1.88-.41 3.65-1.08 5.28l3.03 3.03C41.25 29.82 42 27 42 24c0-8.56-5.99-15.72-14-17.54v4.13c5.78 1.72 10 7.07 10 13.41zM8.55 6L6 8.55 15.45 18H6v12h8l10 10V26.55l8.51 8.51c-1.34 1.03-2.85 1.86-4.51 2.36v4.13c2.75-.63 5.26-1.89 7.37-3.62L39.45 42 42 39.45l-18-18L8.55 6zM24 8l-4.18 4.18L24 16.36V8z");

    private StringProperty addSvg = new SimpleStringProperty(this, null, "M38 26H26v12h-4V26H10v-4h12V10h4v12h12v4z");

    public StringProperty volumeDownProperty() {
        return volumeDown;
    }

    public StringProperty volumeMuteProperty() {
        return volumeMute;
    }

    public StringProperty volumeOffProperty() {
        return volumeOff;
    }

    public StringProperty volumeUpProperty() {
        return volumeUp;
    }

    public StringProperty fastForwardProperty() {
        return fastForward;
    }

    public StringProperty fastRewindProperty() {
        return fastRewind;
    }

    public StringProperty fullscreenSvgProperty() {
        return fullscreenSvg;
    }

    public StringProperty repeatAllSvgProperty() {
        return repeatAllSvg;
    }

    public StringProperty repeatOneSvgProperty() {
        return repeatOneSvg;
    }

    public StringProperty stopSvgProperty() {
        return stopSvg;
    }

    public StringProperty playSvgProperty() {
        return playSvg;
    }

    public StringProperty pauseSvgProperty() {
        return pauseSvg;
    }

    public StringProperty addSvgProperty() {
        return addSvg;
    }

}
