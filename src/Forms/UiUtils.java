/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Forms;

/**
 *
 * @author Jobelle
 */
public final class UiUtils {
    private UiUtils() {}

    /** Convert an object to a non-null trimmed string for table code. */
    public static String safeToString(Object o) {
        if (o == null) return "";
        String s = o.toString();
        return s == null ? "" : s.trim();
    }
}
