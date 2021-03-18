package ru.dorofeev22.draft.endpoint;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dorofeev22.draft.DraftProperties;
import ru.dorofeev22.draft.core.constant.UrlConstants;
import ru.dorofeev22.draft.core.utils.SignatureHelper;
import ru.dorofeev22.draft.service.model.PaymentModel;

@RestController
@RequestMapping(value = UrlConstants.PUBLIC, produces = MediaType.APPLICATION_JSON_VALUE)
public class PublicEndpoint {
    
    private static final String PAYMENT_SIGNED_DATA_DELIMITER = ";";
    private final SignatureHelper signatureHelper;
    private final DraftProperties draftProperties;
    
    public PublicEndpoint(SignatureHelper signatureHelper, DraftProperties draftProperties) {
        this.signatureHelper = signatureHelper;
        this.draftProperties = draftProperties;
    }
    
    @PostMapping(UrlConstants.PAYMENTS)
    public void post(@RequestBody PaymentModel paymentModel) {
        if (!ifSignatureCorrect(paymentModel)) {
            throw new RuntimeException("Service unavailable");
        }
    }
    
    private boolean ifSignatureCorrect(@NotNull final PaymentModel paymentModel) {
        return signatureHelper.ifSignatureCorrect(
                paymentModel.getSignature(),
                draftProperties.getPaymentSecretKey(),
                paymentModel,
                PAYMENT_SIGNED_DATA_DELIMITER);
    }
}