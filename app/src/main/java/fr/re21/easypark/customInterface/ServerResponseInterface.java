package fr.re21.easypark.customInterface;

/**
 * Created by maxime on 19/05/15.
 */
public interface ServerResponseInterface {
    public void onEventCompleted(int method, String type);
    public void onEventFailed(int method, String type);
}