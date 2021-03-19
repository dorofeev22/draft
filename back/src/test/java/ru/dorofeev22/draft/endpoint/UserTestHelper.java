package ru.dorofeev22.draft.endpoint;

import org.apache.commons.lang3.tuple.ImmutablePair;
import ru.dorofeev22.draft.service.model.UserCreationModel;

import java.util.ArrayList;
import java.util.List;

import static ru.dorofeev22.draft.core.constant.RequestConstants.LIMIT;
import static ru.dorofeev22.draft.core.constant.RequestConstants.OFFSET;

public class UserTestHelper {

    public static UserCreationModel createUserCreationModel(String name, String login) {
        UserCreationModel userCreationModel = new UserCreationModel();
        userCreationModel.setName(name);
        userCreationModel.setLogin(login);
        userCreationModel.setPassword("1_ghyjk^9");
        return userCreationModel;
    }

    public static List<ImmutablePair<String, String>> createLikeSearchParameters() {
        return new ArrayList<ImmutablePair<String, String>>() {{
            add(new ImmutablePair<>("name", "r"));
            add(new ImmutablePair<>("login", "ey"));
        }};
    }

    public static List<ImmutablePair<String, String>> createInSearchParameters() {
        return new ArrayList<ImmutablePair<String, String>>() {{
            add(new ImmutablePair<>("id", "087053ae-c31f-4842-baba-9ebafe3ee594"));
            add(new ImmutablePair<>("id", "087053ae-c31f-4842-baba-9ebafe3ee59b"));
        }};
    }

    public static List<ImmutablePair<String, String>> createDateSearchParameters() {
        return new ArrayList<ImmutablePair<String, String>>() {{
            add(new ImmutablePair<>("creationMoment", "2021-03-13T01:00:00.000"));
            add(new ImmutablePair<>("creationMoment", "2021-03-14T22:00:00.000"));
        }};
    }

    public static List<ImmutablePair<String, String>> createPageParameters(int limit, int offset) {
        return new ArrayList<ImmutablePair<String, String>>() {{
            add(new ImmutablePair<>(LIMIT, String.valueOf(limit)));
            add(new ImmutablePair<>(OFFSET, String.valueOf(offset)));
        }};
    }

}