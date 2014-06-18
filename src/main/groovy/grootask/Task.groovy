package grootask

import java.lang.annotation.Target
import java.lang.annotation.Retention
import java.lang.annotation.ElementType
import java.lang.annotation.RetentionPolicy

@Target([ElementType.TYPE])
@Retention(RetentionPolicy.SOURCE)
@interface Task {
    Class<?> value()
}
