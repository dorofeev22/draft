package ru.dorofeev22.draft.endpoint;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dorofeev22.draft.DraftProperties;
import ru.dorofeev22.draft.core.constant.UrlConstants;
import ru.dorofeev22.draft.core.crypt.CryptoHelper;
import ru.dorofeev22.draft.core.crypt.HmacHelper;
import ru.dorofeev22.draft.service.model.OrderModel;
import ru.dorofeev22.draft.service.model.PaymentModel;

@RestController
@RequestMapping(value = UrlConstants.PUBLIC, produces = MediaType.APPLICATION_JSON_VALUE)
public class PublicEndpoint {
    
    private final Logger log = LoggerFactory.getLogger(getClass());
    
    private static final String PAYMENT_SIGNED_DATA_DELIMITER = ";";
    private final HmacHelper hmacHelper;
    private final DraftProperties draftProperties;
    private final CryptoHelper cryptoHelper;
    
    public PublicEndpoint(HmacHelper hmacHelper, DraftProperties draftProperties, CryptoHelper cryptoHelper) {
        this.hmacHelper = hmacHelper;
        this.draftProperties = draftProperties;
        this.cryptoHelper = cryptoHelper;
    }
    
    @PostMapping(UrlConstants.PAYMENTS)
    public void post(@RequestBody PaymentModel paymentModel) {
        if (!ifSignatureCorrect(paymentModel)) {
            throw new RuntimeException("Service unavailable");
        }
    }

    @PostMapping(UrlConstants.ORDERS)
    public void post(@RequestBody OrderModel orderModel) {
        try {
            final String decodedMessage =
                    cryptoHelper.decrypt(
                            cryptoHelper.getDecrypt(draftProperties.getOrderPublicKeyPath()),
                            orderModel.getMessage());
            // todo: use decoded message as required
            log.info("decoded message: {}", decodedMessage);
        } catch (Exception e) {
            throw new RuntimeException("Service unavailable");
        }
    }

    private boolean ifSignatureCorrect(@NotNull final PaymentModel paymentModel) {
        return hmacHelper.ifSignatureCorrect(
                paymentModel.getSignature(),
                draftProperties.getPaymentSecretKey(),
                paymentModel,
                PAYMENT_SIGNED_DATA_DELIMITER);
    }
}