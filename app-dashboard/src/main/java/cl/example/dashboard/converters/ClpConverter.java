package cl.example.dashboard.converters;

import org.apache.commons.lang3.StringUtils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

@FacesConverter("cl.example.dashboard.converters.ClpConverter")
public class ClpConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (StringUtils.isEmpty(string)) {
            return null;
        }
        return Double.parseDouble(string.replace("\\.", "").replace("\\$", ""));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object obj) {
        if (obj == null) {
            return "";
        }

        if (!(obj instanceof Number)) {
            throw new RuntimeException("Value to be converted is not numeric!");
        }

        double n = ((Number) obj).doubleValue();
        String sign = (n < 0 ? "-" : "");
        if (n < 0) {
            n *= -1;
        }
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.GERMAN);
        return sign + "$" + formatter.format(n);
    }

}
