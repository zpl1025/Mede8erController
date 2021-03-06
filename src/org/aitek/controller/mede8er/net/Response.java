package org.aitek.controller.mede8er.net;

/**
 * Created with IntelliJ IDEA.
 * User: andrea
 * Date: 8/26/13
 * Time: 12:27 PM
 */
public class Response {
    private Value value;

    private String content;
    public enum Value {
        OK, FAIL, ERR_UNKNOWN_TAG, ERR_NO_MEDIA, ERR_OPEN_DIR, ERR_FAIL, ERR_PLAYING, ERR_RC_UNKNOWN, EMPTY;

    }
    public Response(Value value, String content) {
        this.value = value;

        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public Value getValue() {
        return value;
    }

    public boolean isEmpty() {
        return value.toString().toUpperCase().equals(Value.EMPTY);
    }

    @Override
    public String toString() {
        return "Response{" +
                "value=" + value +
                ", content='" + content + '\'' +
                '}';
    }

}
