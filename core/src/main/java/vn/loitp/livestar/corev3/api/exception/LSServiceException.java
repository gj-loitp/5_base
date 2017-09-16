package vn.loitp.livestar.corev3.api.exception;

import java.io.IOException;

/**
 * Created by Phu Tran on 8/10/2015.
 */
public class LSServiceException extends IOException {
    private static final long serialVersionUID = 4441445153211266013L;

    public LSServiceException(String message) {
        super(message);
    }
}
