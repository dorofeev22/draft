package ru.dorofeev22.draft.endpoint;

import org.apache.commons.lang3.tuple.ImmutablePair;
import ru.dorofeev22.draft.core.utils.UrlParam;
import ru.dorofeev22.draft.service.model.UserRequest;

import java.util.ArrayList;
import java.util.List;

import static ru.dorofeev22.draft.core.constant.RequestConstants.LIMIT;
import static ru.dorofeev22.draft.core.constant.RequestConstants.OFFSET;

public class UserTestHelper {

    public static UserRequest createUserCreationModel(String name, String login) {
        UserRequest userRequest = new UserRequest();
        userRequest.setName(name);
        userRequest.setLogin(login);
        userRequest.setPassword("1_ghyjk^9");
        return userRequest;
    }

    public static List<ImmutablePair<String, UrlParam>> createLikeSearchParameters() {
        return new ArrayList<ImmutablePair<String, UrlParam>>() {{
            add(createParameter("name", "r"));
            add(createParameter("login", "ey"));
        }};
    }

    public static List<ImmutablePair<String, UrlParam>> createInSearchParameters() {
        return new ArrayList<ImmutablePair<String, UrlParam>>() {{
            add(createParameter("id", "087053ae-c31f-4842-baba-9ebafe3ee594"));
            add(createParameter("id", "087053ae-c31f-4842-baba-9ebafe3ee59b"));
        }};
    }

    public static List<ImmutablePair<String, UrlParam>> createDateSearchParameters() {
        return new ArrayList<ImmutablePair<String, UrlParam>>() {{
            add(createParameter("creationMoment", "2021-03-13T01:00:00.000"));
            add(createParameter("creationMoment", "2021-03-14T22:00:00.000"));
        }};
    }

    public static List<ImmutablePair<String, UrlParam>> createPageParameters(int limit, int offset) {
        return new ArrayList<ImmutablePair<String, UrlParam>>() {{
            add(createParameter(LIMIT, String.valueOf(limit)));
            add(createParameter(OFFSET, String.valueOf(offset)));
        }};
    }

    private static ImmutablePair<String, UrlParam> createParameter(String paramId, String paramValue) {
        return new ImmutablePair<>(paramId, new UrlParam(paramValue));
    }

}