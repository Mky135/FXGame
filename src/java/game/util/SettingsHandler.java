package game.util;

import javafx.scene.paint.Color;

public class SettingsHandler
{
    private FileHandler settings;

    public SettingsHandler()
    {
        settings = new FileHandler("src/Settings.txt");
    }

    private Color getColorFromText(String string)
    {
        char[] chars = string.toCharArray();
        double r=0,g=0,b=0;

        for(int i=0; i< string.length(); i++)
        {
            if(chars[i] == 'r')
                r = chars[i+2]-48;

            if(chars[i] == 'g')
                g = chars[i+2]-48;

            if(chars[i] == 'b')
                b = chars[i+2]-48;
        }

        return new Color(r,g,b,1);
    }

    private boolean checkIfColorFormat(String string)
    {
        boolean containsR = false;
        boolean containsG = false;
        boolean containsB = false;
        char[] chars = string.toCharArray();

        for(int i=0; i<string.length(); i++)
        {
            if(chars[i] == 'r' && chars[i+1] == ':')
                containsR = true;
            if(chars[i] == 'g' && chars[i+1] == ':')
                containsG = true;
            if(chars[i] == 'b' && chars[i+1] == ':')
                containsB = true;
        }

        return containsR && containsG && containsB;
    }

    public Color getCircleColor()
    {
        return getColorFromLine(0, "Circle: ");
    }

    public Color getBackGroundColor()
    {
        return getColorFromLine(1, "Background: ");
    }

    private Color getColorFromLine(int line, String start)
    {
        String colorString = settings.getLine(line).substring(start.length(), settings.getLine(line).length());
        Color color;

        if(checkIfColorFormat(colorString))
            color = getColorFromText(colorString);
        else
        {
            settings.modifyLine(settings.getLine(line), start + "r:0 g:0 b:0;");
            colorString = settings.getLine(line).substring(start.length(), settings.getLine(line).length());
            color = getColorFromText(colorString);
        }

        return color;
    }
}
