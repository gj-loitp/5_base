package vn.puresolutions.livestar.corev3.api.service;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;
import vn.puresolutions.livestar.corev3.api.model.old.Mail;

/**
 * @author hoangphu
 * @since 7/21/16
 */
public interface MailService {
    String CONTROLLER = "mails";

    @GET(CONTROLLER + "/")
    Observable<List<Mail>> getMails();
}
