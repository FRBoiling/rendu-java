package constant;

import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-09
 * Time: 14:24
 */
@Getter
public enum ErrorCode {
    FAIL(0),
    SUCCESS(1),
    ServerNotOpen(2),
    BadToken(3),
    NotCreating(4),
    DuplicatedName(5);

    private int value;
    ErrorCode(int value){
        this.value=value;
    }
}

