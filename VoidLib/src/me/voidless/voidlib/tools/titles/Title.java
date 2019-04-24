package me.voidless.voidlib.tools.titles;

public class Title {

    public TitleType type;
    public String text;
    public String subText;

    public boolean loop;
    public int left;

    public int fadeIn;
    public int stay;
    public int fadeOut;

    public Title(final TitleType titleType, final String text, final int fadeIn, final int stay, final int fadeOut){
        this.type = titleType;
        this.text = text;
        this.subText = "";

        this.loop = false;
        this.left = fadeIn + stay + fadeOut;

        this.fadeIn = fadeIn;
        this.stay = stay;
        this.fadeOut = fadeOut;
    }

    public Title(final TitleType titleType, final String text, final String subText, final int fadeIn, final int stay, final int fadeOut){
        this.type = titleType;
        this.text = text;
        this.subText = subText;

        this.loop = false;
        this.left = fadeIn + stay + fadeOut;

        this.fadeIn = fadeIn;
        this.stay = stay;
        this.fadeOut = fadeOut;
    }

    public void loop(){
        this.loop = true;
        reset();
    }

    public void reset(){
        this.left = fadeIn + stay + (this.loop ? 0 : fadeOut);
    }
}
