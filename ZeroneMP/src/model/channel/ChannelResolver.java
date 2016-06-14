package model.channel;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

/**
 * Created by Getachew on 5/29/2016.
 *
 * @author Getachew Mulat
 */
public class ChannelResolver {

    private static String channelName;
    private static String channelImage;
    private static String channelUrl;
    private static String type;

    private static boolean validImage = false;
    private static boolean validUrl = false;


    public static void updateChannel() {
        CheckValidation();

        if(validUrl && validImage) {

            if (Objects.equals(type, "Tv")) {

                AddNewChannel.insertTvValue(getChannelName(), getChannelImage(), getChannelUrl());

            } else if (Objects.equals(type, "Radios")) {

                AddNewChannel.insertRadiosValue(getChannelName(), getChannelImage(), getChannelUrl());

            } else if (Objects.equals(type, "Movies")) {

                AddNewChannel.insertMoviesValue(getChannelName(), getChannelImage(), getChannelUrl());

            } else if (Objects.equals(type, "Videos")) {

                AddNewChannel.insertVideosValue(getChannelName(), getChannelImage(), getChannelUrl());

            }

        }
    }

    public static void CheckValidation() {

        if(verifyImageLink()) {
            validImage = true;
        }

        if(verifyChannelLink()) {
            validUrl = true;
        }
    }

    private static boolean verifyImageLink() {

        return verifyLink(getChannelImage());

    }

    private static boolean verifyChannelLink() {
        return verifyLink(getChannelUrl());

    }

    private static boolean verifyLink(String url) {

        try {
            URI imageUri = new URI(url);
            URL imageUrl = imageUri.toURL();

            return true;
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
            return false;
        } catch (URISyntaxException e) {
            System.out.println(e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static String getChannelImage() {
        return channelImage;
    }

    public static void setChannelImage(String channelImage) {
        ChannelResolver.channelImage = channelImage;
    }

    public static String getChannelName() {
        return channelName;
    }

    public static void setChannelName(String channelName) {
        ChannelResolver.channelName = channelName;
    }

    public static String getChannelUrl() {
        return channelUrl;
    }

    public static void setChannelUrl(String channelUrl) {
        ChannelResolver.channelUrl = channelUrl;
    }

    public static String getType() {
        return type;
    }

    public static void setType(String channelType) {
        ChannelResolver.type = channelType;
    }

    public static boolean isValidImage() {
        return validImage;
    }

    public static boolean isValidUrl() {
        return validUrl;
    }
}
