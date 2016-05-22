package net.sp1d.chym.web.util;

import java.util.Collection;

/**
 *
 * @author sp1d
 */
public class ContainsFunctionJSP {

    public static Boolean collectionContains(Collection collection, Object item) {
        if (collection != null) {
            return collection.contains(item);
        } else {
            return false;
        }
    }

}
