package project.BaekjoonStatus.shared.common.repository;

import project.BaekjoonStatus.shared.common.exception.InvalidIdFormatException;

public abstract class BaseRepository {
    protected Long parseLong(String str) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            throw new InvalidIdFormatException();
        }
    }
}
